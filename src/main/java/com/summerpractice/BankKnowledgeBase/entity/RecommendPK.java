/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Embeddable
public class RecommendPK implements Serializable {
    @OneToOne(targetEntity = NormalUser.class)
    @JoinColumn(name = "normal_user_id")
    private NormalUser normalUser;
    @ManyToOne(targetEntity = Knowledge.class)
    @JoinColumn(name = "know_id")
    private List<Knowledge> recommend =new ArrayList<>();

    public NormalUser getNormalUser() {
        return normalUser;
    }

    public void setNormalUser(NormalUser normalUser) {
        this.normalUser = normalUser;
    }

    public List<Knowledge> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<Knowledge> recommend) {
        this.recommend = recommend;
    }

    public RecommendPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecommendPK)) return false;
        RecommendPK that = (RecommendPK) o;
        return Objects.equals(getNormalUser(), that.getNormalUser()) &&
                Objects.equals(getRecommend(), that.getRecommend());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNormalUser(), getRecommend());
    }
}
