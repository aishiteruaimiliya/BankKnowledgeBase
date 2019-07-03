/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.service;

import com.summerpractice.BankKnowledgeBase.entity.ExpertUser;
import com.summerpractice.BankKnowledgeBase.entity.KnowledgeType;
import java.util.List;
import java.util.Map;

public interface KnowledgeManagerServiceI {

    public int countKnowledgeNum();

    public boolean refreshCounter();

    public boolean refreshRankBoard();

    public boolean addKnowledgeType(KnowledgeType knowledgeType);

    public boolean changeKnowledgeType(KnowledgeType knowledgeType);

    public List<KnowledgeType> getFirstLayer();

    public boolean deleteKnowledgeType(KnowledgeType knowledgeType);

    public boolean deleteKnowledge(String typeId);

    public boolean distribution(ExpertUser expertUser,KnowledgeType knowledgeType);

    public boolean distribution(String expertUserID,String knowledgeTypeID);

    public boolean refreshRecommend();

    public int getTypeNum();

    public Map<KnowledgeType, List<KnowledgeType>> getTwoLayer();

    public KnowledgeType findKnowledgeTypeById(String id);

    List<KnowledgeType> getNextLayer(String typeId);

    List<ExpertUser> getAllExpert();

    KnowledgeType findKnowledgeTypeByTypeID(String id);
}
