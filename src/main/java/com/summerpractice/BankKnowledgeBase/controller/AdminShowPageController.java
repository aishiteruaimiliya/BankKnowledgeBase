/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.entity.*;
import com.summerpractice.BankKnowledgeBase.service.AdminServiceI;
import com.summerpractice.BankKnowledgeBase.service.KnowledgeManagerServiceI;
import com.summerpractice.BankKnowledgeBase.service.NormalUserServiceI;
import com.summerpractice.BankKnowledgeBase.service.NormalUserServiceImpl;
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
    @Autowired
    NormalUserServiceI normalUserServiceI;
    @GetMapping("/addUser")
    public ModelAndView showAddUser(HttpServletRequest request){
        if ((SystemManager)request.getSession().getAttribute("admin") == null){
            return new ModelAndView("AdminLoginPage");
        }
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
        if (request.getSession().getAttribute("admin") == null){
            return "未登录";
        }
        return "Department";
    }
    @GetMapping("/showDepartmentList")
    public ModelAndView showList(ModelAndView modelAndView,
                                 HttpServletRequest request){
        if (request.getSession().getAttribute("admin") == null){
            modelAndView.setViewName("AdminLoginPage");
            return modelAndView;
        }
        modelAndView.setViewName("DepartmentListPage");
        List<Department> departments=adminServiceI.getAllDept();
        modelAndView.addObject("depts",departments);
        return modelAndView;
    }
    @GetMapping("/showEditDeptPage")
    public ModelAndView showEDitPAge(@RequestParam(name = "depId",required = true)String depId,
                                     ModelAndView modelAndView,
                                     HttpServletRequest request){
        if (request.getSession().getAttribute("admin") == null){
            modelAndView.setViewName("AdminLoginPage");
            return modelAndView;
        }
        modelAndView.setViewName("DepartmentEditPage");
        Department department=adminServiceI.findDepartmentByID(depId);
        modelAndView.addObject("dept",department);
        return modelAndView;
    }
    public SystemManager verify(HttpServletRequest request){
        return (SystemManager) request.getSession().getAttribute("admin");
    }
    @GetMapping("/showEditUserPage")
    public ModelAndView showEditUserPage(@RequestParam(name = "userId",required = true)String userId,
                                     ModelAndView modelAndView,
                                         HttpServletRequest request){
        if (request.getSession().getAttribute("admin") == null){
            modelAndView.setViewName("AdminLoginPage");
            return modelAndView;
        }
        modelAndView.setViewName("UserEditPage");
        User user= normalUserServiceI.getUserByAccount(userId);
        modelAndView.addObject("user", user);
        List<Department> departments=adminServiceI.getAllDept();
        modelAndView.addObject("depts",departments);
        if(user instanceof NormalUser)
            modelAndView.addObject("usertype", "普通用户");
        else if(user instanceof ExpertUser)
            modelAndView.addObject("usertype", "专家用户");
        else if(user instanceof KnowledgeManager)
            modelAndView.addObject("usertype", "知识管理员");
        else
            modelAndView.addObject("usertype", "获取用户类型错误");
        return modelAndView;
    }
}
