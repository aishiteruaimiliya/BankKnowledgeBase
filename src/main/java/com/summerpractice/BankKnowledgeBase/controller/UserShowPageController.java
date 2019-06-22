/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.entity.NormalUser;
import com.summerpractice.BankKnowledgeBase.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller("/user")
public class UserShowPageController {
    /**
     * 去往登录页面
     * @return
     */
    @GetMapping(name = "/login")
    public String showLogin(){
        return "Login";
    }

    /**
     * 去往个人收藏页面
     * @return
     */
    @GetMapping("/favorite")
    public String showMyFavorite(HttpServletRequest request){
        User user= (User) request.getSession().getAttribute("user");
        if(user instanceof NormalUser)
            return "UserFavorite";
        else
            return "Login";
    }
    @GetMapping("/showMyKnowledge")
    public String showMyKnowledge(HttpServletRequest request){
        User user= (User) request.getSession().getAttribute("user");
        if(user instanceof NormalUser)
            return "myKnowledge";
        else
            return "Login";
    }
    @GetMapping("/myHome")
    public String showHomePage(HttpServletRequest request){
        return "userHomePage";
    }


}
