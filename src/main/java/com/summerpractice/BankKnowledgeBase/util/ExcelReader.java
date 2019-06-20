package com.summerpractice.BankKnowledgeBase.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/***
 * author 黄平
 * 用于导入excel的工具类
 * 输入为multipart file
 */
public class ExcelReader {

    /***
     *
     * @param file
     * @return 数组标签
     * @throws Exception
     */
    public static ArrayList<ArrayList<String>>  readExcel(MultipartFile file) throws Exception {
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        try {
            if(file==null)
                throw new Exception("上传文件不能为空!") ;
            String fileName = file.getOriginalFilename();
            if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
               throw new Exception("文件后缀不对！");
            }

            boolean isExcel2003 = true;
            if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
                isExcel2003 = false;
            }
            InputStream is = file.getInputStream();
            Workbook wb = null;
            if (isExcel2003) {
                wb = new HSSFWorkbook(is);
            } else {
                wb = new XSSFWorkbook(is);
            }
            Sheet sheet = wb.getSheetAt(0);
            if(sheet!=null){
                //notNull = true;
            }
            assert sheet != null;
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                ArrayList<String> temp=new ArrayList<>();
                for(int col=0;col<row.getLastCellNum();col++){
                    if(row.getCell(col)!=null)
                        temp.add(row.getCell(col).toString());
                }
                ans.add(temp);
            }
        } catch (IOException e) {
        }
        return ans;
    }
}