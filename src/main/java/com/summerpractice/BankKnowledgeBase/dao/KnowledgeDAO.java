/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.Knowledge;
import com.summerpractice.BankKnowledgeBase.entity.KnowledgeType;
import com.summerpractice.BankKnowledgeBase.entity.NormalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeDAO extends JpaRepository<Knowledge,Integer> {
    Knowledge findAllByDisableAndKnowIdOrderByClicked(boolean disable,String knowId);

    List<Knowledge> findAllByDisableFalseOrderByClickedDesc();
    List<Knowledge> findAllByDisableFalseAndStatusOrderByClickedDesc(String status);
    Knowledge findByDisableFalseAndKnowId(String knowId);
    List<Knowledge> findAllByDisableFalseAndKnowledgeType(KnowledgeType knowledgeType);

    List<Knowledge> findAllByDisableFalseAndStatusAndDigestLikeOrTitleLikeOrDetailLike(String status,String digest,String title,String detail);

    int countAllByDisableFalse();

    List<Knowledge> findAllByDisableFalseAndKnowledgeTypeOrderByClickedDesc(KnowledgeType knowledgeType);

    List<Knowledge> findAllByDisableFalseAndKnowledgeTypeAndStatusOrderByClickedDesc(KnowledgeType knowledgeType,String status);

    List<Knowledge> findAllByDisableFalseAndKnowledgeTypeAndStatus(KnowledgeType knowledgeType,String status);

    List<Knowledge> findAllByDisableFalseAndStatusAndNormalUser(String status,NormalUser normalUser);

    List<Knowledge> findAllByNormalUser(NormalUser normalUser);

    Knowledge findAllByNormalUserAndStatus(NormalUser normalUser,String status);
}
