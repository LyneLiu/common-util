package com.lyne.dao;

import com.lyne.entiry.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author nn_liu
 * @Created 2017-11-15-14:06
 */

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>,
        JpaSpecificationExecutor<Employee> {
}
