package ru.coutvv.lifecycle;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.coutvv.hibapp.util.TestSessionUtil;
import ru.coutvv.lifecycle.entity.validation.Coordinate;
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

    private Coordinate persist(Coordinate person) {
        try(Session session = TestSessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(person);
            tx.commit();
        }
        return person;
    }

    @DataProvider(name = "validCoordinates")
    private Object[][] validCoordinates(){
        return new Object[][]{
                {1, 1},
                {1, 0},
                {-1, 1},
                {0, 1},
                {-1, 0},
                {0, -1},
                {1, -1},
                {0, 0},

        };
    }

    @Test(dataProvider = "validCoordinates")
    public void testValidCoordinate(Integer x, Integer y) {
        Coordinate coord = Coordinate.builder().x(x).y(y).build();
        persist(coord);
    }

    @Test(expectedExceptions =  ConstraintViolationException.class)
    public void testInvalidCoordinate(){
        testValidCoordinate(-1, -1);
        fail("should have gotten a constraint violation");
    }

}
