package com.autocrypt.mon.config;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum LoginResponseType {

    SUCCESS_LOGIN("000000","login.success"),
    SUGGEST_PASSWORD_CHANGE("000010", "login.suggest_password_change"),
    UPDATE_MY_INFO("000011", "login.update_my_info"),
    END_VALIDITY_LICENSE("000012", "login.end_validity_license"),
    NOT_REGISTER_LICENSE("000013", "login.not_register_license"),

    LOCK_PASSWORD_EXP("000021", "login.lock_password_exp"),
    BAD_CREDENTIAL("000022", "login.bad_credential"),
    NOT_FOUNT_USER("000023", "login.not_found_user"),
    UNKNOWN("000001", "login.unknown_error");


    private String resultCode;
    private String message;

}
