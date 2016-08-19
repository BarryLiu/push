package com.push.admin.dbbean ;



public class TPushParams implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
@Override
public String getTableName() {
	return "t_push_params";
}
@Override
public String getPrimaryKeyColumnName() {
	return "id";
}
@Override
public String[] getColumnNames() {
	return new String[]{
"id",
"type",
"server_id",
"name",
"values_item",
"values_all"};
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	
sb.append("id=");	
sb.append(id);	
sb.append(",type=");	
sb.append(type);	
sb.append(",serverId=");	
sb.append(serverId);	
sb.append(",name=");	
sb.append(name);	
sb.append(",valuesItem=");	
sb.append(valuesItem);	
sb.append(",valuesAll=");	
sb.append(valuesAll);
	return sb.toString();
}
	private Long id;
	private String type;
	private Long serverId;
	private String name;
	private String valuesItem;
	private String valuesAll;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getValuesItem() {
		return valuesItem;
	}

	public void setValuesItem(String valuesItem) {
		this.valuesItem = valuesItem;
	}
	
	public String getValuesAll() {
		return valuesAll;
	}

	public void setValuesAll(String valuesAll) {
		this.valuesAll = valuesAll;
	}
}
