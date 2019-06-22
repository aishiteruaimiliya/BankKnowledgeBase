/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * 系统管理员实体
 */
@Entity
@Table(name = "system_maneger")
public class SystemManager extends User {
    public SystemManager(String name, String account, String password) {
        super(name, account, password);
    }
    public SystemManager(){}
}
