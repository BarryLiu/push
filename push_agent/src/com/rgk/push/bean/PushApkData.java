package com.rgk.push.bean;

public class PushApkData {
	private Long server_id;  //t_push_apk_his:id
	private String title; //t_push_apk:title
	private String comments; //t_push_apk:comments
	private String url; //t_apks:url
	private String package_name; //t_apks:package_name
	private String icon; //t_apks:icon
	private Integer install_type; //t_push_apk:install_type
	
	/*** for db **/
//	public void setServerId(Long serverId) {
//		this.server_id = serverId;
//	}
//	public void setPackageName(String packageName) {
//		this.package_name = packageName;
//	}
//	public void setInstallType(Integer installType) {
//		this.install_type = installType;
//	}
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPackage_name() {
		return package_name;
	}
	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getInstall_type() {
		return install_type;
	}
	public void setInstall_type(Integer install_type) {
		this.install_type = install_type;
	}
	
}
