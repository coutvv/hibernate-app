package ru.coutvv.msgapp.entity;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;
import ru.coutvv.hibapp.util.TestSessionUtil;

import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Так делать(обновлять) низзя!
 *
 * Created by coutvv on 13.01.2017.
 */
public class TestSimpleObject {

    private static TestSessionUtil tsu = new TestSessionUtil("hibmsgapp.cfg.xml");

    @Test(enabled = false)
    public void testSavingEntitiesTwice() {

        Long id;
        SimpleObject so;

        try(Session session = tsu.getSessionObj()) {
            Transaction tx = session.beginTransaction();
            so = new SimpleObject();
            so.setKey("FUCK YOU ASSHOLE!");
            so.setValue(1233L);
            session.save(so);
            assertNotNull(so.getId());
            id = so.getId();
            tx.commit();
        }

        try(Session session = tsu.getSessionObj()) {
            Transaction tx = session.beginTransaction();
            so.setValue(32L);
            session.save(so);
            tx.commit();
        }
        System.out.println(id + " " + so.getId());
        assertNotEquals(id, so.getId());
    }

    @Test(enabled = false)
    public void testSaveOrUpdate() throws InterruptedException {
        Long id;
        SimpleObject obj;
        try(Session sessoin = tsu.getSessionObj()) {
            Transaction tx = sessoin.beginTransaction();
            obj = new SimpleObject();
            obj.setKey("Oh come on! Jeez!");
            obj.setValue(123456L);
            sessoin.save(obj);
            id = obj.getId();
            assertNotNull(id);
            tx.commit();
        }
        try(Session session = tsu.getSessionObj()) {
            Transaction tx = session.beginTransaction();
            obj.setValue(2L);
            session.saveOrUpdate(obj);
            tx.commit();
        }

        assertEquals(id, obj.getId());
    }

    @Test
    public void testSaveLoad() {
        Long id = null;
        SimpleObject obj;

        try(Session session = tsu.getSessionObj()) {
            Transaction tx = session.beginTransaction();
            obj = new SimpleObject();
            obj.setKey("shut up");
            obj.setValue(123L);

            session.save(obj);
            id = obj.getId();
            assertNotNull(id);
            tx.commit();
        }

        try(Session session = tsu.getSessionObj()){
            SimpleObject o2 = session.load(SimpleObject.class, id);
            SimpleObject o3 = session.load(SimpleObject.class, id);

            assertEquals(o2.getKey(), "shut up");
            assertNotNull(o2.getValue());
            assertEquals(o2.getValue().longValue(), 123L);

            assertEquals(o3.getKey(), "shut up");
            assertNotNull(o3.getValue());
            assertEquals(o3.getValue().longValue(), 123L);

            assertEquals(o2, o3);
            assertEquals(o2, obj);
            // o2 == o3 cause they were loaded from this session
            // but obj and (o2||o3) is not ==, cause obj is not from here(o2 and o3 had same reference)
            // but obj equals to o2 and o3 like they either
            assertTrue(o2 == o3);
            assertFalse(o2 == obj);
            System.out.println(session.getEntityName(o3));
        }

    }
}
