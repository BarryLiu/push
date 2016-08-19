package com.push.admin.dbbean ;

import java.math.BigDecimal;


public class TCountry implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
@Override
public String getTableName() {
	return "t_country";
}
@Override
public String getPrimaryKeyColumnName() {
	return "id";
}
@Override
public String[] getColumnNames() {
	return new String[]{
"id",
"area",
"name",
"numbers",
"register_date",
"update_date"};
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	
sb.append("id=");	
sb.append(id);	
sb.append(",area=");	
sb.append(area);	
sb.append(",name=");	
sb.append(name);	
sb.append(",numbers=");	
sb.append(numbers);	
sb.append(",registerDate=");	
sb.append(registerDate);	
sb.append(",updateDate=");	
sb.append(updateDate);
	return sb.toString();
}
	private Long id;
	private String area;
	private String name;
	private Long numbers;
	private java.sql.Timestamp registerDate;
	private java.sql.Timestamp updateDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumbers(BigDecimal numbers) {
		this.numbers = numbers.longValue();
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
