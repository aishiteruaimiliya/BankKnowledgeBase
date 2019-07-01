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

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 知识类型
 */
@Entity
@Table(name = "knowledge_type")
public class KnowledgeType {
    @Id
    @GenericGenerator(strategy = "uuid",name = "myuuid")
    @GeneratedValue(generator = "myuuid")
    @Column(name = "type_id",length = 50)
    private String typeid;

    @Column(name = "typecontent")
    private String typecontent;
    //node  content childid

    @Column(name = "pre_column_id")
    private String preTypeId;

    @Column(name = "next_type_id")
    private String nextTypeId;
    @Column(name = "disable",columnDefinition = "tinyint(1) default false")
    private boolean disable;

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getTypecontent() {
        return typecontent;
    }

    public void setTypecontent(String typecontent) {
        this.typecontent = typecontent;
    }

    public String getPreTypeId() {
        return preTypeId;
    }

    public void setPreTypeId(String preTypeId) {
        this.preTypeId = preTypeId;
    }

    public String getNextTypeId() {
        return nextTypeId;
    }

    public void setNextTypeId(String nextTypeId) {
        this.nextTypeId = nextTypeId;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public KnowledgeType() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"typeid\":\"")
                .append(typeid).append('\"');
        sb.append(",\"typecontent\":\"")
                .append(typecontent).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
