<%@page import="com.cmw.entity.funds.RbrelationEntity"%>
<%@page import="com.cmw.service.inter.funds.RbrelationService"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.cmw.entity.funds.ReceiptEntity"%>
<%@page import="com.cmw.service.inter.funds.ReceiptService"%>
<%@page import="com.cmw.service.inter.funds.ReceiptBookAttachmentService"%>
<%@page import="com.cmw.entity.funds.ReceiptBookAttachmentEntity"%>
<%@page import="java.util.List"%>
<%@page import="com.cmw.entity.funds.SettlementEntity"%>
<%@page import="com.cmw.entity.funds.ReceiptBookEntity"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Date"%>
<%@page import="com.cmw.core.util.SHashMap"%>
<%@page import="com.cmw.service.inter.funds.ReceiptBookService"%>
<%@page import="com.cmw.service.inter.funds.SettlementService"%>
<%@page import="com.cmw.core.util.StringHandler"%>
<jsp:directive.page import="org.springframework.web.context.WebApplicationContext"/>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 	String basePath = request.getContextPath();
 	request.setCharacterEncoding("UTF-8");
 	response.setContentType("text/html;charset=UTF-8");
 	String idVal  = request.getParameter("receiptId");
	String rnumVal  ="";
	if(null != request.getParameter("rnum"))rnumVal =new String(request.getParameter("rnum").getBytes("iso8859-1"),"utf-8");
	String amountVal  ="";// = ;//
	if(null != request.getParameter("amount"))amountVal = request.getParameter("amount");
	String pbankVal =""; // = request.getParameter("pbank");
	if(null != request.getParameter("pbank"))pbankVal = new String(request.getParameter("pbank").getBytes("iso8859-1"),"utf-8");
