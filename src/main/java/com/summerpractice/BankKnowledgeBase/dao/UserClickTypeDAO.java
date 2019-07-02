/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.KnowledgeType;
import com.summerpractice.BankKnowledgeBase.entity.NormalUser;
import com.summerpractice.BankKnowledgeBase.entity.UserClickType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserClickTypeDAO extends JpaRepository<UserClickType, Integer> {
    List<UserClickType> findAllByDisableFalseAndUserClickTypePK_UserIdOrderByTimes(NormalUser normalUser);
    UserClickType
    findAllByDisableFalseAndUserClickTypePK_UserIdAndUserClickTypePK_TypeId(NormalUser normalUser, KnowledgeType knowledgeType);
}
