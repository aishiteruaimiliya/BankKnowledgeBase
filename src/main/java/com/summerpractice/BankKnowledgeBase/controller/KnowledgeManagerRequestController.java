/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.entity.ExpertUser;
import com.summerpractice.BankKnowledgeBase.entity.KnowledgeManager;
import com.summerpractice.BankKnowledgeBase.entity.KnowledgeType;
import com.summerpractice.BankKnowledgeBase.service.CommonServiceI;
import com.summerpractice.BankKnowledgeBase.service.KnowledgeManagerServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/knowledgeManagerRequest")
public class KnowledgeManagerRequestController {
    @Autowired
    KnowledgeManagerServiceI knowledgeManagerServiceI;
    @Autowired
    CommonServiceI commonServiceI;
    @RequestMapping("/nextLayer")
    public ModelAndView showNextLayer(@RequestParam(name = "typeId",required = true)String typeId,
                                      HttpServletRequest request){
        if ((KnowledgeManager)request.getSession().getAttribute("user") == null){
            return new ModelAndView("Login");
        }
        List<KnowledgeType> knowledgeTypes=knowledgeManagerServiceI.getNextLayer(typeId);
        ModelAndView modelAndView=
                new ModelAndView("KnowledgeTypeList","types",knowledgeTypes);
        modelAndView.addObject("preId",typeId);
        return modelAndView;
    }
    @RequestMapping("/addLayer")
    public ModelAndView addLayer(@RequestParam(name = "preId",required = false)String preId,
                                 @RequestParam(name = "typecontent",required = true)String typecontent,
                                 HttpServletRequest request){
        if ((KnowledgeManager)request.getSession().getAttribute("user") == null){
            return new ModelAndView("Login");
        }
        ModelAndView modelAndView=new ModelAndView("ResultPage");
        KnowledgeType knowledgeType=new KnowledgeType();
        knowledgeType.setTypecontent(typecontent);
        if(preId!=null&&!preId.equals("")){
            knowledgeType.setPreTypeId(preId);
            KnowledgeType knowledgeType1=knowledgeManagerServiceI.findKnowledgeTypeById(preId);
            knowledgeType1.setNextTypeId("NOTLEAF");
            knowledgeManagerServiceI.changeKnowledgeType(knowledgeType1);
        }
        if(knowledgeManagerServiceI.addKnowledgeType(knowledgeType)){
            modelAndView.addObject("msg","添加知识维度成功");
            return modelAndView;
        }
        modelAndView.addObject("msg","添加知识维度失败");
        return modelAndView;
    }

    @RequestMapping("/deleteKnowledgeType")
    public ModelAndView deleteKnowledgeType(@RequestParam(name = "typeId",required = true)String typeid,
                                            HttpServletRequest request){
        if ((KnowledgeManager)request.getSession().getAttribute("user") == null){
            return new ModelAndView("Login");
        }
        ModelAndView modelAndView=new ModelAndView("ResultPage");
        KnowledgeType knowledgeType=knowledgeManagerServiceI.findKnowledgeTypeByTypeID(typeid);
        if(knowledgeManagerServiceI.deleteKnowledgeType(knowledgeType)){
            modelAndView.addObject("msg","删除知识维度成功");
            return modelAndView;
        }
        modelAndView.addObject("msg","删除知识维度失败");
        return modelAndView;

    }

    @RequestMapping("/showDistributePage")
    public ModelAndView showdistibute(HttpServletRequest request){
        if ((KnowledgeManager)request.getSession().getAttribute("user") == null){
            return new ModelAndView("Login");
        }
        List<ExpertUser> expertUsers=knowledgeManagerServiceI.getAllExpert();
        List<KnowledgeType> knowledgeTypes=knowledgeManagerServiceI.getFirstLayer();
        ModelAndView modelAndView=new ModelAndView("KnowledgeTypeDistributePage");
        modelAndView.addObject("types",knowledgeTypes);
        modelAndView.addObject("experts",expertUsers);
        return modelAndView;
    }
    @RequestMapping("/showChangePass")
    public ModelAndView modelAndView(HttpServletRequest request){
        if ((KnowledgeManager)request.getSession().getAttribute("user") == null){
            return new ModelAndView("Login");
        }
        return new ModelAndView("ChangePasswordPageKnowledeManager");
    }
}
