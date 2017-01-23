package ru.coutvv.lifecycle;

import org.hibernate.Session;
import org.testng.annotations.Test;
import ru.coutvv.lifecycle.entity.JPASessionUtil;
import ru.coutvv.lifecycle.entity.Thing;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertNotNull;

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

    @Test
    public void testEntityManager(){
        EntityManager em = JPASessionUtil.getEntityManager("utiljpa");
        em.getTransaction().begin();
        Thing t = new Thing();
        t.setName("name");
        em.persist(t);
        em.getTransaction().commit();
        em.close();

        em = JPASessionUtil.getEntityManager("utiljpa");
        em.getTransaction().begin();
        TypedQuery<Thing> q = em.createQuery("from Thing t where t.name = :name", Thing.class);
        q.setParameter("name", "name");
        Thing result = q.getSingleResult();
        assertNotNull(result);
        assertEquals(result, t);
        em.remove(result);
        em.getTransaction().commit();
        em.close();
    }
}
