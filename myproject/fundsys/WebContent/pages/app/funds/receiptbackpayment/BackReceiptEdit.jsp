<%@page import="com.cmw.service.inter.funds.SettlementService"%>
<%@page import="com.cmw.entity.funds.SettlementEntity"%>
<%@page import="com.cmw.entity.funds.ReceiptBookEntity"%>
<%@page import="com.cmw.service.inter.funds.ReceiptBookService"%>
<%@page import="com.cmw.entity.funds.RbrelationEntity"%>
<%@page import="com.cmw.service.inter.funds.RbrelationService"%>
<%@page import="com.cmw.service.inter.funds.BackInvoceService"%>
<%@page import="com.cmw.entity.funds.BackInvoceEntity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.cmw.entity.funds.BackReceiptEntity"%>
<%@page import="com.cmw.service.inter.funds.BackReceiptService"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.*"%>
<%@page import="com.cmw.core.util.SHashMap"%>
<jsp:directive.page import="org.springframework.web.context.WebApplicationContext"/>
<%
	String basePath = request.getContextPath();
	String receiptId = request.getParameter("receiptId");
	Long id = null;
    String name = "";
    String reman = "";
    String rcount = "";
    String rnum = "";
    String outMan = "";
    String omaccount = "";
    String pbank = "";
    String outDate = "";
    String endDate = "";
    BigDecimal amount = new BigDecimal(0.00);
    String rtacname = "";
    String rtaccount = "";
    String rtbank = "";
    String recetDate = "";
    String bmonth = "";
    String bday = "";
    
    Long backReceiptId = null;
	String sdate = "";
    String sname = "";
    BigDecimal samount = new BigDecimal(0.00);
    Double rate = new Double(0.00);
    BigDecimal tiamount = new BigDecimal(0.00);
    BigDecimal bamount = new BigDecimal(0.00);
    BigDecimal pamount = new BigDecimal(0.00);
	BigDecimal receiptAmount = new BigDecimal(0);
    
    RbrelationEntity rbrelationEntity = null;//为了查找 实际付款金
    SettlementEntity settlementEntity =null;
    String idVal = request.getParameter("id");
    String orgtype =request.getParameter("orgtype");
	WebApplicationContext wc = (WebApplicationContext)this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	
	BackReceiptEntity backReceiptEntity = null;
	BackInvoceEntity backInvoceEntity = null;
	if( null != orgtype && "3".equals(orgtype)){
	if(null != idVal && !"".equals(idVal)){
		id = Long.parseLong(idVal);
		BackReceiptService backReceiptService = (BackReceiptService)wc.getBean("backReceiptService");		
		SHashMap params=new SHashMap();
		params.put("id", id);
		backReceiptEntity = backReceiptService.getEntity(params);
		name = backReceiptEntity.getName();
		reman = backReceiptEntity.getReman();
		rcount = backReceiptEntity.getRcount();
		rnum = backReceiptEntity.getRnum();
		outMan = backReceiptEntity.getOutMan();
		omaccount = backReceiptEntity.getOmaccount();
		pbank = backReceiptEntity.getPbank();
		if( null != backReceiptEntity.getOutDate()){
			outDate = backReceiptEntity.getOutDate().toString();
		}
		if( null != backReceiptEntity.getEndDate() ){
			endDate = backReceiptEntity.getEndDate().toString();
		}
		amount = backReceiptEntity.getAmount();
		rtacname = backReceiptEntity.getRtacname();
		rtaccount = backReceiptEntity.getRtaccount();
		rtbank = backReceiptEntity.getRtbank();
		if( null != backReceiptEntity.getRecetDate()){
			String time = backReceiptEntity.getRecetDate().toString();
			recetDate = time.split("-")[0];
			bmonth = time.split("-")[1];
			bday = time.split("-")[2];
		}
		
		//汇票结算单
		BackInvoceService backInvoceService = (BackInvoceService)wc.getBean("backInvoceService");
		SHashMap backparam = new SHashMap();
		backparam.put("backReceiptId", backReceiptEntity.getId());
		backInvoceEntity = backInvoceService.getEntity(backparam);
		if(null != backInvoceEntity.getSdate()){
			sdate = backInvoceEntity.getSdate().toString();
		}
		sname = backInvoceEntity.getName();
		samount = backInvoceEntity.getAmount();
		tiamount = backInvoceEntity.getTiamount();
		bamount = backInvoceEntity.getBamount();
		pamount = backInvoceEntity.getPamount();
		
		//根据backReceiptEntity.getReceiptId() 在receiptBook
		
		RbrelationService rbrelationService = (RbrelationService)wc.getBean("rbrelationService");
		SHashMap reparam = new SHashMap();
		reparam.put("receiptId", backReceiptEntity.getReceiptId());
		rbrelationEntity = rbrelationService.getEntity(reparam);
		//获取汇票承诺书的id
		Long receiptBookId = rbrelationEntity.getReceiptBookId();
		SettlementService settlementService = (SettlementService)wc.getBean("settlementService");
		SHashMap settlementparam = new SHashMap();
		settlementparam.put("receiptBookId", receiptBookId);
		settlementEntity = settlementService.getEntity(settlementparam);
		receiptAmount = settlementEntity.getRamount();
		
		}
	}
	
	if( null != receiptId && !"".equals(receiptId)){
		BackReceiptService backReceiptService = (BackReceiptService)wc.getBean("backReceiptService");		
		SHashMap params=new SHashMap();
		params.put("receiptId", Long.parseLong(receiptId));
		backReceiptEntity = backReceiptService.getEntity(params);
		if( null != backReceiptEntity){
			id = backReceiptEntity.getId();
			name = backReceiptEntity.getName();
			reman = backReceiptEntity.getReman();
			rcount = backReceiptEntity.getRcount();
			rnum = backReceiptEntity.getRnum();
			outMan = backReceiptEntity.getOutMan();
			omaccount = backReceiptEntity.getOmaccount();
			pbank = backReceiptEntity.getPbank();
			if( null != backReceiptEntity.getOutDate()){
				outDate = backReceiptEntity.getOutDate().toString();
			}
			if( null != backReceiptEntity.getEndDate() ){
				endDate = backReceiptEntity.getEndDate().toString();
			}
			amount = backReceiptEntity.getAmount();
			rtacname = backReceiptEntity.getRtacname();
			rtaccount = backReceiptEntity.getRtaccount();
			rtbank = backReceiptEntity.getRtbank();
			if( null != backReceiptEntity.getRecetDate()){
				String time = backReceiptEntity.getRecetDate().toString();
				recetDate = time.split("-")[0];
				bmonth = time.split("-")[1];
				bday = time.split("-")[2];
			}
			
			//汇票结算单
			BackInvoceService backInvoceService = (BackInvoceService)wc.getBean("backInvoceService");
			SHashMap backparam = new SHashMap();
			backparam.put("backReceiptId", id);
			backInvoceEntity = backInvoceService.getEntity(backparam);
			if(null != backInvoceEntity.getSdate()){
				sdate = backInvoceEntity.getSdate().toString();
			}
			sname = backInvoceEntity.getName();
			samount = backInvoceEntity.getAmount();
			tiamount = backInvoceEntity.getTiamount();
			bamount = backInvoceEntity.getBamount();
			pamount = backInvoceEntity.getPamount();
			
			//根据backReceiptEntity.getReceiptId() 在receiptBook
			
			RbrelationService rbrelationService = (RbrelationService)wc.getBean("rbrelationService");
			SHashMap reparam = new SHashMap();
			reparam.put("receiptId", backReceiptEntity.getReceiptId());
			rbrelationEntity = rbrelationService.getEntity(reparam);
			//获取汇票承诺书的id
			Long receiptBookId = rbrelationEntity.getReceiptBookId();
			SettlementService settlementService = (SettlementService)wc.getBean("settlementService");
			SHashMap settlementparam = new SHashMap();
			settlementparam.put("receiptBookId", receiptBookId);
			settlementEntity = settlementService.getEntity(settlementparam);
			receiptAmount = settlementEntity.getRamount();
			//只读
		}
	}
		//orgtype = "3";
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
<script type="text/javascript" src="jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	//对input进行设置值和修改值
	$(document).ready(function(){
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
				$(span).css("text-decoration","underline");	//添加下横线
				$(span).unbind("change");//移除事件
			}else{
				var input = $(this).children()[0];
				//1.取出当前td中的文本内容保存起来
				var text = input.value;
				//2.设置当前的input中li父节点里面的内容
				$(input).html(text);
				$(input).css("text-decoration","underline");	
				$(input).unbind("change");
			}	
		 }
	/* function getValueFromJsp(){
		var form = document.forms[0];
		var reman = form.getElementsByTagName("input");
		for(var i = 0;i<reman.length;i++){
			alert(reman[i].value);
			
		}
	} */
