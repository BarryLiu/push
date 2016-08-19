package com.rgk.push.test;

public class Student {
	public static final String TAG = "tag";
	private int number;
	private String name;
	private int age;
	private Scores scores;
	public Student() {}
	public Student(int number, String name, int age) {
		this.number = number;
		this.name = name;
		this.age = age;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Scores getScores() {
		return scores;
	}
	public void setScores(Scores scores) {
		this.scores = scores;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("number=");
		sb.append(number);
		sb.append(",name=");
		sb.append(name);
		sb.append(",age=");
		sb.append(age);
		return sb.toString();
	}
	
}
