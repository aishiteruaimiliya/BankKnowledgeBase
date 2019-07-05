/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.finalController;

import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

public interface LoginI {
    public String doLogin(@RequestParam(name = "account",required = true)String account,
                          @RequestParam(name = "password",required = true)String password,
                          HttpServletRequest request);
}
