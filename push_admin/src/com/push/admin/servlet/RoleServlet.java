package com.push.admin.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.push.admin.dbbean.TRoleInfo;
import com.push.admin.util.DBUtils;

public class RoleServlet extends BasePushServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1498508187623185047L;
	public static final String PARAM_NAME = "roleName";

	@Override
	protected void query(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(id != null) {
			queryById(id, req, resp);
		} else {
			String roleName = req.getParameter(PARAM_NAME);
			doQuery(roleName, req, resp);
		}
	}
	
	private void doQuery(String roleName, 
			HttpServletRequest req, HttpServletResponse resp) 
					throws ServletException, IOException {
		/************* getQuerySelection *********************/
		boolean roleNameEmpty = isEmpty(roleName);
		String selection = null;
		List<String> args = null;
		if(!roleNameEmpty) {
			roleName=getUTF8(roleName);
			StringBuilder sb = new StringBuilder("1=1");
			args = new ArrayList<String>();
			sb.append(" and role_name like '%"+roleName+"%'");
			//args.add(username);
			selection = sb.toString();
		}
        List<TRoleInfo> beanList;
        String order = getUTF8(req,"order");
		if(!order.equals("")){
            String da = getUTF8(req,"da");
            if(da.equals("2")){
                order += " desc";
            }else {
                order += " asc";
            }
            beanList = DBUtils.query(selection, args, order, TRoleInfo.class, pagerInfo);
        }else {
            beanList = DBUtils.query(selection, args, null, TRoleInfo.class, pagerInfo);
        }
		logger.info("UserServlet.doQuery: roleName="+roleName+",beanList size="+beanList.size());
		
		req.setAttribute("roleName", roleName);
		req.setAttribute("pagerInfo", pagerInfo);
		req.setAttribute("beanList", beanList);
		req.getRequestDispatcher("permission/listrole.jsp").forward(req, resp);
	}
	
	private void queryById(Long id, HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String sql = "select * from t_role_info where _id="+id;
		List<TRoleInfo> beanList = DBUtils.query(sql, TRoleInfo.class);
		pagerInfo.setRecordCount(1);
		req.setAttribute("id", id);
		req.setAttribute("pagerInfo", pagerInfo);
		req.setAttribute("beanList", beanList);
		req.getRequestDispatcher("permission/listrole.jsp").forward(req, resp);
	}

}
