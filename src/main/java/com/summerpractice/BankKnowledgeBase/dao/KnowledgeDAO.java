/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.Knowledge;
import com.summerpractice.BankKnowledgeBase.entity.KnowledgeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeDAO extends JpaRepository<Knowledge,Integer> {
    Knowledge findAllByDisableAndKnowIdOrderByClicked(boolean disable,String knowId);

    List<Knowledge> findAllByDisableFalseOrderByClickedDesc();
    Knowledge findByDisableFalseAndKnowId(String knowId);
    List<Knowledge> findAllByDisableFalseAndKnowledgeType(KnowledgeType knowledgeType);

    List<Knowledge> findAllByDisableFalseAndDigestLikeOrTitleLikeOrDetailLike(String digest,String title,String detail);

    int countAllByDisableFalse();

    List<Knowledge> findAllByDisableFalseAndKnowledgeTypeOrderByClickedDesc(KnowledgeType knowledgeType);
}
