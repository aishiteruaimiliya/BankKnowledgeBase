/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.finalController;

import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

public interface ChangePassI {
    public String changePass(@RequestParam(name = "old",required = true)String old,
                             @RequestParam(name = "new",required = true)String  newPass,
                             HttpServletRequest request);
}
