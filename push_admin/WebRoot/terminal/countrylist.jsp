<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="Author" content="dexin.su@ragentek.com" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <title>Upgrade Management</title>
    <style type="text/css">
        .div { table-layout: fixed; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; width: 160px; }
    </style>
    
    <link href="${pageContext.request.contextPath }/css/admin.global.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath }/css/admin.content.css" rel="stylesheet" type="text/css" />
    
    <script type='text/javascript' src='${pageContext.request.contextPath }/dwr/util.js'></script> 
	<script type='text/javascript' src='${pageContext.request.contextPath }/dwr/engine.js'></script> 
	<script type='text/javascript' src='${pageContext.request.contextPath }/dwr/interface/dwr.js'> </script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/calender.js" ></script>  
    
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.utils.js"></script>
    <link href="${pageContext.request.contextPath }/jBox/Skins/Green/jbox.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath }/jBox/jquery.jBox-2.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/admin.js"></script>
    <script type="text/javascript">
    
        // 设置标题
        Index.SetTitle('Country Code Management');
        
        //判断是否已经登录
        if(J.isNullOrEmpty("${sessionScope.username}")) {
			alert('Please exit and re-opearate if you have not an operation for a long time!');
			parent.window.focus();
			parent.window.location.href="${pageContext.request.contextPath}/login.jsp";
		}
        
        // 获取包含当前查询条件的url
        function getPagerUrl() {
        	//var pagerUrl = "terminal/countryManagement?operation=other";
        	var countryName = $('#countryName').trim();
			var BeginDate = $('#BeginDate').trim();
			var EndDate = $('#EndDate').trim();

        	var pagerUrl = 'terminal/countryManagement?operation=other&BeginDate='+BeginDate+'&EndDate=' + EndDate + '&countryName=' + countryName;
        	pagerUrl += "&pageIndex={0}&pageSize={1}";
        	//alert(pagerUrl);
            return pagerUrl;
        }
        
        function getFormatPagerUrl() {
        	return J.FormatString(getPagerUrl(), "${pagerInfo.pageIndex}", "${pagerInfo.pageSize}");
        }

        // 根据ID删除信息
        function DeleteById(id) {
            jBox.confirm("The operation can't be restored,are you sure to delete?", "Delete information", function (v, h, f) {
                if (v == 'ok') {
                    jBox.tip('precessing...', 'loading');
 					// 在这ajax处理
 					dwr.deleteTestUserInfo(id,function (data){
                    	if(data=="success"){
		                    jBox.tip(' Delete successful!! ', 'success');
		                    Index.Open(getFormatPagerUrl());
                    	} else {
                    		jBox.tip(' Delete failed!! ', 'error');
                    	}
                    });                            
                }
                return true;
            });
        }
		
		$(function() {
			$("#search").click(function() {
            	var pageSize = $('#pageSize').trim();

				var countryName = $('#countryName').trim();
				var BeginDate = $('#BeginDate').trim();
				var EndDate = $('#EndDate').trim();

            	var url = 'terminal/countryManagement?operation=other&BeginDate='+BeginDate+'&EndDate=' + EndDate + '&countryName=\"' + countryName + '\"';
            	url = J.FormatString(url + '&pageSize={0}',pageSize);
            	Index.Open(url);
			});
			$("#deleteMulti").click(function(){
				var ids = CheckBox.GetCheckedIds();
            	if (ids.length == 0) {
                	jBox.tip("Please select one information at least！");
                	return;
            	}
            	jBox.confirm("The operation can't be restored,are you sure to delete the selected information?", "Delete information", function (v, h, f) {
                	if (v == 'ok') {
                    	jBox.tip('precessing...', 'loading');					
                    	// 在这ajax处理
                    	dwr.deleteTestUserInfos(ids,function (data){
                    		if(data=="success"){
		                    	jBox.tip(' Delete successful!! ', 'success');
		                    	Index.Open(getFormatPagerUrl());
                    		} else {
                    			jBox.tip(' Delete wrong!! ', 'error');
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
	<input type="hidden" id="countryName" name="countryName" value="${countryName}"/>
    <div class="location">Current Location：Terminal Management -&gt;Country Code Management -&gt; Country Registered Details</div>
    
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
                    <label class="first txt-green">Registration Time：</label>
                    <input type="text" id="BeginDate" name="BeginDate" class="input-small"
                            	 onclick="SelectDate(this,'yyyy-MM-dd')" Style="cursor:pointer;width:70px" readonly="true" value="${BeginDate}"/>
                            	 ~
                    <input type="text" id="EndDate" name="EndDate" class="input-small"
                            	 onclick="SelectDate(this,'yyyy-MM-dd')" Style="cursor:pointer;width:70px" readonly="true" value="${EndDate}"/>
                    <label><a id="search" class="btn-lit btn-middle" href="javascript:;"><span>Search</span></a></label>     
                </div>
            </div>
        </div>
    </div>
    <div class="blank10"></div>

    <div class="block">
        <div class="h">
            <span class="icon-sprite icon-list"></span>
            <h3>Country List</h3>
            <div class="bar" style="display: block;">  
            </div>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt">
                <table class="data-table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th scope="col">Day</th>
                            <th scope="col">Numbers</th>
                            <th scope="col">Begin Time</th>
                            <th scope="col">End Time</th>
                        </tr>
                        <c:forEach items="${beanList}" var="project">
                        <tr>
                          <td class="txt c">
                          	<c:out value="${fn:substring(project.registerDate,0,10)}"/>
			              </td>
			              <td class="txt100 c">${project.numbers }</td>
			              <td class="txt200 c">
                          	<c:out value="${fn:substring(project.registerDate,11,19)}"/>
                          </td>
			              <td class="txt200 c">
                          	<c:out value="${fn:substring(project.updateDate,11,19)}"/>
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