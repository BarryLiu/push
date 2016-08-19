package com.push.admin.dbbean;

import java.sql.Timestamp;

public class TbApk implements SqlBean{

	@Override
	public String getTableName() {
		return "t_apks";
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return "id";
	}

	@Override
	public String[] getColumnNames() {
		return new String[]{
				"id",
				"name",
				"package_name",
				"icon",
				"description",
				"channel",
				"size",
				"version_code",
				"version_name",
				"url",
				"type",
				"status",
				"create_date",
				"update_date"
		};
	}
	
	private Long id;
	private String name;
	private String packageName;
	private String icon;
	private String description;
	private String channel;
	private String size;
	private String versionCode;
	private String versionName;
	private String url;
	private String type;
	private Integer status;
	private Timestamp createDate;
	private Timestamp updateDate;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=");
		sb.append(id);
		sb.append(",name=");
		sb.append(name);
		sb.append(",packageName=");
		sb.append(packageName);
		sb.append(",icon=");
		sb.append(icon);
		sb.append(",description=");
		sb.append(description);
		sb.append(",channel=");
		sb.append(channel);
		sb.append(",size=");
		sb.append(size);
		sb.append(",type=");
		sb.append(type);
		sb.append(",versionCode=");
		sb.append(versionCode);
		sb.append(",versionName=");
		sb.append(versionName);
		sb.append(",url=");
		sb.append(url);
		sb.append(",status=");
		sb.append(status);
		sb.append(",createDate=");
		sb.append(createDate);
		sb.append(",updateDate=");
		sb.append(updateDate);
		return sb.toString();
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
	

	
}
