<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.cmw.core.util.DataTable"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@page import="com.cmw.core.util.StringHandler"%>
<jsp:directive.page import="org.springframework.web.context.WebApplicationContext"/>
<%@page import="com.cmw.service.impl.finance.AuditProjectServiceImpl"%>
<%
	String basePath = request.getContextPath();
	int port = request.getServerPort();
	String sPort = port == 80 ? "" : (":" + port);
	WebApplicationContext wc = (WebApplicationContext)this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	AuditProjectServiceImpl apMgr =  (AuditProjectServiceImpl)wc.getBean("auditProjectService");
	String custType = request.getParameter("custType");
	String customerId = request.getParameter("customerId");
	String projectId = request.getParameter("projectId");
	DataTable dt = null;
	String queryStr = null;
	String cmns = null;
	HashMap<String,Object> params = new HashMap<String,Object>();
	String tabId = null;
	String printaction = request.getParameter("printaction");
	String style = (!StringHandler.isValidStr(printaction)) ? " style='display:\"\"'" : " style='display:none'";
	if(!StringHandler.isValidStr(printaction)) printaction = "";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>贷款调查报告</title>
	<base
		href="http://<%=request.getServerName()%><%=sPort%><%=basePath%>/">
		<script language="javascript" src="<%=basePath%>/js/moneydecimal.js"></script>
	<script language="javascript" src="<%=basePath%>/controls/lodop/LodopFuncs.js"></script>
	<object id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
		<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="<%=basePath%>/controls/lodop/install_lodop.exe"></embed>
	</object>
	
<link rel="stylesheet" type="text/css" href="<%=basePath%>/extlibs/ext-3.3.0/resources/css/ext-all.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/pages/app/workflow/variety/formUIs/survey/auditProject.css"></link>
<script type="text/javascript" src="<%=basePath%>/extlibs/ext-3.3.0/adapter/ext/ext-base.js" ></script>
<script type="text/javascript" src="<%=basePath%>/extlibs/ext-3.3.0/ext-all.js" ></script>
<style type="text/css">
</style>
</head>

<body>
<center>
<div id="auditproject_content">
<%
	//subject,queryStr,isHql,cmns
	params.put("subject","第一部分  基本情况");
	
	queryStr = "select id,name,regaddress,zipCode,contacttel,fax,"+
	"regcapital,incapital,loanNumber,tradNumber,taxNumber,"+
	"loanLog,legalMan,legalIdCard,legalTel,managerName,managerIdCard,"+
	"managerTel,finaManager,finaIdCard,finaTel,"+
	"(select name from ts_gvlist where id=currency) as currency,"+
	" loanBank,bankOrg,account,remark "+
	"  from crm_Ecustomer A left join "+
	" ( select top 1 bankOrg,account,ecustomerId from crm_Ebank where isenabled=1 order by id desc ) AS B on A.id=B.ecustomerId "+
	" where A.id='"+customerId+"'";
	params.put("queryStr",queryStr);
	//params.put("isHql",false);
	cmns = "id,name,regaddress,zipcode,contacttel,fax,"+
		"regcapital,incapital,loannumber,tradnumber,taxnumber,"+
		"loanLog,legalman,legalidcard,legaltel,managername,manageridcard,"+
		"managertel,finamanager,finaidcard,finatel,currency,"+
		"loanBank,bankOrg,account,remark";
	
	params.put("cmns",cmns);
	dt = apMgr.getDataTable(params);
	String currency = "人民币";
	String custName = "&nbsp";
	String regaddress  = "&nbsp";
	String zipcode = "&nbsp";
	String contacttel = "&nbsp";
	String fax = "&nbsp";
	String regcapital = "&nbsp";
	String incapital = "&nbsp";
	String loannumber = "&nbsp";
	String tradnumber = "&nbsp";
	String taxnumber = "&nbsp";
	String loanLog = "&nbsp";
	String legalman = "&nbsp";
	String legalidcard = "&nbsp";
	String legaltel = "&nbsp";
	String managername = "&nbsp";
	String manageridcard = "&nbsp";
	String managertel = "&nbsp";
	String finamanager = "&nbsp";
	String finaidcard = "&nbsp";
	String finatel = "&nbsp";
	String bankOrg = "&nbsp";
	String account = "&nbsp";
	String loanBank = "&nbsp";
	String remark = "&nbsp";
	if(null != dt && dt.getRowCount() > 0){
		currency = dt.getString("currency");
		custName = StringHandler.GetValue(dt.getString("name"),"&nbsp;");
		regaddress = StringHandler.GetValue(dt.getString("regaddress"),"&nbsp;");
		zipcode = StringHandler.GetValue(dt.getString("zipcode"),"&nbsp;");
		contacttel = StringHandler.GetValue(dt.getString("contacttel"),"&nbsp;");
		fax = StringHandler.GetValue(dt.getString("fax"),"&nbsp;");
		regcapital = StringHandler.getScienceVal(StringHandler.Round(dt.getDouble("regcapital"),4)/10000);
		incapital = StringHandler.getScienceVal(StringHandler.Round(dt.getDouble("incapital"),4)/10000);
		loannumber = StringHandler.GetValue(dt.getString("loannumber"),"&nbsp;");
		tradnumber = StringHandler.GetValue(dt.getString("tradnumber"),"&nbsp;") ;
		taxnumber = StringHandler.GetValue(dt.getString("taxnumber"),"&nbsp;");
		loanLog = StringHandler.GetValue(dt.getString("loanLog"),"&nbsp;");
		legalman  =StringHandler.GetValue(dt.getString("legalman"),"&nbsp;");
		legalidcard = StringHandler.GetValue(dt.getString("legalidcard"),"&nbsp;");
		legaltel = StringHandler.GetValue(dt.getString("legaltel"),"&nbsp;");
		managername = StringHandler.GetValue(dt.getString("managername"),"&nbsp;");
		manageridcard = StringHandler.GetValue(dt.getString("manageridcard"),"&nbsp;");
		managertel = StringHandler.GetValue(dt.getString("managertel"),"&nbsp;");
		finamanager = StringHandler.GetValue(dt.getString("finamanager"),"&nbsp;");
		finaidcard = StringHandler.GetValue(dt.getString("finaidcard"),"&nbsp;");
		finatel = StringHandler.GetValue(dt.getString("finatel"),"&nbsp;");
		bankOrg = StringHandler.GetValue(dt.getString("bankOrg"),"&nbsp;");
		account = StringHandler.GetValue(dt.getString("account"),"&nbsp;");
		loanBank = StringHandler.GetValue(dt.getString("loanBank"),"&nbsp;");
		loannumber = StringHandler.GetValue(dt.getString("loannumber"),"&nbsp;");
		remark = StringHandler.GetValue(dt.getString("remark"),"&nbsp;");
	}
 %>
 
<span id="C1" class="firstTitle"><br/>第一部分&nbsp;&nbsp;&nbsp;基本情况</span>
<table id="C1_RT1" width="100%" border="1">
  <tr>
    <td colspan="3">名称：<%=custName %></td>
    </tr>
  <tr>
    <td colspan="2">注册地址：<%=regaddress %></td>
    <td>邮编：<%=zipcode%></td>
  </tr>
  <tr>
    <td colspan="2">联系电话：<%=contacttel%></td>
    <td>传真：<%=fax%></td>
  </tr>
  <tr>
    <td width="33%">注册资本：<%=regcapital %>万<%=currency%></td>
    <td width="33%"> 实收资本：<%=incapital%>万<%=currency%></td>
    <td width="34%">贷款卡号：<%=loannumber%></td>
  </tr>
  <tr>
    <td >工商登记号：<%=tradnumber%></td>
    <td>税务登记号：<%=taxnumber%></td>
    <td>贷款卡记录：<%=loanLog%></td>
  </tr>
  <tr>
    <td>法定代表人：<%=legalman %></td>
    <td>身份证：<%=legalidcard %></td>
    <td>联系电话：<%=legaltel %></td>
  </tr>
  <tr>
    <td>总经理：<%=managername%></td>
    <td>身份证：<%=manageridcard%></td>
    <td>联系电话：<%=managertel%></td>
  </tr>
  <tr>
    <td>财务经理：<%=finamanager%></td>
    <td>身份证：<%=finaidcard%></td>
    <td>联系电话：<%=finatel%></td>
  </tr>
  <tr>
    <td colspan="2">基本帐户开户银行：<%=bankOrg%></td>
    <td>帐号：<%=account %></td>
  </tr>
  <tr>
    <td colspan="2">贷款银行：<%=loanBank%></td>
    <td>帐号：<%=loannumber%></td>
  </tr>
  <tr>
 
    <td colspan="3">经营范围：<%=remark%> </td>
    </tr>
</table>
<%
	//eqname,type,inamount,percents,storderdate
	params.put("subject","股本结构");
	String enteqstructSql = "select A.name,B.name as outType,A.inAmount,A.percents,A.storderDate from crm_Eeqstruct A left join ts_gvlist B on A.outType=B.id where A.ecustomerId='"+customerId+"'";
	params.put("queryStr",enteqstructSql);
	//params.put("isHql",false);
	cmns = "name,outType,inAmount,percents,storderdate#yyyy-MM-dd";
	params.put("cmns",cmns);
	dt = apMgr.getDataTable(params);
 %>
<table id="C1_RT2" width="100%" border="1">
  <tr>
    <td width="10%" rowspan="<%=(null == dt || dt.getRowCount() == 0)?2:dt.getRowCount()+1 %>" class="td_head">股本结构</td>
    <td width="20%"  class="td_head">出资人名称</td>
    <td width="20%"  class="td_head">出资方式</td>
    <td width="20%"  class="td_head">出资额</td>
    <td width="15%"  class="td_head">占比例</td>
    <td width="15%"  class="td_head">出资时间</td>
  </tr>
  <%
  	if(null == dt || dt.getRowCount() == 0){
  %>
   <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <%
  	}else{
  		for(int i=0,count=dt.getRowCount(); i<count; i++){
  %>
  <tr>
    <td style="text-align:center"><%=StringHandler.GetValue(dt.getString(i,"name"),"&nbsp;") %></td>
    <td style="text-align:center"><%=StringHandler.GetValue(dt.getString(i,"outType"),"&nbsp;") %></td>
    <td style="text-align:center"><%=StringHandler.getScienceVal(dt.getDouble(i,"inAmount")) %></td>
    <td style="text-align:center"><%=StringHandler.GetValue(dt.getString(i,"percents"),"0")+"%" %></td>
    <td style="text-align:center"><%=StringHandler.GetValue(dt.getDateString(i,"storderdate"),"&nbsp;") %></td>
  </tr>
  <%	
  		}
  	}
   %>
</table>
<br/>
<p id="C1_C1" style=" text-align:left;">企业实际控制人家庭状况
<% tabId="C1_C1_ET3";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[{id:'R1_CN1',type:2},{id:'R1_CN2',type:2},{id:'R1_CN3',type:2},{id:'R1_CN4',type:2},{id:'R1_CN5'},{id:'R2_CN1'},{id:'R3_CN1'}]}">
  <tr>
    <td width="15%"  class="td_head">实际控制人</td>
    <td width="25%"  class="td_head">家庭住址</td>
    <td width="20%"  class="td_head">家庭财产<br/>（房产、车辆等）</td>
    <td width="20%"  class="td_head">其他财产<br/>（股权、其他收入等）</td>
    <td width="20%"  class="td_head">存折户名及帐号</td>
  </tr>
  <tr id="<%=tabId%>_R1">
    <td id="<%=tabId%>_R1_CN1" rowspan="3">&nbsp;</td>
    <td id="<%=tabId%>_R1_CN2" rowspan="3">&nbsp;</td>
    <td id="<%=tabId%>_R1_CN3" rowspan="3">&nbsp;</td>
    <td id="<%=tabId%>_R1_CN4" rowspan="3">&nbsp;</td>
    <td id="<%=tabId%>_R1_CN5">&nbsp;</td>
  </tr>
  <tr id="<%=tabId%>_R2">
    <td  id="<%=tabId%>_R2_CN1">&nbsp;</td>
  </tr>
  <tr id="<%=tabId%>_R3">
    <td id="<%=tabId%>_R3_CN1">&nbsp;</td>
  </tr>
</table>
<br/>
<p id="C1_C2" style=" text-align:left;">申请担保项目情况:
<% tabId="C1_C2_ET4";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1"  cfg="{type:0,edcmns:[{id:'R1_CN1',type:0},{id:'R1_CN1_1',type:2},{id:'R1_CN2',type:1},{id:'R2_CN1',type:1},{id:'R2_CN2',type:1},{id:'R2_CN3',type:0},{id:'R3_CN1',type:1},{id:'R4_CN1',type:1}]}">
  <tr id="<%=tabId%>_R1">
    <td align="left" > 申请贷款金额：<span style="margin:0px;padding:0px;">
    <span id="<%=tabId%>_R1_CN1" style="width:80px;height:25px; margin:0px; padding-top:4px; "></span><span id="<%=tabId%>_R1_CN1_1" style="width:60px;height:25px;margin:0px; padding-top:4px;">万元</span></span></td>
    <td colspan="2" align="left" >期限：<span style="margin:0px;padding:0px;">
    <span id="<%=tabId%>_R1_CN2" style="width:100px;height:25px; margin:0px; padding-top:4px; "></span>年</span></td>
    </tr>
  <tr id="<%=tabId%>_R2">
    <td width="40%" align="left" style="padding:0px;">拟贷款银行：
    	<span id="<%=tabId%>_R2_CN1" style="width:180px; height:25px; margin:0px; padding-top:4px; "></span>
     </td>
    <td width="30%"  >贷款种类： 
    	<span id="<%=tabId%>_R2_CN2" style="width:125px;height:25px; margin:0px; padding-top:4px; "></span>
    </td>
    <td width="30%" >担保费率：
     <span style="margin:0px;padding:0px;">
    	 <span id="<%=tabId%>_R2_CN3" style="width:100px; height:25px;margin:0px; padding-top:4px; "></span>%</span>
     </td>
  </tr>
  <tr id="<%=tabId%>_R3">
    <td  align="right"  >贷款用途：</td>
    <td colspan="2" align="left"  id="<%=tabId%>_R3_CN1" >&nbsp;</td>
    </tr>
  <tr id="<%=tabId%>_R4">
    <td  align="right">还款来源方式：</td>
    <td colspan="2" align="left" id="<%=tabId%>_R4_CN1">&nbsp;</td>
    </tr>
