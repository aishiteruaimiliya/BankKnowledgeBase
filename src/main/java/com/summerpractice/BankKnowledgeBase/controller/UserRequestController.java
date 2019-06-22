/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.entity.NormalUser;
import com.summerpractice.BankKnowledgeBase.service.DepartmentServiceI;
import com.summerpractice.BankKnowledgeBase.service.NormalUserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller("/userRequest")
public class UserRequestController {
    @Autowired
    DepartmentServiceI departmentServiceI;
    @Autowired
    NormalUserServiceI normalUserServiceI;

    @PostMapping("/search")
    public Model search(HttpServletRequest request,Model model){
        model.addAttribute("result","搜索结果");
        return model;
    }
    @PostMapping("/addKnowledge")
    public String addKnowLedge(){
        //todo add knowledge to database
        return "msg";
    }
    @RequestMapping("/comment")
    public String comment(){
        //todo 采用前端传来的数据，然后采用get请求获取,采用post获取提交的数据
        //save数据
        return "结果";
    }
    @RequestMapping("/getmyKnowledgeByType")
    public String getMyknowlegeByType(Model model){
        return "数据";
    }
    @RequestMapping("/addMyfavorite")
    public String addFavorite() {
        //todo 添加到数据库
        return "";
    }
    @RequestMapping("/showFavoriteDetail")
    public String showDetail(){
        return "";
    }
    @RequestMapping("/deleteFavorite")
    public String deleteFavorite(){
        return "";
    }
    @RequestMapping("/getRecommend")
    public String getRecomment(){
        return "";
    }
    @PostMapping("/doLogin")
    public String login(@RequestParam(name = "account",required = true) String account,
                        @RequestParam(name = "password",required = true) String password){
        NormalUser normalUser=normalUserServiceI.login(account,password);
        if(normalUser!=null){
            return "userHomePage";
        }else {
           return "Login";
        }
    }
}
