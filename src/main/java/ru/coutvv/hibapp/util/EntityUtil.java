package ru.coutvv.hibapp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.Query;

import ru.coutvv.hibapp.entity.Person;
import ru.coutvv.hibapp.entity.Ranking;
import ru.coutvv.hibapp.entity.Skill;

public class EntityUtil {

	public static Person findPerson(Session session, String name) {
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
	
	public static Person savePerson(Session session, String name) {
		Person person = findPerson(session, name);
		if(person == null) {
			person = new Person(name);
			session.save(person);
		}
		return person;
	}
	
	public static Skill findSkill(Session session, String name) {
		Query<Skill> query = 
				session.createQuery("from Skill p where p.name = :name", Skill.class);
		query.setParameter("name", name);
		Skill skill = null;
		try {
			skill = query.getSingleResult();
		} catch (Exception e) {
			System.err.println("Skill "+ name +" is not here");
		}
		return skill;
	}
	
	public static Skill saveSkill(Session session, String name) {
		Skill skill = findSkill(session, name);
		if(skill == null) {
			skill = new Skill(name);
			session.save(skill);
		}
		return skill;
	}
	
	public static Ranking findRanking(Session session, String subject, String obs, String skill) {
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
	
	public static List<Ranking> findRankingBy(Session session, String subject, String skill) {
		Ranking result = null;
		Query<Ranking> query = session.createQuery("from Ranking r"
				+ " where r.subject.name=:name "
				+ " and r.skill.name=:skill",Ranking.class);
		query.setParameter("name", subject);
		query.setParameter("skill", skill);
		return query.getResultList();
	}
	
	public static Map<String, Integer> getAllSkillRanks(Session session, String subject) {
		Query<Ranking> query = session.createQuery("from Ranking r"
				+ " where r.subject.name=:name order by r.skill.name",Ranking.class);
		query.setParameter("name", subject);
		List<Ranking> ranks = query.getResultList();
		Map<String, Integer> result = new HashMap<>();
		String lastSkillName = ""; 
		int sum = 0, count = 0;
		for(Ranking r : ranks) {
			if(!lastSkillName.equals(r.getSkill().getName())) {
				sum = 0;
				count = 0;
				lastSkillName = r.getSkill().getName();
			}
			sum += r.getRanking();
			count++;
			result.put(r.getSkill().getName(), sum/count);
		}
		return result;
	}
	
	public static void saveRanking(Session session, Ranking rank) {
		session.save(rank);
	}
	
	public static Person findBestPersonForSkill(Session session, String skill){
		Query<Object[]> query = session.createQuery("select r.subject.name, avg(r.ranking) "
				+ " from Ranking r where "
				+ " r.skill.name = :skill "
				+ " group by r.subject.name "
				+ " order by avg(r.ranking) desc", Object[].class);
		query.setParameter("skill", skill);
		List<Object[]> list = query.getResultList();
		if(list.size() > 0) {
			return findPerson(session, (String)list.get(0)[0]);
		}
		return null;
	}
}
