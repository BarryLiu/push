package com.push.admin.dbbean;

import java.sql.Timestamp;

public class TbClient implements SqlBean {

	@Override
	public String getTableName() {
		return "t_client";
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return "uuid";
	}

	@Override
	public String[] getColumnNames() {
		return new String[]{
				"uuid",
				"phone_number1",
				"phone_number2",
				"imei",
				"device_model",
				"device_version",
				"client_bin_version",
				"client_apk_version",
				"ip",
				"country_name",
				"country_addr",
				"register_date",
				"update_date",
				"update_comments"
				
		};
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("uuid=");
		sb.append(uuid);
		sb.append(",phoneNumber1=");
		sb.append(phoneNumber1);
		sb.append(",phoneNumber2=");
		sb.append(phoneNumber2);
		sb.append(",imei=");
		sb.append(imei);
		sb.append(",deviceModel=");
		sb.append(deviceModel);
		sb.append(",deviceVersion=");
		sb.append(deviceVersion);
		sb.append(",clientBinVersion=");
		sb.append(clientBinVersion);
		sb.append(",clientApkVersion=");
		sb.append(clientApkVersion);
		sb.append(",ip=");
		sb.append(ip);
		sb.append(",countryName=");
		sb.append(countryName);
		sb.append(",countryAddr=");
		sb.append(countryAddr);
		sb.append(",registerDate=");
		sb.append(registerDate);
		sb.append(",updateDate=");
		sb.append(updateDate);
		sb.append(",updateComments=");
		sb.append(updateComments);
		return sb.toString();
	}
	
	private String uuid;
	private String phoneNumber1;
	private String phoneNumber2;
	private String imei;
	private String deviceModel;
	private String deviceVersion;
	private String clientBinVersion;
	private String clientApkVersion;
	private String ip;
	private String countryName;
	private String countryAddr;
	private Timestamp registerDate;
	private Timestamp updateDate;
	private String updateComments;
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPhoneNumber1() {
		return phoneNumber1;
	}

	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	public String getPhoneNumber2() {
		return phoneNumber2;
	}

	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	public String getClientBinVersion() {
		return clientBinVersion;
	}

	public void setClientBinVersion(String clientBinVersion) {
		this.clientBinVersion = clientBinVersion;
	}

	public String getClientApkVersion() {
		return clientApkVersion;
	}

	public void setClientApkVersion(String clientApkVersion) {
		this.clientApkVersion = clientApkVersion;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryAddr() {
		return countryAddr;
	}

	public void setCountryAddr(String countryAddr) {
		this.countryAddr = countryAddr;
	}

	public Timestamp getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateComments() {
		return updateComments;
	}

	public void setUpdateComments(String updateComments) {
		this.updateComments = updateComments;
	}
	

}
