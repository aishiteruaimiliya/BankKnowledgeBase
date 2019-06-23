/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.entity.*;
import com.summerpractice.BankKnowledgeBase.service.AdminServiceI;
import com.summerpractice.BankKnowledgeBase.util.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/adminRequest")
public class AdminRequestController {
    @Autowired
    AdminServiceI adminServiceI;
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public String uploadFile(@RequestParam(name = "file",required = true) MultipartFile file, HttpServletRequest request){
        //todo 解决multipart加密数据的获取问题
        if(file.isEmpty()||request.getParameter("selected")==null||request.getParameter("selected").equals("")){
            return "ERROR";
        }
        String depid=request.getParameter("selected");
        System.out.print(depid);
        List<User> users;
        try {
            users = ExcelReader.parseUserFromArray(ExcelReader.readExcel(file));
        }catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
        Department department=adminServiceI.findDepartmentByID(depid);
        try {
            adminServiceI.batchAdd(users,department);
        }catch (Exception e){
            return "ERROR";
        }
        return "AddUser";

    }
    @RequestMapping("/adduser")
    public String addUser(@RequestParam(name = "selected",required = true) String depid,
                          @RequestParam(name = "username",required = true) String username,
                          @RequestParam(name = "account",required = true) String account,
                          @RequestParam(name = "password",required = true) String password,
                          @RequestParam(name = "userType",required = true) String userType){
        User user;
        switch (userType){
            case "1":
                user=new NormalUser();
                break;
            case "2":
                user=new KnowledgeManager();
                break;
            case "3":
                user=new ExpertUser();
                break;
            case "4":
                user=new SystemManager();
                break;
                default:
                    return "ERROR";
        }
        user.setAccount(account);
        user.setName(username);
        user.setPassword(password);
        Department department=adminServiceI.findDepartmentByID(depid);
        try{
            adminServiceI.addUser(user,department);
        }catch (Exception e){
            return "ERROR";
        }
        return "AddUser";
    }
}
