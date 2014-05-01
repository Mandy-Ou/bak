<%@page import="java.util.HashMap"%>
<%@page import="com.cmw.service.inter.funds.ReceiptService"%>
<%@page import="com.cmw.entity.funds.ReceiptEntity"%>
<%@page import="com.cmw.service.inter.funds.RqueryApplyService"%>
<%@page import="com.cmw.entity.funds.RqueryApplyEntity"%>
<%@page import="java.util.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Date"%>
<%@page import="com.cmw.core.util.SHashMap"%>
<%@page import="com.cmw.core.util.StringHandler"%>
<jsp:directive.page import="org.springframework.web.context.WebApplicationContext"/>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String basePath = request.getContextPath();
	String receiptIds = request.getParameter("receiptId");
	String idVal = request.getParameter("id");
	String orgtype = request.getParameter("orgtype");
	Long id = null;
	String qbank = "";
	String aorg = "";
	String account = "";
	String rnum = "";
	BigDecimal amount = new BigDecimal(0.00);
	String payMan = "";
	String rtacname = "";
	String pbank = "";
	String bankNum = "";
	String signOrg = "";
	String appDate = "";
	String amonth = "";
	String aday = "";
	
	Long receiptId = null;
	String count = request.getParameter("rcount");
	
	
	RqueryApplyEntity rqueryApplyEntity = null;
	ReceiptEntity receiptEntity = null;
	WebApplicationContext wc = (WebApplicationContext)this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	if(null != idVal && !"".equals(idVal)){
		if( null != orgtype && "3".equals(orgtype)){
		id = Long.parseLong(idVal);
		RqueryApplyService rqueryApplyService = (RqueryApplyService)wc.getBean("rqueryApplyService");		
		SHashMap params=new SHashMap();
		params.put("id", id);
		rqueryApplyEntity = rqueryApplyService.getEntity(params);
		qbank = rqueryApplyEntity.getQbank();
		aorg = rqueryApplyEntity.getAorg();
		account = rqueryApplyEntity.getAccount();
		rnum = rqueryApplyEntity.getRnum();
		amount = rqueryApplyEntity.getAmount();
		payMan = rqueryApplyEntity.getPayMan();
		rtacname = rqueryApplyEntity.getRtacname();
		pbank =rqueryApplyEntity.getPbank();
		bankNum = rqueryApplyEntity.getBankNum();
		signOrg = rqueryApplyEntity.getSignOrg();
		if( null != rqueryApplyEntity.getAppDate()){
			String time = rqueryApplyEntity.getAppDate().toString();
			appDate = time.split("-")[0];
			amonth = time.split("-")[1];
			aday = time.split("-")[2];
		}
		receiptId = rqueryApplyEntity.getReceiptId();
		ReceiptService receiptService = (ReceiptService)wc.getBean("receiptService");
		SHashMap param = new SHashMap();
		param.put("id", receiptId);
		receiptEntity = receiptService.getEntity(param);
		count = receiptEntity.getRcount();
		}
	}
	if( null != receiptIds && !"".equals(receiptId)){
		RqueryApplyService rqueryApplyService = (RqueryApplyService)wc.getBean("rqueryApplyService");		
		SHashMap params=new SHashMap();
		params.put("receiptId", Long.parseLong(receiptIds));
		rqueryApplyEntity = rqueryApplyService.getEntity(params);
		if( null != rqueryApplyEntity){
			id = rqueryApplyEntity.getId();
			qbank = rqueryApplyEntity.getQbank();
			aorg = rqueryApplyEntity.getAorg();
			account = rqueryApplyEntity.getAccount();
			rnum = rqueryApplyEntity.getRnum();
			amount = rqueryApplyEntity.getAmount();
			payMan = rqueryApplyEntity.getPayMan();
			rtacname = rqueryApplyEntity.getRtacname();
			pbank =rqueryApplyEntity.getPbank();
			bankNum = rqueryApplyEntity.getBankNum();
			signOrg = rqueryApplyEntity.getSignOrg();
			if( null != rqueryApplyEntity.getAppDate()){
				String time = rqueryApplyEntity.getAppDate().toString();
				appDate = time.split("-")[0];
				amonth = time.split("-")[1];
				aday = time.split("-")[2];
			}
			receiptId = rqueryApplyEntity.getReceiptId();
			ReceiptService receiptService = (ReceiptService)wc.getBean("receiptService");
			SHashMap param = new SHashMap();
			param.put("id", Long.parseLong(receiptIds));
			receiptEntity = receiptService.getEntity(param);
			count = receiptEntity.getRcount();
			//只读
			orgtype = "3";
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/extlibs/ext-3.3.0/adapter/ext/ext-base.js" ></script>
<script type="text/javascript" src="<%=basePath%>/extlibs/ext-3.3.0/ext-all.js" ></script>
<script type="text/javascript" src="<%=basePath%>/js/cmw_core.js"></script>
<script type="text/javascript" src="<%=basePath%>/controls/My97DatePicker/WdatePicker.js" ></script><!-- 日期my97date日期控件author:李听 -->
<script type="text/javascript" src="<%=basePath%>/js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.validate.messages_cn.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.validate.expand.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/card.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/jquery.validate.css">


</head>
<body>
<style type="text/css">
body{background-color:white;width : 750px;margin : 0 auto}
table{margin:0 auto}
.time{width : 30px}/*最后的年月日的宽度*/
input{
	width : 250px;
	color:black; border:0px; /* #005aa7:浅蓝色*/
	border-bottom:1px solid black; /* 下划线效果 */ 
	background-color:transparent; /* 背景色透明 */
	}
.txt-right{text-align : left}/*让每行的第一列靠右显示*/
</style>

<!-- 进行表单数据验证 -->
<script type="text/javascript">
//表单验证
	$(function(){
			$("#rqueryApply").validate();
	});
</script>
<script type="text/javascript">
	$(document).ready(function(){
		<%
	String parentContainerId = request.getParameter("parentContainerId");
		%>
	parent.Cmw.unmask("<%=parentContainerId%>");
	});
</script>

	<form action="" method ="post" id = "rqueryApply">
		<h1 style="text-align:center">申请</h1>
			<input id="id" name="id" style="visibility : hidden" value="<%=id %>" />
		<table><!--  style="border-collapse:collapse;" ： 使table的边框细线化 -->
			<tr>
				<td colspan="3"><input type="text" id="qbank" name="qbank" class="{required:true,maxlength:80}"  value=<%=qbank %>>:</td>
			</tr>
			<tr>
				<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;兹有我单位：<input type="text" id="aorg" name="aorg"  class="{required:true,maxlength:80}" value=<%=aorg %>></td>
			</tr>
			<tr>
				<td class="txt-right">账号：</td>
				<td><input type="text" id="account" name="account" style="width: 200px"  class="{required:true,maxlength:30}" value=<%=account %>></td>
				<td>现收到银行承兑汇票<input type="text" id="count" name="count" style="width:30px;" readonly="readonly" value=<%=count %> >张</td>
			</tr>
			<tr>
				<td class="txt-right">票号:</td>
				<td colspan="2"><input type="text" id="rnum" name="rnum"  class="{required:true,maxlength:30}" value=<%=rnum %>></td>
			</tr>
			<tr>
				<td class="txt-right">金额:</td>
				<td colspan="2"><input type="text" id="amount" name="amount"  class="{required:true}" value=<%=amount %>></td>
			
			</tr>
			<tr>
				<td class="txt-right">承兑付款人:</td>
				<td colspan="2"><input type="text" id="payMan" name="payMan"  class="{required:true,maxlength:80}" value=<%=payMan %>></td>
			
			</tr>
			<tr>
				<td class="txt-right">收款人:</td>
				<td colspan="2"><input type="text" id="rtacname" name="rtacname"  class="{required:true,maxlength:80}" value=<%=rtacname %>></td>
			</tr>
			<tr>
				<td class="txt-right">付款人行名:</td>
				<td colspan="2"><input type="text" id="pbank" name="pbank" class="{required:true,maxlength:80}" value=<%=pbank %> ></td>
			</tr>
			<tr>
				<td class="txt-right">付款行行号：</td>
				<td colspan="2"><input type="text" id="bankNum" name="bankNum" class="{maxlength:30}" value=<%=bankNum %>></td>
			</tr>
		</table>
			<p style="margin-left : 350px;">望贵行予以查询，（票据是否真实，有无止冻他查）</p>
			<p style="margin-left : 600px;">特此申请！</p>
			<p style="margin-left : 450px;"><input type="text" id="signOrg" name="signOrg" value=<%=signOrg %> ></p><!-- onclick = "WdatePicker()" -->
			<p style="margin-left : 550px;"><input type="text" class="time" onclick = "WdatePicker()" id="myYear" value=<%=appDate%>>年<input type="text" class="time"  id="myMonth" readonly="readonly" value = <%=amonth %>>月<input type="text" class="time" id="myDay" readonly="readonly" value=<%=aday%>>日</p>		
	   		<input type="text" id = "time" style="visibility:hidden" />	</form>
	</form>
</body>
</html>

<script type="text/javascript">
	$(document).ready(function(){
		//对input进行设置值和修改值
		var orgtype = '<%=orgtype%>';
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
</script>
