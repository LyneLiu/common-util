package com.lyne.collection;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * HashSet、TreeSet：
 *
 * 通过IDE快速生成hashCode和equals方法(Apache Common的EqualsBuilder和HashCodeBuilder)
 *
 * Created by nn_liu on 2017/6/8.
 */
public class SetDemo {

    public static void main(String[] args) {
        Employee em1 = new Employee();
        em1.setFirstName("test");
        em1.setLastName("test");

        Employee em2 = new Employee();
        em2.setFirstName("test");
        em2.setLastName("test");

        System.out.println(em1 == em2);

        System.out.println(em1.equals(em2));

        Set<Employee> employeeSet = new HashSet<>();
        employeeSet.add(em1);
        employeeSet.add(em2);

        System.out.println(employeeSet.size());
    }

}

class Employee{
    private Integer id;
    private String firstName;
    private String lastName;
    private String department;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Employee))
            return false;

        Employee employee = (Employee) o;

        return new EqualsBuilder().append(getFirstName(), employee.getFirstName())
                .append(getLastName(), employee.getLastName())
                .append(getDepartment(), employee.getDepartment()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getFirstName())
                .append(getLastName()).append(getDepartment()).toHashCode();
    }
}
