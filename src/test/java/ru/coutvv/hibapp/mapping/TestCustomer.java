package ru.coutvv.hibapp.mapping;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.coutvv.hibapp.util.TestSessionUtil;
import ru.coutvv.mapping.Customer;

import static org.testng.Assert.fail;

/**
 * Created by coutvv on 16.01.2017.
 */
public class TestCustomer {

    @Test
    public void test() {
        TestSessionUtil tsu = new TestSessionUtil("hibmsgapp.cfg.xml");
        try (Session session = tsu.getSessionObj()) {
            Transaction tx = session.beginTransaction();
            Customer cust = new Customer();
            cust.setName("noshit");
            cust.setAddress("fuck you street");
            session.save(cust);

            Customer jeez = new Customer();
            jeez.setName("noshit2");
            jeez.setAddress("fuck you asshole again!!");
            session.save(jeez);
            tx.commit();
        }
        try (Session session = tsu.getSessionObj()) {
            Transaction tx = session.beginTransaction();
            Customer otherCust = new Customer();
            otherCust.setName("noshit");
            otherCust.setAddress("fuck you asshole!");
            session.save(otherCust);
            tx.commit();
            fail("oh honey...");
        } catch (Exception e) {

        }
    }
}
