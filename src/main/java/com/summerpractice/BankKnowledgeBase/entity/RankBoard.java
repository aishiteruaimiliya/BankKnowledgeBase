/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "rank_board")
public class RankBoard implements Serializable {
    @Id
    RankBoardPK rankBoardPK;

    public RankBoardPK getRankBoardPK() {
        return rankBoardPK;
    }

    public void setRankBoardPK(RankBoardPK rankBoardPK) {
        this.rankBoardPK = rankBoardPK;
    }
}
