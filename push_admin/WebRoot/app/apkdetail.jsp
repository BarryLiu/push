<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="rgk://com.push.admin.tags/RgkTag" prefix="rgk" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <title>User Info</title>
    <link href="${pageContext.request.contextPath }/css/admin.global.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath }/css/admin.content.css" rel="stylesheet" type="text/css" />
    
     <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/js/QQcalendar/calendar.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/QQcalendar/calendar.js" ></script>  
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/QQcalendar/calendar-en.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/QQcalendar/calendar-setup.js"></script>
    
    <script type='text/javascript' src='${pageContext.request.contextPath }/dwr/util.js'></script> 
	<script type='text/javascript' src='${pageContext.request.contextPath }/dwr/engine.js'></script> 
	<script type='text/javascript' src='${pageContext.request.contextPath }/dwr/interface/dwr.js'> </script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.utils.js"></script>
    <link href="${pageContext.request.contextPath }/jBox/Skins/Green/jbox.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath }/jBox/jquery.jBox-2.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/admin.js"></script>
    <script type="text/javascript">
   	 	Index.SetTitle('For more information');
    	if(J.isNullOrEmpty("${sessionScope.username}")) {
    		Login.Tip.ReLogin(function() {
				parent.window.focus();
    			parent.window.location.href="${pageContext.request.contextPath}/login.jsp";
			});
    	}
    </script>
</head>
<body>
<div class="container">

    <div class="location">Current Location：Application Push -&gt; For more information</div>
    
    <div class="blank10"></div>

    <div class="block">
        <div class="h">
            <span class="icon-sprite icon-list"></span>
            <h3> PUSH-For more information</h3>
            <div class="bar">
                <a class="btn-lit" href="javascript:window.history.back();"><span>Return</span></a>
            </div>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt form">
                <form>
                <table class="data-form" cellspacing="0" cellpadding="0">
                    <tbody>
                    	<tr>
                            <th scope="row">Name：</th>
                            <td style="font-weight: bold">${apk.name }</td>                            
                        </tr>
                        <tr>
                            <th scope="row">Package Name：</th>
                            <td style="font-weight: bold">${apk.packageName }</td>               
                        </tr>
                        <tr>
                            <th scope="row">Icon：</th>
                            <td style="font-weight: bold">
                            <img id="showIcon"  src="${pageContext.request.contextPath}/${apk.icon}" height="72px" width="72px"/>
                            </td>    
                        </tr>
                        <tr>
                            <th scope="row">Type：</th>
                            <td style="font-weight: bold">${apk.type }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Description：</th>
                            <td style="font-weight: bold">${apk.description }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Channel：</th>
                            <td style="font-weight: bold">${apk.channel }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Size：</th>
                            <td style="font-weight: bold">${apk.size }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Version Code：</th>
                            <td style="font-weight: bold">${apk.versionCode }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Version Name：</th>
                            <td style="font-weight: bold">${apk.versionName }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Url：</th>
                            <td style="font-weight: bold">${apk.url }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Status：</th>
                            <td style="font-weight: bold">${apk.status }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Create Date：</th>
                            <td style="font-weight: bold">${apk.createDate }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Update Date：</th>
                            <td style="font-weight: bold">${apk.updateDate }</td>    
                        </tr>
                    </tbody>
                </table>
                </form>
            </div>
        </div>
    </div>

</div>
</body>
</html>