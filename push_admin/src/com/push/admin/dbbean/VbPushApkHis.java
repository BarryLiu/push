package com.push.admin.dbbean;

import java.sql.Timestamp;

public class VbPushApkHis implements SqlBean {

	@Override
	public String getTableName() {
		return "v_push_apk_his";
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
				"push_date",
				"title",
				"package_name",
				"icon",
				"url",
				"status_name",
				"install_type_name",
				"comments",
				"apks_id",
				"pushed_date",
				"install_type",
				"country_name",
				"model_name",
				"version_name",
				"c_update_date",
				"update_date",
				"update_comments"
		};
	}
	
	private Long id;
	private Long pushId;
	private String uuid;
	private Integer status;
	private Timestamp pushDate;
	private Timestamp pushedDate;
	private Timestamp cUpdateDate;
	private Timestamp updateDate;
	private String title;
	private String comments;
	private Long apksId;
	private Integer installType;
	private String countryName;
	private String modelName;
	private String versionName;
	private String updateComments;
	private String installTypeName;
	private String statusName;
	private String icon;
	private String url;
	private String packageName;
	
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
		sb.append(",pushDate=");
		sb.append(pushDate);
		sb.append(",statusName=");
		sb.append(statusName);
		sb.append(",cUpdateDate=");
		sb.append(cUpdateDate);
		sb.append(",updateDate=");
		sb.append(updateDate);
		sb.append(",updateComments=");
		sb.append(updateComments);
		return sb.toString();
	}
	public Long getId() {
		return id;
	}
	
	public Timestamp getcUpdateDate() {
		return cUpdateDate;
	}

	public void setcUpdateDate(Timestamp cUpdateDate) {
		this.cUpdateDate = cUpdateDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Timestamp getPushedDate() {
		return pushedDate;
	}

	public void setPushedDate(Timestamp pushedDate) {
		this.pushedDate = pushedDate;
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

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getInstallTypeName() {
		return installTypeName;
	}

	public void setInstallTypeName(String installTypeName) {
		this.installTypeName = installTypeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getPushDate() {
		return pushDate;
	}

	public void setPushDate(Timestamp pushDate) {
		this.pushDate = pushDate;
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
