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
import com.push.admin.dbbean.TPushLink;
import com.push.admin.dbbean.VPushLink;
import com.push.admin.dwr.Dwr;
import com.push.admin.dwr.Result;
import com.push.admin.timer.TimerListner;
import com.push.admin.util.DBUtils;
import com.push.admin.util.InsertPushParams;

public class PushLinkServlet extends BasePushServlet {
	public static final String PARAM_NAME = "title";
	private static final long serialVersionUID = 1L;

	@Override
	protected void query(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*String __sql = "select * from t_country";
		List<TCountry> __beanList = DBUtils.query(__sql, TCountry.class);
		Map<String, HashMap<String, String>> countryMap = new HashMap<String, HashMap<String, String>>();
		for (TCountry country : __beanList) {
			if (countryMap.get(country.getName()) == null) {
				countryMap
						.put(country.getName(), new HashMap<String, String>());
			}
			countryMap.get(country.getName()).put(country.getArea(),
					country.getArea());
		}
		req.setAttribute("countryMap", countryMap);*/
		req.setAttribute("countryMap", DBUtils.getCountryMapWithArea());
		
		// 开始查询
		StringBuffer sql = new StringBuffer("select DISTINCT t_push_link.*,");
		sql.append("(select count(t_push_link_his.id) from t_push_link_his where ((t_push_link_his.push_id = t_push_link.id) and (t_push_link_his.status = 0))) AS count_sending,");
		sql.append("(select count(t_push_link_his.id) from t_push_link_his where ((t_push_link_his.push_id = t_push_link.id) and (t_push_link_his.status = 1))) AS count_sended,");
		sql.append("(select count(t_push_link_his.id) from t_push_link_his where ((t_push_link_his.push_id = t_push_link.id) and (t_push_link_his.status = 2))) AS count_clicked ");
		sql.append("from t_push_link LEFT JOIN t_push_params ON (t_push_link.id = t_push_params.server_id and t_push_params.type='link' ) ");
		sql.append("where 1=1 ");

		// 组织查询条件
		List<String> args = new ArrayList<String>();
		String title = req.getParameter(PARAM_NAME); // title
		if (!isEmpty(title)) {
			title = getUTF8(title);
			sql.append(" and t_push_link.title like '%" + title + "%'");
//			args.add(title);
		}
		String countryName = req.getParameter("countryName"); // country name
		if (!isEmpty(countryName)) {
			countryName = getUTF8(countryName);
			sql.append(" and t_push_params.name ='" + InsertPushParams.TYPE_NAME_COUNTRY + "' and t_push_params.values_item = ?");
			args.add(countryName);
		}
		sql.append("order by t_push_link.create_date desc");
		List<VPushLink> beanList = DBUtils.query(sql.toString(), args.toArray(), VPushLink.class);
		
		Date now = new Date();
		for(VPushLink item:beanList){
			if(item.getPushDate()!=null&&item.getPushDate().before(now)){
				item.setCanEdit(false);
			}
		}
		req.setAttribute("countryName", countryName);
		req.setAttribute("title", title);
		req.setAttribute("pagerInfo", pagerInfo);
		req.setAttribute("beanList", beanList);
		req.getRequestDispatcher("pushlinklist.jsp").forward(req, resp);
	}

	@Override
	protected void update(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		TPushLink pushLink = new TPushLink();
		pushLink.setId(id);
		pushLink.setComments(getUTF8(req.getParameter("comments")));
		pushLink.setIcon(getUTF8(req.getParameter("icon")));
		pushLink.setUrl(getUTF8(req.getParameter("url")));

		// add by kui.li start
		pushLink.setImei1(getUTF8(req.getParameter("imei1")));
		pushLink.setImei2(getUTF8(req.getParameter("imei2")));
		pushLink.setProjectName(getUTF8(req.getParameter("projectName")));
		pushLink.setCustomerName(getUTF8(req.getParameter("customerName")));
		pushLink.setModelName(getUTF8(req.getParameter("model")));
		pushLink.setVersionName(getUTF8(req.getParameter("version")));
		// add by kui.li end
		
		pushLink.setLinkType(Integer.parseInt(req.getParameter("linkType")));
		
		pushLink.setCountryName(getUTF8(req.getParameter("countryName")));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try{
			pushLink.setPushDate(new Timestamp(sdf.parse(req.getParameter("pushDate")).getTime()));
		}catch(ParseException pe){
		}
		pushLink.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/xml");
		resp.setHeader("Cache-Control", "no-cache");
		out.println(new Dwr().updateSqlBean(pushLink));
		for (int i=0;i<TimerListner.getInstance().getLinks().size();i++) {
			if (TimerListner.getInstance().getLinks().get(i).getId() == pushLink.getId()) {
				TimerListner.getInstance().getLinks().set(i, pushLink);
				TimerListner.getInstance().openListner();
			}
		}
		out.close();
	}

