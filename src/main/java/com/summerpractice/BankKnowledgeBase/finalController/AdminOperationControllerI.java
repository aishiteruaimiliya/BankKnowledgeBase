/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.finalController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/adminOp")
public interface AdminOperationControllerI extends VerifiableI,ChangePassI,LoginI {
    /**
     *
     * @param file 文件
     * @param request 请求
     * @return 结果
     */
    public String addUserByExcel(@RequestParam(name = "file",required = true) MultipartFile file,
                          HttpServletRequest request);

    public String addUserByForm(@RequestParam(name = "selected",required = true) String depid,
                                @RequestParam(name = "username",required = true) String username,
                                @RequestParam(name = "account",required = true) String account,
                                @RequestParam(name = "password",required = true) String password,
                                @RequestParam(name = "userType",required = true) String userType,
                                @RequestParam(name = "typeId",required = false)String typeId);

    public String addDeptByForm(@RequestParam(name = "first",required = true)String first,
                                @RequestParam(name = "second",required = true)String second,
                                @RequestParam(name = "third",required = true)String third,
                                @RequestParam(name = "fourth",required = true)String fourth
                                ,HttpServletRequest request);

    public String changeDept(@RequestParam(name = "first",required = false)String first,
                             @RequestParam(name = "second",required = false)String second,
                             @RequestParam(name = "third",required = false)String third,
                             @RequestParam(name = "fourth",required = false)String fourth,
                             @RequestParam(name = "dep_id",required = true)String depId);

    public String  findUserByDept(@RequestParam(name = "depid")String depId);

    public String modifyUser(@RequestParam(name = "selected",required = false) String depid,
                             @RequestParam(name = "username",required = true) String username,
                             @RequestParam(name = "account",required = true) String account,
                             @RequestParam(name = "password",required = false) String password,
                             @RequestParam(name = "typeId",required = false)String typeId);


    public String deleteDept(@RequestParam(name = "depId",required = true)String depId);


    public String searchDepartment(@RequestParam(name = "keyword",required = false)String keyword);


    public String showNormalUsers(HttpServletRequest request);

    public String showExpertUser(HttpServletRequest request);

    public String showKnowledgeManager(HttpServletRequest request);

    public String searchUser(@RequestParam(name = "keyword",required = true)String keyword,HttpServletRequest request);

    public String deleteUser(@RequestParam(name = "userId",required = true)String userId,HttpServletRequest request);

    public String recoveryUser(@RequestParam(name = "userId",required = true)String userId,HttpServletRequest request);



}
