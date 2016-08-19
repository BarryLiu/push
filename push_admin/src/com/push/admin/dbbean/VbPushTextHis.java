package com.push.admin.dbbean;

import java.sql.Timestamp;

public class VbPushTextHis implements SqlBean {

	@Override
	public String getTableName() {
		return "v_push_text_his";
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
				"title",
				"comments",
				"icon",
				"status_name",
				"status",
				"push_date",
				"pushed_date",
				"c_update_date",
				"update_date",
				"update_comments"
		};
	}
	
	private Long id;
	private Long pushId;
	private String uuid;
	private String title;
	private String comments;
	private String icon;
	private String statusName;
	private Integer status;
	private Timestamp pushDate;
	private Timestamp pushedDate;
	private Timestamp cUpdateDate;
	private Timestamp updateDate;
	private String updateComments;
	
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

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPushId() {
		return pushId;
	}

	public void setPushId(Long pushId) {
		this.pushId = pushId;
	}
	
	

	public Timestamp getPushedDate() {
		return pushedDate;
	}

	public void setPushedDate(Timestamp pushedDate) {
		this.pushedDate = pushedDate;
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

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Timestamp getcUpdateDate() {
		return cUpdateDate;
	}

	public void setcUpdateDate(Timestamp cUpdateDate) {
		this.cUpdateDate = cUpdateDate;
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
