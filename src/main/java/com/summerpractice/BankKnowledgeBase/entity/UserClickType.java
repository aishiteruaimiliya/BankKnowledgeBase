package com.summerpractice.BankKnowledgeBase.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;
@Entity
@Table(name = "user_click_type")
public class UserClickType implements Serializable {
    @Id
    @Column(name = "userId")
    private int userId;
    //用户对应不同类型的知识的点击次数
    @Column(name = "disable")
    private boolean disable;
    @Id
    @Column(name = "type_id")
    private int typeid;
    @Column(name = "times")
    private int times;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public UserClickType(int userId, boolean disable, int typeid, int times) {
        this.userId = userId;
        this.disable = disable;
        this.typeid = typeid;
        this.times = times;
    }

    public UserClickType() {
    }
}
