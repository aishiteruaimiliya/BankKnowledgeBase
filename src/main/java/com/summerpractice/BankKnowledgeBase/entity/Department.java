/*
 * author:huangping
 *
 */



package com.summerpractice.BankKnowledgeBase.entity;
//    depId int auto_increment primary key,
//    first varchar(20) not null,
//    second varchar(20),
//    third varchar(20),
//    fourth varchar(20)

import javax.persistence.*;

/***
 * author huangping
 * 机构实体
 * id不必填写
 */
@Entity
@Table(name = "department")
public class Department {
    /***
     * 四个级别的机构
     */
    @Id
    @Column(name = "depId")
    private int depId;
    @Column(name = "first")
    private String first;
    @Column(name = "second")
    private String second;
    @Column(name = "third")
    private String third;
    @Column(name = "fourth")
    private String fourth;
    @Column(name = "disable")
    private boolean disable;

    public Department() {
    }

    public Department(String first, String second, String third) {
        this.setFirst(first);
        this.setSecond(second);
        this.setThird(third);
    }

    /**
     * 一对多的关系
     */
    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public Department(String first, String second, String third, String fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public Department(String first) {
        this.first = first;
    }

    public int getDepId() {
        return depId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }

    public String getFourth() {
        return fourth;
    }

    public void setFourth(String fourth) {
        this.fourth = fourth;
    }

    @Override
    public String toString() {
        return "Department{" +
                "depId=" + depId +
                ", first='" + first + '\'' +
                ", second='" + second + '\'' +
                ", third='" + third + '\'' +
                ", fourth='" + fourth + '\'' +
                '}';
    }

    public Department(int depId, String first, String second, String third, String fourth, boolean disable) {
        this.depId = depId;
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.disable = disable;
    }

}
