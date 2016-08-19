package com.push.admin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.push.admin.dbbean.TRoleInfo;
import com.push.admin.dbbean.TSysPermission;
import com.push.admin.dwr.Result;
import com.push.admin.util.DBUtils;

public class RolePermissionServlet extends BasePushServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void query(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String roleSql = "select * from t_role_info ";
		List<TRoleInfo> roleInfos = DBUtils.query(roleSql, TRoleInfo.class);
		if (id == null && roleInfos.size() > 1) {
			for (TRoleInfo roleInfo : roleInfos) {
				if (!roleInfo.getRoleName().equals("超级管理员")) {
					id = roleInfo.getId();
					break;
				}
			}
		}
		if (roleInfos.size() <= 0) {
			logger.error("RolePermissionServlet warning : no role!!");
		} else {
			String existingSql = "select t3.* from t_role_info t1,t_role_permission t2,t_sys_permission t3 "
					+ "where t1._id=t2.role_info_id and t2.permission_id=t3._id "
					+ "and t1._id=" + id;
			String assignableSql = "select * from t_sys_permission";
			List<TSysPermission> existingBeanList = DBUtils.query(existingSql,
					TSysPermission.class);
			List<TSysPermission> assignableBeanList = DBUtils.query(
					assignableSql, TSysPermission.class);
			req.setAttribute("permissions2", existingBeanList);
			req.setAttribute("permissions", assignableBeanList);
			req.setAttribute("id", id);
		}
		req.setAttribute("roleInfos", roleInfos);
		req.getRequestDispatcher("permission/listrolepermission.jsp").forward(
				req, resp);
	}

	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String roleId = req.getParameter("roleid");
		List<String> sqls = new ArrayList<String>();
		sqls.add("delete from t_role_permission where role_info_id=" + roleId);
		for (String perId : req.getParameter("pers").split(",")) {
			sqls.add("insert into t_role_permission(role_info_id,permission_id) values("
					+ roleId + "," + perId + ")");
		}
		boolean res = DBUtils.execute(sqls);
		resp.setContentType("text/xml");
		resp.setHeader("Cache-Control", "no-cache");
		PrintWriter out = resp.getWriter();
		if (res) {
			out.println(Result.toJson("success", " Assign successfully! "));
		} else {
			out.println(Result.toJson("failure", " Assign Failed! "));
		}
		out.close();
	}

}
