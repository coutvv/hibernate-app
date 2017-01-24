package ru.coutvv.lifecycle;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.Reporter;
import org.testng.annotations.Test;
import ru.coutvv.lifecycle.entity.LifeCycleThing;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * Created by coutvv on 24.01.2017.
 */
public class LifecycleTest {

    @Test
    public void lifecycleTest() {
        Integer id;
        LifeCycleThing thing1, thing2, thing3;

        try(Session session = JPASessionUtil.getSession("utiljpa")) {
            Transaction tx = session.beginTransaction();
            thing1 = new LifeCycleThing();
            thing1.setName("shitty");
            session.save(thing1);
            id = thing1.getId();
            tx.commit();
        }

        try(Session session = JPASessionUtil.getSession("utiljpa")) {
            Transaction tx = session.beginTransaction();
            thing2 = session.byId(LifeCycleThing.class).load(-1);
            assertNull(thing2);
            Reporter.log("attemped to load nonexisted reference");
            thing2 = session.byId(LifeCycleThing.class).getReference(id);
            assertNotNull(thing2);
            thing2.setName("shitty2");
            tx.commit();
        }

        try(Session session = JPASessionUtil.getSession("utiljpa")) {
            Transaction tx = session.beginTransaction();
            thing3 = session.byId(LifeCycleThing.class).getReference(id);
            assertNotNull(thing3);
//            assertEquals(thing3, thing2);//just so stupid...
            session.delete(thing3);

            tx.commit();
        }

        assertEquals(LifeCycleThing.lifecycleCalls.nextClearBit(0), 7);
    }
}
