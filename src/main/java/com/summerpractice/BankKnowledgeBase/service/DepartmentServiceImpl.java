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

@Service
public class DepartmentServiceImpl implements DepartmentServiceI {
    @Autowired
    DepartmentDAO departmentDAO;
    @Override
    public List<Department> getAll() {
        return departmentDAO.getAllByDisable(false);
    }

    @Override
    public Department findById(String id) {
        return departmentDAO.findByDepIdAndDisable(id,false);
    }


    @Override
    public void updateInfo(Department department) {
       Department department1=departmentDAO.findByDepIdAndDisable(department.getDepId(),false);
       if(department1!=null){
           try{
               departmentDAO.save(department);
           }catch (Exception e){
               e.printStackTrace();
           }
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
    public void deleleDepartment(Department department) {
        department.setDisable(true);
        updateInfo(department);
    }

    @Override
    public void changeFourth(Department department) {
        Department department1=departmentDAO.findByDepIdAndDisable(department.getDepId(),false);
        if(department1!=null){
            department1.setFourth(department.getFourth());
            try{
                departmentDAO.save(department1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }


}
