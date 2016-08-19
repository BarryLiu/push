package com.push.admin.dbbean ;

import java.sql.Timestamp;
import java.sql.Timestamp;
import java.sql.Timestamp;


public class VPushApk implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
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
"update_comments",
"channel",
"description",
"icon",
"name",
"package_name",
"size",
"status",
"type",
"url",
"version_code",
"apk_version_name",
"install_type_name",
"count_sending",
"count_sended",
"count_downloaded",
"count_installed",
"count_deleted"};
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
sb.append(",channel=");	
sb.append(channel);	
sb.append(",description=");	
sb.append(description);	
sb.append(",icon=");	
sb.append(icon);	
sb.append(",name=");	
sb.append(name);	
sb.append(",packageName=");	
sb.append(packageName);	
sb.append(",size=");	
sb.append(size);	
sb.append(",status=");	
sb.append(status);	
sb.append(",type=");	
sb.append(type);	
sb.append(",url=");	
sb.append(url);	
sb.append(",versionCode=");	
sb.append(versionCode);	
sb.append(",apkVersionName=");	
sb.append(apkVersionName);	
sb.append(",installTypeName=");	
sb.append(installTypeName);	
sb.append(",countSending=");	
sb.append(countSending);	
sb.append(",countSended=");	
sb.append(countSended);	
sb.append(",countDownloaded=");	
sb.append(countDownloaded);	
sb.append(",countInstalled=");	
sb.append(countInstalled);	
sb.append(",countDeleted=");	
sb.append(countDeleted);
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
	private String channel;
	private String description;
	private String icon;
	private String name;
	private String packageName;
	private String size;
	private Integer status;
	private String type;
	private String url;
	private String versionCode;
	private String apkVersionName;
	private String installTypeName;
	private Long countSending;
	private Long countSended;
	private Long countDownloaded;
	private Long countInstalled;
	private Long countDeleted;
	
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
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	
	public String getApkVersionName() {
		return apkVersionName;
	}

	public void setApkVersionName(String apkVersionName) {
		this.apkVersionName = apkVersionName;
	}
	
	public String getInstallTypeName() {
		return installTypeName;
	}

	public void setInstallTypeName(String installTypeName) {
		this.installTypeName = installTypeName;
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
	
	public Long getCountDownloaded() {
		return countDownloaded;
	}

	public void setCountDownloaded(Long countDownloaded) {
		this.countDownloaded = countDownloaded;
	}
	
	public Long getCountInstalled() {
		return countInstalled;
	}

	public void setCountInstalled(Long countInstalled) {
		this.countInstalled = countInstalled;
	}
	
	public Long getCountDeleted() {
		return countDeleted;
	}

	public void setCountDeleted(Long countDeleted) {
		this.countDeleted = countDeleted;
	}
}
