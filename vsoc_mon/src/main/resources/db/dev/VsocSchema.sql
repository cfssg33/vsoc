USE autocrypt_vsoc;

CREATE TABLE IF NOT EXISTS `host_idps_metadata` (
    `eid` int(11) NOT NULL,
    `ts` datetime NOT NULL,
    `dt` varchar(128) NOT NULL,
    `vn` varchar(32) NOT NULL,
    `dtn` varchar(32) NOT NULL,
    `vehicle_id` varchar (64) NOT NULL,
    `region` nvarchar(32) NOT NULL,
    `city` nvarchar(32) NOT NULL,
    `policy` varchar (32) NOT NULL,
    `host_log` varchar (32) NOT NULL,
    PRIMARY KEY (`host_log`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS  `host_idps_detection_log` (
    `uid` varchar (32) NOT NULL,
    `log_uuid` varchar (32) NOT NULL,
    `event_time` datetime NOT NULL,
    `protocol` varchar(64)  DEFAULT NULL,
    `rule_id` int(11) NOT NULL,
    `rule_name` varchar(64) NOT NULL,
    `src_ip` varchar(25) DEFAULT NULL ,
    `src_port` int(11) DEFAULT 0,
    `dst_ip` varchar(25) DEFAULT NULL ,
    `dst_port` int(11) DEFAULT 0,
    `type_id` int(11) NOT NULL,
    `label` varchar(64),
    `checked_log`  varchar(16),
    `severity` varchar (32),
    PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS  `host_idps_status_log` (
    `uid` varchar (32) NOT NULL,
    `log_uuid` varchar (32) NOT NULL,
    `event_time` datetime NOT NULL,
    `ids_policy_ver` varchar(8)  DEFAULT NULL,
    `fw_policy_ver` varchar(8) NOT NULL,
    `traffic_rx` int(11) NOT NULL,
    `traffic_tx` int(11) NOT NULL,
    `checked_log`  varchar(16),
    PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `host_idps_detection_log` DROP CONSTRAINT IF EXISTS `fk_host_idps_detection_log`;
ALTER TABLE `host_idps_detection_log` ADD CONSTRAINT `fk_host_idps_detection_log` FOREIGN KEY (`log_uuid`) REFERENCES `host_idps_metadata` (`host_log`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `host_idps_status_log` DROP CONSTRAINT IF EXISTS `fk_host_idps_status_log`;
ALTER TABLE `host_idps_status_log` ADD CONSTRAINT `fk_host_idps_status_log` FOREIGN KEY (`log_uuid`) REFERENCES `host_idps_metadata` (`host_log`) ON DELETE NO ACTION ON UPDATE NO ACTION;

CREATE TABLE IF NOT EXISTS `can_ids_meta_data` (
   `id`             varchar (32) NOT NULL,
   `create_time`    datetime NOT NULL,
   `vehicle_id`     varchar (64) NOT NULL,
   `region`         nvarchar(32) NOT NULL,
   `city`           nvarchar(32) NOT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `can_ids_detection_log` (
    `id`                        int NOT NULL AUTO_INCREMENT,
    `match_id`                  varchar(32) NOT NULL COMMENT 'can_ids_meta_data 와의 매칭을 위한 id',
    `log_type`                  int NOT NULL COMMENT '1bit',
    `data_length`               int NOT NULL COMMENT '7bit',
    `by_can_bus_num`            int NOT NULL COMMENT '1byte',
    `by_violation_rule_id`      int NOT NULL COMMENT '1byte',
    `by_violation_rule_name`    varchar(64)  NOT NULL,
    `signal_start_bit`          int NOT NULL COMMENT '2byte',
    `signal_length`             int NOT NULL COMMENT '2byte',
    `by_raw_message_length`     int NOT NULL COMMENT '1byte',
    `can_id`                    int NOT NULL COMMENT '4byte',
    `detection_time`            datetime NOT NULL,
    `detection_reason_hi`       int NOT NULL COMMENT '4byte',
    `detection_reason_lo`       int NOT NULL COMMENT '4byte',
    `duplication_number`        int NOT NULL COMMENT '2byte',
    `reserved`                  varchar(50)  NOT NULL COMMENT '14byte',
    `by_raw_message_body`       varchar(250) NOT NULL COMMENT '64byte',
    `label`                     varchar(64),
    `checked_log`               varchar(16),
    `severity` varchar (32),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `can_ids_status_info_log` (
    `id`                varchar(32) NOT NULL COMMENT 'can_ids_meta_data 와의 매칭을 위한 id',
    `log_type`          int NOT NULL COMMENT '1bit',
    `data_length`       int NOT NULL COMMENT '7bit',
    `sequence_number`   int NOT NULL COMMENT '2byte',
    `memory_usage`      int NOT NULL COMMENT '4byte',
    `flash_usage`       int NOT NULL COMMENT '4byte',
    `policy_version`    varchar(30) NOT NULL COMMENT '6byte',
    `firmware_version`  varchar(30) NOT NULL COMMENT '6byte',
    `reserved`          varchar(50) NOT NULL COMMENT '25byte',
    `checked_log`  varchar(16),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `can_ids_detection_log` DROP CONSTRAINT IF EXISTS `fk_can_ids_detection_log`;
ALTER TABLE `can_ids_detection_log` ADD CONSTRAINT `fk_can_ids_detection_log` FOREIGN KEY (`match_id`) REFERENCES `can_ids_meta_data` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `can_ids_status_info_log` DROP CONSTRAINT IF EXISTS `fk_can_ids_status_info_log`;
ALTER TABLE `can_ids_status_info_log` ADD CONSTRAINT `fk_can_ids_status_info_log` FOREIGN KEY (`id`) REFERENCES `can_ids_meta_data` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;


CREATE TABLE IF NOT EXISTS `vsoc_api_log` (
    `id`                int             NOT NULL AUTO_INCREMENT,
    `created_time`      datetime        NOT NULL,
    `url`               varchar(500)    NOT NULL COMMENT '요청 URL',
    `method`            varchar(20)     NOT NULL COMMENT '요청 메소드',
    `level`             varchar(10)     NOT NULL COMMENT '로그레벨',
    `source_ip`         varchar(128)    NOT NULL COMMENT '요청 IP',
    `request`           varchar(5000)   NOT NULL COMMENT '요청 값',
    `response`          varchar(10000)  NOT NULL COMMENT '응답 값',
    `processing_time`   varchar(50)     COMMENT 'api 처리시간',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `vsoc_account` (
    `account_id`        varchar(30)     NOT NULL,
    `name`              varchar(30)     NOT NULL,
    `password`          varchar(200)    NOT NULL,
    `role`              varchar(20)     NOT NULL,
    `created_date`      datetime        NOT NULL,
    `last_access_time`  datetime,
    `log_check_time`    datetime,
    PRIMARY  KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `kafka_topic` (
    `type`    varchar(16) NOT NULL,
    `topic`   varchar(32) NOT NULL,
    `ip_addr` varchar(15) NOT NULL,
    `port`    int(11)     NOT NULL,
    PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `vsoc_account` (`account_id`, `name`, `password`, `role`, `created_date`, `last_access_time`, `log_check_time`)
SELECT 'admin', 'admin', '$2a$10$fwGTi3NlaPOfLUrfx.49OeWoi3ZZVPmXNbbaF36yKLSWBZc9O//mG', 'ROLE_ADMIN', now(), NULL, NULL
FROM dual WHERE NOT EXISTS (SELECT * FROM vsoc_account WHERE account_id = 'admin');

INSERT INTO `kafka_topic` (`ip_addr`, `port`, `type`, `topic`)
SELECT '20.194.61.132', 9092, 'CAN_Log', 'idps-can-transmit-topic'
FROM dual WHERE NOT EXISTS (SELECT * FROM kafka_topic WHERE topic='idps-can-transmit-topic');

INSERT INTO `kafka_topic` (`ip_addr`, `port`, `type`, `topic`)
SELECT '20.194.61.132',9092,'HOST_Log','idps-hu-transmit-topic'
FROM dual WHERE NOT EXISTS (SELECT * FROM kafka_topic WHERE topic='idps-hu-transmit-topic');

INSERT INTO `kafka_topic` (`ip_addr`, `port`, `type`, `topic`)
SELECT '20.194.61.132',9092,'CAN_Log_Test','CAN_Log_Test'
FROM dual WHERE NOT EXISTS (SELECT * FROM kafka_topic WHERE topic='CAN_Log_Test');

INSERT INTO `kafka_topic` (`ip_addr`, `port`, `type`, `topic`)
SELECT '20.194.61.132',9092,'HOST_Log_Test','HOST_Log_Test'
FROM dual WHERE NOT EXISTS (SELECT * FROM kafka_topic WHERE topic='HOST_Log_Test');

CREATE TABLE IF NOT EXISTS `vehicle_info_from_chang_an` (
    `tuid`                      varchar(256) NOT NULL,
    `brand_code`                varchar(64)  NOT NULL,
    `brand_id`                  varchar(64)  DEFAULT NULL,
    `brand_name`                varchar(64)  DEFAULT NULL,
    `car_id`                    varchar(64)  DEFAULT NULL,
    `car_name`                  varchar(64)  DEFAULT NULL,
    `car_type`                  varchar(64)  DEFAULT NULL,
    `company_id`                varchar(64)  DEFAULT NULL,
    `conf_code`                 varchar(64)  DEFAULT NULL,
    `conf_id`                   varchar(64)  DEFAULT NULL,
    `conf_name`                 varchar(64)  DEFAULT NULL,
    `dealer_id`                 varchar(64)  DEFAULT NULL,
    `deleted`                   int          DEFAULT NULL,
    `engine_no`                 varchar(64)  DEFAULT NULL,
    `img`                       varchar(64)  DEFAULT NULL,
    `insurance_company`         varchar(64)  DEFAULT NULL,
    `insurance_phone`           varchar(64)  DEFAULT NULL,
    `is_ca_car`                 varchar(64)  DEFAULT NULL,
    `is_nev`                    int          DEFAULT NULL,
    `is_privacy_drving_history` tinyint(1)   DEFAULT NULL,
    `last_maintain_mileage`     int          DEFAULT NULL,
    `maintain_space_mileage`    int          DEFAULT NULL,
    `material_id`               varchar(64)  DEFAULT NULL,
    `model_code`                varchar(64)  NOT NULL,
    `model_id`                  varchar(64)  DEFAULT NULL,
    `model_name`                varchar(64)  DEFAULT NULL,
    `oem_company_id`            varchar(64)  DEFAULT NULL,
    `oil_type`                  varchar(64)  DEFAULT NULL,
    `pin`                       varchar(64)  DEFAULT NULL,
    `plate_number`              varchar(64)  DEFAULT NULL,
    `product_time`              datetime     DEFAULT NULL,
    `realname_auth_idcard`      varchar(64)  DEFAULT NULL,
    `realname_auth_idcard_type` varchar(64)  DEFAULT NULL,
    `realname_auth_mobile`      varchar(64)  DEFAULT NULL,
    `realname_auth_pass_time`   datetime     DEFAULT NULL,
    `realname_auth_status`      varchar(64)  DEFAULT NULL,
    `realname_type`             int          DEFAULT NULL,
    `register_time`             varchar(64)  NOT NULL,
    `series_code`               varchar(64)  NOT NULL,
    `series_id`                 varchar(64)  DEFAULT NULL,
    `series_name`               varchar(64)  DEFAULT NULL,
    `source`                    varchar(64)  DEFAULT NULL,
    `system_code`               varchar(64)  DEFAULT NULL,
    `tenant_id`                 varchar(64)  NOT NULL,
    `total_odometer`            int          DEFAULT NULL,
    `tservice_endtime`          datetime     DEFAULT NULL,
    `tservice_starttime`        datetime     DEFAULT NULL,
    `tservice_status`           varchar(64)  DEFAULT NULL,
    `user_id`                   varchar(64)  DEFAULT NULL,
    `vin`                       varchar(64)  DEFAULT NULL,
    PRIMARY KEY (`tuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `host_idps_metadata` DROP CONSTRAINT IF EXISTS `fk_host_idps_metadata`;
ALTER TABLE `host_idps_metadata` ADD CONSTRAINT `fk_host_idps_metadata` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle_info_from_chang_an` (`tuid`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `vehicle_info_from_chang_an` ADD COLUMN IF NOT EXISTS `seriousness` varchar(32) DEFAULT 'NORMAL' NOT NULL;
ALTER TABLE `vehicle_info_from_chang_an` ADD COLUMN IF NOT EXISTS `region` nvarchar(32) NOT NULL;
ALTER TABLE `vehicle_info_from_chang_an` ADD COLUMN IF NOT EXISTS `city` nvarchar(32) NOT NULL;

ALTER TABLE `vehicle_info_from_chang_an` ADD COLUMN IF NOT EXISTS `created_at` datetime NOT NULL;

CREATE TABLE IF NOT EXISTS `vsoc_can_policy` (
    `id`            int NOT NULL AUTO_INCREMENT,
    `name`          varchar(64) NOT NULL,
    `version`       varchar(16) NOT NULL,
    `status`        varchar(32) NOT NULL,
    `file_path`     varchar(256) NOT NULL,
    `file_name`     varchar(32) NOT NULL,
    `updated_user`     varchar(32) DEFAULT NULL,
    `create_time`   datetime    DEFAULT NULL,
    `update_time`   datetime    DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `vsoc_host_policy` (
    `id`            int NOT NULL AUTO_INCREMENT,
    `name`          varchar(64) NOT NULL,
    `version`       varchar(16) NOT NULL,
    `status`        varchar(32) NOT NULL,
    `file_path`     varchar(256) NOT NULL,
    `file_name`     varchar(32) NOT NULL,
    `updated_user`     varchar(32) DEFAULT NULL,
    `create_time`   datetime    DEFAULT NULL,
    `update_time`   datetime    DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `host_idps_detection_log` ADD COLUMN IF NOT EXISTS `rule_type` varchar(64) NOT NULL;
