package com.autocrypt.mon.vehicleInfo.repository.entity;

import com.autocrypt.mon.vehicleInfo.dto.VehicleDto;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vehicle_info_from_chang_an")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInfo {
    @Id
    @Column(name = "tuid")
    private String tuid;

    @NotNull
    @Column(name = "created_at")
    private Date createdAt;

    @NotNull
    @Column(name = "seriousness")
    private String seriousness;

    @NotNull
    @Column(name = "region")
    private String region;

    @NotNull
    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "brand_code")
    private String brandCode;

    @Column(name = "brand_id")
    private String brandId;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "car_id")
    private String carId;

    @Column(name = "car_name")
    private String carName;

    @Column(name = "car_type")
    private String carType;

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "conf_code")
    private String confCode;

    @Column(name = "conf_id")
    private String confId;

    @Column(name = "conf_name")
    private String confName;

    @Column(name = "dealer_id")
    private String dealerId;

    @Column(name = "deleted")
    private int deleted;

    @Column(name = "engine_no")
    private String engineNo;

    @Column(name = "img")
    private String img;

    @Column(name = "insurance_company")
    private String insuranceCompany;

    @Column(name = "insurance_phone")
    private String insurancePhone;

    @Column(name = "is_ca_car")
    private String isCaCar;

    @Column(name = "is_nev")
    private int isNev;

    @Column(name = "is_privacy_drving_history")
    private boolean isPrivacyDrvingHistory;

    @Column(name = "last_maintain_mileage")
    private int lastMaintainMileage;

    @Column(name = "maintain_space_mileage")
    private int maintainSpaceMileage;

    @Column(name = "material_id")
    private String materialId;

    @NotNull
    @Column(name = "model_code")
    private String modelCode;

    @Column(name = "model_id")
    private String modelId;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "oem_company_id")
    private String oemCompanyId;

    @Column(name = "oil_type")
    private String oilType;

    @Column(name = "pin")
    private String pin;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "product_time")
    private Date productTime;

    @Column(name = "realname_auth_idcard")
    private String realnameAuthIdcard;

    @Column(name = "realname_auth_idcard_type")
    private String realnameAuthIdcardType;

    @Column(name = "realname_auth_mobile")
    private String realnameAuthMobile;

    @Column(name = "realname_auth_pass_time")
    private Date realnameAuthPassTime;

    @Column(name = "realname_auth_status")
    private String realnameAuthStatus;

    @Column(name = "realname_type")
    private int realnameType;

    @NotNull
    @Column(name = "register_time")
    private String registerTime;

    @NotNull
    @Column(name = "series_code")
    private String seriesCode;

    @Column(name = "series_id")
    private String seriesId;

    @Column(name = "series_name")
    private String seriesName;

    @Column(name = "source")
    private String source;

    @Column(name = "system_code")
    private String systemCode;

    @NotNull
    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "total_odometer")
    private int totalOdometer;

    @Column(name = "tservice_endtime")
    private Date tserviceEndtime;

    @Column(name = "tservice_starttime")
    private Date tserviceStarttime;

    @Column(name = "tservice_status")
    private String tserviceStatus;

    @Column(name = "user_id")
    private String userId;

    @NotNull
    @Column(name = "vin")
    private String vin;

    @Transient
    public static VehicleDto.VehicleItem toVehicleItem(VehicleInfo vehicleInfo) {

        return VehicleDto.VehicleItem.builder()
                .vehicleId(vehicleInfo.getVin())
                .policy("Policy_ALSVIN_210")
                .version("1.0")
                .registeredDate(new Date())
                .build();
    }
}
