package com.summerpractice.BankKnowledgeBase.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "knowledge_manager")
public class KnowledgeManager extends User {
    //todo
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
