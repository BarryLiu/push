package com.push.admin.dbbean ;

import java.sql.Timestamp;
import java.sql.Timestamp;
import java.sql.Timestamp;


public class TPushApk implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
@Override
public String getTableName() {
	return "t_push_apk";
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
"apks_id",
"install_type",
"imei1",
"imei2",
"country_name",
"project_name",
"customer_name",
"model_name",
"version_name",
"push_date",
"create_date",
"update_date",
"update_comments"};
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
sb.append(",apksId=");	
sb.append(apksId);	
sb.append(",installType=");	
sb.append(installType);	
sb.append(",imei1=");	
sb.append(imei1);	
sb.append(",imei2=");	
sb.append(imei2);	
sb.append(",countryName=");	
sb.append(countryName);	
sb.append(",projectName=");	
sb.append(projectName);	
sb.append(",customerName=");	
sb.append(customerName);	
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
	return sb.toString();
}
	private Long id;
	private String title;
	private String comments;
	private Long apksId;
	private Integer installType;
	private String imei1;
	private String imei2;
	private String countryName;
	private String projectName;
	private String customerName;
	private String modelName;
	private String versionName;
	private java.sql.Timestamp pushDate;
	private java.sql.Timestamp createDate;
	private java.sql.Timestamp updateDate;
	private String updateComments;
	
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
	
	public Long getApksId() {
		return apksId;
	}

	public void setApksId(Long apksId) {
		this.apksId = apksId;
	}
	
	public Integer getInstallType() {
		return installType;
	}

	public void setInstallType(Integer installType) {
		this.installType = installType;
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
	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
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
}
