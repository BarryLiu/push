package com.push.admin.dbbean;

public interface SqlBean {
	String getTableName();
	String getPrimaryKeyColumnName();
	String[] getColumnNames();
}
