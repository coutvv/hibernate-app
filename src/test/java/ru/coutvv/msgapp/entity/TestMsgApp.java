package ru.coutvv.msgapp.entity;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;
import ru.coutvv.hibapp.util.TestSessionUtil;

import static org.testng.Assert.*;

/**
 * Created by coutvv on 12.01.2017.
 */
public class TestMsgApp {

    @Test
    public void testProperSimpleInversationCode() {
        TestSessionUtil util = new TestSessionUtil("hibmsgapp.cfg.xml");

        Long emailId;
        Long msgId;


        try(Session session = util.getSessionObj()) {
            Email email;
            Message message;
            Transaction tx = session.beginTransaction();
            email = new Email("Proper12");
            message = new Message("Proper ms2g");

            message.setEmail(email);
            session.save(email);
            session.save(message);

            emailId = email.getId();
            msgId = message.getId();

            tx.commit();

            assertNull(email.getMessage());
            assertNotNull(message.getEmail());
        }


        System.out.println("that's goood...");

        try(Session session = util.getSessionObj()){
            Email email;
            Message message;

            email = session.get(Email.class, emailId);

            message = session.get(Message.class, msgId);
            System.out.println(email);
            System.out.println(message);

            assertNotNull(email.getMessage());
            assertNotNull(message.getEmail());
        }
    }
}
