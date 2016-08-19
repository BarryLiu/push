package com.push.admin.servlet;

import com.push.admin.dbbean.*;
import com.push.admin.dwr.Result;
import com.push.admin.timer.TimerListner;
import com.push.admin.util.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dexin.su on 2014/11/13.
 */
public class PushCommandsHisServlet extends BasePushServlet {
    public static final String PARAM_NAME = "pushId";
    private static final long serialVersionUID = 1L;

    @Override
    protected void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pushId = req.getParameter(PARAM_NAME);
        String _sql = "select * from t_push_commands where id=" + pushId;
        List<VPushCommands> _beanLists = DBUtils.query(_sql, VPushCommands.class);
        if (_beanLists.size() > 0) {
            req.setAttribute("pushLink", _beanLists.get(0));
        } else {
            req.setAttribute("pushLink", new VPushCommands());
        }
        boolean pushIdEmpty = isEmpty(pushId);
        String selection = null;
        List<String> args = null;
        if(!pushIdEmpty) {
            StringBuilder sb = new StringBuilder("1=1");
            args = new ArrayList<String>();
            sb.append(" and push_id like '%"+pushId+"%'");
            selection = sb.toString();
        }
        resp.setContentType("text/html; charset=utf-8");

        List<VPushCommandsHis> beanList = DBUtils.query(selection, args, null, VPushCommandsHis.class, pagerInfo);

        req.setAttribute("pushId", pushId);
        req.setAttribute("pagerInfo", pagerInfo);
        req.setAttribute("beanList", beanList);
        req.getRequestDispatcher("pushcommandsdetail.jsp").forward(req, resp);
    }

    @Override
    protected void other(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (id != null) {
            String __sql = "select * from v_push_commands_his where id=" + id;
            List<VPushCommandsHis> __beanList = DBUtils.query(__sql, VPushCommandsHis.class);
            if (__beanList.size() > 0) {
                req.setAttribute("pushLinkHis", __beanList.get(0));
            }
        }
        req.getRequestDispatcher("pushcommandshisdetail.jsp").forward(req, resp);
    }
}
