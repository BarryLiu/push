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
        Index.SetTitle('Terminal Management');
        
        //判断是否已经登录
        if(J.isNullOrEmpty("${sessionScope.username}")) {
			Login.Tip.ReLogin(function() {
				parent.window.focus();
				parent.window.location.href="${pageContext.request.contextPath}/login.jsp";
			});
		}
        
        // 获取包含当前查询条件的url
        function getPagerUrl() {
        	var pagerUrl = "terminal/terminalManagement?1=1";
            pagerUrl += "&order="+$("#order").trim()+"&da="+$("#da").trim();
            pagerUrl += "&model="+encodeURI(encodeURI('${requestScope.model}'))+"&countryName="+encodeURI(encodeURI('${requestScope.countryName}'))+
            "&version="+encodeURI(encodeURI('${requestScope.version}'))+"&imei="+encodeURI(encodeURI('${requestScope.imei}'))+
            "&phoneNumer="+encodeURI(encodeURI('${requestScope.countryAddr}'))+"&uuids="+encodeURI(encodeURI('${requestScope.uuids}'));
            pagerUrl += "&project="+encodeURI(encodeURI('${requestScope.project}'))+"&customer="+encodeURI(encodeURI('${requestScope.customer}'));
            pagerUrl += "&pageIndex={0}&pageSize={1}";
            return pagerUrl;
        }
        
        function getFormatPagerUrl() {
        	return J.FormatString(getPagerUrl(), "${pagerInfo.pageIndex}", "${pagerInfo.pageSize}");
        }
        
        function order(name){
            var order = $("#order").trim();
            var da = $("#da").trim();
            if(order==name&&da=='2'){
                $("#da").val("1");
            }else{
                $("#da").val("2");
            }
            $("#order").val(name);
            var url = getFormatPagerUrl();

			var countryName = $('#countryName').trim();
			var project = $('#project').trim();
			var customer = $('#customer').trim();
			var model = $('#model').trim();
			var version = $('#version').trim();
			var phoneNumber = $('#phoneNumber').trim();
			var uuids = $('#uuids').trim();
			var imei = $('#imei').trim();
        	
        	url = J.FormatString(url + '&countryName={0}&version={1}&model={2}&pageSize={3}&imei={4}&phoneNumber={5}&uuids={6}&project={7}&customer={8}', 
        	encodeURI(encodeURI(countryName)),encodeURI(encodeURI(version)),encodeURI(encodeURI(model)), $('#pageSize').trim(),encodeURI(encodeURI(imei)),
        	encodeURI(encodeURI(phoneNumber)),encodeURI(encodeURI(uuids)),encodeURI(encodeURI(project)),encodeURI(encodeURI(customer)));
        	
            Index.Open(url);
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
            var order = $("#order").trim();
            var da = $("#da").trim();
            if(order!='') {
                if (da == '1') {
                    $("#tr_" + order).append("<img src='../images/up.png' alt=''/>");
                } else {
                    $("#tr_" + order).append("<img src='../images/down.png' alt=''/>");
                }
            }
			$("#search").click(function() {
				var countryName = $('#countryName').trim();
				var project = $('#project').trim();
				var customer = $('#customer').trim();
				var model = $('#model').trim();
				var version = $('#version').trim();
				var phoneNumber = $('#phoneNumber').trim();
				var uuids = $('#uuids').trim();
				var imei = $('#imei').trim();
            	var url = 'terminal/terminalManagement';
            	
            	url = J.FormatString(url + '?countryName={0}&version={1}&model={2}&pageSize={3}&imei={4}&phoneNumber={5}&uuids={6}&project={7}&customer={8}', 
            	encodeURI(encodeURI(countryName)),encodeURI(encodeURI(version)),encodeURI(encodeURI(model)), $('#pageSize').trim(),encodeURI(encodeURI(imei)),
            	encodeURI(encodeURI(phoneNumber)),encodeURI(encodeURI(uuids)),encodeURI(encodeURI(project)),encodeURI(encodeURI(customer)));
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
    <div class="location">Current Location：Terminal Management -&gt;Terminal Management</div>
    
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
                    <input type="hidden" name="order" id="order" value="${order}"/>
                    <input type="hidden" name="da" id="da" value="${da}"/>
                    
                    <label class="first txt-green">Project：</label>
                    <input value="${project}" type="text" name="project" id="project" class="input-small" />
                    
                    <label class="first txt-green">Customer：</label>
                    <input value="${customer}" type="text" name="customer" id="customer" class="input-small" />
                    
                    <label class="first txt-green">Model：</label>
                    <input value="${model}" type="text" name="model" id="model" class="input-small" />
                    
                    <label class="first txt-green">Version：</label>
                    <input value="${version }" type="text" name="version" id="version" class="input-small" />
                    
                    <label class="first txt-green">Country Name：</label>
                    <input value="${countryName }" type="text" name="countryName" id="countryName" class="input-small" />
                    <label><a id="search" class="btn-lit btn-middle" href="javascript:;"><span>Search</span></a></label> 
                    <br/>
                    <label class="first txt-green">IMEI：</label>
                    <input value="${imei }" type="text" name="imei" id="imei" class="input-small" />
                    <label class="first txt-green">PHONENUMBER：</label>
                    <input value="${phoneNumber }" type="text" name="phoneNumber" id="phoneNumber" class="input-small" /> 
                    <label class="txt-green">Per Page：</label>
                    <select name="pageSize" id="pageSize" class="small">
                        <option value="10" <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 10)}">selected="selected"</c:if>>10</option>
                        <option value="20" <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 20)}">selected="selected"</c:if>>20</option>
                        <option value="50" <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 50)}">selected="selected"</c:if>>50</option>
                        <option value="100" <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 100)}">selected="selected"</c:if>>100</option>
                        <option value="200" <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 200)}">selected="selected"</c:if>>200</option>
                    </select>     
                </div>
            </div>
        </div>
    </div>
                
    <div class="blank10"></div>

    <div class="block">
        <div class="h">
            <span class="icon-sprite icon-list"></span>
            <h3>Terminal List</h3>
            <div class="bar" style="display: block;">
            </div>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt">
                <table class="data-table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th scope="col"><div id="tr_uuid" style="cursor: pointer" onclick="order('uuid')">IMEI</div></th>
                            <th scope="col"><div id="tr_phone_number1" style="cursor: pointer" onclick="order('phone_number1')">PhoneNumber</div></th>
                            <th scope="col"><div id="tr_device_product" style="cursor: pointer" onclick="order('device_product')">Project</div></th>
                            <th scope="col"><div id="tr_device_customer" style="cursor: pointer" onclick="order('device_customer')">Customer</div></th>
                            <th scope="col"><div id="tr_device_model" style="cursor: pointer" onclick="order('device_model')">Device Model</div></th>
                            <th scope="col"><div id="tr_device_version" style="cursor: pointer" onclick="order('device_version')">Device Version</div></th>
                          	<th scope="col"><div id="tr_country_name" style="cursor: pointer" onclick="order('country_name')">Country name</div></th>
                          	<th scope="col"><div id="tr_register_date" style="cursor: pointer" onclick="order('register_date')">Register Date</div></th>
                          	<th scope="col"><div id="tr_retry" style="cursor: pointer" onclick="order('retry')">Retry</div></th>
                          	<th scope="col"><div id="tr_timestamp" style="cursor: pointer" onclick="order('timestamp')">Timestamp</div></th>
                        </tr>
                        <c:forEach items="${beanList }" var="userinfo">
                        <tr>
                          <td class="txt c">
			              	<a href="javascript:Index.Open('terminal/terminalManagement?'+J.getParam('${userinfo }'));" title="Detail">
			              		${userinfo.uuid}
			              	</a>
			              </td>
			              <td class="txt c">
			              <c:if test="${userinfo.phoneNumber1!='-1'}">
			              	${userinfo.phoneNumber1 }
			              </c:if>
			              <c:if test="${userinfo.phoneNumber2!='-1'}">
			              	${userinfo.phoneNumber2 }
			              </c:if>
			              </td>
			              <td class="txt c">${userinfo.deviceProduct }</td>
			              <td class="txt c">${userinfo.deviceCustomer }</td>
			              <td class="txt c">${userinfo.deviceModel }</td>
                          <td class="txt c">${userinfo.deviceVersion }</td>
                          <td class="txt c">${userinfo.countryName }</td>		
			              <td class="txt c">${userinfo.registerDate }</td>
			               <td class="txt c">${userinfo.retry }</td>
			                 <td class="txt c">${userinfo.timestamp }</td>  
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