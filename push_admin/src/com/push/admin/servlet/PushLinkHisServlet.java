package com.push.admin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.push.admin.dbbean.TPushApk;
import com.push.admin.dbbean.TPushLink;
import com.push.admin.dbbean.VPushLink;
import com.push.admin.dbbean.VPushLinkHis;
import com.push.admin.dwr.Dwr;
import com.push.admin.dwr.Result;
import com.push.admin.util.DBUtils;

public class PushLinkHisServlet extends BasePushServlet {
	public static final String PARAM_NAME = "pushId";
	private static final long serialVersionUID = 1L;

	@Override
	protected void query(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pushId = req.getParameter(PARAM_NAME);
		String _sql = "select * from v_push_link where id=" + pushId;
		List<VPushLink> _beanLists = DBUtils.query(_sql, VPushLink.class);
		if (_beanLists.size() > 0) {
			req.setAttribute("pushLink", _beanLists.get(0));
		} else {
			req.setAttribute("pushLink", new TPushLink());
		}
		boolean pushIdEmpty = isEmpty(pushId);
		String selection = null;
		List<String> args = null;
		if(!pushIdEmpty) {
			StringBuilder sb = new StringBuilder("1=1");
			args = new ArrayList<String>();
			sb.append(" and push_id like ");
			sb.append("'%"+pushId+"%'");
			selection = sb.toString();
		}
		resp.setContentType("text/html; charset=utf-8");
		
		List<VPushLinkHis> beanList = DBUtils.query(selection, args, null, VPushLinkHis.class, pagerInfo);
		
		req.setAttribute("pushId", pushId);
		req.setAttribute("pagerInfo", pagerInfo);
		req.setAttribute("beanList", beanList);
		req.getRequestDispatcher("pushlinkdetail.jsp").forward(req, resp);
	}

	@Override
	protected void update(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		TPushApk pushApk = new TPushApk();
		pushApk.setId(id);
		if (!req.getParameter("apksId").isEmpty()) {
			pushApk.setApksId(Long.parseLong(req.getParameter("apksId")));
		}
		pushApk.setComments(req.getParameter("comments"));
		pushApk.setCountryName(req.getParameter("countryName"));
		if (!req.getParameter("installType").isEmpty()) {
			pushApk.setInstallType(Integer.parseInt(req
					.getParameter("installType")));
		}
		pushApk.setUpdateComments(req.getParameter("updateComments"));
		pushApk.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/xml");
		resp.setHeader("Cache-Control", "no-cache");
		out.println(new Dwr().updateSqlBean(pushApk));
		out.close();
	}

	@Override
	protected void delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] ids = req.getParameter("ids").split(",");
		if(ids.length>0){
			List<String> sqls = new ArrayList<String>();
			for (int i = 0; i < ids.length; i++) {
				sqls.add("delete from t_push_apk where id=" + ids[i]);
				sqls.add("delete from t_push_apk_his where push_id=" + ids[i]);
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
		TPushApk pushApk = new TPushApk();
		pushApk.setTitle(req.getParameter("title"));
		if (!req.getParameter("apksId").isEmpty()) {
			pushApk.setApksId(Long.parseLong(req.getParameter("apksId")));
		}
		pushApk.setComments(req.getParameter("comments"));
		pushApk.setCountryName(req.getParameter("countryName"));
		if (!req.getParameter("installType").isEmpty()) {
			pushApk.setInstallType(Integer.parseInt(req
					.getParameter("installType")));
		}
		pushApk.setUpdateComments(req.getParameter("updateComments"));
		pushApk.setCreateDate(new Timestamp(System.currentTimeMillis()));
		pushApk.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/xml");
		resp.setHeader("Cache-Control", "no-cache");
		out.println(new Dwr().addSqlBean(pushApk));
		out.close();
	}
	
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (id != null) {
			String __sql = "select * from v_push_link_his where id=" + id;
			List<VPushLinkHis> __beanList = DBUtils.query(__sql, VPushLinkHis.class);
			if (__beanList.size() > 0) {
				req.setAttribute("pushLinkHis", __beanList.get(0));
			}
		}
		req.getRequestDispatcher("pushlinkhisdetail.jsp").forward(req, resp);
	}
}
