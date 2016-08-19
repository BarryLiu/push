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
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/calender.js" ></script>  
    
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.utils.js"></script>
    <link href="${pageContext.request.contextPath }/jBox/Skins/Green/jbox.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath }/jBox/jquery.jBox-2.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/admin.js"></script>
    <script type="text/javascript">
    
        // 设置标题
        Index.SetTitle('Project Management');
        
        //判断是否已经登录
        if(J.isNullOrEmpty("${sessionScope.username}")) {
			alert('Please exit and re-opearate if you have not an operation for a long time!');
			parent.window.focus();
			parent.window.location.href="${pageContext.request.contextPath}/login.jsp";
		}
        
        // 获取包含当前查询条件的url
        function getPagerUrl() {
        	var pagerUrl = "terminal/projectManagement?1=1";
            var project = $("#project").trim();
            var customer = $("#customer").trim();
            if(project!=''){
                pagerUrl += "&project="+encodeURI(encodeURI(project));
            }
            if(customer!=''){
                pagerUrl += "&customer="+encodeURI(encodeURI(customer));
            }
            
			var BeginDate = $('#BeginDate').trim();
			var EndDate = $('#EndDate').trim();
            if(BeginDate!=''){
            	pagerUrl += "&BeginDate="+encodeURI(encodeURI(BeginDate));
            }
            if(EndDate!=''){
            	pagerUrl += "&EndDate="+encodeURI(encodeURI(EndDate));
            }

            var searchType = $("#searchType").trim();
            if(searchType!=''){
            	pagerUrl += "&searchType="+encodeURI(encodeURI(searchType));
            }
            pagerUrl += "&order="+$("#order").trim()+"&da="+$("#da").trim();
            pagerUrl += "&pageIndex={0}&pageSize={1}";
            return pagerUrl;
        }
        
        function getFormatPagerUrl() {
        	return J.FormatString(getPagerUrl(), "${pagerInfo.pageIndex}", "${pagerInfo.pageSize}");
        }

        function order(name){
            var order = $("#order").trim();
            var da = $("#da").trim();
            if(order==name&&da=='2'){
                $("#da").val("1");
            }else{
                $("#da").val("2");
            }
            $("#order").val(name);
            var url = getFormatPagerUrl();
            
            Index.Open(url);
        }

        // 根据ID删除信息
        function DeleteById(id) {
            jBox.confirm("The operation can't be restored,are you sure to delete?", "Delete information", function (v, h, f) {
                if (v == 'ok') {
                    jBox.tip('precessing...', 'loading');
 					// 在这ajax处理
 					dwr.deleteTestUserInfo(id,function (data){
                    	if(data=="success"){
		                    jBox.tip(' Delete successful!! ', 'success');
		                    Index.Open(getFormatPagerUrl());
                    	} else {
                    		jBox.tip(' Delete failed!! ', 'error');
                    	}
                    });                            
                }
                return true;
            });
        }
		
		$(function() {
            var order = $("#order").trim();
            var da = $("#da").trim();
            if(order!='') {
                if (da == '1') {
                    $("#tr_" + order).append("<img src='../images/up.png' alt=''/>");
                } else {
                    $("#tr_" + order).append("<img src='../images/down.png' alt=''/>");
                }
            }
			$("#search").click(function() {
            	var pageSize = $('#pageSize').trim();
            	var url = 'terminal/projectManagement?1=1';
                var project = $("#project").trim();
                var customer = $("#customer").trim();
				
                if(project!=''){
                    url += "&project="+encodeURI(encodeURI(project));
                }
                if(customer!=''){
                    url += "&customer="+encodeURI(encodeURI(customer));
                }

				var BeginDate = $('#BeginDate').trim();
				var EndDate = $('#EndDate').trim();
                if(BeginDate!=''){
                    url += "&BeginDate="+encodeURI(encodeURI(BeginDate));
                }
                if(EndDate!=''){
                    url += "&EndDate="+encodeURI(encodeURI(EndDate));
                }

                var searchType = $("#searchType").trim();
                if(searchType!=''){
                    url += "&searchType="+encodeURI(encodeURI(searchType));
                }
                
            	url = J.FormatString(url + '&pageSize={0}',pageSize);
            	Index.Open(url);
			});
			$("#deleteMulti").click(function(){
				var ids = CheckBox.GetCheckedIds();
            	if (ids.length == 0) {
                	jBox.tip("Please select one information at least！");
                	return;
            	}
            	jBox.confirm("The operation can't be restored,are you sure to delete the selected information?", "Delete information", function (v, h, f) {
                	if (v == 'ok') {
                    	jBox.tip('precessing...', 'loading');					
                    	// 在这ajax处理
                    	dwr.deleteTestUserInfos(ids,function (data){
                    		if(data=="success"){
		                    	jBox.tip(' Delete successful!! ', 'success');
		                    	Index.Open(getFormatPagerUrl());
                    		} else {
                    			jBox.tip(' Delete wrong!! ', 'error');
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
    <div class="location">Current Location：Terminal Management -&gt;Project Management</div>
    
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
                    <label class="txt-green">Search Type：</label>
                    <select name="searchType" id="searchType" >
                        <option value="1" <c:if test="${searchType eq 1}">selected="selected"</c:if>>Project->Customer->Model->Versions->Numbers</option>
                        <option value="2" <c:if test="${searchType eq 2}">selected="selected"</c:if>>Project->Customer->Model->Numbers</option>
                        <option value="3" <c:if test="${searchType eq 3}">selected="selected"</c:if>>Project->Customer->Numbers</option>
                        <option value="4" <c:if test="${searchType eq 4}">selected="selected"</c:if>>Project->Numbers</option>
                        <option value="5" <c:if test="${searchType eq 5}">selected="selected"</c:if>>Customer->Numbers</option>
                    </select>
                    <label class="first txt-green">Project：</label>
                    <input value="${project}" type="text" name="project" id="project" class="input-small" />
                    <label class="first txt-green">Customer：</label>
                    <input value="${customer}" type="text" name="customer" id="customer" class="input-small" />
                    
                    <label class="first txt-green">Registration Time：</label>
                    <input type="text" id="BeginDate" name="BeginDate" class="input-small"
                            	 onclick="SelectDate(this,'yyyy-MM-dd')" Style="cursor:pointer;width:70px" readonly="true" value="${BeginDate}"/>
                            	 ~
                    <input type="text" id="EndDate" name="EndDate" class="input-small"
                            	 onclick="SelectDate(this,'yyyy-MM-dd')" Style="cursor:pointer;width:70px" readonly="true" value="${EndDate}"/>
                            	 
                    <input type="hidden" name="order" id="order" value="${order}"/>
                    <input type="hidden" name="da" id="da" value="${da}"/>
                    <label class="txt-green">Per Page：</label>
                    <select name="pageSize" id="pageSize" class="small">
                        <option value="10" <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 10)}">selected="selected"</c:if>>10</option>
                        <option value="20" <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 20)}">selected="selected"</c:if>>20</option>
                        <option value="50" <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 50)}">selected="selected"</c:if>>50</option>
                        <option value="100" <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 100)}">selected="selected"</c:if>>100</option>
                        <option value="200" <c:if test="${(!empty pagerInfo.pageSize) and (pagerInfo.pageSize eq 200)}">selected="selected"</c:if>>200</option>
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
                        	<c:if test="${searchType!='5'}">
                            <th scope="col" ><div id="tr_product" style="cursor: pointer" onclick="order('product')">Project</div></th>
                            </c:if>                            
                        	<c:if test="${searchType!='4'}">
                            <th scope="col" ><div id="tr_customer" style="cursor: pointer" onclick="order('customer')">Customer</div></th>
                            </c:if> 
                        	<c:if test="${searchType<'3'}">
                            <th scope="col" ><div id="tr_model" style="cursor: pointer" onclick="order('model')">Model</div></th>
                            </c:if> 
                        	<c:if test="${searchType<'2'}">
                            <th scope="col" ><div id="tr_version" style="cursor: pointer" onclick="order('version')">Version</div></th>
                            </c:if>
                            <th scope="col" ><div id="tr_numbers" style="cursor: pointer" onclick="order('numbers')">Numbers</div></th>
                            <!-- 
                            <th scope="col" ><div id="tr_register_date" style="cursor: pointer" onclick="order('register_date')">Create Date</div></th>
                            <th scope="col" ><div id="tr_update_date" style="cursor: pointer" onclick="order('update_date')">Update Date</div></th>
                             -->
                        </tr>
                        <c:forEach items="${beanList }" var="project">
                        <tr>
                        	<c:if test="${searchType!='5'}">
				              <td class="txt80 c">
				              	<a href="javascript:Index.Open('terminal/terminalManagement?type=1&project='+encodeURI(encodeURI('${project.product }')));" title="Detail">
				              		${project.product }
				              	</a>
			              	  </td>
				          	</c:if>
                        	<c:if test="${searchType!='4'}">
				              <td class="txt80 c">
				              	<a href="javascript:Index.Open('terminal/terminalManagement?type=1&customer='+encodeURI(encodeURI('${project.customer }')));" title="Detail">
				              		${project.customer }
				              	</a>
				              </td>
			              	</c:if> 
                        	<c:if test="${searchType<'3'}">
				              <td class="txt120 c">
				              	<a href="javascript:Index.Open('terminal/terminalManagement?type=1&model='+encodeURI(encodeURI('${project.model }')));" title="Detail">
				              		${project.model }
				              	</a>	
				              </td>
				             </c:if> 
                        	<c:if test="${searchType<'2'}">
				              <td class="txt c">
				              	<a href="javascript:Index.Open('terminal/terminalManagement?type=2&version='+encodeURI(encodeURI('${project.version }')));" title="Detail">
				              		${project.version }
				              	</a>	
				              </td>
				            </c:if>
				              <td class="txt80 c">${project.numbers }</td>
				              <!-- 
				              <td class="txt200 c">${project.registerDate }</td>
				              <td class="txt200 c">${project.updateDate }</td>
				               -->
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