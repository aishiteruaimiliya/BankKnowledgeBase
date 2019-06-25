/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.service;

import com.summerpractice.BankKnowledgeBase.dao.KnowledgeDAO;
import com.summerpractice.BankKnowledgeBase.entity.Knowledge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ExpertUserServiceImpl implements ExpertUserServiceI {
    @Autowired
    KnowledgeDAO knowledgeDAO;

    @Override
    public boolean deleteKnowledge(Knowledge knowledge) {
        knowledge.setDisable(true);
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
    public boolean batchJudgeKnowledge(List<Knowledge> knowledges,String status) {
        try {
            for(Knowledge knowledge:knowledges){
                if(!judgeKnowledge(knowledge,status)){
                    return false;
                }
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