</table>


<br/>
<span id="C2" class="firstTitle"><br/>第二部分&nbsp;&nbsp;&nbsp;企业评价及其经营模式和市场状况分析</span>
<br/>
<p id="C2_C1" style="text-align:left;">一、企业基本情况：
<% tabId="C2_C1_ET1";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1"  cfg="{type:0,edcmns:[{id:'R2_CN1',type:2},{id:'R3_CN1',type:2}]}"
>
  <tr>
    <td>1、企业概况（企业的历史沿革、基本生产经营方式及发展战略等）</td>
    </tr>
  <tr id="<%=tabId%>_R2">
    <td  style="height:50px;" id="<%=tabId%>_R2_CN1">&nbsp;</td>
    </tr>
  <tr>
    <td>2、管理水平（组织架构、管理层素质与经验、主要经历、发展思路、信用状况等）</td>
    </tr>
    <tr id="<%=tabId%>_R3">
    <td  style="height:50px;" id="<%=tabId%>_R3_CN1">&nbsp;</td>
    </tr>
</table>

<br/>
<p id="C2_C2" style=" text-align:left;">二、企业主要经营模式分析：</p>
<p id="C2_C2_C1" style=" text-align:left;">1、企业经营方式描述：
<% tabId="C2_C2_C1_ET2";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1"  cfg="{type:0, edcmns:[  {id:'R1_CN1',type:1},	{id:'R2_CN1',type:1},	{id:'R3_CN1',type:1},	{id:'R4_CN1',type:1},	{id:'R5_CN1',type:1}, {id:'R6_CN1',type:2}]}">
  <tr id="<%=tabId%>_R1">
    <td width="10%" rowspan="3">主要经营模式</td>
    <td width="10%">供应阶段</td>
    <td width="80%" id="<%=tabId%>_R1_CN1">&nbsp;</td>
    </tr>
  <tr id="<%=tabId%>_R2">
    <td>生产阶段</td>
    <td id="<%=tabId%>_R2_CN1">&nbsp;</td>
    </tr>
  <tr id="<%=tabId%>_R3">
    <td>销售阶段</td>
    <td id="<%=tabId%>_R3_CN1">&nbsp;</td>
    </tr>
    <tr  id="<%=tabId%>_R4">
    <td rowspan="2">资金结算方式</td>
    <td>采购阶段</td>
    <td  id="<%=tabId%>_R4_CN1">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R5">
    <td>销售阶段</td>
    <td id="<%=tabId%>_R5_CN1">&nbsp;</td>
    </tr>
   
    <tr id="<%=tabId%>_R6">
    <td colspan="3"> <p>生产经营模式、资金结算方式分析：</p>
      <span id="<%=tabId%>_R6_CN1" style="height:100px; width:98%"></span></td>
    </tr>
</table>

<p id="C2_C2_C2" style="text-align:left;">2、企业生产阶段：
<% tabId="C2_C2_C2_ET3";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1"   cfg="{type:0, edcmns:[  {id:'LR2_CN1',type:1},	{id:'LR2_CN2',type:1},	{id:'LR2_CN3',type:1},{id:'LR2_CN4',type:1},	{id:'LR2_CN5',type:1},	{id:'LR3_CN1',type:1},	{id:'LR3_CN2',type:1},	{id:'LR3_CN3',type:1},	{id:'LR3_CN4',type:1},	{id:'LR3_CN5',type:1},	{id:'R5_CN1',type:1},	{id:'R5_CN2',type:1},	{id:'R5_CN3',type:1},	{id:'R7_CN1',type:1},	{id:'R7_CN2',type:1},	{id:'R7_CN3',type:1},	{id:'R7_CN4',type:1},	{id:'R7_CN5',type:1}, {id:'R8_CN1',type:2}]}"
>
  <tr>
    <td width="25%">主要生产设备</td>
    <td width="25%">名称</td>
    <td width="25%">数量、价值</td>
    <td width="10%">使用年限</td>
    <td width="15%">是否入账</td>
    </tr>
  <tr id="<%=tabId%>_LR2">
    <td id="<%=tabId%>_LR2_CN1">&nbsp;</td>
    <td id="<%=tabId%>_LR2_CN2">&nbsp;</td>
    <td id="<%=tabId%>_LR2_CN3">&nbsp;</td>
    <td id="<%=tabId%>_LR2_CN4">&nbsp;</td>
    <td id="<%=tabId%>_LR2_CN5">&nbsp;</td>
    </tr>
  <tr id="<%=tabId%>_LR3">
    <td id="<%=tabId%>_LR3_CN1">&nbsp;</td>
    <td id="<%=tabId%>_LR3_CN2">&nbsp;</td>
    <td id="<%=tabId%>_LR3_CN3">&nbsp;</td>
    <td id="<%=tabId%>_LR3_CN4">&nbsp;</td>
    <td id="<%=tabId%>_LR3_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R4">
    <td>工人人数</td>
    <td>旺季人数/生产班次</td>
    <td>淡季人数/生产班次</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R5">
    <td id="<%=tabId%>_R5_CN1">&nbsp;</td>
    <td id="<%=tabId%>_R5_CN2">&nbsp;</td>
    <td id="<%=tabId%>_R5_CN3">&nbsp;</td>
    <td>----</td>
    <td>----</td>
    </tr>
   
   <tr id="<%=tabId%>_R6">
      <td>发包加工品种</td>
    <td>品种名称</td>
    <td>金额</td>
    <td>委托厂家</td>
    <td>依赖性</td>
    </tr>
    <tr id="<%=tabId%>_R7">
    <td id="<%=tabId%>_R7_CN1">&nbsp;</td>
    <td id="<%=tabId%>_R7_CN2">&nbsp;</td>
    <td id="<%=tabId%>_R7_CN3">&nbsp;</td>
    <td id="<%=tabId%>_R7_CN4">&nbsp;</td>
    <td id="<%=tabId%>_R7_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R8">
    <td colspan="5"><p>对企业生产过程进行描述，包括生产工艺流程：</p>
     	 <span id="<%=tabId%>_R8_CN1" style="height:100px; width:98%"></span></td> 
    </tr>
</table>

<p id="C2_C2_C3" style=" text-align:left;">3、企业主要产品结构：
<% tabId="C2_C2_C3_ET4";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1"   cfg="{type:0, edcmns:[	{id:'LR2_CN2',type:1},	{id:'LR2_CN3',type:1},	{id:'LR2_CN4',type:1},	{id:'LR2_CN5',type:1},   	{id:'LR3_CN2',type:1},		{id:'LR3_CN3',type:1},	{id:'LR3_CN4',type:1},	{id:'LR3_CN5',type:1},  	{id:'LR4_CN2',type:1},	{id:'LR4_CN3',type:1},		{id:'LR4_CN4',type:1},	{id:'LR4_CN5',type:1},	{id:'R6_CN1',type:2},	{id:'R7_CN1',type:2}, {id:'R8_CN1',type:2} ]}"
 >
<tr>
      <td width="10%">序号</td>
      <td width="30%">企业主要产品名称</td>
      <td width="15%">实际产量</td>
      <td width="15%">单位价格</td>
      <td width="30%">产品用途</td>
    </tr>
    <tr id="<%=tabId%>_LR2">
      <td >1</td>
      <td id="<%=tabId%>_LR2_CN2">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN4">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN5">&nbsp;</td>
    </tr> 
    <tr id="<%=tabId%>_LR3">
      <td width="12%">2</td>
      <td id="<%=tabId%>_LR3_CN2">&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN4">&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_LR4">
      <td>3</td>
      <td id="<%=tabId%>_LR4_CN2">&nbsp;</td>
      <td id="<%=tabId%>_LR4_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR4_CN4">&nbsp;</td>
      <td id="<%=tabId%>_LR4_CN5">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="5">品分析:（包括产品的性能、消费对象和适销性等）</td>
    </tr>
    <tr id="<%=tabId%>_R6">
      <td  style="height:50px;">产品的性能：</td>
      <td colspan="4" style="height:50px;"  id="<%=tabId%>_R6_CN1">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R7">
      <td  style="height:50px;">消费对象：</td>
      <td colspan="4" style="height:50px;" id="<%=tabId%>_R7_CN1">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R8">
      <td>适销性：</td>
      <td colspan="4" style="height:50px;" id="<%=tabId%>_R8_CN1">&nbsp;</td>
    </tr> 

</table>

<p id="C2_C2_C4" style=" text-align:left;">4、企业前五大供应商：
<% tabId="C2_C2_C4_ET5";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1"  cfg="{type:0,edcmns:[{id:'LR2_CN1',type:1},	{id:'LR2_CN2',type:1},	{id:'LR2_CN3',type:1},	{id:'LR2_CN4',type:1},  {id:'LR3_CN1',type:1},	{id:'LR3_CN2',type:1},	{id:'LR3_CN3',type:1},	{id:'LR3_CN4',type:1},{id:'LR4_CN1',type:1},	{id:'LR4_CN2',type:1},	{id:'LR4_CN3',type:1},	{id:'LR4_CN4',type:1},	{id:'LR5_CN1',type:1},	{id:'LR5_CN2',type:1},	{id:'LR5_CN3',type:1},	{id:'LR5_CN4',type:1},	{id:'LR6_CN1',type:1},	{id:'LR6_CN2',type:1},	{id:'LR6_CN3',type:1},	{id:'LR6_CN4',type:1}, {id:'R7_CN1',type:2} ]}">
	<tr> 
      <td width="35%">供应商</td>
      <td width="15%">年供货量</td>
      <td width="15%">金额</td>
      <td width="35%">结算方式</td>
    </tr>
    <tr id="<%=tabId%>_LR2"> 
      <td id="<%=tabId%>_LR2_CN1">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN2">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN4">&nbsp;</td>
    </tr> 
    <tr  id="<%=tabId%>_LR3">
      <td id="<%=tabId%>_LR3_CN1">&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN2">&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN4">&nbsp;</td>
    </tr>
    <tr  id="<%=tabId%>_LR4">   
      <td id="<%=tabId%>_LR4_CN1">&nbsp;</td>
      <td id="<%=tabId%>_LR4_CN2">&nbsp;</td>
      <td id="<%=tabId%>_LR4_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR4_CN4">&nbsp;</td>
    </tr>
      <tr id="<%=tabId%>_LR5">
      <td id="<%=tabId%>_LR5_CN1">&nbsp;</td>
      <td id="<%=tabId%>_LR5_CN2">&nbsp;</td>
      <td id="<%=tabId%>_LR5_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR5_CN4">&nbsp;</td>
    </tr> 
    <tr id="<%=tabId%>_LR6">   
      <td id="<%=tabId%>_LR6_CN1">&nbsp;</td>
      <td id="<%=tabId%>_LR6_CN2">&nbsp;</td>
      <td id="<%=tabId%>_LR6_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR6_CN4">&nbsp;</td>
    </tr>  
    <tr id="<%=tabId%>_R7">
      <td colspan="4"><p>分析：（供应链的稳定性、供应商结构的合理性、对单一供应商的依赖性、采购政策等方面分析。）</p>
       <span  id="<%=tabId%>_R7_CN1" style="height:100px; width:98%"></span> 
      </td>
    </tr>
</table>

<p id="C2_C2_C5" style=" text-align:left;">5、企业前五大销售客户：
<% tabId="C2_C2_C5_ET6";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1"   cfg="{type:0,edcmns:[	{id:'LR2_CN1',type:1},	{id:'LR2_CN2',type:0},	{id:'LR2_CN3',type:1},	{id:'LR2_CN4',type:1},{id:'LR3_CN1',type:1},	{id:'LR3_CN2',type:0},	{id:'LR3_CN3',type:1},	{id:'LR3_CN4',type:1},{id:'LR4_CN1',type:1},{id:'LR4_CN2',type:0},	{id:'LR4_CN3',type:1},	{id:'LR4_CN4',type:1},	{id:'LR5_CN1',type:1},	{id:'LR5_CN2',type:0},	{id:'LR5_CN3',type:1},	{id:'LR5_CN4',type:1},	{id:'LR6_CN1',type:1},	{id:'LR6_CN2',type:0},	{id:'LR6_CN3',type:1},	{id:'LR6_CN4',type:1}, {id:'R7_CN1',type:2} ]}">
	<tr> 
      <td width="35%">销售客户</td>
      <td width="15%">年销售额</td>
      <td width="15%">销售方式</td>
      <td width="35%">结算方式</td>
    </tr>
    <tr id="<%=tabId%>_LR2"> 
      <td id="<%=tabId%>_LR2_CN1">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN2">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN4">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_LR3">
      <td id="<%=tabId%>_LR3_CN1">&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN2" >&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN4">&nbsp;</td>
    </tr> 
    <tr id="<%=tabId%>_LR4">   
      <td id="<%=tabId%>_LR4_CN1">&nbsp;</td>
      <td id="<%=tabId%>_LR4_CN2">&nbsp;</td>
      <td id="<%=tabId%>_LR4_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR4_CN4">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_LR5">
      <td id="<%=tabId%>_LR5_CN1">&nbsp;</td>
      <td id="<%=tabId%>_LR5_CN2">&nbsp;</td>
      <td id="<%=tabId%>_LR5_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR5_CN4">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_LR6">   
      <td id="<%=tabId%>_LR6_CN1">&nbsp;</td>
      <td id="<%=tabId%>_LR6_CN2">&nbsp;</td>
      <td id="<%=tabId%>_LR6_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR6_CN4">&nbsp;</td>
    </tr> 
    <tr id="<%=tabId%>_R7">
      <td colspan="4"><p>分析：（接单能力、客户结构、客户品牌和稳定性、与客户资金往来的主要结算银行及结算方式。） </p>
       <span id="<%=tabId%>_R7_CN1" style="height:100px; width:98%"></span> 
      </td>
    </tr>
