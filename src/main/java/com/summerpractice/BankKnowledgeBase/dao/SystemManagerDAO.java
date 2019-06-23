/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.SystemManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemManagerDAO extends JpaRepository<SystemManager,Integer> {
    SystemManager findAllByDisableFalseAndAccount(String account);
}
