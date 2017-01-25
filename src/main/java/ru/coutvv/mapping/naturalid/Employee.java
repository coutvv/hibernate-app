package ru.coutvv.mapping.naturalid;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * Created by coutvv on 21.01.2017.
 */
@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NaturalId
    Integer section;
    @NaturalId
    Integer department;
    @Column
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

}
