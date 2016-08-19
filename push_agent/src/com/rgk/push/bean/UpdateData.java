package com.rgk.push.bean;

public class UpdateData {
	private Long server_id;
	private String status;
	private String date;
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("server_id=");
		sb.append(server_id);
		sb.append(",status=");
		sb.append(status);
		sb.append(",date=");
		sb.append(date);
		return sb.toString();
	}
	public boolean hasNullProp() {
		return server_id==null || status==null || date==null;
	}
	
	public Long getServer_id() {
		return server_id;
	}
	public void setServer_id(Long server_id) {
		this.server_id = server_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
