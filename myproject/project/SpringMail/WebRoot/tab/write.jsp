<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<jsp:directive.page import="com.hygj.bean.UsersBean"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
body {
	margin-left: 5px;
	margin-top: 5px;
	margin-right: 5px;
	margin-bottom: 5px;
}
.STYLE1 {font-size: 12px}
.STYLE3 {font-size: 12px; font-weight: bold; }
.STYLE4 {font-size: 16px}
.STYLE6 {color: #666666}
-->
</style>


<link href="../css/pop.css" rel="stylesheet" type="text/css" />
</head>

<body>

<%
	UsersBean ub = (UsersBean)(session.getAttribute("user") != null ? session.getAttribute("user") : "");
%>

<form id="form1" name="form1" method="post" action="../infoAction.do?method=sendedMail">
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="d5d4d4" >

    <tr>
      <td align="left" bgcolor="#FFFFFF" class="STYLE1"><table width="100%" border="0" cellpadding="2">
        
        <tr>
          <td width="7%" align="center">发件人：</td>
          <td width="93%"><input name="fajianren" type="text" class="border" id="fajianren" size="35" value="<%= ub.getUsername() %>@sohu.com" /></td>
        </tr>
        <tr>
          <td align="center">收件人：</td>
          <td><input name="shoujianren" type="text" class="border" id="shoujianren" size="100%"  style="background-color:#FFFF99"  /></td>
        </tr>
        <tr>
          <td align="center">主题：</td>
          <td><input name="title" type="text" class="border" id="title" size="100%" /></td>
        </tr>
        <tr>
          <td align="center">&nbsp;</td>
          <td><p>
            <textarea name="content" cols="99%" rows="20" id="content"></textarea>
          </p>
            <p>&nbsp; </p></td>
        </tr>
        <tr>
          <td colspan="2"><hr width="100%" /></td>
          </tr>
        <tr>
          <td>&nbsp;</td>
          <td> &nbsp;&nbsp;
            <input name="Submit" type="submit" class="button" value="发送" style="font-family:'黑体'"  />
&nbsp;

        </tr>

      </table></td>
    </tr>
  </table>
</form>
</body>
</html>
