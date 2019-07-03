/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.controller;

import com.summerpractice.BankKnowledgeBase.entity.*;
import com.summerpractice.BankKnowledgeBase.service.AdminServiceI;
import com.summerpractice.BankKnowledgeBase.service.CommonServiceI;
import com.summerpractice.BankKnowledgeBase.service.KnowledgeManagerServiceI;
import com.summerpractice.BankKnowledgeBase.service.NormalUserServiceI;
import com.summerpractice.BankKnowledgeBase.util.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/adminRequest")
public class AdminRequestController {
    @Autowired
    AdminServiceI adminServiceI;
    @Autowired
    KnowledgeManagerServiceI knowledgeManagerServiceI;
    @Autowired
    CommonServiceI commonServiceI;
    @Autowired
    NormalUserServiceI normalUserServiceI;
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public String uploadFile(@RequestParam(name = "file",required = true) MultipartFile file, HttpServletRequest request){
        //todo 解决multipart加密数据的获取问题
        if(file.isEmpty()||request.getParameter("selected")==null||request.getParameter("selected").equals("")){
            return "ERROR";
        }
        String depid=request.getParameter("selected");
        System.out.print(depid);
        List<User> users;
        try {
            users = ExcelReader.parseUserFromArray(ExcelReader.readExcel(file));
        }catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
        Department department=adminServiceI.findDepartmentByID(depid);
        try {
            adminServiceI.batchAdd(users,department);
        }catch (Exception e){
            return "ERROR";
        }
        return "AddUser";

    }
    @RequestMapping("/adduser")
    public ModelAndView addUser(@RequestParam(name = "selected",required = true) String depid,
                          @RequestParam(name = "username",required = true) String username,
                          @RequestParam(name = "account",required = true) String account,
                          @RequestParam(name = "password",required = true) String password,
                          @RequestParam(name = "userType",required = true) String userType,
                          @RequestParam(name = "typeId",required = false)String typeId){
        User user;
        switch (userType){
            case "1":
                user=new NormalUser();
                break;
            case "2":
                user=new KnowledgeManager();
                break;
            case "3":
                if(typeId==null) return new ModelAndView("ResultPage","msg","未选择知识领域");
                user=new ExpertUser();
                ExpertUser expertUser=(ExpertUser)user;
                expertUser.setKnowledgeType(normalUserServiceI.findKnowlegeTypeById(typeId));
                user=expertUser;
                break;
            case "4":
                user=new SystemManager();
                break;
                default:
                    return new ModelAndView("ResultPage","msg","添加失败");
        }
        user.setAccount(account);
        user.setName(username);
        user.setPassword(password);
        Department department=adminServiceI.findDepartmentByID(depid);
        try{
            adminServiceI.addUser(user,department);
        }catch (Exception e){
            return new ModelAndView("ResultPage","msg","添加失败");
        }
        return new ModelAndView("ResultPage","msg","添加成功");
    }
    @RequestMapping("/addDept")
    public ModelAndView addDepartment(@RequestParam(name = "first",required = true)String first,
                                @RequestParam(name = "second",required = true)String second,
                                @RequestParam(name = "third",required = true)String third,
                                @RequestParam(name = "fourth",required = true)String fourth,
                                      ModelAndView modelAndView){
        Department department=new Department();
        department.setFirst(first);
        department.setSecond(second);
        department.setThird(third);
        department.setFourth(fourth);
        modelAndView.setViewName("Department");
        if(adminServiceI.addDepartment(department)){
            modelAndView.addObject("msg","添加机构成功");

        }else {
            modelAndView.addObject("msg","添加失败");
        }
        return modelAndView;
    }
    @ResponseBody
    @RequestMapping("/modifyDept")
    public String changeDepartment(@RequestParam(name = "first",required = false)String first,
                                   @RequestParam(name = "second",required = false)String second,
                                   @RequestParam(name = "third",required = false)String third,
                                   @RequestParam(name = "fourth",required = false)String fourth,
                                   @RequestParam(name = "dep_id",required = true)String depId){
        Department department=adminServiceI.findDepartmentByID(depId);
        if(department==null) return "failed";
        if(first!=null)
        department.setFirst(first);
        if(second!=null)
        department.setSecond(second);
        if(third!=null)
        department.setThird(third);
        if(fourth!=null)
        department.setFourth(fourth);
        try{
            adminServiceI.changeDepartment(department);
        }catch (Exception e) {
            return "failed";
        }
        return "success";

    }
//    @ResponseBody
//    @RequestMapping("/deleteDept")
//    public String deleteDepartment( @RequestParam(name = "dep_id",required = true)String depId){
//        Department department=adminServiceI.findDepartmentByID(depId);
//        if(department==null) return "failed";
//        try{
//            adminServiceI.deleteDepartment(depId);
//        }catch (Exception e){
//            return "failed";
//        }
//        return "success";
//    }
//    @ResponseBody
//    @RequestMapping("/searchDept")
//    public String findDepartment(@RequestParam(name = "key",required = false)String key){
//        if(key==null){
//            return "failed";
//        }
//        List<Department> departments;
//        try{
//            departments = adminServiceI.findDepartmentsByKeyword(key,key,key,key);
//        }catch (Exception e){
//            return "搜索失败";
//        }
//        if(departments==null){
//            return "无结果";
//        }else {
//            return departments.toString();
//        }
//
//    }

