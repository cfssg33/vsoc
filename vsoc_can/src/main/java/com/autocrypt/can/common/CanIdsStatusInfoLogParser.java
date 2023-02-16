package com.autocrypt.can.common;

import com.autocrypt.can.ids.controller.dto.CanIdsLogDto;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class CanIdsStatusInfoLogParser {

    public static final int STATUSINFO_LOG_SIZE = 48;

    // Setting CAN IDS Status Info Log.
    public static CanIdsLogDto.StatusInfoLogDto setStatusInfoLog(byte[] binData, String id) {
        CanIdsLogDto.StatusInfoLogDto canIdsStatusInfoLog = new CanIdsLogDto.StatusInfoLogDto();

        // MatchId
        canIdsStatusInfoLog.setId(id);

        // LogType + DataLength
        canIdsStatusInfoLog.setLogType(binData[3] & 0x01);
        canIdsStatusInfoLog.setDataLength((binData[3] & 0xFF) >> 1);

        // SequenceNumber
        canIdsStatusInfoLog.setSequenceNumber(((binData[1] & 0xFF) << 8) | (binData[2] & 0xFF));

        // MemoryUsage
        canIdsStatusInfoLog.setMemoryUsage(((binData[7] & 0xFF) << 32)  |
                                           ((binData[6] & 0xFF) << 16)  |
                                           ((binData[5] & 0xFF) << 8)   |
                                           (binData[0] & 0xFF));

        // FlashUsage
        canIdsStatusInfoLog.setFlashUsage(((binData[11] & 0xFF) << 8) | (binData[4] & 0xFF));

        // PolicyVersion
        StringBuilder policyVersion = new StringBuilder("");

        policyVersion.append(((char)Integer.parseInt(String.valueOf(binData[8] & 0xFF), 10)));
        policyVersion.append(((char)Integer.parseInt(String.valueOf(binData[15] & 0xFF), 10)));
        policyVersion.append(".");

        policyVersion.append(((char)Integer.parseInt(String.valueOf(binData[14] & 0xFF), 10)));
        policyVersion.append(((char)Integer.parseInt(String.valueOf(binData[13] & 0xFF), 10)));
        policyVersion.append(".");

        policyVersion.append(((char)Integer.parseInt(String.valueOf(binData[12] & 0xFF), 10)));
        policyVersion.append(((char)Integer.parseInt(String.valueOf(binData[19] & 0xFF), 10)));

        canIdsStatusInfoLog.setByPolicyVersion(policyVersion.toString());

        // FirmwareVersion
        StringBuilder firmwareVersion = new StringBuilder("");

        firmwareVersion.append(((char)Integer.parseInt(String.valueOf(binData[18] & 0xFF), 10)));
        firmwareVersion.append(((char)Integer.parseInt(String.valueOf(binData[17] & 0xFF), 10)));
        firmwareVersion.append(".");

        firmwareVersion.append(((char)Integer.parseInt(String.valueOf(binData[16] & 0xFF), 10)));
        firmwareVersion.append(((char)Integer.parseInt(String.valueOf(binData[23] & 0xFF), 10)));
        firmwareVersion.append(".");

        firmwareVersion.append(((char)Integer.parseInt(String.valueOf(binData[22] & 0xFF), 10)));
        firmwareVersion.append(((char)Integer.parseInt(String.valueOf(binData[21] & 0xFF), 10)));

        canIdsStatusInfoLog.setByFirmwareVersion(firmwareVersion.toString());

        // Reserved
        String reserved = createReservedData(binData);
        canIdsStatusInfoLog.setReserved(reserved);

        return canIdsStatusInfoLog;
    }

    private static String createReservedData(byte[] bin) {
        StringBuilder builder = new StringBuilder();

        // Reserve
        builder.append(String.format("00000000000000000000000000000000000000000000000000"));

        return builder.toString();
    }

}
