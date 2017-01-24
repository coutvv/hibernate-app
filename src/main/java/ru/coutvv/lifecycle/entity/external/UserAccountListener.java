package ru.coutvv.lifecycle.entity.external;

import javax.persistence.PrePersist;

/**
 * Created by coutvv on 24.01.2017.
 */
public class UserAccountListener {
    @PrePersist
    void setPasswordHash(Object o) {
        UserAccount ua = (UserAccount) o;
        if(ua.getSalt() == null || ua.getSalt() == 0) {
            ua.setSalt((int)(Math.random()*65535));
        }

        ua.setPasswordHash(ua.getPasswordHash().hashCode() * ua.getSalt());
    }
}
