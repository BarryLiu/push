package com.push.admin.dbbean;

import java.sql.Timestamp;

public class TbTestUserInfo implements SqlBean {
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
		return new String[] {
				"_id",
				"name",
				"sex",
				"create_date",
				"update_date"
		};
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=");
		sb.append(id);
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

	private Long id;
	private String name;
	private String sex;
	private Timestamp createDate;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

}
