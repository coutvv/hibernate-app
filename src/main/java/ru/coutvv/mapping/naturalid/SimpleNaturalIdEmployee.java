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
public class SimpleNaturalIdEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NaturalId
    Integer badge;
    String name;

    public SimpleNaturalIdEmployee() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBadge() {
        return badge;
    }

    public void setBadge(Integer badge) {
        this.badge = badge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
