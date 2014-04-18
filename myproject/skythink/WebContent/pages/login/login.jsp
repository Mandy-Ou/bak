<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
 	String type = request.getParameter("type");
 	type = (null == type) ? "" : type;
 	session  = request.getSession();
 	session.removeAttribute("user");  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>佛山市高明民汇小额贷款平台</title>
 <link rel="shortcut icon" href="../../loan.ico" /> 
<link rel="stylesheet" type="text/css" href="<%=basePath%>/extlibs/ext-3.3.0/resources/css/ext-all.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/main.css"/>
<script type="text/javascript" src="<%=basePath%>/extlibs/ext-3.3.0/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=basePath%>/extlibs/ext-3.3.0/ext-all.js"></script>
 </head>
<body>
<input type="hidden" name="logintype" id="logintype" value="<%=type%>" >
</body>
</html>
<script type="text/javascript">
<%@ include file="../../js/constant.js" %> 
</script>
<script type="text/javascript" src="<%=basePath%>/js/cmw_core.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/ext-patch.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/cmw_appDefine.js"></script>
<script type="text/javascript" src="<%=basePath%>/pages/login/login.js"></script>
<script>
AppDef.isLogin = true;	//设此页面为登录页面，只加载登录的JS相关的文件
AppDef.loadJses("<%=basePath%>");
</script>