</table>

<br/>
<p id="C2_C3" style=" text-align:left;">三、市场竞争状况：
<% tabId="C2_C3_ET1";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1"   cfg="{type:0,edcmns:[{id:'R1_CN1',type:2}]}">
  <tr id="<%=tabId%>_R1">
    <td><p>分析：（企业市场竞争能力及市场拓展能力。）</p>
      <span id="<%=tabId%>_R1_CN1" style="height:100px; width:98%"></span> 
     </td>
    </tr>
</table>

<br/>
<p id="C2_C4" style=" text-align:left;">四、成长性分析：
<% tabId="C2_C4_ET1";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1"   cfg="{type:0,edcmns:[{id:'R1_CN1',type:2},{id:'R2_CN1',type:2},{id:'R3_CN1',type:2},{id:'R4_CN1',type:2}]}">
  <tr id="<%=tabId%>_R1">
    <td>股东结构的稳定性、管理团队、管理水平的先进性、前瞻性分析：
       <span id="<%=tabId%>_R1_CN1" style="height:100px; width:98%"></span> 
     </td>
   </tr>
    <tr id="<%=tabId%>_R2">
    <td>1、&nbsp;生产工艺技术及产品的先进性分析：
    	 <span id="<%=tabId%>_R2_CN1" style="height:100px; width:98%"></span>     
    </td>
   </tr>
   <tr id="<%=tabId%>_R3">
    <td>2、未来市场空间及市场营销能力分析：
     	 <span id="<%=tabId%>_R3_CN1" style="height:100px; width:98%"></span> 
     </td>
   </tr>
   <tr id="<%=tabId%>_R4">
    <td>3、行业成长性分析：
       <span id="<%=tabId%>_R4_CN1" style="height:100px; width:98%"></span> 
     </td>
   </tr> 
</table>

<br/>
<p id="C2_C5" style=" text-align:left;">五、 经营环境分析：
<% tabId="C2_C5_ET1";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1"   cfg="{type:0,edcmns:[{id:'R1_CN1',type:2}]}">
  <tr id="<%=tabId%>_R1">
    <td>（从行业政策、法律环境、环保政策、产业政策、信贷政策、税收政策等分析）
       <span id="<%=tabId%>_R1_CN1" style="height:100px; width:98%"></span> 
    </td>
    </tr>
</table>

<br/>
<span id="C3" class="firstTitle"><br/>第三部分&nbsp;&nbsp;&nbsp;企业资信情况分析</span>
<br/>
<p id="C3_C1" style="text-align:left;">一、 企业开户情况：
<% tabId="C3_C1_ET1";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1"   cfg="{type:0,edcmns:[{id:'LR2_CN1',type:1},	{id:'LR2_CN2',type:1},	{id:'LR2_CN3',type:1},	{id:'LR2_CN4',type:1},	{id:'LR2_CN5',type:0},{id:'LR3_CN1',type:1},	{id:'LR3_CN2',type:1},	{id:'LR3_CN3',type:1},	{id:'LR3_CN4',type:1},	{id:'LR3_CN5',type:0},{id:'LR4_CN1',type:1},	{id:'LR4_CN2',type:1},	{id:'LR4_CN3',type:1},	{id:'LR4_CN4',type:1},	{id:'LR4_CN5',type:0},{id:'LR5_CN1',type:1},	{id:'LR5_CN2',type:1},	{id:'LR5_CN3',type:1},	{id:'LR5_CN4',type:1},	{id:'LR5_CN5',type:0},{id:'R6_CN1',type:2} ]}">
<tr>
      <td width="15%">帐户</td>
      <td width="35%">银行（分支机构）</td>
      <td width="25%">帐号</td>
      <td width="15%">月均结算量</td>
      <td width="20%">月均存款余额</td>
    </tr>
    <tr id="<%=tabId%>_LR2">
      <td id="<%=tabId%>_LR2_CN1" >基本结算帐户</td>
      <td id="<%=tabId%>_LR2_CN2" >&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN3" >&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN4" >&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN5" >&nbsp;</td>
    </tr> 
    <tr id="<%=tabId%>_LR3">
      <td id="<%=tabId%>_LR3_CN1">一般结算帐户1</td>
      <td id="<%=tabId%>_LR3_CN2" >&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN3" >&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN4" >&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN5" >&nbsp;</td>
    </tr> 
    <tr id="<%=tabId%>_LR4">
      <td id="<%=tabId%>_LR4_CN1">一般结算帐户2</td>
      <td id="<%=tabId%>_LR4_CN2" >&nbsp;</td>
      <td id="<%=tabId%>_LR4_CN3" >&nbsp;</td>
      <td id="<%=tabId%>_LR4_CN4" >&nbsp;</td>
      <td id="<%=tabId%>_LR4_CN5" >&nbsp;</td>
    </tr> 
	<tr id="<%=tabId%>_LR5">
      <td id="<%=tabId%>_LR5_CN1">外汇帐户</td>
      <td id="<%=tabId%>_LR5_CN2" >&nbsp;</td>
      <td id="<%=tabId%>_LR5_CN3" >&nbsp;</td>
      <td id="<%=tabId%>_LR5_CN4" >&nbsp;</td>
      <td id="<%=tabId%>_LR5_CN5" >&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R6">
      <td>其他情况说明</td>
      <td colspan="4" style="height:100px;" id="<%=tabId%>_R6_CN1">&nbsp;</td>
    </tr>
</table>

<br/>
<p id="C3_C2" style=" text-align:left;">二、银行借款情况：
<% tabId="C3_C2_ET2";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1"  cfg="{type:0,edcmns:[{id:'LR2_CN1',type:1}, {id:'LR2_CN2',type:0}, {id:'LR2_CN3',type:1}, {id:'LR2_CN4',type:1}, {id:'LR2_CN5',type:1}, {id:'LR2_CN6',type:1}, {id:'LR2_CN7',type:1},{id:'LR3_CN1',type:1}, {id:'LR3_CN2',type:0}, {id:'LR3_CN3',type:1}, {id:'LR3_CN4',type:1}, {id:'LR3_CN5',type:1}, {id:'LR3_CN6',type:1}, {id:'LR3_CN7',type:1},  {id:'LR4_CN1',type:1}, {id:'LR4_CN2',type:0}, {id:'LR4_CN3',type:1}, {id:'LR4_CN4',type:1}, {id:'LR4_CN5',type:1}, {id:'LR4_CN6',type:1}, {id:'LR4_CN7',type:1},{id:'LR5_CN1',type:1}, {id:'LR5_CN2',type:0}, {id:'LR5_CN3',type:1}, {id:'LR5_CN4',type:1}, {id:'LR5_CN5',type:1}, {id:'LR5_CN6',type:1}, {id:'LR5_CN7',type:1},{id:'R6_CN1',type:2}]}" >
<tr>
      <td width="20%"><p>银行名称</p></td>
      <td width="10%">借款金额</td>
      <td width="10%">借款期限</td>
      <td width="15%">信贷品种</td>
      <td width="15%">担保方式</td>
      <td width="10%">贷款分类结果</td>
      <td width="20%">备注</td>
    </tr>
    <tr id="<%=tabId%>_LR2">
      <td id="<%=tabId%>_LR2_CN1">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN2">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN4">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN5">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN6">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN7">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_LR3">
      <td id="<%=tabId%>_LR3_CN1">&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN2">&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN4">&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN5">&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN6">&nbsp;</td>
      <td id="<%=tabId%>_LR3_CN7">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_LR4">
      <td id="<%=tabId%>_LR4_CN1">&nbsp;</td>
      <td  id="<%=tabId%>_LR4_CN2">&nbsp;</td>
      <td  id="<%=tabId%>_LR4_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR4_CN4">&nbsp;</td>
      <td id="<%=tabId%>_LR4_CN5">&nbsp;</td>
      <td id="<%=tabId%>_LR4_CN6">&nbsp;</td>
      <td id="<%=tabId%>_LR4_CN7">&nbsp;</td>
    </tr>
	  <tr id="<%=tabId%>_LR5">
      <td id="<%=tabId%>_LR5_CN1">&nbsp;</td>
      <td id="<%=tabId%>_LR5_CN2">&nbsp;</td>
      <td id="<%=tabId%>_LR5_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR5_CN4">&nbsp;</td>
      <td id="<%=tabId%>_LR5_CN5">&nbsp;</td>
      <td id="<%=tabId%>_LR5_CN6">&nbsp;</td>
      <td id="<%=tabId%>_LR5_CN7">&nbsp;</td>
      </tr>
    <tr id="<%=tabId%>_R6">
      <td colspan="7">分析：打印贷款卡，查询企业还款信用记录，有无逃废债务记录，对外担保情况（含主要关联企业）。
      	<span id="<%=tabId%>_R6_CN1" style="height:100px; width:98%"></span> 
      </td>
      </tr>
</table>

<br/>
<p id="C3_C3" style=" text-align:left;">三、所有者（法定代表人/股东）借款情况：
<% tabId="C3_C3_ET3";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1"  cfg="{type:0,edcmns:[ {id:'LR2_CN1',type:1}, {id:'LR2_CN2',type:0}, {id:'LR2_CN3',type:1}, {id:'LR2_CN4',type:1}, {id:'LR2_CN5',type:1}, {id:'LR2_CN6',type:1}, {id:'R3_CN1',type:2}]}" >
<tr>
      <td width="20%"><p>银行名称</p></td>
      <td width="10%">借款金额</td>
      <td width="10%">借款期限</td>
      <td width="15%">信贷品种</td>
      <td width="15%">担保方式</td>
      <td width="30%">贷款分类结果</td>

    </tr>
    <tr id="<%=tabId%>_LR2">
      <td id="<%=tabId%>_LR2_CN1">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN2">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN3">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN4">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN5">&nbsp;</td>
      <td id="<%=tabId%>_LR2_CN6">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R3">
      <td colspan="6">分析：（主要针对企业贷款总额，在担保期间还款对企业可能产生的影响。）
      	<span id="<%=tabId%>_R3_CN1" style="height:100px; width:98%"></span> 
      </td>
    </tr>
</table>

