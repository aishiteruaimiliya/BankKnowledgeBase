/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.service;

import com.summerpractice.BankKnowledgeBase.entity.Department;

import java.util.List;

public interface DepartmentServiceI {
    public List<Department> getAll();
    public Department findById(int id);
    public void updateInfo(Department department);
    public void addDepartment(Department department);
    public void deleteByID(Department department);
    void deleteAllByFirst(String first);
    void deleteAllBySecond(String second);
    void deleteAllByThird(String third);
    void deleteAllByFourth(String fourth);

}
