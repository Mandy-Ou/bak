package com.cmw.action.finance;


import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysCodeConstant;
import com.cmw.constant.SysConstant;
import com.cmw.constant.SysparamsConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.kit.ikexpression.FormulaUtil;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.core.util.XmlHandler;
import com.cmw.entity.sys.RestypeEntity;
import com.cmw.entity.sys.SysparamsEntity;
import com.cmw.entity.sys.VarietyEntity;
import com.cmw.service.inter.finance.BalanceReportService;
import com.cmw.service.inter.sys.GvlistService;
import com.cmw.service.inter.sys.RestypeService;
import com.cmw.service.inter.sys.SysparamsService;
import com.cmw.service.inter.sys.VarietyService;
import com.sun.org.apache.xml.internal.security.signature.InvalidSignatureValueException;


/**
 * 贷款余额汇总表  ACTION类
 * @author 程明卫
 * @date 2013-08-17T00:00:00
 */
@Description(remark="贷款余额汇总表ACTION",createDate="2013-08-17T00:00:00",author="程明卫",defaultVals="fcBalanceReport_")
@SuppressWarnings("serial")
public class BalanceReportAction extends BaseAction {
	@Resource(name="balanceReportService")
	private BalanceReportService balanceReportService;
	@Resource(name="gvlistService")
	private GvlistService gvlistService;
	@Resource(name="restypeService")
	private RestypeService restypeService;
	@Resource(name="sysparamsService")
	private SysparamsService sysparamsService;
	@Resource(name="varietyService")
	private VarietyService varietyService;
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 贷款余额汇总表 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("statMonth,state#I");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			String statMonth = map.getvalAsStr("statMonth");
			setYmParams(statMonth, map);
			map.put("actionType", 0);//指取本年数据（从年初到本月）
			DataTable dtYearLoan = balanceReportService.getResultList(map);
			String datas = getHtmlDatas(dtYearLoan);
			result = ResultMsg.getSuccessMsg(datas);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	private void setYmParams(String statMonth,SHashMap<String, Object> map){
		int[] ymd = DateUtil.getYMD(new Date());
		int yearLoan = ymd[0];
		int monthLoan = ymd[1];
		if(StringHandler.isValidStr(statMonth)){
			String[] ymStr = statMonth.split("-");
			yearLoan = Integer.parseInt(ymStr[0]);
			monthLoan = Integer.parseInt(ymStr[1]);
		}
		map.put("yearLoan", yearLoan);
		map.put("monthLoan", monthLoan);
	}
	
