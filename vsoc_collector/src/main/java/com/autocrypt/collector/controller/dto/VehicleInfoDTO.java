package com.autocrypt.collector.controller.dto;

import com.autocrypt.collector.model.CarInfoMapping;
import com.autocrypt.collector.repository.entity.VehicleInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class VehicleInfoDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetCarInfoByBinReq {
        private String vin;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccessTokenResp {
        private String access_token;
        private String token_type;
        private String expires_in;
        private String scope;
        private String secret_key;
        private String pre_oauth_token;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangAnResp<T> {
        private int code;
        private T data;
        private String msg;
        private boolean success;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Vin {
        private String vin;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class vehicleInfo {
        private String tuid;
        private String brandCode;
        private String brandId;
        private String brandName;
        private String carId;
        private String carName;
        private String carType;
        private String companyId;
        private String confCode;
        private String confId;
        private String confName;
        private String dealerId;
        private int deleted;
        private String engineNo;
        private String img;
        private String insuranceCompany;
        private String insurancePhone;
        private String isCaCar;
        private int isNev;
        private boolean isPrivacyDrvingHistory;
        private int lastMaintainMileage;
        private int maintainSpaceMileage;
        private String materialId;
        private String modelCode;
        private String modelId;
        private String modelName;
        private String oemCompanyId;
        private String oilType;
        private String pin;
        private String plateNumber;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date productTime;
        private String realnameAuthIdcard;
        private String realnameAuthIdcardType;
        private String realnameAuthMobile;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date realnameAuthPassTime;
        private String realnameAuthStatus;
        private int realnameType;
        private String registerTime;
        private String seriesCode;
        private String seriesId;
        private String seriesName;
        private String source;
        private String systemCode;
        private String tenantId;
        private int totalOdometer;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date tserviceEndtime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date tserviceStarttime;
        private String tserviceStatus;
        private String userId;
        private String vin;
    }

    public static VehicleInfo CarInfoDTOtoCarInfoEntity(vehicleInfo vehicleInfo) {
        return CarInfoMapping.INSTANCE.CarInfoDTOtoCarInfoEntity(vehicleInfo);
    }

}
