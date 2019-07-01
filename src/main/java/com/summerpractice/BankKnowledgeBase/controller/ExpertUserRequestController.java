/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.entity.ExpertUser;
import com.summerpractice.BankKnowledgeBase.entity.Knowledge;
import com.summerpractice.BankKnowledgeBase.service.CommonServiceI;
import com.summerpractice.BankKnowledgeBase.service.ExpertUserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/expertUserRequest")
public class ExpertUserRequestController {
    @Autowired
    ExpertUserServiceI expertUserServiceI;
    @Autowired
    CommonServiceI commonServiceI;
    @ResponseBody
    @RequestMapping("/getKD")
    public String getKnowledgeDetail(@RequestParam(name = "knowId",required = true) String know_id){
        Knowledge knowledge=expertUserServiceI.findKnowledgeById(know_id);
        return knowledge.toString();
    }
    @ResponseBody
    @RequestMapping("/getToJudge")
    public String getNeedJudge(HttpServletRequest request){
        ExpertUser expertUser= (ExpertUser) request.getSession().getAttribute("user");
//        if(expertUser==null)  return "未登录";
        return expertUserServiceI.findKnowledgeByStatusAndType("未审批",expertUser.getId()).toString();
    }
    @ResponseBody
    @RequestMapping("/getJudged")
    public String getJudged(HttpServletRequest request){
        ExpertUser expertUser= (ExpertUser) request.getSession().getAttribute("user");
//        if(expertUser==null)  return "未登录";
        return expertUserServiceI.findKnowledgeByStatusAndType("已审批",expertUser.getId()).toString();
    }
    @ResponseBody
    @RequestMapping("/rejectK")
    public String reject(@RequestParam(name = "know_id") String knowId,HttpServletRequest request){
        ExpertUser expertUser= (ExpertUser) request.getSession().getAttribute("user");
//        if(expertUser==null)  return "未登录";
        return expertUserServiceI.judgeKnowledge(expertUserServiceI.findKnowledgeById(knowId),"驳回")?"success":"failed";
    }
    @ResponseBody
    @RequestMapping("/accept.do")
    public String accept(@RequestParam(name = "know_id") String knowId,HttpServletRequest request){
        ExpertUser expertUser= (ExpertUser) request.getSession().getAttribute("user");
//        if(expertUser==null)  return "未登录";
        return expertUserServiceI.judgeKnowledge(expertUserServiceI.findKnowledgeById(knowId),"通过")?"success":"failed";
    }
    @ResponseBody
    @RequestMapping("/batch.do")
    public String batch(HttpServletRequest request){
        List<String> params=null;
        if(request.getMethod().equals("POST")){
           params=Arrays.asList(request.getParameterValues("select"));
        }
        return expertUserServiceI.batchJudgeKnowledge(params,"已审批")?"success":"failed";
    }
    @ResponseBody
    @RequestMapping("/reject.do")
    public String batchReject(HttpServletRequest request){
        List<String> params=null;
        if(request.getMethod().equals("POST")){
            params=Arrays.asList(request.getParameterValues("select"));
        }
        return expertUserServiceI.batchJudgeKnowledge(params,"已驳回")?"success":"failed";
    }
    @ResponseBody
    @RequestMapping("/delete")
    public String delete(@RequestParam(name = "know_id",required = true)String knowId){
        return expertUserServiceI.deleteKnowledge(knowId)?"success":"failed";
    }
    @RequestMapping("/changePass")
    public ModelAndView changePassword(@RequestParam(name = "old",required = true)String old,
                                       @RequestParam(name = "new",required = true)String  newPass,
                                       HttpServletRequest  request,
                                       ModelAndView modelAndView){
        ExpertUser expertUser=verify(request);
        modelAndView.setViewName("ChangePasswordPage");
        if(commonServiceI.changePassword(expertUser.getAccount(),old,newPass)){
            modelAndView.addObject("msg","修改成功");
        }else {
            modelAndView.addObject("msg","修改失败");
        }
        return modelAndView;
    }

    public ExpertUser verify(HttpServletRequest request){
        return  (ExpertUser) request.getSession().getAttribute("user");
    }
}
