package com.jt.vo;

public class Hash {
	private String name;
	private Integer age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Hash(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}
	public Hash() {
		super();
	}
	@Override
	public String toString() {
		return "Hash [name=" + name + ", age=" + age + "]";
	}
	
}
