package ru.coutvv.lifecycle.entity;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by coutvv on 21.01.2017.
 */
public class JPASessionUtil {
    private static Map<String, EntityManagerFactory> persistenceUnits = new HashMap<>();

    public static synchronized EntityManager getEntityManager(String persistenceUnitName) {
        persistenceUnits.putIfAbsent(persistenceUnitName, Persistence.createEntityManagerFactory(persistenceUnitName));
        return persistenceUnits.get(persistenceUnitName).createEntityManager();
    }

    public static Session getSession(String persistentUnitName) {
        return getEntityManager(persistentUnitName).unwrap(Session.class);
    }
}