//	pbankVal = StringHandler.getStr(pbankVal);//或者是使用卫哥的StringHandler工具类中的方法进行转换
	String outDateVal= "";
	if(null != request.getParameter("outDate"))outDateVal = request.getParameter("outDate");
	String endDateVal = "";
	if(null != request.getParameter("endDate"))endDateVal= request.getParameter("endDate");;
	String rcountVal ="";
	if(null != request.getParameter("rcount"))rcountVal = request.getParameter("rcount");;
	//票数转换为大写  第48行代码
	//汇票承诺书
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//转换格式 不加"yyyy-MM-dd" ，效果： 14-1-29 上午12:00
	Long id = null;
	Long receiptBookEditId = null;
	String code = "";
	String tcount = "";
	BigDecimal tamount = new BigDecimal(0.00);
	String name = "";
	String cardNum= "";
	String tel = "";
	String rtacname = "";
	String rtaccount = "";
	String rtbank = "";
	String tman = "";
	String mman = "";
	String tdate = "";
	String rmonth = "";
	String rday = "";
	//汇票结算单
	String sdate = "";
	String sname ="";
	BigDecimal amount = new BigDecimal(0.00);
	BigDecimal qamonut = new BigDecimal(0.00);
	Double rate = new Double(0.00);
	BigDecimal tiamount = new BigDecimal(0.00);
	BigDecimal ramount = new BigDecimal(0.00);
	String auditMan ="";
	String leaderMan ="";
	String finMan ="";
		//汇票附件表
	List<ReceiptBookAttachmentEntity> receiptBookAttachments = null;
	ReceiptEntity receiptEntity  = null;
	
	//根据receiptId判断是否有数据；
	//获取详情的两种方法 
	//1：从主页弹出窗口
	String ids = request.getParameter("id");
	String orgtype = request.getParameter("orgtype");
	ReceiptBookEntity receiptBookEntity = null;
	WebApplicationContext wc = (WebApplicationContext)this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	if( null != orgtype && "3".equals(orgtype)){
	
	if(null != ids && !"".equals(ids)){
		id = Long.parseLong(ids);
		//汇票承诺书
		ReceiptBookService receiptBookService = (ReceiptBookService)wc.getBean("receiptBookService");		
		SHashMap params=new SHashMap();
		params.put("id", id);
		receiptBookEntity = receiptBookService.getEntity(params);
		receiptBookEditId = receiptBookEntity.getId();
		code = receiptBookEntity.getCode();
		tcount = receiptBookEntity.getTcount();
		tamount = receiptBookEntity.getTamount();
		name  = receiptBookEntity.getName();
		cardNum  = receiptBookEntity.getCardNum();
		tel  = receiptBookEntity.getTel();
		rtacname = receiptBookEntity.getRtacname();
		rtaccount  = receiptBookEntity.getRtaccount();
		rtbank = receiptBookEntity.getRtbank();
		tman = receiptBookEntity.getTman();
		mman = receiptBookEntity.getTman();
		if(null != receiptBookEntity.getTdate()){
			String time = receiptBookEntity.getTdate().toString();
			tdate = time.split("-")[0];
			rmonth = time.split("-")[1];
			rday = time.split("-")[2];
		}
		//汇票收条的信息
		ReceiptService receiptService = (ReceiptService)wc.getBean("receiptService");		
		SHashMap receiptparams =new SHashMap();
		receiptparams.put("id", idVal);
		receiptEntity = receiptService.getEntity(receiptparams);
		pbankVal = receiptEntity.getPbank();
		rnumVal = receiptEntity.getRnum();
		amountVal = String.valueOf(receiptEntity.getAmount());//BigDecimal转String  String转BigDecimal：BigDecimal bd=new BigDecimal(字符串);
		rcountVal = receiptEntity.getRcount();
		outDateVal = sdf.format(receiptEntity.getOutDate());
		endDateVal = sdf.format(receiptEntity.getEndDate());
		//汇票结算单
		SHashMap saram=new SHashMap();
		saram.put("receiptBookId", receiptBookEntity.getId());
		SettlementService settlementService = (SettlementService)wc.getBean("settlementService");	
		SettlementEntity settlementEntity = settlementService.getEntity(saram);
		sdate = settlementEntity.getSdate().toString();
		sname =settlementEntity.getName();
		amount = settlementEntity.getAmount();
		qamonut = settlementEntity.getQamonut();
		rate = settlementEntity.getRate();
		tiamount = settlementEntity.getTiamount();
		ramount = settlementEntity.getRamount();
		auditMan =settlementEntity.getAuditMan();
		leaderMan =settlementEntity.getLeaderMan();
		finMan =settlementEntity.getFinMan();

		SHashMap receipHashMap = new SHashMap();
		receiptBookEditId = receiptBookEntity.getId();
		receipHashMap.put("receiptBookId", receiptBookEditId);
		ReceiptBookAttachmentService receiptBookAttachmentService = (ReceiptBookAttachmentService)wc.getBean("receiptBookAttachmentService");	
		receiptBookAttachments = receiptBookAttachmentService.getEntityList(receipHashMap);
	}
	
	}
	//2 ： 在点击汇票收条时，访问数据库，当receiptBook中有这收条的数据时显示详情
		if( null != idVal&&!"".equals(idVal) ){
			Long receiptId = Long.parseLong(idVal);
			RbrelationService rbelationService = (RbrelationService)wc.getBean("rbrelationService");		
			SHashMap rbrelationParams =new SHashMap();
			rbrelationParams.put("receiptId", receiptId);
			RbrelationEntity rbrelationEntity = rbelationService.getEntity(rbrelationParams);
			if( null != rbrelationEntity){
				Long receiptBookId = rbrelationEntity.getReceiptBookId();
				
				ReceiptBookService receiptBookService = (ReceiptBookService)wc.getBean("receiptBookService");		
				SHashMap receiptBookParams =new SHashMap();
				receiptBookParams.put("id", receiptBookId);
				receiptBookEntity = receiptBookService.getEntity(receiptBookParams);
				if( null != receiptBookEntity){
					receiptBookEditId = receiptBookEntity.getId();
					code = receiptBookEntity.getCode();
					tcount = receiptBookEntity.getTcount();
					tamount = receiptBookEntity.getTamount();
					name  = receiptBookEntity.getName();
					cardNum  = receiptBookEntity.getCardNum();
					tel  = receiptBookEntity.getTel();
					rtacname = receiptBookEntity.getRtacname();
					rtaccount  = receiptBookEntity.getRtaccount();
					rtbank = receiptBookEntity.getRtbank();
					tman = receiptBookEntity.getTman();
					mman = receiptBookEntity.getTman();
					if(null != receiptBookEntity.getTdate()){
						String time = receiptBookEntity.getTdate().toString();
						tdate = time.split("-")[0];
						rmonth = time.split("-")[1];
						rday = time.split("-")[2];
					}
					//汇票收条的信息
					ReceiptService receiptService = (ReceiptService)wc.getBean("receiptService");		
					SHashMap receiptparams =new SHashMap();
					receiptparams.put("id", idVal);
					receiptEntity = receiptService.getEntity(receiptparams);
					pbankVal = receiptEntity.getPbank();
					rnumVal = receiptEntity.getRnum();
					amountVal = String.valueOf(receiptEntity.getAmount());//BigDecimal转String  String转BigDecimal：BigDecimal bd=new BigDecimal(字符串);
					rcountVal = receiptEntity.getRcount();
					outDateVal = sdf.format(receiptEntity.getOutDate());
					endDateVal = sdf.format(receiptEntity.getEndDate());
					
					//汇票结算单
					SHashMap saram=new SHashMap();
					saram.put("receiptBookId", receiptBookEntity.getId());
					SettlementService settlementService = (SettlementService)wc.getBean("settlementService");	
					SettlementEntity settlementEntity = settlementService.getEntity(saram);
					sdate = settlementEntity.getSdate().toString();
					sname =settlementEntity.getName();
					amount = settlementEntity.getAmount();
					qamonut = settlementEntity.getQamonut();
					rate = settlementEntity.getRate();
					tiamount = settlementEntity.getTiamount();
					ramount = settlementEntity.getRamount();
					auditMan =settlementEntity.getAuditMan();
					leaderMan =settlementEntity.getLeaderMan();
					finMan =settlementEntity.getFinMan();
	
					SHashMap receipHashMap = new SHashMap();
					receipHashMap.put("receiptBookId", receiptBookEntity.getId());
					ReceiptBookAttachmentService receiptBookAttachmentService = (ReceiptBookAttachmentService)wc.getBean("receiptBookAttachmentService");	
					receiptBookAttachments = receiptBookAttachmentService.getEntityList(receipHashMap);
				//设置成只读
					orgtype = "3";
				}
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

<script type="text/javascript">
	$(document).ready(function(){
		//对input进行设置值和修改值
		var orgtype = '<%=orgtype%>';
		if(orgtype && 3 != orgtype) return;
		$("input").attr("readonly","readonly"); //查看详情时让数据只读
		$("select").attr("disabled","disabled");
		
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
	<%
	String parentContainerId = request.getParameter("parentContainerId");
		%>
	parent.Cmw.unmask("<%=parentContainerId%>");
</script>
<script type="text/javascript">
	$(document).ready(function(){
		
	
	var amountVal = <%=amountVal%>;
	amountVal = idCardNoUtil.cmycurd(amountVal);
	$("#tamount").val(amountVal);
	var rcountVal = <%=rcountVal%>
	rcountVal = idCardNoUtil.cmycurd(rcountVal);
	$("#tcount").val(rcountVal.split("元整")[0]); //转换之后变成 "壹元整" 需要截取  效果等同于下面的语句
	//$("#tcount").html(rcountVal.substring(0,rcountVal.lastIndexOf("元整")));
	});
	function date(){
		var $time = $("#tdate").val();
		if( null != $time){
			var $month = $time.split("-")[1];
			var $day = $time.split("-")[2];
			$("#month").val($month);
			$("#day").val($day);
		}
	}
</script>
<script type="text/javascript">
//表单验证
	$(function checkForm(){
		//通过重写jquery.validat.js中的addMethod来实现
		//身份证认证
		jQuery.validator.addMethod("isIdCardNo", function(value, element) { 
		  return this.optional(element) || idCardNoUtil.checkIdCardNo(value);     
		}, "请正确输入您的身份证号码"); 
		//联系号码认证
		jQuery.validator.addMethod("checkPhone", function(value, element) { 
		  return this.optional(element) || idCardNoUtil.checkPhoneTel(value);     
		}, "请正确输入您的联系电话"); 
		
			$("#myform").validate({
				submitHandler:function(form){ 
				alert("submitted"); 
				form.submit(); 
			}} );
			
		});
	
</script>
<script type="text/javascript">
</script>
<script type="text/javascript">

</script>

</head>
<body>
<style type="text/css">
body{width : 700px;margin:0 auto;background-color:white;text-align : center;}
.receiptbookId input{width:135px;text-align:center;} 
.receiptbookId td{font-weight:bold;text-decoration : underline}
.data{/* text-decoration : underline; */color:red;width :60px;font-size:16px}
th{width : 150px;}
td{text-align:center}
h2{text-align:center}
.txt_right{text-align:left}
input{
	color:black; border:0px; /* #005aa7:浅蓝色*/
	border-bottom:1px solid black; /* 下划线效果 */ 
	background-color:transparent; /* 背景色透明 */ 
	}
/*套在Select外层，用于隐藏Select框*/
.divSelect
{
	text-align : center;
    position: relative;
    background-color: transparent;
    overflow: hidden; /*隐藏了小三角，因为宽度为110px,而select宽度为130px*/
    width :80px;
    border-width:0px;
    border-top-style: none; 
    border-right-style: none; 
    border-left-style: none; 
    border-bottom:1px solid black; /* 下划线效果 */
    /* border-bottom-style: none; */
}
.selectList{
	position: relative;
    background-color: transparent;
    TOP:   -2px;
    left:-2px;
    text-align : center;
    width : 100px;
    border-width: 0px;
    border-top-style: none; 
    border-right-style: none; 
    border-left-style: none; 
    border-bottom-style: none;
    display:block;
    padding-left: 34px;
    outline: none;
    color: #333;
/*  height: 20px;*/  
	overflow:hidden;
}
.rem{width : 350px;}
.time{width : 30px}/*最后的年月日的宽度*/
#settlementId td{width : 80px;}
</style>
	<form action="" method="post" id="myform">
	<input id="id" name="id" style="visibility : hidden" value="<%=receiptBookEditId %>" />
	<input id="receiptId" name="id" style="visibility : hidden" value="<%=idVal %>" />
	<h2>银行承兑汇票转让承诺书</h2>
		<p style="text-align : left">由于本公司急需资金，先将我公司合法拥有的银行承兑汇票共计<input type="text" class="data" id="tcount" name="tcount" value="<%=rcountVal %>">张，共计金额:<input type="text" id="tamount" class="data" style="width:150px;" value=<%=tamount %>>有偿转让给被转让方。（汇票信息详见下表 ）</p>
		<table class="receiptbookId" border="1" style="border-collapse:collapse;">
			<tr>
				<th>票号</th>
				<th style = "width : 100px;">金额</th>
				<th>付款行全称</th>
				<th>出票日期</th>
				<th>汇票到期日</th>
			</tr>
			<tr>
			<!-- 	
				<td><input type="text" id="rnum" name="rnum" disabled="disabled" value=<%=rnumVal %>></td>
				<td><input type="text" id="amount" name="amount" disabled="disabled" value=<%=amountVal %>></td>
				<td><input type="text" id="pbank" name="pbank" disabled="disabled" value=<%=pbankVal %>></td>
				<td><input type="text" id="outDate" name="outDate" disabled="disabled" value=<%=outDateVal %>></td>
				<td><input type="text" id="endDate" name="endDate" disabled="disabled" value=<%=endDateVal %>></td>
			 -->
				<td><%=rnumVal %></td>
				<td id="amountVal"><%=amountVal %></td>
				<td><%=pbankVal %></td>
				<td><%=outDateVal %></td>
				<td><%=endDateVal %></td>
	
			</tr>
		</table>
		<br/>
		<table>
			<tr>
				<td class="txt_right">收款人姓名：</td>
				<td><input type="text" id="name" name="name" class="{required:true,maxlength:80}" value=<%=name %> ></td>
				<td class="txt_right">身份证号:</td>
				<td><input type="text" id="cardNum" name="cardNum" class="{required:true,isIdCardNo:true,maxlength:50}" value=<%=cardNum %> ></td>
			</tr>
			<tr>
				<td class="txt_right">收款人联系电话：</td>
				<td><input type="text" id="tel" name="tel" class="{required:true,checkPhone:true,maxlength:20}" value=<%=tel %> ></td>
			</tr>
			<tr>
				<td class="txt_right">收款人银行账号名称：</td>
				<td><input type="text" id="rtacname" name="rtacname" class="{required:true,maxlength:80}" value=<%=rtacname %> ></td>
				<td class="txt_right">账号：</td>
				<td><input type="text" id="rtaccount" name="rtaccount" class="{required:true,maxlength:30}" value=<%=rtaccount %> ></td>
			</tr>
			<tr>
				<td class="txt_right">开户银行：</td>
				<td><input type="text" id="rtbank" name="rtbank" class="{required:true,maxlength:80}" value=<%=rtbank %> ></td>
			</tr>
		</table>
		<br />
		<table border="1" style="border-collapse:collapse;" id="attachment">
			<tr>
				<th style="width:240px;">材料名称</th>
				<th style="width:90px;">是否提供</th>
				<th style="width:360px;">备注</th>
			</tr>
		<% if(null != receiptBookAttachments) { 
			for(ReceiptBookAttachmentEntity receiptBookAttachmentEntity : receiptBookAttachments){
		%>
			<tr>
				<td><input type="text" name="name" value="<%=receiptBookAttachmentEntity.getName() %>" ></td>
				<td>
					<div class="divSelect" >
						<select name ="status" class="selectList" >
							<%if(receiptBookAttachmentEntity.getStatus()==1){
								
								
								out.print(receiptBookAttachmentEntity.getStatus()+"...");
								%>
								<option value="1">是</option>
							<%}else{ 
								out.print(receiptBookAttachmentEntity.getStatus()+"...");
							%>
								<option value="0">否</option>
							<%} %>
						</select>
					</div>
				</td>
				<td><input type="text" name="remark" class="rem" value=<%=receiptBookAttachmentEntity.getRemark() %>></td>
			</tr>
		<%} }else{%>
			<tr>
				<td><input type="text" name="name" value="身份证复印件" ></td>
				<td>
					<div class="divSelect" >
						<select name ="status" class="selectList" >
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</div>
				</td>
				<td><input type="text" name="remark" class="rem"></td>
			</tr>
			<tr>
				<td><input type="text" name="name" value="银行卡复印件" ></td>
				<td>	
					<div class="divSelect" >
						<select name ="status" class="selectList" >
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</div>
				</td>
				<td><input type="text" name="remark" class="rem"></td>
			</tr>
			<tr>
				<td><input type="text" name="name" value="承兑汇票复印件" ></td>
				<td>
					<div class="divSelect" >
						<select name ="status" class="selectList" >
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</div>
				</td>
				<td><input type="text" name="remark" class = "rem"></td>
			</tr>
			<%} %>
			<!--<tr>
				<td colspan="3"></td>
			</tr>-->
		</table>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;若此次转让的银行承兑汇票出现假票、挂失、克隆、止付、冻结等情况，造成无法背书转让、无法贴现，无法到期承兑，转让方承诺无条件按票面金额全额退款给被转让方，并愿意承担由此引起的一切经济损失和法律责任。</p>
		<p>&nbsp;&nbsp;转让方签字（盖章）：</p>
　　		<p>&nbsp;&nbsp;经办人签字：</p>
		<p style="margin-left : 550px;"><input type="text" class="time" onclick = "WdatePicker()" id="myYear" value=<%=tdate%>>年<input type="text" class="time"  id="myMonth" readonly="readonly" value = <%=rmonth %>>月<input type="text" class="time" id="myDay" readonly="readonly" value=<%=rday%>>日</p>		
		<input type="text" id = "time" style="visibility:hidden" />
		<table border="1px" style="border-collapse:collapse;" id="settlementId" >
			<tr>
				<th colspan="7">汇票结算单</th>
			</tr>
			<tr>
				<td><strong>日期</strong></td>
				<td><input type="text" id="sdate" name="sdate" onclick = "WdatePicker()" class="{required:true}" value=<%=sdate %> ></td>
				<td><strong>查询费</strong></td>
				<td colspan="2"><input type="text" id="qamonut" name="qamonut" class="{required:true,number:true  }" value=<%=qamonut %> ></td>
				<td><strong>审核签字</strong></td>
				<td><input type="text" id="auditMan" name="auditMan" class="{maxlength : 30}" value=<%=auditMan %> ></td>
			</tr>
			<tr>
				<td rowspan="2"><strong>姓名</strong></td>
				<td rowspan="2"><input type="text" id="name" name="name" class="{required:true,maxlength:60}" value=<%=sname %> ></td>
				<td rowspan="2"><strong>贴息</strong></td>
				<td style="font-size:10px;width:65px;"><strong>贴息利率(%)</strong></td>
				<td><input type="text" id="rate" name="rate" style="width:60px;" value=<%=rate %>></td>
				<td rowspan="2"><strong>领导签字</strong></td>
				<td rowspan="2"><input type="text" id="leaderMan" name="leaderMan" class="{maxlength : 30}" value=<%=leaderMan %> ></td>
			</tr>
			<tr>
				<td colspan="2"><input type="text" id="tiamount"  name="tiamount" readonly="readonly" value=<%=tiamount %>></td>
			</tr>
			<tr>
				<td><strong>金额</strong></td>
				<td><input type="text" id="amount" name="amount" class="{required:true,number:true,maxlength:80}" value=<%=amount%>></td>
				<td><strong>实际付款</strong></td>
				<td colspan="2"><input type="text" id="ramount" name="ramount" class="{required:true,number:true  }" readonly="readonly" value=<%=ramount%>></td>
				<td><strong>财务签字</strong></td>
				<td colspan="2"><input type="text" id="finMan" name="finMan" class="{maxlength : 30}" value=<%=finMan%>></td>
			</tr>
		</table>
	</form>
</body>
</html>
<script type="text/javascript">
	$(function(){
		$("#amount").change(calculate);
		$("#rate").change(calculate);
		$("#qamonut").change(calculate);
	});
	function calculate(){
		var amount = $("#amount").val();
		var rate = $("#rate").val()/100;
		var product = (amount * rate).toFixed(2);
		var tiamount = $("#tiamount").val(product);
		var qamonut = $("#qamonut").val();
		var num = (amount-product-qamonut).toFixed(2);
		if(num >= 0){
			$("#ramount").val(num);
			return true;
		}else{
			alert("实际金额不能小于0");
			return false;
		}
	}
</script>

