/*
 * author:huangping
 *
 */

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
public class NormalUserServiceImpl implements NormalUserServiceI {
    @Autowired
    NormalUserDAO normalUserDAO;
    @Autowired
    CommentDAO commentDAO;
    @Autowired
    KnowledgeDAO knowledgeDAO;
    @Autowired
    UserClickTypeDAO userClickTypeDAO;
    @Autowired
    KnowledgeTypeDAO knowledgeTypeDAO;
    @Override
    public NormalUser login(String account, String password) {
        NormalUser normalUser=normalUserDAO.findByAccountAndPasswordAndDisable(account,password,false);
        if(normalUser!=null&&password.equals(normalUser.getPassword())){
            return normalUser;
        }
        return null;
    }

    @Override
    public boolean addComment(Comment comment){
        try{
            commentDAO.save(comment);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean addKnowledge(Knowledge knowledge) {
        try{
            knowledgeDAO.save(knowledge);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean addFavorite(NormalUser normalUser, int knowId) {
        Knowledge knowledge=knowledgeDAO.findByDisableFalseAndKnowId(knowId);
        if(knowledge!=null){
            knowledge.getNormalUsers().add(normalUser);
            try {
                knowledgeDAO.save(knowledge);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteFavorite(NormalUser normalUser, int knowid) {
        Knowledge knowledge=knowledgeDAO.findByDisableFalseAndKnowId(knowid);
        if(knowledge!=null){
            try{
                knowledge.getNormalUsers().remove(normalUser);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
        return true;
    }

    @Override
    public List<Knowledge> getRecommend(NormalUser normalUser) {
        UserClickTypePK userClickTypePK=new UserClickTypePK();
        userClickTypePK.setUserId(normalUser);
        List<UserClickType> userClickType=userClickTypeDAO.findAllByDisableFalseAndUserClickTypePKOrderByTimes(userClickTypePK);
        if(userClickType.size()>1){
            try{
                return knowledgeDAO.findAllByDisableAndTypeId(false,userClickType.get(0).getUserClickTypePK().getTypeId().getTypeid());
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public List<Knowledge> getKnowledgeByType(KnowledgeType knowledgeType) {
        List<Knowledge> knowledges=knowledgeDAO.findAllByDisableAndTypeId(false,knowledgeType.getTypeid());
        return knowledges;
    }

    @Override
    public List<KnowledgeType> getKnowledgeType(KnowledgeType knowledgeType) {
        List<KnowledgeType> knowledges=knowledgeTypeDAO.findAllByPreTypeId(knowledgeType.getTypeid());
        return knowledges;
    }

    @Override
    public List<Knowledge> getFavorite(int id) {
        NormalUser normalUser=normalUserDAO.findByDisableFalseAndId(id);
        return normalUser.getFavorite();
    }

    @Override
    public List<KnowledgeType> getFirstLayer() {
        return knowledgeTypeDAO.findAllByPreTypeIdIsNull();
    }

    @Override
    public boolean changePassword(int id, String oldPass, String newPass) {
        NormalUser normalUser=normalUserDAO.findByDisableFalseAndId(id);
        if(normalUser!=null&&normalUser.getPassword().equals(oldPass)){
            normalUser.setPassword(newPass);
            try{
                normalUserDAO.save(normalUser);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }

        }else {
            return false;
        }
        return false;
    }
}