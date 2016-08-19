package com.push.admin.dwr;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.push.admin.dbbean.SqlBean;
import com.push.admin.dbbean.TUserInfo;
import com.push.admin.dbbean.TRoleInfo;
import com.push.admin.dbbean.TSysPermission;
import com.push.admin.dbbean.TTestUserinfo;
import com.push.admin.dbbean.TUserInfo;
import com.push.admin.util.DBUtils;


public class Dwr {
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	public static final String RELOGIN = "relogin";
	public static final String REFRESH = "refresh";
	
	public static final String SESSION_USERNAME = "username";
	public static final String SESSION_USERID = "userId";
	public static final String SESSION_LOGINIP = "loginIP";
	public static final String SESSION_LOGINDATE = "loginDate";
	public static final String SESSION_USERPERMISSIONS = "userpermissions";
	
	public static final String SUPER_USER = "admin";
	public static final String SUPER_USER_PWD = "admin";
	
	//Facilitate future expansion
	private void log(String s) {
		System.out.println(s);
	}
	
	private String getSuperUser() {
		//return SUPER_USER;
		return WebContextFactory.get().getServletContext().getInitParameter("superUserName");
	}
	
	private String getSuperUserPwd() {
		//return SUPER_USER_PWD;
		return WebContextFactory.get().getServletContext().getInitParameter("superUserPwd");
	}
	
	public String handleLogin(String username, String password) {
		log("handleLogin : username="+username+",password="+password);
		
		WebContext webContext = WebContextFactory.get();
		HttpSession session = webContext.getSession();
		if(getSuperUser().equals(username)) {
			if(getSuperUserPwd().equals(password)) {
				session.setAttribute(SESSION_USERNAME, username);
				session.setAttribute(SESSION_USERID, -1l);
				session.setAttribute(SESSION_USERPERMISSIONS, getPermissionValues(-1l));
				session.setAttribute(SESSION_LOGINDATE, getLoginDate());
				session.setAttribute(SESSION_LOGINIP, getRemortIP(webContext.getHttpServletRequest()));
				return SUCCESS;
			}
		} else {
			String sql = "select * from t_user_info where username=? ";
			List<TUserInfo> userInfos = DBUtils.query(sql, new Object[]{username}, TUserInfo.class);
			if(userInfos.size()==1&&userInfos.get(0).getPwd().equals(password)) {
				TUserInfo user = userInfos.get(0);
				session.setAttribute(SESSION_USERNAME, user.getUsername());
				session.setAttribute(SESSION_USERID, user.getId());
				session.setAttribute(SESSION_USERPERMISSIONS, getPermissionValues(user.getId()));
				session.setAttribute(SESSION_LOGINDATE, getLoginDate());
				session.setAttribute(SESSION_LOGINIP, getRemortIP(webContext.getHttpServletRequest()));
				return SUCCESS;
			}
		}
		return "Username or Password is incorrect, the login fails.";
	}
	
	public String resetLogin() {
		HttpSession session = getHttpSession();
		session.setAttribute(SESSION_USERNAME, null);
		session.setAttribute(SESSION_USERID, null);
		session.setAttribute(SESSION_USERPERMISSIONS, null);
		session.setAttribute(SESSION_LOGINDATE, null);
		session.setAttribute(SESSION_LOGINIP, null);
		return SUCCESS;
	}
	
	private HttpSession getHttpSession() {
		WebContext webContext = WebContextFactory.get();
		HttpSession session = webContext.getSession();
		return session;
	}
	
	private String getRemortIP(HttpServletRequest request) {
		  if (request.getHeader("x-forwarded-for") == null) {
			  return request.getRemoteAddr();
		  }
		  return request.getHeader("x-forwarded-for");
	}
	
	private String getLoginDate() {
		SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currTime=simple.format(new Date());
		return currTime;
	}
	
	private List<String> getPermissionValues(long userId) {
		List<String> permissionList = new ArrayList<String>();
		String perSql = null;
		if(userId==-1l) {
			perSql = "select * from t_sys_permission";
		} else {
			perSql = "select distinct t5.* from t_user_info t1,t_user_role t2,t_role_info t3,t_role_permission t4,t_sys_permission t5 " +
					"where t1._id=t2.user_info_id and t2.role_info_id=t3._id and t3._id=t4.role_info_id and t4.permission_id=t5._id " +
					"and t1._id="+userId;
		}
		List<TSysPermission> pers = DBUtils.query(perSql, TSysPermission.class);
		for(TSysPermission per : pers) {
			permissionList.add(per.getPerValue());
		}
		return permissionList;
	}
	
