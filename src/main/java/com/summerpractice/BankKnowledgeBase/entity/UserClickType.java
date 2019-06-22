/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.entity;



import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "user_click_type")
public class UserClickType implements Serializable {
    @Id
    private UserClickTypePK userClickTypePK;

    public UserClickTypePK getUserClickTypePK() {
        return userClickTypePK;
    }

    public void setUserClickTypePK(UserClickTypePK userClickTypePK) {
        this.userClickTypePK = userClickTypePK;
    }

    //用户对应不同类型的知识的点击次数
    @Column(name = "disable")
    private boolean disable;

    @Column(name = "times",columnDefinition = "int default 0")
    private int times;


    public UserClickType() {
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
