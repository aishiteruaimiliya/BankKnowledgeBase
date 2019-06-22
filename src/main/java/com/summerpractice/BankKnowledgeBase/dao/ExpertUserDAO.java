/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.ExpertUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpertUserDAO extends JpaRepository<ExpertUser,Integer> {
}
