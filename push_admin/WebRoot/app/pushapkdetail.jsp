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
    	
    	function getPagerUrl() {
        	var pagerUrl = "app/pushApkHisServlet?1=1";
            if(!J.isNullOrEmpty("${requestScope.pushId}")) {
              	pagerUrl += "&pushId=${requestScope.pushId}&id=${param.id}";
            }
            pagerUrl += "&pageIndex={0}&pageSize={1}";
            return pagerUrl;
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
                            <th scope="row">Title：</th>
                            <td style="font-weight: bold" colspan="3">${pushApk.title }</td>   

                            <th scope="row">Icon：</th>
                            <td style="font-weight: bold" colspan="1">
                            <img id="showIcon"  src="${pageContext.request.contextPath}/${apk.icon}" height="48px" width="48px"/>
                            </td>    
                        </tr>
                        <tr>
                            <th scope="row">Comments：</th>
                            <td style="font-weight: bold" colspan="4">${pushApk.comments }</td>               
                        </tr>
                        <tr>
                            <th scope="row">Apk Name：</th>
                            <td style="font-weight: bold" colspan="3">${apk.name }</td>  
                            
                            <th scope="row">Version Name：</th>
                            <td style="font-weight: bold">${pushApk.apkVersionName }</td>    
                            
                            <th scope="row">Install Type：</th>
                            <td style="font-weight: bold">${type}</td>    
                        </tr>
                        <tr>
                            <th scope="row">IMEI：</th>
                            <td style="font-weight: bold" colspan="4">${pushApk.imei1 }~${pushApk.imei2 }</td>     
                        </tr>
                        <tr>
                            <th scope="row">Project Name：</th>
                            <td style="font-weight: bold" colspan="4">${pushApk.projectName }</td> 
                        </tr>
                        <tr>
                            <th scope="row">Customer Name：</th>
                            <td style="font-weight: bold" colspan="4">${pushApk.customerName }</td>  
                        </tr>
                        <tr>   
                            <th scope="row">Country Name：</th>
                            <td style="font-weight: bold" colspan="4">${pushApk.countryName }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Model Name：</th>
                            <td style="font-weight: bold" colspan="4">${pushApk.modelName }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Push Date：</th>
                            <td style="font-weight: bold">${pushApk.pushDate }</td>    

                            <th scope="row">Update Date：</th>
                            <td style="font-weight: bold" colspan="3">${pushApk.updateDate }</td>    
                        </tr>
                        <tr>
                            <th scope="row">Count Sended：</th>
                            <td style="font-weight: bold">
                            <c:choose>
                                <c:when test="${empty pushApk.countSended}">
                                	0
                                </c:when>
                                <c:otherwise>
                                  ${pushApk.countSended }
                                </c:otherwise>
                                </c:choose>
                            </td>  

                            <th scope="row">Count Downloaded：</th>
                            <td style="font-weight: bold">
                             <c:choose>
                                <c:when test="${empty pushApk.countDownloaded}">
                                	0
                                </c:when>
                                <c:otherwise>
                                  ${pushApk.countDownloaded }
                                </c:otherwise>
                                </c:choose>
                            </td>   

                            <th scope="row">Count Installed：</th>
                            <td style="font-weight: bold">
                            <c:choose>
                                <c:when test="${empty pushApk.countInstalled}">
                                	0
                                </c:when>
                                <c:otherwise>
                                  ${pushApk.countInstalled }
                                </c:otherwise>
                                </c:choose>
                            </td>    

                            <th scope="row">Count Deleted：</th>
                            <td style="font-weight: bold">
                            <c:choose>
                                <c:when test="${empty pushApk.countDeleted}">
                                	0
                                </c:when>
                                <c:otherwise>
                                  ${pushApk.countDeleted }
                                </c:otherwise>
                                </c:choose>
                            </td>    
                        </tr>
                        <!-- 
                        <tr>
                            <th scope="row">Update Comments：</th>
                            <td valign="top" style="font-weight: bold;word-wrap:break-word;">${pushApk.updateComments }</td>  
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