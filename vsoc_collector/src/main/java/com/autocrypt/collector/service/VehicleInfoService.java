package com.autocrypt.collector.service;

import com.autocrypt.collector.common.VehicleInfoSignature;
import com.autocrypt.collector.common.Nonce;
import com.autocrypt.collector.controller.dto.VehicleInfoDTO;
import com.autocrypt.collector.repository.VehicleInfoRepository;
import com.autocrypt.collector.repository.entity.VehicleInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleInfoService {

    private final VehicleInfoRepository vehicleInfoRepository;

    private static final String BASE_HOST = "https://dev-open.iov.changan.com.cn";
    private static final String CLIENT_ID = "22672fe8df0f4f3dad5c7780ebef3baf";
    private static final String APPLICATION_KEY = "MjI2NzJmZThkZjBmNGYzZA==";

    @Transactional
    public VehicleInfo saveAndGetVehicleInfoByTUID(String tuid, String region, String city) throws Exception {
        // Use Chang An Open API for get Vehicle Info and Restore them with tuid, region and city
        Optional<VehicleInfo> optionalVehicleInfo =  vehicleInfoRepository.findByTuidEquals(tuid);
        VehicleInfo vehicleInfo;
        if (optionalVehicleInfo.isPresent()) {
            vehicleInfo = optionalVehicleInfo.get();
        } else {
            VehicleInfoDTO.AccessTokenResp accessTokenResp = this.getAccessToken();
            // get VIN from tuid, get VehicleInfo from tuid, VIN
            String vin = this.tuidToVin(accessTokenResp.getAccess_token(), tuid);
            vehicleInfo = this.getVehicleInfo(accessTokenResp.getAccess_token(), tuid, vin);
        }

        vehicleInfo.setSeriousness("NORMAL");
        vehicleInfo.setRegion(region);
        vehicleInfo.setCity(city);
        vehicleInfoRepository.saveAndFlush(vehicleInfo);

        return vehicleInfo;
    }

    @Transactional
    public void updateVehicleSeriousness(VehicleInfo vehicleInfo, String seriousness) {
        String original = vehicleInfo.getSeriousness();
        if (original.equals("WARNING")) {
            if (seriousness.equals("DANGER")) {
                vehicleInfo.setSeriousness(seriousness);
            }
        } else if (original.equals("NORMAL")) {
            vehicleInfo.setSeriousness(seriousness);
        }
        vehicleInfoRepository.save(vehicleInfo);
    }

    private VehicleInfoDTO.AccessTokenResp getAccessToken() throws Exception {
        // Issue AccessToken for Using Chang An Open API
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", CLIENT_ID);
        body.add("client_secret", APPLICATION_KEY);
        body.add("grant_type", "client_credentials");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<VehicleInfoDTO.AccessTokenResp> res = new RestTemplate().exchange(
                BASE_HOST + "/oauth/token",
                HttpMethod.POST,
                httpEntity,
                VehicleInfoDTO.AccessTokenResp.class
        );
        if (res.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Exception Occurred With Get Access Token");
        } else {
            return res.getBody();
        }
    }

    private String tuidToVin(String accessToken, String tuid) throws Exception {
        String url = "/open-apigw/bdc/api/v2/open/car/getVinByTuid";
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(BASE_HOST + url)
                .queryParam("tuid", tuid);

        HttpEntity<?> httpEntity = new HttpEntity<>(buildChangAnHeader(url, "tuid="+tuid, accessToken));
        ResponseEntity<VehicleInfoDTO.ChangAnResp<VehicleInfoDTO.Vin>> res = new RestTemplate().exchange(
                uriComponentsBuilder.toUriString(),
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<VehicleInfoDTO.ChangAnResp<VehicleInfoDTO.Vin>>() {}
        );
        if (res.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Exception Occurred With TuidToVin Response Not OK");
        } else if (!res.getBody().isSuccess()) {
            throw new Exception("Exception Occurred With TuidToVin Response Not Success : Response : " + res.getBody().getMsg());
        } else {
            VehicleInfoDTO.Vin vin = res.getBody().getData();
            return vin.getVin();
        }
    }


    public VehicleInfo getVehicleInfo(String accessToken, String tuid, String vin) throws Exception {
        String url = "/open-apigw/openserver/api/v2/open/car/get-full-car-info-by-vin";
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(BASE_HOST + url)
                .queryParam("vin", vin);

        HttpEntity<?> httpEntity = new HttpEntity<>(buildChangAnHeader(url, "vin="+vin, accessToken));
        ResponseEntity<VehicleInfoDTO.ChangAnResp<VehicleInfoDTO.vehicleInfo>> res = new RestTemplate().exchange(
                uriComponentsBuilder.toUriString(),
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<VehicleInfoDTO.ChangAnResp<VehicleInfoDTO.vehicleInfo>>() {}
        );
        if (res.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Exception Occurred With Get Vehicle Info Response Not OK");
        } else if (!res.getBody().isSuccess()) {
            throw new Exception("Exception Occurred With Get Vehicle Info Response Not Success " + res.getBody().getMsg());
        } else {
            VehicleInfo vehicleInfoResp = VehicleInfoDTO.CarInfoDTOtoCarInfoEntity(res.getBody().getData());
            vehicleInfoResp.setTuid(tuid);
            vehicleInfoResp.setCreatedAt(new Date());

            return vehicleInfoResp;
        }
    }

    public HttpHeaders buildChangAnHeader(String url, String parameters, String accessToken) throws Exception {
        String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
        String nonce = Nonce.generate(4, new SecureRandom(), Nonce.ALPHANUM);

        // Need to make Signature with accessToken for Using Chang An Open API
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-VCS-Access-Token", accessToken);
        httpHeaders.add("X-VCS-Timestamp", timestamp);
        httpHeaders.add("X-VCS-Nonce", nonce);
        httpHeaders.add("Authorization", "VCS-HMAC-SHA256 Signature=" +
                VehicleInfoSignature.generate(url, parameters, timestamp, nonce, accessToken, APPLICATION_KEY));
        return httpHeaders;
    }

}
