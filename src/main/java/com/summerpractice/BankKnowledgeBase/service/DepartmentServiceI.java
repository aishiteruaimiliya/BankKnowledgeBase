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
    public Department findById(String id);
    public void updateInfo(Department department);
    public void addDepartment(Department department);
    public void deleteByID(Department department);
    public void deleleDepartment(Department department);
    public void changeFourth(Department department);

}
