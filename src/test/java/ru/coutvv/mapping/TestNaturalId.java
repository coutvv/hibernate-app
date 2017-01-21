package ru.coutvv.mapping;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.coutvv.hibapp.util.TestSessionUtil;
import ru.coutvv.mapping.naturalid.Employee;
import ru.coutvv.mapping.naturalid.SimpleNaturalIdEmployee;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;


/**
 * Created by coutvv on 21.01.2017.
 */
public class TestNaturalId {

    TestSessionUtil util = new TestSessionUtil("hibmsgapp.cfg.xml");

    @Test
    public void test() {
        Integer id = create("Tray", 123).getId();

        try(Session session = util.getSessionObj()) {
            Transaction tx = session.beginTransaction();
            SimpleNaturalIdEmployee objById = session.byId(SimpleNaturalIdEmployee.class).load(id);

            SimpleNaturalIdEmployee objByBadge = session.bySimpleNaturalId(SimpleNaturalIdEmployee.class).load(123);
            Assert.assertEquals(objById, objByBadge);
            tx.commit();

        }
    }

    @Test
    public  void testComplexNaturalId() {
        Employee first  = createEmployee("John", 1, 1);
        try(Session session = util.getSessionObj()) {
            Transaction tx = session.beginTransaction();
            Employee second = session.byNaturalId(Employee.class)
                    .using("section", 1)
                    .using("department", 1)
                    .load();
            //fuck off idea...
            assertNotNull(second);
            assertEquals(first, second); //did'nt work, i dunno why...
            tx.commit();
        }
    }

    @Test
    public  void testComplexNaturalIdAndRef() {
        Employee first  = createEmployee("John", 2, 3);
        try(Session session = util.getSessionObj()) {
            Transaction tx = session.beginTransaction();
            Employee second = session.byNaturalId(Employee.class)
                    .using("section", 2)
                    .using("department", 3)
                    .getReference();
            System.out.println(first.equals(second));
            System.out.println(first);
            System.out.println(second);
            assertEquals(first, second); //did'nt work, i dunno why... //refs is others... so stupid
            tx.commit();
        }
    }

    private SimpleNaturalIdEmployee create(String name, int badge) {
        SimpleNaturalIdEmployee result = new SimpleNaturalIdEmployee();
        result.setName(name);
        result.setBadge(badge);

        try(Session session = util.getSessionObj()) {
            Transaction tx = session.beginTransaction();
            session.save(result);
            tx.commit();
        }
        return result;
    }

    private Employee createEmployee(String name, int section, int department) {
        Employee result = new Employee();
        result.setName(name);
        result.setDepartment(department);
        result.setSection(section);
        try(Session session = util.getSessionObj()) {
            Transaction tx = session.beginTransaction();
            session.save(result);
            tx.commit();
        }
        return result;
    }
}
