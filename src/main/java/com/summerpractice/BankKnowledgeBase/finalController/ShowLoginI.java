/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.finalController;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public interface ShowLoginI {

    public ModelAndView showLogin(HttpServletRequest request);

    public ModelAndView showHomePage(HttpServletRequest request);
}