<br/>
<span id="C4" class="firstTitle"><br/>第四部分&nbsp;&nbsp;&nbsp;企业财务状况分析</span>
<br/>
<p  id="C4_C1" style=" text-align:left;">一、企业资产负债状况</p>
<p  id="C4_C1_C1" style=" text-align:left;">1、企业近三年及当期资产负债表（调整后）&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   <span style="float:right">单位：万元</span>
<% tabId="C4_C1_C1_ET1";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[ 
{id:'R2_CN1',type:0},{id:'R3_CN1',type:0},{id:'R3_CN2',type:0},{id:'R3_CN3',type:0},
{id:'R4_CN2',type:0},{id:'R4_CN3',type:0},{id:'R4_CN4',type:0},{id:'R4_CN5',type:0},
{id:'R5_CN2',type:0},{id:'R5_CN3',type:0},{id:'R5_CN4',type:0},{id:'R5_CN5',type:0},
{id:'R6_CN2',type:0},{id:'R6_CN3',type:0},{id:'R6_CN4',type:0},{id:'R6_CN5',type:0},
{id:'R7_CN2',type:0},{id:'R7_CN3',type:0},{id:'R7_CN4',type:0},{id:'R7_CN5',type:0},
{id:'R8_CN2',type:0},{id:'R8_CN3',type:0},{id:'R8_CN4',type:0},{id:'R8_CN5',type:0},
{id:'R9_CN2',type:0},{id:'R9_CN3',type:0},{id:'R9_CN4',type:0},{id:'R9_CN5',type:0},
{id:'R10_CN2',type:0},{id:'R10_CN3',type:0},{id:'R10_CN4',type:0},{id:'R10_CN5',type:0},
{id:'R11_CN2',type:0},{id:'R11_CN3',type:0},{id:'R11_CN4',type:0},{id:'R11_CN5',type:0},
{id:'R12_CN2',type:0},{id:'R12_CN3',type:0},{id:'R12_CN4',type:0},{id:'R12_CN5',type:0},
{id:'R13_CN2',type:0},{id:'R13_CN3',type:0},{id:'R13_CN4',type:0},{id:'R13_CN5',type:0},
{id:'R14_CN2',type:0},{id:'R14_CN3',type:0},{id:'R14_CN4',type:0},{id:'R14_CN5',type:0},
{id:'R15_CN2',type:0},{id:'R15_CN3',type:0},{id:'R15_CN4',type:0},{id:'R15_CN5',type:0},
{id:'R16_CN2',type:0},{id:'R16_CN3',type:0},{id:'R16_CN4',type:0},{id:'R16_CN5',type:0},
{id:'R17_CN2',type:0},{id:'R17_CN3',type:0},{id:'R17_CN4',type:0},{id:'R17_CN5',type:0},
{id:'R18_CN2',type:0},{id:'R18_CN3',type:0},{id:'R18_CN4',type:0},{id:'R18_CN5',type:0},
{id:'R19_CN2',type:0},{id:'R19_CN3',type:0},{id:'R19_CN4',type:0},{id:'R19_CN5',type:0},
{id:'R20_CN2',type:0},{id:'R20_CN3',type:0},{id:'R20_CN4',type:0},{id:'R20_CN5',type:0},
{id:'R21_CN2',type:0},{id:'R21_CN3',type:0},{id:'R21_CN4',type:0},{id:'R21_CN5',type:0},
{id:'R22_CN2',type:0},{id:'R22_CN3',type:0},{id:'R22_CN4',type:0},{id:'R22_CN5',type:0},
{id:'R23_CN2',type:0},{id:'R23_CN3',type:0},{id:'R23_CN4',type:0},{id:'R23_CN5',type:0},
{id:'R24_CN2',type:0},{id:'R24_CN3',type:0},{id:'R24_CN4',type:0},{id:'R24_CN5',type:0},
{id:'R25_CN2',type:0},{id:'R25_CN3',type:0},{id:'R25_CN4',type:0},{id:'R25_CN5',type:0},
{id:'R26_CN2',type:0},{id:'R26_CN3',type:0},{id:'R26_CN4',type:0},{id:'R26_CN5',type:0},
{id:'R27_CN2',type:0},{id:'R27_CN3',type:0},{id:'R27_CN4',type:0},{id:'R27_CN5',type:0},
{id:'R28_CN2',type:0},{id:'R28_CN3',type:0},{id:'R28_CN4',type:0},{id:'R28_CN5',type:0},
{id:'R29_CN2',type:0},{id:'R29_CN3',type:0},{id:'R29_CN4',type:0},{id:'R29_CN5',type:0},
{id:'R30_CN2',type:0},{id:'R30_CN3',type:0},{id:'R30_CN4',type:0},{id:'R30_CN5',type:0},
{id:'R31_CN1',type:2},{id:'R32_CN1',type:2},{id:'R33_CN1',type:2}]}">
    <tr>
      <td colspan="5" style="text-align:center" >企业资产负债状况</td>
      </tr>
    <tr id="<%=tabId%>_R2">
      <td width="30%" rowspan="2" >项&nbsp;&nbsp;目</td>
      <td colspan="3"  style="text-align:center">前三年数据</td>
      <td rowspan="2"  style="text-align:center"><span style="margin:0px;padding:0px;">本年 <span id="<%=tabId%>_R2_CN1" style="width:30px;height:25px; margin:0px; padding-top:4px; "></span>月末</span></td>
    </tr>
    <tr id="<%=tabId%>_R3">
      <td width="20%"   style="text-align:center"><span style="margin:0px;padding:0px;">
    <span id="<%=tabId%>_R3_CN1" style="width:65px;height:25px; margin:0px; padding-top:4px; "></span>年末</span></td>
      <td width="20%"  style="text-align:center"><span style="margin:0px;padding:0px;">
    <span id="<%=tabId%>_R3_CN2" style="width:65px;height:25px; margin:0px; padding-top:4px; "></span>年末</span></td>
      <td  style="text-align:center"><span id="<%=tabId%>_R3_CN3" style="width:65px;height:25px; margin:0px; padding-top:4px; "></span> 年末</td>
     </tr>
	 <tr id="<%=tabId%>_R4">
      <td>一、资产总额</td>
      <td id="<%=tabId%>_R4_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R4_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R4_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R4_CN5">&nbsp;</td>
    </tr>
     <tr id="<%=tabId%>_R5">
      <td>1、流动资产合计</td>
      <td id="<%=tabId%>_R5_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R5_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R5_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R5_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R6">
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;货币资金</td>
      <td id="<%=tabId%>_R6_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R6_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R6_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R6_CN5">&nbsp;</td>
    </tr>
	  <tr id="<%=tabId%>_R7">
       <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;应收帐款</td>
      <td id="<%=tabId%>_R7_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R7_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R7_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R7_CN5">&nbsp;</td>
    </tr>
      <tr id="<%=tabId%>_R8">
       <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;预付帐款</td>
      <td id="<%=tabId%>_R8_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R8_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R8_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R8_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R9">
       <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其它应收款</td>
      <td id="<%=tabId%>_R9_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R9_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R9_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R9_CN5">&nbsp;</td>
    </tr>
	<tr id="<%=tabId%>_R10">
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　存  &nbsp;&nbsp;&nbsp;&nbsp;  货</td>
      <td id="<%=tabId%>_R10_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R10_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R10_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R10_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R11">
      <td>2、长期投资合计</td>
      <td id="<%=tabId%>_R11_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R11_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R11_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R11_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R12">
      <td>3、固定资产合计</td>
      <td id="<%=tabId%>_R12_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R12_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R12_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R12_CN5">&nbsp;</td>
    </tr>
	<tr id="<%=tabId%>_R13">
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　固定资产净值</td>
      <td id="<%=tabId%>_R13_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R13_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R13_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R13_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R14">
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　在建工程</td>
      <td id="<%=tabId%>_R14_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R14_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R14_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R14_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R15">
      <td>4、无形及递延资产合计</td>
      <td id="<%=tabId%>_R15_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R15_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R15_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R15_CN5">&nbsp;</td>
    </tr>
	<tr id="<%=tabId%>_R16">
      <td>二、负债总额</td>
      <td id="<%=tabId%>_R16_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R16_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R16_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R16_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R17">
      <td>1、流动负债合计</td>
      <td id="<%=tabId%>_R17_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R17_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R17_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R17_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R18">
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　短期借款</td>
      <td id="<%=tabId%>_R18_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R18_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R18_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R18_CN5">&nbsp;</td>
    </tr>
	<tr id="<%=tabId%>_R19">
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　应付帐款</td>
      <td id="<%=tabId%>_R19_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R19_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R19_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R19_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R20">
     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　预收账款</td>
      <td id="<%=tabId%>_R20_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R20_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R20_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R20_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R21">
     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 其他应付款</td>
      <td id="<%=tabId%>_R21_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R21_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R21_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R21_CN5">&nbsp;</td>
    </tr>
	<tr id="<%=tabId%>_R22">
     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　其他</td>
      <td id="<%=tabId%>_R22_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R22_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R22_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R22_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R23">
      <td>2、长期负债合计</td>
      <td id="<%=tabId%>_R23_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R23_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R23_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R23_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R24">
      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　长期借款</td>
      <td id="<%=tabId%>_R24_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R24_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R24_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R24_CN5">&nbsp;</td>
    </tr>
	<tr id="<%=tabId%>_R25">
      <td>三、所在者权益</td>
      <td id="<%=tabId%>_R25_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R25_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R25_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R25_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R26">
      <td>1、实收资本</td>
      <td id="<%=tabId%>_R26_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R26_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R26_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R26_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R27">
      <td>2、资本公积</td>
      <td id="<%=tabId%>_R27_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R27_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R27_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R27_CN5">&nbsp;</td>
    </tr>
	<tr id="<%=tabId%>_R28">
      <td>3、盈余公积</td>
      <td id="<%=tabId%>_R28_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R28_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R28_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R28_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R29">
      <td>4、未分配利润</td>
      <td id="<%=tabId%>_R29_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R29_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R29_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R29_CN5">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R30">
       <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　★或有负债</td>
      <td id="<%=tabId%>_R30_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R30_CN3">&nbsp;</td>
      <td id="<%=tabId%>_R30_CN4">&nbsp;</td>
      <td id="<%=tabId%>_R30_CN5">&nbsp;</td>
    </tr>
	<tr id="<%=tabId%>_R31">
      <td colspan="5"><p>说明：（现场调查核实过程、重要科目说明及趋势分析等）</p>
       <span id="<%=tabId%>_R31_CN1" style="height:100px; width:98%"></span>
      </td>
     </tr>
     <tr id="<%=tabId%>_R32">
      <td colspan="5"><p>负债结构和增长趋势：</p>
       <span id="<%=tabId%>_R32_CN1" style="height:100px; width:98%"></span>
      </td>
     </tr>
    <tr id="<%=tabId%>_R33">
      <td colspan="5"><p>所有者权益和未分配利润：</p>
        <span id="<%=tabId%>_R33_CN1" style="height:100px; width:98%"></span>
      </td>
    </tr>
</table>

