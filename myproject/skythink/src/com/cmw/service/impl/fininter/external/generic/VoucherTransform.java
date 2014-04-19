package com.cmw.service.impl.fininter.external.generic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.entity.IdBaseEntity;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.kit.ikexpression.FormulaUtil;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.fininter.BussFinCfgEntity;
import com.cmw.entity.fininter.CodeRuleEntity;
import com.cmw.entity.fininter.CurrencyEntity;
import com.cmw.entity.fininter.EntryTempEntity;
import com.cmw.entity.fininter.FinBussObjectEntity;
import com.cmw.entity.fininter.FinCustFieldEntity;
import com.cmw.entity.fininter.FinSysCfgEntity;
import com.cmw.entity.fininter.ItemTempEntity;
import com.cmw.entity.fininter.SettleEntity;
import com.cmw.entity.fininter.UserMappingEntity;
import com.cmw.entity.fininter.VoucherOplogEntity;
import com.cmw.entity.fininter.VoucherTempEntity;
import com.cmw.entity.sys.AccountEntity;
import com.cmw.entity.sys.FormulaEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.fininter.external.FinSysFactory;
import com.cmw.service.impl.fininter.external.FinSysService;
import com.cmw.service.inter.fininter.AmountLogService;
import com.cmw.service.inter.fininter.BussFinCfgService;
import com.cmw.service.inter.fininter.CodeRuleService;
import com.cmw.service.inter.fininter.CurrencyService;
import com.cmw.service.inter.fininter.EntryTempService;
import com.cmw.service.inter.fininter.FinBussObjectService;
import com.cmw.service.inter.fininter.FinCustFieldService;
import com.cmw.service.inter.fininter.FinSysCfgService;
import com.cmw.service.inter.fininter.ItemTempService;
import com.cmw.service.inter.fininter.SettleService;
import com.cmw.service.inter.fininter.UserMappingService;
import com.cmw.service.inter.fininter.VoucherOplogService;
import com.cmw.service.inter.fininter.VoucherTempService;
import com.cmw.service.inter.sys.AccountService;
import com.cmw.service.inter.sys.FormulaService;
/**
 * 凭证转换类
 * 
 * @author chengmingwei
 *	reffinAccountId
 */
@SuppressWarnings("serial")
@Service(value="voucherTransform")
public class VoucherTransform implements Serializable {
	
	@Resource(name="bussFinCfgService")
	private BussFinCfgService bussFinCfgService;
	
	@Resource(name="finSysCfgService")
	private FinSysCfgService finSysCfgService;
	
	@Resource(name="userMappingService")
	private UserMappingService userMappingService;
	
	@Resource(name="voucherTempService")
	private VoucherTempService voucherTempService;
	
	@Resource(name="entryTempService")
	private EntryTempService entryTempService;
	
	@Resource(name="itemTempService")
	private ItemTempService itemTempService;
	
	@Resource(name="settleService")
	private SettleService settleService;
	
	@Resource(name="finBussObjectService")
	private FinBussObjectService finBussObjectService;
	
	@Resource(name="finCustFieldService")
	private FinCustFieldService finCustFieldService;
	
	@Resource(name="formulaService")
	private FormulaService formulaService;
	
	@Resource(name="currencyService")
	private CurrencyService currencyService;
	
	@Resource(name="voucherOplogService")
	private VoucherOplogService voucherOplogService;
	
	@Resource(name="accountService")
	private AccountService accountService;
	
	@Resource(name="amountLogService")
	private AmountLogService amountLogService;
	
	
	protected static Logger log = Logger.getLogger(VoucherTransform.class);
	//凭证模板缓存对象
	public static Map<String,VoucherTempEntity> voucheTempCache = new HashMap<String,VoucherTempEntity>();
	//分录模板缓存对象
	public static Map<Long,List<EntryTempEntity>> entryTempCache = new HashMap<Long,List<EntryTempEntity>>();
	//核算项模板缓存对象
	public static Map<Long,ItemTempEntity> itemTempCache = new HashMap<Long,ItemTempEntity>();
	//结算方式缓存对象
	public static Map<String,SettleEntity> settleCache = new HashMap<String,SettleEntity>();
	//币别缓存对象
	public static Map<String, CurrencyEntity> currencyCache = new HashMap<String,CurrencyEntity>();
	//自定义业务对象缓存对象
	public static Map<Long, FinBussObjectEntity> bussObjCache = new HashMap<Long,FinBussObjectEntity>();
	//自定义字段缓存对象
	public static Map<Long, FinCustFieldEntity> custFieldCache = new HashMap<Long,FinCustFieldEntity >();
	//公式缓存对象
	public static Map<Long, FormulaEntity> formulaCache = new HashMap<Long,FormulaEntity >();
	//公司账号缓存对象
	public static Map<Long, AccountEntity> accountCache = new HashMap<Long,AccountEntity>();
	//财务最大凭证号缓存对象
	//public static Map<String, CodeRuleEntity> fnumbersCache = new HashMap<String, CodeRuleEntity>();
	
	//--> 业务财务映射配置Id
	private Long bussfinsysId;
	private VoucherTempEntity voucherTemp = null;/*当前凭证模板*/
	private List<EntryTempEntity> entryTemps = null;/*当前分录模板*/
	private  Map<Long,ItemTempEntity> itemTempMap = null;/*当前核算项模板*/
	private static Map<String,SettleEntity> settleMap = null;/*当前结算方式*/
	private Map<Long,AccountEntity> accountMap = null;/*公司银行帐号对象*/
	private  Map<Long,FinBussObjectEntity> bussObjMap = null;/*当前核算业务对象*/
	private Map<Long, FinCustFieldEntity> custFieldMap = null;/*当前自定义字段对象*/
	private Map<Long, FormulaEntity> formulaMap = null;/*当前公式对象*/
	private Map<String, CurrencyEntity> currencyMap = null;/*当前币别对象*/
	private List<VoucherOplogEntity> errOplogs = new ArrayList<VoucherOplogEntity>();/*有异常的凭证日志列表*/
	private int errcount = 0;
	public List<VoucherModel> convert(Map<AmountLogEntity,DataTable> dataMap, SHashMap<String, Object> params) throws ServiceException{
		if(null == dataMap || dataMap.size() == 0) return null;
		Set<AmountLogEntity> alList = dataMap.keySet();
		boolean isNoErr = init(alList, params);
		if(!isNoErr) return null;
		List<VoucherModel> vouchers = startConvert(dataMap,params);
//		updateChangeFnumbers();
		if(null != errOplogs && errOplogs.size() > 0){/*如果有异常，因保存异常日志*/
			errcount = errOplogs.size();
			voucherOplogService.batchSaveEntitys(errOplogs);
		}
		return vouchers;
	}
	
	
	
	/**
	 * 开始转换凭证
	 * @param dataMap 凭证所需的金额日志数据和实放、收款记录 DataTable 对象
	 * @param params	其它参数,如： SysConstant.USER_KEY,sysId,vtempCode
	 * @return 返回转换后的凭证模型列表对象
	 * @throws ServiceException 
	 */
	private List<VoucherModel> startConvert(Map<AmountLogEntity,DataTable> dataMap, SHashMap<String, Object> params) throws ServiceException{
		UserEntity user = (UserEntity)params.get(SysConstant.USER_KEY);
		List<VoucherModel> list = new ArrayList<VoucherModel>();
		Set<AmountLogEntity> alSet = dataMap.keySet();
		for(AmountLogEntity amountLog : alSet){
			DataTable recordDt = dataMap.get(amountLog);
			if(null == recordDt || recordDt.getRowCount() == 0) continue;
			VoucherModel model = createVoucher(amountLog,recordDt,user);
			list.add(model);
		}
		return list;
	}
	
