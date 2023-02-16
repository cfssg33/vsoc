package com.autocrypt.can.common;

import com.autocrypt.can.ids.controller.dto.CanIdsLogDto;
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;


@NoArgsConstructor
public class CanIdsDetectionLogParser {

    public static final int DETECTION_LOG_SIZE = 104;

    // Setting CAN IDS Detection Log
    public static CanIdsLogDto.DetectionLogDto setDetectionLog(byte[] bin, String id) {

        CanIdsLogDto.DetectionLogDto canIdsDetectionLog = new CanIdsLogDto.DetectionLogDto();

        // MatchId
        canIdsDetectionLog.setMatchId(id);

        // LogType + DataLength
        canIdsDetectionLog.setLogType(bin[3] & 0x01);
        canIdsDetectionLog.setDataLength((bin[3] & 0xFF) >> 1);

        // CanBusNum
        canIdsDetectionLog.setByCanBusNum(bin[2] & 0xFF);

        // ViolationRuleId
        canIdsDetectionLog.setByViolationRuleId(bin[1] & 0xFF);

        // SignalStartBit
        canIdsDetectionLog.setSignalStartBit(((bin[7] & 0xFF) << 8) | (bin[0] & 0xFF));

        // SignalLength
        byte[] signalLength = new byte[2];
        ByteBuffer.wrap(bin, 5, bin.length - 5)
                .get(signalLength, 0, 2);

        canIdsDetectionLog.setSignalLength((ByteBuffer
                .wrap(signalLength)
                .order(ByteOrder.BIG_ENDIAN).getShort()) & 0xFFFF);

        // RawMessageLength
        canIdsDetectionLog.setByRawMessageLength(bin[4] & 0xFF);

        // CanId
        byte[] canId = new byte[4];
        ByteBuffer.wrap(bin, 8, bin.length - 8)
                .get(canId, 0, canId.length);

        canIdsDetectionLog.setCanId(ByteBuffer
                .wrap(canId)
                .order(ByteOrder.BIG_ENDIAN).getInt());

        // detectionTime
        byte[] detectionTime = new byte[4];
        ByteBuffer.wrap(bin, 12, bin.length - 12)
                .get(detectionTime, 0, detectionTime.length);

        long tempTime = ByteBuffer
                .wrap(detectionTime)
                .order(ByteOrder.BIG_ENDIAN).getInt();

        canIdsDetectionLog.setDetectionTime(new Date(tempTime * 1000));

        // DetectionReason_Hi
        byte[] detectionReasonHi = new byte[4];
        ByteBuffer.wrap(bin, 16, bin.length - 16)
                .get(detectionReasonHi, 0, detectionReasonHi.length);

        canIdsDetectionLog.setDetectionReasonHi(ByteBuffer
                .wrap(detectionReasonHi)
                .order(ByteOrder.BIG_ENDIAN).getInt());

        // DetectionReason_Lo
        byte[] detectionReasonLo = new byte[4];
        ByteBuffer.wrap(bin, 20, bin.length - 20)
                .get(detectionReasonLo, 0, detectionReasonLo.length);

        canIdsDetectionLog.setDetectionReasonLo(ByteBuffer
                .wrap(detectionReasonLo)
                .order(ByteOrder.BIG_ENDIAN).getInt());

        // Get RuleName & Severity
        CanIdsDetectionEvidence.CanIdsDetectionEvidenceReturnValue returnValue =
                CanIdsDetectionEvidence.setCanIdsDetectionEvidence(canIdsDetectionLog.getByViolationRuleId(),
                                                                   canIdsDetectionLog.getDetectionReasonHi(),
                                                                   canIdsDetectionLog.getDetectionReasonLo());

        // ViolationRuleName
        canIdsDetectionLog.setByViolationRuleName(returnValue.getRuleName());

        // Severity
        canIdsDetectionLog.setSeverity(returnValue.getSeverity());

        // DuplicationNumber
        byte[] duplicationNumber = new byte[2];
        ByteBuffer.wrap(bin, 26, bin.length - 26)
                .get(duplicationNumber, 0, duplicationNumber.length);

        canIdsDetectionLog.setDuplicationNumber(ByteBuffer
                                                .wrap(duplicationNumber)
                                                .order(ByteOrder.BIG_ENDIAN).getShort() & 0xFFFF);

        // Reserved
        String reserved = createReservedData(bin);
        canIdsDetectionLog.setReserved(reserved);

        // RawMessageBody
        String rawMessageBody = createRawMessageBodyData(bin, canIdsDetectionLog.getByRawMessageLength());
        canIdsDetectionLog.setByRawMessageBody(rawMessageBody);

        return canIdsDetectionLog;
    }

    private static String createReservedData(byte[] bin) {
        StringBuilder builder = new StringBuilder();

        // Reserve는 예약공간이라 작업 필요하지 않으므로 초기화 리턴
        builder.append(String.format("0000000000000000000000000000"));

        return builder.toString();
    }

    private static String createRawMessageBodyData(byte[] bin, int rawMessageLength) {
        StringBuilder builder = new StringBuilder();

        // rawMessageLength 만큼만 값이 있기 때문에 해당 값들만 넣어주고 리턴
        if (0 != rawMessageLength && 64 >= rawMessageLength) {
            for (int i = 0; i < rawMessageLength; i++) {
                // 40byte부터 rawMessageBody 시작
                builder.append(String.format("%02x", bin[40 + i] & 0xFF));
            }
        }

        return builder.toString();
    }

}