	public List<TSysPermission> getPermissions(long userId) {
		String perSql = null;
		if(userId==-1l) {
			perSql = "select * from t_sys_permission";
		} else {
			perSql = "select distinct t5.* from t_user_info t1,t_user_role t2,t_role_info t3,t_role_permission t4,t_sys_permission t5 " +
					"where t1._id=t2.user_info_id and t2.role_info_id=t3._id and t3._id=t4.role_info_id and t4.permission_id=t5._id " +
					"and t1._id="+userId;
		}
		List<TSysPermission> pers = DBUtils.query(perSql, TSysPermission.class);
		return pers;
	}
	
	public Long getCurUserId() {
		HttpSession session = getHttpSession();
		Object userId = session.getAttribute(SESSION_USERID);
		if(userId!=null && userId instanceof Long) {
			return (Long) userId;
		}
		return null;
	}
	
	private boolean isCurrentUser(long userId) {
		Long curUserId = getCurUserId();
		if(curUserId!=null && userId==curUserId) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean hasCurrentUser(long[] userIds) {
		boolean res = false;
		Long curUserId = getCurUserId();
		if(curUserId!=null) {
			for(long id : userIds) {
				if(id==curUserId) {
					res = true;
					break;
				}
			}
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public boolean changeUserPermissions() {
		boolean change = false;
		HttpSession session = getHttpSession();
		Long curUserId = getCurUserId();
		List<String> oriPerms = (List<String>) session.getAttribute(SESSION_USERPERMISSIONS);
		if(curUserId != null || oriPerms!=null) {
			List<String> curPerms = getPermissionValues(curUserId);
			if(oriPerms.size() != curPerms.size()) {
				change = true;
			} else {
				for(String perm : oriPerms) {
					if(!curPerms.contains(perm)) {
						change = true;
						break;
					}
				}
			}
			if(change) {
				session.setAttribute(SESSION_USERPERMISSIONS, curPerms);
			}
		}
		return change;
	}
	
	/****************************** Common operation ********************************/
	public String deleteSqlBean(SqlBean bean) {
		int res = DBUtils.delete(bean);
		if(res > 0) {
			return Result.toJson(SUCCESS, " Delete successfully! ");
		} else {
			return Result.toJson(FAILURE, " Delete failed! ");
		}
	}
	
	public String deleteSqlBeans(List<? extends SqlBean> beanlist) {
		int res = DBUtils.delete(beanlist);
		if(res > 0) {
			return Result.toJson(SUCCESS, " Delete successfully! ");
		} else {
			return Result.toJson(FAILURE, " Delete failed! ");
		}
	}
	
	public String updateSqlBean(SqlBean bean) {
		int res = DBUtils.update(bean);
		if(res > 0) {
			return Result.toJson(SUCCESS, " Modify successfully! ");
		} else {
			return Result.toJson(FAILURE, " Modify failed! ");
		}
	}
	
	public String updateSqlBean(SqlBean bean, String existSelection, String[] selectionArgs, String existName) {
		existName = existName==null?"":existName;
		int res = DBUtils.update(bean, existSelection, selectionArgs);
		if(res == -2) {
			return Result.toJson(FAILURE, existName+" already exists!");
		} else if(res <= 0) {
			return Result.toJson(FAILURE, " Modify failed! ");
		} else {
			return Result.toJson(SUCCESS, " Modify successfully! ");
		}
	}
	
	public  String addSqlBean(SqlBean bean) {
		long id = DBUtils.insert(bean);
		if(id > 0) {
			return Result.toJson(SUCCESS, "Add successfully!", id);
		} else {
			return Result.toJson(FAILURE, "Add failed!", id);
		}
	}
	
	public String addSqlBean(SqlBean bean, String existSelection, String[] selectionArgs, String existName) {
		existName = existName==null?"":existName;
		long id = DBUtils.insert(bean, existSelection, selectionArgs);
		if(id == -2) {
			return Result.toJson(FAILURE, existName+" already exists!", id);
		} else if(id <= 0) {
			return Result.toJson(FAILURE, "Add failed!", id);
		} else {
			return Result.toJson(SUCCESS, "Add successfully!", id);
		}
	}
	
	/****************************** TTestUserinfo start ********************************/
	public long addTestUserInfo(String name, String sex) {
		TTestUserinfo user = new TTestUserinfo();
		user.setName(name);
		user.setSex(sex);
		user.setCreateDate(new Timestamp(System.currentTimeMillis()));
		long id = DBUtils.insert(user);
		return id;
	}
	
	public String updateTestUserInfo(long id, String name, String sex) {
		TTestUserinfo user = new TTestUserinfo();
		user.setId(id);
		user.setName(name);
		user.setSex(sex);
		int res = DBUtils.update(user);
		if(res > 0) {
			return SUCCESS;
		} else {
			return FAILURE;
		}
	}
	
	public String deleteTestUserInfo(long id) {
		TTestUserinfo user = new TTestUserinfo();
		user.setId(id);
		int res = DBUtils.delete(user);
		if(res > 0) {
			return SUCCESS;
		} else {
			return FAILURE;
		}
	}
	
	public String deleteTestUserInfos(long[] ids) {
		List<TTestUserinfo> beanlist = new ArrayList<TTestUserinfo>();
		for(long id : ids) {
			TTestUserinfo user = new TTestUserinfo();
			user.setId(id);
			beanlist.add(user);
		}
		int res = DBUtils.delete(beanlist);
		if(res > 0) {
			return SUCCESS;
		} else {
			return FAILURE;
		}
	}
	/****************************** TTestUserinfo end ********************************/
	
	/****************************** TUserInfo start ********************************/
	public String updateUserInfo(long id, String pwd, String comments) {
		TUserInfo userInfo = new TUserInfo();
		userInfo.setId(id);
		userInfo.setPwd(pwd);
		userInfo.setUpdateComments(comments);
		userInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		return updateSqlBean(userInfo);
	}
	
	public String addUserInfo(String username, String pwd) {
		if(username.equals(getSuperUser())) {
			return Result.toJson(FAILURE, getSuperUser()+" is the super administrator, can not add it!");
		}
		TUserInfo userInfo = new TUserInfo();
		userInfo.setUsername(username);
		userInfo.setPwd(pwd);
		userInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
		return addSqlBean(userInfo, "username=?", new String[]{username}, username);
	}
	
	public String deleteUserInfo(long id) {
//		TUserInfo userInfo = new TUserInfo();
//		userInfo.setId(id);
//		return deleteSqlBean(userInfo);
		List<String> sqls = new ArrayList<String>();
		sqls.add("delete from t_user_info where _id=" + id);
		sqls.add("delete from t_user_role where user_info_id=" + id);
		boolean res = DBUtils.execute(sqls);
		if(res) {
			if(isCurrentUser(id)) {
				resetLogin();
				return Result.toJson("relogin", "The current user has been deleted, please re-login system!", "Delete successfully");
			} else {
				return Result.toJson(SUCCESS, " Delete successfully! ");
			}
		} else {
			return Result.toJson(FAILURE, " Delete failed! ");
		}
	}
	
	public String deleteUserInfos(long[] ids) {
//		List<TUserInfo> beanlist = new ArrayList<TUserInfo>();
//		for(long id : ids) {
//			TUserInfo bean = new TUserInfo();
//			bean.setId(id);
//			beanlist.add(bean);
//		}
//		return deleteSqlBeans(beanlist);
		List<String> sqls = new ArrayList<String>();
		for(long id : ids) {
			sqls.add("delete from t_user_info where _id=" + id);
			sqls.add("delete from t_user_role where user_info_id=" + id);
		}
		boolean res = DBUtils.execute(sqls);
		if(res) {
			if(hasCurrentUser(ids)) {
				resetLogin();
				return Result.toJson("relogin", "The current user has been deleted, please re-login system!", "Delete successfully");
			} else {
				return Result.toJson(SUCCESS, " Delete successfully! ");
			}
		} else {
			return Result.toJson(FAILURE, " Delete failed! ");
		}
	}
	/****************************** TUserInfo end ********************************/
	
	/****************************** TRoleInfo start ********************************/
	public String updateRoleInfo(long id, String roleName, String comments) {
		TRoleInfo roleInfo = new TRoleInfo();
		roleInfo.setId(id);
		roleInfo.setRoleName(roleName);
		roleInfo.setUpdateComments(comments);
		roleInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		return updateSqlBean(roleInfo, "role_name=? and _id!="+id, new String[]{roleName}, roleName);
	}
	
	public String addRoleInfo(String roleName) {
		TRoleInfo roleInfo = new TRoleInfo();
		roleInfo.setRoleName(roleName);
		roleInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
		return this.addSqlBean(roleInfo, "role_name=?", new String[]{roleName}, roleName);
	}
	
//	private String tipDelete(boolean res) {
//	if(res) {
//		return Result.toJson(SUCCESS, " Delete successfully! ");
//	} else {
//		return Result.toJson(FAILURE, " Delete failed! ");
//	}
//}
	public String deleteRoleInfo(long id) {
//		TRoleInfo roleInfo = new TRoleInfo();
//		roleInfo.setId(id);
//		return deleteSqlBean(roleInfo);
		List<String> sqls = new ArrayList<String>();
		sqls.add("delete from t_role_info where _id=" + id);
		sqls.add("delete from t_user_role where role_info_id=" + id);
		sqls.add("delete from t_role_permission where role_info_id=" + id);
		boolean res = DBUtils.execute(sqls);
		if(res) {
			if(changeUserPermissions()) {
				return Result.toJson(REFRESH, "The current user\\'s permissions have changed, need to refresh the main page", "Delete successfully");
			} else {
				return Result.toJson(SUCCESS, " Delete successfully! ");
			}
		} else {
			return Result.toJson(FAILURE, " Delete failed! ");
		}
	}
	
	public String deleteRoleInfos(long[] ids) {
//		List<TRoleInfo> beanlist = new ArrayList<TRoleInfo>();
//		for(long id : ids) {
//			TRoleInfo bean = new TRoleInfo();
//			bean.setId(id);
//			beanlist.add(bean);
//		}
//		return deleteSqlBeans(beanlist);
		List<String> sqls = new ArrayList<String>();
		for(long id : ids) {
			sqls.add("delete from t_role_info where _id=" + id);
			sqls.add("delete from t_user_role where role_info_id=" + id);
			sqls.add("delete from t_role_permission where role_info_id=" + id);
		}
		boolean res = DBUtils.execute(sqls);
		if(res) {
			if(changeUserPermissions()) {
				return Result.toJson(REFRESH, "The current user\\'s permissions have changed, need to refresh the main page", "Delete successfully");
			} else {
				return Result.toJson(SUCCESS, " Delete successfully! ");
			}
		} else {
			return Result.toJson(FAILURE, " Delete failed! ");
		}
	}
	/****************************** TRoleInfo end ********************************/
	
	/****************************** assign start ********************************/
	public String assignRoles(long userId, long[] roleIds) {
		List<String> sqls = new ArrayList<String>();
		sqls.add("delete from t_user_role where user_info_id=" + userId);
		for(long roleId : roleIds) {
			sqls.add("insert into t_user_role(user_info_id, role_info_id) values(" + userId + "," + roleId + ")");
		}
		boolean res = DBUtils.execute(sqls);
		if(res) {
			if(changeUserPermissions()) {
				return Result.toJson(REFRESH, "The current user\\'s permissions have changed, need to refresh the main page", "Assign successfully");
			} else {
				return Result.toJson(SUCCESS, " Assign successfully! ");
			}
		} else {
			return Result.toJson(FAILURE, " Assign Failed! ");
		}
	}
	public String assignPermissions(long roleId, long[] permissionIds) {
		List<String> sqls = new ArrayList<String>();
		sqls.add("delete from t_role_permission where role_info_id=" + roleId);
		for(long perId : permissionIds) {
			sqls.add("insert into t_role_permission(role_info_id,permission_id) values(" + roleId + "," + perId + ")");
		}
		boolean res = DBUtils.execute(sqls);
		if(res) {
			if(changeUserPermissions()) {
				return Result.toJson(REFRESH, "The current user\\'s permissions have changed, need to refresh the main page", "Assign successfully");
			} else {
				return Result.toJson(SUCCESS, " Assign successfully! ");
			}
		} else {
			return Result.toJson(FAILURE, " Assign Failed! ");
		}
	}
	/****************************** assign end ********************************/
	
}
