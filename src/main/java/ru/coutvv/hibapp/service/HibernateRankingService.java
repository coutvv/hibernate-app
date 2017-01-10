package ru.coutvv.hibapp.service;


import static ru.coutvv.hibapp.util.EntityUtil.findRankingBy;
import static ru.coutvv.hibapp.util.EntityUtil.savePerson;
import static ru.coutvv.hibapp.util.EntityUtil.*;
import static ru.coutvv.hibapp.util.EntityUtil.saveSkill;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.coutvv.hibapp.entity.Person;
import ru.coutvv.hibapp.entity.Ranking;
import ru.coutvv.hibapp.entity.Skill;
import ru.coutvv.hibapp.util.SessionUtil;

public class HibernateRankingService implements RankingService	 {

	@Override
	public Integer getRankingFor(String subject, String skill) {
		Integer result = null;
		try(Session session = SessionUtil.getSession()) {
			Transaction tx = session.beginTransaction();
			List<Ranking> ranks = findRankingBy(session, subject,skill);
			IntSummaryStatistics stats = ranks.stream().collect(Collectors.summarizingInt(Ranking::getRanking));
			result = (int) stats.getAverage();
			tx.commit();
		}
		return result;
	}

	@Override
	public void addRanking(String subjectName, String obsName, String skillName, Integer ranking) {
		try(Session session = SessionUtil.getSession()){
			Transaction tx = session.beginTransaction();
			Person subject = savePerson(session, subjectName);
			Person observer = savePerson(session, obsName);
			Skill skill = saveSkill(session, skillName);
			Ranking rank = new Ranking();
			rank.setSubject(subject);
			rank.setObserver(observer);
			rank.setSkill(skill);
			rank.setRanking(ranking);
			saveRanking(session, rank);
			tx.commit();
		}
	}

	@Override
	public void updateRanking(String subjectName, String obsName, String skillName, Integer ranking) {
		try(Session session = SessionUtil.getSession()){
			Transaction tx = session.beginTransaction();
			Ranking rank = findRanking(session, subjectName, obsName, skillName);
			if(rank == null) {
				addRanking(subjectName, obsName, skillName, ranking);
			} else {
				rank.setRanking(ranking);
			}
			tx.commit();
		}
	}

	@Override
	public void removeRanking(String subject, String observer, String skill) {
		try(Session session = SessionUtil.getSession()) {
			Transaction tx = session.beginTransaction();
			Ranking rank = findRanking(session, subject, observer, skill);
			if(rank != null) {
				session.delete(rank);
			}
			tx.commit();
		}
	}

	@Override
	public Map<String, Integer> findRankingsFor(String subject) {
		Map<String, Integer> result;
		try(Session session = SessionUtil.getSession()) {
			Transaction tx = session.beginTransaction();
			result = getAllSkillRanks(session, subject);
			tx.commit();
		}
		return result;
	}

	@Override
	public Person findBestPersonFor(String skill) {
		Person result = null;
		try(Session session = SessionUtil.getSession()) {
			Transaction tx = session.beginTransaction();
			result = findBestPersonForSkill(session, skill);
			tx.commit();
		}
		return result;
	}
	
	
}
