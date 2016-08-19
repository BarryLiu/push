<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="rgk://com.push.admin.tags/RgkTag" prefix="rgk" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
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
        Index.SetTitle('Application Push');
        
        //判断是否已经登录
        if(J.isNullOrEmpty("${sessionScope.username}")) {
			Login.Tip.ReLogin(function() {
				parent.window.focus();
				parent.window.location.href="${pageContext.request.contextPath}/login.jsp";
			});
		}
        
        // 获取包含当前查询条件的url
        function getPagerUrl() {
        	var pagerUrl = "app/appPushServlet?1=1";
            pagerUrl += "&title="+encodeURI(encodeURI('${requestScope.title}'))+"&countryName="+
            encodeURI(encodeURI('${requestScope.countryName}'))+"&installType="+encodeURI(encodeURI('${requestScope.installType}'));
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
 					$.ajax({
				           cache:true,
				           type: "POST",
				           url:"/padm/app/appPushServlet",
				           data:{"ids":id,"operation":"delete"},
				           dataType:"text",
				           async:false,
				           error: function(request){
				                Login.Tip("An unknown error has occurred!", "error");
				           },
				           success: function(data){
				              var res = J.toObject(data);
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
				    }
			        });                       
                }
                return true;
            });
        }
        function showCity(){
		    $("#countryAddr").html("");
		    $("#countryAddr").append("<option value=''>-请选择-</option>");
		    var countryName = $("#countryName").trim();
		    <c:forEach items="${countryMap}" var="country">
		    if('${country.key}'==countryName){
		    <c:forEach items="${country.value}" var="addr">
		    if('${addr.key}'=='${requestScope.countryAddr}'){
		    $("#countryAddr").append("<option value='${addr.key}' selected='selected'>${addr.key}</option>");
		    }else{
		    $("#countryAddr").append("<option value='${addr.key}'>${addr.key}</option>");
		    }
		    </c:forEach>
		    }
		    </c:forEach>
		}
		
		$(function() {
			$("#search").click(function() {
				var title = $('#title').trim();
				var countryName = $('#countryName').trim();
				var installType = $('#installType').trim();
            	var pageSize = $('#pageSize').trim();
            	var url = 'app/appPushServlet';
            	url = J.FormatString(url + '?title={0}&pageSize={1}&countryName={2}&installType={3}', 
            	title, pageSize,countryName,installType);
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
                    	var id = "";
                	for(var i=0;i<ids.length;i++){
                	id+=ids[i]+",";
                	}
                    	$.ajax({
				           cache:true,
				           type: "POST",
				           url:"/padm/app/appPushServlet",
				           data:{"ids":id,"operation":"delete"},
				           dataType:"text",
				           async:false,
				           error: function(request){
				                Login.Tip("An unknown error has occurred!", "error");
				           },
				           success: function(data){
				              var res = J.toObject(data);
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
    <div class="location">Current Location：Application Push -&gt;Application Push</div>
    
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
                    <label class="first txt-green">Title：</label>
                    <input value="${requestScope.title }" type="text" name="title" id="title" class="input-small" />                   
                    <label class="txt-green">Country Name：</label>
                    <select name="countryName" id="countryName" class="small" style="width: auto">
	                <option value="">-请选择-</option>
	                <c:forEach items="${countryMap}" var="country">
	                <option value="${country.key}" <c:if test="${country.key==requestScope.countryName}">selected="selected"</c:if>>${country.key}</option>
	                </c:forEach>
                    </select> 
	                <label class="txt-green">Install Type：</label>
                    <select name="installType" id="installType" class="small" style="width: auto">
                    <option value="">-请选择-</option>
	                <option value="1" <c:if test="${installType==1}">selected="selected"</c:if>>静默安装</option>
	                <option value="2" <c:if test="${installType==2}">selected="selected"</c:if>>许可型安装</option>
                    </select> 
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
            <h3>Push Apk List</h3>
            <div class="bar" style="display: block;">
                <a class="btn-lit" href="javascript:Index.Open('app/appPushServlet?operation=other&pageCount=${pagerInfo.pageCount }');"><span>Add</span></a>
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
                            <th scope="col">Title</th>
                            <th scope="col">Name</th>
                            <th scope="col">Install Type</th>
                            <th scope="col">Count Sended</th>
                            <th scope="col">Count Downloaded</th>
                            <th scope="col">Count Installed</th>
                            <th scope="col">Count Deleted</th>
                            <th scope="col">Total</th>
                            <th scope="col">Push Date</th>
                          	<th scope="col">Modify</th>
                            <th scope="col">Delete</th>
                        </tr>
                        <c:forEach items="${beanList }" var="userinfo">
                        <tr>
			              <td class="chk">
			              <c:if test="${userinfo.canEdit }">
			              <input value="true" type="checkbox" rel="${userinfo.id }" name="CheckBox" class="check-box" /><input value="false" type="hidden" name="CheckBox" />
			              </c:if>
			              </td>
			              
			              <td class="txt c">
			              	<a href="javascript:Index.Open('app/pushApkHisServlet?pushId=${userinfo.id}');" title="Detail">
			              		${userinfo.title }
			              	</a>	
			              </td>
			              <td class="txt c">${userinfo.name }</td>
			              <td class="txt c">${userinfo.installTypeName }</td>
			              <td class="txt c">${userinfo.countSended }</td>
			              <td class="txt c">${userinfo.countDownloaded }</td>
			              <td class="txt c">${userinfo.countInstalled }</td>
			              <td class="txt c">${userinfo.countDeleted}</td>
			              <td class="txt c">${userinfo.countSended + userinfo.countDownloaded + userinfo.countInstalled + userinfo.countDeleted}</td>
			              <td class="txt c">${userinfo.pushDate}</td>
			              <td class="icon">
			              <c:if test="${userinfo.canEdit }">
			              <a class="opt" title="Modify" href="javascript:Index.Open('app/appPushServlet?operation=other&'+J.getParam('${userinfo }'));"><span class="icon-sprite icon-edit"></span></a>
			              </c:if>
			              </td>
                          <td class="icon tail">
                          <!-- 
                          <c:if test="${userinfo.canEdit }">
                          <a class="opt" title="Delete" href="javascript:DeleteById('${userinfo.id }');"><span class="icon-sprite icon-delete"></span></a>
                          </c:if> -->
                          <a class="opt" title="Delete" href="javascript:DeleteById('${userinfo.id }');"><span class="icon-sprite icon-delete"></span></a>
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