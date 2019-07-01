/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.entity.Department;
import com.summerpractice.BankKnowledgeBase.entity.SystemManager;
import com.summerpractice.BankKnowledgeBase.service.AdminServiceI;
import com.summerpractice.BankKnowledgeBase.service.KnowledgeManagerServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminShowPageController {
    @Autowired
    AdminServiceI adminServiceI;
    @Autowired
    KnowledgeManagerServiceI knowledgeManagerServiceI;
    @GetMapping("/addUser")
    public ModelAndView showAddUser(){
      List<Department> dep=adminServiceI.getAll();
//        System.out.print(dep.toString());
        ModelAndView modelAndView= new ModelAndView("AddUser","departments",dep);
        modelAndView.addObject("types",knowledgeManagerServiceI.getFirstLayer());
        return modelAndView;
    }
    @GetMapping("/ShowLogin")
    public ModelAndView showLogin(HttpServletRequest request,ModelAndView modelAndView){
        SystemManager systemManager=verify(request);
        if(systemManager==null){
            modelAndView.setViewName("AdminLoginPage");
        }else {
            modelAndView.addObject("depts",adminServiceI.getAllDept());
            modelAndView.setViewName("AdminHomePage");
            modelAndView.addObject("admin",systemManager);
        }
        return modelAndView;
    }
    @GetMapping("/showChangePass")
    public String showChangePass(HttpServletRequest request){
        SystemManager systemManager=verify(request);
        if(systemManager==null) return "AdminLoginPage";
        return "ChangePasswordPageAdmin";
    }
    @GetMapping("/showAddDeptPage")
    public String showAddDeptPage(HttpServletRequest request){
        //todo  后期添加身份拦截
        return "Department";
    }
    @GetMapping("/showDepartmentList")
    public ModelAndView showList(ModelAndView modelAndView){
        modelAndView.setViewName("DepartmentListPage");
        List<Department> departments=adminServiceI.getAllDept();
        modelAndView.addObject("depts",departments);
        return modelAndView;
    }
    @GetMapping("/showEditDeptPage")
    public ModelAndView showEDitPAge(@RequestParam(name = "depId",required = true)String depId,
                                     ModelAndView modelAndView){
        modelAndView.setViewName("DepartmentEditPage");
        Department department=adminServiceI.findDepartmentByID(depId);
        modelAndView.addObject("dept",department);
        return modelAndView;
    }
    public SystemManager verify(HttpServletRequest request){
        return (SystemManager) request.getSession().getAttribute("admin");
    }
}
