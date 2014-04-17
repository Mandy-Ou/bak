package com.cmw.action.finance;


import java.util.ArrayList;
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
import com.cmw.service.inter.finance.LoanStructReportService;
import com.cmw.service.inter.sys.GvlistService;
import com.cmw.service.inter.sys.RestypeService;
import com.cmw.service.inter.sys.SysparamsService;
import com.cmw.service.inter.sys.VarietyService;


/**
 * 贷款业务结构报表  ACTION类
 * @author 程明卫
 * @date 2013-08-09T00:00:00
 */
@Description(remark="贷款业务结构报表ACTION",createDate="2013-08-09T00:00:00",author="程明卫",defaultVals="fcLoanStructReport_")
@SuppressWarnings("serial")
public class LoanStructReportAction extends BaseAction {
	@Resource(name="loanStructReportService")
	private LoanStructReportService loanStructReportService;
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
	 * 获取 贷款业务结构报表 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("statMonth,state#I");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			String statMonth = map.getvalAsStr("statMonth");
			setYmParams(statMonth, map);
			map.put("actionType", 1);//指取本月数据
//			map.put("state", BussStateConstant.LOANINVOCE_STATE_1);//取已放款数据
			DataTable dtMonthLoan = loanStructReportService.getResultList(map);
			map.put("actionType", 0);//指取本年数据
			DataTable dtYearLoan = loanStructReportService.getResultList(map);
			String datas = getHtmlDatas(dtMonthLoan, dtYearLoan);
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
	
	private String getHtmlDatas(DataTable dtMonthLoan, DataTable dtYearLoan) throws ServiceException{
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
		List<JSONObject> dataList = new ArrayList<JSONObject>();
		List<JSONObject> tempList = null;
		RestypeEntity restypeEntity = null;
		String firstTitle = null;
		 Map<String,String> gvlistMap = null;
		
		// 一. 借款主体
		restypeEntity = restypeMap.get(SysCodeConstant.RESTYPE_RECODE_200000);
		if(null != restypeEntity){
			firstTitle = "一、"+restypeEntity.getName();
			gvlistMap = getGvlistByRestype(restypeEntity, dtGvlist);
			tempList = convertData(isLoanInvoceAsOneCount, firstTitle, gvlistMap, dtMonthLoan, dtYearLoan,"loanMain");
			if(null != tempList && tempList.size() > 0) dataList.addAll(tempList);
		}
		 
		// 二. 行业分类
		restypeEntity = restypeMap.get(SysCodeConstant.RESTYPE_RECODE_200001);
		if(null != restypeEntity){
			firstTitle = "二、"+restypeEntity.getName();
			gvlistMap = getGvlistByRestype(restypeEntity, dtGvlist);
			tempList = convertData(isLoanInvoceAsOneCount, firstTitle, gvlistMap, dtMonthLoan, dtYearLoan,"inType");
			if(null != tempList && tempList.size() > 0) dataList.addAll(tempList);
		}
		
		JSONObject cfgData = getCfgByXml();
		String title = null;
		// 三、单笔金额
		JSONObject signleAmountInfo = cfgData.getJSONObject("signleAmountInfo");
		if(null != signleAmountInfo){
			title = signleAmountInfo.getString("itemTitle");
			signleAmountInfo.put("itemTitle", " 三、"+title);
			tempList = convertData(isLoanInvoceAsOneCount, signleAmountInfo, dtMonthLoan, dtYearLoan);
			if(null != tempList && tempList.size() > 0) dataList.addAll(tempList);
		}
		
		// 四、期限类型
		JSONObject loanLimitInfo = cfgData.getJSONObject("loanLimitInfo");
		if(null != loanLimitInfo){
			title = loanLimitInfo.getString("itemTitle");
			loanLimitInfo.put("itemTitle", " 四、"+title);
			tempList = convertData(isLoanInvoceAsOneCount, loanLimitInfo, dtMonthLoan, dtYearLoan);
			if(null != tempList && tempList.size() > 0) dataList.addAll(tempList);
		}
			
		// 五. 贷款方式
		restypeEntity = restypeMap.get(SysCodeConstant.RESTYPE_RECODE_200002);
		if(null != restypeEntity){
			firstTitle = "五、"+restypeEntity.getName();
			gvlistMap = getGvlistByRestype(restypeEntity, dtGvlist);
			tempList = convertData(isLoanInvoceAsOneCount, firstTitle, gvlistMap, dtMonthLoan, dtYearLoan,"loanType");
			if(null != tempList && tempList.size() > 0) dataList.addAll(tempList);
		}
		
		
		// 六、业务品种
		 Map<String,String> breedMap = getBreedMap();
		if(null != breedMap && breedMap.size() >  0){
			firstTitle = "六、业务品种";
			tempList = convertData(isLoanInvoceAsOneCount, firstTitle, breedMap, dtMonthLoan, dtYearLoan,"breed");
			if(null != tempList && tempList.size() > 0) dataList.addAll(tempList);
		}
		
		//是否一级项,项目,贷款余额,本年投放笔数,本年投放金额,本年投放比例,本月投放笔数,本月投放金额,本月投放比例
		//firstLevel,item,zprincipals,ylcount,ypayAmount,ypercent,mlcount,mpayAmount,mpercent

		/*--> step five : 生成HTML  <--*/
		String htmlData = makeHtml(dataList);
		return htmlData;
	}
	
