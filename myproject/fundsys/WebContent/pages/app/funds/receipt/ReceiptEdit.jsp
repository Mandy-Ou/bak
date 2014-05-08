<%@page import="com.cmw.core.util.StringHandler"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.cmw.service.impl.funds.ReceiptServiceImpl"%>
<%@page import="com.cmw.entity.funds.ReceiptEntity"%>
<%@page import="com.cmw.service.inter.funds.ReceiptService"%>
<%@page import="com.cmw.core.util.SHashMap"%>
<%@page import="java.util.*"%>
<jsp:directive.page import="org.springframework.web.context.WebApplicationContext"/>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
	Long id = null;
	String name = "";
	String reman = "";
	String rcount = "";
	String rnum = "";
	String outMan = "";
	String omaccount ="";
	String pbank = "";
	String outDate = "";
	String endDate = "";
	BigDecimal amount = new BigDecimal(0);
	String rtacname = "";
	String rtaccount = "";
	String rtbank = "";
	String recetDate = "";
	String rmonth = "";
	String rday = "";

	String idVal = request.getParameter("id");
	String orgtype = request.getParameter("orgtype");
	if( null != orgtype && ("2".equals(orgtype)||"3".equals(orgtype))){
	WebApplicationContext wc = (WebApplicationContext)this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	
	ReceiptEntity receiptEntity = null;
	if(null != idVal && !"".equals(idVal)){
		id = Long.parseLong(idVal);
		ReceiptService receiptService = (ReceiptService)wc.getBean("receiptService");		
		SHashMap params=new SHashMap();
		params.put("id", id);
		receiptEntity = receiptService.getEntity(params);
		name = receiptEntity.getName();
		reman = receiptEntity.getReman();
		rcount = receiptEntity.getRcount();
		rnum = receiptEntity.getRnum();
		outMan = receiptEntity.getOutMan();
		omaccount = receiptEntity.getOmaccount();
		pbank = receiptEntity.getPbank();
		outDate = StringHandler.dateFormatToStr("yyyy-MM-dd", receiptEntity.getOutDate());
		endDate = StringHandler.dateFormatToStr("yyyy-MM-dd",receiptEntity.getEndDate());
		amount = receiptEntity.getAmount();
		rtacname = receiptEntity.getRtacname();
		rtaccount = receiptEntity.getRtaccount();
		rtbank = receiptEntity.getRtbank();
		if(null != receiptEntity.getRecetDate()){
			String time  = receiptEntity.getRecetDate().toString();
			recetDate = time.split("-")[0];
			rmonth = time.split("-")[1];
			rday = time.split("-")[2]; 
			}
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/cmw_core.js"></script>
<%-- <script type="text/javascript" src="<%=basePath%>/extlibs/ext-3.3.0/adapter/ext/ext-base.js" ></script>
<script type="text/javascript" src="<%=basePath%>/extlibs/ext-3.3.0/ext-all.js" ></script>
<script type="text/javascript" src="<%=basePath%>/js/cmw_core.js"></script> --%>
<script type="text/javascript" src="<%=basePath%>/controls/My97DatePicker/WdatePicker.js" ></script><!-- 日期my97date日期控件author:李听 -->
<script type="text/javascript" src="<%=basePath%>/js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.validate.messages_cn.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.validate.expand.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/card.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/jquery.validate.css">

<body>
<style type="text/css">
body{background-color:white;}
form{text-align:center}
li{float : left; list-style-type:none;}
input{ 
	width : 200px;color:black; border:0px; /* #005aa7:浅蓝色*/
	border-bottom:1px solid black; /* 下划线效果 */ 
	background-color:transparent; /* 背景色透明 */ 
}
.name{ width : 50px;}
.txt-right{text-align : right}/*让每行的第一列靠右显示*/
table{margin:0 auto}/*让表单进行居中显示  */
.time{width : 30px}/*最后的年月日的宽度*/
#id{visibility : hidden}
</style>
	<form method="post" id="receipt" name="theForm">
		<h1 style="text-align:center">收条</h1>
		<input type="text" id="id" name="name" value=<%=id%>>
		<table>
			<tr >
				<td colspan="10">
                	<span><input type = "text" id="reman" name="reman" class = "name" class="{required:true,maxlength:50}" value=<%= reman %>></span>收到
					<span><input type = "text" id="name" name="name" class = "name" class="{required:true,maxlength:50}" value=<%=name%>></span>银行承兑汇票
					<span><input type = "text" id="rcount" name="rcount" style="width:50px;" class="{required:true,maxlength:10}" value = <%=rcount%>></span>张，票面要素如下： 
				</td>
			</tr>
			<tr bordercolor="red">
				<td class="txt-right" >票号：</td>
				<td><input type="text" id = "rnum" name = "rnum" class="{required:true,maxlength:30}" value="<%=rnum%>"></td>
			</tr>
			<tr>
				<td  class="txt-right">出票人：</td>
				<td><input type="text" id = "outMan" name = "outMan" class="{required:true,maxlength:80}" value="<%=outMan%>"></td>
			</tr>
			<tr>
				<td  class="txt-right">出票人账号：</td>
				<td><input type="text" id = "omaccount" name = "omaccount" class="{required:true,maxlength:30}" value="<%=omaccount%>"></td>
			</tr>
			<tr>
				<td  class="txt-right">付款行：</td>
				<td><input type="text" id = "pbank" name = "pbank" class="{required:true,maxlength:80}" value="<%=pbank%>"></td>
			</tr>
			<tr>
				<td  class="txt-right">出票日期：</td>
				<td><input type="text" id = "outDate"  name = "outDate" onclick = "WdatePicker()" class="{required:true,dateISO : true}" value="<%=outDate%>"></td>
			</tr>
			<tr>
				<td class="txt-right" >到票日期：</td>
				<td><input type="text" id = "endDate" name = "endDate" onclick = "WdatePicker()" class="{required:true,dateISO : true }" value="<%=endDate%>"></td>
			</tr>
			<tr>
				<td class="txt-right" >金额：</td>
				<td><input type="text" id = "amount" name = "amount" class="{required:true,number : true}" value="<%=amount %>"></td>
			</tr>
			<tr>
				<td class="txt-right">收款人账户名：</td>
				<td><input type="text" id = "rtacname" name = "rtacname" class="{required:true,maxlength:80}" value="<%=rtacname %>"></td>
			</tr>
			<tr>
				<td class="txt-right" >收款人账号：</td>
				<td><input type="text" id = "rtaccount" name = "rtaccount" class="{required:true,maxlength:30}" value="<%=rtaccount%>"></td>
			</tr>
			<tr>
				<td class="txt-right">收款人开户银行全称：</td>
				<td><input type="text" id = "rtbank" name = "rtbank" class="{required:true,maxlength:80}" value="<%=rtbank%>"></td>
			</tr>
		</table>
		<p style="margin-left : 100px;"><span style="color: red">注：</span>此收条款到后自动作废。</p>	
		
		<p style="margin-left : 550px;"><input type="text" class="time" onclick = "WdatePicker()" id="myYear" value=<%=recetDate%>>年<input type="text" class="time"  id="myMonth" readonly="readonly" value = <%=rmonth %>>月<input type="text" class="time" id="myDay" readonly="readonly" value=<%=rday%>>日</p>		
	   <input type="text" id = "time" style="visibility:hidden" />	</form>
</body>
</html>
<!-- 进行表单数据验证 -->
<script type="text/javascript">
//表单验证
	$(function(){
			$("#receipt").validate();
	});
</script>
<script type="text/javascript">
	$(document).ready(function(){
		//对input进行设置值和修改值
		var orgtype = '<%=request.getParameter("orgtype")%>';
		if(orgtype && 3 != orgtype) return;
		$("input").attr("readonly","readonly"); //查看详情时让数据只读
		
		var td = $("td");
		var span = $("span");
		span.change(tdchange);
		td.change(tdchange);
	});
	
	function tdchange(){
		//0.保存当前的input节点
		if($(this).nodeName == "SPAN"){
			var span = $(this).children()[0];
			var spanText = span.value;
			$(span).html(spanText);
			// .btn{ 
			//	background-color:transparent; /* 背景色透明 */ 
			//	border:0px; /*border:0px solid #005aa7;边框取消 */ 
			//	cursor:pointer; 
			//}  
			
			$(span).css({"background-color":"transparent","border":"0px","cursor":"pointer"});	//1:背景色透明,2:边框取消 ,3:鼠标移动上变为手的样式的css
			$(span).unbind("change");//移除事件
		}else{
			var input = $(this).children()[0];
			//1.取出当前td中的文本内容保存起来
			var text = input.value;
			//2.设置当前的input中li父节点里面的内容
			$(input).html(text);
			$(span).css({"background-color":"transparent","border":"0px","cursor":"pointer"});
			//$(input).css("text-decoration","underline");	//添加下横线
			//$(input).click(getValueFromJsp);
			$(input).unbind("change");
		}	
	 }
	<%  String parentContainerId = request.getParameter("parentContainerId");%>
	parent.Cmw.unmask("<%=parentContainerId%>");
	
	
</script>
<!--  	//获取form表单中的数据
	function getValueFromJsp(){
		var form = document.forms[0];
		var reman = form.getElementsByTagName("input");
		for(var i = 0;i<reman.length;i++){
			alert(reman[i].value);
		}
	
	function timeValue(){
		var value = $(this).val();
		alert(value);
	-->