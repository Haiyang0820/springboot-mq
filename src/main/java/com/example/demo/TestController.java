package com.example.demo;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by think on 2017/8/15.
 */
@Controller
public class TestController {

    void test(){
        InputStream inputStream=this.getClass().getResourceAsStream("/test.xlsx");
        try {
            int avaliable = 0;
            if(inputStream!=null){
                avaliable = inputStream.available();
            }
            System.out.println("avaliable = "+avaliable);
            readXlsx(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取Xlsx
    public static Map<Integer, List<String[]>> readXlsx(InputStream fileInputStream) {
        Map<Integer, List<String[]>> map = new HashMap<Integer, List<String[]>>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
                XSSFSheet xssfSheet = workbook.getSheetAt(numSheet);
                if (xssfSheet == null) {
                    continue;
                }
                List<String[]> list = new ArrayList<String[]>();

                for (int row=2;row<=xssfSheet.getLastRowNum();row++){
                    XSSFRow xssfRow = xssfSheet.getRow(row);
                    if (xssfRow == null) {
                        continue;
                    }
                    String[] singleRow = new String[xssfRow.getLastCellNum()];
                    for(int column=0;column<xssfRow.getLastCellNum();column++){
                        Cell cell = xssfRow.getCell(column, Row.CREATE_NULL_AS_BLANK);
                        switch(cell.getCellType()){
                            case Cell.CELL_TYPE_BLANK:
                                singleRow[column] = "";
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                singleRow[column] = Boolean.toString(cell.getBooleanCellValue());
                                break;
                            case Cell.CELL_TYPE_ERROR:
                                singleRow[column] = "";
                                break;
                            case Cell.CELL_TYPE_FORMULA:
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                singleRow[column] = cell.getStringCellValue();
                                if (singleRow[column] != null) {
                                    singleRow[column] = singleRow[column].replaceAll("#N/A", "").trim();
                                }
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    singleRow[column] = String.valueOf(simpleDateFormat.format(cell.getDateCellValue()));
                                } else {
                                    cell.setCellType(Cell.CELL_TYPE_STRING);
                                    String temp = cell.getStringCellValue();
                                    // 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
                                    if (temp.indexOf(".") > -1) {
                                        singleRow[column] = String.valueOf(new Double(temp)).trim();
                                    } else {
                                        singleRow[column] = temp.trim();
                                    }
                                }

                                break;
                            case Cell.CELL_TYPE_STRING:
                                singleRow[column] = cell.getStringCellValue().trim();
                                break;
                            default:
                                singleRow[column] = "";
                                break;
                        }
                    }
                    list.add(singleRow);
                }
                map.put(numSheet, list);
            }
        } catch (FileNotFoundException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value="/test",method= RequestMethod.GET)
    public String testpath(){
        test();
        return "abc";
    }


}
