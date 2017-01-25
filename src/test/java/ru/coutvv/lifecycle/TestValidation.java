package ru.coutvv.lifecycle;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;
import ru.coutvv.hibapp.util.SessionUtil;
import ru.coutvv.hibapp.util.TestSessionUtil;
import ru.coutvv.lifecycle.entity.validation.ValidatedSimplePerson;

import javax.validation.ConstraintViolationException;

import static org.testng.Assert.fail;

/**
 * Created by coutvv on 25.01.2017.
 */
public class TestValidation {

    @Test
    public void createdValidPerson(){
        ValidatedSimplePerson vsp = ValidatedSimplePerson.builder()
                                    .age(15)
                                    .fname("Jonny")
                                    .lname("McYangster").build();
        persist(vsp);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createValidatedUnderagePerson() {

        ValidatedSimplePerson vsp = ValidatedSimplePerson.builder()
                .age(12)
                .fname("Jonny")
                .lname("McYangster").build();
        persist(vsp);
        fail("Should have failed validation");
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createValidatedPoorFNamePerson2() {
        ValidatedSimplePerson vsp = ValidatedSimplePerson.builder()
                .age(16)
                .fname("J")
                .lname("McYangster").build();
        persist(vsp);
        fail("Should have failed validation");
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createValidatedNoFNamePerson2() {
        ValidatedSimplePerson vsp = ValidatedSimplePerson.builder()
                .age(16)
                .fname("Jonny")
                .build();
        persist(vsp);
        fail("Should have failed validation");
    }

    private ValidatedSimplePerson persist(ValidatedSimplePerson person) {
        try(Session session = TestSessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(person);
            tx.commit();
        }
        return person;
    }
}