</script>
<body>
<style type="text/css">
body{background-color:white;}
form{text-align:center}
li{float : left; list-style-type:none;}
input{ 
	width : 300px;
	color:black; border:0px; /* #005aa7:浅蓝色*/
	border-bottom:1px solid black; /* 下划线效果 */ 
	background-color:transparent; /* 背景色透明 */ 
	text-align:left;
}
.name{ width : 50px;}
.txt-right{text-align : right}/*让每行的第一列靠右显示*/
table{margin:0 auto}/*让表单进行居中显示  */
.time{width : 30px;}
#backinvoce td{width : 120px;}
#backinvoce td input{width : 120px;}
</style>
	<form method = "post" action=""  id="backreceipt"> 
		<h1 style="text-align:center">　　回 款 收 条</h1>
			<input id="id" name="id" style="visibility : hidden" value="<%=id %>" />
		<table>
			<tr >
				<td colspan="10">
                	<span><input type = "text" id="reman" name="reman" class = "name" class="{required:true,maxlength:50}" value=<%=reman %>></span>收到
					<span><input type = "text" id="name" name="name" class = "name" class="{required:true,maxlength:50}" value=<%=name %>></span>银行承兑汇票
					<span><input type = "text" id="rcount" name="rcount" style="width:50px;" class="{required:true,maxlength:50}" value=<%=rcount %>></span>张，票面要素如下： 
				</td>
			</tr>
			<tr bordercolor="red">
				<td class="txt-right" >票号：</td>
				<td><input type="text" id = "rnum" name = "rnum" class="{required:true,maxlength:30}" value=<%=rnum %>></td>
			</tr>
			<tr>
				<td  class="txt-right">出票人：</td>
				<td><input type="text" id = "outMan" name = "outMan" class="{required:true,maxlength:80}" value=<%=outMan %>></td>
			</tr>
			<tr>
				<td  class="txt-right">出票人账号：</td>
				<td><input type="text" id = "omaccount" name = "omaccount" class="{required:true,maxlength:30}" value=<%=omaccount %>></td>
			</tr>
			<tr>
				<td  class="txt-right">付款行：</td>
				<td><input type="text" id = "pbank" name = "pbank" class="{required:true,maxlength:80}" value=<%=pbank %>></td>
			</tr>
			<tr>
				<td  class="txt-right">出票日期：</td>
				<td><input type="text" id = "outDate" name = "outDate" class="{required:true,dateISO : true}" onclick = "WdatePicker()" value=<%=outDate %>></td>
			</tr>
			<tr>
				<td class="txt-right" >到票期日：</td>
				<td><input type="text" id = "endDate" name = "endDate" class="{required:true,dateISO : true}" onclick = "WdatePicker()" value=<%=endDate %>></td>
			</tr>
			<tr>
				<td class="txt-right" >金额：</td>
				<td><input type="text" id = "amount" name = "amount" class="{required:true,number : true}" value=<%=amount %>></td>
			</tr>
			<tr>
				<td class="txt-right">收款人账户名：</td>
				<td><input type="text" id = "rtacname" name = "rtacname" class="{required:true,maxlength:80}" value=<%=rtacname %>></td>
			</tr>
			<tr>
				<td class="txt-right" >收款人账号：</td>
				<td><input type="text" id = "rtaccount" name = "rtaccount" class="{required:true,maxlength:30}" value=<%=rtaccount %>></td>
			</tr>
			<tr>
				<td class="txt-right">收款人开户银行全称：</td>
				<td><input type="text" id = "rtbank" name = "rtbank" class="{required:true,maxlength:80}" value=<%=rtbank %>></td>
			</tr>
		</table>
		<p style="margin-left : 100px;"><span style="color: red">注：</span>此收条款到后自动作废。</p>	
		
		<p style="margin-left : 400px;"><input type="text" class="time" onclick = "WdatePicker()" id="myYear" value=<%=recetDate%>>年<input type="text" class="time"  id="myMonth" readonly="readonly" value = <%=bmonth %>>月<input type="text" class="time" id="myDay" readonly="readonly" value=<%=bday%>>日</p>		
	   <input type="text" id = "time" style="visibility:hidden" />
		<table border="1px" style="border-collapse:collapse;" id="backinvoce" >
			<tr>
				<th colspan="6">汇票结算单</th>
			</tr>
			<tr>
				<td rowspan="2"><strong>日期</strong></td>
				<td rowspan="2"><input type="text" id="sdate" name="sdate" onclick = "WdatePicker()" value=<%=sdate %>></td>
				<td rowspan="2"><strong>贴息</strong></td>
				<td style="font-size:10px;width:75px;"><strong>贴息利率(%)</strong></td>
				<td><input type="text" id="rate" name="rate" style="width:60px;" value=<%=rate %>></td>
			</tr>
			<tr>
				<td colspan="2"><input type="text" id="tiamount"  name="tiamount" value=<%=tiamount %>></td>
			</tr>
			<tr>
				<td><strong>姓名</strong></td>
				<td><input type="text" id="sname" name="sname" value=<%=sname %>></td>
				<td><strong>实际回款</strong></td>
				<td colspan="2"><input type="text" id="bamount" name="bamount" value=<%=bamount %>></td>
			</tr>
			<tr>
				<td><strong>金额</strong></td>
				<td><input type="text" id="samount" name="samount" value=<%=samount %>></td>
				<td><strong>净利润</strong></td>
				<td colspan="2"><input type="text" id="pamount" name="pamount" value=<%=pamount %>></td>
			</tr>
		</table>	
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
<script type="text/javascript">
		<%
	String parentContainerId = request.getParameter("parentContainerId");
		%>
	parent.Cmw.unmask("<%=parentContainerId%>");
</script>
<!-- 表单验证 -->
<script type="text/javascript">
	$(function(){
			$('#backreceipt').validate();
		});
</script>
<script type="text/javascript">
	$(function(){
		$("#samount").change(calculate);
		$("#rate").change(calculate);
		$("#tiamount").change(calculate);
	});

	function calculate(){
		var samount = $("#samount").val();
		var rate = $("#rate").val()/100;
		var product = (samount * rate).toFixed(2);
		var tiamount = $("#tiamount").val(product);
		//实际付款金额 
		var ramount = <%=receiptAmount %>;
		//实际回收金额
		var bamount = $("#bamount").val(samount-tiamount.val());
		var num = (bamount.val()-ramount).toFixed(2);
		if(num > 0){
			$("#pamount").val(num);
			return true;
		}else{
			//alert("实际金额不能小于0");
			return false;
		}
	}
</script>
