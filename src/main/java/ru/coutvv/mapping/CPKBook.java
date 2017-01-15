package ru.coutvv.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by coutvv on 15.01.2017.
 */
@Entity
public class CPKBook {
    @Id
    ISBN id;
    @Column
    String name;

    public CPKBook(){}

    public ISBN getId() {
        return id;
    }

    public void setId(ISBN id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
