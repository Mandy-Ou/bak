<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath()+"/pages/desktop";
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
 <link rel="shortcut icon" href="loan.ico" /> 
  <link rel="stylesheet" type="text/css" href="./extlibs/ext-3.3.0/resources/css/ext-all.css"/>
  <link rel="stylesheet" type="text/css" href="./pages/desktop/css/desktop.css" />
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
<div id="x-desktop">
<iframe style="margin:5px; float:right;" width="420" scrolling="no" height="60" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=12&color=%2300B0F0&icon=1&py=guangzhou&num=5"></iframe>
<!--  <iframe style="margin:5px; float:right;" allowtransparency="true" frameborder="0" width="200" height="250" scrolling="no" src="txrClock.html"></iframe> -->
 
   <!-- <a href="http://extjs.com" target="_blank" style="margin:5px; float:right;" title="同心日信息科技责任有限公司"><img src="<%=basePath%>/images/TXR70.png" /></a>
    --> 
	 <!-- 此处的图标是左对齐，从后台加载的的应用程序 -->
	
    <div id="x-appsys-shortcuts">
     	<!-- 
		 <dl  class="x-shortcuts" style="float:left;">
    	 <dt id="3-win-shortcut">  
    	 	  <a href="#"><img src="./pages/desktop/images/s.gif" />    
    	 	 	<div>微金融贷款系统</div>
    	 	 	</a>
    	 	</dt> 
    	 	<dt id="2-win-shortcut"> 
    	 		   <a href="#"><img src="./pages/desktop/images/s.gif" />   
    	 		   <div>企业协同办公系统</div>
    	 		   </a>
    	 	</dt> 
    	 	<dt id="1-win-shortcut">  
    	 		<a href="#"><img src="./pages/desktop/images/s.gif" /> 
    	 			<div>系统基础平台</div>
    	 	   </a>
    	 	</dt>
    	</dl>
    	  --> 
    </div>
    <!-- 此处的图标是右对齐，放不从后台加载的固定的应用程序 -->
    <!-- 
    <div id="x-appfix-shortcuts">
    	<dl class="x-shortcuts" >
	      <dt id="acc-win-shortcut">
	          <a href="#"><img src="<%=basePath%>/images/s.gif" />
	          <div>Web QQ </div></a>
	      </dt>
    	</dl>
    </div>
     -->
     
     <!-- 
       <dl id="x-shortcuts" style="float : left;">
        <dt id="oa-win-shortcut">
            <a href="#"><img src="<%=basePath%>/images/s.gif" />
            <div>OA系统</div></a>
        </dt>
        <dt id="acc-win-shortcut">
            <a href="#"><img src="<%=basePath%>/images/s.gif" />
            <div>Web QQ </div></a>
        </dt>
          <dt id="smartplatform-win-shortcut">
            <a href="#"><img src="<%=basePath%>/images/s.gif" />
            <div>智能平台</div></a>
        </dt>
    </dl>
    <dl id="x-shortcuts" style="float :right;">
      <dt id="acc-win-shortcut">
          <a href="#"><img src="<%=basePath%>/images/s.gif" />
          <div>Web QQ </div></a>
      </dt>
    </dl>
     -->
     
</div>

<div id="ux-taskbar">
	<div id="ux-taskbar-start"></div>
	<div id="ux-taskbuttons-panel"></div>
	<div class="x-clear"></div>
</div>

</body>
	<script type="text/javascript">
	<%@ include file="./js/constant.js"  %> 
	</script>
	 <!-- DESKTOP -->
	 
  <script type="text/javascript" src="./js/Ext.ux/AppTabTreeWin.js"></script>
  <script type="text/javascript" src="<%=basePath%>/js/StartMenu.js"></script>
  <script type="text/javascript" src="<%=basePath%>/js/TaskBar.js"></script>
  <script type="text/javascript" src="<%=basePath%>/js/Desktop.js"></script>
  <script type="text/javascript" src="<%=basePath%>/js/App.js"></script>
  <script type="text/javascript" src="<%=basePath%>/js/Module.js"></script>
  <script type="text/javascript" src="<%=basePath%>/Sysinit.js"></script>

	<script type="text/javascript" src="./js/cmw_appDefine.js"></script>
	<script>
	AppDef.loadJses(".");
	function makeEnter(){
		if(event.keyCode==13) return false
		
	}
</script>
</html>
