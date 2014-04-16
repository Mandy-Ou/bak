<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
	String ie=request.getHeader("User-Agent"); //得到浏览器等相关信息
	String doctypeUrl = "";
	if(ie.indexOf("MSIE")!=-1){//这里是msie 即微软 ie浏览器 (在IE下要加 loose.dtd，否则系统基础平台中的功能就会竖排拉的很长显示)
		doctypeUrl = "http://www.w3.org/TR/html4/loose.dtd";
	}  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "<%=doctypeUrl%>">
<!--<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  -->
<html> 
<head>

<meta http-equiv="X-UA-Compatible" content="IE=8" /> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>同心日小额平台</title>
  <link rel="stylesheet" type="text/css" href="./extlibs/ext-3.3.0/resources/css/ext-all.css"/>
  <link href="./extlibs/ext-3.3.0/resources/css/ext-extend.css" rel="stylesheet" type="text/css" />
  <link href="./extlibs/ext-3.3.0/resources/css/hint.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" type="text/css" href="./css/main.css" />
   <link rel="stylesheet" type="text/css" href="./extlibs/ext-3.3.0/resources/css/chooser.css" />
  <script type="text/javascript" src="./extlibs/ext-3.3.0/adapter/ext/ext-base-debug.js"></script>
  <script type="text/javascript" src="./extlibs/ext-3.3.0/ext-all-debug.js"></script>
  <script type="text/javascript" src="./js/ext-patch.js"></script>
   <script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script>
  <!-- 打印控件加载 -->
  <script language="javascript" src="./controls/lodop/LodopFuncs.js"></script>
  <object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
	<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="./controls/lodop/install_lodop32.exe"></embed>
  </object> 
</head>
<body scroll="no" onkeypress="makeEnter()">
<div class="oa_accordion_menus" id="oa_accordion_menus_20131129"></div>
</body>
	<script type="text/javascript">
	<%@ include file="./js/constant.js"  %> 
	</script>
	 <!-- DESKTOP -->
	 
<script type="text/javascript" src="./js/Ext.ux/ExtMain.js"></script>
<script type="text/javascript" src="./js/cmw_appDefine.js"></script>
<script>
Ext.BLANK_IMAGE_URL = "<%=basePath%>/images/public/s.gif";
	AppDef.loadJses(".");
	function makeEnter(){
		if(event.keyCode==13) return false;
	}
	
</script>
</html>