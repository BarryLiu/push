package com.push.admin.dbbean ;

import java.sql.Timestamp;
import java.sql.Timestamp;
import java.sql.Timestamp;


public class VPushUpdateConfig implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
@Override
public String getTableName() {
	return "v_push_update_config";
}
@Override
public String getPrimaryKeyColumnName() {
	return "id";
}
@Override
public String[] getColumnNames() {
	return new String[]{
"id",
"title",
"comments",
"tcp_ip",
"tcp_port",
"time_out",
"http_addr",
"imei1",
"imei2",
"project_name",
"customer_name",
"country_name",
"model_name",
"version_name",
"push_date",
"create_date",
"update_date",
"update_comments",
"count_sending",
"count_sended",
"count_upgraded"};
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	
sb.append("id=");	
sb.append(id);	
sb.append(",title=");	
sb.append(title);	
sb.append(",comments=");	
sb.append(comments);	
sb.append(",tcpIp=");	
sb.append(tcpIp);	
sb.append(",tcpPort=");	
sb.append(tcpPort);	
sb.append(",timeOut=");	
sb.append(timeOut);	
sb.append(",httpAddr=");	
sb.append(httpAddr);	
sb.append(",imei1=");	
sb.append(imei1);	
sb.append(",imei2=");	
sb.append(imei2);	
sb.append(",projectName=");	
sb.append(projectName);	
sb.append(",customerName=");	
sb.append(customerName);	
sb.append(",countryName=");	
sb.append(countryName);	
sb.append(",modelName=");	
sb.append(modelName);	
sb.append(",versionName=");	
sb.append(versionName);	
sb.append(",pushDate=");	
sb.append(pushDate);	
sb.append(",createDate=");	
sb.append(createDate);	
sb.append(",updateDate=");	
sb.append(updateDate);	
sb.append(",updateComments=");	
sb.append(updateComments);	
sb.append(",countSending=");	
sb.append(countSending);	
sb.append(",countSended=");	
sb.append(countSended);	
sb.append(",countUpgraded=");	
sb.append(countUpgraded);
	return sb.toString();
}
	private Long id;
	private String title;
	private String comments;
	private String tcpIp;
	private Integer tcpPort;
	private Long timeOut;
	private String httpAddr;
	private String imei1;
	private String imei2;
	private String projectName;
	private String customerName;
	private String countryName;
	private String modelName;
	private String versionName;
	private java.sql.Timestamp pushDate;
	private java.sql.Timestamp createDate;
	private java.sql.Timestamp updateDate;
	private String updateComments;
	private Long countSending;
	private Long countSended;
	private Long countUpgraded;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getTcpIp() {
		return tcpIp;
	}

	public void setTcpIp(String tcpIp) {
		this.tcpIp = tcpIp;
	}
	
	public Integer getTcpPort() {
		return tcpPort;
	}

	public void setTcpPort(Integer tcpPort) {
		this.tcpPort = tcpPort;
	}
	
	public Long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Long timeOut) {
		this.timeOut = timeOut;
	}
	
	public String getHttpAddr() {
		return httpAddr;
	}

	public void setHttpAddr(String httpAddr) {
		this.httpAddr = httpAddr;
	}
	
	public String getImei1() {
		return imei1;
	}

	public void setImei1(String imei1) {
		this.imei1 = imei1;
	}
	
	public String getImei2() {
		return imei2;
	}

	public void setImei2(String imei2) {
		this.imei2 = imei2;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	
	public java.sql.Timestamp getPushDate() {
		return pushDate;
	}

	public void setPushDate(java.sql.Timestamp pushDate) {
		this.pushDate = pushDate;
	}
	
	public java.sql.Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.sql.Timestamp createDate) {
		this.createDate = createDate;
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
	
	public Long getCountSending() {
		return countSending;
	}

	public void setCountSending(Long countSending) {
		this.countSending = countSending;
	}
	
	public Long getCountSended() {
		return countSended;
	}

	public void setCountSended(Long countSended) {
		this.countSended = countSended;
	}
	
	public Long getCountUpgraded() {
		return countUpgraded;
	}

	public void setCountUpgraded(Long countUpgraded) {
		this.countUpgraded = countUpgraded;
	}
}
