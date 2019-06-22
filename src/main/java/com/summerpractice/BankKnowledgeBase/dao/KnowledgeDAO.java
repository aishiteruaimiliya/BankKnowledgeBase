/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.Knowledge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeDAO extends JpaRepository<Knowledge,Integer> {
    Knowledge findAllByDisableAndKnowIdOrderByClicked(boolean disable,int knowId);
    List<Knowledge> findAllByDisableAndTypeId(boolean disable,int typeId);
    Knowledge findAllByDisableFalseAndTypeId(int type_id);
    Knowledge findByDisableFalseAndKnowId(int knowId);
}
