package ru.coutvv.msgapp.entity;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.BeforeClass;
import ru.coutvv.hibapp.util.TestSessionUtil;

import static org.testng.Assert.assertEquals;


public abstract class AbstractTest {

    protected static final TestSessionUtil util = new TestSessionUtil("hibmsgapp.cfg.xml");
    protected static Long id;

    protected static final String MAIN_KEY = "Fuck you asshole";
    protected static final Long MAIN_VALUE = 23L;

    @BeforeClass
    public void init() {
        //save entity
        try(Session session = util.getSessionObj()) {
            Transaction tx = session.beginTransaction();
            SimpleObject so = new SimpleObject();
            so.setKey(MAIN_KEY);
            so.setValue(MAIN_VALUE);
            session.save(so);
            System.out.println(so);
            id = so.getId();
            tx.commit();
        }
    }

    protected SimpleObject validateSO(Long id, Long value) {
        SimpleObject so = null;
        try(Session session = util.getSessionObj()) {
            so = session.get(SimpleObject.class, id);
            assertEquals(so.getKey(), MAIN_KEY);
            assertEquals(so.getValue(), value);
        }
        return so;
    }
}
