package com.rgk.push.bean;

public class PushTextData {
	private Long server_id; //t_push_text_his:id
	private String title; //t_push_text:title
	private String comments; //t_push_text:comments
	private String icon; //t_push_text:icon
	
	/*** for db **/
	public void setServerId(Long serverId) {
		this.server_id = serverId;
	}
	/*** for db **/
	
	public Long getServer_id() {
		return server_id;
	}
	public void setServer_id(Long server_id) {
		this.server_id = server_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
