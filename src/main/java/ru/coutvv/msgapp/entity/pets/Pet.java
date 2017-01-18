package ru.coutvv.msgapp.entity.pets;

import javax.persistence.*;

/**
 * Created by coutvv on 18.01.2017.
 */

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;

    @OneToMany
    Human owner;

    public Pet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
