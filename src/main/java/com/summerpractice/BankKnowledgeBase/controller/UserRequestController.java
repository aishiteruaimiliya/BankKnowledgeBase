/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.entity.*;
import com.summerpractice.BankKnowledgeBase.service.CommonServiceI;
import com.summerpractice.BankKnowledgeBase.service.DepartmentServiceI;
import com.summerpractice.BankKnowledgeBase.service.KnowledgeManagerServiceI;
import com.summerpractice.BankKnowledgeBase.service.NormalUserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userRequest")
public class UserRequestController {
    @Autowired
    DepartmentServiceI departmentServiceI;
    @Autowired
    NormalUserServiceI normalUserServiceI;
    @Autowired
    KnowledgeManagerServiceI knowledgeManagerServiceI;

    @Autowired
    CommonServiceI commonServiceI;
    @PostMapping("/search")
    public ModelAndView search(@RequestParam(name = "keyword",required = true)String keyword,HttpServletRequest request,ModelAndView modelAndView){
      modelAndView.setViewName("Login");
      NormalUser normalUser=verifyUser(request);
      if(normalUser==null) return modelAndView;
      else{
          modelAndView.addObject("res",normalUserServiceI.searchByKeyWord(keyword));
          modelAndView.setViewName("KnowledgeShowPage");
          return modelAndView;
      }
    }
    @ResponseBody
    @PostMapping("/addKnowledge")
    public String addKnowLedge(@RequestParam(name = "title",required = true)String title,
                               @RequestParam(name = "digest",required = true)String digest,
                               @RequestParam(name = "typeid",required = true)String typeId,
                               @RequestParam(name = "detail",required = true)String detail,
                               @RequestParam(name = "knowId",required = false)String knowId,
                               HttpServletRequest request){
                NormalUser normalUser=verifyUser(request);
                Knowledge knowledge=null;
                if(normalUser==null) return "Login";
                if(knowId==null||knowId.equals(""))
                    knowledge = new Knowledge();
                else {
                    knowledge=normalUserServiceI.getCaogaoByID(normalUser.getAccount());
                }
                 if((title.equals("")||detail.equals("")||typeId.equals("")||digest.equals(""))){
                    return "信息不能为空";
                }
                knowledge.setStatus("未审批");
                knowledge.setDigest(digest);
                knowledge.setDetail(detail);
                knowledge.setTitle(title);
                knowledge.setTypeId(normalUserServiceI.findKnowlegeTypeById(typeId));
                knowledge.setNormalUser(normalUser);
                try{
                    knowledge.setExpertUser(normalUserServiceI.findExpertUserByTypeId(typeId));
                }catch (Exception e) {
                    return "灭有对应知识维度的专家";
                }
                if(normalUserServiceI.addKnowledge(knowledge)){
                    return "success";
                }else {
                    return "failed";
                }
    }

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    public String comment(@RequestParam(name = "knowId",required = true)String know_id,
                          @RequestParam(name = "content",required = true)String content,
                          @RequestParam(name = "star",required = true)int star,
                          HttpServletRequest request){
        NormalUser normalUser=verifyUser(request);
        if(verifyUser(request)==null) return "未登录";
        Comment comment=new Comment();
        comment.setContent(content);
        comment.setNormaluser(normalUser);
        comment.setStar(star);
        if(normalUserServiceI.addComment(comment,know_id)){
            return "success";
        }else {
            return "failed";
        }
    }
    @RequestMapping("/getMyKnowledgeByType")
    public ModelAndView getMyknowlegeByType(ModelAndView modelAndView,HttpServletRequest request,@RequestParam(name = "typeid",required = true)String typeid){
       NormalUser normalUser=verifyUser(request);
       modelAndView.setViewName("Login");
       if(normalUser==null){
           return modelAndView;
       }else {
           List<Knowledge> knowledges= normalUserServiceI.getKnowledgeByType(normalUserServiceI.findKnowlegeTypeById(typeid));
           modelAndView.addObject("res",knowledges);
           modelAndView.setViewName("resultPage");
           return modelAndView;
       }
    }
    @RequestMapping("/addMyfavorite")
    public ModelAndView addFavorite(@RequestParam(name = "know_id",required = true)String know_id,HttpServletRequest request) {
        //todo 添加到数据库
        NormalUser normalUser=verifyUser(request);
        ModelAndView modelAndView=new ModelAndView("Login");
        if(normalUser==null) return modelAndView;
        if(normalUserServiceI.addFavorite(normalUser,know_id)){
            modelAndView.setViewName("Favorite");
            modelAndView.addObject("knows",normalUser.getFavorite());
            return modelAndView;
        }
        return modelAndView;
    }
    @RequestMapping("/showFavoriteDetail")
    public ModelAndView showDetail(HttpServletRequest request,@RequestParam(name = "knowId",required = true)String knowID,ModelAndView modelAndView){
        //todo 展示用户的收藏
        NormalUser normalUser=verifyUser(request);
        if(normalUser==null) return new ModelAndView("Login","msg","用户未登录");
        modelAndView.setViewName("detail");
        modelAndView.addObject("res",normalUserServiceI.deleteFavorite(normalUser,knowID));
        return modelAndView;
    }
    @RequestMapping("/deleteFavorite")
    public ModelAndView deleteFavorite(HttpServletRequest request,@RequestParam(name = "knowId",required = true)String knowID,ModelAndView modelAndView){
        //todo 删除用户的
        NormalUser normalUser=verifyUser(request);
        if(normalUser==null) return new ModelAndView("Login","msg","用户未登录");
        else {
            if(normalUserServiceI.deleteFavorite(normalUser,knowID)){
                return new ModelAndView("ResultPage","msg","成功");
            }else {
                return new ModelAndView("ResultPage","msg","错误");
            }
        }
    }
    @RequestMapping("/getRecommend")
    @ResponseBody
    public String getRecommend(HttpServletRequest request){
        //todo 获取对该用户推荐知识
        NormalUser normalUser=verifyUser(request);
        if(normalUser==null) return "failed";
        else {
            return normalUserServiceI.getRecommend(normalUser).toString();
        }
    }
    @RequestMapping("/doLogin")
    public ModelAndView login(@RequestParam(name = "account",required = false) String account,
                              @RequestParam(name = "password",required = false) String password,
                              HttpServletRequest request,ModelAndView modelAndView){
        User usr= (User) request.getSession().getAttribute("user");
        if(usr==null){
            usr=normalUserServiceI.login(account,password);
            modelAndView.setViewName("Login");
            request.getSession().setAttribute("user",usr);
        }
        if(usr instanceof NormalUser){
            modelAndView.addObject("user",usr);
            request.getSession().setAttribute("user",usr);
            Map<KnowledgeType,List<KnowledgeType>> map=knowledgeManagerServiceI.getTwoLayer();
            List<KnowledgeType> knowledgeTypes=normalUserServiceI.getLastLayer();
            modelAndView.addObject("map",map);
            modelAndView.addObject("types",knowledgeTypes);
            modelAndView.addObject("caogao",normalUserServiceI.getCaogaoByID(usr.getAccount()));
            modelAndView.addObject("recommend",normalUserServiceI.getRecommend((NormalUser) usr));
            modelAndView.addObject("rank",normalUserServiceI.getRankBoard());
            System.out.print(map.toString());
            modelAndView.setViewName("UserHomePage2");
        }else if( usr instanceof ExpertUser) {
           modelAndView.setViewName("expertUserHomePage");
            modelAndView.addObject("user",usr);
            request.getSession().setAttribute("user",usr);
        }else if(usr instanceof KnowledgeManager){
            modelAndView.setViewName("knowledgeHomePage");
            modelAndView.addObject("user",usr);
            request.getSession().setAttribute("user",usr);
        }else {
            modelAndView.addObject("msg","登录信息输入有误");
        }
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/firstLayer")
    public String getFirstLayer(HttpServletRequest request){
        NormalUser normalUser=verifyUser(request);
        if(normalUser==null) return "未登录";

        return normalUserServiceI.getFirstLayer().toString();

    }
    @ResponseBody
    @RequestMapping(value = "/getTypeByType",method = RequestMethod.GET)
    public String getTypeByType(@RequestParam(name = "typeId",required = true) String typeid,HttpServletRequest request){
        NormalUser normalUser=verifyUser(request);
        if (normalUser == null) {
            return "未登录";
        }
        return normalUserServiceI.findnextKnowlegeTypeByPreId(typeid).toString();

    }
    @ResponseBody
    @RequestMapping(value = "/addCaogao",method = RequestMethod.POST)
    public String addCaogao(@RequestParam(name = "title",required = false)String title,
                            @RequestParam(name = "digest",required = false)String digest,
                            @RequestParam(name = "detail",required = false)String detail,
                            @RequestParam(name = "typeid",required = false)String typeid,
                            HttpServletRequest request){
        NormalUser  normalUser=verifyUser(request);
        if(normalUser == null)
            return "failed";
        Knowledge knowledge=new Knowledge();
        knowledge.setTypeId(normalUserServiceI.findKnowlegeTypeById(typeid));
        knowledge.setNormalUser(normalUser);
        knowledge.setStatus("草稿");
        knowledge.setTitle(title);
        knowledge.setDetail(detail);
        knowledge.setDigest(digest);
        knowledge.setExpertUser(normalUserServiceI.findExpertUserByTypeId(typeid));
        return normalUserServiceI.addCaogao(normalUser.getId(),knowledge)?"success":"failed";
    }
    @RequestMapping("/changePass")
    public ModelAndView changePassword(@RequestParam(name = "old",required = true)String old,
                                       @RequestParam(name = "new",required = true)String  newPass,
                                       HttpServletRequest  request,
                                       ModelAndView modelAndView){
        NormalUser normalUser=verifyUser(request);
        if (normalUser == null){
            modelAndView.setViewName("Login");
            return modelAndView;
        }
        modelAndView.setViewName("ChangePassPageNormalUser");
        if(commonServiceI.changePassword(normalUser.getAccount(),old,newPass)){
            modelAndView.addObject("msg","修改成功");
        }else {
            modelAndView.addObject("msg","修改失败");
        }
        return modelAndView;
    }
    @RequestMapping("/showKnowledgeDetail")
    public ModelAndView showDetail(@RequestParam(name = "knowId",required = true)String knowId
                                    ,HttpServletRequest request){
        NormalUser normalUser=verifyUser(request);
        ModelAndView modelAndView=new ModelAndView("knowledge");
        if (normalUser == null){
            modelAndView.setViewName("Login");
            return modelAndView;
        }
        modelAndView.addObject("knowledge",normalUserServiceI.getKnledgeByKnowId(knowId,normalUser.getAccount()));
        modelAndView.addObject("comments",normalUserServiceI.getCommentByKnowledgeId(knowId));
        return modelAndView;
    }
    @RequestMapping("/logoutAll")
    public String logout(HttpServletRequest request){
        User user= (User) request.getSession().getAttribute("user");
        if(user==null)
            user= (User) request.getSession().getAttribute("admin");
            if(user!=null)
                request.getSession().removeAttribute("admin");
        request.getSession().removeAttribute("user");
        return "Login";
    }

    private NormalUser verifyUser(HttpServletRequest request){
        return (NormalUser) request.getSession().getAttribute("user");
    }
}
