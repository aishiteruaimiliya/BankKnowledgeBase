/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.NormalUser;
import com.summerpractice.BankKnowledgeBase.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecmmendDAO extends JpaRepository<Recommend,Integer> {
   List<Recommend> findAllByRecommendPK_NormalUser(NormalUser normalUser);
}
