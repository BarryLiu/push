package com.push.admin.dbbean ;

import java.sql.Timestamp;
import java.sql.Timestamp;
import java.sql.Timestamp;


public class VPushUpdateClientHis implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
@Override
public String getTableName() {
	return "v_push_update_client_his";
}
@Override
public String getPrimaryKeyColumnName() {
	return "id";
}
@Override
public String[] getColumnNames() {
	return new String[]{
"id",
"uuid",
"push_id",
"title",
"comments",
"country_name",
"model_name",
"version_name",
"type",
"type_name",
"version",
"url",
"status",
"status_name",
"create_date",
"c_update_date",
"update_date",
"update_comments"};
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	
sb.append("id=");	
sb.append(id);	
sb.append(",uuid=");	
sb.append(uuid);	
sb.append(",pushId=");	
sb.append(pushId);	
sb.append(",title=");	
sb.append(title);	
sb.append(",comments=");	
sb.append(comments);	
sb.append(",countryName=");	
sb.append(countryName);	
sb.append(",modelName=");	
sb.append(modelName);	
sb.append(",versionName=");	
sb.append(versionName);	
sb.append(",type=");	
sb.append(type);	
sb.append(",typeName=");	
sb.append(typeName);	
sb.append(",version=");	
sb.append(version);	
sb.append(",url=");	
sb.append(url);	
sb.append(",status=");	
sb.append(status);	
sb.append(",statusName=");	
sb.append(statusName);	
sb.append(",createDate=");	
sb.append(createDate);	
sb.append(",cUpdateDate=");	
sb.append(cUpdateDate);	
sb.append(",updateDate=");	
sb.append(updateDate);	
sb.append(",updateComments=");	
sb.append(updateComments);
	return sb.toString();
}
	private Long id;
	private String uuid;
	private Long pushId;
	private String title;
	private String comments;
	private String countryName;
	private String modelName;
	private String versionName;
	private Integer type;
	private String typeName;
	private Integer version;
	private String url;
	private Integer status;
	private String statusName;
	private java.sql.Timestamp createDate;
	private java.sql.Timestamp cUpdateDate;
	private java.sql.Timestamp updateDate;
	private String updateComments;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Long getPushId() {
		return pushId;
	}

	public void setPushId(Long pushId) {
		this.pushId = pushId;
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
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
	
	public java.sql.Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.sql.Timestamp createDate) {
		this.createDate = createDate;
	}
	
	public java.sql.Timestamp getCUpdateDate() {
		return cUpdateDate;
	}

	public void setCUpdateDate(java.sql.Timestamp cUpdateDate) {
		this.cUpdateDate = cUpdateDate;
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
