<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="rgk://com.push.admin.tags/RgkTag" prefix="rgk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>User Info</title>
<link href="${pageContext.request.contextPath }/css/admin.global.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath }/css/admin.content.css"
	rel="stylesheet" type="text/css" />

<script type='text/javascript'
	src='${pageContext.request.contextPath }/dwr/util.js'></script>
<script type='text/javascript'
	src='${pageContext.request.contextPath }/dwr/engine.js'></script>
<script type='text/javascript'
	src='${pageContext.request.contextPath }/dwr/interface/dwr.js'>
	
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/calender.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.utils.js"></script>
<link
	href="${pageContext.request.contextPath }/jBox/Skins/Green/jbox.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/jBox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/admin.js"></script>
<style type="text/css">
#nav {
	min-width: 200px;
	· /* 控制导航总宽度 */
}

/* 取消列表样式，内外补间为零 */
#nav ul {
	list-style: none;
	margin: 0;
	padding: 0;
	position: absolute;
}

/* 竖排：控制导航高度*/
#nav li {
	display: block;
	position: relative;
	height: 30px;
	line-height: 30px; /* 文字垂直居中 指定行高为li高度 自动垂直居中 */
}

/* 将a标签区块化就可以指定高度 */
#nav a {
	display: block;
	height: 100%;
	text-decoration: none; /* 取消a标签下划线 */
	text-align: center; /* 文字水平居中显示 */
	background-color: black;
	color: white;
}

