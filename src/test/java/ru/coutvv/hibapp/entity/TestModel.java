package ru.coutvv.hibapp.entity;

import static ru.coutvv.hibapp.util.EntityUtil.*;

import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestModel  {

	SessionFactory factory;
	@BeforeTest
	public void init() {
		StandardServiceRegistry reg = new StandardServiceRegistryBuilder().configure().build();
		factory = new MetadataSources(reg).buildMetadata().buildSessionFactory();
	}

	@Test
	public void testModelCreation() {
		try(Session session = factory.openSession()) {
			Transaction tx = session.beginTransaction();
			Person subject = savePerson(session, "John Lock");
			Person observer = savePerson(session, "David Key");
			Skill skill = saveSkill(session, "Java");
			
			Ranking rank = createRank(subject, observer, skill, 8);
			
			saveRanking(session, rank);
			tx.commit();
		}
	}
	
	@Test
	public void testRanking() {
		fillRanking();
		try(Session session = factory.openSession()) {
			Transaction tx = session.beginTransaction();
			Query<Ranking> query = session.createQuery("from Ranking r"
					+ " where r.subject.name=:name "
					+ " and r.skill.name=:skill",Ranking.class);
			query.setParameter("name", "Bitch");
			query.setParameter("skill", "childhood");
			IntSummaryStatistics stats = query.getResultList().stream().collect(Collectors.summarizingInt(Ranking::getRanking));
			long count = stats.getCount();
			int average = (int) stats.getAverage();
					
			tx.commit();
			session.close();
			System.out.println(count);
			System.out.println(average);
		}
	}
	
	@Test(dependsOnMethods="testRanking")
	public void changeRanking() {
		try(Session session = factory.openSession()) {
			Transaction tx = session.beginTransaction();
			Query<Ranking> query = session.createQuery("from Ranking r"
					+ " where r.subject.name=:name "
					+ " and r.observer.name=:obsName"
					+ " and r.skill.name=:skill",Ranking.class);
			query.setParameter("name", "Bitch");
			query.setParameter("obsName", "Muhammed");
			query.setParameter("skill", "childhood");
			Ranking rank = query.getSingleResult();
			rank.setRanking(5);
			tx.commit();
		}
	}
	
	@Test(dependsOnMethods="changeRanking")
	public void deleteRanking() {
		try(Session session = factory.openSession()) {
			Transaction tx = session.beginTransaction();
			Ranking rank = findRanking(session, "Bitch", "Mother", "childhood");
			session.delete(rank);
			tx.commit();
		}
	}
	
	private Ranking findRanking(Session session, String subject, String obs, String skill) {
		Ranking result = null;
		Query<Ranking> query = session.createQuery("from Ranking r"
				+ " where r.subject.name=:name "
				+ " and r.observer.name=:obsName"
				+ " and r.skill.name=:skill",Ranking.class);
		query.setParameter("name", subject);
		query.setParameter("obsName", obs);
		query.setParameter("skill", skill);
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
			System.out.println("this ranking does not exists");
		}
		return result;
	}
	
	private Ranking createRank(Person subject, Person observer, Skill skill, Integer ranking) {
		Ranking rank = new Ranking();
		rank.setSubject(subject);
		rank.setObserver(observer);
		rank.setSkill(skill);
		rank.setRanking(ranking);
		return rank;
	}	
	
	private void fillRanking() {
		try(Session session = factory.openSession()) {
			Transaction tx = session.beginTransaction();
			createRankingData(session, "Bitch", "Mother", "childhood", 8);
			createRankingData(session, "Bitch", "Honey", "childhood", 1);
			createRankingData(session, "Bitch", "Muhammed", "childhood", 2);
			tx.commit();
		}
	}
	
	private void createRankingData(Session session, String subjectName, String obsName, String skillName, Integer mark) {
		Person subject = savePerson(session, subjectName);
		Person observer = savePerson(session, obsName);
		Skill skill = saveSkill(session, skillName);
		Ranking rank = new Ranking();
		rank.setSubject(subject);
		rank.setObserver(observer);
		rank.setSkill(skill);
		rank.setRanking(mark);
		saveRanking(session, rank);
	}
	
	
	
}
