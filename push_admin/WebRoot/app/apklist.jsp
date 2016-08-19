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
        Index.SetTitle('Application Management');
        
        //判断是否已经登录
        if(J.isNullOrEmpty("${sessionScope.username}")) {
			Login.Tip.ReLogin(function() {
				parent.window.focus();
				parent.window.location.href="${pageContext.request.contextPath}/login.jsp";
			});
		}
        
        // 获取包含当前查询条件的url
        function getPagerUrl() {
        	var pagerUrl = "app/appManagementServlet?1=1";
            if(!J.isNullOrEmpty("${requestScope.name}")) {
              	pagerUrl += "&name="+encodeURI(encodeURI('${requestScope.name}')) +"&type1="+encodeURI(encodeURI('${requestScope.type1}'))+
              	"&type2="+encodeURI(encodeURI('${requestScope.type2}'));
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
 					$.ajax({
				           cache:true,
				           type: "POST",
				           url:"/padm/app/appManagementServlet",
				           data:{"ids":id,"operation":"delete"},
				           dataType:"text",
				           async:false,
				           timeout:8000,
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
        
        function showType(){
		    $("#type2").html("");
		    $("#type2").append("<option value=''>--请选择--</option>");
		    var countryName = $("#type1").trim();
		    <c:forEach items="${typeMap}" var="country">
		    if('${country.key}'==countryName){
		    <c:forEach items="${country.value}" var="addr">
		    if('${addr.key}'=='${type2}'){
		    $("#type2").append("<option value='${addr.key}' selected='selected'>${addr.key}</option>");
		    }else{
		    $("#type2").append("<option value='${addr.key}'>${addr.key}</option>");
		    }
		    </c:forEach>
		    }
		    </c:forEach>
		}
		
		$(function() {
		showType();
			$("#search").click(function() {
				var name = $('#name').trim();
            	var pageSize = $('#pageSize').trim();
            	var type1 = $('#type1').trim();
            	var type2 = $('#type2').trim();
            	var url = 'app/appManagementServlet';
            	url = J.FormatString(url + '?name={0}&pageSize={1}&type1={2}&type2={3}', encodeURI(encodeURI(name)), pageSize,
            	encodeURI(encodeURI(type1)),encodeURI(encodeURI(type2)));
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
				           url:"/padm/app/appManagementServlet",
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
    <div class="location">Current Location：Application Push -&gt;Application Management</div>
    
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
                    <label class="first txt-green">Name：</label>
                    <input value="${requestScope.name }" type="text" name="name" id="name" class="input-small" />
                    <label class="first txt-green">Type：</label>
                    <select name="type1" id="type1" onchange="showType()" class="small" style="width: auto">
                    <option value="">--请选择--</option>
	                <c:forEach items="${typeMap}" var="type">
	                <option value="${type.key}" <c:if test="${type.key==type1}">selected="selected"</c:if>>${type.key}</option>
	                </c:forEach>
                    </select> 
                    <select name="type2" id="type2" class="small" style="width: auto">
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
            <h3>User List</h3>
            <div class="bar" style="display: block;">
                <a class="btn-lit" href="javascript:Index.Open('app/appManagementServlet?operation=other&pageCount=${pagerInfo.pageCount }');"><span>Add</span></a>
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
                            <th scope="col">Name</th>
                            <th scope="col">Package Name</th>
                            <th scope="col">Type</th>
                            <th scope="col">Channel</th>
                            <th scope="col">Version Code</th>
                            <th scope="col">Version name</th>
                          	<th scope="col">Modify</th>
                            <th scope="col">Delete</th>
                        </tr>
                        <c:forEach items="${beanList }" var="userinfo">
                        <tr>
			              <td class="chk">
			              <c:if test="${userinfo.status!=1 }">
			              <input value="true" type="checkbox" rel="${userinfo.id }" name="CheckBox" class="check-box" /><input value="false" type="hidden" name="CheckBox" />
			              </c:if>
			              </td>
			              
			              <td class="txt c">
			              	<a href="javascript:Index.Open('app/appManagementServlet?'+J.getParam('${userinfo }'));" title="Detail">
			              		${userinfo.name }
			              	</a>	
			              </td>
			              
			              <td class="txt100 c">${userinfo.packageName }</td>
			              <td class="txt100 c">${userinfo.type}</td>
			              <td class="txt100 c">${userinfo.channel }</td>
			              <td class="txt100 c">${userinfo.versionCode }</td>
			              <td class="txt100 c">${userinfo.versionName }</td>
			              <td class="icon">
			              <c:if test="${userinfo.status!=1 }">
			              <a class="opt" title="Modify" href="javascript:Index.Open('app/appManagementServlet?operation=other&'+J.getParam('${userinfo }'));"><span class="icon-sprite icon-edit"></span></a>
			              </c:if>
			              </td>
                          <td class="icon tail">
                          <c:if test="${userinfo.status!=1 }">
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