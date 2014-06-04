<%@ page language="java" import="java.util.*" pageEncoding="GBk"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<style type="text/css">
<!--
.style1 {
	font-family: Geneva, Arial, Helvetica, sans-serif;
	font-size: 14px;
}
.style2 {color: #6699CC}
-->
</style>
</head>

<body>
<span class="style1">尊敬的<span class="style2"><strong>${requestScope.user }</strong></span>，您的<a href="shoujianxiang.html" target="I2" class="style2">收件箱</a>有（<span class="style2">${requestScope.wel }</span>）封邮件，其中（<span class="style2">${requestScope.come }</span>）封未读，请及时查收。
</span>
</body>
</html>
