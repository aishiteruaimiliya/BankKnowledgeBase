/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.entity.Department;
import com.summerpractice.BankKnowledgeBase.service.AdminServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminShowPageController {
    @Autowired
    AdminServiceI adminServiceI;
    @GetMapping("/addUser")
    public ModelAndView showAddUser(){
        List<Department> dep=adminServiceI.getAll();
        System.out.print(dep.toString());
        return new ModelAndView("AddUser","departments",dep);
    }
}
