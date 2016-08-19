package com.push.admin.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.push.admin.dbbean.TUserInfo;
import com.push.admin.util.DBUtils;

public class UserServlet extends BasePushServlet {
	public static final String PARAM_NAME = "username";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void query(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(id != null) {
			queryById(id, req, resp);
		} else {
			String username = req.getParameter(PARAM_NAME);
			doQuery(username, req, resp);
		}
	}
	
	private void doQuery(String username, 
			HttpServletRequest req, HttpServletResponse resp) 
					throws ServletException, IOException {
		/************* getQuerySelection *********************/
		boolean usernameEmpty = isEmpty(username);
		String selection = null;
		List<String> args = null;
		if(!usernameEmpty) {
			StringBuilder sb = new StringBuilder("1=1");
			args = new ArrayList<String>();
			sb.append(" and username like '%"+username+"%'");
			//args.add(username);
			selection = sb.toString();
		}
		
		
		List<TUserInfo> beanList = DBUtils.query(selection, args, null, TUserInfo.class, pagerInfo);
		logger.info("UserServlet.doQuery: username="+username+",beanList size="+beanList.size());
		
		req.setAttribute("username", username);
		req.setAttribute("pagerInfo", pagerInfo);
		req.setAttribute("beanList", beanList);
		req.getRequestDispatcher("permission/listuser.jsp").forward(req, resp);
	}
	
	private void queryById(Long id, HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String sql = "select * from t_user_info where _id="+id;
		List<TUserInfo> beanList = DBUtils.query(sql, TUserInfo.class);
		pagerInfo.setRecordCount(1);
		req.setAttribute("id", id);
		req.setAttribute("pagerInfo", pagerInfo);
		req.setAttribute("beanList", beanList);
		req.getRequestDispatcher("permission/listuser.jsp").forward(req, resp);
	}

}
