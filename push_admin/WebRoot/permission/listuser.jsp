<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="rgk://com.push.admin.tags/RgkTag" prefix="rgk" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>User Info</title>
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
        Index.SetTitle('User Info');
        
        //判断是否已经登录
        if(J.isNullOrEmpty("${sessionScope.username}")) {
			Login.Tip.ReLogin(function() {
				parent.window.focus();
				parent.window.location.href="${pageContext.request.contextPath}/login.jsp";
			});
		}
        
        // 获取包含当前查询条件的url
        function getPagerUrl() {
        	var pagerUrl = "user?1=1";
            if(!J.isNullOrEmpty("${requestScope.username}")) {
              	pagerUrl += "&username=${requestScope.username}";
            }
            pagerUrl += "&pageIndex={0}&pageSize={1}";
            return pagerUrl;
        }
        
        function getFormatPagerUrl() {
        	return J.FormatString(getPagerUrl(), "${pagerInfo.pageIndex}", "${pagerInfo.pageSize}");
        }

        // 根据ID删除信息
        function DeleteById(id) {
            jBox.confirm("The operation can't be restored,are you sure to delete?", "Delete information", function (v, h, f) {
                if (v == 'ok') {
                    Login.Tip('precessing...', 'loading');
 					// 在这ajax处理
 					dwr.deleteUserInfo(id,{callback:function (json){
 						var res = J.toObject(json);
 						if(res.res==2) { //Re-Login
 							Login.Success(res.msg, res.title, function() {
								parent.window.focus();
								parent.window.location.href="${pageContext.request.contextPath}/login.jsp";
							});
 						} else if(res.res == 1){
		                    Login.Tip(res.msg, 'success');
		                    Index.Open(getFormatPagerUrl());
                    	} else {
                    		Login.Tip(res.msg, 'error');
                    	}
                    },timeout:8000,async:false});                            
                }
                return true;
            });
        }
		
		$(function() {
			$("#search").click(function() {
				var username = $('#username').trim();
            	var pageSize = $('#pageSize').trim();

            	var url = 'user';
            	url = J.FormatString(url + '?username={0}&pageSize={1}', username, pageSize);
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
                    	dwr.deleteUserInfos(ids,function (json){
                    		var res = J.toObject(json);
                    		if(res.res==2) { //Re-Login
 								Login.Success(res.msg, res.title, function() {
									parent.window.focus();
									parent.window.location.href="${pageContext.request.contextPath}/login.jsp";
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
    <div class="location">Current Location：Permission Management -&gt;User Info</div>
    
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
                    <label class="first txt-green">UserName：</label>
                    <input value="${requestScope.username }" type="text" name="username" id="username" class="input-small" />
                    
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
            <h3>User List</h3>
            <div class="bar" style="display: block;">
                <a class="btn-lit" href="javascript:Index.Open('permission/userModify.jsp?pageCount=${pagerInfo.pageCount }');"><span>Add</span></a>
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
                            <th scope="col">User Name</th>
                            <th scope="col">Create Date</th>
                            <th scope="col">Update Date</th>
                          	<th scope="col">Modify</th>
                            <th scope="col">Delete</th>
                        </tr>
                        <c:forEach items="${beanList }" var="userinfo">
                        <tr>
			              <td class="chk">
			              <c:if test="${userinfo.username!='sys' }">
			              <input value="true" type="checkbox" rel="${userinfo.id }" name="CheckBox" class="check-box" /><input value="false" type="hidden" name="CheckBox" />
			              </c:if>
			              </td>
			              
			              <td class="txt c">
			              	<a href="javascript:Index.Open('permission/userDetail.jsp?'+encodeURI(encodeURI(J.getParam('${userinfo }'))));" title="Detail">
			              		${userinfo.username }
			              	</a>	
			              </td>
			              
			              <td class="txt200 c">${userinfo.createDate }</td>
			              <td class="txt200 c">${userinfo.updateDate }</td>
			              <td class="icon">
			              <c:if test="${userinfo.username!='sys' }">
			              <a class="opt" title="Modify" href="javascript:Index.Open('permission/userModify.jsp?'+encodeURI(encodeURI(J.getParam('${userinfo }'))));"><span class="icon-sprite icon-edit"></span></a>
			              </c:if>
			              </td>
                          <td class="icon tail">
                          <c:if test="${userinfo.username!='sys' }">
                          <a class="opt" title="Delete" href="javascript:DeleteById('${userinfo.id }');"><span class="icon-sprite icon-delete"></span></a>
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