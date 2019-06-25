/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.entity.*;
import com.summerpractice.BankKnowledgeBase.service.DepartmentServiceI;
import com.summerpractice.BankKnowledgeBase.service.NormalUserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/userRequest")
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
    @RequestMapping("/getMyKnowledgeByType")
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
    public String getRecommend(){
        return "";
    }
    @PostMapping("/doLogin")
    public ModelAndView login(@RequestParam(name = "account",required = true) String account,
                              @RequestParam(name = "password",required = true) String password,
                              HttpServletRequest request,ModelAndView modelAndView){

        User usr=normalUserServiceI.login(account,password);
        modelAndView.setViewName("Login");
        request.getSession().setAttribute("user",usr);
        modelAndView.addObject("user",usr);
        if(usr instanceof NormalUser){
            modelAndView.setViewName("userHomePage");
        }else if( usr instanceof ExpertUser) {
           modelAndView.setViewName("expertUserHomePage");
        }else if(usr instanceof KnowledgeManager){
            modelAndView.setViewName("knowledgeHomePage");
        }else {
            modelAndView.addObject("msg","用户不存在！请重新输入！");
        }
        return modelAndView;
    }
    @ResponseBody
    @RequestMapping("/addknowledge.do")
    public String  addKnowledge(@RequestParam(name = "title",required = true)String title,
                                @RequestParam(name = "digest",required = true)String digest,
                                @RequestParam(name = "detail",required = true)String detail,
                                @RequestParam(name = "typeid",required = true)String typeid
                                ,HttpServletRequest request){
        NormalUser normalUser= (NormalUser) request.getSession().getAttribute("user");

        if(normalUser==null) return "failed";

        try{
            Knowledge knowledge=new Knowledge();
            knowledge.setTypeId(normalUserServiceI.findKnowlegeTypeById(typeid));
            knowledge.setNormalUser(normalUser);
            knowledge.setStatus("未审批");
            knowledge.setTitle(title);
            knowledge.setDetail(detail);
            knowledge.setDigest(digest);
            normalUserServiceI.addKnowledge(knowledge);
        }catch (Exception e){
            e.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/firstLayer")
    public String getFirstLayer(HttpServletRequest request){
        NormalUser normalUser=verifyUser(request);
//        if(normalUser==null) return "404";

        return normalUserServiceI.getFirstLayer().toString();

    }
    @ResponseBody
    @RequestMapping(value = "/getTypeByType",method = RequestMethod.GET)
    public String getTypeByType(@RequestParam(name = "typeId",required = true) String typeid,HttpServletRequest request){
//        NormalUser normalUser=verifyUser(request);
//        if (normalUser == null) {
//            return "404";
//        }
        return normalUserServiceI.findnextKnowlegeTypeByPreId(typeid).toString();

    }

    private NormalUser verifyUser(HttpServletRequest request){
        return (NormalUser) request.getSession().getAttribute("user");
    }
}
