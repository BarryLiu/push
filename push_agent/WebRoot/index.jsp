<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
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
    This is my JSP page. <br>
    <form action="/push_agent/agent" method="post">
    	<input type="text" value="我是一个中文">
    	<input type="hidden" value="{name:'c_regist',details:{uuid:'8888888888',sim1:'18721743188',sim2:'55555555',imei:'086222333666444',model:'G101',version:'G101_V0.0.1_S20140101',local:'zh_cn',c_version:'1'}}" name="data1"/>
    	<input type="hidden" value="{name:'c_get_msg',details:{uuid:'rrttyu'}}" name="data"/>
    	<input type="hidden" value="{name:'c_update_text',details:{server_id:'1',status:'31',date:'2014-04-30 13:50:10.438'}}" name="data3"/>
    	<input type="submit" value="Submit" />
    </form>
  </body>
</html>
