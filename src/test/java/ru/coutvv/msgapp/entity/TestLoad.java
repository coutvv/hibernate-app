package ru.coutvv.msgapp.entity;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by coutvv on 13.01.2017.
 */
public class TestLoad extends AbstractTest {

    @Test
    public void loadByEntityName(){
        try(Session session = util.getSessionObj()) {
            SimpleObject so = (SimpleObject)session.load(SimpleObject.class.getName(), id);
            assertObject(so);
        }
    }

    @Test
    public void loadByClass(){
        try(Session session = util.getSessionObj()) {
            SimpleObject so = (SimpleObject)session.load(SimpleObject.class, id);
            assertObject(so);
        }
    }

    @Test
    public void loadInObject() {
        try(Session session = util.getSessionObj()) {
            SimpleObject so = new SimpleObject();
            session.load(so, id);
            assertObject(so);
        }
    }

    @Test
    public void loadUnexistingObject() {
        try(Session session = util.getSessionObj()) {
            SimpleObject so = session.load(SimpleObject.class, (id+1));
            assertNull(so);
        } catch (Exception e) {
            assertTrue(e instanceof ObjectNotFoundException);
        }
    }

    @Test
    public void getObject() {
        try(Session session = util.getSessionObj()) {
            SimpleObject so1 = session.get(SimpleObject.class, id);
            assertObject(so1);
            //get unexisting object
            String entityName = session.getEntityName(so1);
            SimpleObject so2 = (SimpleObject) session.get(entityName, id+1);
            assertNull(so2);
        }
    }

    private void assertObject(SimpleObject so) {
        assertEquals(so.getId(), id);
        assertEquals(so.getKey(), MAIN_KEY);
        assertEquals(so.getValue(), MAIN_VALUE);
    }
}
