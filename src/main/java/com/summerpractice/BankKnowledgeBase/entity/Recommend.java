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
@Table(name = "recommend")
public class Recommend implements Serializable {
    @Id
    private RecommendPK recommendPK;

    public RecommendPK getRecommendPK() {
        return recommendPK;
    }

    public void setRecommendPK(RecommendPK recommendPK) {
        this.recommendPK = recommendPK;
    }
}
