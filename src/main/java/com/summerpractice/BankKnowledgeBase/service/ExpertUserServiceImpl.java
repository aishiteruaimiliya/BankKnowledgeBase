/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.service;

import com.summerpractice.BankKnowledgeBase.dao.ExpertUserDAO;
import com.summerpractice.BankKnowledgeBase.dao.KnowledgeDAO;
import com.summerpractice.BankKnowledgeBase.dao.KnowledgeTypeDAO;
import com.summerpractice.BankKnowledgeBase.entity.ExpertUser;
import com.summerpractice.BankKnowledgeBase.entity.Knowledge;
import com.summerpractice.BankKnowledgeBase.entity.KnowledgeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class ExpertUserServiceImpl implements ExpertUserServiceI {
    @Autowired
    KnowledgeDAO knowledgeDAO;
    @Autowired
    ExpertUserDAO expertUserDAO;

    @Autowired
    KnowledgeTypeDAO knowledgeTypeDAO;
    @Override
    public boolean deleteKnowledge(Knowledge knowledge) {
        knowledge.setDisable(true);
        knowledge.setStatus("已失效");
        try{
            knowledgeDAO.save(knowledge);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteKnowledge(String knowledgeID) {
        Knowledge knowledge=knowledgeDAO.findByDisableFalseAndKnowId(knowledgeID);
        if(knowledge==null) return false;
        knowledge.setDisable(true);
        knowledge.setStatus("已失效");
        try{
            knowledgeDAO.save(knowledge);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean judgeKnowledge(Knowledge knowledge,String status) {
        knowledge.setStatus(status);
        try{
            knowledgeDAO.save(knowledge);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean batchJudgeKnowledge(List<String> knowledgesIds,String status) {
        try {
            for(String knowledge:knowledgesIds){
                if(!judgeKnowledge(knowledgeDAO.findByDisableFalseAndKnowId(knowledge),status)){
                    return false;
                }
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public Knowledge findKnowledgeById(String id) {
        return knowledgeDAO.findByDisableFalseAndKnowId(id);
    }

    @Override
    public List<Knowledge> findKnowledgeByStatusAndType(String status, String expertId) {
        ExpertUser expertUser=expertUserDAO.findAllByDisableFalseAndId(expertId);
        KnowledgeType knowledgeType=expertUser.getKnowledgeType();
        List<Knowledge> knowledges=new ArrayList<>();
        for(KnowledgeType knowledgeType1:bfs(knowledgeType)){
            knowledges.addAll(knowledgeDAO.findAllByDisableFalseAndKnowledgeTypeAndStatus(knowledgeType1,status));
        }
        return knowledges;
    }

    public List<KnowledgeType> bfs(KnowledgeType knowledgeType){
        Queue<KnowledgeType> queue=new LinkedList<>();
        List<KnowledgeType> ans=new ArrayList<>();
        queue.offer(knowledgeType);
        while(!queue.isEmpty()){
            KnowledgeType tmp=queue.poll();
            ans.add(tmp);
            List<KnowledgeType> knowledgeTypes=knowledgeTypeDAO.findAllByDisableFalseAndPreTypeId(tmp.getTypeid());
            for (KnowledgeType type : knowledgeTypes) {
                if (type.getNextTypeId() != null) {
                    queue.offer(type);
                }
                ans.add(type);
            }
        }
        return ans;
    }
}
