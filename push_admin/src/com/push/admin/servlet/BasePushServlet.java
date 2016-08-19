package com.push.admin.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.directwebremoting.util.Logger;

import com.push.admin.util.PagerInfo;

public abstract class BasePushServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5727801664354902040L;

	protected Logger logger = Logger.getLogger(BasePushServlet.class);

	public static final String PARAM_ID = "id"; // key值,用于修改，单条删除，单条查询等
	public static final String PARAM_OPERATION = "operation"; // 操作类型，查询，删除，修改等，默认查询
	public static final String PARAM_PAGESIZE = "pageSize"; // 每页显示多少条
	public static final String PARAM_PAGEINDEX = "pageIndex"; // 当前第几页

	public static final String OPE_QUERY = "query";
	public static final String OPE_ADD = "add";
	public static final String OPE_DELETE = "delete";
	public static final String OPE_UPDATE = "update";
	public static final String OPE_OTHER = "other";

	protected Long id;
	protected String operation;
	protected PagerInfo pagerInfo;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		/************** Parameter acquired in advance(can empty) ****************/
		id = getLong(req.getParameter(PARAM_ID));
		/************** PagerInfo ****************/
		pagerInfo = new PagerInfo();
		String pageSize = req.getParameter(PARAM_PAGESIZE);
		String pageIndex = req.getParameter(PARAM_PAGEINDEX);
		pagerInfo.setPageSize(getInteger(pageSize, PagerInfo.DEFAULT_PAGESIZE));
		pagerInfo.setPageIndex(getInteger(pageIndex,
				PagerInfo.DEFAULT_PAGEINDEX));

		/************** Operation Type(can empty, default is OPE_QUERY) ****************/
		String ope = req.getParameter(PARAM_OPERATION);
		operation = isEmpty(ope) ? OPE_QUERY : ope;
		if (OPE_QUERY.equals(operation)) {
			query(req, resp);
		} else if (OPE_ADD.equals(operation)) {
			add(req, resp);
		} else if (OPE_DELETE.equals(operation)) {
			delete(req, resp);
		} else if (OPE_UPDATE.equals(operation)) {
			update(req, resp);
		} else if (OPE_OTHER.equals(operation)) {
			other(req, resp);
		}
	}

	protected final Integer getInteger(String s, Integer def) {
		Integer res = def;
		if (s != null && !"".equals(s)) {
			try {
				res = Integer.parseInt(s);
			} catch (Exception e) {
				logger.error(e.toString());
			}
		}
		return res;
	}

	protected final Integer getInteger(String s) {
		return getInteger(s, null);
	}

	protected final Long getLong(String s, Long def) {
		Long res = def;
		if (s != null && !"".equals(s)) {
			try {
				res = Long.parseLong(s);
			} catch (Exception e) {
				logger.error(e.toString());
			}
		}
		return res;
	}

	protected final Long getLong(String s) {
		return getLong(s, null);
	}

	protected final String getUTF8(String s) {
		try {
			if (s != null && !s.equals("")) {
				return URLDecoder.decode(new String(s.getBytes("ISO-8859-1"),
						"UTF-8"), "UTF-8");
			} else {
				return "";
			}
		} catch (UnsupportedEncodingException se) {
			logger.error("UFT-8Error", se);
			return "";
		}
	}

	protected final boolean isEmpty(String s) {
		return s == null || s.trim().equals("");
	}

	protected final boolean notEmpty(String s) {
		return s != null && !s.trim().equals("");
	}

	/**
	 * 子类重写用于做查询操作，默认的动作
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected abstract void query(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException;

	/**
	 * 请求参数包含：operation=update 时调用 子类重写用于更新数据
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void update(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	/**
	 * 请求参数包含：operation=delete 时调用 子类重写用于删除数据
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	/**
	 * 请求参数包含：operation=add 时调用 子类重写用于添加数据
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	/**
	 * 请求参数包含：operation=other 时调用 子类重写用于添加数据
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void other(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

    protected final String getUTF8(HttpServletRequest req,String key) {
        try {
            String s = req.getParameter(key);
            if (s != null && !s.equals("")) {
                return URLDecoder.decode(new String(s.getBytes("ISO-8859-1"),
                        "UTF-8"), "UTF-8");
            } else {
                return "";
            }
        } catch (UnsupportedEncodingException se) {
            logger.error("UFT-8Error", se);
            return "";
        }
    }

    protected  Long getCurUserId(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object userId = session.getAttribute("userID");
        if(userId!=null && userId instanceof Long) {
            return (Long) userId;
        }
        return new Long(-2);
    }

}
