package com.push.admin.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.push.admin.dbbean.TProject;
import com.push.admin.util.DBUtils;

public class ProjectManagementServlet extends BasePushServlet {

	private static final long serialVersionUID = 1L;
	@Override
	protected void query(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// get params
        String project = getUTF8(req,"project");
        String customer = getUTF8(req,"customer");
        String order = getUTF8(req,"order");
        String da = getUTF8(req,"da");
        
        String BeginDate = getUTF8(req,"BeginDate");
        String EndDate = getUTF8(req,"EndDate");
        
        String searchType = getUTF8(req,"searchType");        
		
        // search sql
		String selectStr = "select product,customer,model,version,sum(numbers) as numbers";
		String whereStr = "where 1=1 ";
		String otherStr = "group by product,customer,model,version";

        if(isEmpty(searchType))
        	searchType = "1";
        switch(Integer.parseInt(searchType)) {
	        case 1: // Project->Customer->Model->Versions->Numbers
	        	break;
	        case 2: // Project->Customer->Model->Numbers
	        	selectStr = "select product,customer,model,sum(numbers) as numbers";
	        	otherStr = "group by product,customer,model";
	        	break;
	        case 3: // Project->Customer->Numbers
	        	selectStr = "select product,customer,sum(numbers) as numbers";
	        	otherStr = "group by product,customer";
	        	break;
	        case 4: // Project->Numbers
	        	selectStr = "select product,sum(numbers) as numbers";
	        	otherStr = "group by product";
	        	break;
	        case 5: // Customer->Numbers
	        	selectStr = "select customer,sum(numbers) as numbers";
	        	otherStr = "group by customer";
	        	break;
        }
        
		// where sql
		if(!isEmpty(project)) {
			whereStr += " and product like '%" + project + "%'";
		}
		if(!isEmpty(customer)) {
			whereStr += " and customer like '%" + customer + "%'";
		}
		if(!isEmpty(BeginDate)) {
			whereStr += " and register_date >='" + BeginDate + "'";
		}
		if(!isEmpty(EndDate)) {
			whereStr += " and register_date <= '" + EndDate + " 59:59:59.999'";
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
		List<TProject> list = DBUtils.query(selectStr, whereStr, otherStr, new ArrayList<String>(), TProject.class, pagerInfo);
		
		// set params
		req.setAttribute("pagerInfo", pagerInfo);
		req.setAttribute("beanList", list);
        req.setAttribute("order", order);
        req.setAttribute("project", project);
        req.setAttribute("customer", customer);
        req.setAttribute("da", da);
        req.setAttribute("BeginDate", BeginDate);
        req.setAttribute("EndDate", EndDate);
        req.setAttribute("searchType", searchType);
		
		req.getRequestDispatcher("projectlistTotal.jsp").forward(req, resp);
	}
	
	/* (non-Javadoc)
	 * @see com.push.admin.servlet.BasePushServlet#other(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String selection = null;
		List<String> args = new ArrayList<String>();
		StringBuilder sb = new StringBuilder("1=1");
        String project = getUTF8(req,"project");
        String customer = getUTF8(req,"customer");
        String version = project+"_"+customer;
        if(!isEmpty(project)||!isEmpty(customer)){
            sb.append(" and version like ?");
            args.add("%"+version+"%");
        }
		selection = sb.toString();
        List<TProject> beanList;
        String order = getUTF8(req,"order");
        String da = getUTF8(req,"da");
        String ord = "";
        if(!order.equals("")){
            if(da.equals("2")){
                ord = order + " desc";
            }else {
                ord = order + " asc";
            }
            beanList = DBUtils.query(selection, args, ord, TProject.class, pagerInfo);
        }else {
            beanList = DBUtils.query(selection, args, "register_date desc", TProject.class, pagerInfo);
        }
		req.setAttribute("pagerInfo", pagerInfo);
		req.setAttribute("beanList", beanList);
        req.setAttribute("order", order);
        req.setAttribute("project", project);
        req.setAttribute("customer", customer);
        req.setAttribute("da", da);
		req.getRequestDispatcher("projectlist.jsp").forward(req, resp);
	}
	
}
