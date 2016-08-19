package com.rgk.push.bean;

public class PushConfigData {
	private Long server_id; //t_push_update_config_his:id
	private String ip; //t_push_update_config:tcp_ip
	private Integer port; //t_push_update_config:tcp_port
	private String url; //t_push_update_config:http_addr
	private Long time_out; //t_push_update_config:time_out
	
	/*** for db **/
	public void setServerId(Long serverId) {
		this.server_id = serverId;
	}
	public void setTimeOut(Long timeOut) {
		this.time_out = timeOut;
	}
	/*** for db **/
	
	public Long getServer_id() {
		return server_id;
	}
	public void setServer_id(Long server_id) {
		this.server_id = server_id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getTime_out() {
		return time_out;
	}
	public void setTime_out(Long time_out) {
		this.time_out = time_out;
	}
}
