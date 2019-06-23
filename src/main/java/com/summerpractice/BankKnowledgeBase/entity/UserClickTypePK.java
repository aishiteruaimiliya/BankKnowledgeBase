/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
@Embeddable
public class UserClickTypePK implements Serializable {
    @ManyToOne(cascade= CascadeType.ALL,fetch= FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private NormalUser userId;
    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private KnowledgeType typeId;

    public UserClickTypePK() {
    }

    public UserClickTypePK(NormalUser userId, KnowledgeType typeId) {
        this.userId = userId;
        this.typeId = typeId;
    }

    public NormalUser getUserId() {
        return userId;
    }

    public void setUserId(NormalUser userId) {
        this.userId = userId;
    }

    public KnowledgeType getTypeId() {
        return typeId;
    }

    public void setTypeId(KnowledgeType typeId) {
        this.typeId = typeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserClickTypePK)) return false;
        UserClickTypePK that = (UserClickTypePK) o;
        return Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getTypeId(), that.getTypeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getTypeId());
    }
}
