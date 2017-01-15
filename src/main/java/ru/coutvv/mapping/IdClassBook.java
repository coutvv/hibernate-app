package ru.coutvv.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

/**
 * Created by coutvv on 15.01.2017.
 */
@Entity
@IdClass(IdClassBook.EmbeddedISBN.class)
public class IdClassBook {


    @Id
    int group;
    @Id
    int publisher;
    @Id
    int title;
    @Id
    int checkDigit;
    String name;

    static class EmbeddedISBN implements Serializable {
        @Column(name="group_number") // because “group” is an invalid column name for SQL
        int group;
        int publisher;
        int title;
        int checkdigit;

        public EmbeddedISBN() {
        }

        public int getGroup() {
            return group;
        }

        public void setGroup(int group) {
            this.group = group;
        }

        public int getPublisher() {
            return publisher;
        }

        public void setPublisher(int publisher) {
            this.publisher = publisher;
        }

        public int getTitle() {
            return title;
        }

        public void setTitle(int title) {
            this.title = title;
        }

        public int getCheckdigit() {
            return checkdigit;
        }

        public void setCheckdigit(int checkdigit) {
            this.checkdigit = checkdigit;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EmbeddedISBN that = (EmbeddedISBN) o;

            if (group != that.group) return false;
            if (publisher != that.publisher) return false;
            if (title != that.title) return false;
            return checkdigit == that.checkdigit;
        }

        @Override
        public int hashCode() {
            int result = group;
            result = 31 * result + publisher;
            result = 31 * result + title;
            result = 31 * result + checkdigit;
            return result;
        }
    }
}
