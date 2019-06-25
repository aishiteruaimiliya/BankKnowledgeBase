/*
 * author:huangping
 *
 */



package com.summerpractice.BankKnowledgeBase.entity;

//todo 产生的联系
public class Favorite {
    private String userId;
    private String knowId;
    private boolean disable;
    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
    public Favorite(String userId, String knowId) {
        this.userId = userId;
        this.knowId = knowId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKnowId() {
        return knowId;
    }

    public void setKnowId(String knowId) {
        this.knowId = knowId;
    }


}
