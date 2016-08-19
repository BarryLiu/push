<%@ page import="java.net.URLDecoder" %>
<%--
  Created by IntelliJ IDEA.
  User: dexin.su
  Date: 2014/8/21
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
    if (request.getParameter("name") != null) {
        request.setAttribute("name", URLDecoder.decode(request.getParameter("name"), "utf-8"));
    }
%>
<head>
    <title>User Info</title>
    <style type="text/css">
        .div {
            table-layout: fixed;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            width: 160px;
        }
    </style>
    <link href="${pageContext.request.contextPath }/css/admin.global.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath }/css/admin.content.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.utils.js"></script>
    <link href="${pageContext.request.contextPath }/jBox/Skins/Green/jbox.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath }/jBox/jquery.jBox-2.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/admin.js"></script>
    <script type="text/javascript">

        // 设置标题
        Index.SetTitle('Lock List');
        // 获取包含当前查询条件的url
        function getPagerUrl() {
            var pagerUrl = "order?1=1";
            var name = $("#name").trim();
            if (name != '') {
                pagerUrl += "&name=" + encodeURI(encodeURI(name));
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
                    // 在这ajax处理
                    $.ajax({
                        cache: true,
                        type: "POST",
                        url: "${pageContext.request.contextPath}/order",
                        data: {"ids": id, "operation": "delete"},
                        dataType: "text",
                        async: false,
                        error: function (request) {
                            Login.Tip.Login("An unknown error has occurred!",function () {
                            });
                        },
                        success: function (data) {
                            var res = J.toObject(data);
                            if (res.res == 1) {
                                jBox.tip(res.msg, 'warning');
                                Index.Open("order");
                            } else {
                                jBox.tip(res.msg, 'warning');
                            }
                        }
                    });
                }
            });
        }

        $(function () {
            $("#search").click(function () {
                var name = $('#project').trim();
                var pageSize = $('#pageSize').trim();
                var url = 'order';
                url = J.FormatString(url + '?name={0}&pageSize={1}', encodeURI(encodeURI(name)), pageSize);
                Index.Open(url);
            });
            $("#deleteMulti").click(function () {
                var ids = CheckBox.GetCheckedIds();
                if (ids.length == 0) {
                    jBox.tip("Please select one information at least！", 'warning');
                    return;
                }
                jBox.confirm("The operation can't be restored,are you sure to delete the selected information?", "Delete information", function (v, h, f) {
                    if (v == 'ok') {
                        var id = "";
                        for (var i = 0; i < ids.length; i++) {
                            id += ids[i] + ",";
                        }
                        $.ajax({
                            cache: true,
                            type: "POST",
                            url: "${pageContext.request.contextPath}/autoSendServlet",
                            data: {"ids": id, "operation": "delete"},
                            dataType: "text",
                            async: false,
                            error: function (request) {
                                jBox.tip("An unknown error has occurred!", 'warning');
                            },
                            success: function (data) {
                                var res = J.toObject(data);
                                if (res.res == 1) {
                                    jBox.tip(res.msg, 'warning');
                                    Index.Open("autoSendServlet");
                                } else {
                                    jBox.tip(res.msg, 'warning');
                                }
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

    <div class="search block">
        <form>
            <div class="h">
                <span class="icon-sprite icon-magnifier"></span>

                <h3>Quick Search</h3>
            </div>
            <div class="corner"></div>
            <div class="cnt-wp">
                <div class="cnt">
                    <div class="search-bar">
                        <input type="hidden" id="pageIndex" name="pageIndex" value="${pagerInfo.pageIndex}">
                        <label class="first txt-green">Title：</label>
                        <input value="${name}" type="text" name="name" id="name" class="input-small"/>
                        <label class="txt-green">Per Page：</label>
                        <select name="pageSize" id="pageSize" class="small">
                            <option value="10"
                                    <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 10)}">selected="selected"</c:if>>
                                10
                            </option>
                            <option value="12"
                                    <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 12)}">selected="selected"</c:if>>
                                12
                            </option>
                            <option value="15"
                                    <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 15)}">selected="selected"</c:if>>
                                15
                            </option>
                        </select>
                        <label><a id="search" class="btn-lit btn-middle" onclick="searchForm();"
                                  href="javascript:;"><span>Search</span></a></label>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div class="blank10"></div>
    <div class="block">
        <div class="h">
            <span class="icon-sprite icon-list"></span>

            <h3>Project List</h3>

            <div class="bar" style="display: block;">
                <a class="btn-lit" href="javascript:Index.Open('order/ordermodify.jsp');"><span>Add</span></a>
                <a class="btn-lit" id="deleteMulti" href="javascript:;"><span>Delete item</span></a>
            </div>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt">
                <table class="data-table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                    <tr>
                        <th scope="col"><input value="true" type="checkbox" title="Select/Don't choose"
                                               onclick="CheckBox.CheckAll(this);" name="CheckAll"
                                               id="CheckAll"/><input value="false" type="hidden" name="CheckAll"/>
                        </th>
                        <th scope="col">Title</th>
                        <th scope="col">Code</th>
                        <th scope="col">Project</th>
                        <th scope="col">Customer</th>
                        <th scope="col">Model</th>
                        <th scope="col">Version</th>
                        <th scope="col">Imei</th>
                        <th scope="col">Modify</th>
                        <th scope="col">Delete</th>
                    </tr>
                    <c:forEach items="${beanList }" var="userinfo">
                        <tr>
                            <td class="chk">
                                <input value="true" type="checkbox" rel="${userinfo.id }" name="CheckBox"
                                       class="check-box"/><input value="false" type="hidden" name="CheckBox"/>
                            </td>
                            <td class="txt80 c">${userinfo.name}</td>
                            <td class="txt80 c">${userinfo.phoneOrder}</td>
                            <td class="txt80 c">${userinfo.project}</td>
                            <td class="txt80 c">${userinfo.customer}</td>
                            <td class="txt120 c">${userinfo.model}</td>
                            <td class="txt c">${userinfo.version}</td>
                            <td class="txt c">${userinfo.imei}</td>
                            <td class="icon">
                                <a class="opt" title="Modify"
                                   href="javascript:Index.Open('order/ordermodify.jsp?id=${userinfo.id}');"><span
                                        class="icon-sprite icon-edit"></span></a>
                            </td>
                            <td class="icon">
                                <a class="opt" title="Delete"
                                   href="javascript:DeleteById('${userinfo.id}');"><span
                                        class="icon-sprite icon-delete"></span></a>
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
