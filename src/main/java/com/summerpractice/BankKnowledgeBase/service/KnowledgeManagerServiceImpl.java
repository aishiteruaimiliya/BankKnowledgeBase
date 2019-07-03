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
        try{
            List<NormalUser> normalUsers=normalUserDAO.findAllByDisableFalse();
            for(NormalUser normalUser:normalUsers){
                List<UserClickType> userClickTypes=userClickTypeDAO.findAllByDisableFalseAndUserClickTypePK_UserIdOrderByTimes(normalUser);
                UserClickType userClickType=userClickTypes.get(0);
                List<Knowledge> knowledges=new ArrayList<>();
                List<KnowledgeType> knowledgeTypes=bfsTravel(userClickType.getUserClickTypePK().getTypeId());
                for(KnowledgeType knowledgeType:knowledgeTypes)
                    knowledges.addAll(knowledgeDAO.findAllByDisableFalseAndKnowledgeTypeAndStatus(knowledgeType,"通过"));
                //todo 修改逻辑
                normalUser.getRecommends().clear();
                int recommendNum = 20;
                if(knowledges.size()> recommendNum){
                    for (int i = 0; i < recommendNum; i++) {
                       normalUser.getRecommends().add(knowledges.get(i));
                    }
                }else {
                    for (Knowledge knowledge:knowledges) {
                       normalUser.getRecommends().add(knowledge);
                    }
                }
                normalUserDAO.save(normalUser);
            }
        }catch (Exception e){
            return false;
        }
        return true;
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
    public Map<KnowledgeType, List<KnowledgeType>> getTwoLayer() {
        List<KnowledgeType> knowledgeTypes=knowledgeTypeDAO.findAllByPreTypeIdIsNull();
        Map<KnowledgeType,List<KnowledgeType>> map=new HashMap<>();
        for(KnowledgeType knowledgeType:knowledgeTypes){
            map.put(knowledgeType,knowledgeTypeDAO.findAllByDisableFalseAndPreTypeId(knowledgeType.getTypeid()));
        }
        return  map;
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
