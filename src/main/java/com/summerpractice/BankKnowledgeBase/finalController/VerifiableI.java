/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.finalController;

import com.summerpractice.BankKnowledgeBase.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface VerifiableI {
    public User verify(HttpServletRequest request);
}
