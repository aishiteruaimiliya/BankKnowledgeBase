/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.service;

import com.summerpractice.BankKnowledgeBase.dao.*;
import com.summerpractice.BankKnowledgeBase.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KnowledgeManagerServiceImpl implements KnowledgeManagerServiceI {

    @Autowired
    KnowledgeTypeDAO knowledgeTypeDAO;
    @Autowired
    KnowledgeDAO knowledgeDAO;
    @Autowired
    ExpertUserDAO expertUserDAO;

    @Autowired
    RankBoardDAO rankBoardDAO;

    @Autowired
    UserClickTypeDAO userClickTypeDAO;

    @Autowired
    NormalUserDAO normalUserDAO;


    @Override
    public int countKnowledgeNum() {
//        return knowledgeDAO.countAll();
        return knowledgeDAO.countAllByDisableFalse();
    }

    @Override
    public boolean refreshCounter() {
        //todo 新建一张表 存储count值
        return false;
    }

    @Override
    public boolean refreshRankBoard() {
        try{
            List<Knowledge> knowledges=knowledgeDAO.findAllByDisableFalseAndStatusOrderByClickedDesc("通过");
            rankBoardDAO.deleteAll();
            if(knowledges.size()>20){
                for (int i = 0; i <20 ; i++) {
                    RankBoard rankBoard=new RankBoard();
                    RankBoardPK rankBoardPK=new RankBoardPK();
                    rankBoardPK.setKnowledge(knowledges.get(i));
                    rankBoard.setRankBoardPK(rankBoardPK);
                    rankBoardDAO.save(rankBoard);
                }
            }else {
                for (Knowledge knowledge:knowledges) {
                    RankBoard rankBoard=new RankBoard();
                    RankBoardPK rankBoardPK=new RankBoardPK();
                    rankBoardPK.setKnowledge(knowledge);
                    rankBoard.setRankBoardPK(rankBoardPK);
                    rankBoardDAO.save(rankBoard);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean addKnowledgeType(KnowledgeType knowledgeType) {
        try{
            knowledgeTypeDAO.save(knowledgeType);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean changeKnowledgeType(KnowledgeType knowledgeType) {
        try{
            knowledgeTypeDAO.save(knowledgeType);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public List<KnowledgeType> getFirstLayer() {
        return knowledgeTypeDAO.findAllByDisableFalseAndPreTypeIdIsNull();
    }

    @Override
    public boolean deleteKnowledgeType(KnowledgeType knowledgeType) {
        try{
            bfsDelete(knowledgeType);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteKnowledge(String typeId) {
        KnowledgeType knowledgeType=knowledgeTypeDAO.findByDisableFalseAndTypeid(typeId);
        if(knowledgeType==null) return false;
        knowledgeType.setDisable(true);
        try{
            knowledgeTypeDAO.save(knowledgeType);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean distribution(ExpertUser expertUser, KnowledgeType knowledgeType) {
        try{
            expertUser.setKnowledgeType(knowledgeType);
            expertUserDAO.save(expertUser);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean distribution(String expertUserID, String knowledgeTypeID) {
        ExpertUser expertUser=expertUserDAO.findAllByDisableFalseAndId(expertUserID);
        KnowledgeType knowledgeType=knowledgeTypeDAO.findByDisableFalseAndTypeid(knowledgeTypeID);
        if(expertUser==null||knowledgeType==null) return false;
        return distribution(expertUser,knowledgeType);
    }

    @Override
    public boolean refreshRecommend() {
        //修改推荐算法
        //根据用户点击的知识维度的次数，采取4:3:2:1的比例进行取值推荐，避免全部推荐为一个知识维度的知识的情况
        try{
            List<NormalUser> normalUsers=normalUserDAO.findAllByDisableFalse();
            for(NormalUser normalUser:normalUsers) {
                List<UserClickType> userClickTypes = userClickTypeDAO.findAllByDisableFalseAndUserClickTypePK_UserIdOrderByTimes(normalUser);
                normalUser.getRecommends().clear();
                if (userClickTypes.size() > 4) {
                    for (int i = 0; i < 4; i++) {
                        recommendHelper(normalUser, userClickTypes, i);
                    }
                }else {
                    for (int i = 0; i <userClickTypes.size() ; i++) {
                        recommendHelper(normalUser, userClickTypes, i);
                    }
                }
                normalUserDAO.save(normalUser);
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private void recommendHelper(NormalUser normalUser, List<UserClickType> userClickTypes, int i) {
        UserClickType userClickType = userClickTypes.get(i);
        List<Knowledge> knowledges = new ArrayList<>();
        List<KnowledgeType> knowledgeTypes = bfsTravel(userClickType.getUserClickTypePK().getTypeId());
        for (KnowledgeType knowledgeType : knowledgeTypes)
            knowledges.addAll(knowledgeDAO.findAllByDisableFalseAndKnowledgeTypeAndStatus(knowledgeType, "通过"));
        normalUser.getRecommends().addAll(knowledges.size() > 4 - i ? knowledges.subList(0, 4 - i - 1) : knowledges);
    }

    private List<KnowledgeType> bfsTravel(KnowledgeType typeId) {
        List<KnowledgeType> ans=new ArrayList<>();
        Queue<KnowledgeType> queue=new LinkedList<>();
        queue.offer(typeId);
        try{
            while (!queue.isEmpty()){
                KnowledgeType tmp=queue.poll();
                ans.add(tmp);
                List<KnowledgeType> knowledgeTypes=knowledgeTypeDAO.findAllByPreTypeId(tmp.getTypeid());
                for (KnowledgeType know:knowledgeTypes) {
                    if(tmp.getNextTypeId()!=null) queue.offer(know);
                    ans.add(know);
                }
            }
        }catch (Exception e){
            return null;
        }
        return ans;
    }

    @Override
    public int getTypeNum() {
        return knowledgeTypeDAO.countAllByDisableFalseAndPreTypeIdIsNull();
    }

    @Override
    public List<TypeNode> getTwoLayer() {
        List<KnowledgeType> knowledgeTypes=knowledgeTypeDAO.findAllByPreTypeIdIsNull();
        List<TypeNode> typeNodes=new ArrayList<>();
        for(KnowledgeType knowledgeType:knowledgeTypes){
            typeNodes.add(new TypeNode(knowledgeType,knowledgeTypeDAO.findAllByDisableFalseAndPreTypeId(knowledgeType.getTypeid())));
        }
        return  typeNodes;
    }

    @Override
    public KnowledgeType findKnowledgeTypeById(String id) {
        return knowledgeTypeDAO.findByDisableFalseAndTypeid(id);
    }

    @Override
    public List<KnowledgeType> getNextLayer(String typeId) {
        return knowledgeTypeDAO.findAllByDisableFalseAndPreTypeId(typeId);
    }

    @Override
    public List<ExpertUser> getAllExpert() {
        return expertUserDAO.findAllByDisableFalse();
    }

    @Override
    public KnowledgeType findKnowledgeTypeByTypeID(String id) {
        return knowledgeTypeDAO.findByDisableFalseAndTypeid(id);
    }


    public boolean bfsDelete(KnowledgeType knowledgeType){
        Queue<KnowledgeType> queue=new LinkedList<>();
        queue.offer(knowledgeType);
        try{
            while (!queue.isEmpty()){
                KnowledgeType tmp=queue.poll();
                tmp.setDisable(true);
                knowledgeTypeDAO.save(tmp);
                List<KnowledgeType> knowledgeTypes=knowledgeTypeDAO.findAllByPreTypeId(tmp.getTypeid());
                for (KnowledgeType know:knowledgeTypes) {
                    know.setDisable(true);
                    if(tmp.getNextTypeId()!=null) queue.offer(know);
                    knowledgeTypeDAO.save(know);
                }
            }
        }catch (Exception e){
            return false;
        }

        return true;
    }
}
