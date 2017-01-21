package ru.coutvv.mapping.naturalid;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by coutvv on 21.01.2017.
 */
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NaturalId
    Integer section;
    @NaturalId
    Integer department;

    String name;

    public Employee() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ! (o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        if (id != null ? !id.equals(employee.getId()) : employee.getId() != null) return false;
        if (section != null ? !section.equals(employee.getSection()) : employee.getSection() != null) return false;
        if (department != null ? !department.equals(employee.getDepartment()) : employee.getDepartment() != null) return false;
        return name != null ? name.equals(employee.getName()) : employee.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (section != null ? section.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", section=" + section +
                ", department=" + department +
                ", name='" + name + '\'' +
                '}';
    }
}