	private String makeHtml(List<JSONObject> dataList){
		if(null == dataList || dataList.size() == 0) return "";
		StringBuilder sbHtml = new StringBuilder();
		//是否一级项,项目,贷款余额,本年投放笔数,本年投放金额,本年投放比例,本月投放笔数,本月投放金额,本月投放比例
		//firstLevel,item,zprincipals,ylcount,ypayAmount,ypercent,mlcount,mpayAmount,mpercent
		double t_zprincipals = 0d;
		int t_ylcount = 0;
		double t_ypayAmount = 0d;
		double t_ypercent = 0d;
		int t_mlcount = 0;
		double t_mpayAmount = 0d;
		double t_mpercent = 0d;
		String htmlTr = null;
		for(JSONObject data : dataList){
			boolean firstLevel = data.getBooleanValue("firstLevel");
			htmlTr = getHtmlTr(data,firstLevel);
			sbHtml.append(htmlTr);
			if(!firstLevel) continue;
			t_zprincipals += data.getDouble("zprincipals");
			t_ylcount += data.getInteger("ylcount");
			t_ypayAmount += data.getDouble("ypayAmount");
			t_ypercent += data.getDouble("ypercent");
			t_mlcount += data.getInteger("mlcount");
			t_mpayAmount += data.getDouble("mpayAmount");
			t_mpercent += data.getDouble("mpercent");
		}
		/*--- 处理最后合计行  ---*/
		JSONObject sumData = new JSONObject();
		sumData.put("item", "合计");
		sumData.put("zprincipals", StringHandler.Round(t_zprincipals, 2));
		sumData.put("ylcount", t_ylcount);
		sumData.put("ypayAmount", StringHandler.Round(t_ypayAmount, 2));
		sumData.put("ypercent", StringHandler.Round(t_ypercent, 2));
		sumData.put("mlcount", t_mlcount);
		sumData.put("mpayAmount", StringHandler.Round(t_mpayAmount, 2));
		sumData.put("mpercent", StringHandler.Round(t_mpercent, 2));
		htmlTr = getHtmlTr(sumData,true);
		sbHtml.append(htmlTr);
		return sbHtml.toString();
	}
	
	
	/**
	 * 获取 LoanReportItem.xml 中的统计项
	 * @return
	 */
	private JSONObject getCfgByXml(){
		String xmlFilePath = FileUtil.getFilePath(getRequest(), "WEB-INF/classes/reportcfg/LoanReportItems.xml");
		XmlHandler xmlHandler = new XmlHandler();
		String content = xmlHandler.getRootContent(xmlFilePath);
		JSONObject jsonObj = FastJsonUtil.convertStrToJSONObj(content);
		return jsonObj;
	}
	private String getHtmlTr(JSONObject jsonObj, boolean isFirstLevel){
		StringBuilder sb = new StringBuilder();
		String trstyle = isFirstLevel ? "style='background-color:#ff9900;'" : "";
		sb.append("<tr "+trstyle+">");
		Object cellData = null;
		cellData = jsonObj.get("item");
		String style = "text-align:left;padding-left:10px;";
		String itemStyle = "text-align:left;padding-left:5px;";
		if(StringHandler.isValidObj(cellData) && cellData.toString().equals("合计")){
			itemStyle = "color:red;";
		}
		String itemTd = isFirstLevel ? getHtmlFirstTd(cellData,itemStyle) : getHtmlTd(cellData,style);
		sb.append(itemTd);
		
		cellData = StringHandler.fmtMicrometer(jsonObj.get("zprincipals"))+"元";
		sb.append(getHtmlTd(cellData,null));
		
		cellData = jsonObj.get("ylcount")+"笔";
		sb.append(getHtmlTd(cellData,null));
		
		cellData = StringHandler.fmtMicrometer(jsonObj.get("ypayAmount"))+"元";
		sb.append(getHtmlTd(cellData,null));
		
		cellData = jsonObj.get("ypercent")+"%";
		sb.append(getHtmlTd(cellData,null));
		
		cellData = jsonObj.get("mlcount")+"笔";
		sb.append(getHtmlTd(cellData,null));
		
		cellData = StringHandler.fmtMicrometer(jsonObj.get("mpayAmount"))+"元";
		sb.append(getHtmlTd(cellData,null));
		
		cellData = jsonObj.get("mpercent")+"%";
		sb.append(getHtmlTd(cellData,null));
		sb.append("</tr>");
		return sb.toString();
	}
	
