/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.Department;
import com.summerpractice.BankKnowledgeBase.entity.ExpertUser;
import com.summerpractice.BankKnowledgeBase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpertUserDAO extends JpaRepository<ExpertUser,Integer> {
    ExpertUser findAllByDisableFalseAndAccount(String account);
    List<User> findAllByDisableFalseAndDepartment(Department department);
}
