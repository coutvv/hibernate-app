package ru.coutvv.msgapp.entity;

import javax.persistence.*;

/**
 * Created by coutvv on 12.01.2017.
 */
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String content;

    @OneToOne
    @JoinColumn(name = "email_id")
    private Email email;

    public Message() {

    }

    public Message(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", email=" + email.getSubject() +
                '}';
    }

}
