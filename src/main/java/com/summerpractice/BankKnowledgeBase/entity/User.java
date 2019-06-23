/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * author:huangping
 */

@MappedSuperclass
public class User implements Serializable {
    @Id
    @GenericGenerator(strategy = "uuid",name = "myuuid")
    @GeneratedValue(generator = "myuuid")
    @Column(name = "id",length = 50)
    protected String id;
    @Column(name = "name",columnDefinition = "varchar(50) unique not null")
    protected String name;
    @Column(name = "account")
    protected String account;
    @Column(name = "password")
    protected String password;
    @Column(name = "disable",columnDefinition = "tinyint(1) default false")
    protected boolean disable;

    public User() {

    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
    public User(String name, String account, String password) {
        this.name = name;
        this.account = account;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
