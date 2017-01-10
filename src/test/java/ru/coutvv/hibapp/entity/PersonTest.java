package ru.coutvv.hibapp.entity;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static ru.coutvv.hibapp.util.EntityUtil.*;
public class PersonTest {

	SessionFactory factory;
	@BeforeTest
	public void init() {
		StandardServiceRegistry reg = new StandardServiceRegistryBuilder().configure().build();
		factory = new MetadataSources(reg).buildMetadata().buildSessionFactory();
	}
	
	@Test
	public void testSavePerson() {
		try(Session session = factory.openSession()) {
			Transaction tx = session.beginTransaction();
			String personName = "John Locke";
			Person person = savePerson(session, personName);
			System.out.println(findPerson(session, personName));
			tx.commit();
		} 
		
	}
	
}
