package com.rgk.push.bean;

public class RequestData {
	private String uuid;

	@Override
	public String toString() {
		return "uuid="+uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
