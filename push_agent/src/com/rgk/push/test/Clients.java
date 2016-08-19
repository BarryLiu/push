package com.rgk.push.test;

import com.rgk.push.dbbean.SqlBean;

public class Clients implements SqlBean {
	private Integer clientId;
	private String clientName;
	private String address;
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String getTableName() {
		return "clients";
	}
	@Override
	public String getPrimaryKeyColumnName() {
		return "client_id";
	}
	@Override
	public String[] getColumnNames() {
		return new String[] {
				"client_id", 
				"client_name", 
				"address"
				};
	}
}
