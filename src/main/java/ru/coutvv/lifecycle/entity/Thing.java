package ru.coutvv.lifecycle.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by coutvv on 23.01.2017.
 */
@Entity
@Data
public class Thing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Thing)) return false;

        Thing thing = (Thing) o;

        if (id != null ? !id.equals(thing.id) : thing.id != null) return false;
        return name != null ? name.equals(thing.name) : thing.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
