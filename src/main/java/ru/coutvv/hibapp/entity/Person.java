package ru.coutvv.hibapp.entity;

public class Person {

	public String name;

	public Person(String name) {
		super();
		this.name = name;
	}
	public Person(){}
	@Override
	public String toString() {
		return "Person [name=" + name + "]";
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
