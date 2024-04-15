package com.javaex.miniproject01;

public class Person {
	private String name;
	private String mp;
	private String hp;
	
	public void setName(String name) {
		this.name = name;
	}
	public void setMp(String mp) {
		this.mp = mp;
	}
	public void setHp(String hp) {
		this.hp = hp;
	}
	public String getName() {
		return name;
	}
	
	public String getMp() {
		return mp;
	}
	public String getHp() {
		return hp;
	}
	@Override
	public String toString() {
		return name + "\t" + mp + "\t" + hp;
	}
	
	
}
