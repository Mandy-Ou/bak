<%@page import="com.cmw.entity.sys.UserEntity"%>
<%@page import="com.cmw.service.impl.sys.UserServiceImpl"%>
<%@page import="com.cmw.core.util.DateUtil"%>
<%@page import="com.cmw.entity.sys.VarietyEntity"%>
<%@page import="com.cmw.service.impl.sys.VarietyServiceImpl"%>
<%@page import="com.cmw.entity.crm.EcustomerEntity"%>
<%@page import="com.cmw.entity.crm.CustomerInfoEntity"%>
<%@page import="com.cmw.service.impl.crm.EcustomerServiceImpl"%>
<%@page import="com.cmw.service.impl.crm.CustomerInfoServiceImpl"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="javax.persistence.criteria.CriteriaBuilder.In"%>
<%@page import="javax.persistence.criteria.CriteriaBuilder.In"%>
<%@page import="com.cmw.core.util.SHashMap"%>
<%@page import="com.cmw.entity.finance.ApplyEntity"%>
<%@page import="com.cmw.service.impl.finance.ApplyServiceImpl"%>
<%@page import="com.cmw.entity.finance.AppraiseEntity"%>
<%@page import="com.cmw.service.impl.finance.AppraiseServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.cmw.core.util.DataTable"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.*"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@page import="com.cmw.core.util.StringHandler"%>
<jsp:directive.page import="org.springframework.web.context.WebApplicationContext"/>
<%@page import="com.cmw.service.impl.finance.AuditProjectServiceImpl"%>
<%
 	String basePath = request.getContextPath();
	String parentContainerId = request.getParameter("parentContainerId");
	WebApplicationContext wc = (WebApplicationContext)this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	AppraiseServiceImpl appraiseService =  (AppraiseServiceImpl)wc.getBean("appraiseService");
	CustomerInfoServiceImpl customerInfoService =  (CustomerInfoServiceImpl)wc.getBean("customerInfoService");
	EcustomerServiceImpl ecustomerService =  (EcustomerServiceImpl)wc.getBean("ecustomerService");
	VarietyServiceImpl varietyService =  (VarietyServiceImpl)wc.getBean("varietyService");
	UserServiceImpl userService =  (UserServiceImpl)wc.getBean("userService");
	String custType = request.getParameter("custType");
	String customerId = request.getParameter("customerId");
	String applyId = request.getParameter("projectId");
	String sysid = request.getParameter("sysid");
	DataTable dt = null;
	String queryStr = null;
	String cmns = null;
	SHashMap params=new SHashMap();
	params.put("applyId", applyId);
	AppraiseEntity appraiseEntity=appraiseService.getEntity(params);
	
	Long breed;
	Double	appAmount=0.00;
	Integer yearLoan=0;
	Integer monthLoan=0;
	Integer dayLoan=0;
	Integer rateType=0;
	Double rate=0.00;
	String situation="";
	String auditDate=null;
	String address="";
	Long hostMan=0L;
	Long recordMan=0L;
	String opinion="";
	Integer resultTag=0;
	String regaddress="";
	String name="";//名称(企业或者个人)
	String legalMan="";//法定代表人
	Double regcapital=0.00;//注册资金
	String Vname="";//业务品种
	String yeas="";
	String mons="";
	String days="";
	String datass="";
	String monss="";
	String hostMans="";
	String recordMans="";
	if(appraiseEntity!=null){
		breed=	appraiseEntity.getBreed();
		appAmount=(appraiseEntity.getAppAmount()).doubleValue();
		yearLoan=appraiseEntity.getYearLoan();
		monthLoan=appraiseEntity.getMonthLoan();
		dayLoan=appraiseEntity.getDayLoan();
		situation=appraiseEntity.getSituation();
		auditDate=DateUtil.dateFormatToStr(appraiseEntity.getAuditDate());
		address=appraiseEntity.getAddress();
		hostMan=appraiseEntity.getHostMan();
		recordMan=appraiseEntity.getRecordMan();
		opinion=appraiseEntity.getOpinion();
		resultTag=appraiseEntity.getResultTag();
		yearLoan=appraiseEntity.getYearLoan();///贷款期限年
		monthLoan=appraiseEntity.getMonthLoan();//贷款期限月
		dayLoan=appraiseEntity.getDayLoan();//贷款期限日
		recordMan=appraiseEntity.getRecordMan();
/*  	  	if(StringHandler.isValidObj(breed)){
		VarietyEntity varietyEntity=varietyService.getEntity(breed);//业务品种的获取
		if(varietyEntity!=null){
		Vname=varietyEntity.getName();
		}
}  */ 
	yeas=Integer.toString(yearLoan);
	monss=Integer.toString(monthLoan);
	if(Integer.parseInt(monss)<10){
		mons=0+monss;
	}else{
		mons=monss;
	}
	days=Integer.toString(dayLoan);
	datass=yeas+"-"+mons+"-"+days;
 		if(varietyService!=null&&sysid!=null){
 			long id=Long.parseLong(sysid);
 			VarietyEntity varietyeEntity=	varietyService.getEntity(id);
 			Vname=	varietyeEntity.getName();
 			}
  		if(userService!=null&&hostMan!=null){
  			SHashMap paramsd=new SHashMap();
  			paramsd.put("userId", hostMan);
  			UserEntity userEntity=userService.getEntity(paramsd);
  			hostMans=userEntity.getEmpName();
 		} 
  		if(userService!=null&&recordMan!=null){
  			SHashMap paramsds=new SHashMap();
  			paramsds.put("userId", recordMan);
  			UserEntity userEntity=userService.getEntity(paramsds);
  			recordMans=userEntity.getEmpName();
  					
 		} 
 		
 		if(custType!=null&&customerId!=null){
		Integer custTypes=Integer.parseInt(custType);
		Long applyIds=Long.parseLong(applyId);
		Long customerIds=Long.parseLong(customerId);
	if(custTypes==0){//个人客户
	 	 CustomerInfoEntity CustomerInfoEntity=customerInfoService.getEntity(customerIds);
		if(CustomerInfoEntity!=null){
			name=CustomerInfoEntity.getName(); 
			}		
		}else if(custTypes==1){//企业客户
			EcustomerEntity ecustomerEntity= ecustomerService.getEntity(customerIds);
			if(ecustomerEntity!=null){
			name=ecustomerEntity.getName();
			legalMan=ecustomerEntity.getLegalMan(); 
			}
		}
	} 
	
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">object,embed{-webkit-animation-duration:.001s;-webkit-animation-name:playerInserted;-ms-animation-duration:.001s;-ms-animation-name:playerInserted;-o-animation-duration:.001s;-o-animation-name:playerInserted;animation-duration:.001s;animation-name:playerInserted;}@-webkit-keyframes playerInserted{from{opacity:0.99;}to{opacity:1;}}@-ms-keyframes playerInserted{from{opacity:0.99;}to{opacity:1;}}@-o-keyframes playerInserted{from{opacity:0.99;}to{opacity:1;}}@keyframes playerInserted{from{opacity:0.99;}to{opacity:1;}}</style>
<style type="text/css"> 
body{font-size:12px;}
.selectItemcont{padding:8px;}
#selectItem{background:#FFF;position:absolute;top:0px;left:center;border:1px solid #000;overflow:hidden;width:240px;z-index:1000;}
#selectItem2{background:#FFF;position:absolute;top:0px;left:center;border:1px solid #000;overflow:hidden;width:240px;z-index:1000;}
.selectItemtit{line-height:20px;height:20px;margin:1px;padding-left:12px;}
.bgc_ccc{background:#E88E22;}
.selectItemleft{float:left;margin:0px;padding:0px;font-size:12px;font-weight:bold;color:#fff;}
.selectItemright{float:right;cursor:pointer;color:#fff;}
.selectItemcls{clear:both;font-size:0px;height:0px;overflow:hidden;}
.selectItemhidden{display:none;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>审贷评审</title>
		<script language="javascript" src="<%=basePath%>/js/moneydecimal.js"></script>
	<script language="javascript" src="<%=basePath%>/controls/lodop/LodopFuncs.js"></script>
	<object id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
	<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="<%=basePath%>/controls/lodop/install_lodop.exe"></embed>
	</object>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/extlibs/ext-3.3.0/resources/css/ext-all.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/pages/app/workflow/variety/formUIs/survey/auditProject.css"></link>
<script type="text/javascript" src="<%=basePath%>/extlibs/ext-3.3.0/adapter/ext/ext-base.js" ></script>
<script type="text/javascript" src="<%=basePath%>/extlibs/ext-3.3.0/ext-all.js" ></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.7.2.min.js"></script>
</head>
<body id="tbody">
<center>
<table cellspacing="0" cellpadding="0">
  <col width="43" />
  <col width="55" />
  <col width="117" />
  <col width="72" />
  <col width="53" />
  <col width="63" />
  <col width="157" />
  <col width="146" />
  <tr>
    <td colspan="8" width="706"><b><center>重庆巴南区向南小额贷款有限公司贷款</center></b></td>
  </tr>
  <tr>
    <td colspan="8"><center>审贷会评审表</CENTER></td>
  </tr>
  <tr>
    <td colspan="2">客户名称</td>
    <td colspan="4"><%=name %></td>
    <td>注册资本</td>
    <td><%=regcapital %>元</td>
  </tr>
  <tr>
    <td colspan="2">注册地址</td>
    <td colspan="4"><%=regaddress %></td>
    <td>法定代表人</td>
    <td><%=legalMan %></td>
  </tr>
  <tr>
    <td colspan="2">经营范围</td>
    <td colspan="6">主要经营范围煤炭批发</td>
  </tr>
  <tr>
    <td colspan="2">申请业务种类</td>
    <td>
    <input type="text" id="breed" />
    <span id="breeds" ><%=Vname %></span>
    <select id="breedsd">
</select>
    </td>
    <td colspan="2">币种及金额：</td>
    <td colspan="2"><input type="text" id="appAmount" /><span id="appAmounts"><%=appAmount %></span></td>
    <td>期限：
    <input class="Wdate" style="display:inline-block;width:88px" type="text" id="date" name="ids" onClick="WdatePicker()">
      <span id="dates" style="display:inline-block"><%=datass%></span>
    </td>
  </tr>
  <tr>
    <td rowspan="12" width="43">企   业  基     本  情  况</td>
    <td colspan="7" rowspan="12" dir="ltr" width="663"> 
    <textarea rows="10px" cols="100px" id="situation"></textarea>
   	<span id="situations" style="width:700px"  ><%=situation %></span>
    </td>
  </tr>
  <tr> </tr>
  <tr> </tr>
  <tr> </tr>
  <tr> </tr>
  <tr> </tr>
  <tr> </tr>
  <tr> </tr>
  <tr> </tr>
  <tr> </tr>
  <tr> </tr>
  <tr> </tr>
  <tr>
    <td rowspan="5" width="43">评     审  情  况</td>
   <td colspan="4" width="297" style="nowrap="nowrap"><span style="word-break:keep-all;">
   评审会时间：
   <input style="width:100px;" class="Wdate" type="text" id="auditDate" name="data" onClick="WdatePicker()"></span>
    <span id="auditDates"><%=auditDate%></span> 
    </td>
    <td colspan="3">评审会地点：<input type="text" id="address" /><span  id="addresss"><%=address%></span></td>
  </tr>
  <tr>
  <td colspan="4"><label for="R3_CN3"> 主持人：</label>
  <input type="text" name="address2"  onclick="setUserVal(this)" id="address2" />
  <span id="hostMans"><%=hostMans %></span>
  <span name="hides"></span> </td>
    <td colspan="3">记录人:
    <input type="text" id="address1" onclick="setUserVal(this)" />
    <span id="recordMans"><%=recordMans %></span>
     <span name="hiderecordMan"></span> </td> </td>
  </tr>
  <tr>
    <td rowspan="2" width="55">审贷小组成员评审意见</td>
    <td colspan="6" rowspan="2"> 签字：                        日期：</td>
  </tr>
  <tr> </tr>
  <tr>
    <td colspan="7"> 结论：  
 	  <input type="radio" name="ids" id="resultTag" value="1" />同意
      <input type="radio" name="ids" id="resultTag" value="2"  />不同意</td> 
  </tr>
</table>
</center>
<!--主持人控件显示  -->
 <div  id="selectItem2" class="selectItemhidden" style="display: none;overflow-x:scroll;overflow-y:scroll;"> 
	<div  id="selectItemAd" class="selectItemtit bgc_ccc" style="width:auto;"> 
		<h2 id="selectItemClose" class="selectItemright"><a>关闭</a></h2>
		<h2 id="resert_hostMan" style="color:#fff;position: absolute;top:2px;right: 40px" onclick="resert_hostMan(this)"><a>重置</a></h2>
		<h2 id="serch" style="color:#fff;position:absolute;top:2px;left:110px" onclick="serch_name(this)"><a>查找</a></h2>
	</div>
	<div id="selectItemCount" class="selectItemcont"> 
		<div id="selectSub"> 
		</div> 
	</div> 
</div>  
<!--记录人控件显示  -->
 <div id="selectItem1" class="selectItemhidden" style="display: none;overflow-x:scroll;overflow-y:scroll;"> 
	<div id="selectItemAd" class="selectItemtit bgc_ccc" style="style="width:239px"> 
		<h2 id="resert_hostMans" style="color:#fff;position: absolute;top:2px;right: 40px;cursor:hand;" onclick="resert_hostMans(this)"><a>重置</a></h2>
		<h2 id="serchs" style="color:#fff;position:absolute;top:2px;left:110px" onclick="serch_name(this)"><a>查找</a></h2>
		<div id="selectItemClose" class="selectItemright">关闭</div>
	</div> 
	<div id="selectItemCount" class="selectItemcont" style="width:242px;"> 
		<div id="selectSubs"> 
		</div> 
	</div> 
</div>  
<div style="position: absolute; display: none; margin: 0px; padding: 0px; border: none; text-align: left; z-index: 999999; left: 33px; top: -11px;"><div id="__lb_tip_325120658" "="" style="position: absolute; top: 0px; left: 0px; display: none; background-color: rgb(252, 252, 252); font-family: 微软雅黑, 宋体; font-size: 12px; width: 500px; border: 1px solid rgb(184, 184, 184); border-top-left-radius: 4px; border-top-right-radius: 4px; border-bottom-right-radius: 4px; border-bottom-left-radius: 4px; box-shadow: rgba(0, 0, 0, 0.45098) 0px 5px 16px; font-weight: normal;">
<div id="__lb_tip_header_325120658" style="height:35px;background-color:#FFF5E6;color:#F2951F;padding-left:12px;line-height:35px;font-size:12px;cursor:move;">
<span></span>
<div id="__lb_tip_close_325120658" style="float:right;display:inline-block;margin:15px;width:8px;height:8px;background-image:url(&#39;chrome-extension://dehbijahfabddfbolechpnlgoblcgpie/images/tip.png&#39;);background-repeat:no-repeat;cursor:pointer;"></div>
</div>
<div id="__lb_tip_article_325120658" style="max-height:100px;overflow-x:hidden;overflow-y:auto;margin:8px 12px 8px 12px;line-height:22px;color:#000000;"></div>
<div style="border-top:1px solid #E4E4E4;height:33px;margin:0 12px;line-height:33px;position:relative;">
<label id="__lb_tip_setting_325120658" style="cursor:pointer;text-decoration:underline;color:rgba(0,0,0,.57);display:inline;font-weight:normal;">关闭划词翻译</label>
<div id="__lb_tip_poweredBy_325120658" style="font-family:&#39;Arial&#39;;font-size:10px;color:rgba(0,0,0,.45);font-style:italic;float:right;"></div>
</div>
</div>
<div id="__lb_tip_search_325120658" style="position: absolute; top: 0px; left: 0px; display: block; width: 65px; height: 24px;">
<input type="button" style="width:63px;height:22px;cursor:pointer;border-radius:3px;font-size:12px;font-family:&#39;微软雅黑&#39;,&#39;宋体&#39;;" value="翻译">
</div></div>
<script type="text/javascript" src="./jquery.bgiframe.js"></script>
<script>
jQuery.fn.selectCity = function(targetId) {
	var _seft = this;
	var targetId = $(targetId);

	this.click(function(){
		var A_top = $(this).offset().top + $(this).outerHeight(true);  //  1
		var A_left =  $(this).offset().left;
		targetId.bgiframe();
		targetId.show().css({"position":"absolute","top":A_top+"px" ,"left":A_left+"px"});
	});

	targetId.find("#selectItemClose").click(function(){
		targetId.hide();
	});


	$(document).click(function(event){
		if(event.target.id!=_seft.selector.substring(1)){
			targetId.hide();	
		}
	});

	targetId.click(function(e){
		e.stopPropagation(); //  2
	});

    return this;
};
$(function(){
	//j记录人
	$("#address1").selectCity("#selectItem1");
	//主持人
	$("#address2").selectCity("#selectItem2");
});
 </script>
 <!--下拉框(主持人) -->
<style type="text/css" id="LB_ADBLOCK_0">[src*="&sourcesuninfo=ad-"],[id^="youku_ad_"],#Class_1_ad>.footad,#body>#myads,[align="center"]>a>[src^="http://drmcmm.baidu.com/media/"][width="960"][height="80"],[id^="span_myads"],[id^="lovexin"],#ks>.DaKuang,.ks>.DaKuang,#topad>.tad,.box1>.ad,[class="center margintop border clear data"]>.margintop,[class="center margintop border clear data"]>[width="880"][height="90"],[class="center margintop border clear"]>.margintop,#Class_1_ad>[id^="china_ads_div"],#foot>#foot2>#Class_1_ad,#floatDiv>#rightFloat,#top>.topSponsor.mt10,.mainArea.px9>.bottomSponsor,.pause-ad,.pause-ad,img[src^="http://user.hongdou.gxnews.com.cn/upload/index/"],[src^=" http://soft.mumayi.net/js/"],.a_fr.a_cb,.adarea,.adarea_top,#lovexin1,#lovexin2,.m_ad,#topBanner,.ad1,.ad2,.ad3,.ad4,.ad5,.ad6,.ad7,.ad8,.ad9,.ad_240_h,.side-Rad,.ol_ad_xiala,iframe[src^="http://a.cntv.cn"],.h8r.font0,.topbod>.topbodr,.topbodr>table[width="670"][height="70"],.widget_ad_slot_wrapper,.gg01,.gg02,.gg03,.gg04,.gg05,.gg06,.gg07,.gg08,.gg09,#_SNYU_Ad_Wrap,[id^="snyu_slot_"],.random-box>.random-banner,.top-ads-list,.palm01-ad,.topAd950,[class^="ad-banner"][id^="ad_"],.gonglue_rightad,iframe[src^="http://www.37cs.com/html/click/"],#AdZoneRa,#AdZoneRb,#AdZoneRc,#AdZoneRd,#AdZoneRe,#AdZoneRf,#AdZoneRg,div[id="adAreaBbox"],.absolute.a_cover,#QQCOM_Width3,#auto_gen_6,.l_qq_com,#cj_ad,[href^="http://c.l.qq.com/adsclick?oid="],.right_ad_lefttext,.right_ad_righttext,.AdTop-Article-QQ,.business-Article-QQ,.qiye-Article-QQ,.AdBox-Article-QQ,td[width="960"][height="90"][align="center"],.adclass,.ad1,[id^="tonglan_"],.ads5,.adv,.ads220_60,.ad-h60,.ad-h65,.ggs,[class^="ads360"],.news_ad_box,#XianAd,.Ad3Top-Article-QQ,.AdTop2-Article-QQ,.adbutton-Aritcle-QQ,#AdRight-Article-QQ,[id^=ad-block],.sidBoxNoborder.mar-b8,#ent_ad,[class="ad"][id="ad_bottom"],[class="ad"][id="ad_top"],.plrad,.ad300,#top_ad1,#top_ad2,#top_ad3,#top_ad4,#top_ad5,#top_ad6,#top_ad7,#top_ad8,#top_ad9,#mid_ad1,#mid_ad2,#mid_ad3,#mid_ad4,#mid_ad5,#mid_ad6,#mid_ad7,#mid_ad8,#mid_ad9,#ads1,#ads2,#ads3,#ads4,#ads5,#ads6,#ads7,#ads8,#ads9,.dlAd,.changeAd,.unionCpv,#Advertisement,iframe[src*="/advertisement."],img[src*="/advertisement."],.ad_headerbanner,#ad_headerbanner,div[class^=ad_textlink],iframe[src*="guanggao"],img[src*="guanggao"],#ads-top,#ads-bot,.adBanner,#topAd,.top_ad,.topAds,.topAd,.ad_column,#ad1,#ad2,#ad3,#ad4,#ad5,#ad6,#ad7,#ad8,#ad9,.ad_footerbanner,#adleft,#adright,.advleft,.advright,.ad980X60,.banner-ad,.top-ad,#adright,#AdLayer1,#AdLayer2,#AdLayer3,div[href*="click.mediav.com"],div[class=top_ad],div[class^="headads"],.txtad,.guanggao,#guanggao,.adclass,div[id*="AdsLayer"],.ad950,.guangg,.header-ads-top,#adleft,#adright,#ad_show,.ad_pic,#fullScreenAd,div[class^="adbox"],#topADImg,div[class^="ad_box"],div[id^="adbox"],div[class^="ads_"],div[alt*="￥ﾹ﾿￥ﾑﾊ￤ﾽﾍ"],div[id^="ads_"],div[src*="/adfile/"],.delayadv,#vplayad,.jadv,div[src*="/ads/"],div[src*="/advpic/"],div[id*="_adbox"],div[id*="-adbox"],div[class^="showad"],div[id^="adshow"],#bottomads,.ad_column,div[id^="_AdSame"],iframe[src^="http://drmcmm.baidu.com"],div[src^="http://drmcmm.baidu.com"],frame[src^="http://drmcmm.baidu.com"],div[href^="http://www.baidu.com/cpro.php?"],iframe[href^="http://www.baidu.com/cpro.php?"],div[src^="http://cpro.baidu.com"],div[src^="http://eiv.baidu.com"],div[href^="http://www.baidu.com/baidu.php?url="],div[href^="http://www.baidu.com/adrc.php?url="],.ad_text,div[href^="http://click.cm.sandai.net"],div.adA.area,div[src*=".qq937.com"],iframe[src*=".qq937.com"],div[src*=".88210212.com"],iframe[src*=".88210212.com"],.adBox,.adRight,.adLeft,.banner-ads,.right_ad,.left_ad,.content_ad,.post-top-gg,div[class*="_ad_slot"],.col_ad,.block_ad,.adBlue,.mar_ad,div[id^="ArpAdPro"],.adItem,.ggarea,.adiframe,iframe[src*="/adiframe/"],#bottom_ad,.bottom_ad,.crumb_ad,.topadna,.topadbod,div[src*="qq494.cn"],iframe[src*="qq494.cn"],.topadbod,embed[src*="gamefiles.qq937.com"],embed[src*="17kuxun.com"],.crazy_ad_layer,#crazy_ad_layer,.bannerad,iframe[src*="/ads/"],img[src*="/ads/"],embed[src*="/ads/"],#crazy_ad_float,.crazy_ad_float,.main_ad,.topads,div[class^="txtadsblk"],.head-ad,div[src*="/728x90."],img[src*="/728x90."],embed[src*="/728x90."],iframe[src*="/gg/"],img[src*="/gg/"],iframe[src^="http://www.460.com.cn"],#bg_ad,.ad_pic,iframe[src*="gg.yxdown.com"],.ad_top,#baiduSpFrame,.flashad,#flashad,#ShowAD,[onclick^="ad_clicked"],[class^="ad_video_"],#ad_240,.wp.a_f,.a_mu,#hd_ad,#top_ads,#header_ad,#adbanner,#adbanner_1,#Left_bottomAd,#Right_bottomAd,#ad_alimama,#vipTip,.ad_pip,#show-gg,.ad-box,.advbox,.widget-ads.banner,.a760x100,.a200x375,.a760x100,.a200x100,.ad_left,.ad_right,[id="ad"][class="toJd"],.g_ad,div[class$="-ads"]{display:none !important;}</style><script>window["_GOOG_TRANS_EXT_VER"] = "1";</script><script>window["_GOOG_TRANS_EXT_VER"] = "1";</script><style>#__lb_tip_article_325120658::-webkit-scrollbar{width: 8px;background-color:#FCFCFC;}
#__lb_tip_article_325120658::-webkit-scrollbar-thumb{-webkit-border-radius:4px;border:0;background-color: #E5E5E5;}
#__lb_tip_close_325120658{background-position:-66px 0;}
#__lb_tip_close_325120658:hover{background-position:-66px -9px;}
#__lb_tip_close_325120658:active{background-position:-66px -18px;}
#__lb_tip_search_325120658 input[type='button'] {border:1px solid #B2B2B2;background:-webkit-linear-gradient(top,#FEFEFE,#E1E1E1);color:#000000;}
#__lb_tip_search_325120658 input[type='button']:hover {border:1px solid #C58201;background:-webkit-linear-gradient(top,#F5A101,#E17C05);color:#FFFFFF;}
#__lb_tip_search_325120658 input[type='button']:active {border:1px solid #B46404;background:-webkit-linear-gradient(top,#E27D05,#F09702);color:#FFFFFF;}
#__lb_tip_poweredBy_325120658 a{color:rgba(0,0,0,.45);text-decoration:none;}
#__lb_tip_poweredBy_325120658 a:hover{text-decoration:underline;}
#__lb_tip_325120658 {border:10px solid red;}
</style>
</body>
</html>
<script type="text/javascript" src="<%=basePath%>/js/verification.js" ></script>
<script type="text/javascript" src="<%=basePath%>/js/auditProject.js" ></script>
<script type="text/javascript" src="<%=basePath%>/controls/My97DatePicker/WdatePicker.js" ></script><!-- 日期my97date日期控件author:李听 -->
<script type="text/javascript">
parent.Cmw.unmask("<%=parentContainerId%>");
</script>
<script type="text/javascript">
/*控制一开始进入页面进行加载需要显示隐藏的元素:block是显示 none是隐藏  */
window.onload=function(){ 
	/*只控制textarea的特殊元素  */
	document.getElementById("breedsd").style.display="none";//隐藏申请业务种类
	document.getElementById("situation").style.display="none";
	var span= document.getElementsByTagName("span");
	var input= document.getElementsByTagName("input");
for(var i=0;i<span.length;i++){
	span[i].style.display="block";//显示
}
for(var i=0;i<input.length;i++){
	input[i].style.display="none";//隐藏 
	}
};
</script>
<script type="text/javascript">
 $(document).ready(function(){  
	 var hides=document.getElementsByName("hides")[0];
 	 hides.style.display="none";//隐藏id传给后台调用的ID
    $.ajax({  
            url : encodeURI("./fcAppraise_listVariety.action"),  
            type : 'POST',  
            dataType : "json",  
            success : function(data) {  
            /* 	console.log(data);//调试的时候在控制台打印的信息
            	alert(data.list[0].id); */
            		var subs=data.list;//获取data中list的数组
            		var bb =$(document).find("#breedsd");  
            		/*                 pub_platform.empty();  
 */                for ( var i = 0; i < subs.length; i++) {  
                    bb.append("<option  value='"+subs[i].id+"'>"+ subs[i].name+ "</option>");  
                } 
            }  
        });  
}); 
</script>
<script type="text/javascript">
/*  主持人记录人的选择*/
          function  setUserVal(obj) {
                $.ajax({  
                        url : encodeURI("./fcAppraise_listVarUser.action"),  
                        type : 'POST',  
                        dataType : "json",  
                        success : function(data) {  
                        		var subs=data.list;//获取data中list的数组
                        		if(obj.id=="address2"){//主持人部分的创建
                        			var selectbb =$(document).find("#selectSub");
                        			selectbb.empty();
    	                   		  	for ( var i = 0; i < subs.length; i++) {
    	                   		  	selectbb.append("<input type='text'  style='border:2px;width:50px;margin:0 1px 0px 8px;'   id='"+subs[i].userId+"'   value='"+subs[i].userName+"'/>");
    	                   			selectbb.append("<input type='radio' id='"+subs[i].userId+"' style='margin:0px 28px 0px 2px;'  onclick='setVal(this)' name='cr'  value='"+subs[i].userName+"'>");
    	                   		  	} 
                        		}
                        		if(obj.id=="address1"){//记录人部分的创建
                        			var selectbb =$(document).find("#selectSubs");
                        			selectbb.empty();
    	                   		  	for ( var i = 0; i < subs.length; i++) {  
    	                   		  	selectbb.append("<input type='text'  style='border:0px;width:50px;margin:0 1px 0px 15px;'   id='"+subs[i].userId+"'   value='"+subs[i].userName+"'/>");
    	                   			selectbb.append("<input type='radio' id='"+subs[i].userId+"' style='margin:0px 30px 0px 2px;' onclick='setVals(this)' name='cr'  value='"+subs[i].userName+"'>");
	                  		} 
                        }
	                   }  
                });
        }

</script>
<script type="text/javascript">
	function setVal(obj){
		var address= document.getElementById("address2");
		var hostMans= document.getElementById("hostMans");
		var hides= document.getElementsByName("hides")[0];
		 hides.style.display="none";//隐藏id传给后台调用的ID
  		address.value=obj.value;
  		hides.innerHTML=obj.id;
  		hostMans.innerHTML=obj.value;
	}
</script>
<script type="text/javascript">
	function setVals(obj){
		var address= document.getElementById("address1");
		var recordMans= document.getElementById("recordMans");
		var hiderecordMan= document.getElementsByName("hiderecordMan")[0];
		hiderecordMan.style.display="none";//隐藏id传给后台调用的ID
  		address.value=obj.value;
  		hiderecordMan.innerHTML=obj.id;
  		recordMans.innerHTML=obj.value;
	}
</script>
<script type="text/javascript">
//重置按钮实现
function resert_hostMan(obj){
		var address2= document.getElementById("address2");
		address2.value="";	
		var  name_hides=document.getElementsByName("hides")[0];
		name_hides.innerHTML="";	
}
function resert_hostMans(obj){
		var address2= document.getElementById("address1");
		address2.value="";	
		var  name_hides=document.getElementsByName("hiderecordMan")[0];
		name_hides.innerHTML="";	
}
//查询实现
function serch_name(obj){
	
	if(obj.id=="serch"){
		var serchId=document.getElementById("address2").value;
	}
	if(obj.id=="serchs"){
		var serchId=document.getElementById("address1").value;		
	}
$.ajax({  
    url : encodeURI("./fcAppraise_search.action?serchId="+serchId),  
    type : 'POST',  
    dataType : "json",  
    success : function(data) {
    		var subs=data.list;//获取data中list的数组
    		console.log(data);
    		if(obj.id=="serch"){//主持人部分的创建
    			var selectbb =$(document).find("#selectSub");
    			selectbb.empty();
       		  	for ( var i = 0; i < subs.length; i++) {
       		  	selectbb.append("<input type='text'  style='border:2px;width:50px;margin:0 1px 0px 8px;'   id='"+subs[i].userId+"'   value='"+subs[i].userName+"'/>");
       			selectbb.append("<input type='radio' id='"+subs[i].userId+"' style='margin:0px 28px 0px 2px;'  onclick='setVal(this)' name='cr'  value='"+subs[i].userName+"'>");
       		  	} 
    		}
    		
    		if(obj.id=="serchs"){//记录人部分的创建
    			var selectbb =$(document).find("#selectSubs");
    			selectbb.empty();
       		  	for ( var i = 0; i < subs.length; i++) {  
       		  	selectbb.append("<input type='text'  style='border:0px;width:50px;margin:0 1px 0px 15px;'   id='"+subs[i].userId+"'   value='"+subs[i].userName+"'/>");
       			selectbb.append("<input type='radio' id='"+subs[i].userId+"' style='margin:0px 30px 0px 2px;' onclick='setVals(this)' name='cr'  value='"+subs[i].userName+"'>");
  		} 
    		}
		  }  
});
}
</script>

