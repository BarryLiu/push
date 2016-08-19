package com.push.admin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.push.admin.dbbean.TCountry;
import com.push.admin.dbbean.TProject;
import com.push.admin.dbbean.TPushApk;
import com.push.admin.dbbean.VPushApk;
import com.push.admin.dwr.Dwr;
import com.push.admin.dwr.Result;
import com.push.admin.timer.TimerListner;
import com.push.admin.util.DBUtils;
import com.push.admin.util.InsertPushParams;

public class AppPushServlet extends BasePushServlet {
	public static final String PARAM_NAME = "title";
	private static final long serialVersionUID = 1L;

	@Override
	protected void query(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*String __sql = "select name,area from t_country group by name order by name";
		List<TCountry> __beanList = DBUtils.query(__sql, TCountry.class);
		Map<String, String> countryMap = new HashMap<String, String>();
		for (TCountry country : __beanList) {
			countryMap.put(country.getName(), "");
		}
		req.setAttribute("countryMap", countryMap);*/
		req.setAttribute("countryMap", DBUtils.getCountryMap());
		
		// 开始查询
		StringBuffer sql = new StringBuffer("select DISTINCT t_push_apk.*,t_apks.channel,t_apks.description,t_apks.icon,t_apks.name,t_apks.package_name,t_apks.size,t_apks.status,t_apks.type,t_apks.url,t_apks.version_code,t_apks.version_name as apk_version_name,");
		sql.append("(case t_push_apk.install_type when 1 then _utf8'静默安装' when 2 then _utf8'许可型安装' else _utf8'未知' end) AS install_type_name,");
		sql.append("(select count(t_push_apk_his.id) from t_push_apk_his where ((t_push_apk.id = t_push_apk_his.push_id) and (t_push_apk_his.status = 0))) AS count_sending,");
		sql.append("(select count(t_push_apk_his.id) from t_push_apk_his where ((t_push_apk.id = t_push_apk_his.push_id) and (t_push_apk_his.status = 1))) AS `count_sended`,");
		sql.append("(select count(t_push_apk_his.id) from t_push_apk_his where ((t_push_apk.id = t_push_apk_his.push_id) and (t_push_apk_his.status = 2))) AS count_downloaded,");
		sql.append("(select count(t_push_apk_his.id) from t_push_apk_his where ((t_push_apk.id = t_push_apk_his.push_id) and (t_push_apk_his.status = 3))) AS count_installed,");
		sql.append("(select count(t_push_apk_his.id) from t_push_apk_his where ((t_push_apk.id = t_push_apk_his.push_id) and (t_push_apk_his.status = 4))) AS count_deleted ");
		sql.append("from t_push_apk LEFT JOIN t_push_params ON (t_push_apk.id = t_push_params.server_id and t_push_params.type='app') ");
		sql.append("LEFT JOIN t_apks ON t_apks.id = t_push_apk.apks_id ");
		sql.append("where 1=1 ");
		
		// 组织查询条件
		List<String> args = new ArrayList<String>();
		String title = req.getParameter(PARAM_NAME); // title
		if (!isEmpty(title)) {
			title = getUTF8(title);
			sql.append(" and t_push_apk.title like '%" + title + "%'");
//			args.add(title);
		}
		String countryName = req.getParameter("countryName"); // country name
		if (!isEmpty(countryName)) {
			countryName = getUTF8(countryName);
			sql.append(" and t_push_params.name ='" + InsertPushParams.TYPE_NAME_COUNTRY + "' and t_push_params.values_item = ?");
			args.add(countryName);
		}
		String installType = req.getParameter("installType"); // install_type
		if (!isEmpty(installType)) {
			installType = getUTF8(installType);
			sql.append(" and t_push_apk.install_type = ?");
			args.add(installType);
		}
		sql.append("order by t_push_apk.create_date desc");
		List<VPushApk> beanList = DBUtils.query(sql.toString(), args.toArray(), VPushApk.class);
		
		Date now = new Date();
		for (VPushApk item : beanList) {
			if (item.getPushDate() != null && item.getPushDate().before(now)) {
				item.setCanEdit(false);
			}
		}
		req.setAttribute("countryName", countryName);
		req.setAttribute("installType", installType);
		req.setAttribute("title", title);
		req.setAttribute("pagerInfo", pagerInfo);
		req.setAttribute("beanList", beanList);
		req.getRequestDispatcher("pushapklist.jsp").forward(req, resp);
	}

