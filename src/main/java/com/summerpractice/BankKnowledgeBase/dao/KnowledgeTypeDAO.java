/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.KnowledgeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeTypeDAO extends JpaRepository<KnowledgeType,Integer> {
    List<KnowledgeType> findAllByPreTypeId(int pretypeid);
    List<KnowledgeType> findAllByPreTypeIdIsNull();
}
