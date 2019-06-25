/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.dao.ExpertUserDAO;
import com.summerpractice.BankKnowledgeBase.dao.KnowledgeDAO;
import com.summerpractice.BankKnowledgeBase.dao.KnowledgeTypeDAO;
import com.summerpractice.BankKnowledgeBase.dao.NormalUserDAO;
import com.summerpractice.BankKnowledgeBase.entity.*;
import com.summerpractice.BankKnowledgeBase.service.DepartmentServiceI;
import com.summerpractice.BankKnowledgeBase.service.NormalUserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class IndexController {
    @Autowired
    NormalUserDAO normalUserDAO;
    @Autowired
    DepartmentServiceI departmentServiceI;
    @Autowired
    NormalUserServiceI normalUserServiceI;
    @Autowired
    KnowledgeTypeDAO knowledgeTypeDAO;
    @Autowired
    KnowledgeDAO knowledgeDAO;
    @Autowired
    ExpertUserDAO expertUserDAO;
    @GetMapping("/genSample")
    public String index(){
//        departmentServiceI.getAll();
        init();
        return "ExcelUpload";
    }

    private void init() {
        Department department=new Department();
        department.setFirst("hello");
        departmentServiceI.addDepartment(department);
        NormalUser normalUser=new NormalUser();
        normalUser.setAccount("huangping");
        normalUser.setPassword("123");
        normalUser.setDepartment(department);
        normalUser.setName("tom");
        normalUserDAO.save(normalUser);
        KnowledgeType knowledgeType=new KnowledgeType();
        knowledgeType.setTypecontent("jieck");
        knowledgeTypeDAO.save(knowledgeType);
        ExpertUser expertUser=new ExpertUser();
        expertUser.setDepartment(department);
        expertUser.setKnowledgeType(knowledgeType);
        expertUser.setAccount("123");
        expertUser.setName("hp");
        expertUser.setPassword("123456");
        expertUserDAO.save(expertUser);
        Knowledge knowledge=new Knowledge();
        knowledge.setExpertUser(null);
        knowledge.setNormalUser(normalUser);
        knowledge.setDetail("hhhh");
        knowledge.setTitle("hello");
        knowledge.setDigest("hhhhhhhh");
        knowledge.setStatus("pass");
        knowledge.setTypeId(knowledgeType);
        knowledgeDAO.save(knowledge);
        List<Knowledge>knowledges=new ArrayList<>();
        knowledges.add(knowledge);
        normalUser.setFavorite(knowledges);
        normalUserDAO.save(normalUser);
    }
    @GetMapping("/firstLayer")
    public String get(){
        return "getFirstLayer";
    }
}
