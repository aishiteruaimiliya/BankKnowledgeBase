/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.entity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RecommendPK implements Serializable {
    @OneToOne(targetEntity = NormalUser.class)
    @JoinColumn(name = "normal_user_id")
    private NormalUser normalUser;
    @OneToOne(targetEntity = Knowledge.class)
    @JoinColumn(name = "know_id")
    private Knowledge knowledge;

    public NormalUser getNormalUser() {
        return normalUser;
    }

    public void setNormalUser(NormalUser normalUser) {
        this.normalUser = normalUser;
    }



    public RecommendPK() {
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecommendPK)) return false;
        RecommendPK that = (RecommendPK) o;
        return Objects.equals(getNormalUser(), that.getNormalUser()) &&
                Objects.equals(getKnowledge(), that.getKnowledge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNormalUser(), getKnowledge());
    }
}
