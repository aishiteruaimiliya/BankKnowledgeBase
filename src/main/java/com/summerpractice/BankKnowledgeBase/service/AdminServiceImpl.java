/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.service;

import com.summerpractice.BankKnowledgeBase.dao.*;
import com.summerpractice.BankKnowledgeBase.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
    public boolean addDepartment(Department department) {
        try{
            departmentDAO.save(department);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteDepartment(String departmentId) {
        Department department=departmentDAO.findDepartmentByDepIdAndDisableFalse(departmentId);
        department.setDisable(true);
        try{
            departmentDAO.save(department);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean changeDepartment(Department department) {
        try{
            departmentDAO.save(department);
        }catch (Exception e){
            return false;
        }
        return true;
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

    /**
     * 根据机构查询人员
     * @param department
     * @return
     */
    @Override
    public List<User> findUserByDepartment(Department department) {
        List<User> ans=new ArrayList<>();
        Collections.copy(ans,normalUserDAO.findAllByDisableFalseAndDepartment(department));
        Collections.copy(ans,expertUserDAO.findAllByDisableFalseAndDepartment(department));
        Collections.copy(ans,knowledgeManagerDAO.findAllByDisableFalseAndDepartment(department));
        return ans;
    }

    @Override
    public List<User> findNormalUserByDepartment(Department department) {
        return normalUserDAO.findAllByDisableFalseAndDepartment(department);
    }

    @Override
    public List<User> findExpertUserByDepartment(Department department) {
        return expertUserDAO.findAllByDisableFalseAndDepartment(department);
    }

    @Override
    public List<User> findKnowledgeManagerByDepartment(Department department) {
        return knowledgeManagerDAO.findAllByDisableFalseAndDepartment(department);
    }

    @Override
    public User findNormalUserByAccount(String Account) {
        return normalUserDAO.findAllByDisableFalseAndAccount(Account);
    }

    @Override
    public User findExpertUserByAccount(String Account) {
        return expertUserDAO.findAllByDisableFalseAndAccount(Account);
    }

    @Override
    public User findKnowledgeManagerByAccount(String Account) {
        return systemManagerDAO.findAllByDisableFalseAndAccount(Account);
    }

    @Override
    public boolean deleteUser(String account) {
        try{
            User user=normalUserDAO.findAllByDisableFalseAndAccount(account);
            if(user==null) {
                user = expertUserDAO.findAllByDisableFalseAndAccount(account);
                if (user == null) {
                    user = systemManagerDAO.findAllByDisableFalseAndAccount(account);
                    if (user == null) {
                        return false;
                    } else {
                        user.setDisable(false);
                        systemManagerDAO.save((SystemManager) user);
                        return true;
                    }
                } else {
                    user.setDisable(false);
                    expertUserDAO.save((ExpertUser) (user));
                    return true;
                }
            }
            else {
                user.setDisable(false);
                normalUserDAO.save((NormalUser)user);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean changeUser(User user) {
        try{
            if(user instanceof NormalUser){
                normalUserDAO.save((NormalUser)user);
            }else if(user instanceof ExpertUser){
                expertUserDAO.save((ExpertUser)user);
            }else if(user instanceof SystemManager){
                systemManagerDAO.save((SystemManager)user);
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
