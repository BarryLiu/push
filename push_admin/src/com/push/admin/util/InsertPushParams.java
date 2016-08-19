/**
 * 
 */
package com.push.admin.util;

import java.util.List;

import com.push.admin.dbbean.TPushParams;

/**
 * @author kui.li
 *
 */
public class InsertPushParams implements Runnable {
	
	// 存储数据类型
	public static String TYPE_APK = "app";
	public static String TYPE_LINK = "link";
	public static String TYPE_TEXT = "text";
	public static String TYPE_CLIENT = "client";
	public static String TYPE_CONFIG = "config";
	public static String TYPE_COMMANDS = "commands";
	
	// 参数名称
	public static String TYPE_NAME_COUNTRY = "country";
	public static String TYPE_NAME_PROJECT = "project";
	public static String TYPE_NAME_CUSTOMER = "customer";
	public static String TYPE_NAME_MODEL = "model";
	public static String TYPE_NAME_VERSION = "version";

	private String type; 
	private long serverId;
	private String country; 
	private String project; 
	private String customer; 
	private String model; 
	private String version;
	
	/**
	 * 
	 */
	public InsertPushParams() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	public InsertPushParams(String type, long serverId, String country, String project, String customer, String model, String version) {
		// TODO Auto-generated constructor stub
		this.type = type;
		this.serverId = serverId;
		this.country = country;
		this.project = project;
		this.customer = customer;
		this.model = model;
		this.version = version;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		String sql = "select * from t_push_params where server_id=" + serverId + " and type='" + type + "'";
		List<TPushParams> lstOldData = DBUtils.query(sql, TPushParams.class);
		
		// 保存国家信息
		if(country != null && !"".equals(country)) {
			String arrCountry[] = country.split(",");
			if(arrCountry.length>0) {
				TPushParams data = new TPushParams();
				data.setType(type);
				data.setServerId(serverId);
				data.setValuesAll(country);
				data.setName(TYPE_NAME_COUNTRY);
				for(String item : arrCountry) {
					data.setValuesItem(item);
					DBUtils.insert(data);					
				}
			}
		}
		// 保存项目信息
		if(project != null && !"".equals(project)) {
			String arrProject[] = project.split(",");
			if(arrProject.length>0) {
				TPushParams data = new TPushParams();
				data.setType(type);
				data.setServerId(serverId);
				data.setValuesAll(project);
				data.setName(TYPE_NAME_PROJECT);
				for(String item : arrProject) {
					data.setValuesItem(item);
					DBUtils.insert(data);					
				}
			}
		}
		// 保存客户信息
		if(customer != null && !"".equals(customer)) {
			String arrCustomer[] = customer.split(",");
			if(arrCustomer.length>0) {
				TPushParams data = new TPushParams();
				data.setType(type);
				data.setServerId(serverId);
				data.setValuesAll(customer);
				data.setName(TYPE_NAME_CUSTOMER);
				for(String item : arrCustomer) {
					data.setValuesItem(item);
					DBUtils.insert(data);					
				}
			}
		}
		// 保存型号信息
		if(model != null && !"".equals(model)) {
			String arrModel[] = model.split(",");
			if(arrModel.length>0) {
				TPushParams data = new TPushParams();
				data.setType(type);
				data.setServerId(serverId);
				data.setValuesAll(model);
				data.setName(TYPE_NAME_MODEL);
				for(String item : arrModel) {
					data.setValuesItem(item);
					DBUtils.insert(data);					
				}
			}
		}
		// 保存版本信息
		if(version != null && !"".equals(version)) {
			String arrVersion[] = version.split(",");
			if(arrVersion.length>0) {
				TPushParams data = new TPushParams();
				data.setType(type);
				data.setServerId(serverId);
				data.setValuesAll(version);
				data.setName(TYPE_NAME_VERSION);
				for(String item : arrVersion) {
					data.setValuesItem(item);
					DBUtils.insert(data);					
				}
			}
		}
		
		// 将旧数据全部删除
		if(lstOldData != null && lstOldData.size()>0)
			DBUtils.delete(lstOldData);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
