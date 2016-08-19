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
    	function getPagerUrl() {
        	var pagerUrl = "commands/pushCommandsHisServlet?1=1";
            if(!J.isNullOrEmpty("${requestScope.pushId}")) {
              	pagerUrl += "&pushId=${requestScope.pushId}";
            }
            pagerUrl += "&pageIndex={0}&pageSize={1}";
            return pagerUrl;
        }
    </script>
</head>
<body>
<div class="container">

    <div class="location">Current Location：Link Push -&gt; For more information</div>
    
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
                            <th scope="row">Title：</th>
                            <td style="font-weight: bold">${pushLink.title }</td>                            
                        </tr>
                        <tr>
                            <th scope="row">Comments：</th>
                            <td style="font-weight: bold">${pushLink.comments }</td>               
                        </tr>
                        <tr>
                            <th scope="row">Commands：</th>
                            <td style="font-weight: bold">${pushLink.commands}</td>
                        </tr>
                        <tr>
                            <th scope="row">Imei：</th>
                            <td style="font-weight: bold">${pushLink.imei1}~${pushLink.imei2}</td>
                        </tr>
                        <tr>
                            <th scope="row">Country Name：</th>
                            <td style="font-weight: bold">${pushLink.countryName }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Project Name：</th>
                            <td style="font-weight: bold">${pushLink.projectName }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Customer Name：</th>
                            <td style="font-weight: bold">${pushLink.customerName }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Model Name：</th>
                            <td style="font-weight: bold">${pushLink.modelName }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Version Name：</th>
                            <td style="font-weight: bold">${pushLink.versionName }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Push Date：</th>
                            <td style="font-weight: bold">${pushLink.pushDate }</td>   
                            <th scope="row">Update Date：</th>
                            <td style="font-weight: bold">${pushLink.updateDate }</td>   
                        </tr>
                        <tr>
                            <th scope="row">Count Sended：</th>
                            <td style="font-weight: bold">
                            <c:choose>
                                <c:when test="${empty pushLink.countSended }">
                                	0
                                </c:when>
                                <c:otherwise>
                                  ${pushLink.countSended }
                                </c:otherwise>
                                </c:choose>
                            </td>    
                            <th scope="row">Count Clicked：</th>
                            <td style="font-weight: bold">
                            <c:choose>
                                <c:when test="${empty pushLink.countRunned }">
                                	0
                                </c:when>
                                <c:otherwise>
                                  ${pushLink.countRunned }
                                </c:otherwise>
                                </c:choose>
                            </td>  
                        </tr>
                        <!-- 
                        <tr>
                            <th scope="row">Update Comments：</th>
                            <td valign="top" style="font-weight: bold;word-wrap:break-word;">${pushLink.updateComments }</td>  
                        </tr>
                         -->
                    </tbody>
                </table>
                </form>
            </div>
        </div>
        <div class="cnt-wp">
            <div class="cnt">
                <table class="data-table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th scope="col">Uuid</th>
                            <th scope="col">Status</th>
                            <th scope="col">Push Date</th>
                            <th scope="col">Update Date</th>
                        </tr>
                        <c:forEach items="${beanList }" var="userinfo">
                        <tr>
			              <td class="txt100 c">
			              		${userinfo.uuid }
			              </td>
			              <td class="txt100 c">${userinfo.statusName }</td>
			              <td class="txt140 c">${userinfo.pushDate }</td>
			              <td class="txt140 c">${userinfo.updateDate }</td>
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