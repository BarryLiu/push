package com.push.admin.dbbean ;

import java.sql.Timestamp;
import java.sql.Timestamp;


public class TTestUserinfo implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
@Override
public String getTableName() {
	return "t_test_userinfo";
}
@Override
public String getPrimaryKeyColumnName() {
	return "_id";
}
@Override
public String[] getColumnNames() {
	return new String[]{
"_id",
"name",
"sex",
"create_date",
"update_date"};
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	
sb.append("Id=");	
sb.append(Id);	
sb.append(",name=");	
sb.append(name);	
sb.append(",sex=");	
sb.append(sex);	
sb.append(",createDate=");	
sb.append(createDate);	
sb.append(",updateDate=");	
sb.append(updateDate);
	return sb.toString();
}
	private Long Id;
	private String name;
	private String sex;
	private java.sql.Timestamp createDate;
	private java.sql.Timestamp updateDate;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long Id) {
		this.Id = Id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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
}
