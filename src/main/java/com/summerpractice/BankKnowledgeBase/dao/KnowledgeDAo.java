/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.Knowledge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeDAo extends JpaRepository<Knowledge,Integer> {
}
