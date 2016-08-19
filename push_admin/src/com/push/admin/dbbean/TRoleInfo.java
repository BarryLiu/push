package com.push.admin.dbbean ;

import java.sql.Timestamp;
import java.sql.Timestamp;


public class TRoleInfo implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
@Override
public String getTableName() {
	return "t_role_info";
}
@Override
public String getPrimaryKeyColumnName() {
	return "_id";
}
@Override
public String[] getColumnNames() {
	return new String[]{
"_id",
"role_name",
"create_date",
"update_date",
"update_comments"};
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	
sb.append("Id=");	
sb.append(Id);	
sb.append(",roleName=");	
sb.append(roleName);	
sb.append(",createDate=");	
sb.append(createDate);	
sb.append(",updateDate=");	
sb.append(updateDate);	
sb.append(",updateComments=");	
sb.append(updateComments);
	return sb.toString();
}
	private Long Id;
	private String roleName;
	private java.sql.Timestamp createDate;
	private java.sql.Timestamp updateDate;
	private String updateComments;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long Id) {
		this.Id = Id;
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
}
