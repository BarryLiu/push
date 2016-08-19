package com.push.admin.dbbean;

import java.sql.Timestamp;

public class TbCountry implements SqlBean {

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
				"rigister_date",
				"update_date"
		};
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
	private Timestamp registerDate;
	private Timestamp updateDate;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Long getNumbers() {
		return numbers;
	}

	public void setNumbers(Long numbers) {
		this.numbers = numbers;
	}

	public Timestamp getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	
	
	
	
	
}
