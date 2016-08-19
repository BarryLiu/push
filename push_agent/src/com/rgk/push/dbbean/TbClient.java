package com.rgk.push.dbbean;

import java.sql.Timestamp;

public class TbClient implements SqlBean {
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(uuid);
		sb.append(":");
		sb.append(phoneNumber1);
		sb.append(":");
		sb.append(phoneNumber2);
		sb.append(":");
		sb.append(imei);
		sb.append(":");
		sb.append(deviceModel);
		sb.append(":");
		sb.append(deviceVersion);
		sb.append(":");
		sb.append(clientBinVersion);
		sb.append(":");
		sb.append(clientApkVersion);
		sb.append(":");
		sb.append(ip);
		sb.append(":");
		sb.append(countryName);
		sb.append(":");
		sb.append(countryAddr);
		sb.append(":");
		sb.append(registerDate);
		sb.append(":");
		sb.append(updateDate);
		sb.append(":");
		sb.append(updateComments);
		sb.append(":");
		return sb.toString();
	}

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
		return new String[] {
				"uuid", //uuid
				"phone_number1", //sim1
				"phone_number2", //sim2
				"imei", //imei
				"device_model", //model
				"device_version", //version
				"client_bin_version", //c_version
				"client_apk_version", //c_version
				"ip", //ip
				"country_name", //country name
				"country_addr", //cuntry addr
				"register_date",
				"update_date",
				"update_comments"
		};
	}
	
	/*** for JSON start */
	public void setSim1(String sim1) {
		this.phoneNumber1 = sim1;
	}
	public void setSim2(String sim2) {
		this.phoneNumber2 = sim2;
	}
	public void setModel(String model) {
		this.deviceModel = model;
	}
	public void setVersion(String version) {
		this.deviceVersion = version;
	}
	public void setC_bin_version(String c_bin_version) {
		this.clientBinVersion = c_bin_version;
	}
	public void setC_apk_version(String c_apk_version) {
		this.clientApkVersion = c_apk_version;
	}
	/*** for JSON end */

	/*** get/set method */
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

	/**
	 * @return the countryAddr
	 */
	public String getCountryAddr() {
		return countryAddr;
	}

	/**
	 * @param countryAddr the countryAddr to set
	 */
	public void setCountryAddr(String countryAddr) {
		this.countryAddr = countryAddr;
	}

	/**
	 * @return the iP
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	/**
	 * @return the iP
	 */
	public String getIp() {
		return ip;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}
}
