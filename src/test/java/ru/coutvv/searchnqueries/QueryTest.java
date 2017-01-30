package ru.coutvv.searchnqueries;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class QueryTest {

	static SessionFactory factory;
	
	static {
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.query.xml").build();
		factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	}
	
	Session session;
	Transaction tx;
	
	@BeforeMethod
	public void populateData() {
		try(Session session = factory.openSession()) {
			Transaction tx = session.beginTransaction();
			Supplier sup = new Supplier(); sup.setName("Hardware Inc.");
			sup.getProducts().add(createProd(sup, "Optical Wheel Mouse", "Mouse", 5.00));
			sup.getProducts().add(createProd(sup, "Trackball Mouse", "Mouse", 22.00));
			session.save(sup);
			
			sup = new Supplier();sup.setName("Next Sup"); 
			sup.getProducts().add(createSoft(sup, "Super Detect", "Antivirus", 13.83, "1.0"));
			sup.getProducts().add(createSoft(sup, "Wildcat", "Browser", 2.83, "22.2"));
			sup.getProducts().add(createProd(sup, "AxeGrinder", "Gaming Mouse", 121.00));
			session.save(sup);
			tx.commit();
		}
		this.session = factory.openSession();
		this.tx = this.session.beginTransaction();
	}
	
	@AfterMethod
	public void closeSession() {
		session.createQuery("delete from Product").executeUpdate();
		session.createQuery("delete from Supplier").executeUpdate();
		if(tx.isActive()) {
			tx.commit();
		}
		if(session.isOpen()) {
			session.close();
		}
	}
	
	@Test
	public void testNamedQuery() {
		Query query = session.getNamedQuery("supplier.findAll");
		List<Supplier> sups = query.getResultList();
		Assert.assertEquals(sups.size(), 2);
	}
	
	@Test
	public void testSimpleQuery() {
		Query<Product> query = session.createQuery("from Product", Product.class);
		query.setComment("this is only a query for product");
		List<Product> products = query.getResultList();
		Assert.assertEquals(products.size(), 5);
	}
	
	@Test
	public void testPagination() {
		
		try(Session session = factory.openSession()) {
			Transaction tx = session.beginTransaction();
			session.createQuery("delete from Product").executeUpdate();
			session.createQuery("delete from Supplier").executeUpdate();
			
			for(int i = 0; i < 30; i++ ) {
				Supplier sup = new Supplier();
				sup.setName(String.format("supplier %02d", i));
				session.save(sup);
			}
			tx.commit();
		}
		
		try(Session session = factory.openSession()) {
			Query<String> query = session.createQuery("select s.name from Supplier s order by s.name", String.class);
			query.setFirstResult(5);
			query.setMaxResults(5);
			List<String> suppliers = query.getResultList();
			String list = suppliers.stream().collect(Collectors.joining(","));
			Assert.assertEquals(list, "supplier 05,supplier 06,supplier 07,supplier 08,supplier 09");
		}
	}
	
	private Product createProd(Supplier sup, String name, String descr, double price) {
		Product result = new Product(); 
		result.setName(name);
		result.setSupplier(sup);
		result.setDescription(descr);
		result.setPrice(price);
		return result;
	}
	
	private Software createSoft(Supplier sup, String name, String descr, double price, String version) {
		Software result = new Software();
		result.setName(name);
		result.setSupplier(sup);
		result.setDescription(descr);
		result.setPrice(price);
		result.setVersion(version);
		return result;
	}
}
