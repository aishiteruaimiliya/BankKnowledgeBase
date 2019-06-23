/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.entity.NormalUser;
import com.summerpractice.BankKnowledgeBase.entity.User;
import com.summerpractice.BankKnowledgeBase.service.NormalUserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
@Controller
@RequestMapping("/user")
public class UserShowPageController {
    /**
     * 去往登录页面
     * @return
     */
    @Autowired
    NormalUserServiceI normalUserServiceI;

    @RequestMapping(value = "/login",method =RequestMethod.GET)
    public String showLogin(){
        return "Login";
    }

    /**
     * 去往个人收藏页面
     * @return
     */
    @GetMapping("/favorite")
    public ModelAndView showMyFavorite(HttpServletRequest request,ModelAndView modelAndView){
        User user= (User) request.getSession().getAttribute("user");
        if(user instanceof NormalUser){
            modelAndView.setViewName("Favorite");
            modelAndView.addObject("knows", normalUserServiceI.getFavorite(user.getId()));
        }
        else{
            modelAndView.setViewName("Login");
        }
        return modelAndView;
    }
    @GetMapping("/showMyKnowledge")
    public String showMyKnowledge(HttpServletRequest request){
        User user= (User) request.getSession().getAttribute("user");
        if(user instanceof NormalUser)
            return "myKnowledge";
        else
            return "Login";
    }
    @RequestMapping(value = "/myHome",method = RequestMethod.GET)
    public ModelAndView showHomePage(HttpServletRequest request,ModelAndView modelAndView){
        NormalUser user= (NormalUser) modelAndView.getModel().get("user");
        if(user==null)
            return new ModelAndView("NOTFOUND");
        request.getSession().setAttribute("user",user);
        modelAndView.setViewName("userHomePage");
        return modelAndView;
    }


}
