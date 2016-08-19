/**
 * 
 */
package com.rgk.push.agent;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.rgk.push.bean.PushApkData;
import com.rgk.push.bean.PushClientData;
import com.rgk.push.bean.PushConfigData;
import com.rgk.push.bean.PushLinkData;
import com.rgk.push.bean.PushOrderData;
import com.rgk.push.bean.PushTextData;
import com.rgk.push.bean.RequestData;
import com.rgk.push.bean.Response;
import com.rgk.push.bean.UpdateData;
import com.rgk.push.dbbean.TClient;
import com.rgk.push.dbbean.TPushApkHis;
import com.rgk.push.dbbean.TPushCommandsHis;
import com.rgk.push.dbbean.TPushLinkHis;
import com.rgk.push.dbbean.TPushTextHis;
import com.rgk.push.dbbean.TPushUpdateClientHis;
import com.rgk.push.dbbean.TPushUpdateConfigHis;
import com.rgk.push.ip.Utils;
import com.rgk.push.util.DBUtils;
import com.rgk.push.util.DateTools;
import com.rgk.push.util.JSONUtils;
import com.rgk.push.util.StringUtil;

/**
 * @author kui.li
 *
 */
public class Agent_bak extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(Agent_bak.class);
	private ServletConfig configLoc;
	private static Map<String,String> mCountry = new HashMap<String,String>();

	/**
	 * 
	 */
	public Agent_bak() {

	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		configLoc=config;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//super.doGet(req, resp);
		System.out.println("===============doget================");
		dealRequest(req, resp);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("===============doPost================");
		//super.doPost(req, resp);
		dealRequest(req, resp);
	}

	/**
	 * 处理客户端的请求，并将要返回的数据格式组织输出
	 * 涉及接口：
	 * 1、客户端注册接口(c_regist)：本地卡1、卡2手机号码、设备型号、设备版本号、客户端版本、国家码变更时均需上上传
	 * 2、客户端信息更新接口(c_update)
	 * 3、客户端信息获取接口(c_get_msg)
	 * 4、APK推送反馈接口(c_push_apk)
	 * 5、链接推送反馈接口(c_push_links)
	 * 6、文本消息推送反馈接口(c_push_msg)
	 * 7、系统更新推送反馈接口(c_push_update)
	 * 8、配置更新反馈接口(c_push_config)
	 * 
	 * 客户端上传数据格式(JSON)
	 * 
	 * 
	 * @param req
	 * @param resp
	 */
	public void dealRequest(HttpServletRequest req, HttpServletResponse resp) {
//		logger.info("dealRequest start");
//		try {
//			resp.setContentType("text/html;charset=UTF-8");
//			req.setCharacterEncoding("UTF-8");
//			resp.setCharacterEncoding("UTF-8");
//			PrintWriter out=resp.getWriter();
//			String outStr = "";
//
//			// 获取参数
//			String sData = req.getParameter("data");
//			logger.info("req.getParameter(\"data\")=" + sData);
//			if(sData == null || "".equals(sData)) return; // 参数非法则直接return
//
//			// 数据解析 ，获取接口类型
//			JSONObject json = JSONObject.fromObject(sData);
//			Object name = json.get("name");
//			logger.info("json.get(\"name\")=" + name);
//			if(name != null && !"".equals(name)) { // 参数存在
//				String sName = String.valueOf(name); 
//
//				// check明细信息是否存在
//				if(json.get("details") != null && !"".equals(json.get("details"))) { // details信息存在
//					JSONObject dJson = json.getJSONObject("details");
//
////					logger.info("name=" + sName + ";details=" + dJson);
//
//					/**
//					 * 参数实例：
//					 * {"name":"c_regist","details":{"uuid":"868969010014527","version":"D3030_XOLO_L_F1_V0.3.2_S0203","model":"Cube_4.5","c_bin_version":"4","c_apk_version":"1","imei":"868969010014527","phoneNumber1":" 8618621323137","phoneNumber2":"-1","countryName":"en_US"}}
//					 */
//					if("c_regist".equals(sName)) { // 客户端注册接口(c_regist)
//						outStr = clientRegist(dJson,req);
//					} 
//					else if("c_update".equals(sName)) { // 客户端信息更新接口(c_update)
//						//暂时不实现
//						
//					/**
//					 * 参数实例：
//					 * {"name":"c_get_msg","details":{"uuid":"868969010014527"}}
//					 */
//					} else if("c_get_msg".equals(sName)) { // 客户端信息获取接口(c_get_msg)
//						RequestData reqData = JSONUtils.toBean(dJson, RequestData.class);
//						if(reqData != null) 
//							outStr = getMsg(reqData.getUuid());
//					/**
//					 * 参数实例：
//					 * {"name":"c_update_apk","details":{"server_id":"9","status":"12","date":"2015-03-03 21:52:20.000"}}
//					 */
//					} else if("c_update_apk".equals(sName)) { // APK推送反馈接口(c_update_apk)
//						outStr = updateApk(dJson);
//					
//					/**
//					 * 参数实例：
//					 * {"name":"c_update_link","details":{"server_id":"25318","status":"00","date":"2015-03-03 18:05:25.000"}}
//					 */
//					} else if("c_update_link".equals(sName)) { // 链接推送反馈接口(c_update_link)
//						outStr = updateLink(dJson);
//						
//						/**
//						 * 参数实例：
//						 * {"name":"c_update_text","details":{"server_id":"8441","status":"00","date":"2015-03-03 18:06:23.000"}}
//						 */
//					} else if("c_update_text".equals(sName)) { // 文本消息推送反馈接口(c_update_text)
//						outStr = updateText(dJson);
//						
//					} else if("c_update_client".equals(sName)) { // 系统更新推送反馈接口(c_update_client)
//						//outStr = updateClient(dJson);
//						// 客户端更新由每次注册时检测是否有更新
//						
//					} else if("c_update_config".equals(sName)) { // 配置更新反馈接口(c_update_config)
//						outStr = updateConfig(dJson);
//						
//						/**
//						 * 参数实例：
//						 * {"name":"c_update_commands","details":{"server_id":"139","status":"61","date":"2015-03-03 18:50:39.000"}}
//						 */
//					} else if("c_update_commands".equals(sName)) { // 配置更新反馈接口(c_update_config)
//                        outStr = updateOrder(dJson);
//                    } else {
//						outStr = Response.FAIL_NO_ID;
//					}
//				} else { // 无数据
//					outStr = Response.FAIL_NO_DATA;
//				}
//			} else {
//				outStr = Response.FAIL_NO_ID;
//			}
//			logger.info("outStr=" + outStr);
//			out.println(outStr);
//			out.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		logger.info("dealRequest end");
	}

