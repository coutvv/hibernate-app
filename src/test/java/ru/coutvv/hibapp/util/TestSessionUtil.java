package ru.coutvv.hibapp.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestSessionUtil {

	private static final TestSessionUtil instance = new TestSessionUtil(); 
	private final SessionFactory factory;
	
	public TestSessionUtil() {
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.testcfg.xml").build();
		factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		
	}
	
	public static Session getSession() {
		return instance.factory.openSession();
	}
	
	@Test
	public void testSession() {
		
		try(Session session = SessionUtil.getSession();) {
			assertNotNull(session);
		}
	}
}
