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
	
	static Person findPerson(Session session, String name) {
		Query<Person> query = 
				session.createQuery("from Person p where p.name = :name", Person.class);
		query.setParameter("name", name);
		Person person = null;
		try {
			person = query.getSingleResult();
		} catch (Exception e) {
			System.err.println("Person "+ name +" is not here");
		}
		return person;
	}
	
	static Person savePerson(Session session, String name) {
		Person person = findPerson(session, name);
		if(person == null) {
			person = new Person(name);
			session.save(person);
		}
		return person;
	}
}
