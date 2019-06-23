/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.dao;

import com.summerpractice.BankKnowledgeBase.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentDAO extends JpaRepository<Department,Integer> {
    List<Department> getAllByDisable(boolean disable);
    List<Department> findByFirstAndDisable(String first,boolean disable);
    Department findByDepIdAndDisable(String id,boolean disable);
    List<Department>  findAllByDisableFalseAndFirst(String first);
    List<Department>  findAllByDisableFalseAndSecond(String second);
    List<Department> findAllByDisableFalseAndThird(String third);
    List<Department> findAllByDisableFalseAndFourth(String fourth);
    List<Department> getFirstByDisableFalse();
    List<Department> getAllByDisableFalse();
    Department findDepartmentByDepIdAndDisableFalse(String id);
}
