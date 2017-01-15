package ru.coutvv.mapping;

import javax.persistence.Entity;

/**
 * Created by coutvv on 15.01.2017.
 */
@Entity
public class Book {
    String title;
    int pages;
    int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
