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
    
    <script type='text/javascript' src='${pageContext.request.contextPath }/dwr/util.js'></script> 
	<script type='text/javascript' src='${pageContext.request.contextPath }/dwr/engine.js'></script> 
	<script type='text/javascript' src='${pageContext.request.contextPath }/dwr/interface/dwr.js'> </script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.utils.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/ajaxfileupload.js"></script>
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
		function showType(){
		    $("#type2").html("");
		    var countryName = $("#type1").trim();
		    <c:forEach items="${typeMap}" var="country">
		    if('${country.key}'==countryName){
		    <c:forEach items="${country.value}" var="addr">
		    if('${addr.key}'=='${type2}'){
		    $("#type2").append("<option value='${addr.key}' selected='selected'>${addr.key}</option>");
		    }else{
		    $("#type2").append("<option value='${addr.key}'>${addr.key}</option>");
		    }
		    </c:forEach>
		    }
		    </c:forEach>
		}
		
		
		$(function() {
		showType();
	    $("#reUpload").click(function() {
	        $("#uptr").show();
			$("#showtr").hide();
	    });
	    $("#cancle").click(function() {
	        document.getElementById("uploadFile").outerHTML = document.getElementById("uploadFile").outerHTML;
	        $("#uptr").hide();
			$("#showtr").show();
	    });
	    $("#upload").click(function() {
	    	var path = document.getElementById("uploadFile").value;
			var extend = path.substring(path.lastIndexOf(".")+1);
			if(extend!='apk'){
			Login.Tip("Must upload an apk file!", "Worning");
			return;
			}
	        $.ajaxFileUpload({
				url:"${pageContext.request.contextPath}/app/apkFileServlet",
				data:{},
				dataType:"json",
				secureuri:false,
				fileElementId:"uploadFile",
				success: function(data,status){
				$("#icon").val(data.icon);
				$("#url").val(data.url);
				$("#name").val(data.name);
				$("#packageName").val(data.packageName);
				$("#versionName").val(data.versionName);
				$("#versionCode").val(data.versionCode);
				$("#size").val(data.size);
				$("#showVersionName").html(data.versionName);
				$("#showVersionCode").html(data.versionCode);
				$("#showSize").html(data.size);
				$("#showUrl").html(data.url);
				$("#showPackageName").html(data.packageName);
				$("#showIcon").attr("src","${pageContext.request.contextPath}/"+data.icon);
				$("#name").val(data.name);
			    }
		    });
		    $("#uptr").hide();
			$("#showtr").show();
	    });
			$("#submit").click(function() {
			var name = $("#name").trim();
			var packageName = $("#packageName").trim();
			var icon = $("#icon").trim();
			var description = $("#description").trim();
			var channel = $("#channel").trim();
			var size = $("#size").trim();
			var url = $("#url").trim();
			var versionCode = $("#versionCode").trim();
			var versionName = $("#versionName").trim();
			var id = $("#id").trim();
			var type1 = $("#type1").trim();
			var type2 = $("#type2").trim();
			var operation = $("#operation").trim();
			if(Check.isEmptyOrLenTooLong(name, 30, "name")) {
					J.focus($("#name"));
					return;
			}
			if(Check.isEmptyOrLenTooLong(description, 500, "description")) {
					J.focus($("#description"));
					return;
			}
			if(Check.isEmptyOrLenTooLong(channel, 20, "channel")) {
					J.focus($("#channel"));
					return;
			}
			if(url==''){
			Login.Tip("Must upload an apk file!", "Worning");
			return;
			}
		    $.ajax({
				cache:true,
				type: "POST",
				url:"${pageContext.request.contextPath}/app/appManagementServlet",
				data:{name:name,packageName:packageName,icon:icon,description:description,type1:type1,type2:type2,url:url,
				channel:channel,size:size,versionCode:versionCode,versionName:versionName,id:id,operation:operation},
				dataType:"text",
				async:false,
				error: function(request){
				      Login.Tip("An unknown error has occurred!", "error");
				},
				success: function(data){
				            var res = J.toObject(data);
							if(res.res) {
								Login.Tip(res.msg, "success");
								var url = 'app/appManagementServlet?pageIndex=${param.pageCount+1}';
            					Index.Open(url);
							} else {
								Login.Tip(res.msg, "error");
					  }
				 }
			});
			});
	    });
    </script>