<br/>
<p id="C4_C1_C2" style=" text-align:left;">2、重要科目的核实分析（当期）</p>
<p id="C4_C1_C2_C1"  style=" text-align:left;">①、资产情况核实分析 <span style="float:right">单位：万元</span>
<% tabId="C4_C1_C2_C1_ET2";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[ 
{id:'R2_CN1',type:1},
{id:'R2_CN2',type:0},
{id:'R2_CN3',type:2},

{id:'R3_CN1',type:1},
{id:'R3_CN2',type:0},
{id:'R4_CN1',type:1},
{id:'R4_CN2',type:0},

{id:'R6_CN1',type:1},
{id:'R6_CN2',type:0},
{id:'R6_CN3',type:2},

{id:'R7_CN1',type:1},
{id:'R7_CN2',type:0},

{id:'R8_CN1',type:1},
{id:'R8_CN2',type:0},

{id:'R9_CN1',type:1},
{id:'R9_CN2',type:0},

{id:'R10_CN1',type:1},
{id:'R10_CN2',type:0},

{id:'R11_CN2',type:1},
{id:'R11_CN3',type:0},

{id:'R12_CN2',type:1},
{id:'R12_CN3',type:0},

{id:'R13_CN2',type:1},
{id:'R13_CN3',type:0},

{id:'R14_CN2',type:1},
{id:'R14_CN3',type:0},

{id:'R15_CN2',type:1},
{id:'R15_CN3',type:0},

{id:'R16_CN1',type:0},

{id:'R17_CN2',type:0},
{id:'R17_CN3',type:0},
{id:'R17_CN4',type:2},


{id:'R18_CN2',type:0},
{id:'R18_CN3',type:0},

{id:'R19_CN2',type:0},
{id:'R19_CN3',type:0},

{id:'R21_CN1',type:0},
{id:'R21_CN2',type:0},
{id:'R21_CN3',type:0},

{id:'R22_CN1',type:2},
{id:'R23_CN1',type:2},
{id:'R24_CN1',type:2}
]}">
	<tr>
      <td width="5%" rowspan="16">流动资产</td>
      <td width="10%" rowspan="4">主要存货</td>
      <td>分类</td>
      <td width="15%">金额</td>
      <td>说明</td>
    </tr>
    <tr  id="<%=tabId%>_R2" >
      <td id="<%=tabId%>_R2_CN1">&nbsp;</td>
      <td id="<%=tabId%>_R2_CN2" width="15%">&nbsp;</td>
      <td id="<%=tabId%>_R2_CN3" rowspan="3">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R3">
      <td id="<%=tabId%>_R3_CN1">&nbsp;</td>
      <td id="<%=tabId%>_R3_CN2" width="15%">&nbsp;</td>
      </tr>
    <tr  id="<%=tabId%>_R4">
      <td id="<%=tabId%>_R4_CN1">&nbsp;</td>
      <td id="<%=tabId%>_R4_CN2" width="15%">&nbsp;</td>
      </tr>
      <tr>
      <td width="10%" rowspan="6">其他<br />
        应收款<br />
        （前五大客户）</td>
      <td>对方单位</td>
      <td width="15%">金额</td>
      <td>说明</td>
    </tr>
      <tr  id="<%=tabId%>_R6">
        <td id="<%=tabId%>_R6_CN1">&nbsp;</td>
        <td id="<%=tabId%>_R6_CN2">&nbsp;</td>
        <td  id="<%=tabId%>_R6_CN3" rowspan="10">&nbsp;</td>
      </tr>
      <tr  id="<%=tabId%>_R7">
      <td id="<%=tabId%>_R7_CN1">&nbsp;</td>
      <td id="<%=tabId%>_R7_CN2" width="15%">&nbsp;</td>
      </tr>
    <tr id="<%=tabId%>_R8">
      <td id="<%=tabId%>_R8_CN1">&nbsp;</td>
      <td id="<%=tabId%>_R8_CN2" width="15%">&nbsp;</td>
      </tr>
    <tr id="<%=tabId%>_R9">
      <td id="<%=tabId%>_R9_CN1">&nbsp;</td>
      <td id="<%=tabId%>_R9_CN2">&nbsp;</td>
      </tr>
    <tr id="<%=tabId%>_R10">
      <td  id="<%=tabId%>_R10_CN1">&nbsp;</td>
      <td id="<%=tabId%>_R10_CN2">&nbsp;</td>
      </tr>
    <tr id="<%=tabId%>_R11">
      <td width="10%" rowspan="5"><p >应收账款（前五大客户）</p></td>
      <td id="<%=tabId%>_R11_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R11_CN3">&nbsp;</td>
      </tr>
    <tr id="<%=tabId%>_R12">
      <td id="<%=tabId%>_R12_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R12_CN3">&nbsp;</td>
      </tr>
    <tr id="<%=tabId%>_R13">
      <td id="<%=tabId%>_R13_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R13_CN3">&nbsp;</td>
      </tr>
    <tr id="<%=tabId%>_R14">
      <td id="<%=tabId%>_R14_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R14_CN3">&nbsp;</td>
      </tr>
    <tr id="<%=tabId%>_R15">
      <td id="<%=tabId%>_R15_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R15_CN3">&nbsp;</td>
      </tr>
    <tr id="<%=tabId%>_R16">
      <td width="10%">预付帐款</td>
      <td  id="<%=tabId%>_R16_CN1" colspan="3">&nbsp;</td>
      </tr>
      
      <tr>
      <td width="5%" rowspan="4">固定资产</td>
      <td width="10%">名称</td>
      <td>原值</td>
      <td width="15%">已用年限</td>
      <td>说明</td>
    </tr>
    <tr id="<%=tabId%>_R17">
      <td width="10%">房屋建筑物</td>
      <td id="<%=tabId%>_R17_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R17_CN3" width="15%">&nbsp;</td>
      <td id="<%=tabId%>_R17_CN4" rowspan="3">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R18">
      <td width="10%"><p >机器设备</p></td>
      <td id="<%=tabId%>_R18_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R18_CN3" width="15%">&nbsp;</td>
      </tr>
    <tr id="<%=tabId%>_R19">
      <td width="10%">交通运输工具</td>
      <td id="<%=tabId%>_R19_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R19_CN3" width="15%">&nbsp;</td>
    </tr>
      <tr>
        <td width="5%" rowspan="4">无形及递延资产</td>
        <td width="10%" rowspan="2"><p >土地使<br />
        用权</p></td>
        <td>评估值</td>
        <td>面积</td>
        <td>情况说明</td>
      </tr>
    <tr id="<%=tabId%>_R21">
      <td id="<%=tabId%>_R21_CN1">&nbsp;</td>
      <td id="<%=tabId%>_R21_CN2">&nbsp;</td>
      <td id="<%=tabId%>_R21_CN3">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R22">
      <td><p >可转让的其它权利</p></td>
      <td id="<%=tabId%>_R22_CN1"colspan="3">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R23">
      <td>长期待摊费用</td>
      <td id="<%=tabId%>_R23_CN1" colspan="3">&nbsp;</td>
    </tr>
    <tr id="<%=tabId%>_R24">
      <td colspan="5"><p >资产分析：（企业主要资产占总资产的比例，存货及应收款形成的主要原因，资产的有效性方面分析）</p>
        <span id="<%=tabId%>_R24_CN1" style="height:100px; width:98%"></span> 
      </td>
      </tr>
    </table>
    
    <p id="C4_C1_C2_C2" style=" text-align:left;">②、负债情况核实分析<span style="float:right">单位：万元</span>
    <% tabId="C4_C1_C2_C2_ET3";%>
    <span id="TL_<%=tabId%>" <%=style%>>
    <Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
    <Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
    </span>
    </p>
    <table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[ 
{id:'R1_CN1',type:0},
{id:'R3_CN1',type:1},
{id:'R3_CN2',type:0},
{id:'R3_CN3',type:2	},
{id:'R4_CN1',type:1},
{id:'R4_CN2',type:0},
{id:'R5_CN1',type:1},
{id:'R5_CN2',type:0},
{id:'R6_CN1',type:1},
{id:'R6_CN2',type:0},
{id:'R7_CN1',type:1},
{id:'R7_CN2',type:0},
{id:'R9_CN1',type:1},
{id:'R9_CN2',type:0},
{id:'R9_CN3',type:2	},
{id:'R10_CN1',type:1},
{id:'R10_CN2',type:0},
{id:'R11_CN1',type:1},
{id:'R11_CN2',type:0},
{id:'R12_CN1',type:0},
{id:'R13_CN1',type:0},
{id:'R14_CN1',type:0},
{id:'R15_CN1',type:0},
{id:'R16_CN1',type:0}]}">
	<tr id="<%=tabId%>_R1">
	  <td width="5%" rowspan="14" >流动负债</td>
	  <td width="20%" >短期借款</td>
	  <td colspan="3"  id="<%=tabId%>_R1_CN1">&nbsp;</td>
	  </tr>
	<tr>
	  <td rowspan="6" >应付帐款<br />
	    （前五大<br />
	    供应商）</td>
	  <td width="30%">对方单位</td>
	  <td width="20%">金额</td>
	  <td width="25%">说明</td>
	  </tr>
	<tr id="<%=tabId%>_R3">
	  <td id="<%=tabId%>_R3_CN1">&nbsp;</td>
	  <td id="<%=tabId%>_R3_CN2">&nbsp;</td>
	  <td id="<%=tabId%>_R3_CN3" rowspan="5">&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R4">
	  <td id="<%=tabId%>_R4_CN1">&nbsp;</td>
	  <td id="<%=tabId%>_R4_CN2">&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R5">
	  <td id="<%=tabId%>_R5_CN1">&nbsp;</td>
	  <td id="<%=tabId%>_R5_CN2">&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R6">
	  <td id="<%=tabId%>_R6_CN1">&nbsp;</td>
	  <td id="<%=tabId%>_R6_CN2">&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R7">
	  <td id="<%=tabId%>_R7_CN1">&nbsp;</td>
	  <td id="<%=tabId%>_R7_CN2">&nbsp;</td>
	  </tr>
	<tr>
	  <td rowspan="4" >其它<br />
	    应付款</td>
	  <td>对方单位</td>
	  <td>金额</td>
	  <td>说明</td>
	  </tr>
	<tr id="<%=tabId%>_R9">
	  <td id="<%=tabId%>_R9_CN1">&nbsp;</td>
	  <td id="<%=tabId%>_R9_CN2">&nbsp;</td>
	  <td id="<%=tabId%>_R9_CN3"rowspan="3">&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R10">
	  <td id="<%=tabId%>_R10_CN1">&nbsp;</td>
	  <td id="<%=tabId%>_R10_CN2">&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R11">
	  <td id="<%=tabId%>_R11_CN1">&nbsp;</td>
	  <td id="<%=tabId%>_R11_CN2">&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R12">
	  <td >应付票据</td>
	  <td id="<%=tabId%>_R12_CN1" colspan="3">&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R13">
	  <td >预收帐款</td>
	  <td id="<%=tabId%>_R13_CN1" colspan="3">&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R14">
	  <td >其他负债</td>
	  <td id="<%=tabId%>_R14_CN1" colspan="3">&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R15">
	  <td rowspan="2" >长期负债</td>
	  <td >长期借款</td>
	  <td  id="<%=tabId%>_R15_CN1" colspan="3">&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R16">
	  <td >长期应付款</td>
	  <td  id="<%=tabId%>_R16_CN1" colspan="3">&nbsp;</td>
	  </tr>
    </table>
    
    <br/>
<p id="C4_C1_C3" style=" text-align:left;">3、所有者权益（净资产）核实分析：
<% tabId="C4_C1_C3_ET4";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[ 
{id:'R2_CN1',type:1},
{id:'R2_CN2',type:0},
{id:'R2_CN3',type:1},
{id:'R2_CN4',type:2},
{id:'R3_CN1',type:1},
{id:'R3_CN2',type:0},
{id:'R3_CN3',type:1},
{id:'R4_CN1',type:1},
{id:'R4_CN2',type:0},
{id:'R4_CN3',type:1},
{id:'R5_CN1',type:1},
{id:'R5_CN2',type:0},
{id:'R5_CN3',type:1},
{id:'R6_CN1',type:0},
{id:'R7_CN1',type:0},
{id:'R8_CN1',type:0},
{id:'R9_CN1',type:0}
]}">
	<tr>
	  <td width="15%" rowspan="5" >实收资本</td>
	  <td width="30%" >出资方式</td>
	  <td width="15%">金额</td>
	  <td width="10%">占比</td>
	  <td width="30%">说明</td>
	  </tr>
	<tr id="<%=tabId%>_R2">
	  <td id="<%=tabId%>_R2_CN1" >&nbsp;</td>
	  <td id="<%=tabId%>_R2_CN2">&nbsp;</td>
	  <td id="<%=tabId%>_R2_CN3">&nbsp;</td>
	  <td id="<%=tabId%>_R2_CN4" rowspan="4">&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R3">
	  <td id="<%=tabId%>_R3_CN1" >&nbsp;</td>
	  <td id="<%=tabId%>_R3_CN2">&nbsp;</td>
	  <td id="<%=tabId%>_R3_CN3">&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R4">
	  <td id="<%=tabId%>_R4_CN1" >&nbsp;</td>
	  <td id="<%=tabId%>_R4_CN2">&nbsp;</td>
	  <td id="<%=tabId%>_R4_CN3">&nbsp;</td>	
	  </tr>
	<tr id="<%=tabId%>_R5">
	  <td id="<%=tabId%>_R5_CN1" >&nbsp;</td>
	  <td id="<%=tabId%>_R5_CN2">&nbsp;</td>
	  <td id="<%=tabId%>_R5_CN3">&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R6"> 
	  <td rowspan="2" >资本公积</td>
	  <td colspan="2" style="text-align:center" >资本（股本）溢价</td>
	  <td  id="<%=tabId%>_R6_CN1" colspan="2">&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R7">
	  <td colspan="2"  style="text-align:center">其它</td>
	  <td  id="<%=tabId%>_R7_CN1" colspan="2">&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R8">
	  <td >盈余公积</td>
	  <td id="<%=tabId%>_R8_CN1" colspan="4" >&nbsp;</td>
	  </tr>
	<tr id="<%=tabId%>_R9">
     <td >未分配利润</td>
	  <td id="<%=tabId%>_R9_CN1" colspan="4" >&nbsp;</td>
	  </tr>
</table>

<br/>
<p id="C4_C2" style=" text-align:left;">二、企业收入及利润状况分析：</p>
<p  id="C4_C2_C1" style=" text-align:left;">重要科目的核实分析（调整后） <span style="float:right">单位：万元</span>
<% tabId="C4_C2_C1_ET5";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[ 
{id:'R1_CN1',type:0},
{id:'R2_CN1',type:0},
{id:'R2_CN2',type:0},
{id:'R2_CN3',type:0},
{id:'R3_CN2',type:0},
{id:'R3_CN3',type:0},
{id:'R3_CN4',type:0},
{id:'R3_CN5',type:0},
{id:'R4_CN2',type:0},
{id:'R4_CN3',type:0},
{id:'R4_CN4',type:0},
{id:'R4_CN5',type:0},
{id:'R5_CN2',type:0},
{id:'R5_CN3',type:0},
{id:'R5_CN4',type:0},
{id:'R5_CN5',type:0},
{id:'R6_CN2',type:0},
{id:'R6_CN3',type:0},
{id:'R6_CN4',type:0},
{id:'R6_CN5',type:0},
{id:'R7_CN2',type:0},
{id:'R7_CN3',type:0},
{id:'R7_CN4',type:0},
{id:'R7_CN5',type:0},
{id:'R8_CN2',type:0},
{id:'R8_CN3',type:0},
{id:'R8_CN4',type:0},
{id:'R8_CN5',type:0},
{id:'R9_CN2',type:0},
{id:'R9_CN3',type:0},
{id:'R9_CN4',type:0},
{id:'R9_CN5',type:0},
{id:'R10_CN2',type:0},
{id:'R10_CN3',type:0},
{id:'R10_CN4',type:0},
{id:'R10_CN5',type:0},
{id:'R11_CN2',type:0},
{id:'R11_CN3',type:0},
{id:'R11_CN4',type:0},
{id:'R11_CN5',type:0},
{id:'R13_CN1',type:2},
{id:'R14_CN1',type:2},
{id:'R15_CN1',type:2},
{id:'R16_CN1',type:2}
]}"
>
    <tr>
      <td colspan="5" style="text-align:center" ><p >企业主要经营指标</p></td>
      </tr>
    <tr id="<%=tabId%>_R1">
      <td width="40%" style="text-align:center"  rowspan="2" >项&nbsp;&nbsp;目</td>
      <td colspan="3"  style="text-align:center">前三年</td>
      <td width="15%" rowspan="2"  style="text-align:center"><p>本年</p>
        <p><span style="margin:0px;padding:0px;">
    <span id="<%=tabId%>_R1_CN1" style="width:60px;height:25px; margin:0px; padding-top:4px; "></span>月</span></p></td>
    </tr>
    <tr id="<%=tabId%>_R2">
      <td width="15%"   style="text-align:center"><span style="margin:0px;padding:0px;">
    <span id="<%=tabId%>_R2_CN1" style="width:60px;height:25px; margin:0px; padding-top:4px; "></span>年</span></td>
      <td width="15%"  style="text-align:center"><span style="margin:0px;padding:0px;">
    <span id="<%=tabId%>_R2_CN2" style="width:60px;height:25px; margin:0px; padding-top:4px; "></span>年</span></td>
      <td width="15%"  style="text-align:center"><span style="margin:0px;padding:0px;">
    <span id="<%=tabId%>_R2_CN3" style="width:60px;height:25px; margin:0px; padding-top:4px; "></span>年</span></td>
      </tr>
       <tr id="<%=tabId%>_R3">
         <td style="text-align:center">一、主营业务收入</td>
         <td id="<%=tabId%>_R3_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R3_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R3_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R3_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R4">
         <td style="text-align:center">二、主营业务成本</td>
         <td id="<%=tabId%>_R4_CN2"  style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R4_CN3"  style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R4_CN4"  style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R4_CN5"  style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R5">
         <td style="text-align:center">三、主营业务利润</td>
         <td id="<%=tabId%>_R5_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R5_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R5_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R5_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R6">
         <td style="text-align:center">四、营业费用</td>
         <td id="<%=tabId%>_R6_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R6_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R6_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R6_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R7">
         <td style="text-align:center">五、管理费用</td>
         <td id="<%=tabId%>_R7_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R7_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R7_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R7_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R8">
         <td style="text-align:center">六、财务费用</td>
         <td id="<%=tabId%>_R8_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R8_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R8_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R8_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R9">
         <td style="text-align:center">七、营业利润</td>
         <td id="<%=tabId%>_R9_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R9_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R9_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R9_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R10">
         <td style="text-align:center">八、利润总额</td>
         <td id="<%=tabId%>_R10_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R10_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R10_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R10_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R11">
         <td style="text-align:center">九、净利润</td>
         <td id="<%=tabId%>_R11_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R11_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R11_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R11_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr>
         <td colspan="5" style="text-align:left">说明：（现场调查核实过程、重要科目说明及趋势分析等）</td>
       </tr>
       <tr id="<%=tabId%>_R13">
         <td style="text-align:center">1、收入的确认：</td>
         <td id="<%=tabId%>_R13_CN1" colspan="4" style="text-align:center; height:60px;">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R14">
         <td style="text-align:center">2、主营业务成本的确认：</td>
         <td  id="<%=tabId%>_R14_CN1" colspan="4" style="text-align:center; height:60px;">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R15">
         <td style="text-align:center">3、营业、管理、财务费用的确认： </td>
         <td id="<%=tabId%>_R15_CN1" colspan="4" style="text-align:center; height:60px;">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R16">
      <td style="text-align:center">4、纳税情况的核实分析</td>
      <td id="<%=tabId%>_R16_CN1" colspan="4" style="text-align:center; height:60px;">&nbsp;</td>
      </tr>
