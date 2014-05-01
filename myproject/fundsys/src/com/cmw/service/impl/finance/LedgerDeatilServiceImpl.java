package com.cmw.service.impl.finance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.FormDiyRecodeConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.LedgerDeatilDaoInter;
import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.ExtPlanEntity;
import com.cmw.entity.finance.LoanInvoceEntity;
import com.cmw.entity.finance.MortgageEntity;
import com.cmw.entity.finance.PlanEntity;
import com.cmw.entity.finance.PledgeEntity;
import com.cmw.entity.sys.FieldValEntity;
import com.cmw.entity.sys.FormdiyEntity;
import com.cmw.entity.sys.VarietyEntity;
import com.cmw.service.inter.crm.CustomerInfoService;
import com.cmw.service.inter.crm.EbankService;
import com.cmw.service.inter.crm.EcustomerService;
import com.cmw.service.inter.finance.AmountRecordsService;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.finance.ExtPlanService;
import com.cmw.service.inter.finance.LedgerDeatilService;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.finance.LoanInvoceService;
import com.cmw.service.inter.finance.MortgageService;
import com.cmw.service.inter.finance.PlanService;
import com.cmw.service.inter.finance.PledgeService;
import com.cmw.service.inter.sys.CityService;
import com.cmw.service.inter.sys.CountryService;
import com.cmw.service.inter.sys.FieldValService;
import com.cmw.service.inter.sys.FormdiyService;
import com.cmw.service.inter.sys.ProvinceService;
import com.cmw.service.inter.sys.RegionService;
import com.cmw.service.inter.sys.UserService;
import com.cmw.service.inter.sys.VarietyService;

/**
 *Title: LedgerDeatilServiceImp.java
 *@作者： 彭登浩
 *@ 创建时间：2013-12-11下午12:03:17
 *@ 公司：	同心日信科技有限公司
 */
@Description(remark="台账明细报表service实现类",createDate="2013-01-15T00:00:00",author="彭登浩")
@Service("ledgerDeatilService")
public class LedgerDeatilServiceImpl extends AbsService<Object, Long> implements LedgerDeatilService {
	@Autowired
	private LedgerDeatilDaoInter ledgerDeatilDao;
	
	@Resource(name="applyService")
	private ApplyService applyService;
	
	@Resource(name="loanContractService")
	private LoanContractService loanContractService;
	
