/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.UserClickType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserClickTypeDAO extends JpaRepository<UserClickType, List<Integer>> {
}
