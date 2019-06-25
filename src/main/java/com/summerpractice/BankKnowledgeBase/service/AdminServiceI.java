/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.service;

import com.summerpractice.BankKnowledgeBase.entity.*;

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

    boolean addDepartment(Department Department);

    boolean deleteDepartment(String departmentId);

    boolean changeDepartment(Department department);

    List<Department> getFirst();

    List<Department> getAll();
    Department findDepartmentByID(String id);

    List<User> findUserByDepartment(Department department);

    List<User> findNormalUserByDepartment(Department department);

    List<User> findExpertUserByDepartment(Department department);

    List<User> findKnowledgeManagerByDepartment(Department department);

    User findNormalUserByAccount(String Account);

    User findExpertUserByAccount(String Account);

    User findKnowledgeManagerByAccount(String Account);

    boolean deleteUser(String account);

    boolean changeUser(User user);

}
