<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
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


<link href="<%=request.getContextPath() %>/css/pop.css" rel="stylesheet" type="text/css" />
</head>

<body>
<c:forEach items="${requestScope.list}" var="po">
<form id="form1" name="form1" method="post" action="../infoAction.do?method=sendedMail">
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="d5d4d4" >

    <tr>
      <td align="left" bgcolor="#FFFFFF" class="STYLE1"><table width="100%" border="0" cellpadding="2">
        
        <tr>
          <td width="7%" align="center">�����ˣ�</td>
          <td width="93%"><input name="fajianren" type="text" class="border" id="fajianren" size="35" value="${po.sender }" /></td>
        </tr>
        <tr>
          <td align="center">�ռ��ˣ�</td>
          <td><input name="shoujianren" type="text" class="border" id="shoujianren" size="100%"  style="background-color:#FFFF99" value="${po.recipients }"  /></td>
        </tr>
        <tr>
          <td align="center">���⣺</td>
          <td><input name="title" type="text" class="border" id="title" size="100%" value="${po.title }" /></td>
        </tr>
        <tr>
          <td align="center">&nbsp;</td>
          <td><p>
            <textarea name="content" cols="99%" rows="20" id="content" >${po.content }</textarea>
          </p>
            <p>&nbsp; </p></td>
        </tr>
     


      </table></td>
    </tr>
  </table>
</form>
</c:forEach>
</body>
</html>
