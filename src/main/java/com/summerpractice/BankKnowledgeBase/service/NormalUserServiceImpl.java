/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.service;

import com.summerpractice.BankKnowledgeBase.dao.NormalUserDAO;
import com.summerpractice.BankKnowledgeBase.entity.NormalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NormalUserServiceImpl implements NormalUserServiceI {
    @Autowired
    NormalUserDAO normalUserDAO;
    @Override
    public NormalUser login(String account, String password) {
        NormalUser normalUser=normalUserDAO.findByAccountAndPassword(account,password);
        if(normalUser!=null&&password.equals(normalUser.getPassword())){
            return normalUser;
        }
        return null;
    }
}