	@Override
	protected void update(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		TPushApk pushApk = new TPushApk();
		pushApk.setId(id);
		pushApk.setTitle(getUTF8(req.getParameter("title")));
		if (!req.getParameter("apksId").isEmpty()) {
			pushApk.setApksId(Long.parseLong(req.getParameter("apksId")));
		}
		// add by kui.li start
		pushApk.setImei1(getUTF8(req.getParameter("imei1")));
		pushApk.setImei2(getUTF8(req.getParameter("imei2")));
		pushApk.setProjectName(getUTF8(req.getParameter("projectName")));
		pushApk.setCustomerName(getUTF8(req.getParameter("customerName")));
		// add by kui.li end
		pushApk.setComments(getUTF8(req.getParameter("comments")));
		pushApk.setModelName(getUTF8(req.getParameter("model")));
		pushApk.setVersionName(getUTF8(req.getParameter("version")));
		pushApk.setCountryName(getUTF8(req.getParameter("countryName")));
		if (!req.getParameter("installType").isEmpty()) {
			pushApk.setInstallType(Integer.parseInt(req
					.getParameter("installType")));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			pushApk.setPushDate(new Timestamp(sdf.parse(
					req.getParameter("pushDate")).getTime()));
		} catch (ParseException pe) {
		}
		pushApk.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/xml");
		resp.setHeader("Cache-Control", "no-cache");
		out.println(new Dwr().updateSqlBean(pushApk));

		// 更新push params数据
		InsertPushParams ipp = new InsertPushParams(InsertPushParams.TYPE_APK,
				id, pushApk.getCountryName(), pushApk.getProjectName(),
				pushApk.getCustomerName(), pushApk.getModelName(),
				pushApk.getVersionName());
		Thread thread = new Thread(ipp);
		thread.start();

		for (int i = 0; i < TimerListner.getInstance().getApks().size(); i++) {
			if (TimerListner.getInstance().getApks().get(i).getId()
					.equals(pushApk.getId())) {
				TimerListner.getInstance().getApks().set(i, pushApk);
				TimerListner.getInstance().openListner();
			}
		}
		out.close();
	}

