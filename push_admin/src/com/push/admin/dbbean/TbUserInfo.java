package com.push.admin.dbbean;

import java.sql.Timestamp;

public class TbUserInfo implements SqlBean {
	public static final String TABLE_NAME = "t_user_info";
	public static final String PRIMARY_KEY = "_id";
	private Long id;
	private String username;
	private String pwd;
	private Timestamp createDate;
	private Timestamp updateDate;
	private String updateComments;

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return PRIMARY_KEY;
	}

	@Override
	public String[] getColumnNames() {
		return new String[] {
				"_id",
				"username",
				"pwd",
				"create_date",
				"update_date",
				"update_comments"
		};
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=");
		sb.append(id);
		sb.append(",username=");
		sb.append(username);
		sb.append(",pwd=");
		sb.append(pwd);
		sb.append(",createDate=");
		sb.append(createDate);
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
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
