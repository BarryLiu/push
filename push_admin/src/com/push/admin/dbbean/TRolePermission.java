package com.push.admin.dbbean ;



public class TRolePermission implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
@Override
public String getTableName() {
	return "t_role_permission";
}
@Override
public String getPrimaryKeyColumnName() {
	return "_id";
}
@Override
public String[] getColumnNames() {
	return new String[]{
"_id",
"role_info_id",
"permission_id"};
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	
sb.append("Id=");	
sb.append(Id);	
sb.append(",roleInfoId=");	
sb.append(roleInfoId);	
sb.append(",permissionId=");	
sb.append(permissionId);
	return sb.toString();
}
	private Long Id;
	private Long roleInfoId;
	private Long permissionId;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long Id) {
		this.Id = Id;
	}
	
	public Long getRoleInfoId() {
		return roleInfoId;
	}

	public void setRoleInfoId(Long roleInfoId) {
		this.roleInfoId = roleInfoId;
	}
	
	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
}