</table>


<br/>
<p ID="C4_C3" style=" text-align:left;">三、现金流量分析</p>
<p ID="C4_C3_C1" style=" text-align:left;">企业近三年及当期现金流量表（调整后）<span style="float:right">单位：万元</span>
<% tabId="C4_C3_C1_ET6";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[
{id:'R3_CN1',type:0},
{id:'R3_CN2',type:0},
{id:'R3_CN3',type:0},
 
{id:'R4_CN2',type:0},
{id:'R4_CN3',type:0},
{id:'R4_CN4',type:0},
{id:'R4_CN5',type:0},

{id:'R5_CN2',type:0},
{id:'R5_CN3',type:0},
{id:'R5_CN4',type:0},
{id:'R5_CN5',type:0},

{id:'R6_CN2',type:0},
{id:'R6_CN3',type:0},
{id:'R6_CN4',type:0},
{id:'R6_CN5',type:0},

{id:'R7_CN2',type:0},
{id:'R7_CN3',type:0},
{id:'R7_CN4',type:0},
{id:'R7_CN5',type:0},

{id:'R8_CN2',type:0},
{id:'R8_CN3',type:0},
{id:'R8_CN4',type:0},
{id:'R8_CN5',type:0},

{id:'R9_CN2',type:0},
{id:'R9_CN3',type:0},
{id:'R9_CN4',type:0},
{id:'R9_CN5',type:0},

{id:'R10_CN2',type:0},
{id:'R10_CN3',type:0},
{id:'R10_CN4',type:0},
{id:'R10_CN5',type:0},

{id:'R11_CN2',type:0},
{id:'R11_CN3',type:0},
{id:'R11_CN4',type:0},
{id:'R11_CN5',type:0},

{id:'R12_CN2',type:0},
{id:'R12_CN3',type:0},
{id:'R12_CN4',type:0},
{id:'R12_CN5',type:0},

{id:'R13_CN2',type:0},
{id:'R13_CN3',type:0},
{id:'R13_CN4',type:0},
{id:'R13_CN5',type:0},

{id:'R14_CN2',type:0},
{id:'R14_CN3',type:0},
{id:'R14_CN4',type:0},
{id:'R14_CN5',type:0},

{id:'R15_CN2',type:0},
{id:'R15_CN3',type:0},
{id:'R15_CN4',type:0},
{id:'R15_CN5',type:0},

{id:'R16_CN1',type:2}
]}">
    <tr>
      <td colspan="5" style="text-align:center" ><p >企业主要经营指标</p></td>
      </tr>
    <tr>
      <td width="40%" style="text-align:center"  rowspan="2" >项&nbsp;&nbsp;目</td>
      <td colspan="3"  style="text-align:center">前三年</td>
      <td width="15%" rowspan="2"  style="text-align:center"><p>本期</p></td>
    </tr>
    <tr id="<%=tabId%>_R3">
      <td width="15%"   style="text-align:center"><span style="margin:0px;padding:0px;">
    <span id="<%=tabId%>_R3_CN1" style="width:60px;height:25px; margin:0px; padding-top:4px; "></span>年</span></td>
      <td width="15%"  style="text-align:center"><span style="margin:0px;padding:0px;">
    <span id="<%=tabId%>_R3_CN2" style="width:60px;height:25px; margin:0px; padding-top:4px; "></span>年</span> </td>
      <td width="15%"  style="text-align:center"><span style="margin:0px;padding:0px;">
    <span id="<%=tabId%>_R3_CN3" style="width:60px;height:25px; margin:0px; padding-top:4px; "></span>年</span></td>
      </tr>
       <tr id="<%=tabId%>_R4">
         <td style="text-align:center">现金流入总量</td>
         <td id="<%=tabId%>_R4_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R4_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R4_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R4_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R5">
         <td style="text-align:center">现金流出总量</td>
         <td id="<%=tabId%>_R5_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R5_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R5_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R5_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R6">
         <td style="text-align:center">现金净流量</td>
         <td id="<%=tabId%>_R6_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R6_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R6_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R6_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R7">
         <td style="text-align:center">经营活动现金流入量</td>
         <td id="<%=tabId%>_R7_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R7_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R7_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R7_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R8">
         <td style="text-align:center">经营活动现金流出量</td>
         <td id="<%=tabId%>_R8_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R8_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R8_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R8_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R9">
         <td style="text-align:center">经营活动现金净流量</td>
         <td id="<%=tabId%>_R9_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R9_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R9_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R9_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R10">
         <td style="text-align:center">投资活动现金流入量</td>
         <td id="<%=tabId%>_R10_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R10_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R10_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R10_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R11">
         <td style="text-align:center">投资活动现金流出量</td>
         <td id="<%=tabId%>_R11_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R11_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R11_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R11_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R12">
         <td style="text-align:center">投资活动现金净流量</td>
         <td id="<%=tabId%>_R12_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R12_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R12_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R12_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R13">
         <td style="text-align:center">筹资活动现金流入量</td>
         <td id="<%=tabId%>_R13_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R13_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R13_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R13_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R14">
         <td style="text-align:center">筹资活动现金流出量</td>
         <td id="<%=tabId%>_R14_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R14_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R14_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R14_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R15">
         <td style="text-align:center">筹资活动现金净流量</td>
         <td id="<%=tabId%>_R15_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R15_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R15_CN4" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R15_CN5" style="text-align:center">&nbsp;</td>
       </tr>
       <tr>
         <td colspan="5" style="text-align:left">说明：（企业未编制现金流量表的，须根据工作底稿采用直接法和间接法进行现金流量估算。）</td>
       </tr>
       <tr id="<%=tabId%>_R16">
         <td id="<%=tabId%>_R16_CN1" colspan="5" style="text-align:left;height:60px;" height="60">&nbsp;</td>
       </tr>
</table>

<br/>
<p  ID="C4_C4" style=" text-align:left;">四、 企业主要财务指标分析
<% tabId="C4_C4_ET7";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[ 
{id:'R3_CN1',type:1},
{id:'R3_CN2',type:1},
{id:'R3_CN3',type:1},
{id:'R3_CN4',type:1},

{id:'R4_CN1',type:0},
{id:'R4_CN2',type:0},
{id:'R4_CN3',type:0},
{id:'R4_CN4',type:0},

{id:'R5_CN1',type:0},
{id:'R5_CN2',type:0},
{id:'R5_CN3',type:0},
{id:'R5_CN4',type:0},

{id:'R6_CN1',type:0},
{id:'R6_CN2',type:0},
{id:'R6_CN3',type:0},
{id:'R6_CN4',type:0},

{id:'R7_CN1',type:0},
{id:'R7_CN2',type:0},
{id:'R7_CN3',type:0},
{id:'R7_CN4',type:0},

{id:'R8_CN1',type:0},
{id:'R8_CN2',type:0},
{id:'R8_CN3',type:0},
{id:'R8_CN4',type:0},

{id:'R9_CN1',type:0},
{id:'R9_CN2',type:0},
{id:'R9_CN3',type:0},
{id:'R9_CN4',type:0},

{id:'R10_CN1',type:0},
{id:'R10_CN2',type:0},
{id:'R10_CN3',type:0},
{id:'R10_CN4',type:0},

{id:'R11_CN1',type:0},
{id:'R11_CN2',type:0},
{id:'R11_CN3',type:0},
{id:'R11_CN4',type:0},

{id:'R12_CN1',type:0},
{id:'R12_CN2',type:0},
{id:'R12_CN3',type:0},
{id:'R12_CN4',type:0},

{id:'R13_CN1',type:0},
{id:'R13_CN2',type:0},
{id:'R13_CN3',type:0},
{id:'R13_CN4',type:0},

{id:'R14_CN1',type:0},
{id:'R14_CN2',type:0},
{id:'R14_CN3',type:0},
{id:'R14_CN4',type:0},

{id:'R15_CN1',type:0},
{id:'R15_CN2',type:0},
{id:'R15_CN3',type:0},
{id:'R15_CN4',type:0},

{id:'R16_CN1',type:0},
{id:'R16_CN2',type:0},
{id:'R16_CN3',type:0},
{id:'R16_CN4',type:0},

{id:'R17_CN1',type:0},
{id:'R17_CN2',type:0},
{id:'R17_CN3',type:0},
{id:'R17_CN4',type:0},

{id:'R18_CN1',type:0},
{id:'R18_CN2',type:0},
{id:'R18_CN3',type:0},
{id:'R18_CN4',type:0},

{id:'R19_CN1',type:2}
]}">

    <tr>
      <td colspan="6" style="text-align:center" ><p >企业主要经营指标</p></td>
      </tr>
    <tr>
      <td width="40%" colspan="2"  rowspan="2" style="text-align:center" >项&nbsp;&nbsp;目</td>
      <td colspan="3"  style="text-align:center">前三年</td>
      <td width="15%"  style="text-align:center">当期</td>
    </tr>
    <tr id="<%=tabId%>_R3">
      <td width="15%"   style="text-align:center"><span style="margin:0px;padding:0px;">
    <span id="<%=tabId%>_R3_CN1" style="width:125px;height:25px; margin:0px; padding-top:4px; "></span>年度</span></td>
      <td width="15%"  style="text-align:center"><span style="margin:0px;padding:0px;">
    <span id="<%=tabId%>_R3_CN2" style="width:125px;height:25px; margin:0px; padding-top:4px; "></span>年度</span></td>
      <td width="15%"  style="text-align:center"><span style="margin:0px;padding:0px;">
    <span id="<%=tabId%>_R3_CN3" style="width:125px;height:25px; margin:0px; padding-top:4px; "></span>年度</span></td>
      <td width="15%"  style="text-align:center"><span style="margin:0px;padding:0px;">
    <span id="<%=tabId%>_R3_CN4" style="width:125px;height:25px; margin:0px; padding-top:4px; "></span>月</span></td>
      </tr>
       <tr id="<%=tabId%>_R4">
         <td rowspan="4" style="text-align:center">偿债能力</td>
         <td style="text-align:center">资产负债率</td>
         <td id="<%=tabId%>_R4_CN1" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R4_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R4_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R4_CN4" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R5">
         <td style="text-align:center">流动比率</td>
         <td id="<%=tabId%>_R5_CN1" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R5_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R5_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R5_CN4" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R6">
         <td style="text-align:center">速动比率</td>
         <td id="<%=tabId%>_R6_CN1" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R6_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R6_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R6_CN4" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R7">
         <td style="text-align:center">利息保障倍数</td>
         <td id="<%=tabId%>_R7_CN1" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R7_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R7_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R7_CN4" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R8">
         <td rowspan="4" style="text-align:center">盈利能力</td>
         <td style="text-align:center">总资产利润率</td>
         <td id="<%=tabId%>_R8_CN1" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R8_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R8_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R8_CN4" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R9">
         <td style="text-align:center">销售利润率</td>
         <td id="<%=tabId%>_R9_CN1" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R9_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R9_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R9_CN4" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R10">
         <td style="text-align:center">净利润率</td>
         <td id="<%=tabId%>_R10_CN1" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R10_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R10_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R10_CN4" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R11">
         <td style="text-align:center">净资产收益率</td>
         <td id="<%=tabId%>_R11_CN1" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R11_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R11_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R11_CN4" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R12">
         <td rowspan="3" style="text-align:center">营运能力</td>
         <td style="text-align:center">总资产周转率</td>
         <td id="<%=tabId%>_R12_CN1" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R12_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R12_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R12_CN4" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R13">
         <td style="text-align:center">应收帐款周转率</td>
         <td id="<%=tabId%>_R13_CN1" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R13_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R13_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R13_CN4" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R14">
         <td style="text-align:center">存货周转率</td>
         <td id="<%=tabId%>_R14_CN1" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R14_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R14_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R14_CN4" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R15">
         <td rowspan="4" style="text-align:center">成长能力</td>
         <td style="text-align:center">销售增长率</td>
         <td id="<%=tabId%>_R15_CN1" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R15_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R15_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R15_CN4" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R16">
         <td style="text-align:center">总资产增长率</td>
         <td id="<%=tabId%>_R16_CN1" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R16_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R16_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R16_CN4" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R17">
         <td style="text-align:center">利润增长率</td>
         <td id="<%=tabId%>_R17_CN1" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R17_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R17_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R17_CN4" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R18">
         <td style="text-align:center">经营净现金流变化率</td>
         <td id="<%=tabId%>_R18_CN1" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R18_CN2" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R18_CN3" style="text-align:center">&nbsp;</td>
         <td id="<%=tabId%>_R18_CN4" style="text-align:center">&nbsp;</td>
       </tr>
       <tr id="<%=tabId%>_R19">
         <td colspan="6" style="text-align:left"><p>说明：</p>
         <span id="<%=tabId%>_R19_CN1" style="height:100px; width:98%"></span> </td>
       </tr>
    </table>
    
    <br/>
