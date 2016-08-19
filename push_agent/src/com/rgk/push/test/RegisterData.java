package com.rgk.push.test;


public class RegisterData {
	private String name;
	private RegisterDetails details;
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name=");
		sb.append(name);
		sb.append(",details={");
		sb.append(details.toString());
		sb.append("}");
		return sb.toString();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public RegisterDetails getDetails() {
		return details;
	}
	public void setDetails(RegisterDetails details) {
		this.details = details;
	}
}