	/**
	 * 获取一级加粗的单元格
	 * @param cellData
	 * @return
	 */
	private String getHtmlFirstTd(Object cellData,String style){
		if(StringHandler.isValidStr(style)){
			style="style='font-weight:bold;"+style+"'";
		}else{
			style="style='font-weight:bold;'";
		}
		return "<td "+style+">"+cellData+"</td>";
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
			String restypeName,Map<String,String> gvlistData,DataTable dtMonthLoan, DataTable dtYearLoan,String cmn){
		if(!StringHandler.isValidStr(restypeName)) return null;
		if(null == gvlistData || gvlistData.size() == 0) return null;
		LinkedList<JSONObject> list = new LinkedList<JSONObject>();
		int rowNum = 0;
		double sum_zprincipals = 0d;
		int sum_ylcount = 0;
		double sum_ypayAmount = 0d;
		double sum_mpayAmount = 0d;
		int sum_mlcount = 0;
		
		Set<String> keys = gvlistData.keySet();
		for(String gvlisId : keys){
			double t_zprincipals = 0d;
			int t_ylcount = 0;
			double t_ypayAmounts = 0d;
			int t_mlcount = 0;
			double t_mpayAmount = 0d;
			
			/*------ step one : 年度数据的设置  ------*/
			for(int i=0,count=dtYearLoan.getRowCount(); i<count; i++){
				String eqId = dtYearLoan.getString(i, cmn);
				if(StringHandler.isValidStr(eqId)){
					if(eqId.indexOf(gvlisId)== -1) continue;
				}else{
					continue;
				}
				t_zprincipals += dtYearLoan.getDouble(i, "zprincipals");
				Integer loanCount = 0;
				if(isLoanInvoceAsOneCount){
					loanCount = dtYearLoan.getInteger(i, "loanCount");
					if(null == loanCount) loanCount = 0;
				}else{
					loanCount = 1;
				}
				t_ylcount += loanCount;
				t_ypayAmounts +=  dtYearLoan.getDouble(i, "payAmounts");
			}
			
			/*------ step two : 月份数据的设置  ------*/
			for(int i=0,count=dtMonthLoan.getRowCount(); i<count; i++){
				String eqId = dtMonthLoan.getString(i, cmn);
				if(!gvlisId.equals(eqId)) continue;
				
				Integer loanCount = 0;
				if(isLoanInvoceAsOneCount){
					loanCount = dtMonthLoan.getInteger(i, "loanCount");
					if(null == loanCount) loanCount = 0;
				}else{
					loanCount = 1;
				}
				t_mlcount += loanCount;
				t_mpayAmount +=  dtMonthLoan.getDouble(i, "payAmounts");
			}
			
			t_zprincipals = StringHandler.Round(t_zprincipals, 4);
			t_ypayAmounts = StringHandler.Round(t_ypayAmounts, 4);
			t_mpayAmount = StringHandler.Round(t_mpayAmount, 4);
			
			sum_zprincipals += t_zprincipals;
			sum_ylcount += t_ylcount;
			sum_ypayAmount += t_ypayAmounts;
			sum_mpayAmount += t_mpayAmount;
			sum_mlcount += t_mlcount;
			
			rowNum++;
			JSONObject data = new JSONObject();
			String item = "&nbsp;&nbsp;"+rowNum +". "+ gvlistData.get(gvlisId);
			data.put("firstLevel", false);
			data.put("item", item);
			data.put("zprincipals", t_zprincipals);
			data.put("ylcount", t_ylcount);
			data.put("ypayAmount", t_ypayAmounts);
			data.put("mlcount", t_mlcount);
			data.put("mpayAmount", t_mpayAmount);
			list.add(data);
		}
		
		//是否一级项,项目,贷款余额,本年投放笔数,本年投放金额,本年投放比例,本月投放笔数,本月投放金额,本月投放比例
		//firstLevel,item,zprincipals,ylcount,ypayAmount,ypercent,mlcount,mpayAmount,mpercent
		
		/*------ step three : 添加一级分类数据行 [如：一、借款主体]  ------*/
		JSONObject firstData = new JSONObject();
		firstData.put("firstLevel", true);
		firstData.put("item", restypeName);
		firstData.put("zprincipals", StringHandler.Round(sum_zprincipals, 4));
		firstData.put("ylcount", sum_ylcount);
		firstData.put("ypayAmount", StringHandler.Round(sum_ypayAmount, 4));
		firstData.put("mlcount", sum_mlcount);
		firstData.put("mpayAmount", StringHandler.Round(sum_mpayAmount, 4));
		list.addFirst(firstData);
		caclutePercent(list);
		return list;
	}
	
