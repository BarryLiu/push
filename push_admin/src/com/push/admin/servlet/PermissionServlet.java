package com.push.admin.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.push.admin.dbbean.TSysPermission;
import com.push.admin.util.DBUtils;

public class PermissionServlet extends BasePushServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String PARAM_NAME = "perName";

	@Override
	protected void query(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String perName = req.getParameter(PARAM_NAME);
		doQuery(perName, req, resp);
	}
	
	private void doQuery(String perName, 
			HttpServletRequest req, HttpServletResponse resp) 
					throws ServletException, IOException {
		/************* getQuerySelection *********************/
		boolean perNameEmpty = isEmpty(perName);
		String selection = null;
		List<String> args = null;
		if(!perNameEmpty) {
			perName=getUTF8(perName);
			StringBuilder sb = new StringBuilder("1=1");
			args = new ArrayList<String>();
			sb.append(" and per_name like '%"+perName+"%'");
			//args.add(username);
			selection = sb.toString();
		}
		
		
		List<TSysPermission> beanList = DBUtils.query(selection, args, null, TSysPermission.class, pagerInfo);
		logger.info("UserServlet.doQuery: perName="+perName+",beanList size="+beanList.size());
		
		req.setAttribute("perName", perName);
		req.setAttribute("pagerInfo", pagerInfo);
		req.setAttribute("beanList", beanList);
		req.getRequestDispatcher("permission/listpermission.jsp").forward(req, resp);
	}

}
