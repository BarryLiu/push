package com.push.admin.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.push.admin.dbbean.TTestUserinfo;
import com.push.admin.util.DBUtils;

public class UserInfoServlet extends BasePushServlet {
	public static final String PARAM_ID = "id";
	public static final String PARAM_NAME = "name";
	public static final String PARAM_SEX = "sex";
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
			String name = req.getParameter(PARAM_NAME);
			String sex = req.getParameter(PARAM_SEX);
			doQuery(name, sex, req, resp);
		}	
	}
	
	private void doQuery(String name, String sex, 
			HttpServletRequest req, HttpServletResponse resp) 
					throws ServletException, IOException {
		/************* getQuerySelection *********************/
		boolean nameEmpty = isEmpty(name);
		boolean sexEmpty = isEmpty(sex);
		String selection = null;
		List<String> args = null;
		if(!(nameEmpty && sexEmpty)) {
			StringBuilder sb = new StringBuilder("1=1");
			args = new ArrayList<String>();
			if(!nameEmpty) {
				sb.append(" and name=?");
				args.add(name);
			}
			if(!sexEmpty) {
				sb.append(" and sex=?");
				args.add(sex);
			}
			selection = sb.toString();
		}
		
		
		List<TTestUserinfo> beanList = DBUtils.query(selection, args, null, TTestUserinfo.class, pagerInfo);
		logger.info("UserInfoServlet.doQuery: name="+name+",sex="+sex+",beanList size="+beanList.size());
		
		req.setAttribute("name", name);
		req.setAttribute("sex", sex);
		req.setAttribute("pagerInfo", pagerInfo);
		req.setAttribute("beanList", beanList);
		req.getRequestDispatcher("test/listtest.jsp").forward(req, resp);
	}
	
	private void queryById(Long id, HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String sql = "select * from t_test_userinfo where _id="+id;
		List<TTestUserinfo> beanList = DBUtils.query(sql, TTestUserinfo.class);
		pagerInfo.setRecordCount(1);
		req.setAttribute("id", id);
		req.setAttribute("pagerInfo", pagerInfo);
		req.setAttribute("beanList", beanList);
		req.getRequestDispatcher("test/listtest.jsp").forward(req, resp);
	}
	
}
