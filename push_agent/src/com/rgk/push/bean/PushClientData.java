package com.rgk.push.bean;

public class PushClientData {
	private Long bin_server_id;  //t_push_update_client_his:id
	private Long apk_server_id;  //t_push_update_client_his:id
	private Integer bin_version; //t_push_update_client:version
	private Integer apk_version; //t_push_update_client:version
	private String bin_url; //t_push_update_client:url
	private String apk_url; //t_push_update_client:url
	/**
	 * @return the bin_server_id
	 */
	public Long getBin_server_id() {
		return bin_server_id;
	}
	/**
	 * @param bin_server_id the bin_server_id to set
	 */
	public void setBin_server_id(Long bin_server_id) {
		this.bin_server_id = bin_server_id;
	}
	/**
	 * @return the apk_server_id
	 */
	public Long getApk_server_id() {
		return apk_server_id;
	}
	/**
	 * @param apk_server_id the apk_server_id to set
	 */
	public void setApk_server_id(Long apk_server_id) {
		this.apk_server_id = apk_server_id;
	}
	/**
	 * @return the bin_version
	 */
	public Integer getBin_version() {
		return bin_version;
	}
	/**
	 * @param bin_version the bin_version to set
	 */
	public void setBin_version(Integer bin_version) {
		this.bin_version = bin_version;
	}
	/**
	 * @return the apk_version
	 */
	public Integer getApk_version() {
		return apk_version;
	}
	/**
	 * @param apk_version the apk_version to set
	 */
	public void setApk_version(Integer apk_version) {
		this.apk_version = apk_version;
	}
	/**
	 * @return the bin_url
	 */
	public String getBin_url() {
		return bin_url;
	}
	/**
	 * @param bin_url the bin_url to set
	 */
	public void setBin_url(String bin_url) {
		this.bin_url = bin_url;
	}
	/**
	 * @return the apk_url
	 */
	public String getApk_url() {
		return apk_url;
	}
	/**
	 * @param apk_url the apk_url to set
	 */
	public void setApk_url(String apk_url) {
		this.apk_url = apk_url;
	}
}
