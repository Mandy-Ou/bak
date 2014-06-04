<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE2 {
	font-size: 12px;
	color: #03515d;
}
a:link {font-size:12px; text-decoration:none; color:#03515d;}
a:visited{font-size:12px; text-decoration:none; color:#03515d;}
-->
</style>
<link href="css/pop.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE4 {
	font-size: 14px;
	font-family: "黑体";
}
.STYLE5 {font-size: 14px}
-->
</style>
</head>

<body>
<table width="156" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" valign="top"><table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="font">
      <tr>
        <td height="33" background="images/main_21.gif">&nbsp;</td>
      </tr>
      <tr>
        <td height="20"><form name="form1" method="post" action="" target="I2">
          <table width="155" border="0" class="borders">
            <tr>
              <td align="center"><img src="images/top_15.gif" width="20" height="20" border="0"><span class="STYLE4"><a href="shoujianxiang.html" target="I2">收信</a></span></td>
              <td><img src="images/top_6.gif" width="14" height="14"><span class="STYLE4"><a href="writemail.html" target="I2">写信</a></span></td>
            </tr>
          </table>
                </form>
        </td>
      </tr>
      <tr>
        <td align="center" valign="top"><table width="145" border="0" align="center" cellpadding="0" cellspacing="0">
          
          <tr>
            <td><table width="130" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="41" height="35"><div align="center"><img src="images/left_1.gif" width="31" height="31"></div></td>
                  <td width="89" height="35"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="23" style="cursor:hand" onMouseOver="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"><span class="STYLE2">&nbsp;<a href="shoujianxiang.html" target="I2">收件箱(${requestScope.shoujian})</a></span></td>
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td width="41" height="35"><div align="center"><img src="images/left_2.gif" width="31" height="31"></div></td>
                  <td height="35"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="23" style="cursor:hand" onMouseOver="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"><span class="STYLE2">&nbsp;<a href="caogaoxiang.html" target="I2">草稿箱(${requestScope.caogao})</a></span></td>
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td width="41" height="35"><div align="center"><img src="images/left_3.gif" width="31" height="31"></div></td>
                  <td height="35"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="23" style="cursor:hand" onMouseOver="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"><span class="STYLE2">&nbsp;<a href="yifasong.html" target="I2">已发送(${requestScope.yifa})</a></span></td>
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td width="41" height="35"><div align="center"><img src="images/left_4.gif" width="31" height="31"></div></td>
                  <td height="35"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="23" style="cursor:hand" onMouseOver="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"><span class="STYLE2">&nbsp;<a href="yishanchu.html" target="I2">已删除(${requestScope.delete})</a></span></td>
                    </tr>
                  </table></td>
                </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="22">&nbsp;</td>
      </tr>
      <tr>
        <td height="22">&nbsp;</td>
      </tr>
      <tr>
        <td height="22">&nbsp;</td>
      </tr>
      <tr>
        <td height="39" background="images/main_37.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
