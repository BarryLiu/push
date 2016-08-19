package com.rgk.push.dbbean ;

import java.sql.Timestamp;
import java.sql.Timestamp;


public class TClient implements SqlBean {
	
	public static final int RETRY_NULL = -1;	//
	public static final int RETRY_STRING = -2;	//
	public static final int RETRY_NORETRY = -3; //请求参数里面没有 retry 这个参数
	
	
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
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
	return new String[]{
"uuid",
"phone_number1",
"phone_number2",
"imei",
"device_product",
"device_customer",
"device_model",
"device_version",
"client_bin_version",
"client_apk_version",
"ip",
"country_name",
"language",
"register_date",
"update_date",
"update_comments",
"retry",
"timestamp"
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
sb.append(",deviceProduct=");	
sb.append(deviceProduct);	
sb.append(",deviceCustomer=");	
sb.append(deviceCustomer);	
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
sb.append(",language=");	
sb.append(language);	
sb.append(",registerDate=");	
sb.append(registerDate);	
sb.append(",updateDate=");	
sb.append(updateDate);	
sb.append(",updateComments=");	
sb.append(updateComments);
sb.append(",retry=");	
sb.append(retry);
sb.append(",timestamp=");	
sb.append(timestamp);
	return sb.toString();
}
	private String uuid;
	private String phoneNumber1;
	private String phoneNumber2;
	private String imei;
	private String deviceProduct;
	private String deviceCustomer;
	private String deviceModel;
	private String deviceVersion;
	private String clientBinVersion;
	private String clientApkVersion;
	private String ip;
	private String countryName;
	private String language;
	private java.sql.Timestamp registerDate;
	private java.sql.Timestamp updateDate;
	private String updateComments;
	private Integer retry;// yingjing.liu add ��� �ظ�����
	private String timestamp; //yingjing.liu 20160819 add 
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public Integer getRetry() {
		return retry;
	}
	public void setRetry(Integer retry) {
		this.retry = retry;
	}
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
	
	public String getDeviceProduct() {
		return deviceProduct;
	}

	public void setDeviceProduct(String deviceProduct) {
		this.deviceProduct = deviceProduct;
	}
	
	public String getDeviceCustomer() {
		return deviceCustomer;
	}

	public void setDeviceCustomer(String deviceCustomer) {
		this.deviceCustomer = deviceCustomer;
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
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public java.sql.Timestamp getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(java.sql.Timestamp registerDate) {
		this.registerDate = registerDate;
	}
	
	public java.sql.Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.sql.Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getUpdateComments() {
		return updateComments;
	}

	public void setUpdateComments(String updateComments) {
		this.updateComments = updateComments;
	}
}
