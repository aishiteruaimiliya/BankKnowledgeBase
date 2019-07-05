/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.entity.ExpertUser;
import com.summerpractice.BankKnowledgeBase.entity.Knowledge;
import com.summerpractice.BankKnowledgeBase.service.ExpertUserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/expertUser")
public class ExpertUserController {
    @Autowired
    ExpertUserServiceI expertUserServiceI;

    @RequestMapping("/showKnowledge")
   public ModelAndView showUnjudge(ModelAndView modelAndView, HttpServletRequest request){
        ExpertUser expertUser= (ExpertUser) request.getSession().getAttribute("user");
        if (expertUser == null){
            modelAndView.setViewName("Login");
            return modelAndView;
        }
        List<Knowledge> knowledges=expertUserServiceI.findKnowledgeByStatusAndType("未审批",expertUser.getId());
        modelAndView.setViewName("knowledgePage");
        modelAndView.addObject("knows",knowledges);
        return modelAndView;
   }
    @RequestMapping("/showJudged")
   public ModelAndView showJudged(ModelAndView modelAndView,HttpServletRequest request){
       ExpertUser expertUser= (ExpertUser) request.getSession().getAttribute("user");
        if (expertUser == null){
            modelAndView.setViewName("Login");
            return modelAndView;
        }
       List<Knowledge> knowledges=expertUserServiceI.findKnowledgeByStatusAndType("通过",expertUser.getId());
       modelAndView.setViewName("knowledgePage");
       modelAndView.addObject("knows",knowledges);
       return modelAndView;
   }
    @RequestMapping("/showDetail")
    public ModelAndView showDetail(@RequestParam(name = "know_id",required = true)String knowId, HttpServletRequest request, ModelAndView modelAndView){
        ExpertUser expertUser= (ExpertUser) request.getSession().getAttribute("user");
        if (expertUser == null){
            modelAndView.setViewName("Login");
            return modelAndView;
        }
        Knowledge knowledge=expertUserServiceI.findKnowledgeById(knowId);
        modelAndView.setViewName("knowledgeDetail");
        modelAndView.addObject("knowledge",knowledge);
        return modelAndView;
    }
    @RequestMapping("/showchangePass")
    public String showChangePass(HttpServletRequest request){
        ExpertUser expertUser= (ExpertUser) request.getSession().getAttribute("user");
       if(expertUser==null) return "Login";
       return "ChangePasswordPage";
    }

}
