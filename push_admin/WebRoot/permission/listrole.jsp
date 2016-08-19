<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="rgk://com.push.admin.tags/RgkTag" prefix="rgk" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Role Info</title>
    <style type="text/css">
        .div { table-layout: fixed; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; width: 160px; }
    </style>
    
    <link href="${pageContext.request.contextPath }/css/admin.global.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath }/css/admin.content.css" rel="stylesheet" type="text/css" />
    
    <script type='text/javascript' src='${pageContext.request.contextPath }/dwr/util.js'></script> 
	<script type='text/javascript' src='${pageContext.request.contextPath }/dwr/engine.js'></script> 
	<script type='text/javascript' src='${pageContext.request.contextPath }/dwr/interface/dwr.js'> </script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.utils.js"></script>
    <link href="${pageContext.request.contextPath }/jBox/Skins/Green/jbox.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath }/jBox/jquery.jBox-2.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/admin.js"></script>
    <script type="text/javascript">
    
        // 设置标题
        Index.SetTitle('Role Info');
        
        //判断是否已经登录
        if(J.isNullOrEmpty("${sessionScope.username}")) {
			Login.Tip.ReLogin(function() {
				parent.window.focus();
				parent.window.location.href="${pageContext.request.contextPath}/login.jsp";
			});
		}
        
        // 获取包含当前查询条件的url
        function getPagerUrl() {
        	var pagerUrl = "role?order="+$("#order").trim()+"&da="+$("#order").trim();
            if(!J.isNullOrEmpty("${requestScope.roleName}")) {
              	pagerUrl += "&roleName="+encodeURI(encodeURI('${requestScope.roleName}'));
            }
            pagerUrl += "&pageIndex={0}&pageSize={1}";
            return pagerUrl;
        }
        
        function getFormatPagerUrl() {
        	return J.FormatString(getPagerUrl(), "${pagerInfo.pageIndex}", "${pagerInfo.pageSize}");
        }

        function order(name,da){
            $("#order").val(name);
            $("#da").val(da);
            var roleName = $('#roleName').trim();
            var pageSize = $('#pageSize').trim();
            var url = "role?order="+$("#order").trim()+"&da="+$("#da").trim();
            url = J.FormatString(url + '&roleName={0}&pageSize={1}',encodeURI(encodeURI(roleName)), pageSize);
            Index.Open(url);
        }

        // 根据ID删除信息
        function DeleteById(id) {
            jBox.confirm("The operation can't be restored,are you sure to delete?", "Delete information", function (v, h, f) {
                if (v == 'ok') {
                    Login.Tip('precessing...', 'loading');
 					// 在这ajax处理
 					dwr.deleteRoleInfo(id,function (json){
 						var res = J.toObject(json);
 						if(res.res == 3) { //Refresh
 							Login.Success(res.msg, res.title, function() {
								parent.window.focus();
								parent.window.location.href="${pageContext.request.contextPath}/index.jsp";
							});
 						} else if(res.res == 1){
		                    Login.Tip(res.msg, 'success');
		                    Index.Open(getFormatPagerUrl());
                    	} else {
                    		Login.Tip(res.msg, 'error');
                    	}
                    });                            
                }
                return true;
            });
        }
		
		$(function() {
			$("#search").click(function() {
				var roleName = $('#roleName').trim();
            	var pageSize = $('#pageSize').trim();

            	var url = "role?order="+$("#order").trim()+"&da="+$("#order").trim();
            	url = J.FormatString(url + '&roleName={0}&pageSize={1}',encodeURI(encodeURI(roleName)), pageSize);
            	Index.Open(url);
			});
			$("#deleteMulti").click(function(){
				var ids = CheckBox.GetCheckedIds();
            	if (ids.length == 0) {
                	Login.Tip("Please select one information at least！");
                	return;
            	}
            	jBox.confirm("The operation can't be restored,are you sure to delete the selected information?", "Delete information", function (v, h, f) {
                	if (v == 'ok') {
                    	Login.Tip('precessing...', 'loading');					
                    	// 在这ajax处理
                    	dwr.deleteRoleInfos(ids,function (json){
                    		var res = J.toObject(json);
                    		if(res.res == 3) {
                    			Login.Success(res.msg, res.title, function() {
									parent.window.focus();
									parent.window.location.href="${pageContext.request.contextPath}/index.jsp";
								});
                    		} else if(res.res == 1){
		                    	Login.Tip(res.msg, 'success');
		                    	Index.Open(getFormatPagerUrl());
                    		} else {
                    			Login.Tip(res.msg, 'error');
                    		}
                    	});
                    }
                	return true;
            	});
			});
		});
    </script>
