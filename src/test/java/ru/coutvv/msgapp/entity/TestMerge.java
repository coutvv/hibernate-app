package ru.coutvv.msgapp.entity;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

/**
 * Created by coutvv on 13.01.2017.
 */
public class TestMerge extends AbstractTest {

    @Test
    public void merge() {
        SimpleObject so = validateSO(id, MAIN_VALUE); so.setValue(24L);
        try(Session session = util.getSessionObj()) {
            Transaction tx = session.beginTransaction();
            session.merge(so);
            tx.commit();
        }
        validateSO(id, 24L);
    }

}
