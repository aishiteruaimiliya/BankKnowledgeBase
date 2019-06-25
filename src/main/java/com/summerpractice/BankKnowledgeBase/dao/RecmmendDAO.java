/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.NormalUser;
import com.summerpractice.BankKnowledgeBase.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecmmendDAO extends JpaRepository<Recommend,Integer> {
   Recommend findAllByRecommendPK_NormalUser(NormalUser normalUser);
}
