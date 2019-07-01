/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.Department;
import com.summerpractice.BankKnowledgeBase.entity.NormalUser;
import com.summerpractice.BankKnowledgeBase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NormalUserDAO extends JpaRepository<NormalUser,Integer> {
    NormalUser findByAccountAndPasswordAndDisable(String account,String password,boolean disable);
    NormalUser findByDisableFalseAndId(String id);
    NormalUser findAllByDisableFalseAndAccount(String account);
    List<User> findAllByDisableFalseAndDepartment(Department department);
    List<NormalUser> findAllByDisableFalse();
    NormalUser findAllByAccount(String account);
    List<NormalUser> findAllByAccountLikeOrNameLike(String account,String name);

}
