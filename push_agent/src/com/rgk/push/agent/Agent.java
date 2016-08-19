/**
 * 
 */
package com.rgk.push.agent;

import java.io.IOException;
import java.io.PrintWriter;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

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
import com.rgk.push.dbbean.TPushCommands;
import com.rgk.push.dbbean.TPushCommandsHis;
import com.rgk.push.dbbean.TPushLinkHis;
import com.rgk.push.dbbean.TPushTextHis;
import com.rgk.push.dbbean.TPushUpdateClient;
import com.rgk.push.dbbean.TPushUpdateClientHis;
import com.rgk.push.dbbean.TPushUpdateConfig;
import com.rgk.push.dbbean.TPushUpdateConfigHis;
import com.rgk.push.dbbean.VPushApkHis;
import com.rgk.push.dbbean.VPushCommandsHis;
import com.rgk.push.dbbean.VPushLinkHis;
import com.rgk.push.dbbean.VPushTextHis;
import com.rgk.push.ip.Utils;
import com.rgk.push.util.DBUtils;
import com.rgk.push.util.DateTools;
import com.rgk.push.util.JSONUtils;
import com.rgk.push.util.JSONUtils.JSON_TYPE;
import com.rgk.push.util.StringUtil;

/**
 * @author kui.li
 *
 */
