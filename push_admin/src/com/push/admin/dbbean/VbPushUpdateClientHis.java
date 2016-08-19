package com.push.admin.dbbean;

import java.sql.Timestamp;

public class VbPushUpdateClientHis implements SqlBean {
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
				"push_id",
				"uuid",
				"status",
				"create_date",
				"c_update_date",
				"update_date",
				"update_comments",
				"url",
				"version",
				"type",
				"title",
				"comments",
				"country_name",
				"model_name",
				"version_name",
				"status_name",
				"type_name"
		};
	}
	
	private Long id;
	private Long pushId;
	private String uuid;
	private String comments;
	private Integer status;
	private Integer version;
	private String url;
	private Integer type;
	private String countryName;
	private String modelName;
	private String versionName;
	private Timestamp createDate;
	private Timestamp cUpdateDate;
	private Timestamp updateDate;
	private String updateComments;
	private String title;
	private String statusName;
	private String typeName;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=");
		sb.append(id);
		sb.append(",pushId=");
		sb.append(pushId);
		sb.append(",uuid=");
		sb.append(uuid);
		sb.append(",status=");
		sb.append(status);
		sb.append(",createDate=");
		sb.append(createDate);
		sb.append(",cUpdateDate=");
		sb.append(cUpdateDate);
		sb.append(",title=");
		sb.append(title);
		sb.append(",updateComments=");
		sb.append(updateComments);
		return sb.toString();
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}


	public Timestamp getcUpdateDate() {
		return cUpdateDate;
	}

	public void setcUpdateDate(Timestamp cUpdateDate) {
		this.cUpdateDate = cUpdateDate;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getCUpdateDate() {
		return cUpdateDate;
	}

	public void setCUpdateDate(Timestamp cUpdateDate) {
		this.cUpdateDate = cUpdateDate;
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
