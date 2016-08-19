<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.net.URLDecoder"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="rgk://com.push.admin.tags/RgkTag" prefix="rgk" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>User Info</title>
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
    	if(J.isNullOrEmpty("${sessionScope.username}")) {
			Login.Tip.ReLogin(function() {
				parent.window.focus();
				parent.window.location.href="${pageContext.request.contextPath}/login.jsp";
			});
		}
		if(J.isNullOrEmpty("${param.id}")) {
			Index.SetTitle('Add');
		} else {
			Index.SetTitle('Modify');
		}
		
		function submitForm() {
			var username = $("#username").trim();
			var newPwd = $("#new_pwd").trim();
			var confirmPwd = $("#confirm_pwd").trim();
			if(J.isNullOrEmpty("${param.id}")) { //Add
			    var usern = /^[a-zA-Z0-9_.]{1,}$/;
			    if(!username.match(usern)){
			        Login.Tip("Username can only contain 0-9,a-z,A-Z,'.' and '_'!", "error");
			        return;
			    }
				if(Check.isEmptyOrLenTooLong(username, 30, "username")) {
					J.focus($("#username"));
				} else if(Check.isEmptyOrLenTooLong(newPwd, 30, "new password")) {
					J.focus($("#new_pwd"));
				} else if(confirmPwd != newPwd) {
					Login.Tip("The confirm password is not same as the new password!", "error");
					J.focus($("#confirm_pwd"));
				} else {
					dwr.addUserInfo(username, newPwd, {
						callback: function(json){
							var res = J.toObject(json);
							if(res.res) {
								Login.Tip(res.msg, "success");
								var url = 'user?pageIndex=${param.pageCount+1}';
            					Index.Open(url);
							} else {
								Login.Tip(res.msg, "error");
							}
						},
						timeout: 1000
					});
				}
			} else { //Modify
				var oldPwd = $("#old_pwd").trim();
				var updateComments = $("#updateComments").trim();
				if(J.isNullOrEmpty(oldPwd) || oldPwd!='${param.pwd}') {
					Login.Tip("The old password is wrong!", "error");
				} else if(Check.isEmptyOrLenTooLong(newPwd, 30, "new password")) {
					J.focus($("#new_pwd"));
				} else if(newPwd == oldPwd) {
					Login.Tip("The new password can not be same as the old password!");
					J.focus($("#new_pwd"));
				} else if(confirmPwd != newPwd) {
					Login.Tip("The confirm password is not same as the new password!", "error");
					J.focus($("#confirm_pwd"));
				} else if(Check.isEmptyOrLenTooLong(updateComments, 1000, "update comments")) {
					J.focus($("#updateComments"));
				} else {
					dwr.updateUserInfo("${param.id}", newPwd, updateComments, {
						callback: function(json) {
							var res = J.toObject(json);
							if(res.res) {
								Login.Tip(res.msg, "success");
								var url = 'user';
								Index.Open(url);
							} else {
								Login.Tip(res.msg, "error");
							}
						}
					});
				}
			}
		}
    </script>
</head>
<body>
<div class="container">

    <div class="location">Current Location：UserInfo -&gt; 
    	<c:choose>
    	<c:when test="${!empty param.id }">Modify </c:when>
    	<c:otherwise>Add </c:otherwise>
    	</c:choose>
    </div>
    
    <div class="blank10"></div>

    <div class="block">
        <div class="h">
            <span class="icon-sprite icon-list"></span>
            <h3> 
            	<c:choose>
    			<c:when test="${!empty param.id }">Modify </c:when>
    			<c:otherwise>Add </c:otherwise>
    			</c:choose>User
            </h3>
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
                            <th scope="row">Username：</th>
                            <td>
                            	<input value="<%if(request.getParameter("username")!=null)out.print(URLDecoder.decode(request.getParameter("username"),"utf-8"));%>" type="text" name="username" id="username" class="input-normal" 
                            		<c:if test="${!empty param.id }">disabled</c:if> />
                            	<font color="red">*</font>
                            </td>
                        </tr>
                        <c:if test="${!empty param.id }">
                        <tr>
                            <th scope="row">Old Password：</th>
                            <td>
                            	<input type="password" name="old_pwd" id="old_pwd" class="input-normal" />
                            	<font color="red">*</font>
                            </td>
                        </tr>
                        </c:if>
                        <tr>
                            <th scope="row"><c:if test="${!empty param.id }">New </c:if>Password：</th>
                            <td>
                            	<input type="password" name="new_pwd" id="new_pwd" class="input-normal" />
                            	<font color="red">*</font>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Confirm Password：</th>
                            <td>
                            	<input type="password" name="confirm_pwd" id="confirm_pwd" class="input-normal" />
                            	<font color="red">*</font>
                            </td>
                        </tr>
                        <c:if test="${!empty param.id }">
                        <tr>
                            <th scope="row" valign="top">Update Comments：</th>
                            <td>
                            	<textarea rows="5" cols="40" name="updateComments" id="updateComments" class="textarea-normal"></textarea>
                            	<font color="red">*</font>
                            </td>
                        </tr>
                        </c:if>
                        
                                           
                        <tr>
                            <th scope="row">&nbsp;</th>
                            <td>
                                <a class="btn-lit" href="javascript:submitForm();"><span>OK</span></a>
                                <c:choose>
                                <c:when test="${empty param.id }">
                                	<input name="operation" type="hidden" value="add" />
                                </c:when>
                                <c:otherwise>
                                	<input name="operation" type="hidden" value="update" />
                                	<input name="id" type="hidden" value="${param.id }" />
                                </c:otherwise>
                                </c:choose>
                            </td>
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