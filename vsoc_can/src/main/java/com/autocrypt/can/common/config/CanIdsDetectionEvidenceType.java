package com.autocrypt.can.common.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CanIdsDetectionEvidenceType {

    // Bus MPS
    BUS_LOAD_TOO_HIGH(1, "BUS_LOAD_TOO_HIGH", "high"),
    BUS_LOAD_TOO_LOW(2, "BUS_LOAD_TOO_LOW", "high"),
    UNKNOWN_ARB_ID(3, "UNKNOWN_ARB_ID", "middle"),

    // Signal Correlation
    SIGNAL_CORRELATION_ERROR(4, "SIGNAL_CORRELATION_ERROR", "middle"),

    // Ecu Message
    RECV_UNKNOWN_ECU_MESSAGE(5, "RECV_UNKNOWN_ECU_MESSAGE", "middle"),

    // Dos Attack
    DETECTED_DOS_ATTACK(6, "DETECTED_DOS_ATTACK", "high"),
    UNKNOWN_DIAG_ID(7, "UNKNOWN_DIAG_ID", "middle"),

    // Diag
    DIAG_MESSAGE_FLOODING(8, "DIAG_MESSAGE_FLOODING", "high"),
    DIAG_SCANNING_DETECTED(9, "DIAG_SCANNING_DETECTED", "middle"),
    SHORTER_DATA_LENGTH_ERROR(30, "SHORTER_DATA_LENGTH_ERROR", "middle"),
    LONGER_DATA_LENGTH_ERROR(31, "LONGER_DATA_LENGTH_ERROR", "middle"),
    ANOMALY_CYCLE(32, "ANOMALY_CYCLE", "middle"),
    INVALID_DIAG_MESSAGE(33, "INVALID_DIAG_MESSAGE", "middle"),

    // Signal CRC
    SIGNAL_SPACE_VIOLATION(60, "SIGNAL_SPACE_VIOLATION", "middle"),
    CRC_SIGNAL_ERROR(61, "CRC_SIGNAL_ERROR", "middle"),
    COUNTER_SIGNAL_SEQ_ERROR(62, "COUNTER_SIGNAL_SEQ_ERROR", "middle"),
    ENUM_SIGNAL_ERROR(63, "ENUM_SIGNAL_ERROR", "middle"),
    SIGNAL_UNDERFLOW(64, "SIGNAL_UNDERFLOW", "middle"),
    SIGNAL_OVERFLOW(65, "SIGNAL_OVERFLOW", "middle"),
    ANOMALY_CHANGE_RATE(66, "ANOMALY_CHANGE_RATE", "middle"),

    // etc
    GEAR_P_TIRE_ROTATION(100, "GEAR_P_TIRE_ROTATION", "middle"),
    EACH_WHEEL_ROTATION_ABNORMAL(101, "EACH_WHEEL_ROTATION_ABNORMAL", "middle"),
    VEHICLE_BATTERY_VOLTAGE(102, "VEHICLE_BATTERY_VOLTAGE", "middle"),
    HEATED_SEAT_VENTILATION_SEAT_ON(103, "HEATED_SEAT_VENTILATION_SEAT_ON", "middle"),
    GEAR_R_VEHICLE_DIRECTION_FRONT(104, "GEAR_R_VEHICLE_DIRECTION_FRONT", "middle"),
    REQUEST_TORQUE_OVERFLOW(105, "REQUEST_TORQUE_OVERFLOW", "middle"),
    ENGINE_TEMPERATURE_ABNORMAL(106, "ENGINE_TEMPERATURE_ABNORMAL", "middle"),
    VEHICLE_SPEED_WHEEL_SPEED_BIG_DIFFERENCE(107, "VEHICLE_SPEED_WHEEL_SPEED_BIG_DIFFERENCE", "middle"),
    ANOTHER_MESSAGE_GEAR_SIGNAL_DIFFERENCE(108, "ANOTHER_MESSAGE_GEAR_SIGNAL_DIFFERENCE", "middle"),
    GEARBOX_OIL_TEMPERATURE_ABNORMAL(109, "GEARBOX_OIL_TEMPERATURE_ABNORMAL", "middle"),
    IMMEDIATELY_GEAR_CHANGE_ABNORMAL(110, "IMMEDIATELY_GEAR_CHANGE_ABNORMAL", "middle");

    private int value;
    private String ruleName;
    private String severity;
}