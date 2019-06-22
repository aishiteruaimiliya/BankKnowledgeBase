package com.summerpractice.BankKnowledgeBase.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "expert_user")
public class ExpertUser extends User {
    @ManyToOne
    @JoinColumn(name = "dep_id")
    private Department department;
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
