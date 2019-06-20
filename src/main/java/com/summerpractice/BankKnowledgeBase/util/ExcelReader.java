package com.summerpractice.BankKnowledgeBase.util;

import org.apache.maven.shared.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ExcelReader {


    public static ArrayList<ArrayList<String>>  readExcel(MultipartFile file) throws Exception {
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        boolean notNull = false;//判断Excel文件是否有内容
        String filename = file.getOriginalFilename();

        if (!filename.matches("^.+\\.(?i)(xls)$") && !filename.matches("^.+\\.(?i)(xlsx)$")) {
            throw new Exception("上传文件格式不正确");
        }
        //判断Excel文件的版本
        boolean isExcel2003 = true;
        if (filename.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }

        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }

        //获取要导入的表的字段值，因为我的属性文件里表名前都加了cqwf2

        //获取Excel文件的第一页sheet，判断是否有信息
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            notNull = true;
        }

        //遍历Excel文件
        int totalRows = sheet.getPhysicalNumberOfRows();    //获取行数，第一行是标题
        Row row = null;
        int totalCells = 0;
        for (int i = 1; i < totalRows; i++) {
            //遍历单元格
            row = sheet.getRow(i);
            totalCells = row.getPhysicalNumberOfCells();        //获取每一行的单元格数
            //循环设置每个单元格的数据类型为String类型
            for (int j = 0; j < totalCells; j++) {
                if (row.getCell(j) != null) {
                    row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                }
            }


        }
    }
}