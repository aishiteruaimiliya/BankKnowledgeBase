/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.service;

import com.summerpractice.BankKnowledgeBase.entity.Knowledge;

import java.util.List;

public interface ExpertUserServiceI {

    public boolean deleteKnowledge(Knowledge knowledge);

    public boolean deleteKnowledge(String  knowledgeID);

    public boolean judgeKnowledge(Knowledge knowledge,String status);

    public boolean batchJudgeKnowledge(List<String> knowledgeIds,String status);

    public Knowledge findKnowledgeById(String id);

    public List<Knowledge> findKnowledgeByStatusAndType(String status,String expertId);
}
