package com.push.admin.dbbean ;



public class TSysPermission implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
@Override
public String getTableName() {
	return "t_sys_permission";
}
@Override
public String getPrimaryKeyColumnName() {
	return "_id";
}
@Override
public String[] getColumnNames() {
	return new String[]{
"_id",
"per_name",
"per_value",
"per_desc",
"url",
"parent",
"enable"};
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	
sb.append("Id=");	
sb.append(Id);	
sb.append(",perName=");	
sb.append(perName);	
sb.append(",perValue=");	
sb.append(perValue);	
sb.append(",perDesc=");	
sb.append(perDesc);	
sb.append(",url=");	
sb.append(url);	
sb.append(",parent=");	
sb.append(parent);	
sb.append(",enable=");	
sb.append(enable);
	return sb.toString();
}
	private Long Id;
	private String perName;
	private String perValue;
	private String perDesc;
	private String url;
	private Long parent;
	private Integer enable;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long Id) {
		this.Id = Id;
	}
	
	public String getPerName() {
		return perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
	}
	
	public String getPerValue() {
		return perValue;
	}

	public void setPerValue(String perValue) {
		this.perValue = perValue;
	}
	
	public String getPerDesc() {
		return perDesc;
	}

	public void setPerDesc(String perDesc) {
		this.perDesc = perDesc;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}
	
	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}
}