    @ResponseBody
    @RequestMapping("/searchUserByDept")
    public String findUserByDept(@RequestParam(name = "depid")String depId){
        Department department=adminServiceI.findDepartmentByID(depId);
        List<User> users=adminServiceI.findUserByDepartment(department);
        return users.toString();
    }


    @ResponseBody
    @RequestMapping("/modifyNormalUser")
    public String modifyNormalUser(@RequestParam(name = "account",required = true) String account,
                                   @RequestParam(name = "username",required = false) String username,
                                   @RequestParam(name = "password",required = false) String password,
                                   @RequestParam(name = "selected",required = false) String depid){


        NormalUser normalUser= (NormalUser) adminServiceI.findNormalUserByAccount(account);
        if(normalUser==null) return "failed";
        if(depid!=null)
        normalUser.setDepartment(adminServiceI.findDepartmentByID(depid));
        if(username!=null)
        normalUser.setName(username);
        if(password!=null)
        normalUser.setPassword(password);
        try{
            adminServiceI.changeUser(normalUser);
        }catch (Exception e){
            return "failed";
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/modifyExpertUser")
    public String modifyExpertUser(@RequestParam(name = "selected",required = false) String depid,
                                   @RequestParam(name = "username",required = true) String username,
                                   @RequestParam(name = "account",required = false) String account,
                                   @RequestParam(name = "password",required = false) String password,
                                   @RequestParam(name = "typeId",required = false)String typeId){
        ExpertUser expertUser= (ExpertUser) adminServiceI.findExpertUserByAccount(account);
        if (expertUser==null) return "failed";
        if(username!=null) expertUser.setName(username);
        if(password!=null) expertUser.setPassword(password);
        try{
            if(depid!=null)
                expertUser.setDepartment(adminServiceI.findDepartmentByID(depid));
            adminServiceI.changeUser(expertUser);
            if(typeId!=null)
                knowledgeManagerServiceI.distribution(expertUser.getId(),typeId);
        }catch (Exception e){
            return "failed";
        }
        return "success";
    }
    @ResponseBody
    @RequestMapping("/modifyKnowledgeManager")
    public String modifyKnowledgeManager(@RequestParam(name = "selected",required = false) String depid,
                                         @RequestParam(name = "username",required = true) String username,
                                         @RequestParam(name = "account",required = true) String account,
                                         @RequestParam(name = "password",required = false) String password,
                                         @RequestParam(name = "typeId",required = false)String typeId){
//        KnowledgeManager knowledgeManager= (KnowledgeManager) adminServiceI.findKnowledgeManagerByAccount(account);
        KnowledgeManager knowledgeManager = adminServiceI.findKnowledgeManagerByAccount(account);

        if (knowledgeManager==null) return "failed";
        if(depid!=null)
            knowledgeManager.setDepartment(adminServiceI.findDepartmentByID(depid));
        if(username!=null)
            knowledgeManager.setName(username);
        if(password!=null)
            knowledgeManager.setPassword(password);
        try{
            adminServiceI.changeUser(knowledgeManager);
        }catch (Exception e){
            return "failed";
        }
        return "success";
    }
    @ResponseBody
    @RequestMapping("/fetchDept")
    public String getAllDept(){
        return adminServiceI.getAllDept().toString();
    }
    @RequestMapping(value = "/DoLogin",method = RequestMethod.POST)
    public ModelAndView doLogin(@RequestParam(name = "account",required = true)String account,
                                @RequestParam(name = "password",required = true)String password,
                                HttpServletRequest request){
        SystemManager systemManager=adminServiceI.login(account,password);
        if(systemManager==null){
            return new ModelAndView("AdminLoginPage","msg","用户不存在！");
        }else {
            request.getSession().setAttribute("admin",systemManager);
            ModelAndView modelAndView=new ModelAndView("AdminHomePage");
            modelAndView.addObject("admin",systemManager);
            modelAndView.addObject("depts",adminServiceI.getAllDept());
            return modelAndView;
        }
    }
    @RequestMapping("/changePass")
    public ModelAndView changePassword(@RequestParam(name = "old",required = true)String old,
                                        @RequestParam(name = "new",required = true)String  newPass,
                                        HttpServletRequest  request,
                                       ModelAndView modelAndView){
        SystemManager systemManager=verify(request);
        modelAndView.setViewName("ChangePasswordPageAdmin");
        if(commonServiceI.changePassword(systemManager.getAccount(),old,newPass)){
            modelAndView.addObject("msg","修改成功");
        }else {
            modelAndView.addObject("msg","修改失败");
        }
        return modelAndView;
    }
    @ResponseBody
    @RequestMapping(value = "/changeDept",method = RequestMethod.GET)
    public ModelAndView changeDept(@RequestParam(name = "first",required = true)String first,
                                   @RequestParam(name = "second",required = true)String second,
                                   @RequestParam(name = "third",required = true)String third,
                                   @RequestParam(name = "fourth",required = true)String fourth,
                                   @RequestParam(name = "depId",required = true) String depid,
                                   ModelAndView modelAndView){
        Department department=adminServiceI.findDepartmentByID(depid);
        if(first!=null&&!first.equals(""))
        department.setFirst(first);
        if(second!=null&&!second.equals(""))
        department.setSecond(second);
        if(third!=null&&!third.equals(""))
        department.setSecond(third);
        if(fourth!=null&&!fourth.equals(""))
        department.setFourth(fourth);
        modelAndView.setViewName("DepartmentListPage");
        if(adminServiceI.changeDepartment(department)){
            modelAndView.addObject("msg","修改成功");
        }else {
            modelAndView.addObject("msg","修改失败");
        }
        modelAndView.addObject("depts",adminServiceI.getAllDept());
        return modelAndView;
    }
    @RequestMapping(value = "/deleteDept",method = RequestMethod.GET)
    public ModelAndView deleteDept(@RequestParam(name = "depId",required = true)String depId,
                                   ModelAndView modelAndView){
        modelAndView.setViewName("DepartmentListPage");
        if(adminServiceI.deleteDepartment(depId)){
            modelAndView.addObject("msg","删除成功");
        }else {
            modelAndView.addObject("msg","删除失败");
        }
        modelAndView.addObject("depts",adminServiceI.getAllDept());
        return modelAndView;
    }

    @RequestMapping("/searchDept")
    public ModelAndView searchDepartment(@RequestParam(name = "keyword",required = false)String keyword,
                                   ModelAndView modelAndView){
        modelAndView.setViewName("DepartmentListPage");
        List<Department> departments=adminServiceI.findDepartmentsByKeyword(keyword,keyword,keyword,keyword);
        modelAndView.addObject("depts",departments);
        return modelAndView;
    }
    @RequestMapping("/showNormalUser")
    public ModelAndView showNormalUsers(HttpServletRequest request,ModelAndView modelAndView){
        modelAndView.setViewName("UserListPage");
        List<NormalUser> users=adminServiceI.getNormalUser();
        modelAndView.addObject("users",users);
        return modelAndView;
    }
    @RequestMapping("/showExpertUser")
    public ModelAndView showExpertUser(HttpServletRequest request,ModelAndView modelAndView){
        modelAndView.setViewName("UserListPage");
        List<ExpertUser> users=adminServiceI.getExpertUser();
        modelAndView.addObject("users",users);
        return modelAndView;
    }
    @RequestMapping("/showKnowledgeManager")
    public ModelAndView showUsers(HttpServletRequest request,ModelAndView modelAndView){
        modelAndView.setViewName("UserListPage");
        List<KnowledgeManager> users=adminServiceI.getKnowledgeManager();
        modelAndView.addObject("users",users);
        return modelAndView;
    }
    @RequestMapping("/showUserByDept")
    public ModelAndView showByDept(@RequestParam(name = "depId",required = true)String depID){
        return new ModelAndView("UserListPage","users",
                adminServiceI.findUserByDepartment(adminServiceI.findDepartmentByID(depID)));
    }
    @RequestMapping("/showSearchResult")
    public ModelAndView showByKeyword(@RequestParam(name = "keyword",required = true)String keyword){
        ModelAndView modelAndView=new ModelAndView("KeyWordSearchLIstPage");
        modelAndView.addObject("normal",adminServiceI.findNormalUserByKeyword(keyword));
        modelAndView.addObject("expert",adminServiceI.findExpertUserByKeyword(keyword));
        modelAndView.addObject("manager",adminServiceI.findKnowledgeManagerByKeyword(keyword));
        return modelAndView;
    }
    @RequestMapping("/deleteUserFromS")
    public ModelAndView deleteUser(@RequestParam(name = "userId",required = true)String userId){
        ModelAndView modelAndView=new ModelAndView("ResultPage");
        if(adminServiceI.deleteUser(userId)){
            modelAndView.addObject("msg","删除成功");
        }else {
            modelAndView.addObject("msg","删除失败");
        }
        return  modelAndView;
    }
    @RequestMapping("/recoveryUser")
    public ModelAndView recovery(@RequestParam(name = "userId",required = true)String userId){
        ModelAndView modelAndView=new ModelAndView("ResultPage");
        if(adminServiceI.recoveryUser(userId)){
            modelAndView.addObject("msg","恢复成功");
        }else {
            modelAndView.addObject("msg","恢复失败");
        }
        return  modelAndView;
    }


    public SystemManager verify(HttpServletRequest request){
        return (SystemManager) request.getSession().getAttribute("admin");
    }
}
