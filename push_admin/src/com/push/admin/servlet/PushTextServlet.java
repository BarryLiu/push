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
import com.push.admin.dbbean.TPushText;
import com.push.admin.dbbean.VPushLink;
import com.push.admin.dbbean.VPushText;
import com.push.admin.dwr.Dwr;
import com.push.admin.dwr.Result;
import com.push.admin.timer.TimerListner;
import com.push.admin.util.DBUtils;
import com.push.admin.util.InsertPushParams;

public class PushTextServlet extends BasePushServlet{
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
		StringBuffer sql = new StringBuffer("select DISTINCT t_push_text.*,");
		sql.append("(select count(t_push_text_his.id) from t_push_text_his where ((t_push_text_his.push_id = t_push_text.id) and (t_push_text_his.status = 0))) AS count_sending,");
		sql.append("(select count(t_push_text_his.id) from t_push_text_his where ((t_push_text_his.push_id = t_push_text.id) and (t_push_text_his.status = 1))) AS count_sended,");
		sql.append("(select count(t_push_text_his.id) from t_push_text_his where ((t_push_text_his.push_id = t_push_text.id) and (t_push_text_his.status = 2))) AS count_clicked ");
		sql.append("from t_push_text left join t_push_params ");
		sql.append("on t_push_text.id = t_push_params.server_id ");
		sql.append("and t_push_params.type='text' ");
		sql.append("where 1=1 ");

		// 组织查询条件
		List<String> args = new ArrayList<String>();
		String title = req.getParameter(PARAM_NAME); // title
		if (!isEmpty(title)) {
			title = getUTF8(title);
			sql.append(" and t_push_text.title like '%" + title + "%'");
//			args.add(title);
		}
		String countryName = req.getParameter("countryName"); // country name
		if (!isEmpty(countryName)) {
			countryName = getUTF8(countryName);
			sql.append(" and t_push_params.name ='" + InsertPushParams.TYPE_NAME_COUNTRY + "' and t_push_params.values_item = ?");
			args.add(countryName);
		}
		sql.append("order by t_push_text.create_date desc");
		List<VPushText> beanList = DBUtils.query(sql.toString(), args.toArray(), VPushText.class);
		
		Date now = new Date();
		for(VPushText item : beanList){
			if(item.getPushDate()!=null&&item.getPushDate().before(now)){
				item.setCanEdit(false);
			}
		}
		req.setAttribute("countryName", countryName);
		req.setAttribute("title", title);
		req.setAttribute("pagerInfo", pagerInfo);
		req.setAttribute("beanList", beanList);
		req.getRequestDispatcher("pushtextlist.jsp").forward(req, resp);
	}

	@Override
	protected void update(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		TPushText pushText = new TPushText();
		pushText.setId(id);
		pushText.setComments(getUTF8(req.getParameter("comments")));
		pushText.setCountryName(getUTF8(req.getParameter("countryName")));
		pushText.setIcon(getUTF8(req.getParameter("icon")));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try{
			pushText.setPushDate(new Timestamp(sdf.parse(req.getParameter("pushDate")).getTime()));
		}catch(ParseException pe){
		}
		pushText.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/xml");
		resp.setHeader("Cache-Control", "no-cache");
		out.println(new Dwr().updateSqlBean(pushText));
		for (int i=0;i<TimerListner.getInstance().getTexts().size();i++) {
			if (TimerListner.getInstance().getTexts().get(i).getId() == pushText.getId()) {
				TimerListner.getInstance().getTexts().set(i, pushText);
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
			List<TPushText> texts = new ArrayList<TPushText>();
			for (int i = 0; i < ids.length; i++) {
				for(TPushText text:TimerListner.getInstance().getTexts()){
					if(text.getId().toString().equals(ids[i])){
						texts.add(text);
					}
				}
				sqls.add("delete from t_push_text where id=" + ids[i]);
				sqls.add("delete from t_push_text_his where push_id=" + ids[i]);
				
				// 删除t_push_params中数据
				sqls.add("delete from t_push_params where server_id=" + ids[i]
						+ " and type='" + InsertPushParams.TYPE_TEXT + "'");
			}
			for(TPushText text:texts){
				TimerListner.getInstance().getTexts().remove(text);
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
		TPushText pushText = new TPushText();
		pushText.setTitle(getUTF8(req.getParameter("title")));
		pushText.setComments(getUTF8(req.getParameter("comments")));
		pushText.setCountryName(getUTF8(req.getParameter("countryName")));
		pushText.setIcon(getUTF8(req.getParameter("icon")));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try{
			pushText.setPushDate(new Timestamp(sdf.parse(req.getParameter("pushDate")).getTime()));
		}catch(ParseException pe){
		}
		pushText.setCreateDate(new Timestamp(System.currentTimeMillis()));
		pushText.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/xml");
		resp.setHeader("Cache-Control", "no-cache");
		long id = DBUtils.insert(pushText);
		if(id > 0) {
			out.println( Result.toJson("success", "Add successfully!", id));
			pushText.setId(id);
			TimerListner.getInstance().getTexts().add(pushText);
			TimerListner.getInstance().openListner();
			
		} else {
			out.println( Result.toJson("failure", "Add failed!", id));
		}
		out.close();
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
		
		// current text info
		if (id != null) {
			String __sql = "select * from t_push_text where id=" + id;
			List<TPushText> __beanList = DBUtils.query(__sql, TPushText.class);
			if (__beanList.size() > 0) {
				req.setAttribute("pushText", __beanList.get(0));
			}
		}
		req.getRequestDispatcher("pushtextmodify.jsp").forward(req, resp);
	}
}
