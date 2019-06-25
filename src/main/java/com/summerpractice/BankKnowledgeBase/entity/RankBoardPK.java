/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.entity;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RankBoardPK implements Serializable {
   @OneToOne(targetEntity = Knowledge.class)
    private Knowledge knowledge;

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RankBoardPK)) return false;
        RankBoardPK that = (RankBoardPK) o;
        return Objects.equals(getKnowledge(), that.getKnowledge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKnowledge());
    }

    public RankBoardPK() {
    }
}
