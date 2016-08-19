package com.push.admin.dbbean;

public class TbSysPermission implements SqlBean {
	public static final String TABLE_NAME = "t_sys_permission";
	public static final String PRIMARY_KEY = "_id";
	private Long id;
	private String perName;
	private String perValue;
	private String perDesc;
	private String url;
	private Long parent;
	private Integer enable;
	
	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return PRIMARY_KEY;
	}

	@Override
	public String[] getColumnNames() {
		return new String[] {
				"_id",
				"per_name",
				"per_value",
				"per_desc",
				"url",
				"parent",
				"enable"
		};
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=");
		sb.append(id);
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