</head>
<body>
<div class="container">
    <div class="location">Current Location：Permission Management -&gt;Role Info</div>
    
    <div class="blank10"></div>

    <div class="search block">
        <div class="h">
            <span class="icon-sprite icon-magnifier"></span>
            <h3>Quick Search</h3>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt">
                <div class="search-bar">
                    <label class="first txt-green">Role Name：</label>
                    <input value="${requestScope.roleName }" type="text" name="roleName" id="roleName" class="input-small" />
                    <input type="hidden" name="order" id="order" value="${order}"/>
                    <input type="hidden" name="da" id="da" value="${da}"/>
                    <label class="txt-green">Per Page：</label>
                    <select name="pageSize" id="pageSize" class="small">
	                    <option value="10" <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 10)}">selected="selected"</c:if>>10</option>
	                    <option value="12" <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 12)}">selected="selected"</c:if>>12</option>
	                    <option value="15" <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 15)}">selected="selected"</c:if>>15</option>
                    </select>
                    <label><a id="search" class="btn-lit btn-middle" href="javascript:;"><span>Search</span></a></label>
                </div>
            </div>
        </div>
    </div>
                
    <div class="blank10"></div>

    <div class="block">
        <div class="h">
            <span class="icon-sprite icon-list"></span>
            <h3>Role List</h3>
            <div class="bar" style="display: block;">
                <a class="btn-lit" href="javascript:Index.Open('permission/roleModify.jsp?pageCount=${pagerInfo.pageCount }');"><span>Add</span></a>
                <a class="btn-lit" id="deleteMulti" href="javascript:;"><span>Delete item</span></a>     
            </div>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt">
                <table class="data-table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th scope="col"><input value="true" type="checkbox" title="Select/Don't choose" onclick="CheckBox.CheckAll(this);" name="CheckAll" id="CheckAll" /><input value="false" type="hidden" name="CheckAll" /></th>
                            <th scope="col">Role Name<a  href="javascript:" onclick="order('role_name','1')"><img src="../images/up.png" alt=""/></a><a  href="javascript:" onclick="order('role_name','2')"><img src="../images/down.png" alt=""/></a></th>
                            <th scope="col">Create Date<a href="javascript:" onclick="order('create_date','1')"><img src="../images/up.png" alt=""/></a><a href="javascript:" onclick="order('create_date','2')"><img src="../images/down.png" alt=""/></a></th>
                            <th scope="col">Update Date<a href="javascript:" onclick="order('update_date','1')"><img src="../images/up.png" alt=""/></a><a href="javascript:" onclick="order('update_date','2')"><img src="../images/down.png" alt=""/></a></th>
                            <!-- <th scope="col">Update Comments</th> -->
                          	<th scope="col">Modify</th>
                            <th scope="col">Delete</th>
                        </tr>
                        <c:forEach items="${beanList }" var="bean">
                        <tr>
			              <td class="chk">
			              <c:if test="${bean.roleName!='超级管理员' }">
			              <input value="true" type="checkbox" rel="${bean.id }" name="CheckBox" class="check-box" />
			              <input value="false" type="hidden" name="CheckBox" />
			              </c:if>
			              </td>
			              
			              <td class="txt c">
			              	<a href="javascript:Index.Open('permission/roleDetail.jsp?'+encodeURI(encodeURI(J.getParam('${bean }'))));" title="Detail">
			              		${bean.roleName }
			              	</a>	
			              </td>
			              
			              <td class="txt200 c">${bean.createDate }</td>
			              <td class="txt200 c">${bean.updateDate }</td>
			              <!-- <td class="txt200 c" style="word-wrap:break-word;">${bean.updateComments }</td> -->
			              <td class="icon">
			              <c:if test="${bean.roleName!='超级管理员' }">
			              <a class="opt" title="Modify" href="javascript:Index.Open('permission/roleModify.jsp?'+encodeURI(encodeURI(J.getParam('${bean }'))));"><span class="icon-sprite icon-edit"></span></a>
			              </c:if>
			              </td>
                          <td class="icon tail">
                          <c:if test="${bean.roleName!='超级管理员' }">
                          <a class="opt" title="Delete" href="javascript:DeleteById('${bean.id }');"><span class="icon-sprite icon-delete"></span></a>
                          </c:if>
                          </td>
			            </tr>
			            </c:forEach>
                                    
                    </tbody>
                </table>
            </div>
                        
            <script type="text/javascript">
              Pager.Output(getPagerUrl(), "${pagerInfo.pageSize}", "${pagerInfo.pageIndex}", "${pagerInfo.pageCount}", "${pagerInfo.recordCount}"); //(urlFormat, pageSize, pageIndex, pageCount, recordCount)
            </script>
        </div>
    </div>
</div>
</body>
</html>