	/**
	 * 计算年度和月份累计投放贷款比例
	 * @param list
	 */
	private void caclutePercent(LinkedList<JSONObject> list){
		JSONObject firstData = list.getFirst();
		double sum_ypayAmount = firstData.getDouble("ypayAmount");
		double sum_mpayAmount = firstData.getDouble("mpayAmount");
		double sum_ypercent = 0;
		double sum_mpercent = 0;
		for(int i=1,count=list.size(); i<count; i++){
			JSONObject itemData = list.get(i);
			double ypayAmount = itemData.getDouble("ypayAmount");
			double mpayAmount = itemData.getDouble("mpayAmount");
			double ypercent = 0;
			double mpercent = 0;
			if(ypayAmount > 0){
				ypercent = StringHandler.Round((ypayAmount/sum_ypayAmount)*100, 4) ;
			}
			if(mpayAmount > 0){
				mpercent = StringHandler.Round((mpayAmount/sum_mpayAmount)*100, 4) ;
			}
			sum_ypercent += ypercent;
			sum_mpercent += mpercent;
			itemData.put("ypercent", ypercent);
			itemData.put("mpercent", mpercent);
		}
		firstData.put("ypercent", StringHandler.Round(sum_ypercent,2));
		firstData.put("mpercent", StringHandler.Round(sum_mpercent,2));
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
			JSONObject itemsCfg,DataTable dtMonthLoan, DataTable dtYearLoan) throws ServiceException{
		//是否一级项,项目,贷款余额,本年投放笔数,本年投放金额,本年投放比例,本月投放笔数,本月投放金额,本月投放比例
		//firstLevel,item,zprincipals,ylcount,ypayAmount,ypercent,mlcount,mpayAmount,mpercent
		String restypeName = itemsCfg.getString("itemTitle");
		JSONArray items = itemsCfg.getJSONArray("items");
		if(null == items || items.size() == 0) return null;
		LinkedList<JSONObject> list = new LinkedList<JSONObject>();
		double sum_zprincipals = 0d;
		int sum_ylcount = 0;
		double sum_ypayAmount = 0d;
		double sum_mpayAmount = 0d;
		int sum_mlcount = 0;
		JSONObject firstSumData = new JSONObject();
		boolean isSubItems = false;/*标识是否有二级项*/
		for(int j=0,len = items.size(); j<len; j++){
			JSONObject itemCfg = items.getJSONObject(j);
			if(itemCfg.containsKey("items")){/*有二级项的情况下：（例：期限类型 -> 短期贷款 和 中期贷款）*/
				LinkedList<JSONObject> subItemList = (LinkedList<JSONObject>)convertData(isLoanInvoceAsOneCount,itemCfg,dtMonthLoan,dtYearLoan);
				JSONObject firstData = subItemList.getFirst();
				firstData.put("firstLevel", false);
				setFirstSumData(firstSumData,firstData);
				list.addAll(subItemList);
				isSubItems = true;
			}else{
				isSubItems = false;
				double t_zprincipals = 0d;
				int t_ylcount = 0;
				double t_ypayAmounts = 0d;
				int t_mlcount = 0;
				double t_mpayAmount = 0d;
				String itemTitle = itemCfg.getString("itemTitle");
				String express = itemCfg.getString("express"); 
				JSONArray fields = itemCfg.getJSONArray("fields");
				Map<String,Object> params = new HashMap<String, Object>();
				/*------ step one : 年度数据的设置  ------*/
				for(int i=0,count = dtYearLoan.getRowCount(); i<count; i++){
					setFormulaParams(params, dtYearLoan, i, fields);
					Object result = FormulaUtil.parseExpression(express, params);
					if(null == result || !((Boolean)result)) continue;
					t_zprincipals += dtYearLoan.getDouble(i, "zprincipals");
					Integer loanCount = 0;
					if(isLoanInvoceAsOneCount){
						loanCount = dtYearLoan.getInteger(i, "loanCount");
						if(null == loanCount) loanCount = 0;
					}else{
						loanCount = 1;
					}
					t_ylcount += loanCount;
					t_ypayAmounts +=  dtYearLoan.getDouble(i, "payAmounts");
				}
				
				/*------ step two : 月份数据的设置  ------*/
				for(int i=0,count=dtMonthLoan.getRowCount(); i<count; i++){
					setFormulaParams(params, dtMonthLoan, i, fields);
					Object result = FormulaUtil.parseExpression(express, params);
					if(null == result || !((Boolean)result)) continue;
					
					Integer loanCount = 0;
					if(isLoanInvoceAsOneCount){
						loanCount = dtMonthLoan.getInteger(i, "loanCount");
						if(null == loanCount) loanCount = 0;
					}else{
						loanCount = 1;
					}
					t_mlcount += loanCount;
					t_mpayAmount +=  dtMonthLoan.getDouble(i, "payAmounts");
				}
				
				t_zprincipals = StringHandler.Round(t_zprincipals, 4);
				t_ypayAmounts = StringHandler.Round(t_ypayAmounts, 4);
				t_mpayAmount = StringHandler.Round(t_mpayAmount, 4);
				
				sum_zprincipals += t_zprincipals;
				sum_ylcount += t_ylcount;
				sum_ypayAmount += t_ypayAmounts;
				sum_mpayAmount += t_mpayAmount;
				sum_mlcount += t_mlcount;
				
				JSONObject data = new JSONObject();
				data.put("firstLevel", false);
				data.put("item", itemTitle);
				data.put("zprincipals", t_zprincipals);
				data.put("ylcount", t_ylcount);
				data.put("ypayAmount", t_ypayAmounts);
				data.put("mlcount", t_mlcount);
				data.put("mpayAmount", t_mpayAmount);
				list.add(data);
			}
		}
		
		
		//是否一级项,项目,贷款余额,本年投放笔数,本年投放金额,本年投放比例,本月投放笔数,本月投放金额,本月投放比例
		//firstLevel,item,zprincipals,ylcount,ypayAmount,ypercent,mlcount,mpayAmount,mpercent
		if(isSubItems){
			firstSumData.put("firstLevel", true);
			firstSumData.put("item", restypeName);
			list.addFirst(firstSumData);
		}else{
			/*------ step three : 添加一级分类数据行 [如：一、借款主体]  ------*/
			JSONObject firstData = new JSONObject();
			firstData.put("firstLevel", true);
			firstData.put("item", restypeName);
			firstData.put("zprincipals", StringHandler.Round(sum_zprincipals, 4));
			firstData.put("ylcount", sum_ylcount);
			firstData.put("ypayAmount", StringHandler.Round(sum_ypayAmount, 4));
			firstData.put("mlcount", sum_mlcount);
			firstData.put("mpayAmount", StringHandler.Round(sum_mpayAmount, 4));
			list.addFirst(firstData);
			caclutePercent(list);
		}
		return list;
	}
	
