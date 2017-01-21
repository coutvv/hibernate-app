package ru.coutvv.lifecycle;

import org.hibernate.Session;
import org.testng.annotations.Test;
import ru.coutvv.lifecycle.entity.JPASessionUtil;

import javax.persistence.EntityManager;

import static org.testng.Assert.fail;

/**
 * Created by coutvv on 21.01.2017.
 */
public class TestSimpleJPAResource {

    @Test
    public void getEntityManager() {
        EntityManager em = JPASessionUtil.getEntityManager("utiljpa");
        em.close();
    }

    @Test
    public void getSession() {
        Session session = JPASessionUtil.getSession("utiljpa");
        session.close();
    }

    @Test(expectedExceptions = {javax.persistence.PersistenceException.class})
    public void nonexistenceEntityManagerName() {
        JPASessionUtil.getEntityManager("shitjpa");
        fail("we shouldn't be able to acquire em here");
    }

    @Test(expectedExceptions = {javax.persistence.PersistenceException.class})
    public void nonexistenceSessionName() {
        JPASessionUtil.getSession("shitjpa");
        fail("oh come on how it get session shitjpa???");
    }
}
