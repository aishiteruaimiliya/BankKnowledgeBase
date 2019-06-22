/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.entity;
//create table knowledgetype(
////        typeid int auto_increment primary key,
////        typecontent varchar(20) not null
////        );

import javax.persistence.*;

/**
 * 知识类型
 */
@Entity
@Table(name = "knowledge_type")
public class KnowledgeType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "type_id")
    private int typeid;
    @Column(name = "typecontent")
    private String typecontent;
    //node  content childid
    @Column(name = "pre_column_id")
    private int preTypeId;
    @Column(name = "next_type_id")
    private int nextTypeId;
    @Column(name = "disable",columnDefinition = "tinyint(1) default false")
    private boolean disable;

    public int getPreTypeId() {
        return preTypeId;
    }

    public void setPreTypeId(int preTypeId) {
        this.preTypeId = preTypeId;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getTypecontent() {
        return typecontent;
    }

    public void setTypecontent(String typecontent) {
        this.typecontent = typecontent;
    }

    public int getNextTypeId() {
        return nextTypeId;
    }

    public void setNextTypeId(int nextTypeId) {
        this.nextTypeId = nextTypeId;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public KnowledgeType(String typecontent, int nextTypeId, boolean disable) {
        this.typecontent = typecontent;
        this.nextTypeId = nextTypeId;
        this.disable = disable;
    }

    public KnowledgeType() {
    }
}