	private String getHtmlDatas(DataTable dtYearLoan) throws ServiceException{
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("recode", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + SysparamsConstant.LOAN_ONE_COUNT);
		/*--> step one : 获取是否一笔放款单是否作为一笔投放笔数参数值  <--*/
		SysparamsEntity sysparams = sysparamsService.getEntity(params);
		//-->是否将一张放款单作为一笔投放笔数 [true : 是,false ：(否,即多张放款单只对应一笔投放笔数)]
		boolean isLoanInvoceAsOneCount =(null != sysparams && (StringHandler.isValidStr(sysparams.getVal()) && sysparams.getVal().equals("1")));
		
		/*--> step two : 获取资源列表  <--*/
		String recodes = SysCodeConstant.RESTYPE_RECODE_200000+","+SysCodeConstant.RESTYPE_RECODE_200001+","+SysCodeConstant.RESTYPE_RECODE_200002;
		params.put("recode", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+recodes);
		List<RestypeEntity> restypeList = restypeService.getEntityList(params);
		if(null == restypeList || restypeList.size() == 0) throw new ServiceException("没有配置基础数据，无法获取业务结构报表!");
		
		/*--> step three : 获取基础数据列表  <--*/
		params.clear();
		String restypeIds = getRestypeIds(restypeList);
		params.put("restypeIds", restypeIds);
		DataTable dtGvlist = gvlistService.getReportDt(params);
		
		/*--> step four : 转换数据  <--*/
		Map<String,List<JSONObject>> groupMap = new LinkedHashMap<String, List<JSONObject>>();
		List<JSONObject> tempList = null;
		RestypeEntity restypeEntity = null;
		String firstTitle = null;
		 Map<String,String> gvlistMap = null;
		
		// 一. 借款主体
		restypeEntity = restypeMap.get(SysCodeConstant.RESTYPE_RECODE_200000);
		if(null != restypeEntity){
			firstTitle = "一、"+restypeEntity.getName();
			gvlistMap = getGvlistByRestype(restypeEntity, dtGvlist);
			tempList = convertData(isLoanInvoceAsOneCount, gvlistMap, dtYearLoan,"loanMain");
			if(null != tempList && tempList.size() > 0) groupMap.put(firstTitle, tempList);
		}
		 
		// 二. 行业分类
		restypeEntity = restypeMap.get(SysCodeConstant.RESTYPE_RECODE_200001);
		if(null != restypeEntity){
			firstTitle = "二、"+restypeEntity.getName();
			gvlistMap = getGvlistByRestype(restypeEntity, dtGvlist);
			tempList = convertData(isLoanInvoceAsOneCount, gvlistMap, dtYearLoan,"inType");
			if(null != tempList && tempList.size() > 0) groupMap.put(firstTitle, tempList);
		}
		
		JSONObject cfgData = getCfgByXml();
		// 三、单笔金额
		JSONObject signleAmountInfo = cfgData.getJSONObject("signleAmountInfo");
		if(null != signleAmountInfo){
			firstTitle = " 三、"+ signleAmountInfo.getString("itemTitle");
			tempList = convertData(isLoanInvoceAsOneCount, signleAmountInfo, dtYearLoan);
			if(null != tempList && tempList.size() > 0) groupMap.put(firstTitle, tempList);
		}
		
		// 四、期限类型
		JSONObject loanLimitInfo = cfgData.getJSONObject("loanLimitInfo");
		if(null != loanLimitInfo){
			firstTitle = " 四、"+ loanLimitInfo.getString("itemTitle");
			tempList = convertData(isLoanInvoceAsOneCount, loanLimitInfo, dtYearLoan);
			if(null != tempList && tempList.size() > 0) groupMap.put(firstTitle, tempList);
		}
			
		// 五. 贷款方式
		restypeEntity = restypeMap.get(SysCodeConstant.RESTYPE_RECODE_200002);
		if(null != restypeEntity){
			firstTitle = "五、"+restypeEntity.getName();
			gvlistMap = getGvlistByRestype(restypeEntity, dtGvlist);
			tempList = convertData(isLoanInvoceAsOneCount, gvlistMap, dtYearLoan,"loanType");
			if(null != tempList && tempList.size() > 0) groupMap.put(firstTitle, tempList);
		}
		
		
		// 六、业务品种
		 Map<String,String> breedMap = getBreedMap();
		if(null != breedMap && breedMap.size() >  0){
			firstTitle = "六、业务品种";
			tempList = convertData(isLoanInvoceAsOneCount, breedMap, dtYearLoan,"breed");
			if(null != tempList && tempList.size() > 0) groupMap.put(firstTitle, tempList);
		}
		
		//项目,本年投放笔数,本年投放金额,本年投放比例,本月投放笔数,本月投放金额,本月投放比例
		//item,ylcount,ypayAmount,ypercent,mlcount,mpayAmount,mpercent

		/*--> step five : 生成HTML  <--*/
		String htmlData = makeHtml(groupMap);
		return htmlData;
	}
	
	private String makeHtml(Map<String,List<JSONObject>> groupMap){
		if(null == groupMap || groupMap.size() == 0) return "";
		StringBuilder sbHtml = new StringBuilder();
	
		JSONObject sumData = getSumData(groupMap);
		double t_yzprincipals = sumData.getDoubleValue("yzprincipals");
//		String htmlTr = getHtmlTr(sumData,"");
		String htmlTr = null;
//		sbHtml.append(htmlTr);
		
		Set<String> groupKeys = groupMap.keySet();
		for(String groupKey : groupKeys){
			List<JSONObject> dataList = groupMap.get(groupKey);
			int rowspan = dataList.size();
			String groupTd = "<td  rowspan=\""+rowspan+"\" class=\"cmw_td_group\">"+groupKey+"</td>";
			for(JSONObject data : dataList){
				double yzprincipals = data.getDoubleValue("yzprincipals");
				double ypercent = (yzprincipals > 0) ? StringHandler.Round((yzprincipals/t_yzprincipals)*100,4) : 0d;
				data.put("ypercent", ypercent);
				htmlTr = getHtmlTr(data,groupTd);
				sbHtml.append(htmlTr);
				groupTd = "";
			}
		}
		return sbHtml.toString();
	}
	
	private JSONObject getSumData(Map<String,List<JSONObject>> groupMap){
		Set<String> keys = groupMap.keySet();
		int t_ylcount = 0;
		double t_yzprincipals = 0d;
		double t_ypercent = 0d;
		for(String key : keys){
			List<JSONObject> dataList = groupMap.get(key);
			for(JSONObject obj : dataList){
				int ylcount = obj.getIntValue("ylcount");
				t_ylcount += ylcount;
				double yzprincipals = obj.getDoubleValue("yzprincipals");
				t_yzprincipals += yzprincipals;
			}
		}
		/*--- 处理最后合计行  ---*/
		JSONObject sumData = new JSONObject();
		sumData.put("item", "合计");
		sumData.put("ylcount", t_ylcount);
		sumData.put("yzprincipals", StringHandler.Round(t_yzprincipals, 2));
		if(t_yzprincipals>0) t_ypercent = 100;
		sumData.put("ypercent", t_ypercent);
		return sumData;
	}
	
