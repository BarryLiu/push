<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="Author" content="dexin.su@ragentek.com" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <title>Upgrade Management</title>
    <style type="text/css">
        .div { table-layout: fixed; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; width: 160px; }
    </style>
    
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
    
        var data = new Array();
        var result = "";
        var index = parent.layer.getFrameIndex(window.name);
        
        // 获取包含当前查询条件的url
        function getPagerUrl() {
        	var pagerUrl = "${pageContext.request.contextPath}/terminal/countryManagement?type=include";
            pagerUrl += "&countryName="+encodeURI(encodeURI(countryName))+"&ids="+encodeURI(encodeURI(result))+"&pageIndex={0}&pageSize={1}";
            return pagerUrl;
        }
        
        function getFormatPagerUrl() {
        	return J.FormatString(getPagerUrl(), "${pagerInfo.pageIndex}"+1, "${pagerInfo.pageSize}");
        }
        
        function submit(){
        parent.document.getElementById("countryName").value=result;
        parent.document.getElementById("showCountryName").innerHTML=result;
        parent.layer.close(index);
        }
        function cancle(){
        parent.layer.close(index);
        }
        function add(){
            $("#showSelect").html("");
            result = "";
            var ids = ""+CheckBox.GetCheckedIds();
            var ida = ids.split(",");
            for(var j=0;j<ida.length;j++){
            var n=0;
            for(var i=0;i<data.length;i++){
            if(ida[j]==data[i]){
            n++;
            }
            }
            if(n==0){
            data.push(ida[j]);
            }
            }
            for(var i=0;i<data.length;i++){
            if(data[i]!=""){
            $("#showSelect").append("<a onclick='' href='javascript:;'><span>"+data[i]+"</span></a>");
            result += data[i]+",";
            }
            }
            result = result.substr(0,result.length-1);
            $("result").val(result);
            parent.layer.iframeAuto(index);
        }
		
		$(function() {
		    $("#showSelect").html("");
            var ids = '${ids}';
            var ida = ids.split(",");
            for(var j=0;j<ida.length;j++){
            var n=0;
            for(var i=0;i<data.length;i++){
            if(ida[j]==data[i]){
            n++;
            }
            }
            if(n==0){
            data.push(ida[j]);
            }
            }
            for(var i=0;i<data.length;i++){
            $("#showSelect").append("<a onclick='' href='javascript:;'><span>"+data[i]+"</span></a>");
            result += data[i]+",";
            }
            result = result.substr(0,result.length-1);
            parent.layer.iframeAuto(index);
			$("#search").click(function() {
				var countryName = $('#countryName').trim();
            	var pageSize = $('#pageSize').trim();

            	var url = '${pageContext.request.contextPath}/terminal/countryManagement?type=include&ids='+encodeURI(encodeURI(result));
            	url = J.FormatString(url + '&countryName={0}&pageSize={1}', encodeURI(encodeURI(countryName)), pageSize);
            	window.location.href=url;
			});
		});
    </script>
</head>
<body>
<div class="container">
    
    <div class="blank10"></div>
    <div class="search block">
        <div class="h">
            <span class="icon-sprite icon-magnifier"></span>
            <h3>Quick Search</h3>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt">
                <div class="search-bar">                 
                    <label class="txt-green">Country Name：</label>
                    <select name="countryName" id="countryName" class="small" style="width: auto">
	                <option value="">-请选择-</option>
	                <c:forEach items="${countryMap}" var="country">
	                <option value="${country.key}" <c:if test="${country.key==countryName}">selected="selected"</c:if>>${country.key}</option>
	                </c:forEach>
                    </select> 
                    <label class="txt-green">Per Page：</label>
                    <select name="pageSize" id="pageSize" class="small">
	                    <option value="10" <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 10)}">selected="selected"</c:if>>10</option>
	                    <option value="12" <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 12)}">selected="selected"</c:if>>12</option>
	                    <option value="15" <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 15)}">selected="selected"</c:if>>15</option>
                    </select>
                    <label><a id="search" class="btn-lit btn-middle" href="javascript:;"><span>Search</span></a></label>
                </div>
            </div>
        </div>
    </div>
                
    <div class="blank10"></div>
    <div class="block">
        <div class="h">
            <span class="icon-sprite icon-list"></span>
            <h3>Country List</h3>
            <div class="bar" style="display: block;">  
                  <a class="btn-lit" onclick="add();" href="javascript:;"><span>Add</span></a>
            </div>
        </div>
        <div class="tl corner"></div><div class="tr corner"></div><div class="bl corner"></div><div class="br corner"></div>
        <div class="cnt-wp">
            <div class="cnt">
                <table class="data-table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th scope="col">
                            <input value="true" type="checkbox" title="Select/Don't choose" onclick="CheckBox.CheckAll(this);" name="CheckAll" id="CheckAll" /><input value="false" type="hidden" name="CheckAll" /></th>
                            <th scope="col">Name</th>
                            <th scope="col">Numbers</th>
                            <th scope="col">Create Date</th>
                            <th scope="col">Update Date</th>
                        </tr>
                        <c:forEach items="${beanList}" var="project">
                        <tr>
                          <td class="chk">
			              <input value="true" type="checkbox" rel="${project.name }" name="CheckBox" class="check-box" /><input value="false" type="hidden" name="CheckBox" />
			              </td>
                          <td class="txt c">${project.name }</td>
			              <td class="txt100 c">${project.numbers }</td>
			              <td class="txt200 c">${project.registerDate }</td>
			              <td class="txt200 c">${project.updateDate }</td>
			            </tr>
			            </c:forEach>
                    </tbody>
                </table>
            </div>
                        
            <script type="text/javascript">
              Pager.Output1(getPagerUrl(), "${pagerInfo.pageSize}", "${pagerInfo.pageIndex}", "${pagerInfo.pageCount}", "${pagerInfo.recordCount}"); //(urlFormat, pageSize, pageIndex, pageCount, recordCount)
            </script>
        </div>
    </div>
    <div id="showSelect"></div>
    <div class="bar" style="display: block;">  
         <a class="btn-lit" onclick="submit();" href="javascript:;"><span>Submit</span></a>
         <a class="btn-lit" onclick="cancle();" href="javascript:;"><span>Cancle</span></a>
         <input type="hidden" value="" id="result"/>
    </div>
</div>
</body>
</html>