	@Override
	protected void delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] ids = req.getParameter("ids").split(",");
		if(ids.length>0){
			List<String> sqls = new ArrayList<String>();
			List<TPushLink> links = new ArrayList<TPushLink>();
			for (int i = 0; i < ids.length; i++) {
				for(TPushLink link:TimerListner.getInstance().getLinks()){
					if(link.getId().toString().equals(ids[i])){
						links.add(link);
					}
				}
				sqls.add("delete from t_push_link where id=" + ids[i]);
				sqls.add("delete from t_push_link_his where push_id=" + ids[i]);
				
				// 删除t_push_params中数据
				sqls.add("delete from t_push_params where server_id=" + ids[i]
						+ " and type='" + InsertPushParams.TYPE_LINK + "'");
			}
			for(TPushLink link:links){
				TimerListner.getInstance().getLinks().remove(link);
			}
			boolean res = DBUtils.execute(sqls);
			if(res) {
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
		TPushLink pushLink = new TPushLink();
		pushLink.setTitle(getUTF8(req.getParameter("title")));
		pushLink.setComments(getUTF8(req.getParameter("comments")));
		pushLink.setIcon(getUTF8(req.getParameter("icon")));
		pushLink.setUrl(getUTF8(req.getParameter("url")));

		// add by kui.li start
		pushLink.setImei1(getUTF8(req.getParameter("imei1")));
		pushLink.setImei2(getUTF8(req.getParameter("imei2")));
		pushLink.setProjectName(getUTF8(req.getParameter("projectName")));
		pushLink.setCustomerName(getUTF8(req.getParameter("customerName")));
		pushLink.setModelName(getUTF8(req.getParameter("model")));
		pushLink.setVersionName(getUTF8(req.getParameter("version")));
		// add by kui.li end
		
		pushLink.setLinkType(Integer.parseInt(req.getParameter("linkType")));
		
		pushLink.setCountryName(getUTF8(req.getParameter("countryName")));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try{
			pushLink.setPushDate(new Timestamp(sdf.parse(req.getParameter("pushDate")).getTime()));
		}catch(ParseException pe){
		}
		pushLink.setCreateDate(new Timestamp(System.currentTimeMillis()));
		pushLink.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/xml");
		resp.setHeader("Cache-Control", "no-cache");
		long id = DBUtils.insert(pushLink);
		if(id > 0) {
			out.println( Result.toJson("success", "Add successfully!", id));
			pushLink.setId(id);
			TimerListner.getInstance().getLinks().add(pushLink);
			TimerListner.getInstance().openListner();
			out.close();
		} else {
			out.println( Result.toJson("failure", "Add failed!", id));
		}
	}
	
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// projectMap
		/*String sql = "select product from t_project where product is not null and product !='' group by product order by product";
		List<TProject> lstProject = DBUtils.query(sql, TProject.class);
		Map<String, String> projectMap = new LinkedHashMap<String, String>();
		for (TProject project : lstProject) {
			projectMap.put(project.getProduct(),"");
		}
		req.setAttribute("projectMap", projectMap);*/
		req.setAttribute("projectMap", DBUtils.getProjectMap());
		
		// customerMap
		/*sql = "select customer from t_project where customer is not null and customer !='' group by customer order by customer";
		List<TProject> lstCustomer = DBUtils.query(sql, TProject.class);
		Map<String, String> customerMap = new LinkedHashMap<String, String>();
		for (TProject project : lstCustomer) {
			customerMap.put(project.getCustomer(),"");
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
			modelMap.get(project.getModel()).put(project.getVersion(), project.getVersion());
		}
		req.setAttribute("modelMap", modelMap);*/
		req.setAttribute("modelMap", DBUtils.getModelMap());
		
		// countryMap
		/*sql = "select name from t_country where name is not null and name !='' group by name order by name";
		List<TCountry> beanList = DBUtils.query(sql, TCountry.class);
		Map<String, String> countryMap = new LinkedHashMap<String, String>();
		for (TCountry country : beanList) {
			countryMap.put(country.getName(),"");
		}
		req.setAttribute("countryMap", countryMap);*/
		req.setAttribute("countryMap", DBUtils.getCountryMap());
		
		// current link push info
		if (id != null) {
			String __sql = "select * from t_push_link where id=" + id;
			List<TPushLink> __beanList = DBUtils.query(__sql, TPushLink.class);
			if (__beanList.size() > 0) {
				req.setAttribute("pushLink", __beanList.get(0));
			}
		}
		req.getRequestDispatcher("pushlinkmodify.jsp").forward(req, resp);
	}
}
