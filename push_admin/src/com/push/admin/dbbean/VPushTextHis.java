package com.push.admin.dbbean ;

import java.sql.Timestamp;
import java.sql.Timestamp;
import java.sql.Timestamp;
import java.sql.Timestamp;


public class VPushTextHis implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
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
"uuid",
"push_id",
"title",
"comments",
"icon",
"push_date",
"status",
"status_name",
"pushed_date",
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
sb.append(",icon=");	
sb.append(icon);	
sb.append(",pushDate=");	
sb.append(pushDate);	
sb.append(",status=");	
sb.append(status);	
sb.append(",statusName=");	
sb.append(statusName);	
sb.append(",pushedDate=");	
sb.append(pushedDate);	
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
	private String icon;
	private java.sql.Timestamp pushDate;
	private Integer status;
	private String statusName;
	private java.sql.Timestamp pushedDate;
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
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public java.sql.Timestamp getPushDate() {
		return pushDate;
	}

	public void setPushDate(java.sql.Timestamp pushDate) {
		this.pushDate = pushDate;
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
	
	public java.sql.Timestamp getPushedDate() {
		return pushedDate;
	}

	public void setPushedDate(java.sql.Timestamp pushedDate) {
		this.pushedDate = pushedDate;
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
