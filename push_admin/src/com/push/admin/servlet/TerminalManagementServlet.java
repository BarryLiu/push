package com.push.admin.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.push.admin.dbbean.TClient;
import com.push.admin.dbbean.TCountry;
import com.push.admin.dbbean.TProject;
import com.push.admin.util.DBUtils;

public class TerminalManagementServlet extends BasePushServlet {
	public static final String PARAM_PROJECT = "project";
	public static final String PARAM_CUSTOMER = "customer";
	public static final String PARAM_MODEL = "model";
	public static final String PARAM_VERSION = "version";
	public static final String PARAM_COUNTRYNAME = "countryName";

	private static final long serialVersionUID = 1L;

	@Override
	protected void query(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try{
			logger.info("TerminalManagementServlet query start....");
			if (req.getParameter("uuid")==null||req.getParameter("uuid").equals("")) {
				req.setCharacterEncoding("utf-8");
		        String order = getUTF8(req,"order");
		        String da = getUTF8(req,"da");
		        
				String project = getUTF8(req.getParameter(PARAM_PROJECT));
				String customer = getUTF8(req.getParameter(PARAM_CUSTOMER));
				String model = getUTF8(req.getParameter(PARAM_MODEL));
				String version = getUTF8(req.getParameter(PARAM_VERSION));
				String countryName = req.getParameter(PARAM_COUNTRYNAME);
				String phoneNumber = req.getParameter("phoneNumber");
				String uuids = req.getParameter("uuids");
				String imei = req.getParameter("imei");
				String selection = null;
				List<String> args = new ArrayList<String>();
				StringBuilder sb = new StringBuilder("1=1");
				if (!isEmpty(project)) {
					project = getUTF8(project);
					sb.append(" and device_product like '%" + project + "%'");
				}
				if (!isEmpty(customer)) {
					customer = getUTF8(customer);
					sb.append(" and device_customer like '%" + customer + "%'");
				}
				if (!isEmpty(model)) {
					model = getUTF8(model);
					sb.append(" and device_model like '%" + model + "%'");
				}
				if (!isEmpty(version)) {
					version = getUTF8(version);
					sb.append(" and device_version like '%" + version + "%'");
				}
				if (!isEmpty(countryName)) {
					countryName = getUTF8(countryName);
					sb.append(" and country_name like '%" + countryName + "%'");
				}
				if (!isEmpty(phoneNumber)) {
					phoneNumber = getUTF8(phoneNumber);
					sb.append(" and (phone_number1 like '%" + phoneNumber + "%' or phone_number2 like '%"+phoneNumber+"%')");
				}
				if (!isEmpty(imei)) {
					imei = getUTF8(imei);
					sb.append(" and imei like '%" + imei + "%'");
				}
				if (!isEmpty(uuids)) {
					uuids = getUTF8(uuids);
					sb.append(" and uuid like '%" + uuids + "%'");
				}
				selection = sb.toString();
				// order sql
				String orderSql = "";
				if(!isEmpty(order)) {
					if(da.equals("2")){
						orderSql = order + " desc";
		            }else {
		            	orderSql = order + " asc";
		            }
				}

				List<TClient> beanList = DBUtils.query(selection, args, orderSql,
						TClient.class, pagerInfo);

//				Map<String, String> countryMap = new HashMap<String, String>();
				
				req.setAttribute("uuids", uuids);
				req.setAttribute("phoneNumber", phoneNumber);
				req.setAttribute("imei", imei);
				req.setAttribute("project", project);
				req.setAttribute("customer", customer);
				req.setAttribute("model", model);
				req.setAttribute("version", version);
				req.setAttribute("countryName", countryName);
//				req.setAttribute("modelMap", modelMap);
//				req.setAttribute("versionMap", versionMap);
//				req.setAttribute("countryMap", countryMap);
				req.setAttribute("pagerInfo", pagerInfo);
				req.setAttribute("beanList", beanList);
				
		        req.setAttribute("order", order);
		        req.setAttribute("da", da);
				req.getRequestDispatcher("terminallist.jsp").forward(req, resp);
			} else {
				String sql = "select * from t_client where uuid='" + req.getParameter("uuid")+"'";
				List<TClient> beanList = DBUtils.query(sql, TClient.class);
				if (beanList.size() > 0) {
					req.setAttribute("client", beanList.get(0));
				}
				req.getRequestDispatcher("terminaldetail.jsp").forward(req, resp);
			}
		}catch(Exception e) {
			e.printStackTrace();
			req.getRequestDispatcher("terminallist.jsp").forward(req, resp);
		}finally{
			logger.info("TerminalManagementServlet query end....");
		}
	}

}
