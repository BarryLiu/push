<%@page import="com.rgk.push.util.StringUtil"%>
<%@page import="com.mysql.jdbc.StringUtils"%>
<%@page import="com.rgk.push.dbbean.TClient"%>
<%@page import="com.rgk.push.util.DBUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.io.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>接口测试</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>

<body>
	<%
		try {

			String sql = "SELECT * FROM t_client";
			List<TClient> lstData = DBUtils.query(sql, TClient.class);
			for (TClient item : lstData) {

				// 删除有乱码的数据或为空的数据
				if (item.getDeviceVersion() == null
						|| "".equals(item.getDeviceVersion())
						|| item.getDeviceModel() == null
						|| "".equals(item.getDeviceModel())
						|| StringUtil.isMessyCode(item.getDeviceVersion())
						|| StringUtil.isMessyCode(item.getDeviceModel())) {
					DBUtils.delete(item);
					continue;
				}

				// 更新项目名、客户名称
				String version = item.getDeviceVersion();
				if (version != null
						&& !"".equals(version)
						&& (item.getDeviceCustomer() == null || ""
								.equals(item.getDeviceCustomer())
								&& (item.getDeviceProduct() == null || ""
										.equals(item.getDeviceProduct())))) {
					String arrTmp[] = version.split("_");
					if (arrTmp != null && arrTmp.length > 2) {
						item.setDeviceProduct(arrTmp[0].trim());
						item.setDeviceCustomer(arrTmp[1].trim());
					}
				}

				// 更新国家语言
				String countryName = item.getCountryName();
				if (countryName != null && !"".equals(countryName)) {
					String arrTmp[] = countryName.split("-");
					if (arrTmp != null && arrTmp.length == 2) {
						item.setLanguage(arrTmp[0].trim());
					}
				}
				DBUtils.update(item);
			}
		} catch (Exception e) {

		}
	%>
</body>
</html>
