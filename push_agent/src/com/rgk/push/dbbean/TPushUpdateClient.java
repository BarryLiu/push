/**
 * 
 */
package com.rgk.push.dbbean;

import java.sql.Timestamp;

/**
 * @author kui.li
 *
 */
public class TPushUpdateClient implements SqlBean {
	public static final int TYPE_BIN = 1;
	public static final int TYPE_APK = 2;
	private Long id ;
	private String title;
	private String comments;
	private Integer version;
	private String url;
	private Integer type;
	private String countryName;
	private String countryAddr;
	private String registerDate;	
	private Timestamp pushDate;
	private Timestamp createDate;
	private Timestamp updateDate;
	private String updateComments;
	
	/**
	 * 
	 */
	public TPushUpdateClient() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.rgk.push.dbbean.SqlBean#getTableName()
	 */
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "t_push_update_client";
	}

	/* (non-Javadoc)
	 * @see com.rgk.push.dbbean.SqlBean#getPrimaryKeyColumnName()
	 */
	@Override
	public String getPrimaryKeyColumnName() {
		// TODO Auto-generated method stub
		return "id";
	}

	/* (non-Javadoc)
	 * @see com.rgk.push.dbbean.SqlBean#getColumnNames()
	 */
	@Override
	public String[] getColumnNames() {
		// TODO Auto-generated method stub
		return new String[] {
				"id",
				"title",
				"comments",
				"version",
				"url",
				"country_code",
				"push_date",
				"create_date",
				"update_date",
				"update_comments"
		};
	}

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return the registerDate
	 */
	public String getRegisterDate() {
		return registerDate;
	}

	/**
	 * @param registerDate the registerDate to set
	 */
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}


	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the pushDate
	 */
	public Timestamp getPushDate() {
		return pushDate;
	}

	/**
	 * @param pushDate the pushDate to set
	 */
	public void setPushDate(Timestamp pushDate) {
		this.pushDate = pushDate;
	}

	/**
	 * @return the createDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the updateComments
	 */
	public String getUpdateComments() {
		return updateComments;
	}

	/**
	 * @param updateComments the updateComments to set
	 */
	public void setUpdateComments(String updateComments) {
		this.updateComments = updateComments;
	}

	/**
	 * @return the countryAddr
	 */
	public String getCountryAddr() {
		return countryAddr;
	}

	/**
	 * @param countryAddr the countryAddr to set
	 */
	public void setCountryAddr(String countryAddr) {
		this.countryAddr = countryAddr;
	}
}
