/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.KnowledgeManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeManagerDAO extends JpaRepository<KnowledgeManager,Integer> {
}
