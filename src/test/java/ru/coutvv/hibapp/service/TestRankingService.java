package ru.coutvv.hibapp.service;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Map;

import org.testng.annotations.Test;

import ru.coutvv.hibapp.entity.Person;

public class TestRankingService {

	RankingService rs = new HibernateRankingService();
	
	final String SUBJ = "StupidSubj";
	final String SKILL = "StupidSkill";
	final Integer RANK = 8;
	
	@Test
	public void testRankSvc() {
		
		rs.addRanking(SUBJ, "StupidObs", SKILL, RANK);
		assertEquals(rs.getRankingFor(SUBJ, SKILL), RANK);
	}
	
	@Test
	public void updateExistingRanking() {
		rs.addRanking("UpdateSubj", "UpdateObs", "UpdateSkill", 6);
		assertEquals(rs.getRankingFor("UpdateSubj", "UpdateSkill"), new Integer(6));
		rs.updateRanking("UpdateSubj", "UpdateObs", "UpdateSkill", 7);
		assertEquals(rs.getRankingFor("UpdateSubj", "UpdateSkill"), new Integer(7));
	}
	
	@Test 
	void updateUnexistingRanking() {
		assertEquals(rs.getRankingFor("UpdateNotExSubj", "UpdateNotExSkill"), new Integer(0));
		rs.updateRanking("UpdateNotExSubj", "JustObs", "UpdateNotExSkill", 7);
		assertEquals(rs.getRankingFor("UpdateNotExSubj", "UpdateNotExSkill"), new Integer(7));
	}
	
	@Test
	void removeRanking() {
		rs.addRanking("subjR", "obsR", "skillR", 6);
		assertEquals((int)rs.getRankingFor("subjR", "skillR"), 6);
		rs.removeRanking("subjR", "obsR", "skillR");
		assertEquals((int)rs.getRankingFor("subjR", "skillR"), 0);
	}
	
	@Test
	void removeUnexistentRanking() {
		rs.removeRanking("fuck!", "not", "nothing");
	}
	
	@Test
	void validateRankingAverage() {
		rs.addRanking("subjAv", "obsAv", "skillAv", 4);
		rs.addRanking("subjAv", "obsAv", "skillAv", 5);
		rs.addRanking("subjAv", "obsAv", "skillAv", 6);
		assertEquals((int)rs.getRankingFor("subjAv", "skillAv"), 5);

		rs.addRanking("subjAv", "obsAv", "skillAv", 7);
		rs.addRanking("subjAv", "obsAv", "skillAv", 8);
		assertEquals((int)rs.getRankingFor("subjAv", "skillAv"), 6);
	}
	
	@Test
	void findAllRankingsEmptySet() {
		assertEquals((int)rs.getRankingFor("Nobody", "Java"), 0);
		assertEquals((int)rs.getRankingFor("Nobody", "PHP"), 0);
		Map<String, Integer> ranks = rs.findRankingsFor("Nobody");
		assertEquals(ranks.size(), 0);
	}
	
	@Test
	void findAllRankings() {

		assertEquals((int)rs.getRankingFor("Somebody", "Java"), 0);
		assertEquals((int)rs.getRankingFor("Somebody", "PHP"), 0);
		rs.addRanking("Somebody", "Nobody", "Java", 9);
		rs.addRanking("Somebody", "Nobody", "Java", 7);
		rs.addRanking("Somebody", "Nobody", "PHP", 5);
		rs.addRanking("Somebody", "Nobody", "PHP", 7);
		Map<String, Integer> ranks = rs.findRankingsFor("Somebody");
		assertEquals(ranks.size(), 2);
		assertNotNull(ranks.get("Java"));
		assertEquals((int)ranks.get("Java"), 8);
		assertNotNull(ranks.get("PHP"));
		assertEquals((int)ranks.get("PHP"), 6);
	}
	
	@Test
	void findBestForSkill() {
		rs.addRanking("S1", "01", "sk01", 1);
		rs.addRanking("S1", "01", "sk01", 3);
		rs.addRanking("S2", "01", "sk01", 2);
		rs.addRanking("S2", "01", "sk01", 4);
		rs.addRanking("SGS7EDGE", "01", "sk01", 9);
		rs.addRanking("SGS7EDGE", "01", "sk01", 9);
		Person p = rs.findBestPersonFor("sk01");
		assertEquals(p.getName(), "SGS7EDGE");
	}
}
