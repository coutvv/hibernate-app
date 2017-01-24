package ru.coutvv.lifecycle.entity.external;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by coutvv on 24.01.2017.
 */
@Entity
@NoArgsConstructor
@Data
@EntityListeners({UserAccountListener.class})
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;
    @Transient
    String password;
    Integer salt;
    Integer passwordHash;

    public boolean validPassword(String newPassword) {
        return newPassword.hashCode() * salt == getPasswordHash();
    }

    public Integer getPasswordHash() {
        return passwordHash;
    }

    public Integer getSalt() {
        return salt;
    }

    public void setSalt(int salt) {
        this.salt = salt;
    }

    public void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
    }
}