<span ID="C5" class="firstTitle"><br/>第五部分&nbsp;&nbsp;&nbsp;关联企业分析</span>
<br/>
<p ID="C5_C1" style=" text-align:left;">一、关联企业统计
<% tabId="C5_C1_ET1";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[ 
{id:'R2_CN1',type:1},
{id:'R2_CN2',type:1},
{id:'R2_CN3',type:1}
]}"S>
  <tr>
    <td width="30%">企业名称</td>
    <td width="30%">与借款企业关系</td>
    <td width="40%">其他情况</td>
  </tr>
  <tr id="<%=tabId%>_R2">
    <td id="<%=tabId%>_R2_CN1">&nbsp;</td>
    <td id="<%=tabId%>_R2_CN2">&nbsp;</td>
    <td id="<%=tabId%>_R2_CN3">&nbsp;</td>
    </tr>
</table>

<br/>
<p ID="C5_C2" style=" text-align:left;">二、关系密切的关联企业情况分析
<% tabId="C5_C2_ET2";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this,'C5_C2_ET3')">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this,'C5_C2_ET3')" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[ 
{id:'R1_CN1',type:1},
{id:'R2_CN1',type:1},
{id:'R2_CN2',type:1},
{id:'R3_CN1',type:1},
{id:'R3_CN2',type:1},
{id:'R4_CN1',type:0},
{id:'R4_CN1_1',type:0},
{id:'R4_CN2',type:0},
{id:'R4_CN2_1',type:0},
{id:'R4_CN3',type:1},
{id:'R5_CN1',type:1},
{id:'R5_CN2',type:1},
{id:'R5_CN3',type:1},
{id:'R6_CN1',type:1},
{id:'R6_CN2',type:1},
{id:'R6_CN3',type:1},
{id:'R7_CN1',type:1},
{id:'R7_CN2',type:1},
{id:'R7_CN3',type:1},
{id:'R8_CN1',type:1},
{id:'R8_CN2',type:1},
{id:'R8_CN3',type:1},
{id:'R9_CN1',type:1},
{id:'R9_CN2',type:1},
{id:'R10_CN1',type:1},
{id:'R10_CN2',type:1},
{id:'R11_CN1',type:1}
]}">
  <tr id="<%=tabId%>_R1">
    <td colspan="3">名称：<span id="<%=tabId%>_R1_CN1" style="width:60%; padding:0PX; margin:0PX;"></span> </td>
    </tr>
  <tr id="<%=tabId%>_R2">
    <td colspan="2">注册地址：<span id="<%=tabId%>_R2_CN1" style="width:60%"></span></td>
    <td>邮编：<span id="<%=tabId%>_R2_CN2" style="width:60%"></span></td>
  </tr>
  <tr id="<%=tabId%>_R3">
    <td colspan="2">联系电话：<span id="<%=tabId%>_R3_CN1" style="width:60%"></span></td>
    <td>传真：<span id="<%=tabId%>_R3_CN2" style="width:60%"></span></td>
  </tr>
  <tr id="<%=tabId%>_R4">
    <td width="33%">注册资本：
    <span id="<%=tabId%>_R4_CN1" style="width:40%"></span><span id="<%=tabId%>_R4_CN1_1" style="width:25%;"></span>
    </td>
    <td width="33%"> 实收资本：<span id="<%=tabId%>_R4_CN2" style="width:40%"></span><span id="<%=tabId%>_R4_CN2_1" style="width:25%;"></span></td>
    <td width="34%">贷款卡号：<span id="<%=tabId%>_R4_CN3" style="width:60%"></span></td>
  </tr>
  <tr id="<%=tabId%>_R5">
    <td >工商登记号<span id="<%=tabId%>_R5_CN1" style="width:60%"></span></td>
    <td>税务登记号：<span id="<%=tabId%>_R5_CN2" style="width:60%"></span></td>
    <td>贷款卡记录：<span id="<%=tabId%>_R5_CN3" style="width:60%"></span></td>
  </tr>
  <tr id="<%=tabId%>_R6">
    <td>法定代表人：<span id="<%=tabId%>_R6_CN1" style="width:60%"></span></td>
    <td>身份证：<span id="<%=tabId%>_R6_CN2" style="width:60%"></span></td>
    <td>联系电话：<span id="<%=tabId%>_R6_CN3" style="width:60%"></span></td>
  </tr>
  <tr id="<%=tabId%>_R7">
    <td>总经理：<span id="<%=tabId%>_R7_CN1" style="width:60%"></span></td>
    <td>身份证：<span id="<%=tabId%>_R7_CN2" style="width:60%"></span></td>
    <td>联系电话：<span id="<%=tabId%>_R7_CN3" style="width:60%"></span></td>
  </tr>
  <tr id="<%=tabId%>_R8">
    <td>财务经理：<span id="<%=tabId%>_R8_CN1" style="width:60%"></span></td>
    <td>身份证：<span id="<%=tabId%>_R8_CN2" style="width:60%"></span></td>
    <td>联系电话：<span id="<%=tabId%>_R8_CN3" style="width:60%"></span></td>
  </tr>
  <tr id="<%=tabId%>_R9">
    <td colspan="2"  style="height:30px;">基本帐户开户银行：<span id="<%=tabId%>_R9_CN1" style="width:60%"></span></td>
    <td>帐号：<span id="<%=tabId%>_R9_CN2" style="width:60%"></span></td>
  </tr>
  <tr id="<%=tabId%>_R10">
    <td colspan="2">贷款银行：<span id="<%=tabId%>_R10_CN1" style="width:60%"></span></td>
    <td>帐号：<span id="<%=tabId%>_R10_CN2" style="width:60%"></span></td>
  </tr>
  <tr id="<%=tabId%>_R11">
    <td colspan="3">经营范围： <span id="<%=tabId%>_R11_CN1" style="width:60%"></span></td>
    </tr>
</table>
<% tabId="C5_C2_ET3";%>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[ 
{id:'R2_CN1',type:1},
{id:'R2_CN2',type:1},
{id:'R2_CN3',type:0},
{id:'R2_CN4',type:1},
{id:'R2_CN5',type:1},
{id:'R3_CN1',type:1},
{id:'R3_CN2',type:1},
{id:'R3_CN3',type:0},
{id:'R3_CN4',type:1},
{id:'R3_CN5',type:1}
]}">
  <tr>
    <td width="10%" rowspan="3" class="td_head">股本结构</td>
    <td width="20%"  class="td_head">出资人名称</td>
    <td width="20%"  class="td_head">出资方式</td>
    <td width="20%"  class="td_head">出资额</td>
    <td width="15%"  class="td_head">占比例</td>
    <td width="15%"  class="td_head">出资时间</td>
  </tr>
  <tr id="<%=tabId%>_R2">
    <td id="<%=tabId%>_R2_CN1">&nbsp;</td>
    <td id="<%=tabId%>_R2_CN2">&nbsp;</td>
    <td id="<%=tabId%>_R2_CN3">&nbsp;</td>
    <td id="<%=tabId%>_R2_CN4">&nbsp;</td>
    <td id="<%=tabId%>_R2_CN5">&nbsp;</td>
  </tr>
  <tr id="<%=tabId%>_R3">
    <td id="<%=tabId%>_R3_CN1">&nbsp;</td>
    <td id="<%=tabId%>_R3_CN2">&nbsp;</td>
    <td id="<%=tabId%>_R3_CN3">&nbsp;</td>
    <td id="<%=tabId%>_R3_CN4">&nbsp;</td>
    <td id="<%=tabId%>_R3_CN5">&nbsp;</td>
  </tr>
</table>

<br/>
<p ID="C5_C3" style=" text-align:left;">三、财务状况简表
<% tabId="C5_C3_ET4";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[ 
{id:'R1_CN1',type:0},
{id:'R1_CN2',type:0},
{id:'R1_CN3',type:0},
{id:'R1_CN4',type:0},
{id:'R1_CN5',type:0},
{id:'R1_CN6',type:0},
{id:'R2_CN1',type:0},
{id:'R2_CN2',type:0},
{id:'R2_CN3',type:0},
{id:'R2_CN4',type:0},
{id:'R2_CN5',type:0},
{id:'R2_CN6',type:0},
{id:'R3_CN1',type:2}
]}"
>
  <tr>
    <td width="14%">&nbsp;</td>
    <td width="14%">资产总额</td>
    <td width="14%">流动资产</td>
    <td width="14%">流动负债</td>
    <td width="14%">所有者权益</td>
    <td width="14%">主营业务收入</td>
    <td width="16%">净利润</td>
  </tr>
  <tr id="<%=tabId%>_R1">
    <td>上年度</td>
    <td id="<%=tabId%>_R1_CN1">&nbsp;</td>
    <td id="<%=tabId%>_R1_CN2">&nbsp;</td>
    <td id="<%=tabId%>_R1_CN3">&nbsp;</td>
    <td id="<%=tabId%>_R1_CN4">&nbsp;</td>
    <td id="<%=tabId%>_R1_CN5">&nbsp;</td>
    <td id="<%=tabId%>_R1_CN6">&nbsp;</td>
  </tr>
  <tr id="<%=tabId%>_R2">
    <td>当期 </td>
    <td id="<%=tabId%>_R2_CN1">&nbsp;</td>
    <td id="<%=tabId%>_R2_CN2">&nbsp;</td>
    <td id="<%=tabId%>_R2_CN3">&nbsp;</td>
    <td id="<%=tabId%>_R2_CN4">&nbsp;</td>
    <td id="<%=tabId%>_R2_CN5">&nbsp;</td>
    <td id="<%=tabId%>_R2_CN6">&nbsp;</td>
  </tr>
  <tr id="<%=tabId%>_R3">
    <td colspan="7"><p>财务状况分析：</p>
      <span id="<%=tabId%>_R3_CN1" style="height:100px; width:98%"></span></td>
    </tr>
  </table>
  
  <br/>
<p ID="C5_C4" style=" text-align:left;">四、对关联企业的综合分析：
<% tabId="C5_C4_ET5";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[ 
{id:'R1_CN1',type:1},
{id:'R2_CN1',type:1},
{id:'R3_CN1',type:1},
{id:'R4_CN1',type:1},
{id:'R5_CN1',type:2}
]}">
  <tr id="<%=tabId%>_R1">
    <td width="40%">经营方面是否有上下游关系（关联交易）</td>
    <td id="<%=tabId%>_R1_CN1" width="60%">&nbsp;</td>
  </tr>
    <tr id="<%=tabId%>_R2">
    <td>资金内部往来是否密切</td>
    <td id="<%=tabId%>_R2_CN1">&nbsp;</td>
  </tr>
    <tr id="<%=tabId%>_R3">
    <td>关联企业间债权债务关系</td>
    <td id="<%=tabId%>_R3_CN1">&nbsp;</td>
  </tr>
    <tr id="<%=tabId%>_R4">
    <td>对外债务、或有负债及信用记录状况</td>
    <td id="<%=tabId%>_R4_CN1">&nbsp;</td>
  </tr>
    <tr id="<%=tabId%>_R5">
    <td colspan="2"><p>分析：（关联企业对借款主体的影响）</p>
       <span id="<%=tabId%>_R5_CN1" style="height:100px; width:98%"></span> </td>
    </tr>
  </table>
  
    <br/>
<span ID="C6" class="firstTitle"><br/>第六部分&nbsp;&nbsp;&nbsp;反担保措施分析</span>
<br/>
<p ID="C6_C1" style=" text-align:left;">一、反担保措施:
<% tabId="C6_C1_ET1";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[ 
{id:'R1_CN1',type:2},
{id:'R2_CN1',type:2},
{id:'R3_CN1',type:2}
]}">
 
  <tr id="<%=tabId%>_R1">
    <td>
     1.抵押
     <span id="<%=tabId%>_R1_CN1" style="height:100px; width:98%"></span></td>
  </tr>
  <tr id="<%=tabId%>_R2">
    <td>
      2、保证
      <span id="<%=tabId%>_R2_CN1" style="height:100px; width:98%"></span></td>
  </tr>
  <tr id="<%=tabId%>_R3">
    <td><p>3、其他</p>
      <span id="<%=tabId%>_R3_CN1" style="height:100px; width:98%"></span></td>
  </tr>
</table>

