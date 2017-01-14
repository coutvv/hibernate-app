package ru.coutvv.msgapp.entity;

import org.hibernate.Session;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by coutvv on 13.01.2017.
 */
public class TestRefresh extends AbstractTest {

    @Test
    public void refresh() {
        SimpleObject so = validateSO(id, MAIN_VALUE);
        so.setValue(MAIN_VALUE + 1);
        try(Session session = util.getSessionObj()) {
            session.refresh(so);
            Assert.assertEquals(so.getValue(), MAIN_VALUE);
        }
        validateSO(id, MAIN_VALUE);
    }
}
