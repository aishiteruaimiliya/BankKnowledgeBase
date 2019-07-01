/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.entity.KnowledgeManager;
import com.summerpractice.BankKnowledgeBase.entity.KnowledgeType;
import com.summerpractice.BankKnowledgeBase.service.CommonServiceI;
import com.summerpractice.BankKnowledgeBase.service.KnowledgeManagerServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/knowledgeManager")
public class KnowledgeManagerController {
    @Autowired
    KnowledgeManagerServiceI knowledgeManagerServiceI;
    @Autowired
    CommonServiceI commonServiceI;
    //todo 完成以下控制器
    public void getKnowledgeType(){}
    @ResponseBody
    @RequestMapping("/addKnowledgeType")
    public String addKnowledgeType(@RequestParam(name = "preid")String preID,
                                 @RequestParam(name = "typecontent")String typecontent){
        KnowledgeType knowledgeType=new KnowledgeType();
        knowledgeType.setTypecontent(typecontent);
        knowledgeType.setPreTypeId(preID);
        return knowledgeManagerServiceI.addKnowledgeType(knowledgeType)?"success":"failed";

    }

    @RequestMapping("/changePass")
    public ModelAndView changePassword(@RequestParam(name = "old",required = true)String old,
                                       @RequestParam(name = "new",required = true)String  newPass,
                                       HttpServletRequest request,
                                       ModelAndView modelAndView){
        KnowledgeManager knowledgeManager=verify(request);
        modelAndView.setViewName("ChangePasswordPage");
        if(commonServiceI.changePassword(knowledgeManager.getAccount(),old,newPass)){
            modelAndView.addObject("msg","修改成功");
        }else {
            modelAndView.addObject("msg","修改失败");
        }
        return modelAndView;
    }

    private KnowledgeManager verify(HttpServletRequest request) {
        return (KnowledgeManager) request.getSession().getAttribute("user");
    }

    @ResponseBody
    @RequestMapping("/changeKnowledgeType")
    public String changeKnowledgeType(@RequestParam(name = "typeid",required = true)String typeId,
                                      @RequestParam(name = "typecontent",required = true)String typecontent){
        KnowledgeType knowledgeType=knowledgeManagerServiceI.findKnowledgeById(typeId);
        if(knowledgeType==null) return "failed";
        knowledgeType.setTypecontent(typecontent);
        return knowledgeManagerServiceI.changeKnowledgeType(knowledgeType)?"success":"failed";
    }
    @ResponseBody
    @RequestMapping("/delete")
    public String deleteKnowledgeType(@RequestParam(name = "typeid",required = true)String typeId){
        return knowledgeManagerServiceI.deleteKnowledgeType(knowledgeManagerServiceI.findKnowledgeById(typeId))?"success":"failed";
    }
    @ResponseBody
    @RequestMapping("/distribute")
    public String distribution(@RequestParam(name = "typeId",required = true)String typeid,
                               @RequestParam(name = "expertId",required = true)String expertId){
        return knowledgeManagerServiceI.distribution(expertId,typeid)?"success":"failed";
    }
    @ResponseBody
    @RequestMapping("/getCount")
    public int getCount(){
        return knowledgeManagerServiceI.countKnowledgeNum();
    }
    @ResponseBody
    @RequestMapping("/refreshRe")
    public String refreshReconmmend(){
        return knowledgeManagerServiceI.refreshRecommend()?"success":"failed";
    }
    @ResponseBody
    @RequestMapping("/refreshRB")
    public String refreshRankBoard(){
        return knowledgeManagerServiceI.refreshRankBoard()?"success":"failed";
    }

    @RequestMapping("/showKnlwdgeType")
    public ModelAndView showKnowledgeType(){
        return new ModelAndView("KnowledgeTypeList","types",knowledgeManagerServiceI.getFirstLayer());
    }
}
