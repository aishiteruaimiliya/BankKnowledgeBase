/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.Knowledge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeDAO extends JpaRepository<Knowledge,Integer> {
    Knowledge findAllByDisableAndKnowIdOrderByClicked(boolean disable,String knowId);
    Knowledge findByDisableFalseAndKnowId(String knowId);
}
