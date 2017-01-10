package ru.coutvv.hibapp.service;

import java.util.Map;

import ru.coutvv.hibapp.entity.Person;

public interface RankingService {

	Integer getRankingFor(String subject, String skill);
	
	void addRanking(String subject, String observer, String skill, Integer ranking);
	
	void updateRanking(String subject, String observer, String skill, Integer ranking);
	
	void removeRanking(String subject, String observer, String skill);
	
	Map<String, Integer> findRankingsFor(String subject);
	
	Person findBestPersonFor(String skill);
}
