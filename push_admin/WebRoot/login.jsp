<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>PUSH - Background Management System</title>
    <link href="css/admin.global.css" rel="stylesheet" type="text/css" />
    <link href="css/admin.login.css" rel="stylesheet" type="text/css" />
    
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>
    <script type='text/javascript' src='dwr/util.js'></script> 
	<script type='text/javascript' src='dwr/engine.js'></script> 
	<script type='text/javascript' src='dwr/interface/dwr.js'> </script>
	
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="js/jquery.utils.js"></script>
    <link href="jBox/Skins/Green/jbox.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="jBox/jquery.jBox-2.3.min.js"></script>
    <script type="text/javascript" src="js/admin.js"></script>
    
    <script type="text/javascript">
       	// 初始化下面的变量
       	Admin.IsLoginPage = true;
       	if(!J.isNullOrEmpty("${sessionScope.username}")) {
       		location.href = "index.jsp";
       	}
       	function login() {
       		var username = $("#username").trim();
       		var password = $("#password").trim();
       		if(Check.isEmptyOrLenTooLong(username, 30, "user name")) {
       			J.focus($("#username"));
       		} else if(Check.isEmptyOrLenTooLong(password, 30, "password")) {
       			J.focus($("#password"));
       		} else {
       			dwr.handleLogin(username, password, {
       				callback: function(res) {
       					if(res == 'success') {
       						location.href="index.jsp";
       					} else {
       						Login.Tip(res, 'error');
       					}
       				},
       				async: false,
       				timeout: 3000
       			});
       		}
       	}
       	$(function() {
       		$("#login_btn").click(function() {
       			login();
       		});
       		$("#reset_btn").click(function() {
       			$("form")[0].reset();
       		});
       		$("body").keydown(function() {
       			if(event.keyCode == 13) {
       				var id = event.srcElement.id;
       				if(id == "username") {
       					J.focus($("#password"));
       				} else if(id == "password" || id == "login_btn") {
       					login();
       				} else if(id == "reset_btn") {
       					event.srcElement.onClick();
       				}
       			}
       		});
       	});
    </script>
    
</head>
<body>
<table class="login" cellpadding="0" cellspacing="0">
    <!-- top -->
    <tr>
        <td class="login_top">
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="login_form_pad"></td>
                    <td class="login_title">PUSH Background Management System V1.0</td>
                </tr>
            </table>
        </td>
    </tr>
    <!-- middle -->
    <tr>
        <td class="login_middle_1"></td>
    </tr>
    <tr>
        <td valign="top" class="login_middle_2">
            <!-- login form -->
            <form method="post" action="check.jsp"  name="admininfo">
            <table id="login_form" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="login_form_pad"></td>
                    <td class="login_form_label">  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;User：</td>
                    <td><input type="text" name="username" id="username" class="login_input" value="" size="30" maxlength="30" /></td>
                </tr>
                <tr>
                    <td class="login_form_pad"></td>
                    <td class="login_form_label">Password：</td>
                    <td><input type="password" name="password" id="password" class="login_input" value="" size="30" maxlength="30" /></td>
                </tr>
                
                <tr>
                    <td class="login_form_pad"></td>
                    <td class="login_form_label"></td>
                    <td><a id="login_btn" class="btn" href="javascript:void(0);"><span>Login</span></a>
                        <a id="reset_btn" class="btn" href="javascript:void(0);"><span>Reset</span></a>
                    </td>
                </tr>
                <tr>
                    <td id="login_auto_height" colspan="3">&nbsp;</td>
                </tr>
            </table>
            </form>
        </td>
    </tr>
    <tr>
        <td class="login_middle_3"><div class="login_welcome"></div></td>
    </tr>
    <!-- bottom -->
    <tr>
        <td class="login-bottom">
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="login_form_pad"></td>
                    <td>Copyright &copy; 2005-2012 www.kudystudio.com All Rights Reserved.</td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<script type="text/javascript" language="javascript">
	/*
	//系统登录
	function subMitToSys(){	 
	//window.open("index.jsp","_bank","");
		check();	    
	}
	function check() {	 
      if(checkspace(document.admininfo.username.value)) {
        document.admininfo.username.focus();
        Login.Tip('The user name can not be empty!', 'warning');
        return false;
      }else  if(checkspace(document.admininfo.password.value)) {
        document.admininfo.password.focus();
        Login.Tip('The password can not be empty！', 'warning');
        return false;
      }else { 
     	 Login.Tip('Logging In...', 'loading'); 
     	 dwr.engine.setAsync(false);
     	 dwr.loginOTASys(document.admininfo.username.value,document.admininfo.password.value,function (data){
     	 		if(data!="1"){
	 		 		Login.Tip(data, 'error');
			 	}
			 	else{
			 		 Login.Tip('Login is successful,Jumping...', 'loading'); 
			 		// var scrwidth=window.screen.availWidth-10;
			 		// var scrheight=window.screen.availHeight-50;			 		
			 		// window.open("index.jsp","_blank","width="+scrwidth+",height="+scrheight+",top=0,left=0,scrollbars=yes,resizable=no,status=yes");
			 		window.location.href="index.jsp";
			 		//window.setTimeout(function () {
			 		 //	window.opener=null;
			 		 	//	window.open('','_self');
			 		 //	window.close();
		               //    }, 50); 
		             dwr.engine.setAsync(true);
			 	}
     	 });
                
     }
	}
	function LoginResult(data){	
	 	
	}
	function checkspace(checkstr) {
	  var str = '';
	  for(i = 0; i < checkstr.length; i++) {
	    str = str + ' ';
	  }
	  return (str == checkstr);
	}
	//重置
	function resetContent(){
		document.admininfo.reset();
	}
	document.body.onkeydown=_onclick
	function _onclick(){
	  	if (event.keyCode=="13") {
	       if (event.srcElement.name=="username") {
	          document.admininfo.password.focus();
	          return (false);
	       }
	       else{
	          check(); 
	       // Login.Tip("Click the Login button,Please.",'info');
	        // return (false);
	       }
	  	}
	}
	*/
</script>
</body>
</html>