	@Resource(name="customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name="ecustomerService")
	private EcustomerService ecustomerService;
	
	@Resource(name="pledgeService")
	private PledgeService pledgeService;
	
	@Resource(name="mortgageService")
	private MortgageService mortgageService;
	
	@Resource(name="extPlanService")
	private ExtPlanService extPlanService;
	
	@Resource(name="ebankService")
	private EbankService ebankService;

	@Resource(name="countryService")
	private CountryService countryService;
	
	@Resource(name="provinceService")
	private ProvinceService provinceService;
	
	@Resource(name="cityService")
	private CityService cityService;
	
	@Resource(name="regionService")
	private RegionService regionService;
	
	@Resource(name="formdiyService")
	private FormdiyService formdiyService;
	
	@Resource(name="fieldValService")
	private FieldValService fieldValService;
	
	@Resource(name="varietyService")
	private VarietyService varietyService;
	
	@Resource(name="loanInvoceService")
	private LoanInvoceService loanInvoceService;
	
	@Resource(name="amountRecordsService")
	private AmountRecordsService amountRecordsService;
	
	@Resource(name="planService")
	private PlanService planService;

	@Resource(name="userService")
	private UserService userService;

	@Override
	public GenericDaoInter<Object, Long> getDao() {
		return ledgerDeatilDao;
	}

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,int pageSize) throws ServiceException {
		SHashMap<String, Object> dtParams = new SHashMap<String, Object>();
		Long sysId = params.getvalAsLng("sysId");
		params.remove((K) "sysId");
		dtParams.put("isenabled", SysConstant.OPTION_ENABLED);
		DataTable dt = null;
//		params.put("state", SqlUtil.LOGIC_GTEQ+SqlUtil.LOGIC+BussStateConstant.FIN_APPLY_STATE_3);
		DataTable applyDt = null;
		try {
			applyDt = ledgerDeatilDao.getResultList(params, offset, pageSize);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(applyDt!=null && applyDt.getRowCount()>0){
			StringBuffer sbFormId = new StringBuffer();
			StringBuffer sbCustomerId = new StringBuffer();
			StringBuffer sbEcustomerId = new StringBuffer();
			SHashMap<String, Object> custParams = new SHashMap<String, Object>();
			for(int i=0,count = applyDt.getRowCount();i<count;i++){
				Long id = Long.parseLong(applyDt.getString(i, "id"));
				sbFormId.append(id+",");
				Integer custType = applyDt.getInteger(i, "custType");
				Long customerId = Long.parseLong(applyDt.getString(i, "customerId"));
				if(custType==SysConstant.CUSTTYPE_0){
					sbCustomerId.append(customerId+",");
				}else{
					sbEcustomerId.append(customerId+",");
				}
			}
			List<CustomerInfoEntity> customerList = null;
			List<EcustomerEntity> ecustomerList = null;
			List<MortgageEntity> mortgageEntityList = null;
			List<PledgeEntity> pledgeList = null;
			List<ExtPlanEntity> extPlanList = null;
			DataTable loanDt = null;
			String strCustomerId = StringHandler.RemoveStr(sbCustomerId);
			if(StringHandler.isValidStr(strCustomerId)){
				custParams.put("id", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+strCustomerId);
				custParams.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
				customerList = customerInfoService.getEntityList(custParams);
			}
			String strEcustomerId = StringHandler.RemoveStr(sbEcustomerId);
			if(StringHandler.isValidStr(strEcustomerId)){
				custParams.clear();
				custParams.put("id", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+strEcustomerId);
				custParams.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
				ecustomerList = ecustomerService.getEntityList(custParams);
			}
				
			String applyId = StringHandler.RemoveStr(sbFormId);
			if(StringHandler.isValidStr(applyId)){
				dtParams.put("formId", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+applyId);
				loanDt = loanContractService.getSuperList(dtParams, offset, pageSize);
				pledgeList = pledgeService.getEntityList(dtParams);
				mortgageEntityList = mortgageService.getEntityList(dtParams);
				extPlanList = extPlanService.getEntityList(dtParams);
			}
			dt = serviceHandler(sysId,applyDt,loanDt,customerList,ecustomerList,
					mortgageEntityList,pledgeList,extPlanList);
		}
		
		return dt;
		
	}
	 
	/**
	 * 民汇台账明细DataTable 业务处理方法
	 * @param applyDt 需要返回的dt
	 * @param loanDt 借款合同dt
	 * @param customerList 个人客户List
	 * @param ecustomerList 企业客户List
	 * @param mortgageEntityList 抵押物List
	 * @param pledgeList 质押物list
	 * @param extPlanList 展期List
	 * @return 
	 * @throws ServiceException
	 */
	private DataTable serviceHandler(Long sysId,DataTable applyDt, DataTable loanDt,List<CustomerInfoEntity> customerList,
			List<EcustomerEntity> ecustomerList,List<MortgageEntity> mortgageEntityList,
			List<PledgeEntity> pledgeList,List<ExtPlanEntity> extPlanList) throws ServiceException{
		try{
			
		List<Object> dataSource = new ArrayList<Object>();
		String columnNames = "id,applyCode,code,name,cardNum,patentNumber,address," +
				"bankOrg,account,ecustomScale,contactor,contacttel," +
				"breedName,appAmount,payAmount,unAmount,rat," +
				"interest,mat,mgrAmount,mortName,mpVal," +
				"payDate,realDate,endDate,exteDate," +
				"managerName,comanager,referrals,loanId,ysRatMonth,ysRat,ysMatMonth,ysMat";
		DataTable dt = new DataTable(dataSource,columnNames);
		
		
		if(applyDt != null && applyDt.getRowCount() >0){
			for1:for(int i=0,count = applyDt.getRowCount();i<count;i++){
				Long id = null;
				String applyCode = "";
				String code = "";
				String name = "";
				String cardNum = "";
				String patentNumber = "";
				String address = "";
				String bankOrg = "";
				String account = "";
				String ecustomScale = "";
				String contactor = "";
				String contacttel = "";
				String breedName = "";
				BigDecimal appAmount = new BigDecimal(0).setScale(2);
				BigDecimal payAmount =  new BigDecimal(0).setScale(2);
				BigDecimal unAmount = new BigDecimal(0).setScale(2);
				
				String ysRatMonth = "";
				String ysRat =  "";
				String ysMatMonth = "";
				String ysMat =  "";
				
				BigDecimal rat =  new BigDecimal(0).setScale(2);
				BigDecimal interest =  new BigDecimal(0).setScale(2);
				BigDecimal mat =  new BigDecimal(0).setScale(2);	
				BigDecimal mgrAmount =  new BigDecimal(0).setScale(2);
				String mortName = "";
				BigDecimal mpVal =  new BigDecimal(0).setScale(2);
				String payDate = "";
				String realDate = "";
				String endDate = "";
				String exteDate = "";
				String managerName = "";
				String comanager = "";
				String referrals = "";
				Long loanId = null;
				Long LoanInvoceId = null;
				realDate = applyDt.getString(i, "realDate#yyyy-MM-dd");
				 id = Long.parseLong(applyDt.getString(i, "id"));
				 LoanInvoceId = Long.parseLong(applyDt.getString(i, "LoanInvoceId"));
				 applyCode = applyDt.getString(i, "code");
				 Integer custType = Integer.parseInt(applyDt.getString(i, "custType"));
				 Long customerId = Long.parseLong(applyDt.getString(i, "customerId"));
				 Long breed = Long.parseLong(applyDt.getString(i, "breed"));
				 VarietyEntity varietyEntity = varietyService.getEntity(breed);
				 if(varietyEntity != null){
					 breedName = varietyEntity.getName();
				 }
				 Long manager = Long.parseLong(applyDt.getString(i, "manager"));
				 managerName = userService.getEntity(manager).getEmpName();
				 referrals = applyDt.getString(i, "referrals");
				 SHashMap<String, Object> formDiyParams = new SHashMap<String, Object>();
				 
				 comanager = this.getValue(formDiyParams, id, "comanager", sysId, 
							comanager,FormDiyRecodeConstant.FROMDIY_APPLY);
				 
				 ysRatMonth = this.getValue(formDiyParams, LoanInvoceId, "ysRatMonth", sysId, 
						 ysRatMonth,FormDiyRecodeConstant.FROMDIY_LOANINVOCE);
				 ysRat = this.getValue(formDiyParams, LoanInvoceId, "ysRat", sysId, 
						 ysRat,FormDiyRecodeConstant.FROMDIY_LOANINVOCE);
				 ysMat = this.getValue(formDiyParams, LoanInvoceId, "ysMat", sysId, 
						 ysMat,FormDiyRecodeConstant.FROMDIY_LOANINVOCE);
				 ysMatMonth = this.getValue(formDiyParams, LoanInvoceId, "ysMatMonth", sysId, 
						 ysMatMonth,FormDiyRecodeConstant.FROMDIY_LOANINVOCE);
				 
				 if(loanDt != null && loanDt.getRowCount()>0){
					 for2:for(int j=0,countLoan = loanDt.getRowCount();j<countLoan;j++){
						 Long formId = null;
						 String fId = loanDt.getString(j,"formId");
						 if(StringHandler.isValidStr(fId)) {
							 formId = Long.parseLong(fId);
						 }else{
							 continue for2;
						 }
						 if(formId == id){
							 bankOrg = loanDt.getString(j, "borBank");
							 account =loanDt.getString(j, "borAccount");
							 
							 exteDate = loanDt.getString(j, "exteDate#yyyy-MM-dd");//这里有错的！
							 if(!StringHandler.isValidStr(exteDate)){
								 endDate = loanDt.getDateString(j, "endDate");
							 }else{
								 endDate = loanDt.getDateString(j, "oldendDate"); 
							 }
							 String loanContractId = loanDt.getString(j, "id");
							 if(StringHandler.isValidStr(loanContractId)){
								 loanId = Long.parseLong(loanContractId);
							 } 
							 appAmount = BigDecimalHandler.get(loanDt.getDouble(j,"appAmount"));
							 code = loanDt.getString(j, "code");
							 SHashMap<String, Object> loanInvoceParams = new SHashMap<String, Object>();
							 loanInvoceParams.put("formId", formId);
							 loanInvoceParams.put("contractId", loanId);
							 loanInvoceParams.put("isenabled", SysConstant.OPTION_ENABLED);
							 loanInvoceParams.put("auditState", BussStateConstant.LOANINVOCE_AUDITSTATE_2);
							 loanInvoceParams.put("state", BussStateConstant.LOANINVOCE_STATE_1);
							 List<LoanInvoceEntity> loanInvoceList = loanInvoceService.getEntityList(loanInvoceParams);
							 if(!loanInvoceList.isEmpty() && loanInvoceList.size()>0){
								 for(LoanInvoceEntity loanInvoce :loanInvoceList){
									 payAmount = BigDecimalHandler.add2BigDecimal(payAmount, loanInvoce.getPayAmount());
									 payDate = DateUtil.dateFormatToStr(loanInvoce.getPayDate());
//									 realDate = DateUtil.dateFormatToStr(loanInvoce.getRealDate());
								 }
							 }
							 unAmount = BigDecimalHandler.sub2BigDecimal(appAmount, payAmount);
							 SHashMap<String, Object> amountParams = new SHashMap<String, Object>();
							 amountParams.put("contractId", loanId);
							 amountParams.put("isenabled", SysConstant.OPTION_ENABLED);
							 List<AmountRecordsEntity> amountRecordsList = amountRecordsService.getEntityList(amountParams);
							 if(!amountRecordsList.isEmpty() && amountRecordsList.size()>0){
								 for(AmountRecordsEntity amountRecords : amountRecordsList){
									 rat = BigDecimalHandler.add2BigDecimal(rat, amountRecords.getRat());
									 mat = BigDecimalHandler.add2BigDecimal(mat, amountRecords.getMat());
								 }
							 }
							 List<PlanEntity> planList = planService.getEntityList(amountParams);
							 if(planList.size()>0 && !planList.isEmpty()){
								 for(PlanEntity plan : planList){
									 interest = BigDecimalHandler.add2BigDecimal(interest, plan.getInterest());
									 mgrAmount = BigDecimalHandler.add2BigDecimal(mgrAmount, plan.getMgrAmount());
								 }
							 }
						 }else{
							 continue for2;
						 }
					 }
				 }
				 if(!StringHandler.isValidStr(code)){
					 continue for1;
				 }
				 if(mortgageEntityList != null && !mortgageEntityList.isEmpty() && mortgageEntityList.size()>0){
					 	StringBuffer motName = new StringBuffer();
						 for(MortgageEntity mort: mortgageEntityList){
							Long formId =  mort.getFormId();
							 if(formId==id){
								 motName.append(mort.getName()+",");
								 mpVal =BigDecimalHandler.add2BigDecimal(mpVal,  mort.getMpVal());
							 }
						 } 
						 mortName = StringHandler.RemoveStr(motName); 
				 }
				 
				 if(custType == SysConstant.CUSTTYPE_0){
					 if(customerList != null && !customerList.isEmpty() && customerList.size()>0){
						 for(CustomerInfoEntity customerInfo: customerList){
							 Long customerInfoId = customerInfo.getId();
							 if(customerInfoId == customerId){
								cardNum = customerInfo.getCardNum();
								name = customerInfo.getName();
								String inArea  = customerInfo.getInArea();
								String inAddress = customerInfo.getInAddress();
								if(!StringHandler.isValidStr(inAddress)){
									inAddress ="";
								}
								address = this.getAddress(inArea)+inAddress;
								String custContactor = customerInfo.getContactor();
								if(!StringHandler.isValidStr(custContactor)){
									custContactor = customerInfo.getName();
								}
								contactor = custContactor;
								contacttel  = customerInfo.getContactTel();
							 }else{
								 continue;
							 }
						 }
					 }
				 }else{
					if(ecustomerList != null && !ecustomerList.isEmpty() && ecustomerList.size()>0){
						for(EcustomerEntity ecustomer: ecustomerList){
							Long ecustomerId = ecustomer.getId();
							 if(ecustomerId == customerId){
								name = ecustomer.getName();
								cardNum = ecustomer.getOrgcode();
								patentNumber = ecustomer.getLicence();
								String inArea  = ecustomer.getAddress();
								String inAddress = ecustomer.getInAddress();
								if(!StringHandler.isValidStr(inAddress)){
									inAddress ="";
								}
								address = this.getAddress(inArea)+inAddress;
								ecustomScale = this.getValue(formDiyParams, ecustomerId, "ecustomScale", sysId, 
										ecustomScale,FormDiyRecodeConstant.FORMDIY_ECUSTOMER_INFO);
								contactor = ecustomer.getContactor();
								contacttel = ecustomer.getContacttel(); 
							 }else{
								 continue;
							 }

						}
					}
				 }
				 Object[] datas = {
							id,applyCode,code,name,cardNum,patentNumber,
							address,bankOrg,account,ecustomScale,contactor,
							contacttel,breedName,appAmount,payAmount,unAmount,
							rat,interest,mat,mgrAmount,mortName,
							mpVal,payDate,realDate,endDate,exteDate,
							managerName,comanager,referrals,loanId,
							ysRatMonth,ysRat,ysMatMonth,ysMat
					};
					dt.addRowData(datas);
			}
		}
		return dt;
		}catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
		
	}
	private String getValue(SHashMap<String, Object> formDiyParams,Long formId,String fieldName,
			Long sysId ,Object val,String recode) throws ServiceException{
		formDiyParams.clear();
		formDiyParams.put("recode",recode);
		formDiyParams.put("sysid", sysId);
		formDiyParams.put("isenabled", SysConstant.OPTION_ENABLED);
		FormdiyEntity formDiy = formdiyService.getEntity(formDiyParams);
		 if(formDiy != null){
				Long formDiyId = formDiy.getId();
				formDiyParams.clear();
				formDiyParams.put("formdiyId", formDiyId);
				formDiyParams.put("formId", formId);
				formDiyParams.put("fieldName", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+"'"+fieldName+"'");
				FieldValEntity fieldvalEntry = fieldValService.getEntity(formDiyParams);
				if(fieldvalEntry != null){
					val = fieldvalEntry.getVal();
				}
			}
		return val.toString();
	}
	/**
	 * @param inArea现居住地区
	 * @param inAddress现居住详细地址
	 * @return 
	 */
	private String getAddress(String inArea) throws ServiceException{
			if(StringHandler.isValidStr(inArea)){
				String[] dq = inArea.split(",");
				inArea  = countryService.getEntity(Long.parseLong(dq[0])).getName();
				inArea  += provinceService.getEntity(Long.parseLong(dq[1])).getName();
				inArea  += cityService.getEntity(Long.parseLong(dq[2])).getName();
				inArea  += regionService.getEntity(Long.parseLong(dq[3])).getName();
			}else{
				inArea = "";
			}
		return inArea;
	}
	
	@Override
	public DataTable getDataSource(HashMap<String, Object> params) throws ServiceException {
		try {
			return getResultList(new SHashMap<String, Object>(params), -1, -1);
		} catch (ServiceException e) {
			throw new ServiceException(e);
		}
	}
}
