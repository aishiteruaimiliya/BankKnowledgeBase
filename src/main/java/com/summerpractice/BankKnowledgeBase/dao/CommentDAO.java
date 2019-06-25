/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.Comment;
import com.summerpractice.BankKnowledgeBase.entity.Knowledge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDAO extends JpaRepository<Comment,Integer> {

    List<Comment> findAllByDisableFalseAndKnowledge(Knowledge knowledge);

}
