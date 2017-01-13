package ru.coutvv.msgapp.entity;

import javax.persistence.*;

/**
 * Created by coutvv on 13.01.2017.
 */
@Entity
public class SimpleObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column
    String key;
    @Column
    Long value;

    public SimpleObject() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof SimpleObject)) return false;

        SimpleObject that = (SimpleObject) o;

        if (id != null ? !id.equals(that.getId()) : that.getId() != null) return false;
        if (key != null ? !key.equals(that.getKey()) : that.getKey() != null) return false;
        return value != null ? value.equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
