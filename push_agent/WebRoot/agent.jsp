<%@page import="java.io.FileWriter"%>
<%@page import="java.io.File"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
		String params = request.getParameter("data");
  		//File file = new File(application.getRealPath("/")+"log.txt");
  		// out.println("path="+file.getAbsolutePath());
  		try {
  			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
  			FileWriter writer = new FileWriter(application.getRealPath("/")+"log.txt", true);
  			writer.write(sFormat.format(new Date()) + ":" + params + "\n");
            writer.close();
  			out.print("{\"code\":\"00\"}");
  		} catch(Exception e) {
  			out.print("{\"code\":\"01\"}");
  		}
  		
  %>
  </body>
</html>
