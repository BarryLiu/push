<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="java.net.URLDecoder" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="com.push.admin.dbbean.TPushCommands" %>
<%@ page import="com.push.admin.util.DBUtils" %>
<%@ page import="com.push.admin.dbbean.TProject" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
    String id = request.getParameter("id");
TPushCommands role = null;
    if (id != null) {
        String sql = "select * from t_order where id=" + id;
        List<TPushCommands> beanList = DBUtils.query(sql, TPushCommands.class);
        if (beanList.size() > 0) {
            role = beanList.get(0);
            request.setAttribute("role", beanList.get(0));
        }
    }
    Map<String, String> proMap = new LinkedHashMap<String, String>();
    Map<String, String> cusMap = new LinkedHashMap<String, String>();
    /*
    String sql = "select version from t_project group by version order by version";
    List<TProject> beanList = DBUtils.query(sql, TProject.class);
    for(TProject project:beanList){
    */
    List<String> versions = DBUtils.getVersions();
    for(String version : versions) {
        String strPrj = "";
        String strCustom = "";
        //String swv = project.getVersion();
        String swv = version;
        String[] insp = swv.split("_");
        if (insp.length >= 3) {
            strPrj = swv.substring(0, swv.indexOf("_"));
            String initVer2 = swv.substring(swv.indexOf("_") + 1, swv.length());
            strCustom = initVer2.substring(0, initVer2.indexOf("_"));

        } else {
            if (swv.indexOf("J660") > -1) {
                strPrj = "J660";
                strCustom = "QingCheng";
            } else if (swv.contains("MyUI")) {
                strCustom = "QingCheng";
            }
        }
        if (("D207".equals(strPrj) && "LAVA".equals(strCustom))
                || ("D208".equals(strPrj) && "LAVA".equals(strCustom))) {
            strCustom = "XOLO";
        }
        if (("J608".equals(strPrj) && "CY".equals(strCustom))
                || ("T102".equals(strPrj) && "PUBLIC".equals(strCustom))) {
            strCustom = "TECNO";
        }
        proMap.put(strPrj,strPrj);
        cusMap.put(strCustom,strCustom);
    }
    request.setAttribute("proMap", proMap);
    request.setAttribute("cusMap", cusMap);
%>
<head>
    <title>Role Info</title>
    <link href="${pageContext.request.contextPath }/css/admin.global.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath }/css/admin.content.css" rel="stylesheet" type="text/css"/>

    <script type='text/javascript' src='${pageContext.request.contextPath }/dwr/util.js'></script>
    <script type='text/javascript' src='${pageContext.request.contextPath }/dwr/engine.js'></script>
    <script type='text/javascript' src='${pageContext.request.contextPath }/dwr/interface/dwr.js'></script>

    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.utils.js"></script>
    <link href="${pageContext.request.contextPath }/jBox/Skins/Green/jbox.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath }/jBox/jquery.jBox-2.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/admin.js"></script>
    <script type="text/javascript">
        if ("${sessionScope.username}" == '') {
            Login.Tip.ReLogin(function () {
                parent.window.focus();
                parent.window.location.href = "${pageContext.request.contextPath}/login.jsp";
            });
        }
        if (J.isNullOrEmpty("${param.id}")) {
            Index.SetTitle('Add');
        } else {
            Index.SetTitle('Modify');
        }

        function submitForm() {
            var id = $("#id").trim();
            var version = $("#version").trim();
            var project = $("#project").trim();
            var customer = $("#customer").trim();
            var imei2 = $("#imei2").trim();
            var imei1 = $("#imei1").trim();
            var model = $("#model").trim();
            var status = $("#status").trim();
            var remote = $("#remote").trim();
            var info = $("#info").trim();
            var name = $("#name").trim();
            var order = $("#order").trim();
            if(name==''){
                jBox.tip('请填写一个规则名称!', 'error');
                return;
            }
            if(order==''){
                jBox.tip('请填写一个命令!', 'error');
                return;
            }
            if(version==''&&project==''&&customer==''&&model==''&&(imei1==''||imei2=='')){
                jBox.tip('请至少选择一个条件!', 'error');
                return;
            }
            $.ajax({
                url: "${pageContext.request.contextPath}/order",
                data: {'id': id, 'version': version, 'project': project, 'customer': customer, 'imei2':encodeURI(imei2), 'imei1': encodeURI(encodeURI(imei1)), 'model': model,
                   'operation': 'other','info':encodeURI(info),'name':encodeURI(name),'order':encodeURI(order)},
                dataType: "text",
                async: false,
                type: "POST",
                success: function (json) {
                    var res = J.toObject(json);
                    if (res.res) {
                        jBox.tip(res.msg, 'success');
                        Index.Open("order");
                    } else {
                        jBox.tip(res.msg, 'error');
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="container">

    <div class="location">Current Location：LockInfo -&gt;
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
                </c:choose>Lock Role
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
                <form id="infoForm">
                    <table class="data-form" cellspacing="0" cellpadding="0">
                        <tbody>
                        <tr>
                            <th scope="row">Title：</th>
                            <td>
                                <input value="${role.name}" type="text" name="name" id="name"
                                       class="input-normal"/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Order：</th>
                            <td>
                                <input value="${role.phoneOrder}" type="text" name="order" id="order"
                                       class="input-normal"/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Project：</th>
                            <td>
                                <select id="project" name="project" class="small" style="width: auto">
                                    <option value="">-Please Select-</option>
                                    <c:forEach items="${proMap}" var="project">
                                        <option value="${project.key}"
                                                <c:if test="${role.project==project.key}">selected="selected" </c:if>>${project.value}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Customer：</th>
                            <td>
                                <select id="customer" name="customer" class="small" style="width: auto">
                                    <option value="">-Please Select-</option>
                                    <c:forEach items="${cusMap}" var="customer">
                                        <option value="${customer.key}"
                                                <c:if test="${role.customer==customer.key}">selected="selected" </c:if>>${customer.value}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Model：</th>
                            <td>
                                <input value="${role.model}" type="text" name="model" id="model"
                                       class="input-normal"/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Version：</th>
                            <td>
                                <input value="${role.version}" type="text" name="version" id="version"
                                       class="input-normal"/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">Imei：</th>
                            <td>
                                <input value="${role.imei}" type="text" name="imei1" id="imei1"
                                       class="input-small"/> ~ <input value="${role.imei2}" type="text" name="imei2"
                                                                      id="imei2"
                                                                      class="input-small"/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" valign="top">Info：</th>
                            <td>
                                <textarea rows="5" cols="40" name="info" id="info" class="textarea-normal">${role.info}</textarea>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">&nbsp;</th>
                            <td>
                                <a class="btn-lit" href="#" onclick="submitForm()"><span>OK</span></a>
                                <input name="id" id="id" type="hidden" value="${param.id}"/>
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