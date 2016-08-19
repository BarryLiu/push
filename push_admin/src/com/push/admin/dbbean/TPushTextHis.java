package com.push.admin.dbbean ;

import java.sql.Timestamp;
import java.sql.Timestamp;
import java.sql.Timestamp;


public class TPushTextHis implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
@Override
public String getTableName() {
	return "t_push_text_his";
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
"c_update_date",
"update_date",
"update_comments"};
}
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
sb.append(",cUpdateDate=");	
sb.append(cUpdateDate);	
sb.append(",updateDate=");	
sb.append(updateDate);	
sb.append(",updateComments=");	
sb.append(updateComments);
	return sb.toString();
}
	private Long id;
	private Long pushId;
	private String uuid;
	private Integer status;
	private java.sql.Timestamp pushDate;
	private java.sql.Timestamp cUpdateDate;
	private java.sql.Timestamp updateDate;
	private String updateComments;
	
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
	
	public java.sql.Timestamp getPushDate() {
		return pushDate;
	}

	public void setPushDate(java.sql.Timestamp pushDate) {
		this.pushDate = pushDate;
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