	/**
	 * 获取 LoanReportItem.xml 中的统计项
	 * @return
	 */
	private JSONObject getCfgByXml(){
		String xmlFilePath = FileUtil.getFilePath(getRequest(), "WEB-INF/classes/reportcfg/LoanSortItems.xml");
		XmlHandler xmlHandler = new XmlHandler();
		String content = xmlHandler.getRootContent(xmlFilePath);
		JSONObject jsonObj = FastJsonUtil.convertStrToJSONObj(content);
		return jsonObj;
	}
	private String getHtmlTr(JSONObject jsonObj, String groupTd){
		StringBuilder sb = new StringBuilder();
		sb.append("<tr>");
		if(StringHandler.isValidStr(groupTd)) sb.append(groupTd);
		Object cellData = null;
		cellData = jsonObj.get("item");
		String style = "";
		String itemStyle = "text-align:left;padding-left:5px;";
		String itemTd = "";
		if(StringHandler.isValidObj(cellData) && cellData.toString().equals("合计")){
			itemStyle = "color:red;text-align:center;background-color:#ff9900;";
			itemTd = getHtmlFirstTd(cellData,itemStyle," colspan=\"2\" ");
			style += " background-color:#ff9900;";
		}else{
			itemTd = getHtmlTd(cellData,"text-align:left;padding-left:10px;background-color:#CC99FF;");
			style = null;
		}
		sb.append(itemTd);
		
		cellData = jsonObj.get("ylcount")+"笔";
		sb.append(getHtmlTd(cellData,style));
		
		cellData = StringHandler.fmtMicrometer(jsonObj.get("yzprincipals"))+"元";
		sb.append(getHtmlTd(cellData,style));
		
		cellData = jsonObj.get("ypercent")+"%";
		sb.append(getHtmlTd(cellData,style));
		
		sb.append("</tr>");
		return sb.toString();
	}
	
	/**
	 * 获取一级加粗的单元格
	 * @param cellData
	 * @return
	 */
	private String getHtmlFirstTd(Object cellData,String style,String attr){
		if(StringHandler.isValidStr(style)){
			style="style='font-weight:bold;"+style+"'";
		}else{
			style="style='font-weight:bold;'";
		}
		return "<td "+style+" "+attr+">"+cellData+"</td>";
	}
	
	private String getHtmlTd(Object cellData,String style){
		if(StringHandler.isValidStr(style)){
			style="style='"+style+"'";
		}else{
			style="";
		}
		return "<td "+style+">"+cellData+"</td>";
	}
	
	/**
	 * 获取所有可用业务品种列表
	 * @return
	 * @throws ServiceException
	 */
	private Map<String,String> getBreedMap() throws ServiceException{
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("isenabled", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + SysConstant.OPTION_ENABLED);
		List<VarietyEntity> list = varietyService.getEntityList(map);
		if(null == list || list.size() == 0) return null;
		Map<String,String> breedMap = new HashMap<String, String>();
		for(VarietyEntity entity : list){
			Long id = entity.getId();
			String name = entity.getName();
			breedMap.put(id.toString(), name);
		}
		return breedMap;
	}
	
	Map<String,RestypeEntity> restypeMap = new HashMap<String, RestypeEntity>();
	private String getRestypeIds(List<RestypeEntity> restypeList){
		restypeMap.clear();
		StringBuilder sb = new StringBuilder();
		for(RestypeEntity restypeObj : restypeList){
			sb.append(restypeObj.getId()).append(",");
			String recode = restypeObj.getRecode();
			restypeMap.put(recode, restypeObj);
		}
		return StringHandler.RemoveStr(sb);
	}
	
	private Map<String,String> getGvlistByRestype(RestypeEntity restypeObj,DataTable dtGvlist){
		if(null == dtGvlist || dtGvlist.getRowCount() == 0) return null;
		Long restypeId = restypeObj.getId();
		Map<String,String> gvlistMap = new LinkedHashMap<String, String>();
		for(int i=0,count=dtGvlist.getRowCount(); i<count; i++){
			Long _restypeId = dtGvlist.getLong(i,"restypeId");
			if(!restypeId.equals(_restypeId)) continue;
			String gvlistId = dtGvlist.getString(i, "id");
			String gvlistName = dtGvlist.getString(i, "name");
			gvlistMap.put(gvlistId, gvlistName);
		}
		return gvlistMap;
	}
	
