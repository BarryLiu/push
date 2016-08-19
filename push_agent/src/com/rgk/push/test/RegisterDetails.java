package com.rgk.push.test;

public class RegisterDetails {
	private String uuid;
	private String sim1;
	private String sim2;
	private String imei;
	private String model;
	private String version;
	private String local;
	private Integer c_version;
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("uuid=");
		sb.append(uuid);
		sb.append(",sim1=");
		sb.append(sim1);
		sb.append(",sim2=");
		sb.append(sim2);
		sb.append(",imei=");
		sb.append(imei);
		sb.append(",model=");
		sb.append(model);
		sb.append(",version=");
		sb.append(version);
		sb.append(",local=");
		sb.append(local);
		sb.append(",c_version=");
		sb.append(c_version);
		return sb.toString();
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getSim1() {
		return sim1;
	}
	public void setSim1(String sim1) {
		this.sim1 = sim1;
	}
	public String getSim2() {
		return sim2;
	}
	public void setSim2(String sim2) {
		this.sim2 = sim2;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public Integer getClientVersion() {
		return c_version;
	}
	public void setClientVersion(Integer c_version) {
		this.c_version = c_version;
	}
	public void setC_version(Integer c_version) {
		this.c_version = c_version;
	}
}
