package com.push.admin.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.push.admin.dbbean.TCountry;
import com.push.admin.dbbean.TProject;
import com.push.admin.util.DBUtils;

public class CountryCodeServlet extends BasePushServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void query(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String BeginDate = req.getParameter("BeginDate");
		String EndDate = req.getParameter("EndDate");

        String order = getUTF8(req,"order");
        String da = getUTF8(req,"da");

        // search sql
		String selectStr = "select name,sum(numbers) as numbers,min(register_date) as register_date, max(update_date) as update_date ";
		String whereStr = "where 1=1 ";
		String otherStr = "group by name";
		
//		String sql = "select name,sum(numbers) as numbers,min(register_date) as register_date, max(update_date) as update_date  from t_country ";
		
		if(!isEmpty(BeginDate)) {
			whereStr += "and register_date>='" + BeginDate + "' ";
		}
		if(!isEmpty(EndDate)) {
			whereStr += "and register_date<='" + EndDate + " 59:59:59.999' ";
		}

		// order sql
		if(!isEmpty(order)) {
			if(da.equals("2")){
				otherStr += " order by " + order + " desc";
            }else {
            	otherStr += " order by " + order + " asc";
            }
		}		

		// do search
		List<TCountry> list = DBUtils.query(selectStr, whereStr, otherStr, new ArrayList<String>(), TCountry.class, pagerInfo);
		

		req.setAttribute("pagerInfo", pagerInfo);
		req.setAttribute("beanList", list);
		req.setAttribute("BeginDate", BeginDate);
		req.setAttribute("EndDate", EndDate);
        req.setAttribute("order", order);
        req.setAttribute("da", da);
		
	    req.getRequestDispatcher("countrylistTotal.jsp").forward(req, resp);	
	}

	/* (non-Javadoc)
	 * @see com.push.admin.servlet.BasePushServlet#other(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String BeginDate = req.getParameter("BeginDate");
		String EndDate = req.getParameter("EndDate");
		
		String type = req.getParameter("type");
		String selection = null;
		List<String> args = new ArrayList<String>();
		StringBuilder sb = new StringBuilder("1=1");
		String countryName = req.getParameter("countryName");
		if (!isEmpty(countryName)) {
			countryName = getUTF8(countryName);
			sb.append(" and name = ?");
			args.add(countryName);
		}
		if(!isEmpty(BeginDate)) {
			sb.append(" and register_date>=? ");
			args.add(BeginDate);
		}
		if(!isEmpty(EndDate)) {
			sb.append(" and register_date<=?");
			args.add(EndDate + " 59:59:59.999");
		}
		
		selection = sb.toString();
		List<TCountry> beanList = DBUtils.query(selection, args, "register_date desc",
				TCountry.class, pagerInfo);
		req.setAttribute("pagerInfo", pagerInfo);
		req.setAttribute("beanList", beanList);
		
		req.setAttribute("countryName", countryName);
		req.setAttribute("BeginDate", BeginDate);
		req.setAttribute("EndDate", EndDate);
		if(isEmpty(type)){
		    req.getRequestDispatcher("countrylist.jsp").forward(req, resp);	
		}else{
			/*
			String sql = "select * from t_country";
			List<TCountry> beanList1 = DBUtils.query(sql, TCountry.class);
			Map<String,HashMap<String,String>> countryMap = new HashMap<String,HashMap<String,String>>();
			for(TCountry country:beanList1){
				if(countryMap.get(country.getName())==null){
				countryMap.put(country.getName(), new HashMap<String,String>());
				}
				countryMap.get(country.getName()).put(country.getArea(),country.getArea());
			}
			req.setAttribute("countryName", countryName);
			req.setAttribute("countryMap", countryMap);*/
			req.setAttribute("countryName", countryName);
			req.setAttribute("countryMap", DBUtils.getCountryMapWithArea());
			String ids = getUTF8(req.getParameter("ids"));
			req.setAttribute("ids", ids);
			req.getRequestDispatcher("../include/countrySelect.jsp").forward(req, resp);	
		}
	}	
}
