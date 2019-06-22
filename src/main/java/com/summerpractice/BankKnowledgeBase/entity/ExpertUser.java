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
}
