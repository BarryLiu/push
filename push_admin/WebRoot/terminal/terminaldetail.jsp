<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="rgk://com.push.admin.tags/RgkTag" prefix="rgk" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="Author" content="dexin.su@ragentek.com" />
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
                            <th scope="row">Phone Numer1：</th>
                            <td style="font-weight: bold">${client.phoneNumber1 }</td>                            
                        </tr>
                        <tr>
                            <th scope="row">Phone Numer2：</th>
                            <td style="font-weight: bold">${client.phoneNumber2 }</td>               
                        </tr>
                        <tr>
                            <th scope="row">Imei：</th>
                            <td style="font-weight: bold">${client.imei }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Device Project：</th>
                            <td style="font-weight: bold">${client.deviceProduct }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Device Customer：</th>
                            <td style="font-weight: bold">${client.deviceCustomer }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Device Model：</th>
                            <td style="font-weight: bold">${client.deviceModel }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Device Version：</th>
                            <td style="font-weight: bold">${client.deviceVersion }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Client Bin Version：</th>
                            <td style="font-weight: bold">${client.clientBinVersion }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Client Apk Version：</th>
                            <td style="font-weight: bold">${client.clientApkVersion }</td>    
                        </tr>
                         <tr>
                            <th scope="row">Retry：</th>
                            <td style="font-weight: bold">${client.retry }</td>    
                        </tr>
                         <tr>
                            <th scope="row">Timestamp：</th>
                            <td style="font-weight: bold">${client.timestamp }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Ip：</th>
                            <td style="font-weight: bold">${client.ip }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Country Name：</th>
                            <td style="font-weight: bold">${client.countryName }</td>    
                        </tr>
                        <tr>
                            <th scope="row">language：</th>
                            <td style="font-weight: bold">${client.language }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Register Date：</th>
                            <td style="font-weight: bold">${client.registerDate }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Update Date：</th>
                            <td style="font-weight: bold">${client.updateDate }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Update Comments：</th>
                            <td valign="top" style="font-weight: bold;word-wrap:break-word;">${client.updateComments }</td>  
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