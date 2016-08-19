package com.push.admin.dbbean ;

import java.sql.Timestamp;


public class TOperateHis implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
@Override
public String getTableName() {
	return "t_operate_his";
}
@Override
public String getPrimaryKeyColumnName() {
	return "_id";
}
@Override
public String[] getColumnNames() {
	return new String[]{
"_id",
"user_id",
"user_name",
"type",
"comments",
"date"};
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	
sb.append("Id=");	
sb.append(Id);	
sb.append(",userId=");	
sb.append(userId);	
sb.append(",userName=");	
sb.append(userName);	
sb.append(",type=");	
sb.append(type);	
sb.append(",comments=");	
sb.append(comments);	
sb.append(",date=");	
sb.append(date);
	return sb.toString();
}
	private Long Id;
	private Long userId;
	private String userName;
	private String type;
	private String comments;
	private java.sql.Timestamp date;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long Id) {
		this.Id = Id;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public java.sql.Timestamp getDate() {
		return date;
	}

	public void setDate(java.sql.Timestamp date) {
		this.date = date;
	}
}
