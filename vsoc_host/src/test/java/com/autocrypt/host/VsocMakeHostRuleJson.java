package com.autocrypt.host;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

//HostIdpsRule.json file was created by reading the Host_IDPS_Ruleset_List.xlsx file. Create a .json file to parse the rules of the host idps log.
public class VsocMakeHostRuleJson {

    static Map<String, Object> hostIdspRule = new HashMap<>();

    static Map<String, String> firewallRules = new HashMap<>();
    static Map<String, String> idsRules = new HashMap<>();

    private static final String HOST_IDPS_RULE_EXCEL_FILE_PATH = "files/HOST_IDPS_Ruleset_List.xlsx";
    private static final String HOST_IDPS_RULE_JSON_FILE_PATH = "vsoc_mon/src/main/resources/json/rule/HostIdpsRule.json";

    private static void mappingData (String key, String value) {
        if (key.equals("Firewall Ruleset") == true) {
            hostIdspRule.put("firewall_rule", firewallRules);
        } else if (key.equals("IDS Ruleset") == true) {
            hostIdspRule.put("idps_rule", idsRules);
        } else if (Integer.parseInt(key) < 1000001) {
            firewallRules.put(key, value);
        } else if (Integer.parseInt(key) >= 1000001) {
            idsRules.put(key, value);
        }
    }

    private static void makeHostRuleJsonFile () {
        try {
            ClassPathResource classPathResource = new ClassPathResource(HOST_IDPS_RULE_EXCEL_FILE_PATH);
            if(classPathResource.exists() == false){
                System.out.println("Invalid filePath : {}" + HOST_IDPS_RULE_EXCEL_FILE_PATH);
                throw new IllegalArgumentException();
            }

            XSSFWorkbook book = new XSSFWorkbook(classPathResource.getFile());
            XSSFSheet sheet = book.getSheet("Rule ID Mapping table");


            int rows = sheet.getPhysicalNumberOfRows();
            for(int i=0 ; i <= rows ; i++){
                XSSFRow row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                int cells = row.getPhysicalNumberOfCells();

                String key = "";
                String value = "";

                for(int j=0 ; j < cells ; j++) {
                    XSSFCell cell = row.getCell(j);

                    switch (cell.getCellType()) {
                        case XSSFCell.CELL_TYPE_NUMERIC:
                            key = ((int) cell.getNumericCellValue()) + "";
                            break;
                        case XSSFCell.CELL_TYPE_STRING:
                            String data = cell.getStringCellValue()+"";
                            if (j == 0) {
                                if (data.equals("rule id") == true || data.equals("rule sid") == true){
                                    break;
                                }
                                key = data;
                            } else if (j >= 1 && j <= 2){
                                value += data + "|";
                            }
                            break;
                    }
                }

                if (value.length() > 0) {
                    value = value.substring(0, value.length()-1);
                }

                if (key.isEmpty() == false) {
                    System.out.println("key : " + key + ", value : " + value);
                    mappingData(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to read json file : " + e);
        }
    }

    public static void main (String[] args) throws IOException {
        makeHostRuleJsonFile();

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hostIdspRule);
        System.out.println(json);

        try {
            File file = new File(HOST_IDPS_RULE_JSON_FILE_PATH);
            System.out.println(file.getAbsolutePath());
            if (file.exists() == false) {
                file.createNewFile();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            bw.write(json);
            bw.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
