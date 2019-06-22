/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.entity.Department;
import com.summerpractice.BankKnowledgeBase.entity.NormalUser;
import com.summerpractice.BankKnowledgeBase.service.DepartmentServiceI;
import com.summerpractice.BankKnowledgeBase.service.NormalUserServiceI;
import com.summerpractice.BankKnowledgeBase.util.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Controller
public class IndexController {
    @Autowired
    DepartmentServiceI departmentServiceI;
    @Autowired
    NormalUserServiceI normalUserServiceI;
    @GetMapping("/")
    public String index(){
        departmentServiceI.getAll();
        return "userHomePage";
    }
    @PostMapping("/uploadfile")
    public String uploadFile(@RequestParam(name = "file",required = true) MultipartFile file){
        ArrayList<ArrayList<String>> ans = null;
        try {
            ans = ExcelReader.readExcel(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
       for(ArrayList<String> row:ans){
           for(String s:row){
               System.out.print(s+" ");
           }
           System.out.println("一层");
       }
        return "userHomePage";
    }
    @PostMapping("/addDept")
    public String addDept(@RequestParam(name = "first",required = true) String first,
                          @RequestParam(name = "second",required = true) String second,
                          @RequestParam(name = "third",required = true) String third,
                          @RequestParam(name = "fourth",required = true) String fourth){

        departmentServiceI.addDepartment(new Department(first,second,third,fourth));
        return "userHomePage";
    }
    @GetMapping("/manageDept")
    public String manage(){
        return "Department";
    }
    @PostMapping("/updateDept")
    public String updateDept(@RequestParam(name = "first",required = true) String first,
                          @RequestParam(name = "second",required = true) String second,
                          @RequestParam(name = "third",required = true) String third,
                          @RequestParam(name = "fourth",required = true) String fourth){

        departmentServiceI.updateInfo(new Department(first,second,third,fourth));
        return "userHomePage";
    }
    @GetMapping("/login")
    public String showloginPage(){
        return "Login";
    }
    @PostMapping("/dologin")
    public String dologin(@RequestParam(name = "account",required = true) String account,
                          @RequestParam(name = "password",required = true) String password){
        NormalUser normalUser=normalUserServiceI.login(account,password);
        if(normalUser==null){
            return "userHomePage";
        }else {
            System.out.print(normalUser.getDepartment().toString());
            return manage();
        }

    }
}
