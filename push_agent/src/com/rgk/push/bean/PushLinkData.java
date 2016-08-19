package com.rgk.push.bean;

public class PushLinkData {
	private Long server_id; //t_push_link_his:id
	private String title; //t_push_link:title
	private Integer linkType;
	private String comments; //t_push_link:comments
	private String url; //t_push_link:url
	private String icon; //t_push_link:icon
	
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
	public Integer getLinkType() {
		return linkType;
	}
	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