</head>
<body>
<div class="container">

    <div class="location">Current Location：Application Push -&gt; 
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
    			</c:choose>Application Push
            </h3>
            <div class="bar">
                <a class="btn-lit" href="javascript:window.history.back();"><span>Return</span></a>
            </div>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt form">
                <form id="modifyForm" enctype="multipart/form-data" action="appManagementServlet" method="post">
                <table class="data-form" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th scope="row">Name：</th>
                            <td>
                            	<input value="${apk.name }" type="text" name="name" id="name" class="input-normal" />
                            	<font color="red">*</font>
                            </td>
                        </tr>
                        <tr id="iconTr">
                            <th scope="row">Icon：</th>
                            <td>
                            	<img id="showIcon" src="${pageContext.request.contextPath}/${apk.icon}" width="72px" height="72px"/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Type：</th>
                            <td>
                            <select name="type1" id="type1" onchange="showType()" class="small" style="width: auto">
	                        <c:forEach items="${typeMap}" var="type">
	                        <option value="${type.key}" <c:if test="${type.key==type1}">selected="selected"</c:if>>${type.key}</option>
	                        </c:forEach>
                            </select> 
                            <select name="type2" id="type2" class="small" style="width: auto">
                            </select>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Description：</th>
                            <td>
                            <textarea rows="3" cols="30" name="description" id="description" class="textarea-normal">${apk.description }</textarea>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Channel：</th>
                            <td>
                            	<input value="${apk.channel }" type="text" name="channel" id="channel" class="input-normal" />
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Size：</th>
                            <td>
                            	<div id="showSize">${apk.size}</div>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Version Code：</th>
                            <td>
                            	<div id="showVersionCode">${apk.versionCode}</div>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Version Name：</th>
                            <td>
                            	<div id="showVersionName">${apk.versionName}</div>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Package Name：</th>
                            <td>
                            	<div id="showPackageName">${apk.packageName}</div>
                            </td>
                        </tr>
                        <tr id="uptr" <c:if test="${!empty apk.id }">style="display: none;"</c:if>>
                            <th scope="row">Upload File：</th>
                            <td>
                            <input  type="file" id="uploadFile" name="uploadFile" accept=".apk" class="input-normal"/>
                            <a class="btn-lit" href="javascript:" id="upload"><span>Submit</span></a>
                            <a class="btn-lit" href="javascript:" id="cancle"><span>Cancle</span></a>
                             &nbsp;<font color="red">*</font>
                            </td>
                        </tr> 
                        <tr id="showtr" <c:if test="${empty apk.id }">style="display: none;"</c:if>>
                            <th scope="row">Url：</th>
                            <td style="font-weight: bold"><span id="showUrl">${apk.url }</span>
                            	  <a class="btn-lit" href="javascript:" id="reUpload"><span>Re-upload</span></a>
                            </td>
                        </tr>                    
                        <tr>
                            <th scope="row">&nbsp;</th>
                            <td>
                                <label><a id="submit" class="btn-lit btn-middle" href="javascript:;"><span>OK</span></a></label>
                                <input name="id" id="id" type="hidden" value="${apk.id }" />
                                <input name="url" id="url" type="hidden" value="${apk.url }" />
                                <input name="icon" id="icon" type="hidden" value="${apk.icon }" />
                                <input name="versionCode" id="versionCode" type="hidden" value="${apk.versionCode }" />
                                <input name="versionName" id="versionName" type="hidden" value="${apk.versionName }" />
                                <input name="size" id="size" type="hidden" value="${apk.size }" />
                                <input name="packageName" id="packageName" type="hidden" value="${apk.packageName }" />
                                <c:choose>
                                <c:when test="${empty apk.id }">
                                	<input name="operation" id="operation"  type="hidden" value="add" />
                                </c:when>
                                <c:otherwise>
                                	<input name="operation" id="operation" type="hidden" value="update" />
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