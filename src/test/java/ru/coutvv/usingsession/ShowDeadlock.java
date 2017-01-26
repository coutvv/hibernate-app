package ru.coutvv.usingsession;

import org.hibernate.LockMode;
import org.hibernate.PessimisticLockException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.coutvv.hibapp.util.TestSessionUtil;
import ru.coutvv.lifecycle.entity.Thing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by coutvv on 26.01.2017.
 */
public class ShowDeadlock {

    /**
     * Не работает =(
     * @throws InterruptedException
     */
    @Test
    public void showDeadlock() throws InterruptedException {
        Long id1;
        Long id2;
        try(Session session = TestSessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("delete from Thing").executeUpdate();
            Thing thing = new Thing();
            thing.setName("A");
            session.save(thing);
            id1 = thing.getId();
            thing = new Thing();
            thing.setName("B");
            session.save(thing);
            id2 = thing.getId();
            tx.commit();
        }

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> updatePublishers("session1 ", id1, id2));
        executor.submit(() -> updatePublishers("session2 ", id2, id1));
        executor.shutdown();

        if(!executor.awaitTermination(60, TimeUnit.SECONDS)) {
            executor.shutdownNow();
            if(!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("Executor can't stopped");
            }
        }
        try(Session session = TestSessionUtil.getSession()) {
            Query<Thing> query = session.createQuery("from Thing p order by p.name", Thing.class);
            String result = query.list().stream().map(Thing::getName).collect(Collectors.joining(","));
            Assert.assertEquals(result, "A,B");
        }

    }

    private void updatePublishers(String prefix, Long... ids) {
        try(Session session = TestSessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            for(Long id : ids) {
                Thread.sleep(1000);
                Thing thing = session.byId(Thing.class).load(id);
                session.lock(thing, LockMode.PESSIMISTIC_WRITE);
                thing.setName(prefix + thing.getName());
            }
            tx.commit();
        } catch (InterruptedException | PessimisticLockException e) {
            e.printStackTrace();
        }
    }
}
