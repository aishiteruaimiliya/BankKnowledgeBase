/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.service;

import com.summerpractice.BankKnowledgeBase.dao.*;
import com.summerpractice.BankKnowledgeBase.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonServiceI {
    @Autowired
    NormalUserDAO normalUserDAO;
    @Autowired
    ExpertUserDAO expertUserDAO;
    @Autowired
    KnowledgeManagerDAO knowledgeManagerDAO;
    @Autowired
    SystemManagerDAO systemManagerDAO;

    @Autowired
    KnowledgeTypeDAO knowledgeTypeDAO;
    @Override
    public boolean changePassword(String account, String old, String newPassword) {

        try{
            User user=knowledgeManagerDAO.findAllByDisableFalseAndAccount(account);
            if(user==null){
                user=normalUserDAO.findAllByDisableFalseAndAccount(account);
                if (user==null){
                    user=expertUserDAO.findAllByDisableFalseAndAccount(account);
                    if(user==null){
                        user=systemManagerDAO.findAllByDisableFalseAndAccount(account);
                        if(user==null) return false;
                        else {
                            if(user.getPassword().equals(old)){
                                user.setPassword(newPassword);
                                systemManagerDAO.save((SystemManager)user);
                                return true;
                            }else {
                                return false;
                            }
                        }
                    }
                    else {
                        if(user.getPassword().equals(old)){
                            user.setPassword(newPassword);
                            expertUserDAO.save((ExpertUser)user);
                            return true;
                        }
                    }
                }else {
                    if(user.getPassword().equals(old)){
                        user.setPassword(newPassword);
                        normalUserDAO.save((NormalUser)user);
                        return true;
                    }
                }

            }else {
                if(user.getPassword().equals(old)){
                    user.setPassword(newPassword);
                    knowledgeManagerDAO.save((KnowledgeManager)(user));
                    return true;
                }
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }

    @Override
    public KnowledgeType findRootTypeByTypeId(String typeId) {
        KnowledgeType knowledgeType=knowledgeTypeDAO.findByDisableFalseAndTypeid(typeId);
        while(knowledgeType.getPreTypeId()!=null){
            knowledgeType=knowledgeTypeDAO.findByDisableFalseAndTypeid(knowledgeType.getPreTypeId());
        }
        return knowledgeType;
    }
}
