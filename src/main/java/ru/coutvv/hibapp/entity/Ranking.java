package ru.coutvv.hibapp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Ranking {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@OneToOne
	Person subject;
	@OneToOne
	Person observer;

	@OneToOne
	Skill skill;
	
	Integer ranking;

	public Ranking(){
		
	}
	
	public Person getSubject() {
		return subject;
	}

	public void setSubject(Person subject) {
		this.subject = subject;
	}

	public Person getObserver() {
		return observer;
	}

	public void setObserver(Person observer) {
		this.observer = observer;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	@Override
	public String toString() {
		return "Ranking [subject=" + subject + ", observer=" + observer + ", skill=" + skill + ", ranking=" + ranking
				+ "]";
	}
	

}
