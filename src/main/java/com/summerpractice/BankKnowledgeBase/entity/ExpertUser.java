/*
 * author:huangping
 *
 */


package com.summerpractice.BankKnowledgeBase.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "expert_user")
public class ExpertUser extends User {
    /**
     * 多个专家可能属于同一机构
     */
    @ManyToOne
    @JoinColumn(name = "dep_id")
    private Department department;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private KnowledgeType knowledgeType;
    //todo
    public ExpertUser(String name, String account, String password) {
        super(name, account, password);
    }

    public Department getDepartment() {
        return department;
    }
    public ExpertUser(){}
    public void setDepartment(Department department) {
        this.department = department;
    }
    public void setDepartment(String dep_id){
        if(this.department==null) department=new Department();
        this.department.setDepId(dep_id);
    }

    @Override
    public String toString() {
        return "ExpertUser{" +
                "department=" + department +
                ", knowledgeType=" + knowledgeType +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", disable=" + disable +
                '}';
    }

    public KnowledgeType getKnowledgeType() {
        return knowledgeType;
    }

    public void setKnowledgeType(KnowledgeType knowledgeType) {
        this.knowledgeType = knowledgeType;
    }
}
