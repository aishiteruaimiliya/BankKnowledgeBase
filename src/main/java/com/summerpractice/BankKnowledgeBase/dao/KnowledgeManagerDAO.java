/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.Department;
import com.summerpractice.BankKnowledgeBase.entity.KnowledgeManager;
import com.summerpractice.BankKnowledgeBase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeManagerDAO extends JpaRepository<KnowledgeManager,Integer> {
    public KnowledgeManager findAllByDisableFalseAndAccount(String account);
    List<User> findAllByDisableFalseAndDepartment(Department department);
    List<KnowledgeManager> findAllByAccountLikeOrNameLike(String account,String name);
    KnowledgeManager findAllByAccount(String account);
}
