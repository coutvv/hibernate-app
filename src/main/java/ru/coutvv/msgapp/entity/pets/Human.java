package ru.coutvv.msgapp.entity.pets;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by coutvv on 18.01.2017.
 */
@Entity
public class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String name;


    Pet pet;

    public Human() {
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


    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
