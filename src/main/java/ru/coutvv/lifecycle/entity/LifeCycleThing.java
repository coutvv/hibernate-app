package ru.coutvv.lifecycle.entity;

import lombok.Data;
import org.jboss.logging.Logger;

import javax.persistence.*;
import java.util.BitSet;

/**
 * Created by coutvv on 24.01.2017.
 */
@Entity
@Data
public class LifeCycleThing {

    static Logger logger = Logger.getLogger(LifeCycleThing.class);
    public static BitSet lifecycleCalls = new BitSet();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    String name;

    @PostLoad
    public void postLoad() {
        log("postLoad", 0);
    }

    @PrePersist
    public void prePersist() {
        log("prePersist", 1);
    }

    @PostPersist
    public void postPersist() {
        log("postPersist", 2);
    }

    @PreUpdate
    public void preUpdate() {
        log("preUpdate", 3);
    }

    @PostUpdate
    public void postUpdate() {
        log("postUpdate", 4);
    }

    @PreRemove
    public void preRemove() {
        log("preRemove", 5);
    }

    @PostRemove
    public void postRemove() {
        log("postRemove", 6);
    }


    private void log(String msg, Integer index) {
        lifecycleCalls.set(index, true);
        logger.errorf("%12s: %s (%s)", msg, this.getClass().getSimpleName(), this.toString());
        System.out.println(msg);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
}
