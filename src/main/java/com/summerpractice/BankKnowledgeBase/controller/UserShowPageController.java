/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.entity.Knowledge;
import com.summerpractice.BankKnowledgeBase.entity.KnowledgeType;
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
import java.util.List;

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
    public ModelAndView showMyKnowledge(HttpServletRequest request,ModelAndView modelAndView){
        User user= (User) request.getSession().getAttribute("user");
        modelAndView.setViewName("Login");
        if(user instanceof NormalUser){
            modelAndView.setViewName("UserContributionKnowledgePage");
            modelAndView.addObject("res",normalUserServiceI.findKnowledgeByNormalUser(user.getAccount()));
            return modelAndView;
        }
        else
           return modelAndView;
    }
    @RequestMapping(value = "/myHome",method = RequestMethod.GET)
    public ModelAndView showHomePage(HttpServletRequest request,ModelAndView modelAndView){
        NormalUser user= (NormalUser) modelAndView.getModel().get("user");
        if(user==null)
            return new ModelAndView("ERROR");
        request.getSession().setAttribute("user",user);
        modelAndView.setViewName("userHomePage");
        return modelAndView;
    }
    @GetMapping(value = "/ShowAddKnowledge")
    public ModelAndView showAddKnowledgePage(HttpServletRequest request,ModelAndView modelAndView){
        NormalUser user=(NormalUser)request.getSession().getAttribute("user");
        if(user==null){
            modelAndView.setViewName("Login");
            return modelAndView;
        }
        List<KnowledgeType> knowledgeTypes=normalUserServiceI.getLastLayer();
        modelAndView.addObject("types",knowledgeTypes);
        modelAndView.setViewName("addKnowledge");
        Knowledge knowledge=normalUserServiceI.getCaogaoByID(user.getAccount());
        modelAndView.addObject("caogao",knowledge);
        return modelAndView;
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "redirect:/user/login";
    }
    @RequestMapping("/showchangePass")
    public String showChangePass(HttpServletRequest request){
        NormalUser normalUser= (NormalUser) request.getSession().getAttribute("user");
        if(normalUser==null) return "Login";
        return "ChangePassPageNormalUser";
    }
}
