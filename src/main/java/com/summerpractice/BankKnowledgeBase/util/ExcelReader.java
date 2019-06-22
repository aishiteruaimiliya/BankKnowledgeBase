/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.util;

import com.summerpractice.BankKnowledgeBase.entity.*;
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
    public static ArrayList<User> parseUserFromArray(ArrayList<ArrayList<String>> arrayLists){
        ArrayList<User> users=new ArrayList<>();
        for(ArrayList<String> user:arrayLists){
           switch (user.get(user.size()-1)){
               /**
                * 导入的excel的最后一位为标识符 1普通用户 2专家用户 3 知识管理员 4 系统管理员
                * excel 第一行为名字 第二行为账号 第三行为密码 第四行为 机构号 若为管理员，则为标识符,第五行为身份标识
                */
               case "1":
                   NormalUser normalUser=new NormalUser();
                   normalUser.setName(user.get(0));
                   normalUser.setAccount(user.get(1));
                   normalUser.setPassword(user.get(2));
                   normalUser.setDepartment(Integer.parseInt(user.get(3)));
                   users.add(normalUser);
                   break;
               case "2":
                   ExpertUser expertUser=new ExpertUser();
                   expertUser.setName(user.get(0));
                   expertUser.setAccount(user.get(1));
                   expertUser.setPassword(user.get(2));
                   expertUser.setDepartment(Integer.parseInt(user.get(3)));
                   users.add(expertUser);
                   break;
               case "3":
                   KnowledgeManager knowledgeManager=new KnowledgeManager();
                   knowledgeManager.setName(user.get(0));
                   knowledgeManager.setAccount(user.get(1));
                   knowledgeManager.setPassword(user.get(2));
                   knowledgeManager.setDepartment(Integer.parseInt(user.get(3)));
                   users.add(knowledgeManager);
                   break;
               case "4":
                   SystemManager systemManager=new SystemManager();
                   systemManager.setName(user.get(0));
                   systemManager.setAccount(user.get(1));
                   systemManager.setPassword(user.get(2));
                   users.add(systemManager);
                   break;
                   default:break;
           }
        }
        return users;
    }
}