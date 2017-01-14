package ru.coutvv.msgapp.entity.library;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by coutvv on 13.01.2017.
 */
@Entity
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    @OneToMany(orphanRemoval = true, mappedBy = "library")
    private List<Book> books = new ArrayList<>();

    public Library() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