public class Agent extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(Agent.class);
	private ServletConfig configLoc;
	private static Map<String,String> mCountry = new HashMap<String,String>();

	/**
	 * 
	 */
	public Agent() {

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
		logger.info("dealRequest start");
		try {
			resp.setContentType("text/html;charset=UTF-8");
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			PrintWriter out=resp.getWriter();
			String outStr = "";

			// 获取参数
			String sData = req.getParameter("data");
			logger.info("req.getParameter(\"data\")=" + sData);
			if(sData == null || "".equals(sData)) return; // 参数非法则直接return

			// 数据解析 ，获取接口类型
			if(JSONUtils.getJSONType(sData) == JSON_TYPE.JSON_TYPE_ARRAY) {
				JSONArray array = JSONArray.fromObject(sData);
				for(int i=0;i<array.size();i++) {
					JSONObject json = array.getJSONObject(i);
					outStr = handleRequestItem(req, resp, json);
				}
			} else {
				JSONObject json = JSONObject.fromObject(sData);
				outStr = handleRequestItem(req, resp, json);
			}
			/*JSONObject json = JSONObject.fromObject(sData);
			outStr = handleRequestItem(req, resp, json);*/
			logger.info("outStr=" + outStr);
			out.println(outStr);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("dealRequest end");
	}
	
	private String handleRequestItem(HttpServletRequest req, HttpServletResponse resp, JSONObject json) {
		String outStr = "";
		Object name = json.get("name");
		logger.info("json.get(\"name\")=" + name);
		if(name != null && !"".equals(name)) { // 参数存在
			String sName = String.valueOf(name); 

			// check明细信息是否存在
			if(json.get("details") != null && !"".equals(json.get("details"))) { // details信息存在
				JSONObject dJson = json.getJSONObject("details");

//				logger.info("name=" + sName + ";details=" + dJson);

				/**
				 * 参数实例：
				 * {"name":"c_regist","details":{"uuid":"868969010014527","version":"D3030_XOLO_L_F1_V0.3.2_S0203","model":"Cube_4.5","c_bin_version":"4","c_apk_version":"1","imei":"868969010014527","phoneNumber1":" 8618621323137","phoneNumber2":"-1","countryName":"en_US"}}
				 */
				if("c_regist".equals(sName)) { // 客户端注册接口(c_regist)
					outStr = clientRegist(dJson,req);
				} 
				else if("c_update".equals(sName)) { // 客户端信息更新接口(c_update)
					//暂时不实现
					
				/**
				 * 参数实例：
				 * {"name":"c_get_msg","details":{"uuid":"868969010014527"}}
				 */
				} else if("c_get_msg".equals(sName)) { // 客户端信息获取接口(c_get_msg)
					/*
					RequestData reqData = JSONUtils.toBean(dJson, RequestData.class);
					if(reqData != null) 
						outStr = getMsg2(reqData.getUuid());*/
					outStr = getMsg(dJson);
				/**
				 * 参数实例：
				 * {"name":"c_get_push_client_apk","details":{"uuid":"868969010014527"}}
				 */
				} else if("c_get_push_client_apk".equals(sName)) { // 客户端信息获取接口(c_get_msg)
					RequestData reqData = JSONUtils.toBean(dJson, RequestData.class);
					if(reqData != null) 
						outStr = getMsg(reqData.getUuid(), sName);
				/**
				 * 参数实例：
				 * {"name":"c_get_push_client_bin","details":{"uuid":"868969010014527"}}
				 */
				} else if("c_get_push_client_bin".equals(sName)) { // 客户端信息获取接口(c_get_msg)
					RequestData reqData = JSONUtils.toBean(dJson, RequestData.class);
					if(reqData != null) 
						outStr = getMsg(reqData.getUuid(), sName);
				/**
				 * 参数实例：
				 * {"name":"c_get_push_config","details":{"uuid":"868969010014527"}}
				 */
				} else if("c_get_push_config".equals(sName)) { // 客户端信息获取接口(c_get_msg)
					RequestData reqData = JSONUtils.toBean(dJson, RequestData.class);
					if(reqData != null) 
						outStr = getMsg(reqData.getUuid(), sName);
				/**
				 * 参数实例：
				 * {"name":"c_get_push_apk","details":{"uuid":"868969010014527"}}
				 */
				} else if("c_get_push_apk".equals(sName)) { // 客户端信息获取接口(c_get_msg)
					RequestData reqData = JSONUtils.toBean(dJson, RequestData.class);
					if(reqData != null) 
						outStr = getMsg(reqData.getUuid(), sName);
				/**
				 * 参数实例：
				 * {"name":"c_get_push_link","details":{"uuid":"868969010014527"}}
				 */
				} else if("c_get_push_link".equals(sName)) { // 客户端信息获取接口(c_get_msg)
					RequestData reqData = JSONUtils.toBean(dJson, RequestData.class);
					if(reqData != null) 
						outStr = getMsg(reqData.getUuid(), sName);
				/**
				 * 参数实例：
				 * {"name":"c_get_push_text","details":{"uuid":"868969010014527"}}
				 */
				} else if("c_get_push_text".equals(sName)) { // 客户端信息获取接口(c_get_msg)
					RequestData reqData = JSONUtils.toBean(dJson, RequestData.class);
					if(reqData != null) 
						outStr = getMsg(reqData.getUuid(), sName);
				/**
				 * 参数实例：
				 * {"name":"c_get_push_order","details":{"uuid":"868969010014527"}}
				 */
				} else if("c_get_push_order".equals(sName)) { // 客户端信息获取接口(c_get_msg)
					RequestData reqData = JSONUtils.toBean(dJson, RequestData.class);
					if(reqData != null) 
						outStr = getMsg(reqData.getUuid(), sName);
				/**
				 * 参数实例：
				 * {"name":"c_update_apk","details":{"server_id":"9","status":"12","date":"2015-03-03 21:52:20.000"}}
				 */
				} else if("c_update_apk".equals(sName)) { // APK推送反馈接口(c_update_apk)
					outStr = updateApk(dJson);
				
				/**
				 * 参数实例：
				 * {"name":"c_update_link","details":{"server_id":"25318","status":"00","date":"2015-03-03 18:05:25.000"}}
				 */
				} else if("c_update_link".equals(sName)) { // 链接推送反馈接口(c_update_link)
					outStr = updateLink(dJson);
					
					/**
					 * 参数实例：
					 * {"name":"c_update_text","details":{"server_id":"8441","status":"00","date":"2015-03-03 18:06:23.000"}}
					 */
				} else if("c_update_text".equals(sName)) { // 文本消息推送反馈接口(c_update_text)
					outStr = updateText(dJson);
					
				} else if("c_update_client".equals(sName)) { // 系统更新推送反馈接口(c_update_client)
					outStr = updateClient(dJson);
					// 客户端更新由每次注册时检测是否有更新
					
				} else if("c_update_config".equals(sName)) { // 配置更新反馈接口(c_update_config)
					outStr = updateConfig(dJson);
					
					/**
					 * 参数实例：
					 * {"name":"c_update_commands","details":{"server_id":"139","status":"61","date":"2015-03-03 18:50:39.000"}}
					 */
				} else if("c_update_commands".equals(sName)) { // 配置更新反馈接口(c_update_config)
                    outStr = updateOrder(dJson);
                } else {
					outStr = Response.FAIL_NO_ID;
				}
			} else { // 无数据
				outStr = Response.FAIL_NO_DATA;
			}
		} else {
			outStr = Response.FAIL_NO_ID;
		}
		
		return outStr;
	}

	/**
	 * 客户端注册接口：本地卡1、卡2手机号码、设备型号、设备版本号、客户端版本、国家码变更时均需上上传
	 * 1、客户端注册接口参数demo
	 * 	{"name":"c_regist",
			details:{
				"uuid":"dfdafdafdafdafda", // Android设备UUID，设备唯一标识
				"sim1":"13588889999", // 卡1 电话号码
				"sim2":"13799998888", // 卡2电话号码
				"model":"G101", // 设备型号，机器 型号
				"version":"G101_V0.0.1_S20140101", // 设备版本号
				"local":"zh_cn" // 国家码，标识用户所属国家
			}
		}
	 * @param details
	 * @return
	 */
	private String clientRegist(JSONObject details, HttpServletRequest request) {
		String res = Response.SUCESS;
		TClient bean = new TClient();//JSONUtils.toBean(details, TClient.class);
		if(details.getString("uuid") == null || "".equals(details.getString("uuid"))) { // null return fail
			return Response.FAIL;
		} else {
			if(StringUtil.isMessyCode(details.getString("uuid"))) // is message code return fail
				return Response.FAIL;
			bean.setUuid(details.getString("uuid"));
		}
		
		if(details.getString("version") == null || "".equals(details.getString("version"))) { // null return fail
			return Response.FAIL;
		} else {
			if(StringUtil.isMessyCode(details.getString("version"))) // is message code return fail
				return Response.FAIL;
			bean.setDeviceVersion(details.getString("version"));
		}
		
		if(details.getString("model") == null || "".equals(details.getString("model"))) { // null return fail
			return Response.FAIL;
		} else {
			if(StringUtil.isMessyCode(details.getString("model"))) // is message code return fail
				return Response.FAIL;
			bean.setDeviceModel(details.getString("model"));
		}
		
		bean.setClientBinVersion(details.getString("c_bin_version"));
		bean.setClientApkVersion(details.getString("c_apk_version"));
		bean.setImei(details.getString("uuid"));
		bean.setPhoneNumber1(details.getString("phoneNumber1"));
		bean.setPhoneNumber2(details.getString("phoneNumber2"));
		
		//yingjing.liu 20160816 add start retry 
//		Integer retry = details.getInt("retry");// 如果来的是空getInt 就不行了 
		
		try{
			String retryStr = details.getString("retry");
			if(retryStr != null && !"".equals(retryStr)){
				Integer retry = Integer.valueOf(retryStr);
				bean.setRetry(retry);	
			}else{
				bean.setRetry(TClient.RETRY_NULL);   //为空时候默认值
			}
		}catch(JSONException e){
			bean.setRetry(TClient.RETRY_NORETRY);		//取不到
		}catch(Exception e){
			bean.setRetry(TClient.RETRY_STRING); 	//只有Integer转中文的时候才会错
		}
		try{
			String timestamp = details.getString("timestamp");
			timestamp = timestamp==null?"":timestamp;//如果取得 null 得为空字符串
			System.out.println("timestamp: "+timestamp);
			bean.setTimestamp(timestamp);   //为空时候默认值
		}catch(Exception e){
			bean.setTimestamp(""); 	//取不到为空字符串值为空
		}
		//yingjing.liu 20160816 add end
		
		if(details.getString("countryName") == null || "".equals(details.getString("countryName"))) { // null return fail
			return Response.FAIL;
		} else {
			if(StringUtil.isMessyCode(details.getString("countryName"))) // is message code return fail
				return Response.FAIL;
			bean.setCountryName(details.getString("countryName"));
		}
		
		if(bean != null) {
			
			logger.info("bean.toString()=" + bean.toString());

			// check wether exists uuid(imei)
			String sql = "SELECT * FROM t_client WHERE uuid=" + bean.getUuid();
			List<TClient> lstData = DBUtils.query(sql, TClient.class);
			if(lstData != null && lstData.size()>0) { // data exists，update this record, only phone-number changed do update .
				TClient oldData = lstData.get(0);
				boolean isNeedUpdate = false;
				int updateNum = 0;
				if(!oldData.getPhoneNumber1().equals(bean.getPhoneNumber1())) {
					oldData.setPhoneNumber1(bean.getPhoneNumber1());
					isNeedUpdate = true;
					updateNum = 1;
				}
				if(!oldData.getPhoneNumber2().equals(bean.getPhoneNumber2())) {
					oldData.setPhoneNumber2(bean.getPhoneNumber2());
					isNeedUpdate = true;
					updateNum = 2;
				}
				if(!oldData.getClientApkVersion().equals(bean.getClientApkVersion())) {					
					// 比较版本号系统中版本大小 ，确认是否需要更新t_push_update_client_his表
					if(Integer.parseInt(oldData.getClientApkVersion()) < Integer.parseInt(bean.getClientApkVersion())) {
						sql = "update t_push_update_client_his set status=3,update_date=now() where uuid='" + bean.getUuid() + "' and push_id=(select id from t_push_update_client where type=2 and version=" + bean.getClientApkVersion() + ")";
						List<String> lstSql = new ArrayList<String>();
						lstSql.add(sql);
						DBUtils.execute(lstSql);
					}
					
					oldData.setClientApkVersion(bean.getClientApkVersion());
					isNeedUpdate = true;
					updateNum = 3;
				}
				if(!oldData.getClientBinVersion().equals(bean.getClientBinVersion())) {
					if(Integer.parseInt(oldData.getClientBinVersion()) < Integer.parseInt(bean.getClientBinVersion())) {
						sql = "update t_push_update_client_his set status=3,update_date=now() where uuid='" + bean.getUuid() + "' and push_id=(select id from t_push_update_client where type=1 and version=" + bean.getClientBinVersion() + ")";
						List<String> lstSql = new ArrayList<String>();
						lstSql.add(sql);
						DBUtils.execute(lstSql);
					}
					
					oldData.setClientBinVersion(bean.getClientBinVersion());
					isNeedUpdate = true;
					updateNum = 4;
				}
				
				if(isNeedUpdate) {
					String comments = "";//oldData.getUpdateComments();
					comments += DateTools.formatDateToString(new Date(),DateTools.FORSTR_YYYYHHMMHHMMSS) + " : ";
					switch(updateNum) {
					case 1:
						comments += "phoneNumber1=" + oldData.getPhoneNumber1() + ";";
						break;
					case 2:
						comments += "phoneNumber2=" + oldData.getPhoneNumber2() + ";";
						break;
					case 3:
						comments += "apkVersion=" + oldData.getClientApkVersion() + ";";
						break;
					case 4:
						comments += "binVersion=" + oldData.getClientBinVersion() + ";";
						break;
					}
					comments += "<br>";
					logger.error("updateComments : comments="+comments+"---->"+oldData.getImei());
					System.out.println("updateComments : comments="+comments+"---->"+oldData.getImei());
					oldData.setUpdateComments(comments);
					oldData.setUpdateDate(new Timestamp(new Date().getTime()));
					
					DBUtils.update(oldData);
				}
				
				// 数据更新成功，比较注册时间与现在时间差，若超过半年返回：OVER_HALF_YEAR {\"code\":\"01\"}
				long registTime = oldData.getRegisterDate().getTime()/1000;
				long nowTime = System.currentTimeMillis()/1000;
				if((nowTime - registTime) > 6*30*24*60*60)
					return Response.OVER_HALF_YEAR;
				
			} else { // data is not exists, insert this record
				
				String ip = Utils.getRemortIP(request);
				bean.setIp(ip);
				String countryAddr = getConuntryName(bean.getCountryName());
				if(countryAddr != null && !"".equals(countryAddr) && countryAddr.contains("-")) {
					bean.setLanguage(countryAddr.split("-")[0].trim());
					bean.setCountryName(countryAddr.split("-")[1].trim());
				}
				
				String countryName=Utils.getIpCountryFromSina(ip);//国家	
				if(countryName != null && !"".equals(countryName)) {
					if(countryName.contains("_ip_info ="))
						bean.setCountryName("本地局域网");
					else
						bean.setCountryName(countryName);
				}
				
				logger.info("ip=" + ip + ", country_name=" + bean.getCountryName() + ", address=" + bean.getLanguage());
				
				bean.setUpdateComments(DateTools.formatDateToString(new Date(),DateTools.FORSTR_YYYYHHMMHHMMSS) + " : phoneNumber1=" + bean.getPhoneNumber1() + ";phoneNumber2=" + bean.getPhoneNumber2() + "<br>");
				bean.setRegisterDate(new Timestamp(new Date().getTime()));
				if(bean.getDeviceVersion() != null) {
					String arrTmp[] = bean.getDeviceVersion().split("_");
					if(arrTmp != null && arrTmp.length>2) {
						bean.setDeviceProduct(arrTmp[0]);
						bean.setDeviceCustomer(arrTmp[1]);
					}
				}
				sql = "insert into t_client(" +
						"uuid,phone_number1,phone_number2,imei,device_product,device_customer,device_model,device_version,client_bin_version," +
						"client_apk_version,ip,country_name,language,register_date,update_comments,retry,timestamp) values(" +
						"'"+bean.getUuid()+"','"+bean.getPhoneNumber1()+"','" + bean.getPhoneNumber2() + "','" + bean.getImei() + "'," +
								"'" + bean.getDeviceProduct() + "','" + bean.getDeviceCustomer() + "','" + bean.getDeviceModel() + "'," +
										"'"+ bean.getDeviceVersion() +"','" +bean.getClientBinVersion()+ "','" +bean.getClientApkVersion()+ "'," +
												"'" +bean.getIp()+ "','" +bean.getCountryName()+ "','" +bean.getLanguage()+ "'," +
														"'" +DateTools.formatDateToString(bean.getRegisterDate(),DateTools.FORSTR_YYYYHHMMHHMMSS)+ "','" +bean.getUpdateComments()+
														"',"+bean.getRetry()+",'"+bean.getTimestamp()+"')";
				
				boolean sId = DBUtils.execute(sql);
				logger.info("sId=" + sId);
			}
		}
		return res;
	}

	/**
	 * params:uuid
	 * ID	名称		描述
	 * uuid	UUID	Android设备UUID，设备唯一标识
	 *
	 * 根据uuid查询是否有推广信息，推广信息包含以下几张表，查到了组装数据返回给客户端
	 * 目前仅支持返回一条，不支持多条，按下面顺序查到一条就返回。
	 * 表顺序：t_push_apk_his、t_push_link_his、t_push_text_his、t_push_update_client_his、t_push_update_config_his
	 * 数据格式参看3.2.3响应格式。
	 * 数据组装成功后更新数据库那条记录的状态（发送中）
	 * 未查询到仅返回code：00，无论是否查询到结果都返回成功(00)
	 * ID		类型		名称		是否必须		描述
	 *	code	Sting	返回码	是			标识此次客户端请求操作结果
	 *										参照【返回码对应表】
	 *	name	String	详细内容	否			存在推广信息时，此项存在，对应消息名称，如：
	 *										push_apk
	 *										push_link
	 *										Push_text
	 *										push_client
	 *										Push_config
	 *	details					否			Name对应自定义数据格式，参照【数据格式】
	 *		对应数据格式		涉及到的表(以his表的push_id关联)
	 *		push_apk(2.3): t_push_apk_his,t_push_apk,t_apks
	 *		push_link(2.4): t_push_link_his,t_push_link
	 *		push_text(2.5): t_push_text_his,t_push_text
	 *		push_client(2.6): t_push_update_client_his,t_push_update_client
	 *		push_config(2.7): t_push_update_config_his,t_push_update_config
	 * @return 
	 */
	private String getMsg(String uuid) {
		logger.info("getMsg start");
		String resp = Response.SUCESS;
		if(uuid != null) {
			if(resp == Response.SUCESS) {
				resp = queryPushClientData(uuid);
			}
			if(resp == Response.SUCESS) {
				resp = queryPushConfigData(uuid);
			}
			if(resp == Response.SUCESS) {
				resp = queryPushApkData(uuid);
			}
			if(resp == Response.SUCESS) {
				resp = queryPushLinkData(uuid);
			}
			if(resp == Response.SUCESS) {
				resp = queryPushTextData(uuid);
			}
            if(resp == Response.SUCESS) {
                resp = queryPushOrderData(uuid);
            }
		}
		logger.info("getMsg end");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getMsg2(String uuid) {
		String resp = Response.SUCESS;
		if(uuid != null) {
			List reslist = new ArrayList();
			Response.PushClientRes clientApkRes = queryPushClientApk(uuid);
			if(clientApkRes != null) {
				reslist.add(clientApkRes);
			}
			Response.PushClientRes clientBinRes = queryPushClientBin(uuid);
			if(clientBinRes != null) {
				reslist.add(clientBinRes);
			}
			Response.PushConfigRes configRes = queryPushConfig(uuid);
			if(configRes != null) {
				reslist.add(configRes);
			}
			List<Response.PushApkRes> apklist = queryPushApk(uuid);
			if(apklist != null && apklist.size() > 0) {
				reslist.addAll(apklist);
			}
			List<Response.PushLinkRes> linklist = queryPushLink(uuid);
			if(linklist != null && linklist.size() > 0) {
				reslist.addAll(linklist);
			}
			List<Response.PushTextRes> textlist = queryPushText(uuid);
			if(textlist != null && textlist.size() > 0) {
				reslist.addAll(textlist);
			}
			List<Response.PushOrderRes> orderlist = queryPushOrder(uuid);
			if(orderlist != null && orderlist.size() > 0) {
				reslist.addAll(orderlist);
			}
			if(reslist.size() > 0) {
				resp = arrayToJson(reslist.toArray());
				logger.info("getMsg2: "+resp);
			}
		}
		return resp;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getMsg(JSONObject object) {
		String resp = Response.SUCESS;
		String uuid = object.getString("uuid");
		if(uuid != null && !"".equals(uuid)) {
			List reslist = new ArrayList();
			//Response.PushClientRes clientApkRes = queryPushClientApk(uuid);
			Response.PushClientRes clientApkRes = queryPushClientApk(object);
			if(clientApkRes != null) {
				reslist.add(clientApkRes);
			}
			//Response.PushClientRes clientBinRes = queryPushClientBin(uuid);
			Response.PushClientRes clientBinRes = queryPushClientBin(object);
			if(clientBinRes != null) {
				reslist.add(clientBinRes);
			}
			Response.PushConfigRes configRes = queryPushConfig(uuid);
			if(configRes != null) {
				reslist.add(configRes);
			}
			List<Response.PushApkRes> apklist = queryPushApk(uuid);
			if(apklist != null && apklist.size() > 0) {
				reslist.addAll(apklist);
			}
			List<Response.PushLinkRes> linklist = queryPushLink(uuid);
			if(linklist != null && linklist.size() > 0) {
				reslist.addAll(linklist);
			}
			List<Response.PushTextRes> textlist = queryPushText(uuid);
			if(textlist != null && textlist.size() > 0) {
				reslist.addAll(textlist);
			}
			List<Response.PushOrderRes> orderlist = queryPushOrder(uuid);
			if(orderlist != null && orderlist.size() > 0) {
				reslist.addAll(orderlist);
			}
			if(reslist.size() > 0) {
				resp = arrayToJson(reslist.toArray());
				logger.info("getMsg2: "+resp);
			}
		}
		return resp;
	}
	
	public String getMsg(String uuid, String name) {
		logger.info("getMsg start");
		String resp = Response.SUCESS;
		if(uuid != null) {
			if("c_get_push_client_apk".equals(name)) {
				resp = queryPushClientApkData(uuid);
			} else if("c_get_push_client_bin".equals(name)) {
				resp = queryPushClientBinData(uuid);
			} else if("c_get_push_config".equals(name)) {
				resp = queryPushConfigData(uuid);
			} else if("c_get_push_apk".equals(name)) {
				resp = queryPushApkData(uuid);
			} else if("c_get_push_link".equals(name)) {
				resp = queryPushLinkData(uuid);
			} else if("c_get_push_text".equals(name)) {
				resp = queryPushTextData(uuid);
			} else if("c_get_push_order".equals(name)) {
				resp = queryPushOrderData(uuid);
			}
		}
		logger.info("getMsg end");
		return resp;
	}

	private String queryPushApkData(String uuid) {
		String resp = Response.SUCESS;
		String sql = "SELECT id AS server_id,title,comments,url,package_name,icon,install_type " +
				"FROM v_push_apk_his " +
				"WHERE (status is null or status = 0) AND uuid='"+uuid+"' limit 1";
		List<VPushApkHis> list = DBUtils.query(sql, VPushApkHis.class);
		if(list.size()>0) {
			VPushApkHis data = list.get(0);

			// update the records status to sending.
			sql = "UPDATE t_push_apk_his SET status=0 WHERE id=" + data.getId();
			boolean sId = DBUtils.execute(sql);
			if(sId) {
				
				// create return data
				PushApkData details = new PushApkData();
				details.setComments(data.getComments());
				details.setIcon(data.getIcon());
				details.setInstall_type(data.getInstallType());
				details.setPackage_name(data.getPackageName());
				details.setServer_id(data.getId());
				details.setTitle(data.getTitle());
				details.setUrl(data.getUrl());
				
				Response.PushApkRes res = new Response.PushApkRes();
				res.setDetails(details);
				JSONObject jsonObject = JSONObject.fromObject(res);
				logger.info("queryPushApkData uuid=" + uuid + " jsonObject="+jsonObject);
				resp = jsonObject.toString();
			}
		}
		return resp;
	}
	
	private List<Response.PushApkRes> queryPushApk(String uuid) {
		List<Response.PushApkRes> reslist = new ArrayList<Response.PushApkRes>();
		/*
		String sql = "SELECT id AS server_id,title,comments,url,package_name,icon,install_type " +
				"FROM v_push_apk_his " +
				"WHERE (status is null or status = 0) AND uuid='"+uuid+"' limit 1";*/
		String sql = "SELECT id AS server_id,title,comments,url,package_name,icon,install_type " +
				"FROM v_push_apk_his " +
				"WHERE (status is null or status = 0) AND uuid='"+uuid+"'";
		List<VPushApkHis> list = DBUtils.query(sql, VPushApkHis.class);
		if(list.size()>0) {
			for(VPushApkHis data : list) {
				// update the records status to sending.
				sql = "UPDATE t_push_apk_his SET status=0 WHERE id=" + data.getId();
				boolean sId = DBUtils.execute(sql);
				if(sId) {
					
					// create return data
					PushApkData details = new PushApkData();
					details.setComments(data.getComments());
					details.setIcon(data.getIcon());
					details.setInstall_type(data.getInstallType());
					details.setPackage_name(data.getPackageName());
					details.setServer_id(data.getId());
					details.setTitle(data.getTitle());
					details.setUrl(data.getUrl());
					
					Response.PushApkRes res = new Response.PushApkRes();
					res.setDetails(details);
					
					reslist.add(res);
				}
			}
		}
		return reslist;
	}

	private String queryPushLinkData(String uuid) {
		String resp = Response.SUCESS;
		String sql = "SELECT id AS server_id,title,link_type,comments,url,icon " +
				"FROM v_push_link_his " +
				"WHERE (status is null or status = 0) AND uuid='"+uuid+"' limit 1";
		List<VPushLinkHis> list = DBUtils.query(sql, VPushLinkHis.class);
		if(list.size()>0) {
			VPushLinkHis data = list.get(0);

			// update the records status to sending.
			sql = "UPDATE t_push_link_his SET status=0 WHERE id=" + data.getId();
			boolean sId = DBUtils.execute(sql);
			if(sId) {
				
				// create return data
				PushLinkData details = new PushLinkData();
				details.setComments(data.getComments());
				details.setIcon(data.getIcon());
				details.setServer_id(data.getId());
				details.setTitle(data.getTitle());
				details.setUrl(data.getUrl());
				details.setLinkType(data.getLinkType()==null?0:data.getLinkType());
				
				Response.PushLinkRes res = new Response.PushLinkRes();
				res.setDetails(details);
				JSONObject jsonObject = JSONObject.fromObject(res);
				logger.info("queryPushLinkData uuid=" + uuid + " jsonObject="+jsonObject);
				resp = jsonObject.toString();
			}
		}
		return resp;
	}
	
	private List<Response.PushLinkRes> queryPushLink(String uuid) {
		List<Response.PushLinkRes> reslist = new ArrayList<Response.PushLinkRes>();
		/*
		String sql = "SELECT id AS server_id,title,comments,url,icon " +
				"FROM v_push_link_his " +
				"WHERE (status is null or status = 0) AND uuid='"+uuid+"' limit 1";*/
		String sql = "SELECT id AS server_id,title,link_type,comments,url,icon " +
				"FROM v_push_link_his " +
				"WHERE (status is null or status = 0) AND uuid='"+uuid+"'";
		List<VPushLinkHis> list = DBUtils.query(sql, VPushLinkHis.class);
		if(list.size()>0) {
			for(VPushLinkHis data : list) {
				// update the records status to sending.
				sql = "UPDATE t_push_link_his SET status=0 WHERE id=" + data.getId();
				boolean sId = DBUtils.execute(sql);
				if(sId) {
					
					// create return data
					PushLinkData details = new PushLinkData();
					details.setComments(data.getComments());
					details.setIcon(data.getIcon());
					details.setServer_id(data.getId());
					details.setTitle(data.getTitle());
					details.setUrl(data.getUrl());
					details.setLinkType(data.getLinkType()==null?0:data.getLinkType());
					
					Response.PushLinkRes res = new Response.PushLinkRes();
					res.setDetails(details);
					
					reslist.add(res);
				}
			}
			
		}
		return reslist;
	}

	private String queryPushTextData(String uuid) {
		String resp = Response.SUCESS;
		String sql = "SELECT id AS server_id,title,comments,icon  " +
				"FROM v_push_text_his  " +
				"WHERE (status is null or status = 0) AND uuid='"+uuid+"' limit 1";
		List<VPushTextHis> list = DBUtils.query(sql, VPushTextHis.class);
		if(list.size()>0) {
			VPushTextHis data = list.get(0);

			// update the records status to sending.
			sql = "UPDATE t_push_text_his SET status=0 WHERE id=" + data.getId();
			boolean sId = DBUtils.execute(sql);
			if(sId) {
				// create return data
				PushTextData details = new PushTextData();
				details.setComments(data.getComments());
				details.setIcon(data.getIcon());
				details.setServer_id(data.getId());
				details.setTitle(data.getTitle());
				
				Response.PushTextRes res = new Response.PushTextRes();
				res.setDetails(details);
				JSONObject jsonObject = JSONObject.fromObject(res);
				logger.info("queryPushTextData uuid=" + uuid + " jsonObject="+jsonObject);
				resp = jsonObject.toString();
			}
		}
		return resp;
	}
	
	private List<Response.PushTextRes> queryPushText(String uuid) {
		List<Response.PushTextRes> reslist = new ArrayList<Response.PushTextRes>();
		/*
		String sql = "SELECT id AS server_id,title,comments,icon  " +
				"FROM v_push_text_his  " +
				"WHERE (status is null or status = 0) AND uuid='"+uuid+"' limit 1";*/
		String sql = "SELECT id AS server_id,title,comments,icon  " +
				"FROM v_push_text_his  " +
				"WHERE (status is null or status = 0) AND uuid='"+uuid+"'";
		List<VPushTextHis> list = DBUtils.query(sql, VPushTextHis.class);
		if(list.size()>0) {
			for(VPushTextHis data : list) {
				// update the records status to sending.
				sql = "UPDATE t_push_text_his SET status=0 WHERE id=" + data.getId();
				boolean sId = DBUtils.execute(sql);
				if(sId) {
					// create return data
					PushTextData details = new PushTextData();
					details.setComments(data.getComments());
					details.setIcon(data.getIcon());
					details.setServer_id(data.getId());
					details.setTitle(data.getTitle());
					
					Response.PushTextRes res = new Response.PushTextRes();
					res.setDetails(details);
					
					reslist.add(res);
				}
			}
			
		}
		return reslist;
	}

    private String queryPushOrderData(String uuid) {
        String resp = Response.SUCESS;

        // 先查询his表中是否有记录未处理完成 
		String sql = "SELECT id AS server_id,title,comments,commands  " +
				"FROM v_push_commands_his  " +
				"WHERE (status is null or status = 0) AND uuid='"+uuid+"' limit 1";

        List<VPushCommandsHis> list = DBUtils.query(sql, VPushCommandsHis.class);
        if(list.size()>0) { // his表中有记录，直接返回到客户端
        	VPushCommandsHis data = list.get(0);

            // update the records status to sending.
            sql = "UPDATE t_push_commands_his SET status=0 WHERE id=" + data.getId();
			boolean sId = DBUtils.execute(sql);
			if(sId) {
				// create return data
	        	PushOrderData details = new PushOrderData();
				details.setComments(data.getComments());
				details.setCommands(data.getCommands());
				details.setServer_id(data.getId());
				details.setTitle(data.getTitle());				
	        	
	            Response.PushOrderRes res = new Response.PushOrderRes();
	            res.setDetails(details);
	            JSONObject jsonObject = JSONObject.fromObject(res);
				logger.info("queryPushOrderData uuid=" + uuid + " jsonObject="+jsonObject);
	            resp = jsonObject.toString();				
			}
		} else { // his表中无记录，从command表中查询是否有数据
			sql = "select * from t_client where uuid='" + uuid + "'";
			List<TClient> lstClient = DBUtils.query(sql, TClient.class);
			if (lstClient != null && lstClient.size() > 0) {
				TClient client = lstClient.get(0);
				sql = "select id,title,comments,commands from t_push_commands "
						+ "where id not in (select push_id from t_push_commands_his where status > 0 and uuid='"
						+ uuid
						+ "') "
						+ "and (project_name is null or project_name='' or project_name like '%"
						+ client.getDeviceProduct()
						+ "%') "
						+ "and (customer_name is null or customer_name='' or customer_name like '%"
						+ client.getDeviceCustomer()
						+ "%') "
						+ "and (model_name is null or model_name='' or model_name like '%"
						+ client.getDeviceModel()
						+ "%') "
						+ "and (version_name is null or version_name='' or version_name like '%"
						+ client.getDeviceVersion()
						+ "%') "
						+ "and (country_name is null or country_name='' or country_name like '%"
						+ client.getCountryName() + "%') ";
				List<TPushCommands> lstCommands = DBUtils.query(sql, TPushCommands.class);
				long sId = 0;
				if (lstCommands != null && lstCommands.size() > 0) {
					TPushCommands commands = lstCommands.get(0);

					TPushCommandsHis his = new TPushCommandsHis();
					his.setPushId(commands.getId());
					his.setStatus(0);
					his.setUuid(uuid);
					his.setPushDate(new Timestamp(System.currentTimeMillis()));
					sId = DBUtils.insert(his);
					if (sId > 0) {
						PushOrderData details = new PushOrderData();
						details.setComments(commands.getComments());
						details.setCommands(commands.getCommands());
						details.setServer_id(sId);
						details.setTitle(commands.getTitle());

						Response.PushOrderRes res = new Response.PushOrderRes();
						res.setDetails(details);
						JSONObject jsonObject = JSONObject.fromObject(res);
						logger.info("queryPushOrderData uuid=" + uuid
								+ " jsonObject=" + jsonObject);
						resp = jsonObject.toString();
					}
				}
			}
		}
        return resp;
    }
    
    private List<Response.PushOrderRes> queryPushOrder(String uuid) {
    	List<Response.PushOrderRes> reslist = new ArrayList<Response.PushOrderRes>();

        // 先查询his表中是否有记录未处理完成 
    	/*
		String sql = "SELECT id AS server_id,title,comments,commands  " +
				"FROM v_push_commands_his  " +
				"WHERE (status is null or status = 0) AND uuid='"+uuid+"' limit 1";*/
    	String sql = "SELECT id AS server_id,title,comments,commands  " +
				"FROM v_push_commands_his  " +
				"WHERE (status is null or status = 0) AND uuid='"+uuid+"'";

        List<VPushCommandsHis> list = DBUtils.query(sql, VPushCommandsHis.class);
        if(list.size()>0) { // his表中有记录，直接返回到客户端
        	for(VPushCommandsHis data : list) {
        		// update the records status to sending.
                sql = "UPDATE t_push_commands_his SET status=0 WHERE id=" + data.getId();
    			boolean sId = DBUtils.execute(sql);
    			if(sId) {
    				// create return data
    	        	PushOrderData details = new PushOrderData();
    				details.setComments(data.getComments());
    				details.setCommands(data.getCommands());
    				details.setServer_id(data.getId());
    				details.setTitle(data.getTitle());				
    	        	
    				Response.PushOrderRes res = new Response.PushOrderRes();
    	            res.setDetails(details);	
    	            
    	            reslist.add(res);
    			}
        	}
            
		} else { // his表中无记录，从command表中查询是否有数据
			sql = "select * from t_client where uuid='" + uuid + "'";
			List<TClient> lstClient = DBUtils.query(sql, TClient.class);
			if (lstClient != null && lstClient.size() > 0) {
				TClient client = lstClient.get(0);
				sql = "select id,title,comments,commands from t_push_commands "
						+ "where id not in (select push_id from t_push_commands_his where status > 0 and uuid='"
						+ uuid
						+ "') "
						+ "and (project_name is null or project_name='' or project_name like '%"
						+ client.getDeviceProduct()
						+ "%') "
						+ "and (customer_name is null or customer_name='' or customer_name like '%"
						+ client.getDeviceCustomer()
						+ "%') "
						+ "and (model_name is null or model_name='' or model_name like '%"
						+ client.getDeviceModel()
						+ "%') "
						+ "and (version_name is null or version_name='' or version_name like '%"
						+ client.getDeviceVersion()
						+ "%') "
						+ "and (country_name is null or country_name='' or country_name like '%"
						+ client.getCountryName() + "%') ";
				List<TPushCommands> lstCommands = DBUtils.query(sql, TPushCommands.class);
				long sId = 0;
				if (lstCommands != null && lstCommands.size() > 0) {
					for(TPushCommands commands : lstCommands) {
						TPushCommandsHis his = new TPushCommandsHis();
						his.setPushId(commands.getId());
						his.setStatus(0);
						his.setUuid(uuid);
						his.setPushDate(new Timestamp(System.currentTimeMillis()));
						sId = DBUtils.insert(his);
						if (sId > 0) {
							PushOrderData details = new PushOrderData();
							details.setComments(commands.getComments());
							details.setCommands(commands.getCommands());
							details.setServer_id(sId);
							details.setTitle(commands.getTitle());

							Response.PushOrderRes res = new Response.PushOrderRes();
							res.setDetails(details);
							
							reslist.add(res);
						}
					}
				}
			}
		}
        return reslist;
    }

    /**
     * 获取新版本更新数据
     * @param uuid
     * @return
     */
    private String queryPushClientData(String uuid) {
    	String sql = "select * from t_client where uuid='" + uuid + "'";
    	List<TClient> lstData = DBUtils.query(sql, TClient.class);
    	if(lstData != null && lstData.size()>0) {
    		TClient client = lstData.get(0);
    		// 查询可更新的client bin
    		sql = "select id,url,version from t_push_update_client where " +
    				"(project_name is null or project_name='' or project_name like '%" + client.getDeviceProduct() + "%') and " +
    				"(customer_name is null or customer_name='' or customer_name like'%" + client.getDeviceCustomer() + "%') and " +
    				"(model_name is null or model_name='' or  model_name like '%" + client.getDeviceModel() + "%') and " +
    				"(version_name is null or version_name='' or version_name like '%" + client.getDeviceVersion() + "%') and " +
    				"(country_name is null or country_name='' or country_name like '%" + client.getCountryName() + "%') and " +
    				"type=1 and push_date < now() and version > " + client.getClientBinVersion() + " order by version desc";
    		List<TPushUpdateClient> lstTmp = DBUtils.query(sql, TPushUpdateClient.class);
    		if(lstTmp != null && lstTmp.size()>0) {
    			// 数据存在 ，insert到his表
    			TPushUpdateClient tmp = lstTmp.get(0);
    			
    			// 查询是否存在 此his
    			sql = "select id from t_push_update_client_his where uuid='" + uuid + "' and push_id=" + tmp.getId();
    			List<TPushUpdateClientHis> lstHis = DBUtils.query(sql, TPushUpdateClientHis.class);
    			long sId = 0;
    			if(lstHis == null || lstHis.size()==0) {
        			
        			TPushUpdateClientHis his = new TPushUpdateClientHis();
        			his.setPushId(tmp.getId());
        			his.setStatus(1);
        			his.setUuid(uuid);
        			his.setCreateDate(new Timestamp(System.currentTimeMillis()));
        			sId = DBUtils.insert(his);
    			} else {
    				sId = lstHis.get(0).getId();
    			}
    			
    			if(sId > 0 ){        			
        			PushClientData data = new PushClientData();
    				data.setBin_server_id(sId);
    				data.setBin_url(tmp.getUrl());
    				data.setBin_version(tmp.getVersion());

    				Response.PushClientRes res = new Response.PushClientRes();
    				res.setDetails(data);
    				JSONObject jsonObject = JSONObject.fromObject(res);
    				logger.info("bin jsonObject="+jsonObject);
    				return jsonObject.toString();
    			}
    		}
    		
    		// 查询可更新的client apk
    		lstTmp.clear();
    		sql = "select id,url,version from t_push_update_client where " +
    				"(project_name is null or project_name='' or project_name like '%" + client.getDeviceProduct() + "%') and " +
    				"(customer_name is null or customer_name='' or customer_name like'%" + client.getDeviceCustomer() + "%') and " +
    				"(model_name is null or model_name='' or  model_name like '%" + client.getDeviceModel() + "%') and " +
    				"(version_name is null or version_name='' or version_name like '%" + client.getDeviceVersion() + "%') and " +
    				"(country_name is null or country_name='' or country_name like '%" + client.getCountryName() + "%') and " +
    				"type=2 and push_date < now() and version > " + client.getClientApkVersion() + " order by version desc";
    		//sql = "select id,url,version from t_push_update_client where type=2 and push_date < now() and version > " + client.getClientApkVersion() + " order by version desc limit 1";
    		lstTmp = DBUtils.query(sql, TPushUpdateClient.class);
    		if(lstTmp != null && lstTmp.size()>0) {
    			// 数据存在 ，insert到his表
    			TPushUpdateClient tmp = lstTmp.get(0);
    			// 查询是否存在 此his
    			sql = "select id from t_push_update_client_his where uuid='" + uuid + "' and push_id=" + tmp.getId();
    			List<TPushUpdateClientHis> lstHis = DBUtils.query(sql, TPushUpdateClientHis.class);
    			long sId = 0;
    			if(lstHis == null || lstHis.size()==0) {
    				
        			TPushUpdateClientHis his = new TPushUpdateClientHis();
        			his.setPushId(tmp.getId());
        			his.setStatus(1);
        			his.setUuid(uuid);
        			his.setCreateDate(new Timestamp(System.currentTimeMillis()));
        			sId = DBUtils.insert(his);
    			} else {
    				sId = lstHis.get(0).getId();
    			}
    			
    			if(sId > 0 ){        			
        			PushClientData data = new PushClientData();
    				data.setApk_server_id(sId);
    				data.setApk_url(tmp.getUrl());
    				data.setApk_version(tmp.getVersion());

    				Response.PushClientRes res = new Response.PushClientRes();
    				res.setDetails(data);
    				JSONObject jsonObject = JSONObject.fromObject(res);
    				logger.info("apk jsonObject="+jsonObject);
    				return jsonObject.toString();
    			}
    		}
    		
    	}
    	
		return Response.SUCESS;
    }
    
    private String queryPushClientApkData(String uuid) {
    	String sql = "select * from t_client where uuid='" + uuid + "'";
    	List<TClient> lstData = DBUtils.query(sql, TClient.class);
    	if(lstData != null && lstData.size()>0) {
    		TClient client = lstData.get(0);
    		// 查询可更新的client apk
    		sql = "select id,url,version from t_push_update_client where " +
    				"(project_name is null or project_name='' or project_name like '%" + client.getDeviceProduct() + "%') and " +
    				"(customer_name is null or customer_name='' or customer_name like'%" + client.getDeviceCustomer() + "%') and " +
    				"(model_name is null or model_name='' or  model_name like '%" + client.getDeviceModel() + "%') and " +
    				"(version_name is null or version_name='' or version_name like '%" + client.getDeviceVersion() + "%') and " +
    				"(country_name is null or country_name='' or country_name like '%" + client.getCountryName() + "%') and " +
    				"(imei1 is null or imei1='' or imei1<='"+client.getImei()+"') and (imei2 is null or imei2='' or imei2>='"+client.getImei()+"') and " +
    				"type=2 and push_date < now() and version > " + client.getClientApkVersion() + " order by version desc";
    		//sql = "select id,url,version from t_push_update_client where type=2 and push_date < now() and version > " + client.getClientApkVersion() + " order by version desc limit 1";
    		List<TPushUpdateClient> lstTmp = DBUtils.query(sql, TPushUpdateClient.class);
    		if(lstTmp != null && lstTmp.size()>0) {
    			// 数据存在 ，insert到his表
    			TPushUpdateClient tmp = lstTmp.get(0);
    			// 查询是否存在 此his
    			sql = "select id from t_push_update_client_his where uuid='" + uuid + "' and push_id=" + tmp.getId();
    			List<TPushUpdateClientHis> lstHis = DBUtils.query(sql, TPushUpdateClientHis.class);
    			long sId = 0;
    			if(lstHis == null || lstHis.size()==0) {
    				
        			TPushUpdateClientHis his = new TPushUpdateClientHis();
        			his.setPushId(tmp.getId());
        			his.setStatus(1);
        			his.setUuid(uuid);
        			his.setCreateDate(new Timestamp(System.currentTimeMillis()));
        			sId = DBUtils.insert(his);
    			} else {
    				sId = lstHis.get(0).getId();
    			}
    			
    			if(sId > 0 ){        			
        			PushClientData data = new PushClientData();
    				data.setApk_server_id(sId);
    				data.setApk_url(tmp.getUrl());
    				data.setApk_version(tmp.getVersion());

    				Response.PushClientRes res = new Response.PushClientRes();
    				res.setDetails(data);
    				JSONObject jsonObject = JSONObject.fromObject(res);
    				logger.info("apk jsonObject="+jsonObject);
    				return jsonObject.toString();
    			}
    		}
    		
    	}
    	
		return Response.SUCESS;
    }
    
    private Response.PushClientRes queryPushClientApk(String uuid) {
    	Response.PushClientRes res = null;
    	String sql = "select * from t_client where uuid='" + uuid + "'";
    	List<TClient> lstData = DBUtils.query(sql, TClient.class);
    	if(lstData != null && lstData.size()>0) {
    		TClient client = lstData.get(0);
    		// 查询可更新的client apk
    		sql = "select id,url,version from t_push_update_client where " +
    				"(project_name is null or project_name='' or project_name like '%" + client.getDeviceProduct() + "%') and " +
    				"(customer_name is null or customer_name='' or customer_name like'%" + client.getDeviceCustomer() + "%') and " +
    				"(model_name is null or model_name='' or  model_name like '%" + client.getDeviceModel() + "%') and " +
    				"(version_name is null or version_name='' or version_name like '%" + client.getDeviceVersion() + "%') and " +
    				"(country_name is null or country_name='' or country_name like '%" + client.getCountryName() + "%') and " +
    				"(imei1 is null or imei1='' or imei1<='"+client.getImei()+"') and (imei2 is null or imei2='' or imei2>='"+client.getImei()+"') and " +
    				"type=2 and push_date < now() and version > " + client.getClientApkVersion() + " order by version desc";
    		//sql = "select id,url,version from t_push_update_client where type=2 and push_date < now() and version > " + client.getClientApkVersion() + " order by version desc limit 1";
    		List<TPushUpdateClient> lstTmp = DBUtils.query(sql, TPushUpdateClient.class);
    		if(lstTmp != null && lstTmp.size()>0) {
    			// 数据存在 ，insert到his表
    			TPushUpdateClient tmp = lstTmp.get(0);
    			// 查询是否存在 此his
    			sql = "select id from t_push_update_client_his where uuid='" + uuid + "' and push_id=" + tmp.getId();
    			List<TPushUpdateClientHis> lstHis = DBUtils.query(sql, TPushUpdateClientHis.class);
    			long sId = 0;
    			if(lstHis == null || lstHis.size()==0) {
    				
        			TPushUpdateClientHis his = new TPushUpdateClientHis();
        			his.setPushId(tmp.getId());
        			his.setStatus(1);
        			his.setUuid(uuid);
        			his.setCreateDate(new Timestamp(System.currentTimeMillis()));
        			sId = DBUtils.insert(his);
    			} else {
    				sId = lstHis.get(0).getId();
    			}
    			
    			if(sId > 0 ){        			
        			PushClientData data = new PushClientData();
    				data.setApk_server_id(sId);
    				data.setApk_url(tmp.getUrl());
    				data.setApk_version(tmp.getVersion());

    				res = new Response.PushClientRes();
    				res.setDetails(data);
    			}
    		}
    	}
    	
    	return res;
    }
    
    private Response.PushClientRes queryPushClientApk(JSONObject details) {
    	String uuid = details.getString("uuid");
    	Response.PushClientRes res = null;
    	String sql = "select * from t_client where uuid='" + uuid + "'";
    	List<TClient> lstData = DBUtils.query(sql, TClient.class);
    	if(lstData != null && lstData.size()>0) {
    		TClient client = lstData.get(0);
    		//String apkVersion = details.getString("c_apk_version");
    		String apkVersion = (String) details.get("c_apk_version");
    		if(apkVersion == null || "".equals(apkVersion.trim())) {
    			apkVersion = client.getClientApkVersion();
    		}
    		// 查询可更新的client apk
    		sql = "select id,url,version from t_push_update_client where " +
    				"(project_name is null or project_name='' or project_name like '%" + client.getDeviceProduct() + "%') and " +
    				"(customer_name is null or customer_name='' or customer_name like'%" + client.getDeviceCustomer() + "%') and " +
    				"(model_name is null or model_name='' or  model_name like '%" + client.getDeviceModel() + "%') and " +
    				"(version_name is null or version_name='' or version_name like '%" + client.getDeviceVersion() + "%') and " +
    				"(country_name is null or country_name='' or country_name like '%" + client.getCountryName() + "%') and " +
    				"(imei1 is null or imei1='' or imei1<='"+client.getImei()+"') and (imei2 is null or imei2='' or imei2>='"+client.getImei()+"') and " +
    				"type=2 and push_date < now() and version > " + apkVersion + " order by version desc";
    		//sql = "select id,url,version from t_push_update_client where type=2 and push_date < now() and version > " + client.getClientApkVersion() + " order by version desc limit 1";
    		List<TPushUpdateClient> lstTmp = DBUtils.query(sql, TPushUpdateClient.class);
    		if(lstTmp != null && lstTmp.size()>0) {
    			// 数据存在 ，insert到his表
    			TPushUpdateClient tmp = lstTmp.get(0);
    			// 查询是否存在 此his
    			sql = "select id from t_push_update_client_his where uuid='" + uuid + "' and push_id=" + tmp.getId();
    			List<TPushUpdateClientHis> lstHis = DBUtils.query(sql, TPushUpdateClientHis.class);
    			long sId = 0;
    			if(lstHis == null || lstHis.size()==0) {
    				
        			TPushUpdateClientHis his = new TPushUpdateClientHis();
        			his.setPushId(tmp.getId());
        			his.setStatus(1);
        			his.setUuid(uuid);
        			his.setCreateDate(new Timestamp(System.currentTimeMillis()));
        			sId = DBUtils.insert(his);
    			} else {
    				sId = lstHis.get(0).getId();
    			}
    			
    			if(sId > 0 ){        			
        			PushClientData data = new PushClientData();
    				data.setApk_server_id(sId);
    				data.setApk_url(tmp.getUrl());
    				data.setApk_version(tmp.getVersion());

    				res = new Response.PushClientRes();
    				res.setDetails(data);
    			}
    		}
    	}
    	
    	return res;
    }
    
    private String queryPushClientBinData(String uuid) {
    	String sql = "select * from t_client where uuid='" + uuid + "'";
    	List<TClient> lstData = DBUtils.query(sql, TClient.class);
    	if(lstData != null && lstData.size()>0) {
    		TClient client = lstData.get(0);
    		// 查询可更新的client bin
    		sql = "select id,url,version from t_push_update_client where " +
    				"(project_name is null or project_name='' or project_name like '%" + client.getDeviceProduct() + "%') and " +
    				"(customer_name is null or customer_name='' or customer_name like'%" + client.getDeviceCustomer() + "%') and " +
    				"(model_name is null or model_name='' or  model_name like '%" + client.getDeviceModel() + "%') and " +
    				"(version_name is null or version_name='' or version_name like '%" + client.getDeviceVersion() + "%') and " +
    				"(country_name is null or country_name='' or country_name like '%" + client.getCountryName() + "%') and " +
    				"(imei1 is null or imei1='' or imei1<='"+client.getImei()+"') and (imei2 is null or imei2='' or imei2>='"+client.getImei()+"') and " +
    				"type=1 and push_date < now() and version > " + client.getClientBinVersion() + " order by version desc";
    		List<TPushUpdateClient> lstTmp = DBUtils.query(sql, TPushUpdateClient.class);
    		if(lstTmp != null && lstTmp.size()>0) {
    			// 数据存在 ，insert到his表
    			TPushUpdateClient tmp = lstTmp.get(0);
    			
    			// 查询是否存在 此his
    			sql = "select id from t_push_update_client_his where uuid='" + uuid + "' and push_id=" + tmp.getId();
    			List<TPushUpdateClientHis> lstHis = DBUtils.query(sql, TPushUpdateClientHis.class);
    			long sId = 0;
    			if(lstHis == null || lstHis.size()==0) {
        			
        			TPushUpdateClientHis his = new TPushUpdateClientHis();
        			his.setPushId(tmp.getId());
        			his.setStatus(1);
        			his.setUuid(uuid);
        			his.setCreateDate(new Timestamp(System.currentTimeMillis()));
        			sId = DBUtils.insert(his);
    			} else {
    				sId = lstHis.get(0).getId();
    			}
    			
    			if(sId > 0 ){        			
        			PushClientData data = new PushClientData();
    				data.setBin_server_id(sId);
    				data.setBin_url(tmp.getUrl());
    				data.setBin_version(tmp.getVersion());

    				Response.PushClientRes res = new Response.PushClientRes();
    				res.setDetails(data);
    				JSONObject jsonObject = JSONObject.fromObject(res);
    				logger.info("bin jsonObject="+jsonObject);
    				return jsonObject.toString();
    			}
    		}
    		
    	}
    	
		return Response.SUCESS;
    }
    
    private Response.PushClientRes queryPushClientBin(String uuid) {
    	Response.PushClientRes res = null;
    	String sql = "select * from t_client where uuid='" + uuid + "'";
    	List<TClient> lstData = DBUtils.query(sql, TClient.class);
    	if(lstData != null && lstData.size()>0) {
    		TClient client = lstData.get(0);
    		// 查询可更新的client bin
    		sql = "select id,url,version from t_push_update_client where " +
    				"(project_name is null or project_name='' or project_name like '%" + client.getDeviceProduct() + "%') and " +
    				"(customer_name is null or customer_name='' or customer_name like'%" + client.getDeviceCustomer() + "%') and " +
    				"(model_name is null or model_name='' or  model_name like '%" + client.getDeviceModel() + "%') and " +
    				"(version_name is null or version_name='' or version_name like '%" + client.getDeviceVersion() + "%') and " +
    				"(country_name is null or country_name='' or country_name like '%" + client.getCountryName() + "%') and " +
    				"(imei1 is null or imei1='' or imei1<='"+client.getImei()+"') and (imei2 is null or imei2='' or imei2>='"+client.getImei()+"') and " +
    				"type=1 and push_date < now() and version > " + client.getClientBinVersion() + " order by version desc";
    		List<TPushUpdateClient> lstTmp = DBUtils.query(sql, TPushUpdateClient.class);
    		if(lstTmp != null && lstTmp.size()>0) {
    			// 数据存在 ，insert到his表
    			TPushUpdateClient tmp = lstTmp.get(0);
    			
    			// 查询是否存在 此his
    			sql = "select id from t_push_update_client_his where uuid='" + uuid + "' and push_id=" + tmp.getId();
    			List<TPushUpdateClientHis> lstHis = DBUtils.query(sql, TPushUpdateClientHis.class);
    			long sId = 0;
    			if(lstHis == null || lstHis.size()==0) {
        			
        			TPushUpdateClientHis his = new TPushUpdateClientHis();
        			his.setPushId(tmp.getId());
        			his.setStatus(1);
        			his.setUuid(uuid);
        			his.setCreateDate(new Timestamp(System.currentTimeMillis()));
        			sId = DBUtils.insert(his);
    			} else {
    				sId = lstHis.get(0).getId();
    			}
    			
    			if(sId > 0 ){        			
        			PushClientData data = new PushClientData();
    				data.setBin_server_id(sId);
    				data.setBin_url(tmp.getUrl());
    				data.setBin_version(tmp.getVersion());

    				res = new Response.PushClientRes();
    				res.setDetails(data);
    			}
    		}
    	}
    	
    	return res;
    }
    
    private Response.PushClientRes queryPushClientBin(JSONObject details) {
    	String uuid = details.getString("uuid");
    	Response.PushClientRes res = null;
    	String sql = "select * from t_client where uuid='" + uuid + "'";
    	List<TClient> lstData = DBUtils.query(sql, TClient.class);
    	if(lstData != null && lstData.size()>0) {
    		TClient client = lstData.get(0);
    		//String binVersion = details.getString("c_bin_version");
    		String binVersion = (String) details.get("c_bin_version");
    		if(binVersion == null || "".equals(binVersion.trim())) {
    			binVersion = client.getClientBinVersion();
    		}
    		// 查询可更新的client bin
    		sql = "select id,url,version from t_push_update_client where " +
    				"(project_name is null or project_name='' or project_name like '%" + client.getDeviceProduct() + "%') and " +
    				"(customer_name is null or customer_name='' or customer_name like'%" + client.getDeviceCustomer() + "%') and " +
    				"(model_name is null or model_name='' or  model_name like '%" + client.getDeviceModel() + "%') and " +
    				"(version_name is null or version_name='' or version_name like '%" + client.getDeviceVersion() + "%') and " +
    				"(country_name is null or country_name='' or country_name like '%" + client.getCountryName() + "%') and " +
    				"(imei1 is null or imei1='' or imei1<='"+client.getImei()+"') and (imei2 is null or imei2='' or imei2>='"+client.getImei()+"') and " +
    				"type=1 and push_date < now() and version > " + binVersion + " order by version desc";
    		List<TPushUpdateClient> lstTmp = DBUtils.query(sql, TPushUpdateClient.class);
    		if(lstTmp != null && lstTmp.size()>0) {
    			// 数据存在 ，insert到his表
    			TPushUpdateClient tmp = lstTmp.get(0);
    			
    			// 查询是否存在 此his
    			sql = "select id from t_push_update_client_his where uuid='" + uuid + "' and push_id=" + tmp.getId();
    			List<TPushUpdateClientHis> lstHis = DBUtils.query(sql, TPushUpdateClientHis.class);
    			long sId = 0;
    			if(lstHis == null || lstHis.size()==0) {
        			
        			TPushUpdateClientHis his = new TPushUpdateClientHis();
        			his.setPushId(tmp.getId());
        			his.setStatus(1);
        			his.setUuid(uuid);
        			his.setCreateDate(new Timestamp(System.currentTimeMillis()));
        			sId = DBUtils.insert(his);
    			} else {
    				sId = lstHis.get(0).getId();
    			}
    			
    			if(sId > 0 ){        			
        			PushClientData data = new PushClientData();
    				data.setBin_server_id(sId);
    				data.setBin_url(tmp.getUrl());
    				data.setBin_version(tmp.getVersion());

    				res = new Response.PushClientRes();
    				res.setDetails(data);
    			}
    		}
    	}
    	
    	return res;
    }

	private String queryPushConfigData(String uuid) {
    	String sql = "select * from t_client where uuid='" + uuid + "'";
    	List<TClient> lstClient = DBUtils.query(sql, TClient.class);
    	if(lstClient != null && lstClient.size()>0) {
    		TClient client = lstClient.get(0);

    		// 查询是否存在 最新的push config,其push_date小于当前时间即可，取最新的一条
    		sql = "select id,time_out from t_push_update_config where " +
    				"(project_name is null or project_name='' or project_name like '%" + client.getDeviceProduct() + "%') and " +
    				"(customer_name is null or customer_name='' or customer_name like'%" + client.getDeviceCustomer() + "%') and " +
    				"(model_name is null or model_name='' or  model_name like '%" + client.getDeviceModel() + "%') and " +
    				"(version_name is null or version_name='' or version_name like '%" + client.getDeviceVersion() + "%') and " +
    				"(country_name is null or country_name='' or country_name like '%" + client.getCountryName() + "%') and " +
    				"(imei1 is null or imei1='' or imei1<='"+client.getImei()+"') and (imei2 is null or imei2='' or imei2>='"+client.getImei()+"') and " +
    				"push_date<NOW() order by push_date desc limit 1";
    		// sql = "select id,time_out from t_push_update_config where push_date<NOW() order by push_date desc limit 1";
    		List<TPushUpdateConfig> lstData = DBUtils.query(sql, TPushUpdateConfig.class);
    		if(lstData != null && lstData.size() > 0) {
    			TPushUpdateConfig cData = lstData.get(0);
    			
    			// 查询his表中是否有数据
    			sql = "select * from t_push_update_config_his where uuid='" + uuid + "' and push_id=" + cData.getId();
    			List<TPushUpdateConfigHis> lstHis = DBUtils.query(sql, TPushUpdateConfigHis.class);
    			if(lstHis != null && lstHis.size() > 0) { // 存在
    				TPushUpdateConfigHis his = lstHis.get(0);
    				if(his.getStatus() == null || his.getStatus() == 0) { // 将此数据重新组装返回
    					PushConfigData data = new PushConfigData();
    					data.setServer_id(his.getId());
    					data.setTime_out(cData.getTimeOut());

    					Response.PushConfigRes res = new Response.PushConfigRes();
    					res.setDetails(data);
    					JSONObject jsonObject = JSONObject.fromObject(res);
    					logger.info("jsonObject=" + jsonObject);

    					return jsonObject.toString();
    				}
    			} else {
    				// 向history 表中插入数据
    				TPushUpdateConfigHis his = new TPushUpdateConfigHis();
    				his.setPushId(cData.getId());
    				his.setUuid(uuid);
    				his.setStatus(0);
    				his.setCreateDate(new Timestamp(System.currentTimeMillis()));
    				long sId = DBUtils.insert(his);
    				if (sId > 0) {
    					PushConfigData data = new PushConfigData();
    					data.setServer_id(sId);
    					data.setTime_out(cData.getTimeOut());

    					Response.PushConfigRes res = new Response.PushConfigRes();
    					res.setDetails(data);
    					JSONObject jsonObject = JSONObject.fromObject(res);
    					logger.info("jsonObject=" + jsonObject);

    					return jsonObject.toString();
    				}
    			}
    		}
    	}
		
		return Response.SUCESS;
	}
	
	private Response.PushConfigRes queryPushConfig(String uuid) {
		Response.PushConfigRes res = null;
    	String sql = "select * from t_client where uuid='" + uuid + "'";
    	List<TClient> lstClient = DBUtils.query(sql, TClient.class);
    	if(lstClient != null && lstClient.size()>0) {
    		TClient client = lstClient.get(0);

    		// 查询是否存在 最新的push config,其push_date小于当前时间即可，取最新的一条
    		sql = "select id,time_out from t_push_update_config where " +
    				"(project_name is null or project_name='' or project_name like '%" + client.getDeviceProduct() + "%') and " +
    				"(customer_name is null or customer_name='' or customer_name like'%" + client.getDeviceCustomer() + "%') and " +
    				"(model_name is null or model_name='' or  model_name like '%" + client.getDeviceModel() + "%') and " +
    				"(version_name is null or version_name='' or version_name like '%" + client.getDeviceVersion() + "%') and " +
    				"(country_name is null or country_name='' or country_name like '%" + client.getCountryName() + "%') and " +
    				"(imei1 is null or imei1='' or imei1<='"+client.getImei()+"') and (imei2 is null or imei2='' or imei2>='"+client.getImei()+"') and " +
    				"push_date<NOW() order by push_date desc limit 1";
    		// sql = "select id,time_out from t_push_update_config where push_date<NOW() order by push_date desc limit 1";
    		List<TPushUpdateConfig> lstData = DBUtils.query(sql, TPushUpdateConfig.class);
    		if(lstData != null && lstData.size() > 0) {
    			TPushUpdateConfig cData = lstData.get(0);
    			
    			// 查询his表中是否有数据
    			sql = "select * from t_push_update_config_his where uuid='" + uuid + "' and push_id=" + cData.getId();
    			List<TPushUpdateConfigHis> lstHis = DBUtils.query(sql, TPushUpdateConfigHis.class);
    			if(lstHis != null && lstHis.size() > 0) { // 存在
    				TPushUpdateConfigHis his = lstHis.get(0);
    				if(his.getStatus() == null || his.getStatus() == 0) { // 将此数据重新组装返回
    					PushConfigData data = new PushConfigData();
    					data.setServer_id(his.getId());
    					data.setTime_out(cData.getTimeOut());

    					res = new Response.PushConfigRes();
    					res.setDetails(data);
    				}
    			} else {
    				// 向history 表中插入数据
    				TPushUpdateConfigHis his = new TPushUpdateConfigHis();
    				his.setPushId(cData.getId());
    				his.setUuid(uuid);
    				his.setStatus(0);
    				his.setCreateDate(new Timestamp(System.currentTimeMillis()));
    				long sId = DBUtils.insert(his);
    				if (sId > 0) {
    					PushConfigData data = new PushConfigData();
    					data.setServer_id(sId);
    					data.setTime_out(cData.getTimeOut());

    					res = new Response.PushConfigRes();
    					res.setDetails(data);
    				}
    			}
    		}
    	}
		
		return res;
	}

	/**
	 * params:details(2.8)
	 * 数据库：t_push_apk_his
	 * 更新表的status和Update_comments字段
	 * 根据params中的server_id,对应表的主键id
	 * c_update_date更新时间使用传过来的时间（操作时间）
	 * @return 
	 */
	private String updateApk(JSONObject dJson) {
		String resp = Response.FAIL;
		UpdateData ud = JSONUtils.toBean(dJson, UpdateData.class);
		logger.info("updateApk: updatedata="+ud);
		if(ud!=null && !ud.hasNullProp()) {
			List<TPushApkHis> his = DBUtils.query("select * from t_push_apk_his where id=" + ud.getServer_id(), TPushApkHis.class);
			if(his != null && his.size()>0) {
				Timestamp cUpdateDate = formatClientUpdateDate(ud.getDate());
				Timestamp updateDate = new Timestamp(System.currentTimeMillis());
				String updateComments = formatUpdateComments(ud.getDate(), his.get(0).getId().toString());
				
				TPushApkHis apkHis = his.get(0);
				apkHis.setStatus(getApkStatus(ud.getStatus()));
				apkHis.setCUpdateDate(cUpdateDate);
				apkHis.setUpdateDate(updateDate);
				apkHis.setUpdateComments(updateComments);
				long rId = DBUtils.update(apkHis);
				if(rId > 0) {
					resp = Response.SUCESS;
				}
			} else {
				return Response.FAIL;
			}
		}
		return resp;
	}

	/**
	 * params:details(2.9)
	 * 数据库：t_push_link_his
	 * 更新表的status和Update_comments字段
	 * 根据params中的server_id,对应表的主键id
	 * c_update_date更新时间使用传过来的时间（操作时间）
	 * @return 
	 */
	private String updateLink(JSONObject dJson) {
		String resp = Response.FAIL;
		UpdateData ud = JSONUtils.toBean(dJson, UpdateData.class);
		logger.info("updateLink: updatedata="+ud);
		if(ud!=null && !ud.hasNullProp()) {
			List<TPushLinkHis> his = DBUtils.query("select * from t_push_link_his where id=" + ud.getServer_id(), TPushLinkHis.class);
			if(his != null && his.size()>0) {
				Timestamp cUpdateDate = formatClientUpdateDate(ud.getDate());
				Timestamp updateDate = new Timestamp(System.currentTimeMillis());
				String updateComments = formatUpdateComments(ud.getDate(), his.get(0).getId().toString());
				
				TPushLinkHis apkHis = his.get(0);
				apkHis.setStatus(getLinkStatus(ud.getStatus()));
				apkHis.setCUpdateDate(cUpdateDate);
				apkHis.setUpdateDate(updateDate);
				apkHis.setUpdateComments(updateComments);
				long rId = DBUtils.update(apkHis);
				if(rId > 0) {
					resp = Response.SUCESS;
				}
			} else {
				return Response.FAIL;
			}
		}
		return resp;
	}

	/**
	 * params:details(2.10)
	 * 数据库：t_push_text_his
	 * 更新表的status和Update_comments字段
	 * 根据params中的server_id,对应表的主键id
	 * c_update_date更新时间使用传过来的时间（操作时间）
	 * @return 
	 */
	private String updateText(JSONObject dJson) {
		String resp = Response.FAIL;
		UpdateData ud = JSONUtils.toBean(dJson, UpdateData.class);
		logger.info("updateText: updatedata="+ud);
		if(ud!=null && !ud.hasNullProp()) {
			List<TPushTextHis> his = DBUtils.query("select * from t_push_text_his where id=" + ud.getServer_id(), TPushTextHis.class);
			if(his != null && his.size()>0) {
				Timestamp cUpdateDate = formatClientUpdateDate(ud.getDate());
				Timestamp updateDate = new Timestamp(System.currentTimeMillis());
				String updateComments = formatUpdateComments(ud.getDate(), his.get(0).getId().toString());
				
				TPushTextHis apkHis = his.get(0);
				apkHis.setStatus(getTextStatus(ud.getStatus()));
				apkHis.setCUpdateDate(cUpdateDate);
				apkHis.setUpdateDate(updateDate);
				apkHis.setUpdateComments(updateComments);
				long rId = DBUtils.update(apkHis);
				if(rId > 0) {
					resp = Response.SUCESS;
				}
			} else {
				return Response.FAIL;
			}
		}
		return resp;
	}

	/**
	 * params:details(2.11)
	 * 数据库：t_push_update_client_his
	 * 更新表的status和Update_comments字段
	 * 根据params中的server_id,对应表的主键id
	 * c_update_date更新时间使用传过来的时间（操作时间）
	 * @return 
	 */
	private String updateClient(JSONObject dJson) {
		String resp = Response.FAIL;
		UpdateData ud = JSONUtils.toBean(dJson, UpdateData.class);
		logger.info("updateClient: updatedata="+ud);
		if(ud!=null && !ud.hasNullProp()) {
			List<TPushUpdateClientHis> his = DBUtils.query("select * from t_push_update_client_his where id=" + ud.getServer_id(), TPushUpdateClientHis.class);
			if(his != null && his.size()>0) {
				/** 更新版本号 start  ***/
				int status = Integer.parseInt(ud.getStatus());
				if(status == TPushUpdateClientHis.STATUS_INSTALLED) { //已安装
					String uuid = his.get(0).getUuid();
					String sql = "select * from t_client where uuid='" + uuid + "'";
			    	List<TClient> lstClient = DBUtils.query(sql, TClient.class);
			    	if(lstClient != null && lstClient.size() > 0) {
			    		TClient tClient = lstClient.get(0);
			    		Long pushId = his.get(0).getPushId();
						sql = "select type,version from t_push_update_client where id="+pushId;
						List<TPushUpdateClient> clients = DBUtils.query(sql, TPushUpdateClient.class);
						if(clients != null && clients.size()>0) {
							TPushUpdateClient client = clients.get(0);
							int version = client.getVersion();
							String updateComments = null;
							if(client.getType().intValue() == TPushUpdateClient.TYPE_APK) {
								tClient.setClientApkVersion(String.valueOf(version));
								updateComments = "change apk version to "+version;
							} else if(client.getType().intValue() == TPushUpdateClient.TYPE_BIN) {
								tClient.setClientBinVersion(String.valueOf(version));
								updateComments = "change bin version to "+version;
							}
							tClient.setUpdateDate(new Timestamp(System.currentTimeMillis()));
							tClient.setUpdateComments(updateComments);
							DBUtils.update(tClient);
						}
			    	}
				}
		    	/** 更新版本号 end ***/
				
				Timestamp cUpdateDate = formatClientUpdateDate(ud.getDate());
				Timestamp updateDate = new Timestamp(System.currentTimeMillis());
				String updateComments = formatUpdateComments(ud.getDate(), his.get(0).getId().toString());
				
				TPushUpdateClientHis apkHis = new TPushUpdateClientHis();
				apkHis.setId(ud.getServer_id());
				//apkHis.setStatus(Integer.parseInt(ud.getStatus()));
				apkHis.setStatus(getApkStatus(ud.getStatus()));
				apkHis.setCUpdateDate(cUpdateDate);
				apkHis.setUpdateDate(updateDate);
				apkHis.setUpdateComments(updateComments);
				long rId = DBUtils.update(apkHis);
				if(rId > 0) {
					resp = Response.SUCESS;
				}
			} else {
				return Response.FAIL;
			}
		}
		return resp;
	}

	/**
	 * params:details(2.12)
	 * 数据库：t_push_update_config_his
	 * 更新表的status和Update_comments字段
	 * 根据params中的server_id,对应表的主键id
	 * c_update_date更新时间使用传过来的时间（操作时间）
	 * @return 
	 */
	private String updateConfig(JSONObject dJson) {
		String resp = Response.FAIL;
		UpdateData ud = JSONUtils.toBean(dJson, UpdateData.class);
		logger.info("updateConfig: updatedata="+ud);
		if(ud!=null && !ud.hasNullProp()) {
			List<TPushUpdateConfigHis> his = DBUtils.query("select * from t_push_update_config_his where id=" + ud.getServer_id(), TPushUpdateConfigHis.class);
			if(his != null && his.size()>0) {
				Timestamp cUpdateDate = formatClientUpdateDate(ud.getDate());
				Timestamp updateDate = new Timestamp(System.currentTimeMillis());
				String updateComments = formatUpdateComments(ud.getDate(), his.get(0).getId().toString());
				
				TPushUpdateConfigHis apkHis = his.get(0);
				apkHis.setStatus(getConfigStatus(ud.getStatus()));
				apkHis.setCUpdateDate(cUpdateDate);
				apkHis.setUpdateDate(updateDate);
				apkHis.setUpdateComments(updateComments);
				long rId = DBUtils.update(apkHis);
				if(rId > 0) {
					resp = Response.SUCESS;
				}
			} else {
				return Response.FAIL;
			}
		}
		return resp;
	}

    /**
     * params:details(2.12)
     * 数据库：t_push_update_config_his
     * 更新表的status和Update_comments字段
     * 根据params中的server_id,对应表的主键id
     * c_update_date更新时间使用传过来的时间（操作时间）
     * @return
     */
    private String updateOrder(JSONObject dJson) {
        String resp = Response.FAIL;
        UpdateData ud = JSONUtils.toBean(dJson, UpdateData.class);
        logger.info("updateOrder: updatedata="+ud);
        if(ud!=null && !ud.hasNullProp()) {
			List<TPushCommandsHis> his = DBUtils.query("select * from t_push_commands_his where id=" + ud.getServer_id(), TPushCommandsHis.class);
			if(his != null && his.size()>0) {
				Timestamp cUpdateDate = formatClientUpdateDate(ud.getDate());
				Timestamp updateDate = new Timestamp(System.currentTimeMillis());
				String updateComments = formatUpdateComments(ud.getDate(), his.get(0).getId().toString());
				
				TPushCommandsHis apkHis = his.get(0);
				apkHis.setStatus(getCommandsStatus(ud.getStatus()));
				apkHis.setCUpdateDate(cUpdateDate);
				apkHis.setUpdateDate(updateDate);
				apkHis.setUpdateComments(updateComments);
				long rId = DBUtils.update(apkHis);
				if(rId > 0) {
					resp = Response.SUCESS;
				}
			} else {
				return Response.FAIL;
			}
        }
        return resp;
    }

	private String formatUpdateComments(String dateStr, String statusDesc) {
		StringBuilder comments = new StringBuilder();
		comments.append(dateStr);
		comments.append(" 状态->");
		comments.append(statusDesc);
		return comments.toString();
	}

	private Timestamp formatClientUpdateDate(String dateStr) {
		Timestamp updateDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			updateDate = new Timestamp(sdf.parse(dateStr).getTime());
		} catch(Exception e) {
			logger.error(e.toString(), e);
		}
		return updateDate;
	}

	/**
	 * 获取国家码
	 * @param countryCode
	 * @return
	 */
	private static String getConuntryName(String countryCode) {
		return mCountry.get(countryCode);
	}

	static {

		// init 国家语言码表
		mCountry.put("af_ZA", "公用荷兰语 - 南非");

		mCountry.put("sq_AL", "阿尔巴尼亚语 -阿尔巴尼亚");

		mCountry.put("ar_DZ", "阿拉伯语 -阿尔及利亚");
		mCountry.put("ar_BH", "阿拉伯语 -巴林");
		mCountry.put("ar_EG", "阿拉伯语 -埃及");
		mCountry.put("ar_IQ", "阿拉伯语 -伊拉克");
		mCountry.put("ar_JO", "阿拉伯语 -约旦");
		mCountry.put("ar_KW", "阿拉伯语 -科威特");
		mCountry.put("ar_LB", "阿拉伯语 -黎巴嫩");
		mCountry.put("ar_LY", "阿拉伯语 -利比亚");
		mCountry.put("ar_MA", "阿拉伯语 -摩洛哥");
		mCountry.put("ar_OM", "阿拉伯语 -阿曼");
		mCountry.put("ar_QA", "阿拉伯语 -卡塔尔");
		mCountry.put("ar_SA", "阿拉伯语 - 沙特阿拉伯");
		mCountry.put("ar_SY", "阿拉伯语 -叙利亚共和国");
		mCountry.put("ar_TN", "阿拉伯语 -北非的共和国");
		mCountry.put("ar_AE", "阿拉伯语 - 阿拉伯联合酋长国");
		mCountry.put("ar_YE", "阿拉伯语 -也门");
		mCountry.put("ar_IL", "阿拉伯语 -以色列");

		mCountry.put("hy_AM", "亚美尼亚语 -亚美尼亚");

		mCountry.put("az_AZ_Cyrl", "Azeri语-(西里尔字母) 阿塞拜疆");
		mCountry.put("az_AZ_Latn", "Azeri语(拉丁文)- 阿塞拜疆");

		mCountry.put("eu_ES", "巴斯克语 -巴斯克");

		mCountry.put("be_BY", "Belarusian-白俄罗斯");

		mCountry.put("bg_BG", "保加利亚语 -保加利亚");

		mCountry.put("ca_ES", "嘉泰罗尼亚语 -嘉泰罗尼亚");
		mCountry.put("zh_HK", "汉语 - 香港");
		mCountry.put("zh_MO", "汉语 - 澳门");
		mCountry.put("zh_CN", "汉语 -中国");
		mCountry.put("zh_CHS", "汉语 (单一化)");
		mCountry.put("zh_SG", "汉语 -新加坡");
		mCountry.put("zh_TW", "汉语 -台湾");
		mCountry.put("zh_CHT", "汉语 (传统)");

		mCountry.put("hr_HR", "克罗埃西亚语 -克罗埃西亚");

		mCountry.put("cs_CZ", "捷克语 - 捷克");

		mCountry.put("da_DK", "丹麦文语 -丹麦");

		mCountry.put("div_MV", "Dhivehi-马尔代夫");

		mCountry.put("nl_BE", "荷兰语 -比利时");
		mCountry.put("nl_NL", "荷兰语 - 荷兰");

		mCountry.put("en_AU", "英语 -澳洲");
		mCountry.put("en_BZ", "英语 -伯利兹");
		mCountry.put("en_CA", "英语 -加拿大");
		mCountry.put("en_CB", "英语 -加勒比海");
		mCountry.put("en_IE", "英语 -爱尔兰");
		mCountry.put("en_IN", "英语 -印度");
		mCountry.put("en_JM", "英语 -牙买加");
		mCountry.put("en_NZ", "英语 -新西兰");
		mCountry.put("en_PH", "英语 -菲律宾共和国");
		mCountry.put("en_ZA", "英语 -南非");
		mCountry.put("en_TT", "英语 -千里达托贝哥共和国");
		mCountry.put("en_GB", "英语 -英国");
		mCountry.put("en_US", "英语 -美国");
		mCountry.put("en_ZW", "英语 -津巴布韦");
		mCountry.put("en_SG", "英语 -新加波");

		mCountry.put("et_EE", "爱沙尼亚语 -爱沙尼亚");

		mCountry.put("fo_FO", "Faroese- 法罗群岛");

		mCountry.put("fa_IR", "波斯语 -伊朗王国");

		mCountry.put("fi_FI", "芬兰语 -芬兰");

		mCountry.put("fr_BE", "法语 -比利时");
		mCountry.put("fr_CA", "法语 -加拿大");
		mCountry.put("fr_FR", "法语 -法国");
		mCountry.put("fr_LU", "法语 -卢森堡");
		mCountry.put("fr_MC", "法语 -摩纳哥");
		mCountry.put("fr_CH", "法语 -瑞士");

		mCountry.put("gl_ES", "加利西亚语 -加利西亚");

		mCountry.put("ka_GE", "格鲁吉亚州语 -格鲁吉亚州");

		mCountry.put("de_AT", "德语 -奥地利");
		mCountry.put("de_DE", "德语 -德国");
		mCountry.put("de_LI", "德语 -列支敦士登");
		mCountry.put("de_LU", "德语-卢森堡");
		mCountry.put("de_CH", "德语 -瑞士");

		mCountry.put("el_GR", "希腊语 -希腊");

		mCountry.put("gu_IN", "Gujarati-印度");

		mCountry.put("he_IL", "希伯来语 -以色列");

		mCountry.put("hi_IN", "北印度语 -印度");

		mCountry.put("hu_HU", "匈牙利语 -匈牙利");

		mCountry.put("is_IS", "冰岛语 -冰岛");

		mCountry.put("id_ID", "印尼语 -印尼");

		mCountry.put("it_IT", "意大利语 -意大利");
		mCountry.put("it_CH", "意大利语 -瑞士");

		mCountry.put("ja_JP", "日语 -日本");

		mCountry.put("kn_IN", "卡纳达语 -印度");

		mCountry.put("kk_KZ", "Kazakh-哈萨克");

		mCountry.put("kok_IN", "Konkani-印度");

		mCountry.put("ko_KR", "韩语 -韩国");

		mCountry.put("ky_KZ", "Kyrgyz-哈萨克");

		mCountry.put("lv_LV", "拉脱维亚语 -拉脱维亚");

		mCountry.put("lt_LT", "立陶宛语 -立陶宛");

		mCountry.put("mk_MK", "马其顿语 -FYROM");

		mCountry.put("ms_BN", "马来语 -汶莱");
		mCountry.put("ms_MY", "马来语 -马来西亚");

		mCountry.put("mr_IN", "马拉地语 -印度");

		mCountry.put("mn_MN", "蒙古语 -蒙古");

		mCountry.put("nb_NO", "挪威 (Bokm?l) - 挪威");
		mCountry.put("nn_NO", "挪威 (Nynorsk)- 挪威");

		mCountry.put("pl_PL", "波兰语 -波兰");

		mCountry.put("pt_BR", "葡萄牙语 -巴西");
		mCountry.put("pt_PT", "葡萄牙语 -葡萄牙");

		mCountry.put("pa_IN", "Punjab 语 -印度");

		mCountry.put("ro_RO", "罗马尼亚语 -罗马尼亚");

		mCountry.put("ru_RU", "俄语 -俄国");

		mCountry.put("sa_IN", "梵文语 -印度");
		mCountry.put("sr_SP_Cyrl", "塞尔维亚语 -(西里尔字母的) 塞尔维亚共和国");
		mCountry.put("sr_SP_Latn", "塞尔维亚语 (拉丁文)- 塞尔维亚共和国");

		mCountry.put("sk_SK", "斯洛伐克语 -斯洛伐克");

		mCountry.put("sl_SI", "斯洛文尼亚语 -斯洛文尼亚");

		mCountry.put("es_AR", "西班牙语 -阿根廷");
		mCountry.put("es_BO", "西班牙语 -玻利维亚");
		mCountry.put("es_CL", "西班牙语 -智利");
		mCountry.put("es_CO", "西班牙语 -哥伦比亚");
		mCountry.put("es_CR", "西班牙语 - 哥斯达黎加");
		mCountry.put("es_DO", "西班牙语 - 多米尼加共和国");
		mCountry.put("es_EC", "西班牙语 -厄瓜多尔");
		mCountry.put("es_SV", "西班牙语 - 萨尔瓦多");
		mCountry.put("es_GT", "西班牙语 -危地马拉");
		mCountry.put("es_HN", "西班牙语 -洪都拉斯");
		mCountry.put("es_MX", "西班牙语 -墨西哥");
		mCountry.put("es_NI", "西班牙语 -尼加拉瓜");
		mCountry.put("es_PA", "西班牙语 -巴拿马");
		mCountry.put("es_PY", "西班牙语 -巴拉圭");
		mCountry.put("es_PE", "西班牙语 -秘鲁");
		mCountry.put("es_PR", "西班牙语 - 波多黎各");
		mCountry.put("es_ES", "西班牙语 -西班牙");
		mCountry.put("es_US", "西班牙语 -美国");
		mCountry.put("es_UY", "西班牙语 -乌拉圭");
		mCountry.put("es_VE", "西班牙语 -委内瑞拉");

		mCountry.put("sw_KE", "Swahili-肯尼亚");

		mCountry.put("sv_FI", "瑞典语 -芬兰");
		mCountry.put("sv_SE", "瑞典语 -瑞典");

		mCountry.put("syr_SY", "Syriac-叙利亚共和国");

		mCountry.put("ta_IN", "坦米尔语 -印度");

		mCountry.put("tt_RU", "Tatar-俄国");

		mCountry.put("te_IN", "Telugu-印度");

		mCountry.put("th_TH", "泰语 -泰国");

		mCountry.put("tr_TR", "土耳其语 -土耳其");

		mCountry.put("uk_UA", "乌克兰语 -乌克兰");

		mCountry.put("ur_PK", "Urdu-巴基斯坦");

		mCountry.put("uz_UZ_Cyrl", "Uzbek-(西里尔字母的) 乌兹别克斯坦");
		mCountry.put("uz_UZ_Latn", "Uzbek(拉丁文)- 乌兹别克斯坦");

		mCountry.put("vi_VN", "越南语 -越南");
		
		mCountry.put("bn_IN", "孟加拉语 -印度");
		mCountry.put("iw_IL", "希伯来语 -以色列");
		mCountry.put("in_ID", "印度尼西亚语 -印度尼西亚");
		mCountry.put("sr_RS", "塞尔维亚语 -塞尔维亚");
		mCountry.put("tl_PH", "菲律宾语 -菲律宾");
		mCountry.put("rm_CH", "罗曼什语 -瑞士");
		mCountry.put("my_MM", "缅甸语 -缅甸");
		mCountry.put("my_ZG", "缅甸语 -缅甸");
		mCountry.put("km_KH", "柬埔寨语 -柬埔寨");
		mCountry.put("sw_TZ", "斯瓦希里语 -坦桑尼亚");
		mCountry.put("zu_ZA", "祖鲁语 -南非");
		mCountry.put("lo_LA", "老挝语 -老挝");
		mCountry.put("ne_NP", "尼泊尔语 -尼泊尔");
		mCountry.put("si_LK", "僧加罗语 -斯里兰卡");
	}
	
	public static int getApkStatus(String statusStr) {
		int status = -1; // 未知状态  0: 发送中
		if ("00".equals(statusStr)) {
			status = 1; // 接收成功
		} else if ("11".equals(statusStr)) {
			status = 2; // 用户下载
		} else if ("12".equals(statusStr)) {
			status = 3; // 用户安装
		} else if ("13".equals(statusStr)) {
			status = 4; // 用户删除
		} else {
			try {
				status = Integer.parseInt(statusStr);
			} catch (NumberFormatException e) {
				logger.error("getApkStatus error : statusStr="+statusStr);
			}
		}
		return status;
	}
	
	public static int getLinkStatus(String statusStr) {
		int status = -1; // 未知状态  0: 发送中
		if ("00".equals(statusStr)) {
			status = 1; // 接收成功
		} else if ("21".equals(statusStr)) {
			status = 2; // 用户点击
		} else {
			try {
				status = Integer.parseInt(statusStr);
			} catch (NumberFormatException e) {
				logger.error("getLinkStatus error : statusStr="+statusStr);
			}
		}
		return status;
	}
	
	public static int getTextStatus(String statusStr) {
		int status = -1; // 未知状态 0: 发送中
		if ("00".equals(statusStr)) {
			status = 1; // 接收成功
		} else if ("31".equals(statusStr)) {
			status = 2; // 用户点击
		} else {
			try {
				status = Integer.parseInt(statusStr);
			} catch (NumberFormatException e) {
				logger.error("getTextStatus error : statusStr="+statusStr);
			}
		}
		return status;
	}
	
	public static int getConfigStatus(String statusStr) {
		int status = -1; // 未知状态 0: 发送中
		if ("00".equals(statusStr)) {
			status = 1; // 接收成功
		} else if ("51".equals(statusStr)) {
			status = 2; // 更新成功
		} else {
			try {
				status = Integer.parseInt(statusStr);
			} catch (NumberFormatException e) {
				logger.error("getConfigStatus error : statusStr="+statusStr);
			}
		}
		return status;
	}
	
	public static int getCommandsStatus(String statusStr) {
		int status = -1; // 未知状态 0: 发送中
		if ("00".equals(statusStr)) {
			status = 1; // 接收成功
		} else if ("61".equals(statusStr)) {
			status = 2; // 执行成功
		} else {
			try {
				status = Integer.parseInt(statusStr);
			} catch (NumberFormatException e) {
				logger.error("getCommandsStatus error : statusStr="+statusStr);
			}
		}
		return status;
	}
	
	private String arrayToJson(Object[] objs) {
//		JSONArray jsonArray = JSONArray.fromObject(objs);
//		return jsonArray.toString();
		StringBuilder sb = new StringBuilder();
		if(objs == null || objs.length == 0) {
			sb.append("[]");
		} else {
			sb.append("[");
			for(int i=0;i<objs.length;i++) {
				JSONObject obj = JSONObject.fromObject(objs[i]);
				sb.append(obj.toString());
				if(i == objs.length - 1) {
					sb.append("]");
				} else {
					sb.append("|");
				}
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
//		String[] ss = new String[] {"zhang","zi","xiao"};
//		String t = "[{name:'zhang|',name2:'zi',name3:'xiao'},{name:'li',name2:'hua'}]";
//		JSONArray jsonArray = JSONArray.fromObject(t);
//		StringBuilder sb = new StringBuilder();
//		for(int i=0;i<jsonArray.size();i++) {
//			JSONObject jobj = (JSONObject) jsonArray.get(i);
//			System.out.println(jobj.toString());
//		}
//		String[] ts = new String[0];
//		JSONArray jsonArray = JSONArray.fromObject(ts);
//		System.out.println(jsonArray.size());
//		List<Object> s = new ArrayList<Object>();
//		s.add("zhang");
//		s.add("zi");
//		s.add("xiao");
//		Object[] sss = s.toArray();
//		JSONArray arry = JSONArray.fromObject(sss);
//		if(JSONUtils.getJSONType(arry.toString()) == JSON_TYPE.JSON_TYPE_ARRAY) {
//			JSONArray array2 = JSONArray.fromObject(arry.toArray());
//			System.out.println("array2="+array2.toString());
//		}
//		System.out.println(JSONUtils.getJSONType(arry.toString()));
	}
}
