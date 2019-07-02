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
import java.util.Random;

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
    @Autowired
    KnowledgeManagerDAO knowledgeManagerDAO;
    @Autowired
    ExpertUserDAO expertUserDAO;

    @Autowired
    RecmmendDAO recmmendDAO;

    @Autowired
    RankBoardDAO rankBoardDAO;
    @Autowired
    ExpertUserServiceI expertUserServiceI;
    @Override
    public User login(String account, String password) {
        NormalUser normalUser=normalUserDAO.findByAccountAndPasswordAndDisable(account,password,false);
        ExpertUser expertUser=expertUserDAO.findAllByDisableFalseAndAccount(account);
        KnowledgeManager knowledgeManager=knowledgeManagerDAO.findAllByDisableFalseAndAccount(account);
        if(normalUser!=null&&password.equals(normalUser.getPassword())){
            return normalUser;
        }
        if(expertUser!=null&&password.equals(expertUser.getPassword())){
            return expertUser;
        }
        if(knowledgeManager!=null&&password.equals(knowledgeManager.getPassword())){
            return knowledgeManager;
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
    public boolean addComment(Comment comment, String know_id) {
        comment.setKnowledge(knowledgeDAO.findByDisableFalseAndKnowId(know_id));
        return addComment(comment);
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
    public boolean addFavorite(NormalUser normalUser, String knowId) {
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
    public boolean deleteFavorite(NormalUser normalUser, String knowid) {
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
        return recmmendDAO.findAllByRecommendPK_NormalUser(normalUser).getRecommendPK().getRecommend();
    }

    @Override
    public List<Knowledge> getKnowledgeByType(KnowledgeType knowledgeType) {
        return knowledgeDAO.findAllByDisableFalseAndKnowledgeTypeAndStatus(knowledgeType,"通过");
    }

    @Override
    public List<KnowledgeType> getKnowledgeTypeByPreID(String typeid) {
        return knowledgeTypeDAO.findAllByDisableFalseAndPreTypeId(typeid);
    }

//    @Override
//    public List<Knowledge> getRecommend(NormalUser normalUser) {
//        UserClickTypePK userClickTypePK=new UserClickTypePK();
//        userClickTypePK.setUserId(normalUser);
//        List<UserClickType> userClickType=userClickTypeDAO.findAllByDisableFalseAndUserClickTypePKOrderByTimes(userClickTypePK);
//        if(userClickType.size()>1){
//            try{
//                return knowledgeDAO.findAllByDisableAndTypeId(false,userClickType.get(0).getUserClickTypePK().getTypeId().getTypeid());
//            }catch (Exception e){
//                e.printStackTrace();
//                return null;
//            }
//        }
//        return null;
//    }

//    @Override
//    public List<Knowledge> getKnowledgeByType(KnowledgeType knowledgeType) {
//        return knowledgeDAO.findAllByDisableAndTypeId(false,knowledgeType.getTypeid());
//    }

    @Override
    public List<KnowledgeType> getKnowledgeType(KnowledgeType knowledgeType) {
        return knowledgeTypeDAO.findAllByPreTypeId(knowledgeType.getTypeid());
    }

    @Override
    public List<Knowledge> getFavorite(String id) {
        NormalUser normalUser=normalUserDAO.findByDisableFalseAndId(id);
        return normalUser.getFavorite();
    }

    @Override
    public List<KnowledgeType> getFirstLayer() {
        return knowledgeTypeDAO.findAllByPreTypeIdIsNull();
    }

    @Override
    public boolean changePassword(String id, String oldPass, String newPass) {
        NormalUser normalUser=normalUserDAO.findByDisableFalseAndId(id);
        if(normalUser!=null&&normalUser.getPassword().equals(oldPass)){
            normalUser.setPassword(newPass);
            try{
                normalUserDAO.save(normalUser);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            return true;

        }else {
            return false;
        }
    }

    @Override
    public KnowledgeType findKnowlegeTypeById(String typeid) {

        return knowledgeTypeDAO.findByDisableFalseAndTypeid(typeid);
    }

    @Override
    public List<KnowledgeType> findnextKnowlegeTypeByPreId(String pretypeid) {
        return knowledgeTypeDAO.findAllByDisableFalseAndPreTypeId(pretypeid);
    }

    @Override
    public List<Comment> getCommentByKnowledge(Knowledge knowledge) {
        return commentDAO.findAllByDisableFalseAndKnowledge(knowledge);
    }

    @Override
    public List<Comment> getCommentByKnowledgeId(String knowId) {
        Knowledge knowledge=knowledgeDAO.findByDisableFalseAndKnowId(knowId);
        if(knowledge==null) return null;
        return commentDAO.findAllByDisableFalseAndKnowledge(knowledge);
    }

    @Override
    public List<Knowledge> searchByKeyWord(String keyword) {
        return knowledgeDAO.findAllByDisableFalseAndStatusAndDigestLikeOrTitleLikeOrDetailLike("通过",keyword,keyword,keyword);
    }

    @Override
    public List<KnowledgeType> getLastLayer() {
        return knowledgeTypeDAO.findAllByDisableFalseAndNextTypeIdIsNull();
    }

    @Override
    public List<RankBoard> getRankBoard() {
        return rankBoardDAO.findAll();
    }

    @Override
    public ExpertUser findExpertUserByTypeId(String typeid) {
        //寻找到第一个大类，借此寻找专家
        KnowledgeType knowledgeType=knowledgeTypeDAO.findByDisableFalseAndTypeid(typeid);
        while (knowledgeType.getPreTypeId()!=null){
            knowledgeType=knowledgeTypeDAO.findByDisableFalseAndTypeid(knowledgeType.getPreTypeId());
        }
        List<ExpertUser> expertUsers=expertUserDAO.findAllByDisableFalseAndKnowledgeType(knowledgeType);
        Random random=new Random();
        return expertUsers.get(random.nextInt(expertUsers.size()));
    }

    @Override
    public List<Knowledge> findKnowledgeByNormalUser(String account) {
        return knowledgeDAO.findAllByNormalUser(normalUserDAO.findAllByDisableFalseAndAccount(account));
    }

    /**
     * 通过标题等匹配知识
     * 但是必须是已通过审批的知识
     * @param keyword
     * @return
     */
    @Override
    public List<Knowledge> findKnowledgeByKeyWord(String keyword) {
        return knowledgeDAO.findAllByDisableFalseAndStatusAndDigestLikeOrTitleLikeOrDetailLike("通过",keyword,keyword,keyword);
    }

    /**
     * 得到用户的草稿
     * @param userid
     * @return
     */
    @Override
    public Knowledge getCaogaoByID(String account) {
        return knowledgeDAO.findAllByNormalUserAndStatus(normalUserDAO.findAllByDisableFalseAndAccount(account),"草稿");
    }

    /**
     * 添加草稿
     * @param userid
     * @param knowledge
     * @return
     */
    @Override
    public boolean addCaogao(String userid, Knowledge knowledge) {
        knowledge.setStatus("草稿");
//        knowledge.setNormalUser(normalUserDAO.findAllByDisableFalseAndAccount(userid));
        try{
            knowledgeDAO.save(knowledge);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteCaogao(String id) {
        try {
            Knowledge knowledge=knowledgeDAO.findAllByDisableFalseAndKnowId(id);
            knowledgeDAO.delete(knowledge);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
