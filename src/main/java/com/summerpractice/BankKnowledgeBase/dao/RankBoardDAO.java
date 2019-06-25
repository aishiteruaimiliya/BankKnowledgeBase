/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.RankBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankBoardDAO extends JpaRepository<RankBoard, Integer> {
    List<RankBoard> findAllByRankBoardPKNotNull();
}
