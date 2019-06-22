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
    Department findByDepIdAndDisable(int id,boolean disable);
    void deleteAllByFirst(String first);
    void deleteAllBySecond(String second);
    void deleteAllByThird(String third);
    void deleteAllByFourth(String fourth);
}
