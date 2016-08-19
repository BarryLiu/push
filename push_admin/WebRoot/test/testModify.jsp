<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upgrade Management</title>
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
			alert('Please exit and re-opearate if you have not an operation for a long time!');
			parent.window.focus();
			parent.window.location.href="${pageContext.request.contextPath}/login.jsp";
		}
		if(J.isNullOrEmpty("${param.id}")) {
			Index.SetTitle('Add');
		} else {
			Index.SetTitle('Modify');
		}
		function submitForm() {
			var name = $("#name").trim();
			if(J.isNullOrEmpty(name)) {
				Login.Tip('The name can not be empty!', 'warning');
			} else {
				var sex = $("#sex").trim();
				if(J.isNullOrEmpty("${param.id}")) { //Add
					dwr.addTestUserInfo(name, sex, {
						callback: function(id){
							if(id > 0) {
								jBox.tip(" Add Successfully!! ", "success");
								var url = 'userInfo?pageIndex=${param.pageCount+1}';
            					Index.Open(url);
							} else {
								jBox.tip(" Add Failure!! ", "warn");
							}
						},
						timeout: 1000
					});
				} else { //Update
					dwr.updateTestUserInfo("${param.id}", name, sex, {
						callback: function(resStr) {
							if(resStr == "success") {
								jBox.tip(" Update Successfully!! ", "success");
							} else {
								jBox.tip(" Update Failure!! ", "warn");
							}
							var url = 'userInfo?id=${param.id}';
							Index.Open(url);
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
    			</c:choose>UserInfo
            </h3>
            <div class="bar">
                <a class="btn-lit" href="javascript:window.history.back();"><span>Return</span></a>
            </div>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt form">
                <form method="get" action="${pageContext.request.contextPath }/userInfo">
                <table class="data-form" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th scope="row">Name：</th>
                            <td>
                            	<input value="${param.name }" type="text" name="name" id="name" class="input-normal" />
                            	<c:if test="${empty param.id }">&nbsp;<font color="red">*</font></c:if>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Sex：</th>
                            <td>
                            	<select name="sex" id="sex" style="width: 190px;">
                            		<option value="F" <c:if test="${param.sex eq 'F' }">selected="selected"</c:if> >男</option>
                            		<option value="M" <c:if test="${param.sex eq 'M' }">selected="selected"</c:if> >女</option>
                            	</select>
                            	<c:if test="${empty param.id }">&nbsp;<font color="red">*</font></c:if>
                            </td>
                        </tr>
                                           
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