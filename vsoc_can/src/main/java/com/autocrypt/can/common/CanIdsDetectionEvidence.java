package com.autocrypt.can.common;

import com.autocrypt.can.common.config.CanIdsDetectionEvidenceType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;


@NoArgsConstructor
public class CanIdsDetectionEvidence {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CanIdsDetectionEvidenceReturnValue {
        public String ruleName = "UNDEFINED";
        public String severity = "UNDEFINED";
    }

    public static CanIdsDetectionEvidenceReturnValue setCanIdsDetectionEvidence(int violationId, int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result;

        if (violationId == CanIdsDetectionEvidenceType.BUS_LOAD_TOO_HIGH.getValue() ||
            violationId == CanIdsDetectionEvidenceType.BUS_LOAD_TOO_LOW.getValue()) {
            result = setBusLoadEvidence(reasonHigh, reasonLow);

        } else if (violationId == CanIdsDetectionEvidenceType.UNKNOWN_ARB_ID.getValue()) {
            result = setUnknownArbIdEvidence(reasonHigh, reasonLow);

        } else if (violationId == CanIdsDetectionEvidenceType.SIGNAL_CORRELATION_ERROR.getValue()) {
            result = setSignalCorrelationErrorEvidence();

        } else if (violationId == CanIdsDetectionEvidenceType.RECV_UNKNOWN_ECU_MESSAGE.getValue()) {
            result = setRecvUnknownEcuMsgEvidence();

        } else if (violationId == CanIdsDetectionEvidenceType.DETECTED_DOS_ATTACK.getValue()) {
            result = setDetectDosAttackEvidence(reasonHigh, reasonLow);

        } else if (violationId == CanIdsDetectionEvidenceType.UNKNOWN_DIAG_ID.getValue()) {
            result = setUnknownDiagIdEvidence(reasonHigh, reasonLow);

        } else if (violationId == CanIdsDetectionEvidenceType.DIAG_MESSAGE_FLOODING.getValue()) {
            result = setDiagMsgFloodingEvidence(reasonHigh, reasonLow);

        } else if (violationId == CanIdsDetectionEvidenceType.DIAG_SCANNING_DETECTED.getValue()) {
            result = setDiagScanningDetectedEvidence(reasonHigh, reasonLow);

        } else if (violationId == CanIdsDetectionEvidenceType.SHORTER_DATA_LENGTH_ERROR.getValue()) {
            result = setShortLengthDataErrorEvidence(reasonHigh, reasonLow);

        } else if (violationId == CanIdsDetectionEvidenceType.LONGER_DATA_LENGTH_ERROR.getValue()) {
            result = setLongLengthDataErrorEvidence(reasonHigh, reasonLow);

        } else if (violationId == CanIdsDetectionEvidenceType.ANOMALY_CYCLE.getValue()) {
            result = setAnomalyCycleEvidence(reasonHigh, reasonLow);

        } else if (violationId == CanIdsDetectionEvidenceType.INVALID_DIAG_MESSAGE.getValue()) {
            result = setInvalidDiagMsgEvidence(reasonHigh, reasonLow);

        } else if (violationId == CanIdsDetectionEvidenceType.SIGNAL_SPACE_VIOLATION.getValue()) {
            result = setSignalSpaceViolationEvidence(reasonHigh, reasonLow);

        } else if (violationId == CanIdsDetectionEvidenceType.CRC_SIGNAL_ERROR.getValue()) {
            result = setCrcSignalErrorEvidence(reasonHigh, reasonLow);

        } else if (violationId == CanIdsDetectionEvidenceType.COUNTER_SIGNAL_SEQ_ERROR.getValue()) {
            result = setCounterSignalSeqErrorEvidence(reasonHigh, reasonLow);

        } else if (violationId == CanIdsDetectionEvidenceType.ENUM_SIGNAL_ERROR.getValue()) {
            result = setEnumSignalErrorEvidence(reasonHigh, reasonLow);

        } else if (violationId == CanIdsDetectionEvidenceType.SIGNAL_UNDERFLOW.getValue()) {
            result = setSignalUnderflowEvidence(reasonHigh, reasonLow);

        } else if (violationId == CanIdsDetectionEvidenceType.SIGNAL_OVERFLOW.getValue()) {
            result = setSignalOverflowEvidence(reasonHigh, reasonLow);

        } else if (violationId == CanIdsDetectionEvidenceType.ANOMALY_CHANGE_RATE.getValue()) {
            result = setAnomalyChangeRateEvidence(reasonHigh, reasonLow);

        } else {
            // etc
            result = setEtcDetectionEvidence(violationId);

        }

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setBusLoadEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        byte[] bytes = ByteBuffer.allocate(4).putInt(reasonHigh).array();

        int highValue = ((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF);
        int lowValue = ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);

        if (reasonLow < lowValue) {
            result.setRuleName(CanIdsDetectionEvidenceType.BUS_LOAD_TOO_LOW.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.BUS_LOAD_TOO_LOW.getSeverity());
        } else if (reasonLow > highValue) {
            result.setRuleName(CanIdsDetectionEvidenceType.BUS_LOAD_TOO_HIGH.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.BUS_LOAD_TOO_HIGH.getSeverity());
        }

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setUnknownArbIdEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        if (reasonHigh != reasonLow) {
            result.setRuleName(CanIdsDetectionEvidenceType.UNKNOWN_ARB_ID.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.UNKNOWN_ARB_ID.getSeverity());
        }

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setSignalCorrelationErrorEvidence() {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        result.setRuleName(CanIdsDetectionEvidenceType.SIGNAL_CORRELATION_ERROR.getRuleName());
        result.setSeverity(CanIdsDetectionEvidenceType.SIGNAL_CORRELATION_ERROR.getSeverity());

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setRecvUnknownEcuMsgEvidence() {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        result.setRuleName(CanIdsDetectionEvidenceType.RECV_UNKNOWN_ECU_MESSAGE.getRuleName());
        result.setSeverity(CanIdsDetectionEvidenceType.RECV_UNKNOWN_ECU_MESSAGE.getSeverity());

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setDetectDosAttackEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        if (reasonHigh != reasonLow) {
            result.setRuleName(CanIdsDetectionEvidenceType.DETECTED_DOS_ATTACK.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.DETECTED_DOS_ATTACK.getSeverity());
        }

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setUnknownDiagIdEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        if (reasonHigh != reasonLow) {
            result.setRuleName(CanIdsDetectionEvidenceType.UNKNOWN_DIAG_ID.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.UNKNOWN_DIAG_ID.getSeverity());
        }

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setDiagMsgFloodingEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        if (reasonHigh <= reasonLow) {
            result.setRuleName(CanIdsDetectionEvidenceType.DIAG_MESSAGE_FLOODING.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.DIAG_MESSAGE_FLOODING.getSeverity());
        }

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setDiagScanningDetectedEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        if (reasonHigh == reasonLow) {
            result.setRuleName(CanIdsDetectionEvidenceType.DIAG_SCANNING_DETECTED.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.DIAG_SCANNING_DETECTED.getSeverity());
        }

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setShortLengthDataErrorEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        if (reasonHigh >= reasonLow) {
            result.setRuleName(CanIdsDetectionEvidenceType.SHORTER_DATA_LENGTH_ERROR.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.SHORTER_DATA_LENGTH_ERROR.getSeverity());
        }

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setLongLengthDataErrorEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        if (reasonHigh <= reasonLow) {
            result.setRuleName(CanIdsDetectionEvidenceType.LONGER_DATA_LENGTH_ERROR.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.LONGER_DATA_LENGTH_ERROR.getSeverity());
        }

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setAnomalyCycleEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        byte[] bytes = ByteBuffer.allocate(4).putInt(reasonHigh).array();

        int highValue = ((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF);
        int lowValue = ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);

        if (reasonLow < lowValue || reasonLow > highValue) {
            result.setRuleName(CanIdsDetectionEvidenceType.ANOMALY_CYCLE.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.ANOMALY_CYCLE.getSeverity());
        }

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setInvalidDiagMsgEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        result.setRuleName(CanIdsDetectionEvidenceType.INVALID_DIAG_MESSAGE.getRuleName());
        result.setSeverity(CanIdsDetectionEvidenceType.INVALID_DIAG_MESSAGE.getSeverity());

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setSignalSpaceViolationEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        result.setRuleName(CanIdsDetectionEvidenceType.SIGNAL_SPACE_VIOLATION.getRuleName());
        result.setSeverity(CanIdsDetectionEvidenceType.SIGNAL_SPACE_VIOLATION.getSeverity());

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setCrcSignalErrorEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        if (reasonHigh != reasonLow) {
            result.setRuleName(CanIdsDetectionEvidenceType.CRC_SIGNAL_ERROR.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.CRC_SIGNAL_ERROR.getSeverity());
        }

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setCounterSignalSeqErrorEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        result.setRuleName(CanIdsDetectionEvidenceType.COUNTER_SIGNAL_SEQ_ERROR.getRuleName());
        result.setSeverity(CanIdsDetectionEvidenceType.COUNTER_SIGNAL_SEQ_ERROR.getSeverity());

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setEnumSignalErrorEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        result.setRuleName(CanIdsDetectionEvidenceType.ENUM_SIGNAL_ERROR.getRuleName());
        result.setSeverity(CanIdsDetectionEvidenceType.ENUM_SIGNAL_ERROR.getSeverity());

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setSignalUnderflowEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        if (reasonHigh >= reasonLow) {
            result.setRuleName(CanIdsDetectionEvidenceType.SIGNAL_UNDERFLOW.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.SIGNAL_UNDERFLOW.getSeverity());
        }

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setSignalOverflowEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        if (reasonHigh <= reasonLow) {
            result.setRuleName(CanIdsDetectionEvidenceType.SIGNAL_OVERFLOW.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.SIGNAL_OVERFLOW.getSeverity());
        }

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setAnomalyChangeRateEvidence(int reasonHigh, int reasonLow) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        result.setRuleName(CanIdsDetectionEvidenceType.ANOMALY_CHANGE_RATE.getRuleName());
        result.setSeverity(CanIdsDetectionEvidenceType.ANOMALY_CHANGE_RATE.getSeverity());

        return result;
    }

    private static CanIdsDetectionEvidenceReturnValue setEtcDetectionEvidence(int violationId) {
        CanIdsDetectionEvidenceReturnValue result = new CanIdsDetectionEvidenceReturnValue();

        if (violationId == CanIdsDetectionEvidenceType.GEAR_P_TIRE_ROTATION.getValue()) {
            result.setRuleName(CanIdsDetectionEvidenceType.GEAR_P_TIRE_ROTATION.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.GEAR_P_TIRE_ROTATION.getSeverity());

        } else if(violationId == CanIdsDetectionEvidenceType.EACH_WHEEL_ROTATION_ABNORMAL.getValue()) {
            result.setRuleName(CanIdsDetectionEvidenceType.EACH_WHEEL_ROTATION_ABNORMAL.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.EACH_WHEEL_ROTATION_ABNORMAL.getSeverity());

        } else if(violationId == CanIdsDetectionEvidenceType.VEHICLE_BATTERY_VOLTAGE.getValue()) {
            result.setRuleName(CanIdsDetectionEvidenceType.VEHICLE_BATTERY_VOLTAGE.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.VEHICLE_BATTERY_VOLTAGE.getSeverity());

        } else if(violationId == CanIdsDetectionEvidenceType.HEATED_SEAT_VENTILATION_SEAT_ON.getValue()) {
            result.setRuleName(CanIdsDetectionEvidenceType.HEATED_SEAT_VENTILATION_SEAT_ON.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.HEATED_SEAT_VENTILATION_SEAT_ON.getSeverity());

        } else if(violationId == CanIdsDetectionEvidenceType.GEAR_R_VEHICLE_DIRECTION_FRONT.getValue()) {
            result.setRuleName(CanIdsDetectionEvidenceType.GEAR_R_VEHICLE_DIRECTION_FRONT.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.GEAR_R_VEHICLE_DIRECTION_FRONT.getSeverity());

        } else if(violationId == CanIdsDetectionEvidenceType.REQUEST_TORQUE_OVERFLOW.getValue()) {
            result.setRuleName(CanIdsDetectionEvidenceType.REQUEST_TORQUE_OVERFLOW.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.REQUEST_TORQUE_OVERFLOW.getSeverity());

        } else if(violationId == CanIdsDetectionEvidenceType.ENGINE_TEMPERATURE_ABNORMAL.getValue()) {
            result.setRuleName(CanIdsDetectionEvidenceType.ENGINE_TEMPERATURE_ABNORMAL.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.ENGINE_TEMPERATURE_ABNORMAL.getSeverity());

        } else if(violationId == CanIdsDetectionEvidenceType.VEHICLE_SPEED_WHEEL_SPEED_BIG_DIFFERENCE.getValue()) {
            result.setRuleName(CanIdsDetectionEvidenceType.VEHICLE_SPEED_WHEEL_SPEED_BIG_DIFFERENCE.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.VEHICLE_SPEED_WHEEL_SPEED_BIG_DIFFERENCE.getSeverity());

        } else if(violationId == CanIdsDetectionEvidenceType.ANOTHER_MESSAGE_GEAR_SIGNAL_DIFFERENCE.getValue()) {
            result.setRuleName(CanIdsDetectionEvidenceType.ANOTHER_MESSAGE_GEAR_SIGNAL_DIFFERENCE.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.ANOTHER_MESSAGE_GEAR_SIGNAL_DIFFERENCE.getSeverity());

        } else if(violationId == CanIdsDetectionEvidenceType.GEARBOX_OIL_TEMPERATURE_ABNORMAL.getValue()) {
            result.setRuleName(CanIdsDetectionEvidenceType.GEARBOX_OIL_TEMPERATURE_ABNORMAL.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.GEARBOX_OIL_TEMPERATURE_ABNORMAL.getSeverity());

        } else if(violationId == CanIdsDetectionEvidenceType.IMMEDIATELY_GEAR_CHANGE_ABNORMAL.getValue()) {
            result.setRuleName(CanIdsDetectionEvidenceType.IMMEDIATELY_GEAR_CHANGE_ABNORMAL.getRuleName());
            result.setSeverity(CanIdsDetectionEvidenceType.IMMEDIATELY_GEAR_CHANGE_ABNORMAL.getSeverity());

        }

        return result;
    }

}
