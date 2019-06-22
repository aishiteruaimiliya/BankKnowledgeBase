/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.NormalUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalUserDAO extends JpaRepository<NormalUser,Integer> {
    NormalUser findByAccountAndPasswordAndDisable(String account,String password,boolean disable);
    NormalUser findByDisableFalseAndId(int id);

}