//	/**
//	 * 客户端注册接口：本地卡1、卡2手机号码、设备型号、设备版本号、客户端版本、国家码变更时均需上上传
//	 * 1、客户端注册接口参数demo
//	 * 	{"name":"c_regist",
//			details:{
//				"uuid":"dfdafdafdafdafda", // Android设备UUID，设备唯一标识
//				"sim1":"13588889999", // 卡1 电话号码
//				"sim2":"13799998888", // 卡2电话号码
//				"model":"G101", // 设备型号，机器 型号
//				"version":"G101_V0.0.1_S20140101", // 设备版本号
//				"local":"zh_cn" // 国家码，标识用户所属国家
//			}
//		}
//	 * @param details
//	 * @return
//	 */
//	private String clientRegist(JSONObject details, HttpServletRequest request) {
//		String res = Response.SUCESS;
//		TClient bean = new TClient();//JSONUtils.toBean(details, TClient.class);
//		if(details.getString("uuid") == null || "".equals(details.getString("uuid"))) { // null return fail
//			return Response.FAIL;
//		} else {
//			if(StringUtil.isMessyCode(details.getString("uuid"))) // is message code return fail
//				return Response.FAIL;
//			bean.setUuid(details.getString("uuid"));
//		}
//		
//		if(details.getString("version") == null || "".equals(details.getString("version"))) { // null return fail
//			return Response.FAIL;
//		} else {
//			if(StringUtil.isMessyCode(details.getString("version"))) // is message code return fail
//				return Response.FAIL;
//			bean.setDeviceVersion(details.getString("version"));
//		}
//		
//		if(details.getString("model") == null || "".equals(details.getString("model"))) { // null return fail
//			return Response.FAIL;
//		} else {
//			if(StringUtil.isMessyCode(details.getString("model"))) // is message code return fail
//				return Response.FAIL;
//			bean.setDeviceModel(details.getString("model"));
//		}
//		
//		bean.setClientBinVersion(details.getString("c_bin_version"));
//		bean.setClientApkVersion(details.getString("c_apk_version"));
//		bean.setImei(details.getString("uuid"));
//		bean.setPhoneNumber1(details.getString("phoneNumber1"));
//		bean.setPhoneNumber2(details.getString("phoneNumber2"));
//
//		if(details.getString("countryName") == null || "".equals(details.getString("countryName"))) { // null return fail
//			return Response.FAIL;
//		} else {
//			if(StringUtil.isMessyCode(details.getString("countryName"))) // is message code return fail
//				return Response.FAIL;
//			bean.setCountryName(details.getString("countryName"));
//		}
//		
//		if(bean != null) {
//			
//			logger.info("bean.toString()=" + bean.toString());
//
//			// check wether exists uuid(imei)
//			String sql = "SELECT * FROM t_client WHERE uuid=" + bean.getUuid();
//			List<TClient> lstData = DBUtils.query(sql, TClient.class);
//			if(lstData != null && lstData.size()>0) { // data exists，update this record, only phone-number changed do update .
//				TClient oldData = lstData.get(0);
//				boolean isNeedUpdate = false;
//				if(!oldData.getPhoneNumber1().equals(bean.getPhoneNumber1())) {
//					oldData.setPhoneNumber1(bean.getPhoneNumber1());
//					isNeedUpdate = true;
//				}
//				if(!oldData.getPhoneNumber2().equals(bean.getPhoneNumber2())) {
//					oldData.setPhoneNumber2(bean.getPhoneNumber2());
//					isNeedUpdate = true;
//				}
//				
//				if(isNeedUpdate) {
//					String comments = oldData.getUpdateComments();
//					comments += DateTools.formatDateToString(new Date(),DateTools.FORSTR_YYYYHHMMHHMMSS) + " : phoneNumber1=" + oldData.getPhoneNumber1() + ";phoneNumber2=" + oldData.getPhoneNumber2() + "<br>";
//					oldData.setUpdateComments(comments);
//					oldData.setUpdateDate(new Timestamp(new Date().getTime()));
//					
//					DBUtils.update(oldData);
//				}
//			} else { // data is not exists, insert this record
//				
//				String ip = Utils.getRemortIP(request);
//				bean.setIp(ip);
//				String countryAddr = getConuntryName(bean.getCountryName());
//				if(countryAddr != null && !"".equals(countryAddr) && countryAddr.contains("-")) {
//					bean.setLanguage(countryAddr.split("-")[0].trim());
//					bean.setCountryName(countryAddr.split("-")[1].trim());
//				}
//				
//				String countryName=Utils.getIpCountryFromSina(ip);//国家	
//				if(countryName != null && !"".equals(countryName)) {
//					if(countryName.contains("_ip_info ="))
//						bean.setCountryName("本地局域网");
//					else
//						bean.setCountryName(countryName);
//				}
//				
//				logger.info("ip=" + ip + ", country_name=" + bean.getCountryName() + ", address=" + bean.getLanguage());
//				
//				bean.setUpdateComments(DateTools.formatDateToString(new Date(),DateTools.FORSTR_YYYYHHMMHHMMSS) + " : phoneNumber1=" + bean.getPhoneNumber1() + ";phoneNumber2=" + bean.getPhoneNumber2() + "<br>");
//				bean.setRegisterDate(new Timestamp(new Date().getTime()));
//				if(bean.getDeviceVersion() != null) {
//					String arrTmp[] = bean.getDeviceVersion().split("_");
//					if(arrTmp != null && arrTmp.length>2) {
//						bean.setDeviceProduct(arrTmp[0]);
//						bean.setDeviceCustomer(arrTmp[1]);
//					}
//				}
//				DBUtils.insert(bean);
//			}
//		}
//		return res;
//	}
//
//	/**
//	 * params:uuid
//	 * ID	名称		描述
//	 * uuid	UUID	Android设备UUID，设备唯一标识
//	 *
//	 * 根据uuid查询是否有推广信息，推广信息包含以下几张表，查到了组装数据返回给客户端
//	 * 目前仅支持返回一条，不支持多条，按下面顺序查到一条就返回。
//	 * 表顺序：t_push_apk_his、t_push_link_his、t_push_text_his、t_push_update_client_his、t_push_update_config_his
//	 * 数据格式参看3.2.3响应格式。
//	 * 数据组装成功后更新数据库那条记录的状态（发送中）
//	 * 未查询到仅返回code：00，无论是否查询到结果都返回成功(00)
//	 * ID		类型		名称		是否必须		描述
//	 *	code	Sting	返回码	是			标识此次客户端请求操作结果
//	 *										参照【返回码对应表】
//	 *	name	String	详细内容	否			存在推广信息时，此项存在，对应消息名称，如：
//	 *										push_apk
//	 *										push_link
//	 *										Push_text
//	 *										push_client
//	 *										Push_config
//	 *	details					否			Name对应自定义数据格式，参照【数据格式】
//	 *		对应数据格式		涉及到的表(以his表的push_id关联)
//	 *		push_apk(2.3): t_push_apk_his,t_push_apk,t_apks
//	 *		push_link(2.4): t_push_link_his,t_push_link
//	 *		push_text(2.5): t_push_text_his,t_push_text
//	 *		push_client(2.6): t_push_update_client_his,t_push_update_client
//	 *		push_config(2.7): t_push_update_config_his,t_push_update_config
//	 * @return 
//	 */
//	private String getMsg(String uuid) {
//		logger.info("getMsg start");
//		String resp = Response.SUCESS;
//		if(uuid != null) {
//			resp = queryPushApkData(uuid);
//			if(resp == Response.SUCESS) {
//				resp = queryPushConfigData(uuid);
//			}
//			if(resp == Response.SUCESS) {
//				resp = queryPushLinkData(uuid);
//			}
//			if(resp == Response.SUCESS) {
//				resp = queryPushTextData(uuid);
//			}
//            if(resp == Response.SUCESS) {
//                resp = queryPushOrderData(uuid);
//            }
//			if(resp == Response.SUCESS) {
//				String sql = "SELECT * FROM t_client WHERE uuid='" + uuid + "'";
//				List<TClient> list = DBUtils.query(sql, TClient.class);
//				if(list != null && list.size()>0) {
//					TClient data = list.get(0);
//					resp = queryPushClientData(uuid, Integer.parseInt(data.getClientBinVersion()), Integer.parseInt(data.getClientApkVersion()));
//				}
//			}
//		}
//		logger.info("getMsg end");
//		return resp;
//	}
//
//	private String queryPushApkData(String uuid) {
//		String resp = Response.SUCESS;
//		String sql = "SELECT TOP 1 id AS server_id,title,comments,url,package_name,icon,install_type " +
//				"FROM v_push_apk_his " +
//				"WHERE (ISNULL(status,0)=0 OR status=0) AND uuid='"+uuid+"'";
//		List<PushApkData> list = DBUtils.query(sql, PushApkData.class);
//		if(list.size()>0) {
//			PushApkData details = list.get(0);
//			Response.PushApkRes res = new Response.PushApkRes();
//			res.setDetails(details);
//			JSONObject jsonObject = JSONObject.fromObject(res);
//			logger.info("jsonObject="+jsonObject);
//			resp = jsonObject.toString();
//
//			// update the records status to sending.
//			sql = "UPDATE t_push_apk_his SET status=0 WHERE id=?";
//			List<Object> lstParams = new ArrayList<Object>();
//			lstParams.add(details.getServer_id());
//			DBUtils.execute(sql, lstParams);
//		}
//		return resp;
//	}
//
//	private String queryPushLinkData(String uuid) {
//		String resp = Response.SUCESS;
//		String sql = "SELECT TOP 1 id AS server_id,title,comments,url,icon " +
//				"FROM v_push_link_his " +
//				"WHERE (ISNULL(status,0)=0 OR status=0) AND uuid='"+uuid+"'";
//		List<PushLinkData> list = DBUtils.query(sql, PushLinkData.class);
//		if(list.size()>0) {
//			PushLinkData details = list.get(0);
//			Response.PushLinkRes res = new Response.PushLinkRes();
//			res.setDetails(details);
//			JSONObject jsonObject = JSONObject.fromObject(res);
//			logger.info("jsonObject="+jsonObject);
//			resp = jsonObject.toString();
//
//			// update the records status to sending.
//			sql = "UPDATE t_push_link_his SET status=0 WHERE id=?";
//			List<Object> lstParams = new ArrayList<Object>();
//			lstParams.add(details.getServer_id());
//			DBUtils.execute(sql, lstParams);
//		}
//		return resp;
//	}
//
//	private String queryPushTextData(String uuid) {
//		String resp = Response.SUCESS;
//		String sql = "SELECT TOP 1 id AS server_id,title,comments,icon  " +
//				"FROM v_push_text_his  " +
//				"WHERE (ISNULL(status,0)=0 OR status=0) AND uuid='"+uuid+"'";
//		List<PushTextData> list = DBUtils.query(sql, PushTextData.class);
//		if(list.size()>0) {
//			PushTextData details = list.get(0);
//			Response.PushTextRes res = new Response.PushTextRes();
//			res.setDetails(details);
//			JSONObject jsonObject = JSONObject.fromObject(res);
//			logger.info("jsonObject="+jsonObject);
//			resp = jsonObject.toString();
//
//			// update the records status to sending.
//			sql = "UPDATE t_push_text_his SET status=0 WHERE id=?";
//			List<Object> lstParams = new ArrayList<Object>();
//			lstParams.add(details.getServer_id());
//			DBUtils.execute(sql, lstParams);
//		}
//		return resp;
//	}
//
//    private String queryPushOrderData(String uuid) {
//        String resp = Response.SUCESS;
//        String sql = "SELECT TOP 1 id AS server_id,title,comments,commands  " +
//                "FROM v_push_commands_his  " +
//                "WHERE (ISNULL(status,0)=0 OR status=0) AND uuid='"+uuid+"'";
//        List<PushOrderData> list = DBUtils.query(sql, PushOrderData.class);
//        if(list.size()>0) {
//            PushOrderData details = list.get(0);
//            Response.PushOrderRes res = new Response.PushOrderRes();
//            res.setDetails(details);
//            JSONObject jsonObject = JSONObject.fromObject(res);
//            logger.info("jsonObject="+jsonObject);
//            resp = jsonObject.toString();
//
//            // update the records status to sending.
//            sql = "UPDATE t_push_commands_his SET status=0 WHERE id=?";
//            List<Object> lstParams = new ArrayList<Object>();
//            lstParams.add(details.getServer_id());
//            DBUtils.execute(sql, lstParams);
//        }
//        return resp;
//    }
//
//	private String queryPushClientData(String uuid, int binVersion, int apkVersion) {
//		logger.info("queryPushClientData start");
//		Connection conn = DBUtils.openConnection();
//		try {
//			/**
//			 * check update version
//			 * proc pro_check_update 
//			 * @uuid varchar(50),
//			 * @client_bin_version int,
//			 * @client_apk_version int
//			 */
//			CallableStatement cs = conn.prepareCall("{call pro_check_update(?,?,?)}");
//			cs.setString("uuid", uuid);
//			cs.setInt("client_bin_version", binVersion);
//			cs.setInt("client_apk_version", apkVersion);
//			ResultSet rs = null;
//			try{
//				rs = cs.executeQuery();
//			}catch(SQLServerException e) {
//				logger.info("无新数据返回");
//			}
//			if(rs != null)
//				while(rs.next()) {
//					PushClientData data = new PushClientData();
//					data.setApk_server_id(rs.getLong("apk_server_id"));
//					data.setApk_url(rs.getString("apk_url"));
//					data.setApk_version(rs.getInt("apk_version"));
//					data.setBin_server_id(rs.getLong("bin_server_id"));
//					data.setBin_url(rs.getString("bin_url"));
//					data.setBin_version(rs.getInt("bin_version"));
//
//					Response.PushClientRes res = new Response.PushClientRes();
//					res.setDetails(data);
//					JSONObject jsonObject = JSONObject.fromObject(res);
//					logger.info("jsonObject="+jsonObject);
//
//					if(rs!=null)rs.close();
//					if(cs!=null)cs.close();	
//					if(conn!=null)conn.close();
//
//					return jsonObject.toString();
//				}
//			if(rs!=null)rs.close();
//			if(cs!=null)cs.close();		
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return Response.SUCESS;
//	}
//
//	private String queryPushConfigData(String uuid) {
//		Connection conn = DBUtils.openConnection();
//		try {
//			/**
//			 * check update config
//			 * proc pro_check_config 
//			 * @uuid varchar(50)
//			 */
//			CallableStatement cs = conn.prepareCall("{call pro_check_config(?)}");
//			cs.setString("uuid", uuid);
//			ResultSet rs = null;
//			try {
//				rs = cs.executeQuery();
//			} catch(SQLServerException e) {
//				logger.info("无新数据返回");
//			}
//			if(rs != null)
//				while(rs.next()) {
//					PushConfigData data = new PushConfigData();
//					data.setServer_id(rs.getLong("server_id"));
//					data.setIp(rs.getString("ip"));
//					data.setPort(rs.getInt("port"));
//					data.setTime_out(rs.getLong("time_out"));
//					data.setUrl(rs.getString("url"));
//
//					Response.PushConfigRes res = new Response.PushConfigRes();
//					res.setDetails(data);
//					JSONObject jsonObject = JSONObject.fromObject(res);
//					logger.info("jsonObject="+jsonObject);
//
//					if(rs!=null)rs.close();
//					if(cs!=null)cs.close();	
//					if(conn!=null)conn.close();
//
//					return jsonObject.toString();
//				}
//			if(rs!=null)rs.close();
//			if(cs!=null)cs.close();		
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return Response.SUCESS;
//		/*
//		String resp = Response.SUCESS;
//		String sql = "select t2.id server_id,t1.tcp_ip ip,t1.tcp_port port,t1.http_addr url,time_out " +
//				"from t_push_update_config t1,t_push_update_config_his t2 " +
//				"where t1.id=t2.push_id and t2.uuid='"+uuid+"'";
//		List<PushConfigData> list = DBUtils.query(sql, PushConfigData.class);
//		if(list.size()>0) {
//			PushConfigData details = list.get(0);
//			Response.PushConfigRes res = new Response.PushConfigRes();
//			res.setDetails(details);
//			JSONObject jsonObject = JSONObject.fromObject(res);
//			logger.info("jsonObject="+jsonObject);
//			resp = jsonObject.toString();
//		}*/
//	}
//
//	/**
//	 * params:details(2.8)
//	 * 数据库：t_push_apk_his
//	 * 更新表的status和Update_comments字段
//	 * 根据params中的server_id,对应表的主键id
//	 * c_update_date更新时间使用传过来的时间（操作时间）
//	 * @return 
//	 */
//	private String updateApk(JSONObject dJson) {
//		String resp = Response.FAIL;
//		UpdateData ud = JSONUtils.toBean(dJson, UpdateData.class);
//		logger.info("updateApk: updatedata="+ud);
//		if(ud!=null && !ud.hasNullProp()) {
//			Long serverId = ud.getServer_id();
//			int status = TPushApkHis.getStatus(ud.getStatus());
//			if(status == -1) {
//				return Response.FAIL;
//			}
//			Timestamp cUpdateDate = formatClientUpdateDate(ud.getDate());
//			Timestamp updateDate = new Timestamp(System.currentTimeMillis());
//			String updateComments = formatUpdateComments(ud.getDate(), TPushApkHis.getStatusDesc(status));
//			TPushApkHis apkHis = new TPushApkHis();
//			apkHis.setId(serverId);
//			apkHis.setStatus(status);
//			apkHis.setCUpdateDate(cUpdateDate);
//			apkHis.setUpdateDate(updateDate);
//			apkHis.setUpdateComments(updateComments);
//			boolean res = DBUtils.update(apkHis);
//			if(res) {
//				resp = Response.SUCESS;
//			}
//		}
//		return resp;
//	}
//
//	/**
//	 * params:details(2.9)
//	 * 数据库：t_push_link_his
//	 * 更新表的status和Update_comments字段
//	 * 根据params中的server_id,对应表的主键id
//	 * c_update_date更新时间使用传过来的时间（操作时间）
//	 * @return 
//	 */
//	private String updateLink(JSONObject dJson) {
//		String resp = Response.FAIL;
//		UpdateData ud = JSONUtils.toBean(dJson, UpdateData.class);
//		logger.info("updateLink: updatedata="+ud);
//		if(ud!=null && !ud.hasNullProp()) {
//			Long serverId = ud.getServer_id();
//			int status = TPushLinkHis.getStatus(ud.getStatus());
//			if(status == -1) {
//				return Response.FAIL;
//			}
//			Timestamp cUpdateDate = formatClientUpdateDate(ud.getDate());
//			Timestamp updateDate = new Timestamp(System.currentTimeMillis());
//			String updateComments = formatUpdateComments(ud.getDate(), TPushLinkHis.getStatusDesc(status));
//			TPushLinkHis linkHis = new TPushLinkHis();
//			linkHis.setId(serverId);
//			linkHis.setStatus(status);
//			linkHis.setCUpdateDate(cUpdateDate);
//			linkHis.setUpdateDate(updateDate);
//			linkHis.setUpdateComments(updateComments);
//			boolean res = DBUtils.update(linkHis);
//			if(res) {
//				resp = Response.SUCESS;
//			}
//		}
//		return resp;
//	}
//
//	/**
//	 * params:details(2.10)
//	 * 数据库：t_push_text_his
//	 * 更新表的status和Update_comments字段
//	 * 根据params中的server_id,对应表的主键id
//	 * c_update_date更新时间使用传过来的时间（操作时间）
//	 * @return 
//	 */
//	private String updateText(JSONObject dJson) {
//		String resp = Response.FAIL;
//		UpdateData ud = JSONUtils.toBean(dJson, UpdateData.class);
//		logger.info("updateText: updatedata="+ud);
//		if(ud!=null && !ud.hasNullProp()) {
//			Long serverId = ud.getServer_id();
//			int status = TPushTextHis.getStatus(ud.getStatus());
//			if(status == -1) {
//				return Response.FAIL;
//			}
//			Timestamp cUpdateDate = formatClientUpdateDate(ud.getDate());
//			Timestamp updateDate = new Timestamp(System.currentTimeMillis());
//			String updateComments = formatUpdateComments(ud.getDate(), TPushTextHis.getStatusDesc(status));
//			TPushTextHis textHis = new TPushTextHis();
//			textHis.setId(serverId);
//			textHis.setStatus(status);
//			textHis.setCUpdateDate(cUpdateDate);
//			textHis.setUpdateDate(updateDate);
//			textHis.setUpdateComments(updateComments);
//			boolean res = DBUtils.update(textHis);
//			if(res) {
//				resp = Response.SUCESS;
//			}
//		}
//		return resp;
//	}
//
//	/**
//	 * params:details(2.11)
//	 * 数据库：t_push_update_client_his
//	 * 更新表的status和Update_comments字段
//	 * 根据params中的server_id,对应表的主键id
//	 * c_update_date更新时间使用传过来的时间（操作时间）
//	 * @return 
//	 */
//	private String updateClient(JSONObject dJson) {
//		String resp = Response.FAIL;
//		UpdateData ud = JSONUtils.toBean(dJson, UpdateData.class);
//		logger.info("updateClient: updatedata="+ud);
//		if(ud!=null && !ud.hasNullProp()) {
//			Long serverId = ud.getServer_id();
//			int status = TPushUpdateClientHis.getStatus(ud.getStatus());
//			if(status == -1) {
//				return Response.FAIL;
//			}
//			Timestamp cUpdateDate = formatClientUpdateDate(ud.getDate());
//			Timestamp updateDate = new Timestamp(System.currentTimeMillis());
//			String updateComments = formatUpdateComments(ud.getDate(), TPushUpdateClientHis.getStatusDesc(status));
//			TPushUpdateClientHis clientHis = new TPushUpdateClientHis();
//			clientHis.setId(serverId);
//			clientHis.setStatus(status);
//			clientHis.setCUpdateDate(cUpdateDate);
//			clientHis.setUpdateDate(updateDate);
//			clientHis.setUpdateComments(updateComments);
//			boolean res = DBUtils.update(clientHis);
//			if(res) {
//				resp = Response.SUCESS;
//			}
//		}
//		return resp;
//	}
//
//	/**
//	 * params:details(2.12)
//	 * 数据库：t_push_update_config_his
//	 * 更新表的status和Update_comments字段
//	 * 根据params中的server_id,对应表的主键id
//	 * c_update_date更新时间使用传过来的时间（操作时间）
//	 * @return 
//	 */
//	private String updateConfig(JSONObject dJson) {
//		String resp = Response.FAIL;
//		UpdateData ud = JSONUtils.toBean(dJson, UpdateData.class);
//		logger.info("updateConfig: updatedata="+ud);
//		if(ud!=null && !ud.hasNullProp()) {
//			Long serverId = ud.getServer_id();
//			int status = TPushUpdateConfigHis.getStatus(ud.getStatus());
//			if(status == -1) {
//				return Response.FAIL;
//			}
//			Timestamp cUpdateDate = formatClientUpdateDate(ud.getDate());
//			Timestamp updateDate = new Timestamp(System.currentTimeMillis());
//			String updateComments = formatUpdateComments(ud.getDate(), TPushUpdateConfigHis.getStatusDesc(status));
//			TPushUpdateConfigHis configHis = new TPushUpdateConfigHis();
//			configHis.setId(serverId);
//			configHis.setStatus(status);
//			configHis.setCUpdateDate(cUpdateDate);
//			configHis.setUpdateDate(updateDate);
//			configHis.setUpdateComments(updateComments);
//			boolean res = DBUtils.update(configHis);
//			if(res) {
//				resp = Response.SUCESS;
//			}
//		}
//		return resp;
//	}
//
//    /**
//     * params:details(2.12)
//     * 数据库：t_push_update_config_his
//     * 更新表的status和Update_comments字段
//     * 根据params中的server_id,对应表的主键id
//     * c_update_date更新时间使用传过来的时间（操作时间）
//     * @return
//     */
//    private String updateOrder(JSONObject dJson) {
//        String resp = Response.FAIL;
//        UpdateData ud = JSONUtils.toBean(dJson, UpdateData.class);
//        logger.info("updateOrder: updatedata="+ud);
//        if(ud!=null && !ud.hasNullProp()) {
//            Long serverId = ud.getServer_id();
//            int status = TPushCommandsHis.getStatus(ud.getStatus());
//            if(status == -1) {
//                return Response.FAIL;
//            }
//            Timestamp cUpdateDate = formatClientUpdateDate(ud.getDate());
//            Timestamp updateDate = new Timestamp(System.currentTimeMillis());
//            String updateComments = formatUpdateComments(ud.getDate(), TPushUpdateConfigHis.getStatusDesc(status));
//            TPushCommandsHis orderHis = new TPushCommandsHis();
//            orderHis.setId(serverId);
//            orderHis.setStatus(status);
//            orderHis.setCUpdateDate(cUpdateDate);
//            orderHis.setUpdateDate(updateDate);
//            orderHis.setUpdateComments(updateComments);
//            boolean res = DBUtils.update(orderHis);
//            if(res) {
//                resp = Response.SUCESS;
//            }
//        }
//        return resp;
//    }
//
//	private String formatUpdateComments(String dateStr, String statusDesc) {
//		StringBuilder comments = new StringBuilder();
//		comments.append(dateStr);
//		comments.append(" 状态->");
//		comments.append(statusDesc);
//		return comments.toString();
//	}
//
//	private Timestamp formatClientUpdateDate(String dateStr) {
//		Timestamp updateDate = null;
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try {
//			updateDate = new Timestamp(sdf.parse(dateStr).getTime());
//		} catch(Exception e) {
//			logger.error(e.toString(), e);
//		}
//		return updateDate;
//	}
//
//	/**
//	 * 获取国家码
//	 * @param countryCode
//	 * @return
//	 */
//	private static String getConuntryName(String countryCode) {
//		return mCountry.get(countryCode);
//	}
//
//	static {
//
//		// init 国家语言码表
//		mCountry.put("af_ZA", "公用荷兰语 - 南非");
//
//		mCountry.put("sq_AL", "阿尔巴尼亚语 -阿尔巴尼亚");
//
//		mCountry.put("ar_DZ", "阿拉伯语 -阿尔及利亚");
//		mCountry.put("ar_BH", "阿拉伯语 -巴林");
//		mCountry.put("ar_EG", "阿拉伯语 -埃及");
//		mCountry.put("ar_IQ", "阿拉伯语 -伊拉克");
//		mCountry.put("ar_JO", "阿拉伯语 -约旦");
//		mCountry.put("ar_KW", "阿拉伯语 -科威特");
//		mCountry.put("ar_LB", "阿拉伯语 -黎巴嫩");
//		mCountry.put("ar_LY", "阿拉伯语 -利比亚");
//		mCountry.put("ar_MA", "阿拉伯语 -摩洛哥");
//		mCountry.put("ar_OM", "阿拉伯语 -阿曼");
//		mCountry.put("ar_QA", "阿拉伯语 -卡塔尔");
//		mCountry.put("ar_SA", "阿拉伯语 - 沙特阿拉伯");
//		mCountry.put("ar_SY", "阿拉伯语 -叙利亚共和国");
//		mCountry.put("ar_TN", "阿拉伯语 -北非的共和国");
//		mCountry.put("ar_AE", "阿拉伯语 - 阿拉伯联合酋长国");
//		mCountry.put("ar_YE", "阿拉伯语 -也门");
//		mCountry.put("ar_IL", "阿拉伯语 -以色列");
//
//		mCountry.put("hy_AM", "亚美尼亚语 -亚美尼亚");
//
//		mCountry.put("az_AZ_Cyrl", "Azeri语-(西里尔字母) 阿塞拜疆");
//		mCountry.put("az_AZ_Latn", "Azeri语(拉丁文)- 阿塞拜疆");
//
//		mCountry.put("eu_ES", "巴斯克语 -巴斯克");
//
//		mCountry.put("be_BY", "Belarusian-白俄罗斯");
//
//		mCountry.put("bg_BG", "保加利亚语 -保加利亚");
//
//		mCountry.put("ca_ES", "嘉泰罗尼亚语 -嘉泰罗尼亚");
//		mCountry.put("zh_HK", "汉语 - 香港");
//		mCountry.put("zh_MO", "汉语 - 澳门");
//		mCountry.put("zh_CN", "汉语 -中国");
//		mCountry.put("zh_CHS", "汉语 (单一化)");
//		mCountry.put("zh_SG", "汉语 -新加坡");
//		mCountry.put("zh_TW", "汉语 -台湾");
//		mCountry.put("zh_CHT", "汉语 (传统)");
//
//		mCountry.put("hr_HR", "克罗埃西亚语 -克罗埃西亚");
//
//		mCountry.put("cs_CZ", "捷克语 - 捷克");
//
//		mCountry.put("da_DK", "丹麦文语 -丹麦");
//
//		mCountry.put("div_MV", "Dhivehi-马尔代夫");
//
//		mCountry.put("nl_BE", "荷兰语 -比利时");
//		mCountry.put("nl_NL", "荷兰语 - 荷兰");
//
//		mCountry.put("en_AU", "英语 -澳洲");
//		mCountry.put("en_BZ", "英语 -伯利兹");
//		mCountry.put("en_CA", "英语 -加拿大");
//		mCountry.put("en_CB", "英语 -加勒比海");
//		mCountry.put("en_IE", "英语 -爱尔兰");
//		mCountry.put("en_IN", "英语 -印度");
//		mCountry.put("en_JM", "英语 -牙买加");
//		mCountry.put("en_NZ", "英语 -新西兰");
//		mCountry.put("en_PH", "英语 -菲律宾共和国");
//		mCountry.put("en_ZA", "英语 -南非");
//		mCountry.put("en_TT", "英语 -千里达托贝哥共和国");
//		mCountry.put("en_GB", "英语 -英国");
//		mCountry.put("en_US", "英语 -美国");
//		mCountry.put("en_ZW", "英语 -津巴布韦");
//		mCountry.put("en_SG", "英语 -新加波");
//
//		mCountry.put("et_EE", "爱沙尼亚语 -爱沙尼亚");
//
//		mCountry.put("fo_FO", "Faroese- 法罗群岛");
//
//		mCountry.put("fa_IR", "波斯语 -伊朗王国");
//
//		mCountry.put("fi_FI", "芬兰语 -芬兰");
//
//		mCountry.put("fr_BE", "法语 -比利时");
//		mCountry.put("fr_CA", "法语 -加拿大");
//		mCountry.put("fr_FR", "法语 -法国");
//		mCountry.put("fr_LU", "法语 -卢森堡");
//		mCountry.put("fr_MC", "法语 -摩纳哥");
//		mCountry.put("fr_CH", "法语 -瑞士");
//
//		mCountry.put("gl_ES", "加利西亚语 -加利西亚");
//
//		mCountry.put("ka_GE", "格鲁吉亚州语 -格鲁吉亚州");
//
//		mCountry.put("de_AT", "德语 -奥地利");
//		mCountry.put("de_DE", "德语 -德国");
//		mCountry.put("de_LI", "德语 -列支敦士登");
//		mCountry.put("de_LU", "德语-卢森堡");
//		mCountry.put("de_CH", "德语 -瑞士");
//
//		mCountry.put("el_GR", "希腊语 -希腊");
//
//		mCountry.put("gu_IN", "Gujarati-印度");
//
//		mCountry.put("he_IL", "希伯来语 -以色列");
//
//		mCountry.put("hi_IN", "北印度语 -印度");
//
//		mCountry.put("hu_HU", "匈牙利语 -匈牙利");
//
//		mCountry.put("is_IS", "冰岛语 -冰岛");
//
//		mCountry.put("id_ID", "印尼语 -印尼");
//
//		mCountry.put("it_IT", "意大利语 -意大利");
//		mCountry.put("it_CH", "意大利语 -瑞士");
//
//		mCountry.put("ja_JP", "日语 -日本");
//
//		mCountry.put("kn_IN", "卡纳达语 -印度");
//
//		mCountry.put("kk_KZ", "Kazakh-哈萨克");
//
//		mCountry.put("kok_IN", "Konkani-印度");
//
//		mCountry.put("ko_KR", "韩语 -韩国");
//
//		mCountry.put("ky_KZ", "Kyrgyz-哈萨克");
//
//		mCountry.put("lv_LV", "拉脱维亚语 -拉脱维亚");
//
//		mCountry.put("lt_LT", "立陶宛语 -立陶宛");
//
//		mCountry.put("mk_MK", "马其顿语 -FYROM");
//
//		mCountry.put("ms_BN", "马来语 -汶莱");
//		mCountry.put("ms_MY", "马来语 -马来西亚");
//
//		mCountry.put("mr_IN", "马拉地语 -印度");
//
//		mCountry.put("mn_MN", "蒙古语 -蒙古");
//
//		mCountry.put("nb_NO", "挪威 (Bokm?l) - 挪威");
//		mCountry.put("nn_NO", "挪威 (Nynorsk)- 挪威");
//
//		mCountry.put("pl_PL", "波兰语 -波兰");
//
//		mCountry.put("pt_BR", "葡萄牙语 -巴西");
//		mCountry.put("pt_PT", "葡萄牙语 -葡萄牙");
//
//		mCountry.put("pa_IN", "Punjab 语 -印度");
//
//		mCountry.put("ro_RO", "罗马尼亚语 -罗马尼亚");
//
//		mCountry.put("ru_RU", "俄语 -俄国");
//
//		mCountry.put("sa_IN", "梵文语 -印度");
//		mCountry.put("sr_SP_Cyrl", "塞尔维亚语 -(西里尔字母的) 塞尔维亚共和国");
//		mCountry.put("sr_SP_Latn", "塞尔维亚语 (拉丁文)- 塞尔维亚共和国");
//
//		mCountry.put("sk_SK", "斯洛伐克语 -斯洛伐克");
//
//		mCountry.put("sl_SI", "斯洛文尼亚语 -斯洛文尼亚");
//
//		mCountry.put("es_AR", "西班牙语 -阿根廷");
//		mCountry.put("es_BO", "西班牙语 -玻利维亚");
//		mCountry.put("es_CL", "西班牙语 -智利");
//		mCountry.put("es_CO", "西班牙语 -哥伦比亚");
//		mCountry.put("es_CR", "西班牙语 - 哥斯达黎加");
//		mCountry.put("es_DO", "西班牙语 - 多米尼加共和国");
//		mCountry.put("es_EC", "西班牙语 -厄瓜多尔");
//		mCountry.put("es_SV", "西班牙语 - 萨尔瓦多");
//		mCountry.put("es_GT", "西班牙语 -危地马拉");
//		mCountry.put("es_HN", "西班牙语 -洪都拉斯");
//		mCountry.put("es_MX", "西班牙语 -墨西哥");
//		mCountry.put("es_NI", "西班牙语 -尼加拉瓜");
//		mCountry.put("es_PA", "西班牙语 -巴拿马");
//		mCountry.put("es_PY", "西班牙语 -巴拉圭");
//		mCountry.put("es_PE", "西班牙语 -秘鲁");
//		mCountry.put("es_PR", "西班牙语 - 波多黎各");
//		mCountry.put("es_ES", "西班牙语 -西班牙");
//		mCountry.put("es_US", "西班牙语 -美国");
//		mCountry.put("es_UY", "西班牙语 -乌拉圭");
//		mCountry.put("es_VE", "西班牙语 -委内瑞拉");
//
//		mCountry.put("sw_KE", "Swahili-肯尼亚");
//
//		mCountry.put("sv_FI", "瑞典语 -芬兰");
//		mCountry.put("sv_SE", "瑞典语 -瑞典");
//
//		mCountry.put("syr_SY", "Syriac-叙利亚共和国");
//
//		mCountry.put("ta_IN", "坦米尔语 -印度");
//
//		mCountry.put("tt_RU", "Tatar-俄国");
//
//		mCountry.put("te_IN", "Telugu-印度");
//
//		mCountry.put("th_TH", "泰语 -泰国");
//
//		mCountry.put("tr_TR", "土耳其语 -土耳其");
//
//		mCountry.put("uk_UA", "乌克兰语 -乌克兰");
//
//		mCountry.put("ur_PK", "Urdu-巴基斯坦");
//
//		mCountry.put("uz_UZ_Cyrl", "Uzbek-(西里尔字母的) 乌兹别克斯坦");
//		mCountry.put("uz_UZ_Latn", "Uzbek(拉丁文)- 乌兹别克斯坦");
//
//		mCountry.put("vi_VN", "越南语 -越南");
//		
//		mCountry.put("bn_IN", "孟加拉语 -印度");
//		mCountry.put("iw_IL", "希伯来语 -以色列");
//		mCountry.put("in_ID", "印度尼西亚语 -印度尼西亚");
//		mCountry.put("sr_RS", "塞尔维亚语 -塞尔维亚");
//		mCountry.put("tl_PH", "菲律宾语 -菲律宾");
//		mCountry.put("rm_CH", "罗曼什语 -瑞士");
//		mCountry.put("my_MM", "缅甸语 -缅甸");
//		mCountry.put("km_KH", "柬埔寨语 -柬埔寨");
//		mCountry.put("sw_TZ", "斯瓦希里语 -坦桑尼亚");
//		mCountry.put("zu_ZA", "祖鲁语 -南非");
//		mCountry.put("lo_LA", "老挝语 -老挝");
//		mCountry.put("ne_NP", "尼泊尔语 -尼泊尔");
//		mCountry.put("si_LK", "僧加罗语 -斯里兰卡");
//	}
}