	private List<JSONObject> convertData(boolean isLoanInvoceAsOneCount,
			Map<String,String> gvlistData,DataTable dtYearLoan,String cmn){
		if(null == gvlistData || gvlistData.size() == 0) return null;
		LinkedList<JSONObject> list = new LinkedList<JSONObject>();
		int rowNum = 0;
		
		Set<String> keys = gvlistData.keySet();
		for(String gvlisId : keys){
			int t_ylcount = 0;
			double t_zprincipals = 0d;
			
			/*------ step one : 年度数据的设置  ------*/
			for(int i=0,count=dtYearLoan.getRowCount(); i<count; i++){
				String eqId = dtYearLoan.getString(i, cmn);
				if(StringHandler.isValidStr(eqId)){
					if(eqId.indexOf(gvlisId)== -1) continue;
				}else{
					continue;
				}
				Integer loanCount = 0;
				if(isLoanInvoceAsOneCount){
					loanCount = dtYearLoan.getInteger(i, "loanCount");
					if(null == loanCount) loanCount = 0;
				}else{
					loanCount = 1;
				}
				t_ylcount += loanCount;
				t_zprincipals +=  dtYearLoan.getDouble(i, "zprincipals");
			}
			
			t_zprincipals = StringHandler.Round(t_zprincipals, 4);
			
			
			rowNum++;
			JSONObject data = new JSONObject();
			String item = rowNum +". "+ gvlistData.get(gvlisId);
			data.put("item", item);
			data.put("ylcount", t_ylcount);
			data.put("yzprincipals", t_zprincipals);
			list.add(data);
		}
		return list;
	}
	
	
	/**
	 * 获取 XML 中配置的统计项的值
	 * @param isLoanInvoceAsOneCount 
	 * @param itemsCfg
	 * @param dtMonthLoan
	 * @param dtYearLoan
	 * @return
	 * @throws ServiceException 
	 */
	private List<JSONObject> convertData(boolean isLoanInvoceAsOneCount,
			JSONObject itemsCfg,DataTable dtYearLoan) throws ServiceException{
		//是否一级项,项目,贷款余额,本年投放笔数,本年投放金额,本年投放比例,本月投放笔数,本月投放金额,本月投放比例
		//firstLevel,item,zprincipals,ylcount,ypayAmount,ypercent,mlcount,mpayAmount,mpercent
		JSONArray items = itemsCfg.getJSONArray("items");
		if(null == items || items.size() == 0) return null;
		LinkedList<JSONObject> list = new LinkedList<JSONObject>();
		for(int j=0,len = items.size(); j<len; j++){
			JSONObject itemCfg = items.getJSONObject(j);
			int t_ylcount = 0;
			double t_zprincipals = 0d;
			String itemTitle = itemCfg.getString("itemTitle");
			String express = itemCfg.getString("express"); 
			JSONArray fields = itemCfg.getJSONArray("fields");
			Map<String,Object> params = new HashMap<String, Object>();
			/*------ step one : 年度数据的设置  ------*/
			for(int i=0,count = dtYearLoan.getRowCount(); i<count; i++){
				setFormulaParams(params, dtYearLoan, i, fields);
				Object result = FormulaUtil.parseExpression(express, params);
				if(null == result || !((Boolean)result)) continue;
				Integer loanCount = 0;
				if(isLoanInvoceAsOneCount){
					loanCount = dtYearLoan.getInteger(i, "loanCount");
					if(null == loanCount) loanCount = 0;
				}else{
					loanCount = 1;
				}
				t_ylcount += loanCount;
				t_zprincipals +=  dtYearLoan.getDouble(i, "zprincipals");
			}
			
			t_zprincipals = StringHandler.Round(t_zprincipals, 4);
			
			JSONObject data = new JSONObject();
			data.put("item", itemTitle);
			data.put("ylcount", t_ylcount);
			data.put("yzprincipals", t_zprincipals);
			list.add(data);
		}
		return list;
	}
	
	
	/**
	 * 为公式设置参数值
	 * @param params
	 * @param dt
	 * @param index
	 * @param fields
	 * @throws ServiceException 
	 */
	private void setFormulaParams(Map<String,Object> params, DataTable dt, int rowIndex, JSONArray fields) throws ServiceException{
		if(null == fields || fields.size() == 0) return;
		for(int i=0,count=fields.size(); i<count; i++){
			JSONObject fieldObj = fields.getJSONObject(i);
			String cmn = fieldObj.getString("name");
			Integer type = fieldObj.getInteger("type");
			Object val = null;
			switch (type.intValue()) {
			case 1://整数
				val = dt.getInteger(rowIndex, cmn);
				break;
			case 2://浮点数
				val = dt.getDouble(rowIndex, cmn);
				break;
			default:
				throw new ServiceException("type="+type+" 是无知的数据类型!");
			}
			params.put(cmn, val);
		}
	}
}
