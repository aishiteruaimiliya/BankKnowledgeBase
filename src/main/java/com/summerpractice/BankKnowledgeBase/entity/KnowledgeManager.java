/*
 * author:huangping
 *
 */

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
@Table(name = "knowledge_manager")
public class KnowledgeManager extends User {

    /**
     * 一个部门包含多个知识管理员
     */
    @ManyToOne
    @JoinColumn(name = "dep_id")
    private Department department;
    public KnowledgeManager(String name, String account, String password, int dep_id) {
        super(name, account, password);
    }

    @Override
    public String toString() {
        return "KnowledgeManager{" +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    public KnowledgeManager(){}
}
