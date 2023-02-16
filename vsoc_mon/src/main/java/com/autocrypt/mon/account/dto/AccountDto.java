package com.autocrypt.mon.account.dto;

import com.autocrypt.mon.account.entity.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class AccountResponse {
        private final String accountId;
        private final String name;
        private final String role;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime createdDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime lastAccessTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime logCheckTime;

        public AccountResponse(Account account) {
            this.accountId = account.getAccountId();
            this.name = account.getName();
            this.role = account.getRole();
            this.createdDate = account.getCreatedDate();
            this.lastAccessTime = account.getLastAccessTime();
            this.logCheckTime = account.getLogCheckTime();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class AccountLoginSuccessResponse {
        private final String accountId;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime lastAccessTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime logCheckTime;

        public AccountLoginSuccessResponse(Account account) {
            this.accountId = account.getAccountId();
            this.lastAccessTime = account.getLastAccessTime() != null ? account.getLastAccessTime() : account.getLastAccessTime();
            this.logCheckTime = account.getLogCheckTime();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AccountCreateRequest {
        String loginId;
        String name;
        String password;
    }

}
