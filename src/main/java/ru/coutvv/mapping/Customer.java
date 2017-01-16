package ru.coutvv.mapping;

import javax.persistence.*;

/**
 * Created by coutvv on 16.01.2017.
 */
@Entity
@Table(name="customer",
        uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
@SecondaryTable(name = "customer_details")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public String name;
    @Column(table = "customer_details")
    public String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Customer() {
    }
}
