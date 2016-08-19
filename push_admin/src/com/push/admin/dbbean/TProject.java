package com.push.admin.dbbean ;

import java.math.BigDecimal;


public class TProject implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
@Override
public String getTableName() {
	return "t_project";
}
@Override
public String getPrimaryKeyColumnName() {
	return "id";
}
@Override
public String[] getColumnNames() {
	return new String[]{
"id",
"product",
"customer",
"model",
"version",
"numbers",
"register_date",
"update_date"};
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	
sb.append("id=");	
sb.append(id);	
sb.append(",product=");	
sb.append(product);	
sb.append(",customer=");	
sb.append(customer);	
sb.append(",model=");	
sb.append(model);	
sb.append(",version=");	
sb.append(version);	
sb.append(",numbers=");	
sb.append(numbers);	
sb.append(",registerDate=");	
sb.append(registerDate);	
sb.append(",updateDate=");	
sb.append(updateDate);
	return sb.toString();
}
	private Long id;
	private String product;
	private String customer;
	private String model;
	private String version;
	private Long numbers;
	private java.sql.Timestamp registerDate;
	private java.sql.Timestamp updateDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
	
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public Long getNumbers() {
		return numbers;
	}

	public void setNumbers(Long numbers) {
		this.numbers = numbers;
	}
	public void setNumbers(BigDecimal numbers) {
		this.numbers = numbers.longValue();
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
