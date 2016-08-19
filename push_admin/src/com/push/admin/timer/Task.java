package com.push.admin.timer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.push.admin.dbbean.*;
import org.apache.log4j.Logger;

import com.push.admin.util.DBUtils;

public class Task implements ITask {
	private static final Logger logger = Logger.getLogger(Task.class);

	@Override
	public void execute() {
		TimerListner timerListner = TimerListner.getInstance();
		System.out.println(timerListner.getApks().size()+"+++++++++++++++++++++++++++++++++++");
		if (timerListner.getApks().size() > 0) {
			List<TPushApk> apks = new ArrayList<TPushApk>();
			for (TPushApk apk : timerListner.getApks()) {
				if (apk.getPushDate().before(new Date())) {
					apks.add(apk);
				}
			}
			System.out.println(apks.size());
			for (TPushApk apk : apks) {
				if (apk.getApksId() != null) {
					String sql = "select * from t_apks where id="
							+ apk.getApksId();
					List<TApks> beanList = DBUtils.query(sql, TApks.class);
					if (beanList.size() > 0) {
						TApks _apk = beanList.get(0);
						_apk.setStatus(1);
						DBUtils.update(_apk);
					}
				}
				List<Object> params = new ArrayList<Object>();
				StringBuffer sql = new StringBuffer(
						"insert into t_push_apk_his(push_id,uuid,push_date,update_date) ")
						.append("select ")
						.append(apk.getId())
						.append(",uuid,NOW(),NOW() from t_client where 1=1");
				if (apk.getCountryName() != null
						&& !apk.getCountryName().equals("")) {
					String[] countrys = apk.getCountryName().split(",");
					if (countrys.length > 0) {
						sql.append(" and country_name in (");
						for (int i = 0; i < countrys.length; i++) {
							sql.append("'");
							sql.append(countrys[i]);
							if (i == countrys.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				if (apk.getVersionName() != null
						&& !apk.getVersionName().equals("")) {
					String[] versions = apk.getVersionName().split(",");
					if (versions.length > 0) {
						sql.append(" and device_version in (");
						for (int i = 0; i < versions.length; i++) {
							sql.append("'");
							sql.append(versions[i]);
							if (i == versions.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				if (apk.getModelName() != null
						&& !apk.getModelName().equals("")) {
					String[] models = apk.getModelName().split(",");
					if (models.length > 0) {
						sql.append(" and device_model in (");
						for (int i = 0; i < models.length; i++) {
							sql.append("'");
							sql.append(models[i]);
							if (i == models.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				// add project name by kui.li,start 
				if (apk.getProjectName() != null
						&& !apk.getProjectName().equals("")) {
					String[] projects = apk.getProjectName().split(",");
					if (projects.length > 0) {
						sql.append(" and device_product in (");
						for (int i = 0; i < projects.length; i++) {
							sql.append("'");
							sql.append(projects[i]);
							if (i == projects.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				// add project name by kui.li,end
				// add customer name by kui.li,start
				if (apk.getCustomerName() != null
						&& !apk.getCustomerName().equals("")) {
					String[] customers = apk.getCustomerName().split(",");
					if (customers.length > 0) {
						sql.append(" and device_customer in (");
						for (int i = 0; i < customers.length; i++) {
							sql.append("'");
							sql.append(customers[i]);
							if (i == customers.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				// add customer name by kui.li,end
				// add imei name by kui.li,start
                if (apk.getImei1() != null && !apk.getImei1().equals("")&&apk.getImei2() != null
                        && !apk.getImei2().equals("")) {
                    sql.append(" and imei  >= '").append(apk.getImei1()).append("' and imei  <= '").append(apk.getImei2() + "'");
                }
				// add imei name by kui.li,end
				logger.info("sql:"+sql.toString());
				System.out.println("sql=================="+sql);
				DBUtils.execute(sql.toString(), params);
				timerListner.getApks().remove(apk);
			}
		}
		if (timerListner.getLinks().size() > 0) {
			List<TPushLink> links = new ArrayList<TPushLink>();
			for (TPushLink link : timerListner.getLinks()) {
				if (link.getPushDate().before(new Date())) {
					links.add(link);
				}
			}
			for (TPushLink link : links) {
				List<Object> params = new ArrayList<Object>();
				StringBuffer sql = new StringBuffer(
						"insert into t_push_link_his(push_id,uuid,push_date,update_date) ")
						.append("select ")
						.append(link.getId())
						.append(",uuid,NOW(),NOW() from t_client where 1=1");
				if (link.getCountryName() != null
						&& !link.getCountryName().equals("")) {
					String[] countrys = link.getCountryName().split(",");
					if (countrys.length > 0) {
						sql.append(" and country_name in (");
						for (int i = 0; i < countrys.length; i++) {
							sql.append("'");
							sql.append(countrys[i]);
							if (i == countrys.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				if (link.getVersionName() != null
						&& !link.getVersionName().equals("")) {
					String[] countrys = link.getVersionName().split(",");
					if (countrys.length > 0) {
						sql.append(" and device_version in (");
						for (int i = 0; i < countrys.length; i++) {
							sql.append("'");
							sql.append(countrys[i]);
							if (i == countrys.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				if (link.getModelName() != null
						&& !link.getModelName().equals("")) {
					String[] countrys = link.getModelName().split(",");
					if (countrys.length > 0) {
						sql.append(" and device_model in (");
						for (int i = 0; i < countrys.length; i++) {
							sql.append("'");
							sql.append(countrys[i]);
							if (i == countrys.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				// add project name by kui.li,start 
				if (link.getProjectName() != null
						&& !link.getProjectName().equals("")) {
					String[] projects = link.getProjectName().split(",");
					if (projects.length > 0) {
						sql.append(" and device_product in (");
						for (int i = 0; i < projects.length; i++) {
							sql.append("'");
							sql.append(projects[i]);
							if (i == projects.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				// add project name by kui.li,end
				// add customer name by kui.li,start
				if (link.getCustomerName() != null
						&& !link.getCustomerName().equals("")) {
					String[] customers = link.getCustomerName().split(",");
					if (customers.length > 0) {
						sql.append(" and device_customer in (");
						for (int i = 0; i < customers.length; i++) {
							sql.append("'");
							sql.append(customers[i]);
							if (i == customers.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				// add customer name by kui.li,end
				// add imei name by kui.li,start
                if (link.getImei1() != null && !link.getImei1().equals("")&&link.getImei2() != null
                        && !link.getImei2().equals("")) {
                    sql.append(" and imei  >= '").append(link.getImei1()).append("' and imei  <= '").append(link.getImei2() + "'");
                }
				// add imei name by kui.li,end
				logger.info("sql:"+sql.toString());
				DBUtils.execute(sql.toString(), params);
				timerListner.getLinks().remove(link);
			}
		}
        if (timerListner.getOrders().size() > 0) {
            List<TPushCommands> links = new ArrayList<TPushCommands>();
            for (TPushCommands link : timerListner.getOrders()) {
                if (link.getPushDate().before(new Date())) {
                    links.add(link);
                }
            }
            for (TPushCommands commands : links) {
                List<Object> params = new ArrayList<Object>();
                StringBuffer sql = new StringBuffer(
                        "insert into t_push_commands_his(push_id,uuid,push_date,update_date) ")
                        .append("select ")
                        .append(commands.getId())
                        .append(",uuid,NOW(),NOW() from t_client where 1=1");
                if (commands.getCountryName() != null
                        && !commands.getCountryName().equals("")) {
                    String[] countrys = commands.getCountryName().split(",");
                    if (countrys.length > 0) {
                        sql.append(" and country_name in (");
                        for (int i = 0; i < countrys.length; i++) {
                            sql.append("'");
                            sql.append(countrys[i]);
                            if (i == countrys.length - 1) {
                                sql.append("')");
                            }else{
                                sql.append("',");
                            }
                        }
                    }
                }
                if (commands.getVersionName() != null
                        && !commands.getVersionName().equals("")) {
                    String[] countrys = commands.getVersionName().split(",");
                    if (countrys.length > 0) {
                        sql.append(" and device_version in (");
                        for (int i = 0; i < countrys.length; i++) {
                            sql.append("'");
                            sql.append(countrys[i]);
                            if (i == countrys.length - 1) {
                                sql.append("')");
                            }else{
                                sql.append("',");
                            }
                        }
                    }
                }
                if (commands.getModelName() != null
                        && !commands.getModelName().equals("")) {
                    String[] countrys = commands.getModelName().split(",");
                    if (countrys.length > 0) {
                        sql.append(" and device_model in (");
                        for (int i = 0; i < countrys.length; i++) {
                            sql.append("'");
                            sql.append(countrys[i]);
                            if (i == countrys.length - 1) {
                                sql.append("')");
                            }else{
                                sql.append("',");
                            }
                        }
                    }
                }
                if (commands.getImei1() != null && !commands.getImei1().equals("")&&commands.getImei2() != null
                        && !commands.getImei2().equals("")) {
                    sql.append(" and imei  >= '").append(commands.getImei1()).append("' and imei  <= '").append(commands.getImei2() + "'");
                }
				// add project name by kui.li,start 
				if (commands.getProjectName() != null
						&& !commands.getProjectName().equals("")) {
					String[] projects = commands.getProjectName().split(",");
					if (projects.length > 0) {
						sql.append(" and device_product in (");
						for (int i = 0; i < projects.length; i++) {
							sql.append("'");
							sql.append(projects[i]);
							if (i == projects.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				// add project name by kui.li,end
				// add customer name by kui.li,start
				if (commands.getCustomerName() != null
						&& !commands.getCustomerName().equals("")) {
					String[] customers = commands.getCustomerName().split(",");
					if (customers.length > 0) {
						sql.append(" and device_customer in (");
						for (int i = 0; i < customers.length; i++) {
							sql.append("'");
							sql.append(customers[i]);
							if (i == customers.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				// add customer name by kui.li,end
                logger.info("sql:"+sql.toString());
                DBUtils.execute(sql.toString(), params);
                timerListner.getOrders().remove(commands);
            }
        }
		if (timerListner.getTexts().size() > 0) {
			List<TPushText> texts = new ArrayList<TPushText>();
			for (TPushText text : timerListner.getTexts()) {
				if (text.getPushDate().before(new Date())) {
					texts.add(text);
				}
			}
			for (TPushText text : texts) {
				List<Object> params = new ArrayList<Object>();
				StringBuffer sql = new StringBuffer(
						"insert into t_push_text_his(push_id,uuid,push_date,update_date) ")
						.append("select ")
						.append(text.getId())
						.append(",uuid,NOW(),NOW() from t_client where 1=1");
				if (text.getCountryName() != null
						&& !text.getCountryName().equals("")) {
					String[] countrys = text.getCountryName().split(",");
					if (countrys.length > 0) {
						sql.append(" and country_name in (");
						for (int i = 0; i < countrys.length; i++) {
							sql.append("'");
							sql.append(countrys[i]);
							if (i == countrys.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				if (text.getVersionName() != null
						&& !text.getVersionName().equals("")) {
					String[] countrys = text.getVersionName().split(",");
					if (countrys.length > 0) {
						sql.append(" and device_version in (");
						for (int i = 0; i < countrys.length; i++) {
							sql.append("'");
							sql.append(countrys[i]);
							if (i == countrys.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				if (text.getModelName() != null
						&& !text.getModelName().equals("")) {
					String[] countrys = text.getModelName().split(",");
					if (countrys.length > 0) {
						sql.append(" and device_model in (");
						for (int i = 0; i < countrys.length; i++) {
							sql.append("'");
							sql.append(countrys[i]);
							if (i == countrys.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				// add project name by kui.li,start 
				if (text.getProjectName() != null
						&& !text.getProjectName().equals("")) {
					String[] projects = text.getProjectName().split(",");
					if (projects.length > 0) {
						sql.append(" and device_product in (");
						for (int i = 0; i < projects.length; i++) {
							sql.append("'");
							sql.append(projects[i]);
							if (i == projects.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				// add project name by kui.li,end
				// add customer name by kui.li,start
				if (text.getCustomerName() != null
						&& !text.getCustomerName().equals("")) {
					String[] customers = text.getCustomerName().split(",");
					if (customers.length > 0) {
						sql.append(" and device_customer in (");
						for (int i = 0; i < customers.length; i++) {
							sql.append("'");
							sql.append(customers[i]);
							if (i == customers.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				// add customer name by kui.li,end
				// add imei name by kui.li,start
                if (text.getImei1() != null && !text.getImei1().equals("")&&text.getImei2() != null
                        && !text.getImei2().equals("")) {
                    sql.append(" and imei  >= '").append(text.getImei1()).append("' and imei  <= '").append(text.getImei2() + "'");
                }
				// add imei name by kui.li,end
				logger.info("sql:"+sql.toString());
				DBUtils.execute(sql.toString(), params);
				timerListner.getTexts().remove(text);
			}
		}
		if (timerListner.getConfigs().size() > 0) {
			List<TPushUpdateConfig> configs = new ArrayList<TPushUpdateConfig>();
			for (TPushUpdateConfig config : timerListner.getConfigs()) {
				if (config.getPushDate().before(new Date())) {
					configs.add(config);
				}
			}

			// add project name by kui.li,start 
			for (TPushUpdateConfig config : configs) {
				List<Object> params = new ArrayList<Object>();
				StringBuffer sql = new StringBuffer(
						"insert into t_push_update_config_his(push_id,uuid,create_date,update_date) ")
						.append("select ")
						.append(config.getId())
						.append(",uuid,NOW(),NOW() from t_client where 1=1");
				if (config.getCountryName() != null
						&& !config.getCountryName().equals("")) {
					String[] countrys = config.getCountryName().split(",");
					if (countrys.length > 0) {
						sql.append(" and country_name in (");
						for (int i = 0; i < countrys.length; i++) {
							sql.append("'");
							sql.append(countrys[i]);
							if (i == countrys.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				if (config.getVersionName() != null
						&& !config.getVersionName().equals("")) {
					String[] countrys = config.getVersionName().split(",");
					if (countrys.length > 0) {
						sql.append(" and device_version in (");
						for (int i = 0; i < countrys.length; i++) {
							sql.append("'");
							sql.append(countrys[i]);
							if (i == countrys.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				if (config.getModelName() != null
						&& !config.getModelName().equals("")) {
					String[] countrys = config.getModelName().split(",");
					if (countrys.length > 0) {
						sql.append(" and device_model in (");
						for (int i = 0; i < countrys.length; i++) {
							sql.append("'");
							sql.append(countrys[i]);
							if (i == countrys.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				if (config.getProjectName() != null
						&& !config.getProjectName().equals("")) {
					String[] projects = config.getProjectName().split(",");
					if (projects.length > 0) {
						sql.append(" and device_product in (");
						for (int i = 0; i < projects.length; i++) {
							sql.append("'");
							sql.append(projects[i]);
							if (i == projects.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
				if (config.getCustomerName() != null
						&& !config.getCustomerName().equals("")) {
					String[] customers = config.getCustomerName().split(",");
					if (customers.length > 0) {
						sql.append(" and device_customer in (");
						for (int i = 0; i < customers.length; i++) {
							sql.append("'");
							sql.append(customers[i]);
							if (i == customers.length - 1) {
								sql.append("')");
							}else{
								sql.append("',");
							}
						}
					}
				}
                if (config.getImei1() != null && !config.getImei1().equals("")&&config.getImei2() != null
                        && !config.getImei2().equals("")) {
                    sql.append(" and imei  >= '").append(config.getImei1()).append("' and imei  <= '").append(config.getImei2() + "'");
                }
				logger.info("sql:"+sql.toString());
				DBUtils.execute(sql.toString(), params);
				// add  by kui.li,end
				timerListner.getConfigs().remove(config);
			}
		}
		if (timerListner.getApks().size() == 0
				&& timerListner.getLinks().size() == 0
				&& timerListner.getTexts().size() == 0
				&& timerListner.getConfigs().size() == 0) {
			timerListner.closeListner();
		}
	}
}
