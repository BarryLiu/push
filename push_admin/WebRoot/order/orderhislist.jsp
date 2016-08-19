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
            var pagerUrl = "lockHis?1=1";
            var imei = $("#imei").trim();
            if (imei != '') {
                pagerUrl += "&imei=" + encodeURI(encodeURI(imei));
            }
            var project = $("#project").trim();
            if (project != '') {
                pagerUrl += "&project=" + encodeURI(encodeURI(project));
            }
            var customer = $("#customer").trim();
            if (customer != '') {
                pagerUrl += "&customer=" + encodeURI(encodeURI(customer));
            }
            var version = $("#version").trim();
            if (version != '') {
                pagerUrl += "&version=" + encodeURI(encodeURI(version));
            }
            var model = $("#model").trim();
            if (model != '') {
                pagerUrl += "&model=" + encodeURI(encodeURI(model));
            }
            var status = $("#status").trim();
            if (status != '') {
                pagerUrl += "&status=" + encodeURI(encodeURI(status));
            }
            pagerUrl += "&pageIndex={0}&pageSize={1}";
            return pagerUrl;
        }

        function getFormatPagerUrl() {
            return J.FormatString(getPagerUrl(), "${pagerInfo.pageIndex}", "${pagerInfo.pageSize}");
        }

        $(function () {
            $("#search").click(function () {
                var name = $('#project').trim();
                var pageSize = $('#pageSize').trim();
                var url = 'lockHis?1=1';
                var imei = $("#imei").trim();
                if (imei != '') {
                    url += "&imei=" + encodeURI(encodeURI(imei));
                }
                var project = $("#project").trim();
                if (project != '') {
                    url += "&project=" + encodeURI(encodeURI(project));
                }
                var customer = $("#customer").trim();
                if (customer != '') {
                    url += "&customer=" + encodeURI(encodeURI(customer));
                }
                var version = $("#version").trim();
                if (version != '') {
                    url += "&version=" + encodeURI(encodeURI(version));
                }
                var model = $("#model").trim();
                if (model != '') {
                    url += "&model=" + encodeURI(encodeURI(model));
                }
                var status = $("#status").trim();
                if (status != '') {
                    url += "&status=" + encodeURI(encodeURI(status));
                }
                url = J.FormatString(url + '&pageSize={0}',pageSize);
                Index.Open(url);
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
                        <label class="first txt-green">project：</label>
                        <select id="project" name="project" class="small" style="width: auto">
                            <option value="">-Please Select-</option>
                            <c:forEach items="${proMap}" var="pro">
                                <option value="${pro.key}"
                                        <c:if test="${project==pro.key}">selected="selected" </c:if>>${pro.value}</option>
                            </c:forEach>
                        </select>
                        <label class="first txt-green">Customer：</label>
                        <select id="customer" name="customer" class="small" style="width: auto">
                            <option value="">-Please Select-</option>
                            <c:forEach items="${cusMap}" var="cus">
                                <option value="${cus.key}"
                                        <c:if test="${customer==cus.key}">selected="selected" </c:if>>${cus.value}</option>
                            </c:forEach>
                        </select>
                        <label class="first txt-green">Status：</label>
                        <select id="status" name="status" class="small" style="width: auto">
                            <option value="">-Please Select-</option>
                            <option value="解锁"
                                    <c:if test="${status=='解锁'}">selected="selected" </c:if>>解锁
                            </option>
                            <option value="上锁"
                                    <c:if test="${status=='上锁'}">selected="selected" </c:if>>上锁
                            </option>
                        </select>
                        <br>
                        <label class="first txt-green">Model：</label>
                        <input value="${model}" type="text" name="model" id="model" class="input-small"/>
                        <label class="first txt-green">Version：</label>
                        <input value="${version}" type="text" name="version" id="version" class="input-small"/>
                        <label class="first txt-green">Imei：</label>
                        <input value="${imei}" type="text" name="imei" id="imei" class="input-small"/>
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
            </div>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt">
                <table class="data-table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                    <tr>
                        <th scope="col">Project</th>
                        <th scope="col">Customer</th>
                        <th scope="col">Model</th>
                        <th scope="col">Version</th>
                        <th scope="col">Imei</th>
                        <th scope="col">Status</th>
                    </tr>
                    <c:forEach items="${beanList }" var="userinfo">
                        <tr>
                            <td class="txt80 c">${userinfo.project}</td>
                            <td class="txt80 c">${userinfo.customer}</td>
                            <td class="txt120 c">${userinfo.model}</td>
                            <td class="txt c">${userinfo.version}</td>
                            <td class="txt c">${userinfo.imei}</td>
                            <td class="txt c">${userinfo.status}</td>
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
