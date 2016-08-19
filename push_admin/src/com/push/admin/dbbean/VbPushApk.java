package com.push.admin.dbbean;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class VbPushApk implements SqlBean {
	@Override
	public String getTableName() {
		return "v_push_apk";
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
				"install_type_name",
				"country_name",
				"push_model_name",
				"push_version_name",
				"model_name",
				"version_name",
				"push_date",
				"create_date",
				"update_date",
				"update_comments",
				"name",
				"package_name",
				"icon",
				"description",
				"channel",
				"size",
				"version_code",
				"version_name",
				"url",
				"status",
				"status_name",
				"count_sending",
				"count_downloaded",
				"count_installed",
				"count_deleted"
		};
	}
	
	private Long id;
	private String title;
	private String comments;
	private Long apksId;
	private Integer installType;
	private String countryName;
	private String pushModelName;
	private String pushVersionName;
	private String modelName;
	private Timestamp pushDate;
	private Timestamp createDate;
	private Timestamp updateDate;
	private String updateComments;
	private String name;
	private String packageName;
	private String icon;
	private String description;
	private String channel;
	private String size;
	private String versionCode;
	private String versionName;
	private String url;
	private Integer status;
	private String statusName;
	private String installTypeName;
	private Integer countSending;
	private Integer countSended;
	private Integer countDownloaded;
	private Integer countInstalled;
	private Integer countDeleted;
	
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
		sb.append(",countryName=");
		sb.append(countryName);
		sb.append(",pushDate=");
		if (pushDate != null) {
			sb.append(sdf.format(pushDate));
		}
		sb.append(",createDate=");
		sb.append(createDate);
		sb.append(",updateDate=");
		sb.append(updateDate);
		sb.append(",updateComments=");
		sb.append(updateComments);
		sb.append(",name=");
		sb.append(name);
		return sb.toString();
	}
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Integer getCountSended() {
		return countSended;
	}

	public void setCountSended(Integer countSended) {
		this.countSended = countSended;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	

	public String getPushModelName() {
		return pushModelName;
	}

	public void setPushModelName(String pushModelName) {
		this.pushModelName = pushModelName;
	}

	public String getPushVersionName() {
		return pushVersionName;
	}

	public void setPushVersionName(String pushVersionName) {
		this.pushVersionName = pushVersionName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
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

	public String getInstallTypeName() {
		return installTypeName;
	}

	public void setInstallTypeName(String installTypeName) {
		this.installTypeName = installTypeName;
	}

	public Integer getCountSending() {
		return countSending;
	}

	public void setCountSending(Integer countSending) {
		this.countSending = countSending;
	}

	public Integer getCountDownloaded() {
		return countDownloaded;
	}

	public void setCountDownloaded(Integer countDownloaded) {
		this.countDownloaded = countDownloaded;
	}

	public Integer getCountInstalled() {
		return countInstalled;
	}

	public void setCountInstalled(Integer countInstalled) {
		this.countInstalled = countInstalled;
	}

	public Integer getCountDeleted() {
		return countDeleted;
	}

	public void setCountDeleted(Integer countDeleted) {
		this.countDeleted = countDeleted;
	}

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

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}


	public Timestamp getPushDate() {
		return pushDate;
	}

	public void setPushDate(Timestamp pushDate) {
		this.pushDate = pushDate;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
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
