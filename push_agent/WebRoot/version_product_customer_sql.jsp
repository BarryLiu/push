<%@page import="com.rgk.push.util.StringUtil"%>
<%@page import="com.mysql.jdbc.StringUtils"%>
<%@page import="com.rgk.push.dbbean.TClient"%>
<%@page import="com.rgk.push.util.DBUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.io.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
			BufferedReader br = new BufferedReader(new FileReader(new File(
					"D:/out_result.txt")));
			String temp = br.readLine();
			BufferedWriter boResult = null;
			try {
				boResult = new BufferedWriter(new FileWriter(new File("D:/out_result1.txt")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			while (temp != null) {

				try {
					String imei = temp.split(";")[0];
					String ip = temp.split(";")[1];
					String country = temp.split(";")[2];
					
					String sql = "update t_client set ip='" + ip + "', country_name='" + country + "' where imei='" + imei + "';";
					//out.println(sql + "<br>");

					boResult.write(sql);
					boResult.write("\n");
				} catch (Exception e) {
				}
				temp = br.readLine();
			}
			br.close();
			boResult.close();
		} catch (Exception e) {
		}
  %>
</body>
</html>
