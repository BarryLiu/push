package com.push.admin.dbbean ;

import java.sql.Timestamp;
import java.sql.Timestamp;


public class TVersionRegistered implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
@Override
public String getTableName() {
	return "t_version_registered";
}
@Override
public String getPrimaryKeyColumnName() {
	return "version";
}
@Override
public String[] getColumnNames() {
	return new String[]{
"version",
"model",
"numbers",
"register_date",
"update_date"};
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	
sb.append("version=");	
sb.append(version);	
sb.append(",model=");	
sb.append(model);	
sb.append(",numbers=");	
sb.append(numbers);	
sb.append(",registerDate=");	
sb.append(registerDate);	
sb.append(",updateDate=");	
sb.append(updateDate);
	return sb.toString();
}
	private String version;
	private String model;
	private Long numbers;
	private java.sql.Timestamp registerDate;
	private java.sql.Timestamp updateDate;
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public Long getNumbers() {
		return numbers;
	}

	public void setNumbers(Long numbers) {
		this.numbers = numbers;
	}
	
	public java.sql.Timestamp getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(java.sql.Timestamp registerDate) {
		this.registerDate = registerDate;
	}
	
	public java.sql.Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.sql.Timestamp updateDate) {
		this.updateDate = updateDate;
	}
}
