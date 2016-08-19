<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.push.admin.dwr.Dwr" %>
<%@ page import="com.push.admin.util.DBUtils" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="rgk://com.push.admin.tags/RgkTag" prefix="rgk" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
		Object userId = session.getAttribute("userId");
		if(userId!=null && userId instanceof Long) {
			request.setAttribute("permissions", new Dwr().getPermissions((Long) userId));
		}else{
		    request.setAttribute("permissions", new Dwr().getPermissions(-1l));
		}
 %>
<head>
    <title>PUSH - Background Management System</title>
    <link href="css/admin.global.css" rel="stylesheet" type="text/css" />
    <link href="css/admin.index.css" rel="stylesheet" type="text/css" />
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>
    
    <script type='text/javascript' src='${pageContext.request.contextPath }/dwr/util.js'></script> 
	<script type='text/javascript' src='${pageContext.request.contextPath }/dwr/engine.js'></script> 
	<script type='text/javascript' src='${pageContext.request.contextPath }/dwr/interface/dwr.js'> </script>
	
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="js/jquery.utils.js"></script>
    <link href="jBox/Skins/Green/jbox.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="jBox/jquery.jBox-2.3.min.js"></script>
    <script type="text/javascript" src="js/admin.js"></script>
    <script type="text/javascript">
        // 初始化下面的变量
        Admin.IsIndexPage = true;
        Admin.IndexStartPage = 'welcome.jsp';
        Admin.LogoutUrl = 'login.jsp';
       	// window.onload=function (){window.resizeTo(1024,600);}
       	//判断是否已经登录
        if(J.isNullOrEmpty("${sessionScope.username}")) {
			Login.Tip.ReLogin(function() {
				window.location.href="${pageContext.request.contextPath}/login.jsp";
			});
		}
		function logOff() {
			jBox.confirm('You sure you want to log off?', 'Prompt', function (v, h, f) {
        		if (v == 'ok') {
        	  		dwr.resetLogin({
						callback: function() {
							window.location.href="${pageContext.request.contextPath}/login.jsp";
						},
						async:false
					});
        		}
        		return true;
   			});
		}
		function checkPermission() {
			//alert();
			return false;
		}
    </script>
</head>
<body>
<div id="header">
    <div id="header-logo"><img src="images/logo.gif" alt="logo" width="59" height="64" border="0" /></div>
    <div id="header-title">PUSH -  Background Management System</div>
    <div id="header-info">
        <span style="margin-right:50px;color:#fff;">User：<b>${sessionScope.username }</b> &nbsp;,Hello,Welcome to use the system!</span>
        <span style="margin-right:50px;color:#fff;">Version：1.3</span>
        <a href="javascript:Index.Open('welcome.jsp');" style="margin-right:10px;color:#fff; font-weight:bold;">Home</a>
        <a href="javascript:logOff();" style="margin-right:10px;color:#fff; font-weight:bold;">Logoff</a>
    </div>
</div><!--//#header-->

<div id="main">
	<div id="left">
        <div id="menu-container">
          <c:forEach items="${permissions}" var="permission1">
            <c:if test="${permission1.parent==null||permission1.parent==0}">
					<div class="menu-tit">
						<b>${permission1.perName}</b>
					</div>
					<div class="menu-list hide">
						<div class="top-line"></div>
						<ul class="nav-items">
						<c:forEach items="${permissions}" var="permission2">
                            <c:if test="${permission2.parent==permission1.id}">
							<li><a href="${permission2.url}" onclick="return checkPermission();" target="content">${permission2.perName}</a></li>
							</c:if>
							</c:forEach>
						</ul>
					</div>
			</c:if>
		  </c:forEach>	
					
					
          <c:if test="${rgk:len(userpermissions)<=0}">
          <div class="menu-tit"><b>No Management</b></div>
          </c:if>
          
          
        </div>
        <div id="menu-bottom"></div>
    </div><!--//#left-->
    <div id="right">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td class="index-table-top-left"></td>
                <td class="index-table-top-center">
                    <div class="index-table-welcome-left"></div>
                    <div class="index-table-welcome-center" id="index-title">欢迎登录</div>
                    <div class="index-table-welcome-right"></div>
                    <div class="clear"></div>
                </td>
                <td class="index-table-top-right"></td>
            </tr>
            <tr>
                <td class="index-table-middle-left"></td>
                <td class="index-table-middle-center" valign="top" id="content-container" >
                    <div id="loading"><img src="images/loading.gif" alt="loading" border="0" /></div>
                    <script type="text/javascript">
                        Index.OutputIframe();
                    </script>
                </td>
                <td class="index-table-middle-right"></td>
            </tr>
            <tr>
                <td class="index-table-bottom-left"></td><td class="index-table-bottom-center"></td><td class="index-table-bottom-right"></td>
            </tr>
        </table>
    </div><!--//#right-->
    <div class="clear"></div>
</div><!--//#main-->

<div id="footer">
    <div id="footer-msg">Recommends using IE7 or later, or other mainstream browser,Resolution 1024x768 or higher。 | Copyright &copy; 2005-2012 www.ragentek.com All Rights Reserved.</div>
    <div id="footer-lang"><select style="height:19px; line-height:19px; margin-top:3px;" disabled="true"><option value="zh-cn">简体中文</option><option value="zh-tw">繁體中文</option><option value="en-us" selected="selected">English</option></select></div>
</div><!--//#footer-->
</body>

</html>