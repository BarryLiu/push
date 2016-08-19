package com.push.admin.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.push.admin.dbbean.TRoleInfo;
import com.push.admin.dbbean.TUserInfo;
import com.push.admin.util.DBUtils;

public class UserRoleServlet extends BasePushServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void query(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userSql = "select * from t_user_info";
		List<TUserInfo> userInfos = DBUtils.query(userSql, TUserInfo.class);
		if (id == null && userInfos.size() > 1) {
			for (TUserInfo user : userInfos) {
				if (!user.getUsername().equals("sys")) {
					id = user.getId();
					break;
				}
			}
		}
		if(userInfos.size()<=0) {
			logger.error("UserRoleServlet warning : no user!!");
		} else {
			String existingSql = "select t3.* from t_user_info t1,t_user_role t2,t_role_info t3 " +
					"where t1._id=t2.user_info_id and t2.role_info_id=t3._id " +
					"and t1._id=" + id;
			String assignableSql = "select * from t_role_info where _id not in" +
					"(select t2.role_info_id from t_user_info t1,t_user_role t2 " +
						"where t1._id=t2.user_info_id and t1._id=" + id + ")";
			List<TRoleInfo> existingBeanList = DBUtils.query(existingSql, TRoleInfo.class);
			List<TRoleInfo> assignableBeanList = DBUtils.query(assignableSql, TRoleInfo.class);
			req.setAttribute("existingBeans", existingBeanList);
			req.setAttribute("assignableBeans", assignableBeanList);
			req.setAttribute("id", id);
		}
		req.setAttribute("userInfos", userInfos);
		req.getRequestDispatcher("permission/listuserrole.jsp").forward(req, resp);
		
	}

}
