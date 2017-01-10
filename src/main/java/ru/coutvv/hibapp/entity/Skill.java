package ru.coutvv.hibapp.entity;

public class Skill {

	String name;

	public Skill(String name) {
		super();
		this.name = name;
	}
	public Skill(){}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Skill [name=" + name + "]";
	}
}
