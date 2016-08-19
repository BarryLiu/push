<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>网站后台登陆</title>
    <link href="css/admin.global.css" rel="stylesheet" type="text/css" />
    <link href="css/admin.content.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="js/jquery.utils.js"></script>
    <link href="jBox/Skins/Green/jbox.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="jBox/jquery.jBox-2.3.min.js"></script>
    <script type="text/javascript" src="js/admin.js"></script>
    <script type="text/javascript">
        // 只调用最顶的jBox
        if (top.jBox != undefined) {
            window.jBox = top.jBox;
        }

        Index.SetTitle('Home');
		if(J.isNullOrEmpty("${sessionScope.username}")) {
			Login.Tip.ReLogin(function() {
				parent.window.focus();
				parent.window.location.href="${pageContext.request.contextPath}/login.jsp";
			});
		}
    </script>
</head>
<body>
<div class="container">
    <div class="location">Current Location：Home</div>
    
    <table class="hide" width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td width="62" height="55" valign="middle"><img src="images/title.gif" width="54" height="55" alt="" /></td>
            <td valign="top" style="line-height:20px;">
                  	为了账号的安全，如果下面的登录情况不正常，建议您马上<a href="" class="txt-blue">修改密码</a>。
            </td>
        </tr>
    </table>

    <div class="blank10"></div>

    <div class="box">
        <div class="box-title txt-blue-b">Identity</div>
        <div class="box-content">
        	<i>Ordinary Administrator</i>
        </div>
    </div>
    
    <div class="blank10"></div>

    <div class="box">
        <div class="box-title txt-blue-b">Login situation</div>
        <div class="box-content">
            <i>Login IP：</i>${sessionScope.loginIP }<br />
            <i>Login Time：</i>${sessionScope.loginDate }<br />
        </div>
    </div>
    
    <div class="blank10"></div>

    <img src="images/ts.gif" style="margin-bottom:-2px;" width="16" height="16" alt="tip" /> Prompt：Please contact the administrator in time for security of account if login situation above is not normal.
    
    <div class="blank10"></div>
    <div class="line"></div>
    <div class="blank10"></div>

    <img src="images/icon-mail.gif" style="margin-bottom:-1px;" width="16" height="11" alt="mail" /> Contact：dexin.su@Ragentek.com<br />
    <img src="images/icon-phone.gif" style="margin-bottom:-2px;" width="17" height="14" alt="phone" /> Website：<a href="http://www.ragentek.com" target="_blank">http://www.ragentek.com</a>

</div>
</body>
</html>