	/**
	 * 设置一级项汇总数据,并更新二级项 firstLevel = false
	 * @param firstSumData	一级项的汇总数据
	 * @param firstData	二级项的汇总数据
	 */
	private void setFirstSumData(JSONObject firstSumData, JSONObject firstData){
		double zprincipals = firstData.getDouble("zprincipals");
		int ylcount = firstData.getInteger("ylcount");
		double ypayAmount = firstData.getDouble("ypayAmount");
		double ypercent = firstData.getDouble("ypercent");
		int mlcount = firstData.getInteger("mlcount");
		double mpayAmount = firstData.getDouble("mpayAmount");
		double mpercent = firstData.getDouble("mpercent");
		
		if(firstSumData.containsKey("zprincipals")){
			double sum_zprincipals = firstSumData.getDouble("zprincipals");
			sum_zprincipals += zprincipals;
			firstSumData.put("zprincipals", sum_zprincipals);
		}else{
			firstSumData.put("zprincipals", zprincipals);
		}
		
		if(firstSumData.containsKey("ylcount")){
			int sum_ylcount = firstSumData.getInteger("ylcount");
			sum_ylcount += ylcount;
			firstSumData.put("ylcount", sum_ylcount);
		}else{
			firstSumData.put("ylcount", ylcount);
		}
		
		if(firstSumData.containsKey("ypayAmount")){
			double sum_ypayAmount = firstSumData.getDouble("ypayAmount");
			sum_ypayAmount += ypayAmount;
			firstSumData.put("ypayAmount", sum_ypayAmount);
		}else{
			firstSumData.put("ypayAmount", ypayAmount);
		}
		
		if(firstSumData.containsKey("ypercent")){
			double sum_ypercent = firstSumData.getDouble("ypercent");
			sum_ypercent += ypercent;
			firstSumData.put("ypercent", sum_ypercent);
		}else{
			firstSumData.put("ypercent", ypercent);
		}
		
		
		//--> 本月汇总
		if(firstSumData.containsKey("mlcount")){
			int sum_mlcount = firstSumData.getInteger("mlcount");
			sum_mlcount += mlcount;
			firstSumData.put("mlcount", sum_mlcount);
		}else{
			firstSumData.put("mlcount", mlcount);
		}
		
		if(firstSumData.containsKey("mpayAmount")){
			double sum_mpayAmount = firstSumData.getDouble("mpayAmount");
			sum_mpayAmount += mpayAmount;
			firstSumData.put("mpayAmount", sum_mpayAmount);
		}else{
			firstSumData.put("mpayAmount", mpayAmount);
		}
		
		if(firstSumData.containsKey("mpercent")){
			double sum_mpercent = firstSumData.getDouble("mpercent");
			sum_mpercent += mpercent;
			firstSumData.put("mpercent", sum_mpercent);
		}else{
			firstSumData.put("mpercent", mpercent);
		}
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
