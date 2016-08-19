package com.push.admin.dbbean;

import java.sql.Timestamp;

public class VbPushUpdateConfigHis implements SqlBean {
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
				"tcp_ip",
				"tcp_port",
				"time_out",
				"http_addr",
				"push_id",
				"uuid",
				"status",
				"status_name",
				"create_date",
				"push_date",
				"c_update_date",
				"update_date",
				"update_comments"
		};
	}
	
	private Long id;
	private Long pushId;
	private String uuid;
	private Integer status;
	private String tcpIp;
	private Integer tcpPort;
	private Long timeOut;
	private String httpAddr;
	private String statusName;
	private Timestamp createDate;
	private Timestamp cUpdateDate;
	private Timestamp updateDate;
	private Timestamp pushDate;
	private String updateComments;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=");
		sb.append(id);
		sb.append(",pushId=");
		sb.append(pushId);
		sb.append(",uuid=");
		sb.append(uuid);
		sb.append(",status=");
		sb.append(status);
		sb.append(",createDate=");
		sb.append(createDate);
		sb.append(",statusName=");
		sb.append(statusName);
		sb.append(",cUpdateDate=");
		sb.append(cUpdateDate);
		sb.append(",updateDate=");
		sb.append(updateDate);
		sb.append(",updateComments=");
		sb.append(updateComments);
		return sb.toString();
	}
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
	
	

	public Timestamp getPushDate() {
		return pushDate;
	}

	public void setPushDate(Timestamp pushDate) {
		this.pushDate = pushDate;
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

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Timestamp getcUpdateDate() {
		return cUpdateDate;
	}

	public void setcUpdateDate(Timestamp cUpdateDate) {
		this.cUpdateDate = cUpdateDate;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getCUpdateDate() {
		return cUpdateDate;
	}

	public void setCUpdateDate(Timestamp cUpdateDate) {
		this.cUpdateDate = cUpdateDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateComments() {
		return updateComments;
	}

	public void setUpdateComments(String updateComments) {
		this.updateComments = updateComments;
	}
	
}
