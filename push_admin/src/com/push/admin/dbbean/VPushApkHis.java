package com.push.admin.dbbean ;

import java.sql.Timestamp;
import java.sql.Timestamp;
import java.sql.Timestamp;
import java.sql.Timestamp;


public class VPushApkHis implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
@Override
public String getTableName() {
	return "v_push_apk_his";
}
@Override
public String getPrimaryKeyColumnName() {
	return "uuid";
}
@Override
public String[] getColumnNames() {
	return new String[]{
"uuid",
"id",
"push_id",
"status",
"status_name",
"c_update_date",
"update_comments",
"update_date",
"title",
"comments",
"apks_id",
"install_type",
"install_type_name",
"country_name",
"model_name",
"version_name",
"pushed_date",
"url",
"push_date",
"icon",
"package_name"};
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	
sb.append("uuid=");	
sb.append(uuid);	
sb.append(",id=");	
sb.append(id);	
sb.append(",pushId=");	
sb.append(pushId);	
sb.append(",status=");	
sb.append(status);	
sb.append(",statusName=");	
sb.append(statusName);	
sb.append(",cUpdateDate=");	
sb.append(cUpdateDate);	
sb.append(",updateComments=");	
sb.append(updateComments);	
sb.append(",updateDate=");	
sb.append(updateDate);	
sb.append(",title=");	
sb.append(title);	
sb.append(",comments=");	
sb.append(comments);	
sb.append(",apksId=");	
sb.append(apksId);	
sb.append(",installType=");	
sb.append(installType);	
sb.append(",installTypeName=");	
sb.append(installTypeName);	
sb.append(",countryName=");	
sb.append(countryName);	
sb.append(",modelName=");	
sb.append(modelName);	
sb.append(",versionName=");	
sb.append(versionName);	
sb.append(",pushedDate=");	
sb.append(pushedDate);	
sb.append(",url=");	
sb.append(url);	
sb.append(",pushDate=");	
sb.append(pushDate);	
sb.append(",icon=");	
sb.append(icon);	
sb.append(",packageName=");	
sb.append(packageName);
	return sb.toString();
}
	private String uuid;
	private Long id;
	private Long pushId;
	private Integer status;
	private String statusName;
	private java.sql.Timestamp cUpdateDate;
	private String updateComments;
	private java.sql.Timestamp updateDate;
	private String title;
	private String comments;
	private Long apksId;
	private Integer installType;
	private String installTypeName;
	private String countryName;
	private String modelName;
	private String versionName;
	private java.sql.Timestamp pushedDate;
	private String url;
	private java.sql.Timestamp pushDate;
	private String icon;
	private String packageName;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPushId() {
		return pushId;
	}

	public void setPushId(Long pushId) {
		this.pushId = pushId;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	public java.sql.Timestamp getCUpdateDate() {
		return cUpdateDate;
	}

	public void setCUpdateDate(java.sql.Timestamp cUpdateDate) {
		this.cUpdateDate = cUpdateDate;
	}
	
	public String getUpdateComments() {
		return updateComments;
	}

	public void setUpdateComments(String updateComments) {
		this.updateComments = updateComments;
	}
	
	public java.sql.Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.sql.Timestamp updateDate) {
		this.updateDate = updateDate;
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
	
	public String getInstallTypeName() {
		return installTypeName;
	}

	public void setInstallTypeName(String installTypeName) {
		this.installTypeName = installTypeName;
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
	
	public java.sql.Timestamp getPushedDate() {
		return pushedDate;
	}

	public void setPushedDate(java.sql.Timestamp pushedDate) {
		this.pushedDate = pushedDate;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public java.sql.Timestamp getPushDate() {
		return pushDate;
	}

	public void setPushDate(java.sql.Timestamp pushDate) {
		this.pushDate = pushDate;
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
}
