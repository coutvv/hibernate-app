package ru.coutvv.hibapp.entity;

import org.testng.annotations.Test;

public class TestModel  {

	@Test
	public void testModelCreation() {
		Person subject = new Person("John Lock");
		Person observer = new Person("David Key");
		Skill skill = new Skill("Java");
		
		Ranking rank = new Ranking();
		rank.setSubject(subject);
		rank.setObserver(observer);
		rank.setSkill(skill);
		rank.setRanking(8);
		
		System.out.println(rank);
	}
}