	private VoucherModel createVoucher(AmountLogEntity amountLog, DataTable recrodDt, UserEntity user) throws ServiceException{
		VoucherModel voucherModel = new VoucherModel();
		
		Long amountLogId = amountLog.getId();
		voucherModel.setAmountLogId(amountLogId);
		
		Long sysId = amountLog.getSysId();
		voucherModel.setSysId(sysId);
		
		String uuid = UUID.randomUUID().toString();
		voucherModel.setUuid(uuid);
		
		Long vtempId = voucherTemp.getId();
		voucherModel.setVtempId(vtempId);
		
		Date opdate = amountLog.getOpdate();
		voucherModel.setRegDate(new java.sql.Date(opdate.getTime()));
		voucherModel.setTransDate(new java.sql.Date(opdate.getTime()));
		
		int[] ymd = DateUtil.getYMD(opdate);
		int year = ymd[0];
		int month = ymd[1];
		voucherModel.setYear(year);
		voucherModel.setPeriod(month);
		String groupId = voucherTemp.getGroupId();
		voucherModel.setGroupId(groupId);
		//String number = getFnumber(year, month, groupId);
		//System.out.println(voucherTemp.getCode()+":"+voucherTemp.getName()+"凭证号:"+number);
		/*凭证号改为在 K3FinSysServiceImpl 中的  setFnumber 实现。因为在此处实现，会违反 uk_Voucher 约束（K3 t_Voucher表）*/
		//voucherModel.setNumber(number);
		
		String reference = voucherTemp.getName();
		voucherModel.setReference(reference);
		
		String explanation = entryTemps.get(0).getSummary();
		voucherModel.setExplanation(explanation);
		
		voucherModel.setAttachments(0);
		
		Integer entryCount = amountLog.getCustCount();
		voucherModel.setEntryCount(entryCount);
		
		Double sumamount = (null == amountLog.getSumamount()) ? 0d : amountLog.getSumamount().doubleValue();
		voucherModel.setDebitTotal(sumamount);
		voucherModel.setCreditTotal(sumamount);
		
		List<EntryModel> entrys = null;
		VoucherOplogEntity erropLog = null;
		try {
			entrys = createEntrys(amountLog, recrodDt);
			voucherModel.setEntrys(entrys);
			return voucherModel;
		} catch (ServiceException e) {
			e.printStackTrace();
			erropLog = createVoucherOplogEntity(amountLog, sysId, vtempId, BussStateConstant.VOUCHEROPLOG_ERRCODE_4, user);
			
		}
		if(null != erropLog)errOplogs.add(erropLog);
		return voucherModel;
	}
	
//	private String getFnumber(int fyear, int fperiod, String fgroupId) throws ServiceException{
//		Integer fnumber = null;
//		String key = fyear + "_" + fperiod + "_" + fgroupId;
//		CodeRuleEntity codeRuleObj = fnumbersCache.get(key);
//		if(null == codeRuleObj){
//			SHashMap<String, Object> params = new SHashMap<String, Object>();
//			params.put("fyear", fyear);
//			params.put("fperiod", fperiod);
//			params.put("fgroupId", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + fgroupId);
//			codeRuleObj = codeRuleService.getEntity(params);
//			if(null == codeRuleObj){
//				codeRuleObj = new CodeRuleEntity();
//				codeRuleObj.setFyear(fyear);
//				codeRuleObj.setFperiod(fperiod);
//				codeRuleObj.setFgroupId(fgroupId);
//			}
//		}else{
//			fnumber = codeRuleObj.getFnumber();
//		}
//		
//		if(null == fnumber){
//			fnumber = 1;
//		}else{
//			fnumber++;
//		}
//		codeRuleObj.setFnumber(fnumber);
//		codeRuleObj.setChange(BussStateConstant.CODERULE_CHANGE_1);
//		fnumbersCache.put(key, codeRuleObj);
//		return fnumber.toString();
//	}
	
//	/**
//	 * 更新发生改变的最大财务凭证编号对象
//	 * @throws ServiceException 
//	 */
//	private void updateChangeFnumbers() throws ServiceException{
//		if(null == fnumbersCache || fnumbersCache.size() == 0) return;
//		List<CodeRuleEntity> list = new ArrayList<CodeRuleEntity>();
//		Set<String> keys = fnumbersCache.keySet();
//		for(String key : keys){
//			CodeRuleEntity entity = fnumbersCache.get(key);
//			Integer change = entity.getChange();
//			if(change.intValue() == BussStateConstant.CODERULE_CHANGE_1){
//				entity.setChange(BussStateConstant.CODERULE_CHANGE_0);
//				list.add(entity);
//			}
//		}
//		if(null != list && list.size() >0){
//			codeRuleService.batchSaveOrUpdateEntitys(list);
//		}
//	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<EntryModel> createEntrys(AmountLogEntity amountLog, DataTable recordDt) throws ServiceException{
		List<EntryModel> entrys = new ArrayList<EntryModel>();
		Map<String,DataTable> formulaDt = getFormulaDt(entryTemps,amountLog,recordDt,amountLog.getBussTag());
		Integer direction = voucherTemp.getEntry();
		List[] entryTempArr = getEntryTempsByDirection();
		switch (direction.intValue()) {
		case BussStateConstant.VOUCHERTEMP_ENTRY_1:{
			List<EntryModel> lenderModels = createEntryModelsByMore(entryTempArr[0],recordDt,formulaDt);
			if(null != lenderModels && lenderModels.size() > 0) entrys.addAll(lenderModels);
			List<EntryModel> singleModels = createEntryModelsBySingle(entryTempArr[1],amountLog,formulaDt);
			if(null != singleModels && singleModels.size() > 0) entrys.addAll(singleModels);
			break;
		}case BussStateConstant.VOUCHERTEMP_ENTRY_2:{
			List<EntryModel> lenderModels = createEntryModelsByMore(entryTempArr[1],recordDt,formulaDt);
			if(null != lenderModels && lenderModels.size() > 0) entrys.addAll(lenderModels);
			List<EntryModel> singleModels = createEntryModelsBySingle(entryTempArr[0],amountLog,formulaDt);
			if(null != singleModels && singleModels.size() > 0) entrys.addAll(singleModels);
			break;
		}case BussStateConstant.VOUCHERTEMP_ENTRY_3:{
			entrys = createEntryModelsByMore(entryTemps,recordDt,formulaDt);
			break;
		}default:
			log.error("凭证模板:"+voucherTemp.getName()+"的分录方向\""+direction+"\"不存在!");
			break;
		}
		return entrys;
	}
	
	/**
	 * 用来与实收记录进行匹配的ID列
	 */
	public static final String EQINVOCEID = "eqinvoceId";
	/**
	 * 用来暂存当从业务对象中取数据时，record DataTable 中对应的取值比较列
	 */
	public Map<String,String> recordDtCmnMap = new HashMap<String, String>();
	/**
	 * 获取多方分录的分录数据列表
	 * @param lenderTemps
	 * @param amountLog
	 * @param recrodDt
	 * @return
	 */
	private List<EntryModel> createEntryModelsByMore(List<EntryTempEntity> lenderTemps,DataTable recordDt,Map<String,DataTable> formulaDt){
		if(null == lenderTemps || lenderTemps.size() == 0) return null;
		int count = recordDt.getRowCount();
		List<EntryModel> list = new ArrayList<EntryModel>();
		for(EntryTempEntity temp : lenderTemps){
			Long formulaId = temp.getFormulaId();
			Long conditionId = temp.getConditionId();
			FormulaEntity amountFormula = formulaMap.get(formulaId);
			FormulaEntity conditionFormula = formulaMap.get(conditionId);
			for(int i=0; i < count; i++){
				boolean flag = checkThrough(conditionFormula, i, recordDt, formulaDt);
				if(!flag) continue;
				Object amountResult = getFormulaResult(amountFormula, i, recordDt, formulaDt);
				Long entryTempId = temp.getId();
				ItemModel itemModel = createItemModel(entryTempId,i, recordDt, formulaDt);
				EntryModel model = createEntryModel(temp, amountResult,itemModel);
				list.add(model);
			}
		}
		return list;
	}
	
	/**
	 * 获取单分录余额方向的分录数据列表
	 * @param lenderTemps
	 * @param amountLog
	 * @param recrodDt
	 * @return
	 */
	private List<EntryModel> createEntryModelsBySingle(List<EntryTempEntity> lenderTemps,AmountLogEntity amountLog,Map<String,DataTable> formulaDt){
		if(null == lenderTemps || lenderTemps.size() == 0) return null;
		String[] fields = amountLog.getFields();
		Object[] data = amountLog.getDatas();
		String columnNames = StringHandler.join(fields);
		List<Object> dataSource = new ArrayList<Object>(1);
		dataSource.add(data);
		DataTable recordDt = new DataTable(dataSource,columnNames);
		
		List<EntryModel> list = new ArrayList<EntryModel>();
		for(EntryTempEntity temp : lenderTemps){
			Long formulaId = temp.getFormulaId();
			Long conditionId = temp.getConditionId();
			FormulaEntity amountFormula = formulaMap.get(formulaId);
			Map<String,Object> params = null;
			String expression = null;
			String fieldIds = null;
			Object result = null;
			FormulaEntity conditionFormula = formulaMap.get(conditionId);
			if((null != conditionId && conditionId>0) && null != conditionFormula){
				expression = conditionFormula.getExpress();
				fieldIds = conditionFormula.getFieldIds();
				if(StringHandler.isValidStr(fieldIds)){
					params = getParamsFromAmountLog(fieldIds, amountLog);
				}
				result = FormulaUtil.parseExpression(expression, params);
				if(null == result || !((Boolean)result).booleanValue()) continue;
			}
			
			params = null;
			expression = amountFormula.getExpress();
			fieldIds = amountFormula.getFieldIds();
			if(StringHandler.isValidStr(fieldIds)){
				params = getParamsFromAmountLog(fieldIds, amountLog);
			}
			result = FormulaUtil.parseExpression(expression, params);
			Long entryTempId = temp.getId();
			ItemModel itemModel = createItemModel(entryTempId,0, recordDt, formulaDt);
			EntryModel model = createEntryModel(temp, result,itemModel);
			list.add(model);
		}
		return list;
	}
	
	private Map<String,Object> getParamsFromAmountLog(String fieldIds,AmountLogEntity amountLog){
		Map<String,Object> params = new HashMap<String, Object>();
		if(StringHandler.isValidStr(fieldIds)){
			String[] idsArr = fieldIds.split(",");
			if(null == idsArr || idsArr.length == 0) return params;
			List<FinCustFieldEntity> custfieldList = new ArrayList<FinCustFieldEntity>();
			for(String fieldId : idsArr){
				Long fid = Long.parseLong(fieldId);
				FinCustFieldEntity finCustField = custFieldMap.get(fid);
				if(null == finCustField) continue;
				custfieldList.add(finCustField);
			}
			if(null == custfieldList || custfieldList.size() == 0) return params;
			String[] fields = amountLog.getFields();
			Object[] datas = amountLog.getDatas();
			String columnNames = StringHandler.join(fields);
			List<Object> dataSource = new ArrayList<Object>();
			dataSource.add(datas);
			DataTable dt = new DataTable(dataSource, columnNames);
			for(int i=0,count=fields.length; i<count; i++){
				String field = fields[i];
				boolean isSame = false;
				Integer dataType = null;
				for(FinCustFieldEntity fieldObj : custfieldList){
					String _field = fieldObj.getField();
					if(field.equals(_field)){
						isSame = true;
						dataType = fieldObj.getDataType();
						break;
					}
				}
				if(isSame){
					Object value = getValByDataType(dt, 0, field, dataType);
					//Object value = datas[i];
					params.put(field, value);
				}
			}
		}
		return params;
	} 
	
	private ItemModel createItemModel(Long entryTempId,int index,DataTable recordDt,Map<String,DataTable> formulaDtMap){
		ItemTempEntity itemTemp = itemTempMap.get(entryTempId);
		if(null == itemTemp) return null;
		
		String fieldIds = itemTemp.getFieldIds();
		if(!StringHandler.isValidStr(fieldIds)) return null;
		String[] fieldIdArr = fieldIds.split(",");
		if(null == fieldIdArr || fieldIdArr.length == 0) return null;
		Long bussOjbectId = itemTemp.getBussObjectId();
		FinBussObjectEntity bussObject = bussObjMap.get(bussOjbectId);
		String className = bussObject.getClassName();
		Long fieldId = Long.parseLong(fieldIdArr[0]);/*目前只支持获取一个字段值*/
		FinCustFieldEntity custFieldObj = custFieldMap.get(fieldId);
		String fieldName = custFieldObj.getField();
		
		List<FinCustFieldEntity> finCustFieldList = new ArrayList<FinCustFieldEntity>(1);
		finCustFieldList.add(custFieldObj);
		Map<String,List<FinCustFieldEntity>> fieldsmap = new HashMap<String, List<FinCustFieldEntity>>();
		fieldsmap.put(className, finCustFieldList);
		Map<String, Object> params = getFormulaParams(index,recordDt,formulaDtMap, fieldsmap);
		if(null == params || params.size() == 0 || !StringHandler.isValidObj(params.get(fieldName))) return null;
		ItemModel itemModel = new ItemModel();
		String itemClassId = itemTemp.getItemClassId();
		itemModel.setItemClassId(itemClassId);
		String itemId = params.get(fieldName).toString();
		itemModel.setItemId(itemId);
		Integer itemClassType = ItemModel.ITEMCLASSTYPE_1;
		if(className.equals(BussStateConstant.FINBUSSOBJECT_CLASSNAME_ACCOUNTENTITY)) itemClassType = ItemModel.ITEMCLASSTYPE_2;
		itemModel.setItemClassType(itemClassType);
		return itemModel;
	}
	
	private EntryModel createEntryModel(EntryTempEntity temp,Object amountObj,ItemModel item){
		EntryModel model = new EntryModel();
		String summary = temp.getSummary();
		String accountId = temp.getAccountId();
		String accountId2 = temp.getAccountId2();
		String currencyId = temp.getCurrencyId();
		String settleId = temp.getSettleId();
		model.setExplanation(summary);
		model.setAccountId(accountId);
		model.setAccountId2(accountId2);
		if(!StringHandler.isValidStr(currencyId)) currencyId = voucherTemp.getCurrencyId();
		Long finsysId = voucherTemp.getFinsysId();
		model.setCurrencyId(currencyId);
		CurrencyEntity currencyObj = currencyMap.get(finsysId+"_"+currencyId);
		Float exchangeRate = (null == currencyObj.getErate()) ? 1f : currencyObj.getErate().floatValue();
		model.setExchangeRate(exchangeRate);
		model.setDc(temp.getFdc().intValue());	//余额方向	0:贷方,1:借方
		String amountVal = (null == amountObj) ? "0" : amountObj.toString();
		Double amount = Double.parseDouble(amountVal);
		model.setAmount(amount);
		model.setAmountFor(amount);
		model.setSettleTypeId(settleId);
		SettleEntity settleObj = settleMap.get(finsysId+"_"+settleId);
		String settleType = settleObj.getName();
		model.setSettleType(settleType);
		model.setItem(item);
		return model;
	}

	private boolean checkThrough(FormulaEntity formulaObj,int index,DataTable recordDt,Map<String,DataTable> formulaDt){
		Boolean result = (Boolean)getFormulaResult(formulaObj,index, recordDt, formulaDt);
		return (null == result || !result.booleanValue()) ? false : true;
	}
	
	private Object getFormulaResult(FormulaEntity formulaObj,int index,DataTable recordDt,Map<String,DataTable> formulaDtMap){
		String expression = formulaObj.getExpress();
		String fieldIds = formulaObj.getFieldIds();
		Map<String,List<FinCustFieldEntity>> fieldsmap = null;
		if(StringHandler.isValidStr(fieldIds)){
			String[] fieldIdArr = fieldIds.split(",");
			if(null != fieldIdArr && fieldIdArr.length > 0){
				fieldsmap = new HashMap<String, List<FinCustFieldEntity>>();
				for(String fieldIdStr : fieldIdArr){
					Long fieldId = Long.parseLong(fieldIdStr);
					FinCustFieldEntity finCustField = custFieldMap.get(fieldId);
					Long bussObjectId = Long.parseLong(finCustField.getBussObjectId());
					FinBussObjectEntity bussObject = bussObjMap.get(bussObjectId);
					String className = bussObject.getClassName();
					if(fieldsmap.containsKey(className)){
						fieldsmap.get(className).add(finCustField);
					}else{
						List<FinCustFieldEntity> fieldsList = new ArrayList<FinCustFieldEntity>();
						fieldsList.add(finCustField);
						fieldsmap.put(className, fieldsList);
					}
				}
			}
		}
		if(null == fieldsmap || fieldsmap.size() == 0) return FormulaUtil.parseExpression(expression, null);
		Map<String, Object> params = getFormulaParams(index,recordDt,formulaDtMap,fieldsmap);
		return FormulaUtil.parseExpression(expression, params);
	}

	private Map<String, Object> getFormulaParams(
			int index,DataTable recordDt,
			Map<String, DataTable> formulaDtMap,
			Map<String,List<FinCustFieldEntity>> fieldsmap) {
		
		Map<String,Object> params = new HashMap<String, Object>();
		Set<String> classNames = fieldsmap.keySet();
		for(String className : classNames){
			DataTable dt = formulaDtMap.get(className);
			if(null == dt || dt.getRowCount() == 0) continue;
			List<FinCustFieldEntity> finFieldsList = fieldsmap.get(className);
			int offset = equalData(className,index,recordDt,dt);
			if(-1 == offset) continue;
			for(int i=0,count=finFieldsList.size(); i<count; i++){
				FinCustFieldEntity finCustField = finFieldsList.get(i);
				String cmn = finCustField.getCmn();
				String fieldName = finCustField.getField();
				Integer dataType = finCustField.getDataType();
				Object val = getValByDataType(dt, offset, cmn, dataType);
				params.put(fieldName, val);
			}
		}
		return params;
	}

	/**
	 * 根据自定义字段数据类型，获取公式中自定义字段的值
	 * @param dt
	 * @param offset
	 * @param cmn
	 * @param dataType
	 * @return
	 */
	private Object getValByDataType(DataTable dt, int offset, String cmn,
			Integer dataType) {
		if(null == dataType) dataType = 1;
		Object val = null;
		switch (dataType.intValue()) {
		case BussStateConstant.FINCUSTFIELD_DATATYPE_4:
			val = dt.getInteger(offset, cmn);
			break;
		case BussStateConstant.FINCUSTFIELD_DATATYPE_3:
			val = dt.getDouble(offset, cmn);
			break;
		case BussStateConstant.FINCUSTFIELD_DATATYPE_2:
			val = dt.getDate(offset, cmn);
			break;
		default:
			val = dt.getString(offset, cmn);
			break;
		}
		return val;
	}
	
	private int equalData(String className, int index,DataTable recordDt,DataTable bussObjdt){
		int offset = -1;
		String eqCmnName = recordDtCmnMap.get(className);
		String eqVal = recordDt.getString(index, eqCmnName);
		for(int i=0,count=bussObjdt.getRowCount(); i<count; i++){
			String val = bussObjdt.getString(i, EQINVOCEID);
			if(StringHandler.isValidStr(val) && val.equals(eqVal)){
				offset = i;
				break;
			}
		}
		return offset;
	}
	
	private Map<String,DataTable> getFormulaDt(List<EntryTempEntity> temps,AmountLogEntity amountLog,DataTable recordDt,Integer bussTag) throws ServiceException{
		Map<String,StringBuffer> bussObjectMap = getBussObjectAndFields(temps);
		if(null == bussObjectMap || bussObjectMap.size() == 0) return null;
		Map<String,DataTable> dtMap = new HashMap<String, DataTable>();
		Set<String> classNameKeys = bussObjectMap.keySet();
		for(String className : classNameKeys){
			StringBuffer sb = bussObjectMap.get(className);
			String sqlcmns = StringHandler.RemoveStr(sb);
			sqlcmns = unique(sqlcmns);
			if(!StringHandler.isValidStr(sqlcmns)) continue;
			DataTable dt = getDtByBussTag(className, sqlcmns, bussTag, amountLog, recordDt);
			dtMap.put(className, dt);
		}
		return dtMap;
	}
	
	/**
	 * 去重复字段
	 * @param str
	 * @return
	 */
	private String unique(String str){
		StringBuilder sb = new StringBuilder();
		if(!StringHandler.isValidStr(str)) return null;
		String[] strArr = str.split(",");
		if(null == strArr || strArr.length == 0) return null;
		Map<String,String> unMap = new HashMap<String, String>(strArr.length);
		for(String eqStr : strArr){
			if(unMap.containsKey(eqStr)) continue;
			unMap.put(eqStr, eqStr);
			sb.append(eqStr).append(",");
		}
		return StringHandler.RemoveStr(sb);
	}

	
	private DataTable getDtByBussTag(String entityName, String sqlcmns,Integer bussTag,AmountLogEntity amountLog,DataTable recordDt) throws ServiceException{
		DataTable dt = null;
		String sql = null;
		Map<String,String> replaceMap = new HashMap<String, String>();
		String eqinvoceIdCmn = EQINVOCEID;
		replaceMap.put("id", eqinvoceIdCmn);
		String dtCmns = sqlcmns +","+eqinvoceIdCmn;
		if(entityName.equals(BussStateConstant.FINBUSSOBJECT_CLASSNAME_CUSTBASEENTITY)){/*取客户信息*/
			sql = getCustBaseSql(sqlcmns,eqinvoceIdCmn,recordDt);
			recordDtCmnMap.put(entityName, "contractId");
		}else if(entityName.equals(BussStateConstant.FINBUSSOBJECT_CLASSNAME_LOANCONTRACTENTITY)){/*取借款合同*/
			sql = getLoanContractSql(sqlcmns,eqinvoceIdCmn,recordDt);
			recordDtCmnMap.put(entityName, "contractId");
		}else if(entityName.equals(BussStateConstant.FINBUSSOBJECT_CLASSNAME_LOANINVOCEENTITY)){/*放款单表*/
			if(bussTag.intValue() == BussStateConstant.AMOUNTLOG_BUSSTAG_0){
				dt = cloneDt(recordDt,replaceMap);
			}else{
				sql = getLoanInvoceSql(bussTag,sqlcmns,eqinvoceIdCmn,recordDt);
			}
			recordDtCmnMap.put(entityName, "id");
		}else if(entityName.equals(BussStateConstant.FINBUSSOBJECT_CLASSNAME_FREERECORDSENTITY)){/*实收放款手续费表*/
			if(bussTag.intValue() == BussStateConstant.AMOUNTLOG_BUSSTAG_1){
				dt = cloneDt(recordDt,replaceMap);
			}else{
				sql = getFreeRecrodsSql(bussTag,sqlcmns,eqinvoceIdCmn,recordDt);
			}
			recordDtCmnMap.put(entityName, "id");
		}else if(entityName.equals(BussStateConstant.FINBUSSOBJECT_CLASSNAME_AMOUNTRECORDSENTITY)){/*实收金额表*/
			if(bussTag.intValue() == BussStateConstant.AMOUNTLOG_BUSSTAG_2||
				bussTag.intValue() == BussStateConstant.AMOUNTLOG_BUSSTAG_3||
				bussTag.intValue() == BussStateConstant.AMOUNTLOG_BUSSTAG_4||
				bussTag.intValue() == BussStateConstant.AMOUNTLOG_BUSSTAG_5||
				bussTag.intValue() == BussStateConstant.AMOUNTLOG_BUSSTAG_6||
				bussTag.intValue() == BussStateConstant.AMOUNTLOG_BUSSTAG_7||
				bussTag.intValue() == BussStateConstant.AMOUNTLOG_BUSSTAG_8||
				bussTag.intValue() == BussStateConstant.AMOUNTLOG_BUSSTAG_9){
				dt = cloneDt(recordDt,replaceMap);
			}else{
				sql = getAmountRecordsSql(bussTag,sqlcmns,eqinvoceIdCmn,recordDt);
			}
			recordDtCmnMap.put(entityName, "id");
		}else if(entityName.equals(BussStateConstant.FINBUSSOBJECT_CLASSNAME_PREPAYMENTRECORDSENTITY)){/*实收提前还款金额表*/
			if(bussTag.intValue() == BussStateConstant.AMOUNTLOG_BUSSTAG_9||
					bussTag.intValue() == BussStateConstant.AMOUNTLOG_BUSSTAG_10){
					dt = cloneDt(recordDt,replaceMap);
				}else{
					sql = getAmountRecordsSql(bussTag,sqlcmns,eqinvoceIdCmn,recordDt);
				}
				recordDtCmnMap.put(entityName, "id");
			}else if(entityName.equals(BussStateConstant.FINBUSSOBJECT_CLASSNAME_AMOUNTLOGENTITY)){/*实收金额日志表*/
			Long amountLogId = amountLog.getId();
			dt = createDataTable(eqinvoceIdCmn,amountLogId, amountLog);
			recordDtCmnMap.put(entityName, "id");
		}else if(entityName.equals(BussStateConstant.FINBUSSOBJECT_CLASSNAME_ACCOUNTENTITY)){/*公司银行帐户表*/
			Long accountId = amountLog.getAccountId();
			AccountEntity accountEntity = accountMap.get(accountId);
			dt = createDataTable(eqinvoceIdCmn,accountId, accountEntity);
			recordDtCmnMap.put(entityName, "accountId");
		}
		if((null == dt || dt.getRowCount() == 0) && StringHandler.isValidStr(sql)){
			dt = amountLogService.getDtBySql(sql, dtCmns);
		}
		return dt;
	}

	/**
	 * 根据源DataTable对象，复制新DataTable对象，并替换相应的列
	 * @param sourceDt	要被克隆的源DataTable对象
	 * @param replaceMap 要被替换的列
	 * @return
	 */
	private DataTable cloneDt(DataTable sourceDt, Map<String,String> replaceMap){
		String columnNames = sourceDt.getColumnNames();
		List<Object> dataSource = sourceDt.getDataSource();
		DataTable newDt = new DataTable(dataSource,columnNames);
		newDt.replaceCmns(replaceMap);
		return newDt;
	}
	
	private DataTable createDataTable(String eqcmns,Long accountId, IdBaseEntity entity) {
		String[] fields = entity.getFields();
		Object[] data = entity.getDatas();
		String cmns = StringHandler.join(fields)+","+eqcmns;
		data = Arrays.copyOf(data, data.length+1);
		data[data.length-1] = accountId;
		DataTable dt = new DataTable();
		dt.setColumnNames(cmns);
		List<Object> dataSource = new ArrayList<Object>();
		dataSource.add(data);
		dt.setDataSource(dataSource);
		return dt;
	}

	/**
	 * 获取实收金额信息的SQL语句
	 * @param sqlcmns
	 * @param eqinvouceIdCmn
	 * @param recordDt
	 * @return
	 * @throws ServiceException
	 */
	private String getAmountRecordsSql(Integer bussTag,String sqlcmns,String eqinvouceIdCmn,DataTable recordDt) throws ServiceException{
		String errMsg = null;
		errMsg = "不支持从业务标识[bussTag="+bussTag+"]获取实收金额记录数据，异常发生:VoucherTransfrom.getAmountRecordsSql 方法中!";
		throw new ServiceException(errMsg);
	}
	
	/**
	 * 获取实收放款手续费信息的SQL语句
	 * @param sqlcmns
	 * @param eqinvouceIdCmn
	 * @param recordDt
	 * @return
	 * @throws ServiceException
	 */
	private String getFreeRecrodsSql(Integer bussTag,String sqlcmns,String eqinvouceIdCmn,DataTable recordDt) throws ServiceException{
		String errMsg = null;
		errMsg = "不支持从业务标识[bussTag="+bussTag+"]获取放款手续费数据，异常发生:VoucherTransfrom.getFreeRecrodsSql 方法中!";
		throw new ServiceException(errMsg);
	}
	
	/**
	 * 获取获取放款单信息的SQL语句
	 * @param sqlcmns
	 * @param eqinvouceIdCmn
	 * @param recordDt
	 * @return
	 * @throws ServiceException
	 */
	private String getLoanInvoceSql(Integer bussTag,String sqlcmns,String eqinvouceIdCmn,DataTable recordDt) throws ServiceException{
		String ids = null;
		String errMsg = null;
		StringBuffer sb = new StringBuffer();
		switch (bussTag.intValue()) {
		case BussStateConstant.AMOUNTLOG_BUSSTAG_1:{
			ids = getIdsByDt("invoceId", recordDt);
			sqlcmns = "A."+sqlcmns.replace(",", ",A.")+",B.id as "+eqinvouceIdCmn;
			sb.append("select ").append(sqlcmns).append(" from fc_LoanInvoce A ")
			.append(" inner join fc_Free B on A.id=B.loanInvoceId ")
			.append(" where B.id in ("+ids+") ");
			break;
		}default:
			errMsg = "不支持从业务标识[bussTag="+bussTag+"]获取放款单数据，异常发生:VoucherTransfrom.getLoanInvoceSql 方法中!";
			break;
		}
		if(StringHandler.isValidStr(errMsg)){
			throw new ServiceException(errMsg);
		}else{
			return sb.toString();
		}
	}
	
	/**
	 * 获取借款合同信息的SQL语句
	 * @param sqlcmns
	 * @param eqinvouceIdCmn
	 * @param recordDt
	 * @return
	 * @throws ServiceException
	 */
	private String getLoanContractSql(String sqlcmns,String eqinvouceIdCmn,DataTable recordDt){
		String ids = getIdsByDt("contractId", recordDt);
		StringBuffer sb = new StringBuffer();
		sqlcmns += ",id as "+eqinvouceIdCmn;
		sb.append("select ").append(sqlcmns).append(" from fc_LoanContract A ")
		.append(" where A.id in ("+ids+") ");
		return sb.toString();
	}
	
	/**
	 * 获取查询客户基础信息的SQL语句
	 * @param sqlcmns
	 * @param eqinvouceIdCmn
	 * @param recordDt
	 * @return
	 * @throws ServiceException
	 */
	private String getCustBaseSql(String sqlcmns,String eqinvouceIdCmn,DataTable recordDt){
		String ids = getIdsByDt("contractId", recordDt);
		StringBuffer sb = new StringBuffer();
		sqlcmns = "A."+sqlcmns.replace(",", ",A.")+",B."+eqinvouceIdCmn;
		sb.append("select ").append(sqlcmns).append(" from crm_CustBase A inner join ");
		sb.append("(select id as "+eqinvouceIdCmn+",")
		.append("(case custType when 0 then (select baseId from crm_CustomerInfo where id=customerId)")
		.append(" else (select baseId from crm_Ecustomer where id=customerId) end) as baseId")
		.append(" from fc_LoanContract) B on A.id=B.baseId ")
		.append(" where B."+eqinvouceIdCmn+" in ("+ids+") ");
		return sb.toString();
	}
	
	/**
	 * 获取 DataTable 中指定列的值
	 * @param cmn	指定列
	 * @param dt	DataTable 对象
	 * @return 返回指定列的值。
	 */
	private String getIdsByDt(String cmn,DataTable dt){
		StringBuffer sb = new StringBuffer();
		for(int i=0,count=dt.getRowCount(); i<count; i++){
			String val = dt.getString(i, cmn);
			if(!StringHandler.isValidStr(val)) continue;
			sb.append(val).append(",");
		}
		return StringHandler.RemoveStr(sb);
	}
	
	
	private Map<String,StringBuffer> getBussObjectAndFields(List<EntryTempEntity> temps) {
		Map<String,StringBuffer> strMap = new HashMap<String, StringBuffer>();
		for(EntryTempEntity temp : temps){
			Long formulaId = temp.getFormulaId();
			Long conditionId = temp.getConditionId();
			FormulaEntity amountFormula = formulaMap.get(formulaId);
			FormulaEntity conditionFormula = formulaMap.get(conditionId);
			String fieldIds = null,fieldIds2=null;
			if(null != amountFormula) fieldIds = amountFormula.getFieldIds();
			if(null != conditionFormula) fieldIds2 = conditionFormula.getFieldIds();
			Long entryId = temp.getId();
			ItemTempEntity itemTemp = itemTempMap.get(entryId);
			if(null == itemTemp) continue;
			String fieldIds3 = itemTemp.getFieldIds();
			StringBuffer sbFieldIds = new StringBuffer();
			String fieldIdAll = "";
			if(StringHandler.isValidStr(fieldIds)) sbFieldIds.append(fieldIds).append(",");
			if(StringHandler.isValidStr(fieldIds2)) sbFieldIds.append(fieldIds2).append(",");
			if(StringHandler.isValidStr(fieldIds3)) sbFieldIds.append(fieldIds3).append(",");
			fieldIdAll = StringHandler.RemoveStr(sbFieldIds);
			if(!StringHandler.isValidStr(fieldIdAll)) continue;
			
			String[] fieldIdArr = fieldIdAll.split(",");
			for(String fieldId : fieldIdArr){
				FinCustFieldEntity fieldEntity = custFieldMap.get(Long.parseLong(fieldId));
				String fieldName = fieldEntity.getCmn();
				String bussObjectId = fieldEntity.getBussObjectId();
				FinBussObjectEntity bussObjEntity = bussObjMap.get(Long.parseLong(bussObjectId));
				String className = bussObjEntity.getClassName();
				if(strMap.containsKey(className)){
					strMap.get(className).append(fieldName).append(",");
				}else{
					StringBuffer sb = new StringBuffer();
					sb.append(fieldName).append(",");
					strMap.put(className, sb);
				}
			}
		}
		return strMap;
	}
	
	
	
	/**
	 * 获取贷方，借方分录数组 
	 * @return 以数据形式返回  [0] : 借方, [1]:贷方
	 */
	@SuppressWarnings("rawtypes")
	private List[] getEntryTempsByDirection(){
		List<EntryTempEntity> lenderTemps = new ArrayList<EntryTempEntity>();
		List<EntryTempEntity> debitTemps = new ArrayList<EntryTempEntity>();
		for(EntryTempEntity entryTemp : entryTemps){
			Byte fdc = entryTemp.getFdc();
			switch (fdc.byteValue()) {
			case BussStateConstant.ENTRYTEMP_FDC_0:
				lenderTemps.add(entryTemp);
				break;
			case BussStateConstant.ENTRYTEMP_FDC_1:
				debitTemps.add(entryTemp);
				break;
			default:
				break;
			}
		}
		return new List[] {debitTemps,lenderTemps};
	} 
	
	public List<VoucherModel> convert(String amountLogIds, SHashMap<String, Object> params) throws ServiceException{
		Map<AmountLogEntity,DataTable> dataMap = amountLogService.getBussAmountRecords(amountLogIds);
		if(null == dataMap || dataMap.size() == 0) return null;
		Set<AmountLogEntity> alList = dataMap.keySet();
		boolean isNoErr = init(alList, params);
		if(!isNoErr) return null;
		return startConvert(dataMap,params);
	}
	
	
	/**
	 * 初始化凭证转换所需的数据
	 * @param alList	实收金额日志
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	private boolean init(Set<AmountLogEntity> alList,SHashMap<String, Object> params) throws ServiceException {
		Long sysId = params.getvalAsLng("sysId");
		String vtempCode = params.getvalAsStr("vtempCode");/*凭证模板编号*/
		UserEntity user = (UserEntity)params.get(SysConstant.USER_KEY);
		Integer errCode = checkFinInterface(sysId,user);
		voucherTemp = getVoucherTempByCode(vtempCode);
		Long voucherTempId = 0L;
		if(null == voucherTemp){
			errCode = BussStateConstant.VOUCHEROPLOG_ERRCODE_3;
		}else{
			voucherTempId = voucherTemp.getId();
		}
		if(errCode.intValue() != BussStateConstant.VOUCHEROPLOG_ERRCODE_0){/*有异常*/
			saveVoucherOplogs(alList,sysId,voucherTempId,errCode,user);
			return false;
		}
		
		entryTemps = getEntryTempByVtempId(voucherTempId);
		if(null == entryTemps | entryTemps.size() == 0){
			errCode = BussStateConstant.VOUCHEROPLOG_ERRCODE_3;
			saveVoucherOplogs(alList,sysId,voucherTempId,errCode,user);
			return false;
		}
		
		settleMap = getSettles(entryTemps);
		accountMap = getAccountByAmountLogs(alList);
		formulaMap = getFormulasByEntrys(entryTemps);
		currencyMap = getCurrencys(voucherTemp,entryTemps);
		itemTempMap = getItemTempByEntrys(entryTemps);
		String[] idsArr = getBussObjectIdAndFieldIds(entryTemps,itemTempMap);
		if(null != idsArr && idsArr.length > 1){
			String bussObjectIds = idsArr[0];
			if(StringHandler.isValidStr(bussObjectIds)) bussObjMap = getBussObjectByIds(bussObjectIds);
			String custfieldIds = idsArr[1];
			if(StringHandler.isValidStr(custfieldIds)) custFieldMap = getCustFieldByIds(custfieldIds);
		}
		return true;
	}
	
	/**
	 * 保存凭证处理日志
	 * @throws ServiceException 
	 */
	private void saveVoucherOplogs(Set<AmountLogEntity> alList,Long sysId,Long vtempId,
			Integer errCode,UserEntity user) throws ServiceException{
		if(null == alList || alList.size() == 0) return;
		List<VoucherOplogEntity> voplogList = new ArrayList<VoucherOplogEntity>(alList.size());
		for(AmountLogEntity entity : alList){
			VoucherOplogEntity opLog = createVoucherOplogEntity(entity, sysId, vtempId, errCode, user);
			voplogList.add(opLog);
		}
		errcount = alList.size();
		voucherOplogService.batchSaveEntitys(voplogList);
	}
	
	
	/**
	 * 获取凭证异常记录数
	 * @return 返回凭证异常记录数
	 */
	public int getErrcount() {
		return errcount;
	}

	private VoucherOplogEntity createVoucherOplogEntity(AmountLogEntity entity,
		Long sysId, Long vtempId, Integer errCode, UserEntity user) {
		Long amountLogId = entity.getId();
		VoucherOplogEntity opLog = new VoucherOplogEntity();
		opLog.setSysId(sysId);
		opLog.setVtempId(vtempId);
		opLog.setStatus(BussStateConstant.VOUCHEROPLOG_STATUS_1);
		opLog.setErrCode(errCode);
		opLog.setAmountLogId(amountLogId);
		BeanUtil.setCreateInfo(user, opLog);
		return opLog;
	}
	
	/**
	 * 检查指定业务系统的财务接口配置
	 * @param sysId 系统ID
	 * @return 返回错误码。 错误码定义见 : VOUCHEROPLOG_ERRCODE_0 至 VOUCHEROPLOG_ERRCODE_4 的定义
	 */
	private Integer checkFinInterface(Long sysId,UserEntity user){
		Integer errCode = BussStateConstant.VOUCHEROPLOG_ERRCODE_0;
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("sysId", sysId);
		Long finsysId = null;
		try {
			BussFinCfgEntity bussFinCfgEntity = bussFinCfgService.getEntity(params);
			if(null == bussFinCfgEntity || bussFinCfgEntity.getIsenabled().byteValue() != SysConstant.OPTION_ENABLED){
				errCode = BussStateConstant.VOUCHEROPLOG_ERRCODE_5;
			}
			bussfinsysId = bussFinCfgEntity.getId();
			finsysId = bussFinCfgEntity.getFinsysId();
		} catch (ServiceException e) {
			e.printStackTrace();
			errCode = BussStateConstant.VOUCHEROPLOG_ERRCODE_5;
		}
		
		if(errCode.intValue() != BussStateConstant.VOUCHEROPLOG_ERRCODE_0 || null == finsysId) return errCode;
		
		String finsysCode = null;
		try {
			FinSysCfgEntity finSysCfg = finSysCfgService.getEntity(finsysId);
			if(null == finSysCfg || finSysCfg.getIsenabled().byteValue() != SysConstant.OPTION_ENABLED){
				errCode = BussStateConstant.VOUCHEROPLOG_ERRCODE_6;
			}
			finsysCode = finSysCfg.getCode();
		} catch (ServiceException e) {
			e.printStackTrace();
			errCode = BussStateConstant.VOUCHEROPLOG_ERRCODE_6;
		}
		if(errCode.intValue() != BussStateConstant.VOUCHEROPLOG_ERRCODE_0 || !StringHandler.isValidStr(finsysCode)) return errCode;
		
		FinSysService finSysService = FinSysFactory.getInstance(finsysCode);
		try {
			finSysService.testConnection();
		} catch (ServiceException e) {
			e.printStackTrace();
			errCode = BussStateConstant.VOUCHEROPLOG_ERRCODE_1;
		}
		if(errCode.intValue() != BussStateConstant.VOUCHEROPLOG_ERRCODE_0) return errCode;
		
		/*------ 检查用户帐号是否映射  -----*/
		params.clear();
		params.put("finsysId", bussfinsysId);
		params.put("userId", user.getUserId());
		try {
			UserMappingEntity userMappingEntity = userMappingService.getEntity(params);
			if(null == userMappingEntity || userMappingEntity.getRefId() == null){
				errCode = BussStateConstant.VOUCHEROPLOG_ERRCODE_2;
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			errCode = BussStateConstant.VOUCHEROPLOG_ERRCODE_2;
		}
		return errCode;
	}
	
	
	/**
	 * 根据凭证模板编号获取凭证模板
	 * @param vtempCode 凭证模板编号
	 * @return
	 */
	private VoucherTempEntity getVoucherTempByCode(String vtempCode){
		VoucherTempEntity voucherTemp = null;
		try {
			if(this.bussfinsysId == null) throw new ServiceException("not find VoucherTemp !");
			String cacheKey = bussfinsysId + "_" + vtempCode;
			voucherTemp = voucheTempCache.get(cacheKey);
			if(null != voucherTemp) return voucherTemp;
			
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("finsysId", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + bussfinsysId);
			params.put("code", vtempCode);
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			voucherTemp = voucherTempService.getEntity(params);
			if(null != voucherTemp) voucheTempCache.put(cacheKey, voucherTemp);
		} catch (ServiceException e) {
			e.printStackTrace();
			log.error("not find VoucherTemp !");
		}
		return voucherTemp;
	}
	
	
	
	/**
	 * 根据凭证模板Id 获取分录列表
	 * @param voucherTempId 凭证模板ID
	 * @return
	 */
	private List<EntryTempEntity> getEntryTempByVtempId(Long voucherTempId){
		List<EntryTempEntity> entrys  = null;
		try {
			entrys = entryTempCache.get(voucherTempId);
			if(null != entrys) return entrys;
			
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("voucherId", voucherTempId);
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			entrys = entryTempService.getEntityList(params);
			if(null != entrys) entryTempCache.put(voucherTempId, entrys);
		} catch (ServiceException e) {
			e.printStackTrace();
			log.error("not find VoucherTemp !");
		}
		return entrys;
	}
	
	/**
	 * 根据分录列表  获取核算项列表
	 * @param entrys 分录列表
	 * @return
	 */
	private Map<Long,ItemTempEntity> getItemTempByEntrys(List<EntryTempEntity> entrys){
		 Map<Long,ItemTempEntity> itemTempMap  = new HashMap<Long, ItemTempEntity>();
		try {
			StringBuffer sb = new StringBuffer();
			for(EntryTempEntity entry : entrys){
				Long entryId = entry.getId();
				if(itemTempCache.containsKey(entryId)){
					ItemTempEntity itemTemp = itemTempCache.get(entryId);
					itemTempMap.put(entryId, itemTemp);
				}else{
					sb.append(entryId).append(",");
				}
			}
			/*--- 如果全部从缓存中获取的，则返回缓存数据---*/
			if(null == sb || sb.length() == 0) return itemTempMap;
			String entryIds = StringHandler.RemoveStr(sb);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("entryId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + entryIds);
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			List<ItemTempEntity> itemTemps = itemTempService.getEntityList(params);
			if(null != itemTemps && itemTemps.size() > 0){
				for(ItemTempEntity itemTemp : itemTemps){
					Long entryId = itemTemp.getEntryId();
					itemTempCache.put(entryId, itemTemp);
					itemTempMap.put(entryId, itemTemp);
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return itemTempMap;
	}
	
	/**
	 * 根据实收金额列表  获取公司账户 Map对象
	 * @param alList 实收金额日志列表
	 * @return 
	 */
	private Map<Long,AccountEntity> getAccountByAmountLogs(Set<AmountLogEntity> alList){
		 Map<Long,AccountEntity> accountMap  = new HashMap<Long, AccountEntity>();
		 try {
			StringBuffer sb = new StringBuffer();
			for(AmountLogEntity entity : alList){
				Long accountId = entity.getAccountId();
				if(null == accountId) continue;
				if(accountCache.containsKey(accountId)){
					AccountEntity accountObj = accountCache.get(accountId);
					accountMap.put(accountId, accountObj);
				}else{
					sb.append(accountId).append(",");
				}
			}
			/*--- 如果全部从缓存中获取的，则返回缓存数据---*/
			if(null == sb || sb.length() == 0) return accountMap;
			String accountIds = StringHandler.RemoveStr(sb);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + accountIds);
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			List<AccountEntity> list = accountService.getEntityList(params);
			if(null != list && list.size() > 0){
				for(AccountEntity entity : list){
					Long id = entity.getId();
					accountCache.put(id, entity);
					accountMap.put(id, entity);
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return accountMap;
	}
	
	private String[] getBussObjectIdAndFieldIds(List<EntryTempEntity> entryTemps,Map<Long,ItemTempEntity> itemTempMap){
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		Map<String,String> eqMap = new HashMap<String, String>();
		for(EntryTempEntity temp : entryTemps){
			Long formulaId = temp.getFormulaId();
			Long conditionId = temp.getConditionId();
			if(null != formulaId){
				FormulaEntity formulaEntity = formulaMap.get(formulaId);
				if(null != formulaEntity){
					String fieldIds = formulaEntity.getFieldIds();
					if(StringHandler.isValidStr(fieldIds)){
						sb2.append(fieldIds).append(",");
					}
				}
			}
			if(null != conditionId){
				FormulaEntity conditionFormulaEntity = formulaMap.get(conditionId);
				if(null != conditionFormulaEntity){
					String fieldIds2 = conditionFormulaEntity.getFieldIds();
					if(StringHandler.isValidStr(fieldIds2)){
						sb2.append(fieldIds2).append(",");
					}
				}
			}
		}
		 
		String custFieldIds = StringHandler.RemoveStr(sb2, ",");
		Map<Long,FinCustFieldEntity> fieldMap = getCustFieldByIds(custFieldIds);
		eqMap.clear();
		if(null != fieldMap && fieldMap.size() > 0){
			Collection<FinCustFieldEntity> fieldList = fieldMap.values();
			for(FinCustFieldEntity fieldEntity : fieldList){
				String bussObjectIdStr = fieldEntity.getBussObjectId();
				if(!eqMap.containsKey(bussObjectIdStr)){
					sb1.append(bussObjectIdStr).append(",");
					eqMap.put(bussObjectIdStr, bussObjectIdStr);
				}
			}
		}
		if(null != itemTempMap && itemTempMap.size() > 0){
			Collection<ItemTempEntity> list = itemTempMap.values();
			for(ItemTempEntity itemTemp : list){
				Long bussObjectId = itemTemp.getBussObjectId();
				String fieldIds = itemTemp.getFieldIds();
				if(null != bussObjectId) sb1.append(bussObjectId).append(",");
				if(StringHandler.isValidStr(fieldIds)){
					if((null != sb2 && sb2.length() > 0) && (sb2.lastIndexOf(",") < sb2.length() -1)){
						sb2.append(",");
					}
					sb2.append(fieldIds).append(",");
				}
			}
		}
		return new String[]{StringHandler.RemoveStr(sb1),StringHandler.RemoveStr(sb2)};
	}
	/**
	 * 根据核算业务对象ID  获取核算业务对象Map
	 * @param bussObjectIds 核算业务对象ID
	 * @return 
	 */
	private Map<Long,FinBussObjectEntity> getBussObjectByIds(String bussObjectIds){
		if(!StringHandler.isValidStr(bussObjectIds)) return null;
		 Map<Long,FinBussObjectEntity> bussobjMap  = new HashMap<Long, FinBussObjectEntity>();
		try {
			String[] idArr = bussObjectIds.split(",");
			StringBuffer sb = new StringBuffer();
			for(String id : idArr){
				Long bussObjId = Long.parseLong(id);
				if(bussObjCache.containsKey(bussObjId)){
					FinBussObjectEntity bussobj = bussObjCache.get(bussObjId);
					bussobjMap.put(bussObjId, bussobj);
				}else{
					sb.append(bussObjId).append(",");
				}
			}
			/*--- 如果全部从缓存中获取的，则返回缓存数据---*/
			if(null == sb || sb.length() == 0) return bussobjMap;
			String ids = StringHandler.RemoveStr(sb);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			List<FinBussObjectEntity> list = finBussObjectService.getEntityList(params);
			if(null != list && list.size() > 0){
				for(FinBussObjectEntity entity : list){
					Long id = entity.getId();
					bussObjCache.put(id, entity);
					bussobjMap.put(id, entity);
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return bussobjMap;
	}

	
	/**
	 * 根据分录列表  获取结算方式 Map对象
	 * @param entrys 分录列表
	 * @return 
	 */
	private Map<String,SettleEntity> getSettles(List<EntryTempEntity> entrys){
		 Map<String,SettleEntity> settleMap  = new HashMap<String, SettleEntity>();
		 try {
			 SettleEntity settleObj = null;
			 String settleId = null;
			 String key = null;
			 StringBuffer sb = new StringBuffer();
			 Long finsysId = voucherTemp.getFinsysId();
		
			for(EntryTempEntity entry : entrys){
				settleId = entry.getSettleId();
				if(!StringHandler.isValidStr(settleId)) continue;
				key = finsysId+"_"+settleId;
				 if(settleCache.containsKey(key)){
					 settleObj = settleCache.get(key);
					 settleMap.put(key, settleObj);
				 }else{
					 sb.append(settleId).append(",");
				 }
			
			}
			/*--- 如果全部从缓存中获取的，则返回缓存数据---*/
			if(null == sb || sb.length() == 0) return settleMap;
			String ids = StringHandler.RemoveStr(sb);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("finsysId", finsysId);
			params.put("refId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			List<SettleEntity> list = settleService.getEntityList(params);
			if(null != list && list.size() > 0){
				for(SettleEntity entity : list){
					Long refId = entity.getRefId();
					key = finsysId+"_"+refId;
					settleCache.put(key, entity);
					settleMap.put(key, entity);
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return settleMap;
	}
	
	/**
	 * 根据凭证模板和分录列表  获取币别 Map对象
	 * @param bussfinsysId 财务业务映射ID
	 * @param voucherTemp	凭证模板对象
	 * @param entrys 分录列表
	 * @return 
	 */
	private Map<String,CurrencyEntity> getCurrencys(VoucherTempEntity voucherTemp,List<EntryTempEntity> entrys){
		 Map<String,CurrencyEntity> currencyMap  = new HashMap<String, CurrencyEntity>();
		 try {
			 CurrencyEntity currencyObj = null;
			 String currencyId = null;
			 String key = null;
			 StringBuffer sb = new StringBuffer();
			 Long finsysId = voucherTemp.getFinsysId();
			 currencyId = voucherTemp.getCurrencyId();
			
			 if(StringHandler.isValidStr(currencyId)){
				 key = finsysId+"_"+currencyId;
				 if(currencyCache.containsKey(key)){
					 currencyObj = currencyCache.get(key);
					 currencyMap.put(key, currencyObj);
				 }else{
					 sb.append(currencyId).append(",");
				 }
			 }
			 
		
			for(EntryTempEntity entry : entrys){
				currencyId = entry.getCurrencyId();
				if(!StringHandler.isValidStr(currencyId)) continue;
				key = finsysId+"_"+currencyId;
				 if(currencyCache.containsKey(key)){
					 currencyObj = currencyCache.get(key);
					 currencyMap.put(key, currencyObj);
				 }else{
					 sb.append(currencyId).append(",");
				 }
			
			}
			/*--- 如果全部从缓存中获取的，则返回缓存数据---*/
			if(null == sb || sb.length() == 0) return currencyMap;
			String ids = StringHandler.RemoveStr(sb);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("finsysId", finsysId);
			params.put("refId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			List<CurrencyEntity> list = currencyService.getEntityList(params);
			if(null != list && list.size() > 0){
				for(CurrencyEntity entity : list){
					Long refId = entity.getRefId();
					key = finsysId+"_"+refId;
					currencyCache.put(key, entity);
					currencyMap.put(key, entity);
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return currencyMap;
	}
	
	/**
	 * 根据分录列表  获取公式 Map对象
	 * @param entrys 分录列表
	 * @return 
	 */
	private Map<Long,FormulaEntity> getFormulasByEntrys(List<EntryTempEntity> entrys){
		 Map<Long,FormulaEntity> formulaMap  = new HashMap<Long, FormulaEntity>();
		 try {
			StringBuffer sb = new StringBuffer();
			for(EntryTempEntity entry : entrys){
				Long formulaId = entry.getFormulaId();
				Long conditionId = entry.getConditionId();
				if(null != formulaId){
					if(formulaCache.containsKey(formulaId)){
						FormulaEntity formulaObj = formulaCache.get(formulaId);
						formulaMap.put(formulaId, formulaObj);
					}else{
						sb.append(formulaId).append(",");
					}
				}
				
				if(null != conditionId){
					if(formulaCache.containsKey(conditionId)){
						FormulaEntity formulaObj = formulaCache.get(conditionId);
						formulaMap.put(conditionId, formulaObj);
					}else{
						sb.append(conditionId).append(",");
					}
				}
			
			}
			/*--- 如果全部从缓存中获取的，则返回缓存数据---*/
			if(null == sb || sb.length() == 0) return formulaMap;
			String formulaIds = StringHandler.RemoveStr(sb);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + formulaIds);
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			List<FormulaEntity> list = formulaService.getEntityList(params);
			if(null != list && list.size() > 0){
				for(FormulaEntity entity : list){
					Long id = entity.getId();
					formulaCache.put(id, entity);
					formulaMap.put(id, entity);
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return formulaMap;
	}

	/**
	 * 根据核算项列表  获取财务自定义字段 Map对象
	 * @param entrys 分录列表
	 * @return 
	 */
	private Map<Long,FinCustFieldEntity> getCustFieldByIds(String custfieldIds){
		if(!StringHandler.isValidStr(custfieldIds)) return null;
		 Map<Long,FinCustFieldEntity> custfieldMap  = new HashMap<Long, FinCustFieldEntity>();
		try {
			String[] idArr = custfieldIds.split(",");
			StringBuffer sb = new StringBuffer();
			for(String id : idArr){
				Long custfieldId = Long.parseLong(id);
				if(custFieldCache.containsKey(custfieldId)){
					FinCustFieldEntity custfieldObj = custFieldCache.get(custfieldId);
					custfieldMap.put(custfieldId, custfieldObj);
				}else{
					sb.append(custfieldId).append(",");
				}
			}
			/*--- 如果全部从缓存中获取的，则返回缓存数据---*/
			if(null == sb || sb.length() == 0) return custfieldMap;
			String ids = StringHandler.RemoveStr(sb);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			List<FinCustFieldEntity> list = finCustFieldService.getEntityList(params);
			if(null != list && list.size() > 0){
				for(FinCustFieldEntity entity : list){
					Long id = entity.getId();
					custFieldCache.put(id, entity);
					custfieldMap.put(id, entity);
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return custfieldMap;
	}
}
