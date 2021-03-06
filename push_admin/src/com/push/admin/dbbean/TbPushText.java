package com.push.admin.dbbean;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TbPushText implements SqlBean {

	@Override
	public String getTableName() {
		return "t_push_text";
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
				"icon",
				"country_name",
				"model_name",
				"version_name",
				"push_date",
				"create_date",
				"update_date",
				"update_comments"
		};
	}
	
	private Long id;
	private String title;
	private String comments;
	private String icon;
	private String countryName;
	private String modelName;
	private String versionName;
	private Timestamp pushDate;
	private Timestamp createDate;
	private Timestamp updateDate;
	private String updateComments;
	
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
		sb.append(",icon=");
		sb.append(icon);
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
		return sb.toString();
	}
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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
