<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
        Index.SetTitle('Upgrade Management');
        
        //判断是否已经登录
        if(J.isNullOrEmpty("${sessionScope.username}")) {
			alert('Please exit and re-opearate if you have not an operation for a long time!');
			parent.window.focus();
			parent.window.location.href="${pageContext.request.contextPath}/login.jsp";
		}
        
        // 获取包含当前查询条件的url
        function getPagerUrl() {
        	var pagerUrl = "userInfo?1=1";
            if(!J.isNullOrEmpty("${name}")) {
              	pagerUrl += "&name=${name}";
            }
            if(!J.isNullOrEmpty("${sex}")) {
              	pagerUrl += "&sex=${sex}";
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
				var name = $('#name').trim();
            	var sex = $('#sex').trim();
            	var pageSize = $('#pageSize').trim();

            	var url = 'userInfo';
            	url = J.FormatString(url + '?name={0}&sex={1}&pageSize={2}', name, sex, pageSize);
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
    <div class="location">Current Location：Upgrade Management -&gt;System Upgrade Management</div>
    
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
                    <input value="${name }" type="text" name="name" id="name" class="input-small" />
                    <label class="txt-green">Sex：</label>
                    <select name="sex" id="sex">
                    	<option value="">&nbsp;</option>
                    	<option value="F" <c:if test="${sex eq 'F' }">selected="selected"</c:if> >男</option>
                    	<option value="M" <c:if test="${sex eq 'M' }">selected="selected"</c:if> >女</option>
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
            <h3>Project List</h3>
            <div class="bar" style="display: block;">
                <a class="btn-lit" href="javascript:Index.Open('test/testModify.jsp?pageCount=${pagerInfo.pageCount }');"><span>Add</span></a>
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
                            <th scope="col">Sex</th>
                            <th scope="col">Create Date</th>
                            <th scope="col">Update Date</th>
                          	<th scope="col">Modify</th>
                            <th scope="col">Delete</th>
                        </tr>
                        <c:forEach items="${beanList }" var="userinfo">
                        <tr>
			              <td class="chk"><input value="true" type="checkbox" rel="${userinfo.id }" name="CheckBox" class="check-box" /><input value="false" type="hidden" name="CheckBox" /></td>
			              <td class="txt c">
			              	<a href="javascript:Index.Open('test/testDetail.jsp?'+J.getParam('${userinfo }'));" title="Detail">
			              		${userinfo.name }
			              	</a>	
			              </td>
			              <td class="txt120 c">
			              	<c:choose>
			              	<c:when test="${userinfo.sex=='F' }">男</c:when>
			              	<c:otherwise>女</c:otherwise>
			              	</c:choose>
			              </td>
			              <td class="txt200 c">${userinfo.createDate }</td>
			              <td class="txt200 c">${userinfo.updateDate }</td>
			              <td class="icon"><a class="opt" title="Modify" href="javascript:Index.Open('test/testModify.jsp?'+J.getParam('${userinfo }'));"><span class="icon-sprite icon-edit"></span></a></td>
                          <td class="icon tail"><a class="opt" title="Delete" href="javascript:DeleteById('${userinfo.id }');"><span class="icon-sprite icon-delete"></span></a></td>
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