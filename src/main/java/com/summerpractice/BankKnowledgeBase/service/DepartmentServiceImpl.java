/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.service;

import com.summerpractice.BankKnowledgeBase.dao.DepartmentDAO;
import com.summerpractice.BankKnowledgeBase.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentServiceI {
    @Autowired
    DepartmentDAO departmentDAO;
    @Override
    public List<Department> getAll() {
        return departmentDAO.getAllByDisable(false);
    }

    @Override
    public Department findById(int id) {
        return departmentDAO.findByDepIdAndDisable(id,false);
    }


    @Override
    public void updateInfo(Department department) {
        department.setDepId(0);
        Optional<Department> temp=departmentDAO.findById(department.getDepId());

        if(temp.isPresent())
            departmentDAO.save(department);
        else try {
            throw new Exception("数据不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addDepartment(Department department) {
        department.setDisable(false);
        departmentDAO.save(department);
    }

    @Override
    public void deleteByID(Department department) {
        departmentDAO.delete(department);
    }

    @Override
    public void deleteAllByFirst(String first) {
        departmentDAO.deleteAllByFirst(first);
    }

    @Override
    public void deleteAllBySecond(String second) {
        departmentDAO.deleteAllBySecond(second);
    }

    @Override
    public void deleteAllByThird(String third) {
        departmentDAO.deleteAllByThird(third);
    }

    @Override
    public void deleteAllByFourth(String fourth) {
        departmentDAO.deleteAllByFourth(fourth);
    }

}
