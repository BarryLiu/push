package com.rgk.push.test;

public class Scores {
	private int math;
	private int history;
	public Scores() {}
	public Scores(int math, int history) {
		this.math = math;
		this.history = history;
	}
	public int getMath() {
		return math;
	}
	public void setMath(int math) {
		this.math = math;
	}
	public int getHistory() {
		return history;
	}
	public void setHistory(int history) {
		this.history = history;
	}
}
