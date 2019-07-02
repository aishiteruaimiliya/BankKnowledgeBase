/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.KnowledgeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeTypeDAO extends JpaRepository<KnowledgeType,Integer> {
    List<KnowledgeType> findAllByPreTypeId(String pretypeid);
    List<KnowledgeType> findAllByPreTypeIdIsNull();
    KnowledgeType findByDisableFalseAndTypeid(String typeid);
    List<KnowledgeType> findAllByDisableFalseAndPreTypeId(String typeid);
    List<KnowledgeType> findAllByDisableFalseAndNextTypeIdIsNull();
    KnowledgeType findAllByDisableFalseAndTypeid(String typeId);
    int countAllByDisableFalseAndPreTypeIdIsNull();
    List<KnowledgeType> getAllByDisableFalse();
    List<KnowledgeType> findAllByDisableFalseAndPreTypeIdIsNull();
}
