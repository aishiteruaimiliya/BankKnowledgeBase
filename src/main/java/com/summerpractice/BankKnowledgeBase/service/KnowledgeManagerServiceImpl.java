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

    @Autowired
    RecmmendDAO recmmendDAO;
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
            List<Knowledge> knowledges=knowledgeDAO.findAllByDisableFalseOrderByClickedDesc();
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
        return knowledgeTypeDAO.findAllByPreTypeIdIsNull();
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
                List<Knowledge> knowledges=knowledgeDAO.findAllByDisableFalseAndKnowledgeTypeOrderByClickedDesc(userClickType.getUserClickTypePK().getTypeId());
                RecommendPK recommendPK=new RecommendPK();
                recommendPK.setNormalUser(normalUser);
                if(knowledges.size()>20){
                    for (int i = 0; i <20 ; i++) {
                        recommendPK.getRecommend().add(knowledges.get(i));
                    }
                }else {
                    for (Knowledge knowledge:knowledges) {
                        recommendPK.getRecommend().add(knowledge);
                    }
                }
                recmmendDAO.deleteAll();
                Recommend recommend=new Recommend();
                recommend.setRecommendPK(recommendPK);
                recmmendDAO.save(recommend);
            }
        }catch (Exception e){
            return false;
        }
        return true;
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
    public KnowledgeType findKnowledgeById(String id) {
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


    public boolean bfsDelete(KnowledgeType knowledgeType){
        Queue<KnowledgeType> queue=new LinkedList<>();
        queue.offer(knowledgeType);
        try{
            while (!queue.isEmpty()){
                KnowledgeType tmp=queue.poll();
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
