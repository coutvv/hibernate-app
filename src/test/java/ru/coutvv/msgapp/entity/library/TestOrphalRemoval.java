package ru.coutvv.msgapp.entity.library;

import junit.framework.Assert;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.coutvv.msgapp.entity.AbstractTest;

import java.util.List;

import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by coutvv on 13.01.2017.
 */
public class TestOrphalRemoval extends AbstractTest {

    @Test
    public void test() {
        Long id = createLibrary();

        try(Session session = util.getSessionObj()) {
            Transaction tx  = session.beginTransaction();

            Library lib = session.get(Library.class, id);
            assertEquals(lib.getBooks().size(), 3);

            lib.getBooks().remove(0);
            assertEquals(lib.getBooks().size(), 2);

            tx.commit();
        }

        try(Session session = util.getSessionObj()) {
            Transaction tx = session.beginTransaction();
            Library l2 = session.load(Library.class, id);
            assertEquals(l2.getBooks().size(), 2);
            Query<Book> query = session.createQuery("from Book b", Book.class);
            List<Book> books = query.list();
            assertEquals(books.size(), 2);
            tx.commit();
        }
    }

    private Long createLibrary() {
        Library lib = null;
        try(Session session = util.getSessionObj()){
            Transaction tx = session.beginTransaction();
            lib = new Library();
            lib.setName("Fucking asshole library");
            session.save(lib);
            createBook(session, lib, "Moby Dick");
            createBook(session, lib, "Metro 2033");
            createBook(session, lib, "Ironing Man");
            tx.commit();
        }

        assertNotNull(lib);
        return lib.getId();
    }

    private void createBook(Session session, Library lib, String name) {
        Book book = new Book();
        book.setLibrary(lib);
        book.setTitle(name);
        session.save(book);
        lib.getBooks().add(book);

    }
}
