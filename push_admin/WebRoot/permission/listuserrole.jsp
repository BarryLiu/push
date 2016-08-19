<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="rgk://com.push.admin.tags/RgkTag" prefix="rgk" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>User-Role</title>
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
				if (J.IsIE6) {
					alert("IE6");
					document.execCommand("Stop");
				} else {
					window.stop();
				}
			});
		}
		
		function getUrl() {
			var userId = $("#users").val();
			if(J.isNullOrEmpty(userId)) {
				return "userRole";
			} else {
				return "userRole?id="+userId;	
			}
		}
		
		$(function() {
			var extIds = J.optVals($("#extSelect option"));
			$("#users").change(function() {
				Index.Open(getUrl());
			});
			$("#assignRoles").click(function(){
            	var userId = $("#users").val();
            	if(J.isNullOrEmpty(userId)) {
            		return;
            	}
            	var roleIds = J.optVals($("#extSelect option"));
            	if(J.arrayEqual(extIds, roleIds)) {
            		Login.Tip("No Roles Changed!");
            	} else {
            		dwr.assignRoles(userId, roleIds, {
            			callback: function(json) {
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
            			},
            			async: false
            		});
            	}
			});
			$(".ul-assign input").each(function() {
				$(this).mousedown(function() {
					$(this).css("border","1px solid #4a4a4a");
				});
				$(this).mouseup(function() {
					$(this).css("border","1px solid #a4a4a4");
					this.blur();
				});
				$(this).mouseout(function() {
					$(this).css("border","1px solid #a4a4a4");
					this.blur();
				});
				$(this).click(function() {
					var id = $(this).attr("id");
					//asgSelect .. extSelect
					if(id=="but1") { //-->
						$("#asgSelect option:selected").appendTo("#extSelect");
					} else if(id=="but2") { //<--
						$("#extSelect option:selected").appendTo("#asgSelect");
					} else if(id=="but3") { //-->>
						$("#asgSelect option").appendTo("#extSelect");
					} else if(id=="but4") { //<<--
						$("#extSelect option").appendTo("#asgSelect");
					}
				});
			});
			$("#extSelect").dblclick(function() {
				$("#extSelect option:selected").appendTo("#asgSelect");
			});
			$("#asgSelect").dblclick(function() {
				$("#asgSelect option:selected").appendTo("#extSelect");
			});
			if(J.isNullOrEmpty($("#users").val())) {
				$(".btn-lit").hide();
			} else {
				$(".btn_lit").show();
			}
			
		});
    </script>
</head>
<body>
<div class="container">
    <div class="location">Current Location：Permission Management -&gt;User-Role</div>
    
    <div class="blank10"></div>

    <div class="search block">
        <div class="h">
            <span class="icon-sprite icon-magnifier"></span>
            <h3>Select User</h3>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt">
                <div class="search-bar">
                    <label class="first txt-green">User Name：</label>
                    <select id="users" name="users" class="select150">
                    	<c:forEach items="${userInfos }" var="user">
                    	<c:if test="${user.username!='sys'}">
                    	<option value="${user.id }" <c:if test="${user.id==id }">selected</c:if> >${user.username }</option>
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
            <h3>User-Role</h3>
            <div class="bar" style="display: block;padding-right:60px;">
                <a class="btn-lit" id="assignRoles" href="javascript:;"><span>Assign</span></a>     
            </div>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt">
                <table class="data-table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th scope="col">Assignable roles</th>
                            <th scope="col">&nbsp;</th>
                            <th scope="col">Existing roles</th>
                        </tr>
                        <tr>
			              <td class="txt c noborder">
			                <select id="asgSelect" multiple="multiple" size="15" class="select-multi">
			                	<c:forEach items="${assignableBeans }" var="assBean">
			                	<option value="${assBean.id }">${assBean.roleName }</option>
			                	</c:forEach>
			                </select>
			              </td>
			              <td class="txt80 c noborder">
							<ul class="ul-assign">
								<li><input id="but1" type="button" value="&nbsp;--&gt;&nbsp;" /></li>
								<li><input id="but2" type="button" value="&nbsp;&lt;--&nbsp;" /></li>
								<li><input id="but3" type="button" value="--&gt;&gt;" /></li>
								<li><input id="but4" type="button" value="&lt;&lt;--" /></li>
							</ul>
						  </td>
			              <td class="txt c noborder">
							<select id="extSelect" multiple="multiple" size="15" class="select-multi">
			                	<c:forEach items="${existingBeans }" var="extBean">
			                	<option value="${extBean.id }">${extBean.roleName }</option>
			                	</c:forEach>
			                </select>
						  </td>
			            </tr>
                                    
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>