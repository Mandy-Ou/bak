<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 	String uuid = request.getParameter("uuid");
 	uuid = (null == uuid) ? "" : uuid;
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form name="theForm" action="../index.jsp" method="post">
<input type="text" name="uuid" value="<%=uuid%>">
</form>
<script type="text/javascript">
window.onload=function(){
	document.forms["theForm"].submit();
};
</script>
</body>
</html>