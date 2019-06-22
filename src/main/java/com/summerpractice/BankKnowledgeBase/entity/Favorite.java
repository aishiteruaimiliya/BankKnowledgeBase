package com.summerpractice.BankKnowledgeBase.entity;

//todo 产生的联系
public class Favorite {
    private int userId;
    private int knowId;
    private boolean disable;
    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
    public Favorite(int userId, int knowId) {
        this.userId = userId;
        this.knowId = knowId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getKnowId() {
        return knowId;
    }

    public void setKnowId(int knowId) {
        this.knowId = knowId;
    }
}