	@Override
	protected void delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] ids = req.getParameter("ids").split(",");
		if (ids.length > 0) {
			List<String> sqls = new ArrayList<String>();
			List<TPushApk> apks = new ArrayList<TPushApk>();
			for (int i = 0; i < ids.length; i++) {
				for (TPushApk apk : TimerListner.getInstance().getApks()) {
					if (apk.getId().toString().equals(ids[i])) {
						apks.add(apk);
					}
				}
				sqls.add("delete from t_push_apk where id=" + ids[i]);
				sqls.add("delete from t_push_apk_his where push_id=" + ids[i]);
				// 删除t_push_params中数据
				sqls.add("delete from t_push_params where server_id=" + ids[i]
						+ " and type='" + InsertPushParams.TYPE_APK + "'");
			}
			for (TPushApk apk : apks) {
				TimerListner.getInstance().getApks().remove(apk);
			}
			boolean res = DBUtils.execute(sqls);
			if (res) {
				PrintWriter out = resp.getWriter();
				resp.setContentType("text/xml");
				resp.setHeader("Cache-Control", "no-cache");
				out.println(Result.toJson("success", " Delete successfully! "));
				out.close();
			} else {
				PrintWriter out = resp.getWriter();
				resp.setContentType("text/xml");
				resp.setHeader("Cache-Control", "no-cache");
				out.println(Result.toJson("failure", " Delete failed! "));
				out.close();
			}
		}
	}

	@Override
	protected void add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		TPushApk pushApk = new TPushApk();
		pushApk.setTitle(getUTF8(req.getParameter("title")));
		if (!req.getParameter("apksId").isEmpty()) {
			pushApk.setApksId(Long.parseLong(req.getParameter("apksId")));
		}
		pushApk.setComments(getUTF8(req.getParameter("comments")));
		pushApk.setModelName(getUTF8(req.getParameter("model")));
		pushApk.setVersionName(getUTF8(req.getParameter("version")));
		pushApk.setCountryName(getUTF8(req.getParameter("countryName")));
		// add by kui.li start
		pushApk.setImei1(getUTF8(req.getParameter("imei1")));
		pushApk.setImei2(getUTF8(req.getParameter("imei2")));
		pushApk.setProjectName(getUTF8(req.getParameter("projectName")));
		pushApk.setCustomerName(getUTF8(req.getParameter("customerName")));
		// add by kui.li end
		if (!req.getParameter("installType").isEmpty()) {
			pushApk.setInstallType(Integer.parseInt(req
					.getParameter("installType")));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			pushApk.setPushDate(new Timestamp(sdf.parse(
					req.getParameter("pushDate")).getTime()));
		} catch (ParseException pe) {
		}
		pushApk.setUpdateComments("");
		pushApk.setCreateDate(new Timestamp(System.currentTimeMillis()));
		pushApk.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/xml");
		resp.setHeader("Cache-Control", "no-cache");
		long id = DBUtils.insert(pushApk);
		if (id > 0) {
			out.println(Result.toJson("success", "Add successfully!", id));
			pushApk.setId(id);
			TimerListner.getInstance().getApks().add(pushApk);
			TimerListner.getInstance().openListner();

			// 保存push params数据
			InsertPushParams ipp = new InsertPushParams(
					InsertPushParams.TYPE_APK, id, pushApk.getCountryName(),
					pushApk.getProjectName(), pushApk.getCustomerName(),
					pushApk.getModelName(), pushApk.getVersionName());
			Thread thread = new Thread(ipp);
			thread.start();
		} else {
			out.println(Result.toJson("failure", "Add failed!", id));
		}
		out.close();
	}

	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// projectMap
		/*
		String sql = "select product from t_project where product is not null and product !='' group by product order by product";
		List<TProject> lstProject = DBUtils.query(sql, TProject.class);
		Map<String, String> projectMap = new LinkedHashMap<String, String>();
		for (TProject project : lstProject) {
			projectMap.put(project.getProduct(), "");
		}
		req.setAttribute("projectMap", projectMap);*/
		req.setAttribute("projectMap", DBUtils.getProjectMap());

		// customerMap
		/*sql = "select customer from t_project where customer is not null and customer !='' group by customer order by customer";
		List<TProject> lstCustomer = DBUtils.query(sql, TProject.class);
		Map<String, String> customerMap = new LinkedHashMap<String, String>();
		for (TProject project : lstCustomer) {
			customerMap.put(project.getCustomer(), "");
		}
		req.setAttribute("customerMap", customerMap);*/
		req.setAttribute("customerMap", DBUtils.getCustomerMap());

		// modelMap
		/*sql = "select model,version from t_project group by model,version order by model";
		List<TProject> _beanList = DBUtils.query(sql, TProject.class);
		Map<String, Map<String, String>> modelMap = new LinkedHashMap<String, Map<String, String>>();
		for (TProject project : _beanList) {
			if (modelMap.get(project.getModel()) == null) {
				modelMap.put(project.getModel(),
						new LinkedHashMap<String, String>());
			}
			modelMap.get(project.getModel()).put(project.getVersion(),
					project.getVersion());
		}
		req.setAttribute("modelMap", modelMap);*/
		req.setAttribute("modelMap", DBUtils.getModelMap());

		// countryMap
		/*sql = "select name from t_country where name is not null and name !='' group by name order by name";
		List<TCountry> beanList = DBUtils.query(sql, TCountry.class);
		Map<String, String> countryMap = new LinkedHashMap<String, String>();
		for (TCountry country : beanList) {
			countryMap.put(country.getName(), "");
		}
		req.setAttribute("countryMap", countryMap);*/
		req.setAttribute("countryMap", DBUtils.getCountryMap());

		// current app info
		if (id != null) {
			String sql = "select * from v_push_apk where id=" + id;
			List<VPushApk> __beanList = DBUtils.query(sql, VPushApk.class);
			if (__beanList.size() > 0) {
				req.setAttribute("pushApk", __beanList.get(0));
			}
		}
		req.getRequestDispatcher("pushapkmodify.jsp").forward(req, resp);
	}

}
