package com.autocrypt.mon.common;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExcelService {

  public byte[] makeExcel(String sheetName, ArrayList<Map> dataList) throws Exception {
    XSSFWorkbook wb = new XSSFWorkbook();
    ByteArrayOutputStream bsOut = new ByteArrayOutputStream();

    Object[] headerObjects = dataList.get(0).keySet().toArray();
    ArrayList<String> header = new ArrayList<>();
    for (Object obj: headerObjects) {
      header.add(obj.toString());
    }

    try {
      XSSFSheet sheet1 = null;
      sheet1 = wb.createSheet(sheetName);
      int idx = 0;

      XSSFRow headRow = sheet1.createRow(idx++);
      int headerIdx = 0;
      for (String column : header) {
        headRow.createCell(headerIdx++).setCellValue(column);
      }

      for (Map data: dataList) {
        AtomicInteger dataIdx = new AtomicInteger();
        XSSFRow dataRow = sheet1.createRow(idx++);
        data.forEach((key, value) -> {
          if (value == null) {
            dataRow.createCell(dataIdx.getAndIncrement()).setCellValue("");
          } else {
            dataRow.createCell(dataIdx.getAndIncrement()).setCellValue(value.toString());
          }
        });
      }
      for (int i = 0; i < idx; i++) {
        sheet1.autoSizeColumn(i);
      }
    } catch (Exception e) {
      throw new Exception(e);
    }

    wb.write(bsOut);
    return bsOut.toByteArray();
  }

}
