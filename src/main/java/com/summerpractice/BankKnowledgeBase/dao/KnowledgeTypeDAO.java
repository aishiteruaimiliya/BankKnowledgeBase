/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.KnowledgeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeTypeDAO extends JpaRepository<KnowledgeType,Integer> {
}
