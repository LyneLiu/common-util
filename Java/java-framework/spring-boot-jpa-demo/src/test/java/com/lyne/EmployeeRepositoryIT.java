package com.lyne;

import com.lyne.dao.EmployeeRepository;
import com.lyne.entiry.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author nn_liu
 * @Created 2017-11-15-14:14
 */


@SpringBootTest(classes = {Application.class})
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@Transactional
public class EmployeeRepositoryIT {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Test
    public void findingEmployees_joiningDateIsZeroHour_found() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ts = LocalDateTime.parse("2014-04-01 00:00:00", formatter);
        Employee employee = new Employee();
        employee.setName("Test Employee");
        employee.setAge(20);
        employee.setTs(ts);
        employeeRepository.save(employee);
        // Query to find employees
        List<Employee> employees = employeeRepository.findAll((root, query, cb) ->
                cb.and(
                        cb.greaterThanOrEqualTo(root.get("ts").as(LocalDateTime.class), ts),
                        cb.lessThan(root.get("ts").as(LocalDateTime.class), ts.plusDays(1)))
        );
        assertThat(employees).hasSize(1);
    }


    @Test
    public void findingEmployees_joiningDateIsNotZeroHour_found() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ts = LocalDateTime.parse("2014-04-01 08:00:00", formatter);
        LocalDateTime zeroHour = LocalDateTime.parse("2014-04-01 00:00:00", formatter);
        Employee employee = new Employee();
        employee.setName("Test Employee");
        employee.setAge(25);
        employee.setTs(ts);
        employeeRepository.save(employee);
        List<Employee> employees = employeeRepository.findAll((root, query, cb) -> cb
                .and(cb.greaterThanOrEqualTo(root.get("ts"), zeroHour),
                        cb.lessThan(root.get("ts"), zeroHour.plusDays(1))));
        assertThat(employees).hasSize(1);
    }
}
