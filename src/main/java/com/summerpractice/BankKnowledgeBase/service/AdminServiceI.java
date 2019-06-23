/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.service;

import com.summerpractice.BankKnowledgeBase.entity.Department;
import com.summerpractice.BankKnowledgeBase.entity.User;

import java.util.List;

public interface AdminServiceI {
    public boolean addUser(User user,Department department);

    /**
     * 批量导入 所导入目标机构全部一致
     * @param users 用户数组
     * @return 导入是否成功
     */
    boolean batchAdd(List<User> users,Department department);

    List<Department> getDepartmentByFirst(String first);

    List<Department> getDepartmentBySecond(String second);

    List<Department> getDepartmentByThird(String third);

    List<Department> getDepartmentByFourth(String fourth);

    List<Department> getFirst();

    List<Department> getAll();
    Department findDepartmentByID(String id);
}