<br/>
<p ID="C6_C2" style=" text-align:left;">二、抵押物情况明细（含不能办理登记手续）：</p>
<p ID="C6_C2_C1" style=" text-align:left;">1、房产、土地抵押物状况表 
 <% tabId="C6_C2_C1_ET2";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[ 
{id:'R1_CN1',type:1},
{id:'R1_CN2',type:4,items:'办登记,不办登记'},
{id:'R2_CN1',type:1},
{id:'R2_CN2',type:1},
{id:'R3_CN1',type:1},
{id:'R3_CN2',type:1},
{id:'R4_CN1',type:1},
{id:'R4_CN2',type:1},
{id:'R5_CN1',type:1},
{id:'R5_CN2',type:1},
{id:'R6_CN1',type:1},
{id:'R6_CN2',type:1},
{id:'R7_CN1',type:1},
{id:'R7_CN2',type:1}
]}"
>
  <tr  id="<%=tabId%>_R1">
    <td width="15%">抵押物位置</td>
    <td id="<%=tabId%>_R1_CN1"colspan="2">&nbsp;</td>
    <td width="30%"><span style="margin:0px;padding:0px;"><span id="<%=tabId%>_R1_CN2" style="width:100px;height:25px; margin:0px; padding-top:4px; "></span>[办登记或不办登记] </span></td>
   </tr>
    <tr id="<%=tabId%>_R2">
    <td width="15%">抵押物名称</td>
    <td id="<%=tabId%>_R2_CN1" width="30%">&nbsp;</td>
    <td width="15%">所有权人</td>
    <td id="<%=tabId%>_R2_CN2" width="30%">&nbsp;</td>
   </tr>
     <tr id="<%=tabId%>_R3">
    <td>权证号码</td>
    <td id="<%=tabId%>_R3_CN1">&nbsp;</td>
    <td>规化用途</td>
    <td id="<%=tabId%>_R3_CN2">&nbsp;</td>
   </tr>
   <tr id="<%=tabId%>_R4">
    <td>建筑面积</td>
    <td id="<%=tabId%>_R4_CN1">&nbsp;</td>
    <td>建成年月</td>
    <td id="<%=tabId%>_R4_CN2">&nbsp;</td>
   </tr>
     <tr id="<%=tabId%>_R5">
    <td>购入价格</td>
    <td id="<%=tabId%>_R5_CN1">&nbsp;</td>
    <td>市场参考价</td>
    <td id="<%=tabId%>_R5_CN2">&nbsp;</td>
   </tr>
     <tr id="<%=tabId%>_R6">
    <td>楼层</td>
    <td id="<%=tabId%>_R6_CN1">&nbsp;</td>
    <td>通风采光</td>
    <td id="<%=tabId%>_R6_CN2">&nbsp;</td>
   </tr>
     <tr id="<%=tabId%>_R7">
    <td>结构</td>
    <td id="<%=tabId%>_R7_CN1">&nbsp;</td>
    <td>装饰档次</td>
    <td id="<%=tabId%>_R7_CN2">&nbsp;</td>
   </tr>
</table>

<p ID="C6_C2_C2" style=" text-align:left;">2、设备抵押物状况表 
<% tabId="C6_C2_C2_ET3";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
<br />
  <span style="float:right">单位：万元</span></p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[ 
{id:'R1_CN3',type:1},
{id:'R1_CN4',type:4,items:'办登记,不办登记'},
{id:'R2_CN2',type:1},
{id:'R3_CN2',type:1},
{id:'R4_CN2',type:1}
]}">
  <tr id="<%=tabId%>_R1">
    <td width="15%" rowspan="4">设备基本状况</td>
    <td width="15%">抵押人</td>
    <td id="<%=tabId%>_R1_CN3" width="30%">&nbsp;</td>
    <td width="40%"><span style="margin:0px;padding:0px;"><span id="<%=tabId%>_R1_CN4" style="width:100px;height:25px; margin:0px; padding-top:4px; "></span>[办登记或不办登记] </span></td>
  </tr>
   <tr id="<%=tabId%>_R2">
    <td>抵押位置</td>
    <td id="<%=tabId%>_R2_CN2" colspan="2">&nbsp;</td>
  </tr>
   <tr id="<%=tabId%>_R3">
    <td>目前使用状况</td>
    <td id="<%=tabId%>_R3_CN2" colspan="2">&nbsp;</td>
  </tr>
   <tr id="<%=tabId%>_R4">
    <td>变现能力分析</td>
    <td id="<%=tabId%>_R4_CN2" width="30%" colspan="2">&nbsp;</td>
  </tr>
</table>

<br/>
<p  ID="C6_C3" style=" text-align:left;">三、反担保措施总体评价
<% tabId="C6_C3_ET4";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[
{id:'R1_CN1',type:2}]}"
>
  <tr id="<%=tabId%>_R1">
  <td>评价：
 <p><span id="<%=tabId%>_R1_CN1" style="height:100px; width:98%"></span>  </p></td>
  </tr>
</table>

    <br/>
<span ID="C7" class="firstTitle"><br/>第七部分&nbsp;&nbsp;&nbsp;风险综合分析</span>
<br/>
<p  ID="C7_C1" style=" text-align:left;">&nbsp;
<% tabId="C7_C1_ET1";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[
{id:'R1_CN1',type:2},
{id:'R1_CN2',type:2},
{id:'R1_CN3',type:2},
{id:'R2_CN2',type:2},
{id:'R3_CN2',type:2},
{id:'R4_CN3',type:2},
{id:'R5_CN2',type:2},
{id:'R6_CN2',type:2},
{id:'R7_CN2',type:2}
]}">
  <tr id="<%=tabId%>_R1">
  <td width="15%" style="text-align:center" rowspan="3">　风险因素<br />
    分析</td>
  <td  style="text-align:center" width="15%">借款人风险分析</td>
  <td  width="70%">1、经营风险： 
    <p> <span id="<%=tabId%>_R1_CN1" style="height:60px; width:98%"></span></p>
      2、行业风险： 
    <p> <span id="<%=tabId%>_R1_CN2" style="height:60px; width:98%"></span></p>
      3、个人道德风险： 
      <p> <span id="<%=tabId%>_R1_CN3" style="height:60px; width:98%"></span></p>
     </td>
  </tr>
    <tr id="<%=tabId%>_R2">
  <td style="text-align:center">反担保<br />
    措施风<br />
    险分析</td>
  <td  id="<%=tabId%>_R2_CN2" style="height:60px;">&nbsp;</td>
  </tr>
    <tr id="<%=tabId%>_R3">
  <td style="text-align:center">其它因素<br />
    风险分析</td>
  <td id="<%=tabId%>_R3_CN2"  style="height:60px;">&nbsp;</td>
  </tr>
    <tr id="<%=tabId%>_R4">
  <td  style="text-align:center" rowspan="3">　风险因素<br />
    分析</td>
  <td style="text-align:center">设计避<br />
    险方案</td>
  <td  id="<%=tabId%>_R4_CN3" style="height:60px;">&nbsp;</td>
  </tr>
    <tr id="<%=tabId%>_R5">
  <td style="text-align:center">担保前<br />
    提条件</td>
  <td  id="<%=tabId%>_R5_CN2" style="height:60px;">&nbsp;</td>
  </tr>
    <tr id="<%=tabId%>_R6">
  <td style="text-align:center">保后监<br />
    管重点</td>
  <td  id="<%=tabId%>_R6_CN2" style="height:60px;">&nbsp;</td>
  </tr>
    <tr id="<%=tabId%>_R7">
  <td style="text-align:center">综合风险度分析</td>
  <td  id="<%=tabId%>_R7_CN2" style="height:60px;" colspan="2">&nbsp;</td>
  </tr>
</table>

  <br/>
<span ID="C8" class="firstTitle"><br/>第八部分  调查人员总结分析结论</span><br/>
<p  ID="C8_C1" style=" text-align:left;">&nbsp;
<% tabId="C8_C1_ET1";%>
<span id="TL_<%=tabId%>" <%=style%>>
<Button id="ED_TL_<%=tabId%>" onClick="AuditProjectMgr.edit(this)">编辑</Button>
<Button id="SV_TL_<%=tabId%>"  onClick="AuditProjectMgr.save(this)" disabled="disabled">保存</Button>
</span>
</p>
<table id="<%=tabId%>" width="100%" border="1" cfg="{type:0,edcmns:[
{id:'R1_CN1',type:2},
{id:'R2_CN1',type:2}
]}"
>
  <tr id="<%=tabId%>_R1">
  <td style="text-align:left"><p >主调查人意见：结论：&nbsp;建议担保额度（依据有效净资产、现金流、反担保）、建议担保期限及还款方式（依据现金回笼周期）、建议担保费率（依据风险度）。 </p>
   <span id="<%=tabId%>_R1_CN1" style="height:100px; width:98%"></span>    　</td>
  </tr>
  <tr id="<%=tabId%>_R2">
  <td style="text-align:left"><p >协助调查人意见：结论：&nbsp;建议担保额度（依据有效净资产、现金流、反担保）、建议担保期限及还款方式（依据现金回笼周期）、建议担保费率（依据风险度）。 </p>
 <span id="<%=tabId%>_R2_CN1" style="height:100px; width:98%"></span>      　</td>
    </tr>
</table>
</div>
</center>
</body>
</html>
<script type="text/javascript" src="<%=basePath%>/js/verification.js" ></script>
<script type="text/javascript" src="<%=basePath%>/js/auditProject.js" ></script>
<script type="text/javascript">
PrintManager.lodopexePath = "controls/lodop/";
     var LODOP; //声明为全局变量
	function prn1_preview() {	
		CreateOneFormPage();
		LODOP.PREVIEW();
	};
	
	function CreateOneFormPage(){
		var strArr = ["<style>",
					  " body{ text-align:center;}",
					  " #auditproject_content{text-align:center; width:850px;}",
					  " .firstTitle{text-align:center; font-weight:bold; font-size:24px; font-family:华文仿宋,Verdana, Geneva, sans-serif; margin-top:15px;}",
					  "  table {border-collapse:collapse; border:solid #000;border-width:1px 0 0 1px;} table caption {font-size:14px;font-weight:bolder;}",
					  " table th,table td {border:solid #000;border-width:0 1px 1px 0;padding:2px; text-align:left; padding-left:5px; height:24px;}",
					  " .td_head{ text-align:center; padding:0px;}",
					  "</style>"];
		
	
		var strFormHtml=strArr.join(" ")+"<body>"+document.body.innerHTML+"</body>";
	
		LODOP=PrintManager.getLodop(document.getElementById('LODOP'),document.getElementById('LODOP_EM'));  
		LODOP.ADD_PRINT_HTM("0%","0%","100%","100%",strFormHtml);
	};
</script>

<script type="text/javascript">
 AuditProjectMgr.baseParams = {customerId:"<%=customerId%>", custType : "<%=custType%>", projectId : "<%=projectId%>"};
 AuditProjectMgr.basePath="<%=basePath%>";
<%
//customerId custType projectId
String tabCfgs = "["+
				   /*-->第一部分   基本情况 <--*/
				"{tabId:'C1_C1_ET3'},{tabId:'C1_C2_ET4'},"+
				/*-->第二部分   企业评价及其经营模式和市场状况分析 <--*/
				"{tabId:'C2_C1_ET1'},{tabId:'C2_C2_C1_ET2'},{tabId:'C2_C2_C2_ET3'},{tabId:'C2_C2_C3_ET4'},{tabId:'C2_C2_C4_ET5'},"+
				"{tabId:'C2_C2_C5_ET6'},{tabId:'C2_C3_ET1'},{tabId:'C2_C4_ET1'},{tabId:'C2_C5_ET1'},"+
				/*-->第三部分   企业资信情况分析 <--*/
				"{tabId:'C3_C1_ET1'},{tabId:'C3_C2_ET2'},{tabId:'C3_C3_ET3'},"+
				/*-->四部分   企业财务状况分析<--*/
				"{tabId:'C4_C1_C1_ET1'},{tabId:'C4_C1_C2_C1_ET2'},{tabId:'C4_C1_C2_C2_ET3'},{tabId:'C4_C1_C3_ET4'},{tabId:'C4_C2_C1_ET5'},{tabId:'C4_C3_C1_ET6'},{tabId:'C4_C4_ET7'},"+
				/*-->第五部分   关联企业分 析 <--*/
				"{tabId:'C5_C1_ET1'},{tabId:'C5_C2_ET2'},{tabId:'C5_C3_ET4'},{tabId:'C5_C4_ET5'},"+
				/*-->第六部分   反担保措施分析  <--*/
				"{tabId:'C6_C1_ET1'},{tabId:'C6_C2_C1_ET2'},{tabId:'C6_C2_C2_ET3'},{tabId:'C6_C3_ET4'},"+
				/*-->第七部分   风险综合分析<--*/
				"{tabId:'C7_C1_ET1'},"+
				/*-->第八部分 调查人员总结分析结论<--*/
				"{tabId:'C8_C1_ET1'}"+
				"]";	
    params = new HashMap<String,Object>();
	params.put("datas","{customerId:\""+customerId+"\", custType : \""+custType+"\", projectId : \""+projectId+"\"}");
	params.put("tabCfgs",tabCfgs);
	List<String> list = apMgr.findAll(params);
	String parentContainerId = request.getParameter("parentContainerId");
	for(int i=0,count=list.size(); i<count; i++){	
		String jsonObj = list.get(i);
%>
		AuditProjectMgr.parseDatas(<%=jsonObj%>);	
<%	
	}
	if(StringHandler.isValidStr(printaction)){
%>
		prn1_preview();
<%
	}
%>
parent.Cmw.unmask("<%=parentContainerId%>");
AuditProjectMgr.setTabCfgs(<%=tabCfgs%>);
</script>
