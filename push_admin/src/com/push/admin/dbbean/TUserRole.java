package com.push.admin.dbbean ;



public class TUserRole implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
@Override
public String getTableName() {
	return "t_user_role";
}
@Override
public String getPrimaryKeyColumnName() {
	return "_id";
}
@Override
public String[] getColumnNames() {
	return new String[]{
"_id",
"user_info_id",
"role_info_id"};
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	
sb.append("Id=");	
sb.append(Id);	
sb.append(",userInfoId=");	
sb.append(userInfoId);	
sb.append(",roleInfoId=");	
sb.append(roleInfoId);
	return sb.toString();
}
	private Long Id;
	private Long userInfoId;
	private Long roleInfoId;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long Id) {
		this.Id = Id;
	}
	
	public Long getUserInfoId() {
		return userInfoId;
	}

	public void setUserInfoId(Long userInfoId) {
		this.userInfoId = userInfoId;
	}
	
	public Long getRoleInfoId() {
		return roleInfoId;
	}

	public void setRoleInfoId(Long roleInfoId) {
		this.roleInfoId = roleInfoId;
	}
}
