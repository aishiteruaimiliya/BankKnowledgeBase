/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.service;

import com.summerpractice.BankKnowledgeBase.dao.*;
import com.summerpractice.BankKnowledgeBase.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminServiceI {

    @Autowired
    NormalUserDAO normalUserDAO;
    @Autowired
    ExpertUserDAO expertUserDAO;
    @Autowired
    KnowledgeManagerDAO knowledgeManagerDAO;
    @Autowired
    SystemManagerDAO systemManagerDAO;

    @Autowired
    DepartmentDAO departmentDAO;
    @Override
    public boolean addUser(User user,Department department) {
        try {
            if (user instanceof NormalUser) {
                NormalUser temp = normalUserDAO.findAllByDisableFalseAndAccount(user.getAccount());
                if (temp == null) {
                    NormalUser normalUser=(NormalUser) user;
                    normalUser.setDepartment(department);
                    normalUserDAO.save(normalUser);
                    return true;
                }else {
                    return false;
                }
            } else if (user instanceof ExpertUser) {
                ExpertUser temp=expertUserDAO.findAllByDisableFalseAndAccount(user.getAccount());
                if(temp==null){
                    ExpertUser expertUser=(ExpertUser) user;
                    expertUser.setDepartment(department);
                    expertUserDAO.save(expertUser);
                    return true;
                }else{
                    return false;
                }
            } else if (user instanceof KnowledgeManager) {
                KnowledgeManager knowledgeManager=knowledgeManagerDAO.findAllByDisableFalseAndAccount(user.getAccount());
                if(knowledgeManager==null){
                    KnowledgeManager knowledgeManager1=(KnowledgeManager)user;
                    knowledgeManager1.setDepartment(department);
                    knowledgeManagerDAO.save(knowledgeManager1);
                    return true;
                }else {
                    return false;
                }
            } else if (user instanceof SystemManager) {
                SystemManager systemManager=systemManagerDAO.findAllByDisableFalseAndAccount(user.getAccount());
                if(systemManager==null){
                    systemManagerDAO.save((SystemManager)user);
                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean batchAdd(List<User> users,Department department) {
        try {
            for (User u : users) {
                boolean t=addUser(u,department);
                if(!t)
                    return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public List<Department> getDepartmentByFirst(String first) {
        return departmentDAO.findAllByDisableFalseAndFirst(first);
    }

    @Override
    public List<Department> getDepartmentBySecond(String second) {
        return departmentDAO.findAllByDisableFalseAndSecond(second);
    }

    @Override
    public List<Department> getDepartmentByThird(String third) {
        return departmentDAO.findAllByDisableFalseAndThird(third);
    }

    @Override
    public List<Department> getDepartmentByFourth(String fourth) {
        return departmentDAO.findAllByDisableFalseAndFourth(fourth);
    }

    @Override
    public List<Department> getFirst() {
        return departmentDAO.getFirstByDisableFalse();
    }

    @Override
    public List<Department> getAll() {
        return  departmentDAO.getAllByDisableFalse();
    }

    @Override
    public Department findDepartmentByID(String id) {
        return departmentDAO.findDepartmentByDepIdAndDisableFalse(id);
    }
}
