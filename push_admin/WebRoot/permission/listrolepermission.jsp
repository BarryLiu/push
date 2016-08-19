<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="rgk://com.push.admin.tags/RgkTag" prefix="rgk" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Role-Permission</title>
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
        Index.SetTitle('User-Role');
        
        //判断是否已经登录
        if(J.isNullOrEmpty("${sessionScope.username}")) {
			Login.Tip.ReLogin(function() {
				parent.window.focus();
				parent.window.location.href="${pageContext.request.contextPath}/login.jsp";
			});
		}
		
		function getUrl() {
			var roleId = $("#roles").val();
			if(J.isNullOrEmpty(roleId)) {
				return "rolePermission";
			} else {
				return "rolePermission?id="+roleId;	
			}
		}
		
		function changeChild(obj){
		objs = document.getElementsByName(obj.name);
		var n=0;
		for(var i=0;i<objs.length;i++){
		if(objs[i].checked==true){
		n++;
		}
		}
		obj1=document.getElementById(obj.name);
		if(n>0){
		obj1.checked=true;
		}else{
		obj1.checked=false;
		}
		}
		
		function changeParent(obj){
		objs = document.getElementsByName(obj.id);
		if(obj.checked==true){
		for(var i=0;i<objs.length;i++){
		objs[i].checked=true;
		}
		}else{
		for(var i=0;i<objs.length;i++){
		objs[i].checked=false;
		}
		}
		}
		
		function assign(){
		var roleId = $("#roles").val();
		var pers="";
		var objs = $("[id^='check']");
		for(var i=0;i<objs.length;i++){
		if(objs[i].checked==true){
		pers+=objs[i].value +',';
		}
		}
		pers=pers.substr(0,pers.length-1);
		$.ajax({ 
              url:"${pageContext.request.contextPath}/rolePermission",
              data:{'roleid':roleId,'pers':pers,'operation':'other'}, 
              dataType:"text",
			  async:false,
              type:"POST", 
              success:function(json){ 
                            var res = J.toObject(json);
            				if(res.res == 3) {
            					Login.Success(res.msg, res.title, function() {
									parent.window.focus();
									parent.window.location.href="${pageContext.request.contextPath}/index.jsp";
								});
            				} else if(res.res == 1) {
            					Login.Tip(res.msg, 'success');
            					Index.Open(getUrl());
            				} else {
            					Login.Tip(res.msg, 'error');
            				}
              } 
           }); 
		}
		
		$(function() {
			<c:forEach items="${permissions2}" var="permission">
            $("#check"+"${permission.id}").setChecked(true);
            </c:forEach>
			$("#roles").change(function() {
				Index.Open(getUrl());
			});
			
		});
    </script>
</head>
<body>
<div class="container">
    <div class="location">Current Location：Permission Management -&gt;Role-Permission</div>
    
    <div class="blank10"></div>

    <div class="search block">
        <div class="h">
            <span class="icon-sprite icon-magnifier"></span>
            <h3>Select Role</h3>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt">
                <div class="search-bar">
                    <label class="first txt-green">Role Name：</label>
                    <select id="roles" name="roles" class="select150">
                    	<c:forEach items="${roleInfos}" var="role">
                    	<c:if test="${role.roleName!='超级管理员'}">
                    	<option value="${role.id }" <c:if test="${role.id==id }">selected</c:if> >${role.roleName }</option>
                    	</c:if>
                    	</c:forEach>
                    </select>
                </div>
            </div>
        </div>
    </div>
                
    <div class="blank10"></div>

    <div class="block">
        <div class="h" style="margin-left:30px;">
            <span class="icon-sprite icon-list"></span>
            <h3>Role-Permission</h3>
            <div class="bar" style="display: block;padding-right:60px;">
                <a class="btn-lit" id="assignPermissions" onclick="assign()" href="javascript:;"><span id="s_asg">Assign</span></a>     
            </div>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt">
            <c:forEach items="${permissions}" var="permission1">
            <c:if test="${permission1.parent==null||permission1.parent==0}">
            <input type="checkbox" id="check${permission1.id}" onchange="changeParent(this)" value="${permission1.id}" />
            <label>${permission1.perName}</label>
            <br/>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <c:forEach items="${permissions}" var="permission2">
            <c:if test="${permission2.parent==permission1.id}">
            <input type="checkbox" onchange="changeChild(this)" name="check${permission1.id}" id="check${permission2.id}" value="${permission2.id}"/>
            <label>${permission2.perName}</label>
            </c:if>
            </c:forEach>
            <br/>
            </c:if>
            </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>