.showa {
	display: block;
	height: auto;
	text-decoration: none; /* 取消a标签下划线 */
	text-align: center; /* 文字水平居中显示 */
	background-color: black;
	color: white;
	width: auto;
}
</style>
<script type="text/javascript">
	if (J.isNullOrEmpty("${sessionScope.username}")) {
		Login.Tip
				.ReLogin(function() {
					parent.window.focus();
					parent.window.location.href = "${pageContext.request.contextPath}/login.jsp";
				});
	}
	if (J.isNullOrEmpty("${pushApk.id}")) {
		Index.SetTitle('Add');
	} else {
		Index.SetTitle('Modify');
	}
	var countryList = new Array();
	var projectList = new Array();
	var customerList = new Array();
	var modelList = new Array();
	var versionList = new Array();
	function select() {
		var result = "";
		// country name
		var item = $("#countryNameResult").trim();
		if (item != "") {
			$("#showCountryName").html("");
			var n = 0;
			for ( var i = 0; i < countryList.length; i++) {
				if (item == countryList[i]) {
					n++;
				}
			}
			if (n == 0) {
				countryList.push(item);
			}
			for ( var i = 0; i < countryList.length; i++) {
				if (countryList[i] != "") {
					result += countryList[i] + ",";
					$("#showCountryName")
							.append(
									"<a id='country"
											+ countryList[i]
											+ "' class='btn-lit' href='javascript:;' onclick='removeCountry(\""
											+ countryList[i] + "\")'><span>"
											+ countryList[i]
											+ "</span></a><br/>");
				}
			}
			result = result.substr(0, result.length - 1);
			$("#countryName").val(result);
		}
		
		// project name		
		var item = $("#projectNameResult").trim();
		if (item != "") {
			$("#showProjectName").html("");
			result = "";
			var n = 0;
			for ( var i = 0; i < projectList.length; i++) {
				if (item == projectList[i]) {
					n++;
				}
			}
			if (n == 0) {
				projectList.push(item);
			}
			for ( var i = 0; i < projectList.length; i++) {
				if (projectList[i] != "") {
					result += projectList[i] + ",";
					$("#showProjectName")
							.append(
									"<a id='project"
											+ projectList[i]
											+ "' class='btn-lit' href='javascript:;' onclick='removeProject(\""
											+ projectList[i] + "\")'><span>"
											+ projectList[i]
											+ "</span></a><br/>");
				}
			}
			result = result.substr(0, result.length - 1);
			$("#projectName").val(result);
		}
		
		// customer name		
		var item = $("#customerNameResult").trim();
		if (item != "") {
			$("#showCustomerName").html("");
			result = "";
			var n = 0;
			for ( var i = 0; i < customerList.length; i++) {
				if (item == customerList[i]) {
					n++;
				}
			}
			if (n == 0) {
				customerList.push(item);
			}
			for ( var i = 0; i < customerList.length; i++) {
				if (customerList[i] != "") {
					result += customerList[i] + ",";
					$("#showCustomerName")
							.append(
									"<a id='project"
											+ customerList[i]
											+ "' class='btn-lit' href='javascript:;' onclick='removeCustomer(\""
											+ customerList[i] + "\")'><span>"
											+ customerList[i]
											+ "</span></a><br/>");
				}
			}
			result = result.substr(0, result.length - 1);
			$("#customerName").val(result);
		}
		
		// model name
		item = $("#modelResult").trim();
		if (item != "") {
			$("#showModel").html("");
			result = "";
			n = 0;
			for ( var i = 0; i < modelList.length; i++) {
				if (item == modelList[i]) {
					n++;
				}
			}
			if (n == 0) {
				modelList.push(item);
			}
			for ( var i = 0; i < modelList.length; i++) {
				if (modelList[i] != "") {
					result += modelList[i] + ",";
					$("#showModel")
							.append(
									"<a id='model"
											+ modelList[i]
											+ "' class='btn-lit' href='javascript:;' onclick='removeModel(\""
											+ modelList[i] + "\")'><span>"
											+ modelList[i] + "</span></a><br/>");
				}
			}
			result = result.substr(0, result.length - 1);
			$("#model").val(result);

		}
		item = $("#versionResult").trim();
		if (item != "") {
			$("#showVersion").html("");
			result = "";
			n = 0;
			for ( var i = 0; i < versionList.length; i++) {
				if (item == versionList[i]) {
					n++;
				}
			}
			if (n == 0) {
				versionList.push(item);
			}
			for ( var i = 0; i < versionList.length; i++) {
				if (versionList[i] != "") {
					result += versionList[i] + ",";
					$("#showVersion")
							.append(
									"<a id='version"
											+ versionList[i]
											+ "' class='btn-lit' href='javascript:;' onclick='removeVersion(\""
											+ versionList[i] + "\")'><span>"
											+ versionList[i]
											+ "</span></a><br/>");
				}
			}
			result = result.substr(0, result.length - 1);
			$("#version").val(result);
		}
	}
	function removeCountry(id) {
		$("#country" + id).remove();
		$("#showCountryName").html("");
		var countrys = $("#countryName").trim();
		countryList = new Array();
		var country = countrys.split(',');
		for ( var i = 0; i < country.length; i++) {
			if (country[i] != id) {
				countryList.push(country[i]);
			}
		}
		var result = "";
		for ( var i = 0; i < countryList.length; i++) {
			if (countryList[i] != "") {
				result += countryList[i] + ",";
				$("#showCountryName")
						.append(
								"<a id='country"
										+ countryList[i]
										+ "' class='btn-lit' href='javascript:;' onclick='removeCountry(\""
										+ countryList[i] + "\")'><span>"
										+ countryList[i] + "</span></a><br/>");
			}
		}
		result = result.substr(0, result.length - 1);
		$("#countryName").val(result);
	}
	function removeProject(id) {
		$("#project" + id).remove();
		$("#showProjectName").html("");
		var projects = $("#projectName").trim();
		projectList = new Array();
		var project = projects.split(',');
		for ( var i = 0; i < project.length; i++) {
			if (project[i] != id) {
				projectList.push(project[i]);
			}
		}
		var result = "";
		for ( var i = 0; i < projectList.length; i++) {
			if (projectList[i] != "") {
				result += projectList[i] + ",";
				$("#showProjectName")
						.append(
								"<a id='project"
										+ projectList[i]
										+ "' class='btn-lit' href='javascript:;' onclick='removeProject(\""
										+ projectList[i] + "\")'><span>"
										+ projectList[i] + "</span></a><br/>");
			}
		}
		result = result.substr(0, result.length - 1);
		$("#projectName").val(result);
	}
	function removeCustomer(id) {
		$("#customer" + id).remove();
		$("#showCustomerName").html("");
		var customers = $("#customerName").trim();
		customerList = new Array();
		var customer = customers.split(',');
		for ( var i = 0; i < customer.length; i++) {
			if (customer[i] != id) {
				customerList.push(customer[i]);
			}
		}
		var result = "";
		for ( var i = 0; i < customerList.length; i++) {
			if (customerList[i] != "") {
				result += customerList[i] + ",";
				$("#showCustomerName")
						.append(
								"<a id='customer"
										+ customerList[i]
										+ "' class='btn-lit' href='javascript:;' onclick='removeCustomer(\""
										+ customerList[i] + "\")'><span>"
										+ customerList[i] + "</span></a><br/>");
			}
		}
		result = result.substr(0, result.length - 1);
		$("#customerName").val(result);
	}
	function removeModel(id) {
		$("#model" + id).remove();
		$("#showModel").html("");
		modelList = new Array();
		var model = $("#model").trim().split(',');
		for ( var i = 0; i < model.length; i++) {
			if (model[i] != id) {
				modelList.push(model[i]);
			}
		}
		var result = "";
		for ( var i = 0; i < modelList.length; i++) {
			if (modelList[i] != "") {
				result += modelList[i] + ",";
				$("#showModel")
						.append(
								"<a id='model"
										+ modelList[i]
										+ "' class='btn-lit' href='javascript:;' onclick='removeModel(\""
										+ modelList[i] + "\")'><span>"
										+ modelList[i] + "</span></a><br/>");
			}
		}
		result = result.substr(0, result.length - 1);
		$("#model").val(result);
	}
	function removeVersion(id) {
		$("#version" + id).remove();
		$("#showVersion").html("");
		versionList = new Array();
		var version = $("#version").trim().split(',');
		for ( var i = 0; i < version.length; i++) {
			if (version[i] != id) {
				versionList.push(version[i]);
			}
		}
		var result = "";
		for ( var i = 0; i < versionList.length; i++) {
			if (versionList[i] != "") {
				result += versionList[i] + ",";
				$("#showVersion")
						.append(
								"<a id='version"
										+ versionList[i]
										+ "' class='btn-lit' href='javascript:;' onclick='removeVersion(\""
										+ versionList[i] + "\")'><span>"
										+ versionList[i] + "</span></a><br/>");
			}
		}
		result = result.substr(0, result.length - 1);
		$("#version").val(result);
	}
	function reSelect() {
		countryList = new Array();
		modelList = new Array();
		versionList = new Array();
		$("#version").val("");
		$("#showVersion").html("");
		$("#model").val("");
		$("#showModel").html("");
		$("#country").val("");
		$("#showCountryName").html("");
		$("#showProjectName").html("");
		$("#showCustomerName").html("");
	}

	function getVersion() {
		var model = $("#modelResult").trim();
		$("#versionResult").html('');
		$("#versionResult").append('<option value="">-请选择-</option>');
		<c:forEach items="${modelMap}" var="country">
		if (model == "${country.key}") {
			<c:forEach items="${country.value}" var="version">
			$("#versionResult").append(
					'<option value="${version.key}">${version.key}</option>');
			</c:forEach>
		}
		</c:forEach>
	}
	function getApkName() {
		$searchInput = $("#search-text");
		$divul = $("#divul");
		$divul.find('li').remove();
		$apksId = $("#apksId");
		$apksId.val('');
		$.ajax({
					url : "${pageContext.request.contextPath}/app/appManagementServlet",
					data : {
						'searchText' : $searchInput.val(),
						'operation' : 'other'
					},
					dataType : "text",
					async : false,
					type : "POST",
					success : function(res) {
						var data = J.toObject(res);
						if (data.length) {
							$.each(data, function(index, term) {
								$ulli = $('<li></li>').appendTo($divul).attr(
										"style", "cursor:default");
								$lia = $("<a></a>").text(term.name).appendTo(
										$ulli).hover(
										function() {
											$('a').attr('style',
													'background-color:black');
											$(this).attr('style',
													'background-color:green');
											;
										},
										function() {
											$(this).attr('style',
													'background-color:black');
										}).click(function() {
									$searchInput.val(term.name);
									$apksId.val(term.id);
									$divul.find('li').remove();
								});
							}).href = "#";
						}
					}
				});
	}

	$(function() {
		getVersion();
		// add by kui.li start
		var project = $("#projectName").trim().split(',');
		for ( var i = 0; i < project.length; i++) {
			if (project[i] != '') {
				projectList.push(project[i]);
				$("#showProjectName")
						.append(
								"<a id='project"
										+ project[i]
										+ "' class='btn-lit' href='javascript:;' onclick='removeProject(\""
										+ project[i] + "\")'><span>"
										+ project[i] + "</span></a><br/>");
			}
		}
		var customer = $("#customerName").trim().split(',');
		for ( var i = 0; i < customer.length; i++) {
			if (customer[i] != '') {
				customerList.push(customer[i]);
				$("#showCustomerName")
						.append(
								"<a id='customer"
										+ customer[i]
										+ "' class='btn-lit' href='javascript:;' onclick='removeCustomer(\""
										+ customer[i] + "\")'><span>"
										+ customer[i] + "</span></a><br/>");
			}
		}
		// add by kui.li end
		var model = $("#model").trim().split(',');
		for ( var i = 0; i < model.length; i++) {
			if (model[i] != '') {
				modelList.push(model[i]);
				$("#showModel")
						.append(
								"<a id='model"
										+ model[i]
										+ "' class='btn-lit' href='javascript:;' onclick='removeModel(\""
										+ model[i] + "\")'><span>" + model[i]
										+ "</span></a><br/>");
			}
		}
		var country = $("#countryName").trim().split(',');
		for ( var i = 0; i < country.length; i++) {
			if (country[i] != '') {
				countryList.push(country[i]);
				$("#showCountryName")
						.append(
								"<a id='country"
										+ country[i]
										+ "' class='btn-lit' href='javascript:;' onclick='removeCountry(\""
										+ country[i] + "\")'><span>"
										+ country[i] + "</span></a><br/>");
			}
		}
		var version = $("#version").trim().split(',');
		for ( var i = 0; i < version.length; i++) {
			if (version[i] != '') {
				versionList.push(version[i]);
				$("#showVersion")
						.append(
								"<a id='version"
										+ version[i]
										+ "' class='btn-lit' href='javascript:;' onclick='removeVersion(\""
										+ version[i] + "\")'><span>"
										+ version[i] + "</span></a><br/>");
			}
		}
		$("#submit")
				.click(
						function() {

				            var country = $("#countryName").trim();
				            var project = $("#projectName").trim();
				            var customer = $("#customerName").trim();
				            var model = $("#model").trim();
				            var version = $("#version").trim();
				            var imei1 = $("#imei1").trim();
				            var imei2 = $("#imei2").trim();
				            
				            if(country =='' && version==''&&project==''&&customer==''&&model==''&&(imei1==''||imei2=='')){
				                jBox.tip('推送范围【imei】或【country,project,customer,model,version】必须填写一项！', 'error');
				                return;
				            }
				            if(imei1 != '') {
				            	var reg = new RegExp("^[0-9]*$");
				            	if(!reg.test(imei1)){
					                jBox.tip('IMEI1必须为数字!', 'error');
					                return;
				            	}
				            }
				            if(imei2 != '') {
				            	var reg = new RegExp("^[0-9]*$");
				            	if(!reg.test(imei2)){
					                jBox.tip('IMEI2必须为数字!', 'error');
					                return;
				            	}
				            }
				            
				            if(imei1 != '' && imei2 != '' && imei2<=imei1) {
				                jBox.tip('IMEI1必须小于IMEI2!', 'error');
				                return;
				            }
				            
							var title = $("#title").trim();
							if (Check.isEmptyOrLenTooLong(title, 30, "title")) {
								J.focus($("#title"));
								return;
							}
							var comments = $("#comments").trim();
							if (Check.isEmptyOrLenTooLong(comments, 50,
									"comments")) {
								J.focus($("#comments"));
								return;
							}
							var pushDate = $("#pushDate").trim();
							if (Check.isEmptyOrLenTooLong(pushDate, 30,
									"pushDate")) {
								J.focus($("#pushDate"));
								return;
							}
							var apksId = $("#apksId").trim();
							if (apksId == '') {
								Login.Tip(
										"Please select a drop-down list apk!",
										"worning");
								return;
							}
							$
									.ajax({
										cache : true,
										type : "POST",
										url : "/padm/app/appPushServlet",
										data : encodeURI(encodeURI(decodeURIComponent(
												$("#modifyForm").serialize(),
												true))),
										dataType : "text",
										async : false,
										error : function(request) {
											Login
													.Tip(
															"An unknown error has occurred!",
															"error");
										},
										success : function(data) {
											var res = J.toObject(data);
											if (res.res) {
												Login.Tip(res.msg, "success");
												var url = 'app/appPushServlet';
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

		<div class="location">
			Current Location：Application Push -&gt;
			<c:choose>
				<c:when test="${!empty pushapk.id }">Modify </c:when>
				<c:otherwise>Add </c:otherwise>
			</c:choose>
		</div>

		<div class="blank10"></div>

		<div class="block">
			<div class="h">
				<span class="icon-sprite icon-list"></span>
				<h3>
					<c:choose>
						<c:when test="${!empty pushApk.id }">Modify </c:when>
						<c:otherwise>Add </c:otherwise>
					</c:choose>
					Application Push
				</h3>
				<div class="bar">
					<a class="btn-lit" href="javascript:window.history.back();"><span>Return</span></a>
				</div>
			</div>
			<div class="tl corner"></div>
			<div class="tr corner"></div>
			<div class="bl corner"></div>
			<div class="br corner"></div>
			<div class="cnt-wp">
				<div class="cnt form">
					<form id="modifyForm">
						<table class="data-form" cellspacing="0" cellpadding="0">
							<tbody>
								<tr>
									<th scope="row">Title：</th>
									<td><input value="${pushApk.title }" type="text"
										name="title" id="title" class="input-normal" /> <font
										color="red">*</font></td>
								</tr>
								<tr>
									<th scope="row">Apk Name：</th>
									<td><input type="text" onkeyup="getApkName()"
										id="search-text" name="search-text" class="input-normal"
										value="${pushApk.name }" />
										<div id="nav">
											<ul id="divul">
											</ul>
										</div> <input type="hidden" id="apksId" name="apksId"
										value="${pushApk.apksId }" /></td>
								</tr>
								<tr>
									<th scope="row">Install Type：</th>
									<td><select name="installType" id="installType"
										class="small" style="width: auto">
											<option value="1"
												<c:if test="${pushApk.installType==1}">selected="selected"</c:if>>静默安装</option>
											<option value="2"
												<c:if test="${pushApk.installType==2}">selected="selected"</c:if>>许可型安装</option>
									</select> <font color="red">*</font></td>
								</tr>
		                        <tr>
		                            <th scope="row">Imei：</th>
		                            <td>
		                                <input value="${pushApk.imei1}" type="text" name="imei1" id="imei1"
		                                       class="input-small" maxlength="15" style="width:120px"/> ~ <input value="${pushApk.imei2}" type="text" name="imei2" 
		                                                                      id="imei2"
		                                                                      class="input-small" maxlength="15" style="width:120px"/>
		                            </td>
		                        </tr>
								<tr>
									<th scope="row">Country-Model-Version：</th>
									<td>

										<table>
											<tr>
												<td><select id="countryNameResult" class="small"
													style="width: auto">
														<option value="">-请选择-</option>
														<c:forEach items="${countryMap}" var="country">
															<option value="${country.key}">${country.key}</option>
														</c:forEach>
												</select></td>
												<td><select id="projectNameResult" class="small"
													style="width: auto">
														<option value="">-请选择-</option>
														<c:forEach items="${projectMap}" var="project">
															<option value="${project.key}">${project.key}</option>
														</c:forEach>
												</select></td>
												<td><select id="customerNameResult" class="small"
													style="width: auto">
														<option value="">-请选择-</option>
														<c:forEach items="${customerMap}" var="customer">
															<option value="${customer.key}">${customer.key}</option>
														</c:forEach>
												</select></td>
												<td><select id="modelResult" onchange="getVersion()"
													class="small" style="width: auto">
														<option value="">-请选择-</option>
														<c:forEach items="${modelMap}" var="country">
															<option value="${country.key}">${country.key}</option>
														</c:forEach>
												</select></td>
												<td><select id="versionResult" class="small"
													style="width: auto">
												</select></td>
												<td><a onclick="select()" class="btn-lit btn-middle"
													href="javascript:;"><span>select</span></a></td>
											</tr>
											<tr>
												<td valign="top"><span id="showCountryName"></span></td>
												<td valign="top"><span id="showProjectName"></span></td>
												<td valign="top"><span id="showCustomerName"></span></td>
												<td valign="top"><span id="showModel"></span></td>
												<td valign="top"><span id="showVersion"></span></td>
												<td></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<th scope="row">Push Date：</th>
									<td><input type="text" id="pushDate" name="pushDate"
										class="input-small"
										onclick="SelectDate(this,'yyyy-MM-dd hh:mm')"
										style="cursor:pointer" readonly="true"
										value="${pushApk.pushDate }" /> <font color="red">*</font></td>
								</tr>
								<tr>
									<th scope="row">Comments：</th>
									<td><textarea rows="3" cols="30" name="comments"
											id="comments" class="textarea-normal">${pushApk.comments }</textarea>
										<font color="red">*</font></td>
								</tr>
								<tr>
									<th scope="row">&nbsp;</th>
									<td><label><a id="submit"
											class="btn-lit btn-middle" href="javascript:;"><span>OK</span></a></label>
										<c:choose>
											<c:when test="${empty pushApk.id }">
												<input name="operation" type="hidden" value="add" />
											</c:when>
											<c:otherwise>
												<input name="operation" type="hidden" value="update" />
												<input name="id" type="hidden" value="${pushApk.id }" />
											</c:otherwise>
										</c:choose> <input name="version" id="version" type="hidden"
										value="${pushApk.versionName }" /> <input name="model"
										id="model" type="hidden" value="${pushApk.modelName }" />
										<input name="countryName" id="countryName" type="hidden"
										value="${pushApk.countryName }" />
										<input name="projectName" id="projectName" type="hidden"
										value="${pushApk.projectName }" />
										<input name="customerName" id="customerName" type="hidden"
										value="${pushApk.customerName }" />
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