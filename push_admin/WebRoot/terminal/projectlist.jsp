<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.utils.js"></script>
    <link href="${pageContext.request.contextPath }/jBox/Skins/Green/jbox.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath }/jBox/jquery.jBox-2.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/admin.js"></script>
    <script type="text/javascript">
    
        // 设置标题
        Index.SetTitle('Project Management');
        
        //判断是否已经登录
        if(J.isNullOrEmpty("${sessionScope.username}")) {
			alert('Please exit and re-opearate if you have not an operation for a long time!');
			parent.window.focus();
			parent.window.location.href="${pageContext.request.contextPath}/login.jsp";
		}
        
        // 获取包含当前查询条件的url
        function getPagerUrl() {
        	var pagerUrl = "terminal/projectManagement?1=1";
            var project = $("#project").trim();
            var customer = $("#customer").trim();
            if(project!=''){
                pagerUrl += "&project="+encodeURI(encodeURI(project));
            }
            if(customer!=''){
                pagerUrl += "&customer="+encodeURI(encodeURI(customer));
            }
            pagerUrl += "&order="+$("#order").trim()+"&da="+$("#da").trim();
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
            Index.Open(url);
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
            	var pageSize = $('#pageSize').trim();
            	var url = 'terminal/projectManagement?1=1';
                var project = $("#project").trim();
                var customer = $("#customer").trim();
                if(project!=''){
                    url += "&project="+encodeURI(encodeURI(project));
                }
                if(customer!=''){
                    url += "&customer="+encodeURI(encodeURI(customer));
                }
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
    <div class="location">Current Location：Terminal Management -&gt;Project Management</div>
    
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
                    <label class="first txt-green">Project：</label>
                    <input value="${project}" type="text" name="project" id="project" class="input-small" />
                    <label class="first txt-green">Customer：</label>
                    <input value="${customer}" type="text" name="customer" id="customer" class="input-small" />
                    <input type="hidden" name="order" id="order" value="${order}"/>
                    <input type="hidden" name="da" id="da" value="${da}"/>
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
            <h3>Project List</h3>
            <div class="bar" style="display: block;">
            </div>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt">
                <table class="data-table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th scope="col" ><div id="tr_model" style="cursor: pointer" onclick="order('model')">Model</div></th>
                            <th scope="col" ><div id="tr_version" style="cursor: pointer" onclick="order('version')">Version</div></th>
                            <th scope="col" ><div id="tr_numbers" style="cursor: pointer" onclick="order('numbers')">Numbers</div></th>
                            <th scope="col" ><div id="tr_register_date" style="cursor: pointer" onclick="order('register_date')">Create Date</div></th>
                            <th scope="col" ><div id="tr_update_date" style="cursor: pointer" onclick="order('update_date')">Update Date</div></th>
                        </tr>
                        <c:forEach items="${beanList }" var="project">
                        <tr>
			              <td class="txt120 c">
			              	<a href="javascript:Index.Open('terminal/terminalManagement?pageSize=30&type=1&model='+encodeURI(encodeURI('${project.model }')));" title="Detail">
			              		${project.model }
			              	</a>	
			              </td>
			              <td class="txt c">
			              	<a href="javascript:Index.Open('terminal/terminalManagement?pageSize=30&type=2&version='+encodeURI(encodeURI('${project.version }')));" title="Detail">
			              		${project.version }
			              	</a>	
			              </td>
			              <td class="txt80 c">${project.numbers }</td>
			              <td class="txt200 c">${project.registerDate }</td>
			              <td class="txt200 c">${project.updateDate }</td>
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