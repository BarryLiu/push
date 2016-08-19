package com.push.admin.dbbean ;

import java.sql.Timestamp;
import java.sql.Timestamp;
import java.sql.Timestamp;
import java.sql.Timestamp;


public class VPushUpdateConfigHis implements SqlBean {
	private boolean canEdit=true;
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
@Override
public String getTableName() {
	return "v_push_update_config_his";
}
@Override
public String getPrimaryKeyColumnName() {
	return "id";
}
@Override
public String[] getColumnNames() {
	return new String[]{
"id",
"push_id",
"uuid",
"tcp_ip",
"tcp_port",
"time_out",
"http_addr",
"push_date",
"status",
"status_name",
"create_date",
"c_update_date",
"update_date",
"update_comments"};
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	
sb.append("id=");	
sb.append(id);	
sb.append(",pushId=");	
sb.append(pushId);	
sb.append(",uuid=");	
sb.append(uuid);	
sb.append(",tcpIp=");	
sb.append(tcpIp);	
sb.append(",tcpPort=");	
sb.append(tcpPort);	
sb.append(",timeOut=");	
sb.append(timeOut);	
sb.append(",httpAddr=");	
sb.append(httpAddr);	
sb.append(",pushDate=");	
sb.append(pushDate);	
sb.append(",status=");	
sb.append(status);	
sb.append(",statusName=");	
sb.append(statusName);	
sb.append(",createDate=");	
sb.append(createDate);	
sb.append(",cUpdateDate=");	
sb.append(cUpdateDate);	
sb.append(",updateDate=");	
sb.append(updateDate);	
sb.append(",updateComments=");	
sb.append(updateComments);
	return sb.toString();
}
	private Long id;
	private Long pushId;
	private String uuid;
	private String tcpIp;
	private Integer tcpPort;
	private Long timeOut;
	private String httpAddr;
	private java.sql.Timestamp pushDate;
	private Integer status;
	private String statusName;
	private java.sql.Timestamp createDate;
	private java.sql.Timestamp cUpdateDate;
	private java.sql.Timestamp updateDate;
	private String updateComments;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPushId() {
		return pushId;
	}

	public void setPushId(Long pushId) {
		this.pushId = pushId;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getTcpIp() {
		return tcpIp;
	}

	public void setTcpIp(String tcpIp) {
		this.tcpIp = tcpIp;
	}
	
	public Integer getTcpPort() {
		return tcpPort;
	}

	public void setTcpPort(Integer tcpPort) {
		this.tcpPort = tcpPort;
	}
	
	public Long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Long timeOut) {
		this.timeOut = timeOut;
	}
	
	public String getHttpAddr() {
		return httpAddr;
	}

	public void setHttpAddr(String httpAddr) {
		this.httpAddr = httpAddr;
	}
	
	public java.sql.Timestamp getPushDate() {
		return pushDate;
	}

	public void setPushDate(java.sql.Timestamp pushDate) {
		this.pushDate = pushDate;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	public java.sql.Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.sql.Timestamp createDate) {
		this.createDate = createDate;
	}
	
	public java.sql.Timestamp getCUpdateDate() {
		return cUpdateDate;
	}

	public void setCUpdateDate(java.sql.Timestamp cUpdateDate) {
		this.cUpdateDate = cUpdateDate;
	}
	
	public java.sql.Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.sql.Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getUpdateComments() {
		return updateComments;
	}

	public void setUpdateComments(String updateComments) {
		this.updateComments = updateComments;
	}
}
