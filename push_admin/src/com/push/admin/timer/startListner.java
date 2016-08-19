package com.push.admin.timer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.push.admin.dbbean.TPushApk;
import com.push.admin.dbbean.TPushCommands;
import com.push.admin.dbbean.TPushLink;
import com.push.admin.dbbean.TPushText;
import com.push.admin.dbbean.TPushUpdateConfig;
import com.push.admin.util.DBUtils;
import com.push.admin.util.DateTools;

public class startListner implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(startListner.class);
	private static Calendar cal = Calendar.getInstance();

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if(TimerListner.getInstance()!=null){
			TimerListner.getInstance().closeListner();
		}		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//针对目前的设计：1. 如果是在服务器关闭期间达到push时间了，还可以再push；2. 如果到push时间没有任何push，重启服务器后会再次push，虽然不是希望的，但也不会导致重复push，可以接受；3.若后续改为push过的可再次push则当前设计必须更改为查询push时间，并且要修改Task避免重复push。
		String sql = "select * from t_push_apk where id not in (select push_id from t_push_apk_his group by push_id)";
		List<TPushApk> apks = DBUtils.query(sql, TPushApk.class);
		sql = "select * from t_push_text where id not in (select push_id from t_push_text_his group by push_id)";
		List<TPushText> texts = DBUtils.query(sql, TPushText.class);
		sql = "select * from t_push_link where id not in (select push_id from t_push_link_his group by push_id)";
		List<TPushLink> links = DBUtils.query(sql, TPushLink.class);
		sql = "select * from t_push_update_config where id not in (select push_id from t_push_update_config_his group by push_id)";
		List<TPushUpdateConfig> configs = DBUtils.query(sql, TPushUpdateConfig.class);
        sql = "select * from t_push_commands where id not in (select push_id from t_push_commands_his group by push_id)";
        List<TPushCommands> orders = DBUtils.query(sql, TPushCommands.class);
		if(apks.size()>0||texts.size()>0||links.size()>0||configs.size()>0||orders.size()>0){
			TimerListner.getInstance().setApks(apks);
			TimerListner.getInstance().setLinks(links);
			TimerListner.getInstance().setTexts(texts);
			TimerListner.getInstance().setConfigs(configs);
            TimerListner.getInstance().setOrders(orders);
			TimerListner.getInstance().openListner();
		}
		
		// 启动汇总timer
		/*
		cal.set(Calendar.HOUR_OF_DAY, 13);
		cal.set(Calendar.MINUTE, 12);
		Timer totalTimer = new Timer(true);
		totalTimer.schedule(totalTask, cal.getTime(), 24*60*60*1000);
		*/
	}
	
	/// total product Or country
	TimerTask totalTask = new TimerTask() {

		@Override
		public void run() {
			String totalDay = DateTools.formatDateToString(cal.getTime(), DateTools.FORSTR_YYYYHHMM);
			logger.info("totalTask begin ...... " + DateTools.formatDateToString(new Date(), DateTools.FORSTR_YYYYHHMMHHMMSS));
			
			// total product
			String sql = "INSERT INTO t_country(name,numbers,register_date) " +
					"SELECT country_name as name,CAST(count(uuid) AS CHAR) as uuid,SUBSTR(register_date,1,10) as date " +
					"FROM t_client " +
//					"GROUP BY country_name,SUBSTR(register_date,1,10)";
//							"WHERE SUBSTR(register_date,1,10)=? " +
							"GROUP BY country_name,SUBSTR(register_date,1,10)";
			logger.info("totalTask begin sql1=" + sql);
			List<String> lstParams = new ArrayList<String>();
//			lstParams.add(totalDay);
			long rtn = DBUtils.execute(sql, lstParams);
			logger.info("totalTask begin rtn1=" + rtn);
			
			// total product \ customer \ model
			sql = "INSERT INTO t_project(product,customer,model,version,numbers,register_date) " +
					"SELECT device_product,device_customer,device_model,device_version,count(uuid),SUBSTR(register_date,1,10) " +
					"FROM t_client " +
//					"WHERE SUBSTR(register_date,1,10)=? " +
					"GROUP BY device_product,device_customer,device_model,device_version,SUBSTR(register_date,1,10)";

			logger.info("totalTask begin sql2=" + sql);
			rtn = DBUtils.execute(sql, lstParams);
			logger.info("totalTask begin rtn2=" + rtn);
			
			// date ++
			cal.add(Calendar.DAY_OF_MONTH, 1);
			logger.info("totalTask end ...... " + DateTools.formatDateToString(cal.getTime(), DateTools.FORSTR_YYYYHHMMHHMMSS));			
		}		
	};
}
