package com.autocrypt.mon.policy.model;

import java.time.LocalDateTime;

public interface policyLog {

    int getId();
    String getName();
    String getVersion();
    String getStatus();
    String getFile_path();
    String getFile_name();
    String getUpdated_user();
    LocalDateTime getCreate_time();
    LocalDateTime getUpdate_time();
    String getMaxVersion();
}

