package com.push.admin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.push.admin.dbbean.TApks;
import com.push.admin.dwr.Dwr;
import com.push.admin.dwr.Result;
import com.push.admin.util.DBUtils;

public class AppManagementServlet extends BasePushServlet {
	public static final String PARAM_NAME = "name";
	private static final long serialVersionUID = 1L;

	private final Map<String, Map<String, String>> getTypeMap() {
		Map<String, Map<String, String>> typeMap = new HashMap<String, Map<String, String>>();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("娱乐", "娱乐");
		map.put("生活", "生活");
		map.put("工具", "工具");
		map.put("系统", "系统");
		map.put("导航", "导航");
		map.put("购物", "购物");
		map.put("社交", "社交");
		map.put("阅读", "阅读");
		map.put("影音", "影音");
		map.put("安全", "安全");
		map.put("主题", "主题");
		map.put("桌面", "桌面");
		map.put("办公", "办公");
		map.put("理财", "理财");
		map.put("教育", "教育");
		map.put("摄影", "摄影");
		map.put("旅游", "旅游");
		map.put("健康", "健康");
		map.put("新闻", "新闻");
		map.put("其它", "其它");
		typeMap.put("应用", map);
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("益智棋牌", "益智棋牌");
		map1.put("动作射击", "动作射击");
		map1.put("创意休闲", "创意休闲");
		map1.put("角色扮演", "角色扮演");
		map1.put("体育竞速", "体育竞速");
		map1.put("模拟经营", "模拟经营");
		map1.put("网络游戏", "网络游戏");
		map1.put("其它", "其它");
		typeMap.put("游戏", map1);
		return typeMap;
	}

	@Override
	protected void query(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (id != null) {
			queryById(id, req, resp);
		} else {
			doQuery(req, resp);
		}
	}

	private void doQuery(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter(PARAM_NAME);
		boolean nameEmpty = isEmpty(name);
		String type1 = req.getParameter("type1");
		boolean type1Empty = isEmpty(type1);
		String type2 = req.getParameter("type2");
		boolean type2Empty = isEmpty(type2);
		String selection = null;
		List<String> args = new ArrayList<String>();
		StringBuilder sb = new StringBuilder("1=1");
		if (!nameEmpty) {
			name = getUTF8(name);
			sb.append(" and name like '%" + name + "%'");
		}
		if(!type1Empty){
			type1 = getUTF8(type1);
			sb.append(" and type like '%" + type1 + "%'");
		}
		if(!type2Empty){
			type2 = getUTF8(type2);
			sb.append(" and type like '%" + type2 + "%'");
		}
		selection = sb.toString();
		List<TApks> beanList = DBUtils.query(selection, args, "create_date desc",
				TApks.class, pagerInfo);

		req.setAttribute("name", name);
		req.setAttribute("type1", type1);
		req.setAttribute("type2", type2);
		req.setAttribute("pagerInfo", pagerInfo);
		req.setAttribute("beanList", beanList);
		if (req.getParameter("opera") != null
				&& req.getParameter("opera").equals("choose")) {
			req.getRequestDispatcher("chooselist.jsp").forward(req, resp);
		} else {
			req.setAttribute("typeMap", getTypeMap());
			req.getRequestDispatcher("apklist.jsp").forward(req, resp);
		}
	}

	private void queryById(Long id, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String sql = "select * from t_apks where id=" + id;
		List<TApks> beanList = DBUtils.query(sql, TApks.class);
		pagerInfo.setRecordCount(1);
		req.setAttribute("id", id);
		req.setAttribute("pagerInfo", pagerInfo);
		if (beanList.size() > 0) {
			req.setAttribute("apk", beanList.get(0));
		}
		req.getRequestDispatcher("apkdetail.jsp").forward(req, resp);
	}

	@Override
	protected void update(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		TApks apk = new TApks();
		apk.setId(id);
		apk.setName(req.getParameter("name"));
		apk.setChannel(req.getParameter("channel"));
		apk.setDescription(req.getParameter("description"));
		apk.setIcon(req.getParameter("icon"));
		apk.setPackageName(req.getParameter("packageName"));
		apk.setSize(req.getParameter("size"));
		apk.setUrl(req.getParameter("url"));
		apk.setType(req.getParameter("type1")+"-"+req.getParameter("type2"));
		apk.setVersionName(req.getParameter("versionName"));
		apk.setVersionCode(req.getParameter("versionCode"));
		apk.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/xml");
		resp.setHeader("Cache-Control", "no-cache");
		out.println(new Dwr().updateSqlBean(apk));
		out.close();
	}

	@Override
	protected void delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] ids = req.getParameter("ids").split(",");
		if (ids.length > 0) {
			List<String> sqls = new ArrayList<String>();
			for (int i = 0; i < ids.length; i++) {
				sqls.add("delete from t_apks where id=" + ids[i]);
				sqls.add("delete from t_push_apk where apks_id=" + ids[i]);
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
		TApks apk = new TApks();
		apk.setName(req.getParameter("name"));
		apk.setChannel(req.getParameter("channel"));
		apk.setDescription(req.getParameter("description"));
		apk.setIcon(req.getParameter("icon"));
		apk.setPackageName(req.getParameter("packageName"));
		apk.setSize(req.getParameter("size"));
		apk.setUrl(req.getParameter("url"));
		apk.setType(req.getParameter("type1")+"-"+req.getParameter("type2"));
		apk.setVersionName(req.getParameter("versionName"));
		apk.setVersionCode(req.getParameter("versionCode"));
		apk.setCreateDate(new Timestamp(System.currentTimeMillis()));
		apk.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		apk.setStatus(0);
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/xml");
		resp.setHeader("Cache-Control", "no-cache");
		out.println(new Dwr().addSqlBean(apk));
		out.close();
	}

	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getParameter("searchText") == null
				|| req.getParameter("searchText").equals("")) {
			if (id != null) {
				String sql = "select * from t_apks where id=" + id;
				List<TApks> beanList = DBUtils.query(sql, TApks.class);
				if (beanList.size() > 0) {
					TApks apk = beanList.get(0);
					req.setAttribute("apk", apk);
					if (!isEmpty(apk.getType())) {
						if (apk.getType().split("-").length > 1) {
							req.setAttribute("type1",
									apk.getType().split("-")[0]);
							req.setAttribute("type2",
									apk.getType().split("-")[1]);
						}
					}
				}
			}
			req.setAttribute("typeMap", getTypeMap());
			req.getRequestDispatcher("apkmodify.jsp").forward(req, resp);
		} else {
			String name = getUTF8(req.getParameter("searchText"));
			String sql = "select id,name from t_apks where name like '" + name
					+ "%' order by update_date desc limit 10";
			List<TApks> beanList = DBUtils.query(sql, TApks.class);
			String json = "[";
			for (TApks apk : beanList) {
				json += "{'id':'" + apk.getId() + "','name':'" + apk.getName()
						+ "'},";
			}
			json = json.substring(0, json.length() - 1 > 0 ? json.length() - 1
					: 1);
			json += "]";
			resp.setContentType("text/html;charset=UTF-8");
			resp.setHeader("Cache-Control", "no-cache");
			PrintWriter out = resp.getWriter();
			out.println(json);
			out.close();
		}
	}

}
