package com.cmw.service.impl.fininter.external.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.pool.DruidDataSource;
import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.core.util.SystemUtil;
import com.cmw.entity.crm.CustBaseEntity;
import com.cmw.entity.fininter.AcctGroupEntity;
import com.cmw.entity.fininter.BankAccountEntity;
import com.cmw.entity.fininter.BussFinCfgEntity;
import com.cmw.entity.fininter.CurrencyEntity;
import com.cmw.entity.fininter.FinBussObjectEntity;
import com.cmw.entity.fininter.FinSysCfgEntity;
import com.cmw.entity.fininter.ItemClassEntity;
import com.cmw.entity.fininter.SettleEntity;
import com.cmw.entity.fininter.SubjectEntity;
import com.cmw.entity.fininter.UserMappingEntity;
import com.cmw.entity.fininter.VoucherGroupEntity;
import com.cmw.entity.fininter.VoucherOplogEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.fininter.external.DbPool;
import com.cmw.service.impl.fininter.external.FinSysConstant;
import com.cmw.service.impl.fininter.external.FinSysFactory;
import com.cmw.service.impl.fininter.external.FinSysService;
import com.cmw.service.impl.fininter.external.JdbcTemplate;
import com.cmw.service.impl.fininter.external.ResultSetHandler;
import com.cmw.service.impl.fininter.external.generic.EntryModel;
import com.cmw.service.impl.fininter.external.generic.ItemModel;
import com.cmw.service.impl.fininter.external.generic.VoucherModel;
import com.cmw.service.inter.fininter.BussFinCfgService;
import com.cmw.service.inter.fininter.FinBussObjectService;
import com.cmw.service.inter.fininter.FinSysCfgService;
import com.cmw.service.inter.fininter.ItemClassService;
import com.cmw.service.inter.fininter.UserMappingService;
import com.cmw.service.inter.fininter.VoucherOplogService;

@Service(value="k3FinSysService")
public class K3FinSysServiceImpl extends JdbcTemplate implements FinSysService{
	private static DruidDataSource dataSouce = null;
	private static FinSysCfgEntity cfgEntity = null;
	private DbPool pool = DbPool.getInstance();
	
	@Resource(name="finSysCfgService")
	private FinSysCfgService finSysCfgService;
	@Resource(name="itemClassService")
	private ItemClassService itemClassService;
	@Resource(name="finBussObjectService")
	private FinBussObjectService finBussObjectService;
	@Resource(name="userMappingService")
	private UserMappingService userMappingService;
	@Resource(name="bussFinCfgService")
	private BussFinCfgService bussFinCfgService;
	@Resource(name="voucherOplogService")
	private VoucherOplogService voucherOplogService;
	static Logger logger = Logger.getLogger(K3FinSysServiceImpl.class);
	private void init() throws ServiceException{
//		if(DbPool.isReloadDs(FinSysFactory.FINSYS_K3_CODE) && null != dataSouce){
//			pool.removeDataSouce(dataSouce);
//			if(null != dataSouce) dataSouce = null;
//		}
		if(null == dataSouce){
			dataSouce = pool.getDataSource(FinSysFactory.FINSYS_K3_CODE);
			cfgEntity = pool.getEntity();
		}
	}
	
	@Override
	protected DruidDataSource getDataSource() throws ServiceException {
		init();
		return dataSouce;
	}

	@Override
	public void testConnection() throws ServiceException {
		Connection conn = null;
		try{
			conn = getConn();
			log.info("cfgEntity="+(cfgEntity == null));
		}catch(ServiceException e){
			e.printStackTrace();
			dataSouce = null;
			cfgEntity = null;
			throw new ServiceException(e);
		}finally{
			try {
				if(null != conn) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(null == cfgEntity) return;
		cfgEntity.setSpAccgroup((byte)1);
		cfgEntity.setSpCurrency((byte)1);
		cfgEntity.setSpCustomer((byte)1);
		cfgEntity.setSpImclass((byte)1);
		cfgEntity.setSpSettle((byte)1);
		cfgEntity.setSpUserName((byte)1);
		cfgEntity.setSpVhgroup((byte)1);
		finSysCfgService.updateEntity(cfgEntity);
	}
	
	
	
	@Override
	public List<UserMappingEntity> getUserMappings(SHashMap<String, Object> params) throws ServiceException {
		checkRefIds(params);
		final UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		final Long finsysId = getSysId();
		final String refIds = params.getvalAsStr("refIds");
		StringBuffer sb = new StringBuffer();
		sb.append("select A.FUserID as refId,A.FName as userName,B.FNumber as forgcode,")
		.append("B.FName as fsman from t_user A left join t_item B on A.FEmpID = B.FItemID ")
		.append(" where FUserID not in ("+refIds+")");
		 List<UserMappingEntity> list = getList(sb.toString(), new ResultSetHandler<UserMappingEntity>(){
			public UserMappingEntity execute(ResultSet rs) throws SQLException {
				Long refId = rs.getLong("refId");
				String fuserName = rs.getString("userName");
				String forgcode = rs.getString("forgcode");
				String fsman = rs.getString("fsman");
				UserMappingEntity entity = new UserMappingEntity();
				entity.setFinsysId(finsysId);
				entity.setUserId(0L);
				entity.setFuserName(fuserName);
				entity.setFsman(fsman);
				entity.setForgcode(forgcode);
				entity.setRefId(refId);
				BeanUtil.setCreateInfo(user, entity);
				return entity;
			}
		});
		return list;
	}

	@Override
	public List<AcctGroupEntity> getAcctGroups(SHashMap<String, Object> params) throws ServiceException {
		checkRefIds(params);
		final UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		final Long finsysId = getSysId();
		final String refIds = params.getvalAsStr("refIds");
		
		StringBuffer sb = new StringBuffer();
		sb.append("select A.FClassID as refId,A.FGroupID as code,A.FName as name ")
		.append(" from t_AcctGroup A where A.FClassID not in ("+refIds+")");
		 List<AcctGroupEntity> list = getList(sb.toString(), new ResultSetHandler<AcctGroupEntity>(){
			public AcctGroupEntity execute(ResultSet rs) throws SQLException {
				Long refId = rs.getLong("refId");
				String code = rs.getString("code");
				String name = rs.getString("name");
				AcctGroupEntity entity = new AcctGroupEntity();
				entity.setFinsysId(finsysId);
				entity.setCode(code);
				entity.setName(name);
				entity.setRefId(refId);
				BeanUtil.setCreateInfo(user, entity);
				return entity;
			}
		});
		return list;
	}

	@Override
	public List<SubjectEntity> getSubjects(SHashMap<String, Object> params) throws ServiceException {
		checkRefIds(params);
		final UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		final Long finsysId = getSysId();
		final String refIds = params.getvalAsStr("refIds");
		
		StringBuffer sb = new StringBuffer();
		sb.append("select A.FAccountID as refId,A.FNumber as code,A.FName as name,A.FLevel as levels,")
		.append("A.FDetail as detail,A.FRootID as rootId,A.FGroupID as groupId,A.FDC as dc,")
		.append("A.FCurrencyID as currencyId,A.FIsCash as isCash,A.FIsBank as isBank,B.FItemClassID as itemClassId ")
		.append(" from t_Account A left join t_ItemDetailV B on A.FDetailID = B.FDetailID  where A.FAccountID not in ("+refIds+")");
		 List<SubjectEntity> list = getList(sb.toString(), new ResultSetHandler<SubjectEntity>(){
			public SubjectEntity execute(ResultSet rs) throws SQLException {
				Long refId = rs.getLong("refId");
				String code = rs.getString("code");
				String name = rs.getString("name");
				Integer levels = rs.getInt("levels");
				Byte detail = rs.getByte("detail");
				Long rootId = rs.getLong("rootId");
				Long groupId = rs.getLong("groupId");
				Integer dc = rs.getInt("dc");
				if(null == dc) dc = 0;
				Long currencyId = rs.getLong("currencyId");
				Byte isCash = rs.getByte("isCash");
				Byte isBank = rs.getByte("isBank");
				Long itemClassId= rs.getLong("itemClassId");
				SubjectEntity entity = new SubjectEntity();
				entity.setFinsysId(finsysId);
				entity.setCode(code);
				entity.setName(name);
				entity.setRefId(refId);
				entity.setLevels(levels);
				entity.setDetail(detail);
				entity.setRootId(rootId);
				entity.setGroupId(groupId);
				entity.setDc(dc.byteValue());
				entity.setCurrencyId(currencyId);
				Integer atype = 0;
				if(null != isCash && isCash.byteValue() == 1){
					atype = 1;
				}
				if(null != isBank && isBank.byteValue() == 1){
					atype = 2;
				}
				entity.setAtype(atype);
				entity.setItemClassId(itemClassId);
				BeanUtil.setCreateInfo(user, entity);
				return entity;
			}
		});
		return list;
	}

	@Override
	public List<CurrencyEntity> getCurrencys(SHashMap<String, Object> params) throws ServiceException {
		checkRefIds(params);
		final UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		final Long finsysId = getSysId();
		final String refIds = params.getvalAsStr("refIds");
		
		StringBuffer sb = new StringBuffer();
		sb.append("select A.FCurrencyID as refId,A.FNumber as code,A.FName as name,A.FExchangeRate as erate,A.FScale as fscale ")
		.append(" from t_Currency A  where A.FCurrencyID not in ("+refIds+")");
		 List<CurrencyEntity> list = getList(sb.toString(), new ResultSetHandler<CurrencyEntity>(){
			public CurrencyEntity execute(ResultSet rs) throws SQLException {
				Long refId = rs.getLong("refId");
				String code = rs.getString("code");
				String name = rs.getString("name");
				Double erate = rs.getDouble("erate");
				Integer fscale = rs.getInt("fscale");
				CurrencyEntity entity = new CurrencyEntity();
				entity.setFinsysId(finsysId);
				entity.setCode(code);
				entity.setName(name);
				entity.setErate(erate);
				entity.setFscale(fscale);
				entity.setRefId(refId);
				BeanUtil.setCreateInfo(user, entity);
				return entity;
			}
		});
		return list;
	}

	@Override
	public List<ItemClassEntity> getItemClasses(SHashMap<String, Object> params) throws ServiceException {
		checkRefIds(params);
		final UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		final Long finsysId = getSysId();
		final String refIds = params.getvalAsStr("refIds");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select A.FItemClassID as refId,A.FNumber as code,A.FName as name from t_ItemClass A where A.FItemClassID not in ("+refIds+")");
		 List<ItemClassEntity> list = getList(sb.toString(), new ResultSetHandler<ItemClassEntity>(){
			public ItemClassEntity execute(ResultSet rs) throws SQLException {
				Long refId = rs.getLong("refId");
				String code = rs.getString("code");
				String name = rs.getString("name");
				ItemClassEntity entity = new ItemClassEntity();
				entity.setFinsysId(finsysId);
				entity.setCode(code);
				entity.setName(name);
				entity.setRefId(refId);
				BeanUtil.setCreateInfo(user, entity);
				return entity;
			}
		});
		return list;
	}

	@Override
	public List<SettleEntity> getSettles(SHashMap<String, Object> params) throws ServiceException {
		checkRefIds(params);
		final UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		final Long finsysId = getSysId();
		final String refIds = params.getvalAsStr("refIds");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select A.FItemID as refId,A.FNumber as code,A.FName as name from t_Settle A  where A.FItemID not in ("+refIds+")");
		 List<SettleEntity> list = getList(sb.toString(), new ResultSetHandler<SettleEntity>(){
			public SettleEntity execute(ResultSet rs) throws SQLException {
				Long refId = rs.getLong("refId");
				String code = rs.getString("code");
				String name = rs.getString("name");
				SettleEntity entity = new SettleEntity();
				entity.setFinsysId(finsysId);
				entity.setCode(code);
				entity.setName(name);
				entity.setRefId(refId);
				BeanUtil.setCreateInfo(user, entity);
				return entity;
			}
		});
		return list;
	}

	@Override
	public List<BankAccountEntity> getBankAccounts(SHashMap<String, Object> params) throws ServiceException {
		checkRefIds(params);
		final UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		final Long finsysId = getSysId();
		final String refIds = params.getvalAsStr("refIds");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select A.FItemID as refId,B.FNumber as code,A.FAcntName as name,")
		  .append("A.FAcntNumber as actNumber,A.FAcntBranch as bankName ")
		  .append(" from t_BK_Acnt A inner join t_item B on A.FItemID=B.FItemID where A.FItemID not in ("+refIds+")");
		 List<BankAccountEntity> list = getList(sb.toString(), new ResultSetHandler<BankAccountEntity>(){
			public BankAccountEntity execute(ResultSet rs) throws SQLException {
				Long refId = rs.getLong("refId");
				String code = rs.getString("code");
				String name = rs.getString("name");
				String actNumber = rs.getString("actNumber");
				String bankName = rs.getString("bankName");
				BankAccountEntity entity = new BankAccountEntity();
				entity.setFinsysId(finsysId);
				entity.setCode(code);
				entity.setName(name);
				entity.setActNumber(actNumber);
				entity.setBankName(bankName);
				entity.setRefId(refId);
				BeanUtil.setCreateInfo(user, entity);
				return entity;
			}
		});
		return list;
	}
	
	@Override
	public List<VoucherGroupEntity> getVoucherGroups(SHashMap<String, Object> params) throws ServiceException {
		checkRefIds(params);
		final UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		final Long finsysId = getSysId();
		final String refIds = params.getvalAsStr("refIds");
		
		StringBuffer sb = new StringBuffer();
		sb.append("select A.FGroupId as refId,A.FName as name from t_VoucherGroup A  where A.FGroupId not in ("+refIds+")");
		 List<VoucherGroupEntity> list = getList(sb.toString(), new ResultSetHandler<VoucherGroupEntity>(){
			public VoucherGroupEntity execute(ResultSet rs) throws SQLException {
				Long refId = rs.getLong("refId");
				String name = rs.getString("name");
				VoucherGroupEntity entity = new VoucherGroupEntity();
				entity.setFinsysId(finsysId);
				entity.setName(name);
				entity.setRefId(refId);
				BeanUtil.setCreateInfo(user, entity);
				return entity;
			}
		});
		return list;
	}

	@Override
	@Transactional
	public <T> SHashMap<String, Object> saveItem(T entity,SHashMap<String, Object> params) throws ServiceException {
		ItemClassEntity itemClassEntity = null;
		Long custBaseId = null;
		Long refId = null;
		if(isTheType(entity, FinSysConstant.ITEM_CLASS_CUSTOMER)){/*客户核算项*/
			itemClassEntity = getItemClassEntity(FinSysConstant.ITEM_CLASS_CUSTOMER);
			if(null == itemClassEntity) throw new ServiceException(FinSysConstant.ERROR_CUSTOMER_ITEMCLASSID_ISNULL);
			CustBaseEntity custBaseEntity = (CustBaseEntity)entity;
			custBaseId = custBaseEntity.getId();
			refId = saveCustomer(custBaseEntity,itemClassEntity,params);
		}
		SHashMap<String, Object> refMap = new SHashMap<String, Object>();
		refMap.put(custBaseId.toString(), refId.toString());
		return refMap;
	}

	private long saveCustomer(CustBaseEntity customer,ItemClassEntity itemClassEntity,SHashMap<String, Object> params) throws ServiceException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement orgPstmt = null;
		PreparedStatement logPstmt = null;
		PreparedStatement basePropPstmt = null;
		try {
			conn = getConn();
			conn.setAutoCommit(false);
		    UserMappingEntity userMappingEntity = getK3User(params);
			/*-------------- step 1 : 将客户数据插入到 t_Item(基础资料主表)中 ---------------*/
			pstmt = getItemInsertPstmt(conn);
			Object[] unDatas = setItemInsertPars(customer, itemClassEntity, pstmt);
			pstmt.executeUpdate();
			String uuid = (String)unDatas[1];
		    int pkVal = getPkVal(conn,uuid);
		    
			/*-------------- step 2 : 将客户数据插入到 t_Organization(K3 客户表)中 ---------------*/
		    orgPstmt = getOrganizationInsertPstmt(conn);
		    setOrgInsertPars(customer, pkVal, orgPstmt);
		    orgPstmt.executeUpdate();
		    
		    /*-------------- step 3 : 插入日志记录到 t_Log(K3 上机日志表)中 ---------------*/
		    logPstmt = getLogInsertPstmt(conn);
		    Long k3UserId = userMappingEntity.getRefId();
		    params.put("FUserID", k3UserId);
		    params.put("FFunctionID", "A00701");
		    params.put("FDescription", "新建核算项目:"+customer.getCode()+" 核算项目类别:客户");
		    params.put("FMachineName", SystemUtil.getLocalHostName());
		    params.put("FIPAddress", SystemUtil.getLocalHostIP());
		    setLogInsertPars(logPstmt, params);
		    logPstmt.executeUpdate();
		    
//		    
//		    /*-------------- step 4 : 插入数据到 t_BaseProperty(K3 基础资料相关属性)中 ---------------*/
		    params.put("FItemID", pkVal);
		    String fuserName = userMappingEntity.getFuserName();
		    params.put("FCreateUser", fuserName);
		    basePropPstmt = getBasePropertyInsertPstmt(conn);
		    setBasePropertyInsertPars(basePropPstmt,params);
		    basePropPstmt.executeUpdate();
		    conn.commit();
		    return (long)pkVal;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if(null != conn) conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new ServiceException(e);
		}finally{
			try {
				if(null != pstmt) pstmt.close();
				if(null != orgPstmt) orgPstmt.close();
				if(null != logPstmt) logPstmt.close();
				if(null != basePropPstmt) basePropPstmt.close();
				if(null != conn) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 设置基本属性表插入参数值
	 * @param customer
	 * @param itemClassEntity
	 * @param pstmt
	 * @return 返回客户基础资料ID和uuid 数组
	 * @throws SQLException
	 */
	private void setBasePropertyInsertPars(PreparedStatement pstmt,SHashMap<String, Object> params)throws SQLException {
		pstmt.setInt(1, params.getvalAsInt("FItemID"));
		String FCreateDate = DateUtil.dateFormatToStr(DateUtil.DATE_TIME_FORMAT, new Date());
		pstmt.setString(2, FCreateDate);
		pstmt.setString(3, params.getvalAsStr("FCreateUser"));
	}
	
	/**
	 * 设置日志表插入参数值
	 * @param customer
	 * @param itemClassEntity
	 * @param pstmt
	 * @return 返回客户基础资料ID和uuid 数组
	 * @throws SQLException
	 */
	private void setLogInsertPars(PreparedStatement pstmt,SHashMap<String, Object> params)throws SQLException {
		pstmt.setString(1, params.getvalAsStr("FUserID"));
		pstmt.setString(2, params.getvalAsStr("FFunctionID"));
		pstmt.setString(3, params.getvalAsStr("FDescription"));
		pstmt.setString(4, params.getvalAsStr("FMachineName"));
		pstmt.setString(5, params.getvalAsStr("FIPAddress"));
	}
	
	/**
	 * 设置客户主表插入参数值
	 * @param customer
	 * @param itemClassEntity
	 * @param pstmt
	 * @return 返回客户基础资料ID和uuid 数组
	 * @throws SQLException
	 */
	private void setOrgInsertPars(CustBaseEntity customer,int fItemId, PreparedStatement pstmt)throws SQLException {
		String fName = customer.getName();
		String fNumber = customer.getCode();
		pstmt.setString(1, fNumber);
		pstmt.setString(2, fNumber);
		pstmt.setString(3, fName);
		pstmt.setString(4, fItemId+"");
	}
	
	/**
	 * 设置基础资料主表插入参数值
	 * @param customer
	 * @param itemClassEntity
	 * @param pstmt
	 * @return 返回客户基础资料ID和uuid 数组
	 * @throws SQLException
	 */
	private Object[] setItemInsertPars(CustBaseEntity customer,ItemClassEntity itemClassEntity, PreparedStatement pstmt)
			throws SQLException {
		String fItemClassID = itemClassEntity.getRefId().toString();
		Long baseId = customer.getId();
		String fName = customer.getName();
		String fNumber = customer.getCode();
		String uuid =  UUID.randomUUID().toString();
		pstmt.setString(1, fItemClassID);
		pstmt.setString(2, "0");
		pstmt.setString(3, "1");
		pstmt.setString(4, fName);
		pstmt.setString(5, fNumber);
		pstmt.setString(6, fNumber);
		pstmt.setString(7, fNumber);
		pstmt.setString(8, "1");
		pstmt.setString(9, uuid);
		pstmt.setString(10, "0");
		return new Object[]{baseId,uuid};
	}

	private PreparedStatement getOrganizationInsertPstmt(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();
	    sb.append("INSERT INTO t_Organization ")
	    .append("(FStatus,FRegionID,FTrade,FIsCreditMgr,FSaleMode,")
	    .append("FValueAddRate,FCarryingAOS,FTypeID,FSaleID,FStockIDKeep,")
	    .append("FCoSupplierID,FCyID,FSetID,FARAccountID,FPreAcctID,")
	    .append("FOtherARAcctID,FPayTaxAcctID,FAPAccountID,FPreAPAcctID,FOtherAPAcctID,")
	    .append("FfavorPolicy,Fdepartment,Femployee,FlastTradeAmount,FmaxDealAmount,")
	    .append("FminForeReceiveRate,FminReserverate,FdebtLevel,FPayCondition,FShortNumber,")
	    .append("FNumber,FName,FParentID,FItemID) ")
	    .append(" VALUES ")
	    .append("(1072,0,0,0,1057,")
	    .append("17,0,0,0,0,")
	    .append("0,0,0,0,0,")
	    .append("0,0,0,0,0,")
	    .append("0,0,0,0,0,")
	    .append("1,1,0,0,?,")
	    .append("?,?,0,?)");
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		return pstmt;
	}
	
	private PreparedStatement getItemInsertPstmt(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO t_Item ")
		  .append("(FItemClassID,FParentID,FLevel,FName,FNumber, ")
		  .append("FShortNumber,FFullNumber,FDetail,UUID,FDeleted)  ")
		  .append(" VALUES  ")
		  .append("(?,?,?,?,?,")
		  .append("?,?,?,?,?)");
		
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		return pstmt;
	}

	/**
	 * 获取财务业务映射系统ID
	 * @return
	 * @throws ServiceException
	 */
	private Long getSysId() throws ServiceException{
		init();
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("isenabled", SysConstant.OPTION_ENABLED);
		map.put("finsysId", cfgEntity.getId());
		BussFinCfgEntity bussFinCfgEntity = bussFinCfgService.getEntity(map);
		if(null == bussFinCfgEntity) throw new ServiceException(FinSysConstant.ERROR_FINSYSID_ISNULL);
		Long finsysId = bussFinCfgEntity.getId();
		return finsysId;
	}
	
	/**
	 * 获取需要排除的财务业务映射系统ID
	 * @return
	 * @throws ServiceException
	 */
	private String getRefIds(String objectName) throws ServiceException{
		String refIds = itemClassService.getRefIds(objectName);
		return refIds;
	}
	
	
	
	private UserMappingEntity getK3User(SHashMap<String, Object> params) throws ServiceException {
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("finsysId", getSysId());
		UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		Long userId = user.getUserId();
		params.put("userId", userId);
		UserMappingEntity userMappingEntity = userMappingService.getEntity(map);
		if(null == userMappingEntity) throw new ServiceException(FinSysConstant.ERROR_USERMAPING_OBJECT_ISNULL);
		return userMappingEntity;
	}
	
	private PreparedStatement getBasePropertyInsertPstmt(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("Insert Into t_BaseProperty(FTypeID, FItemID,FCreateDate, FCreateUser) VALUES (3,?,?,?)");
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		return pstmt;
	}
	
	private PreparedStatement getLogInsertPstmt(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO t_Log (FDate,FUserID,FFunctionID,FStatement,FDescription,FMachineName,FIPAddress)" +
				" VALUES " +
				"(getdate(),?,?,5,?,?,?)");
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		return pstmt;
	}
	
	/**
	 * 获取主键值
	 * @param pstmt
	 * @return
	 * @throws SQLException
	 */
	private int getPkVal(Connection conn,String uuid) throws SQLException {
		int id = 0;
		String sql = "select FItemID from t_Item where uuid ='"+uuid+"' ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
		  id = rs.getInt(1);
		}
		pstmt.close();
		return id;
	}

	/**
	 * 获取主键值
	 * @param conn Connection 对象
	 * @param tabName 要获取主键值的表名
	 * @param pkCmnName 主键列名
	 * @param uuid 用来过滤的UUID值
	 * @return 以字符串形式返回ID值
	 * @throws SQLException
	 */
	private String getPkVal(Connection conn,String tabName,String pkCmnName,String uuid) throws SQLException {
		String id = null;
		String sql = "select "+pkCmnName+" from "+tabName+" where uuid ='"+uuid+"' ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
		  id = rs.getString(1);
		}
		pstmt.close();
		return id;
	}
	
	/**
	 * 获取主键值
	 * @param conn Connection 对象
	 * @param tabName 要获取主键值的表名
	 * @param pkCmnName 主键列名
	 * @param whereStr where 条件值
	 * @return 以字符串形式返回ID值
	 * @throws SQLException
	 */
	private String getPkValByWhere(Connection conn,String tabName,String pkCmnName,String whereStr) throws SQLException {
		String id = null;
		String sql = "select max("+pkCmnName+") from "+tabName+" where 1=1 ";
		if(StringHandler.isValidStr(whereStr)){
			sql += whereStr;
		}
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
		  id = rs.getString(1);
		}
		pstmt.close();
		return id;
	}
	
	
	private ItemClassEntity getItemClassEntity(String bussObject) throws ServiceException {
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("className", bussObject);
		FinBussObjectEntity bussObjEntity = finBussObjectService.getEntity(map);
		if(null == bussObjEntity) throw new ServiceException(FinSysConstant.ERROR_BUSSOBJENTITY_ISNULL);
		map.clear();
		map.put("bussObjectId", bussObjEntity.getId());
		ItemClassEntity itemClassEntity = itemClassService.getEntity(map);
		return itemClassEntity;
	}

	@Override
	public <T> SHashMap<String, Object> saveItems(List<T> entitys,SHashMap<String, Object> params) throws ServiceException {
		ItemClassEntity itemClassEntity = null;
		Long custBaseId = null;
		Long refId = null;
		if(null == entitys || entitys.size() == 0) return null;
		SHashMap<String, Object> refMap = new SHashMap<String, Object>();
		T firstEntity = entitys.get(0);
		if(isTheType(firstEntity, FinSysConstant.ITEM_CLASS_CUSTOMER)){/*客户核算项*/
			itemClassEntity = getItemClassEntity(FinSysConstant.ITEM_CLASS_CUSTOMER);
			if(null == itemClassEntity) throw new ServiceException(FinSysConstant.ERROR_CUSTOMER_ITEMCLASSID_ISNULL);
			for(T entity : entitys){
				CustBaseEntity custBaseEntity = (CustBaseEntity)entity;
				custBaseId = custBaseEntity.getId();
				refId = saveCustomer(custBaseEntity,itemClassEntity,params);
				refMap.put(custBaseId.toString(), refId.toString());
			}
		}
		return refMap;
	}

	private void checkRefIds(SHashMap<String, Object> params) throws ServiceException {
		String objectName = params.getvalAsStr("objectName");
		if(!StringHandler.isValidStr(objectName)) throw new ServiceException(FinSysConstant.ERROR_OBJECTNAME_ISNULL);
		String refIds = getRefIds(objectName);
		params.put("refIds", refIds);
		UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		if(!StringHandler.isValidObj(user)) throw new ServiceException(FinSysConstant.ERROR_USER_ISNULL);
//		Long finsysId = params.getvalAsLng("finsysId");
//		if(!StringHandler.isValidObj(finsysId)) throw new ServiceException(FinSysConstant.ERROR_FINSYSID_ISNULL);
	}
	
	/**
	 * 对象是否是指定的类型
	 * @param obj 比较的对象
	 * @param theType 类型
	 * @return 如果 obj 对象的类型与 参数 theType 相同，则返回 true , 否则：false
	 */
	private <T> boolean isTheType(T obj,String theType){
		String clzName = obj.getClass().getSimpleName();
		return clzName.equals(theType);
	}

	@Override
	public List<VoucherOplogEntity> saveVouchers(List<VoucherModel> vouchers, SHashMap<String, Object> params) throws ServiceException {
		if(null == vouchers || vouchers.size() == 0) return null;
		UserMappingEntity userMappingEntity = getK3User(params);
		params.put("userMapping", userMappingEntity);
		List<VoucherOplogEntity> oplogList = new ArrayList<VoucherOplogEntity>();
		Connection conn = null;
		conn = getConn();
		try {
			conn.setAutoCommit(false);
			log.info("=========== 开始生成凭证（凭证数："+vouchers.size()+"） ===============");
			int index = 0;
			long endTime = 0l;
			Date totalStartDate = new Date();
			for(VoucherModel voucher : vouchers){
				index++;
				log.info("  开始生成第"+index+"张凭证...");
				Date startDate = new Date();
		    	VoucherOplogEntity opLogEntity = saveVoucher(voucher, params,conn);
		    	oplogList.add(opLogEntity);
		    	Date endDate = new Date();
		    	endTime = endDate.getTime() - startDate.getTime();
		    	log.info("  第"+index+"张凭证处理完成(耗时："+endTime+"毫秒)...");
		    }
			Date totalEndDate = new Date();
			endTime = totalEndDate.getTime() - totalStartDate.getTime();
			log.info("=========== 凭证生成完成完成(耗时："+endTime+"毫秒) ===============");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}finally{
			try {
				if(null != conn) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//	    for(VoucherModel voucher : vouchers){
//	    	VoucherOplogEntity opLogEntity = saveVoucher(voucher, params);
//	    	oplogList.add(opLogEntity);
//	    }
	    //***----- step 2 : 保存凭证日志  ----**//
	    if(null != oplogList && oplogList.size() > 0) voucherOplogService.batchSaveEntitys(oplogList);
		return oplogList;
	}
	
	public VoucherOplogEntity saveVoucher(VoucherModel voucher, SHashMap<String, Object> params) throws ServiceException {
		Connection conn = null;
		conn = getConn();
		try {
			conn.setAutoCommit(false);
			return saveVoucher(voucher,params,conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}finally{
			try {
				if(null != conn) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public VoucherOplogEntity saveVoucher(VoucherModel voucher, SHashMap<String, Object> params,Connection conn) throws ServiceException {
		PreparedStatement voucherPstmt = null;
		PreparedStatement itemDetailvPstmt = null;
		PreparedStatement entryPstmt = null;
		PreparedStatement logPstmt = null;
		PreparedStatement funcControlPstmt = null;
		VoucherOplogEntity opLog = new VoucherOplogEntity();
		Integer status = BussStateConstant.VOUCHEROPLOG_STATUS_0;
		Integer errCode = BussStateConstant.VOUCHEROPLOG_ERRCODE_0;
		String reason = null;
		try {
			UserMappingEntity userMappingEntity = (UserMappingEntity)params.getvalAsObj("userMapping");
			Long k3UserId = userMappingEntity.getRefId();
			log.info("生成凭证共需6步...");
			/*-------------- step 1 : 将凭证数据插入到 t_Voucher(凭证主表)中 ---------------*/
			log.info("step 1 : 将凭证数据插入到 t_Voucher(凭证主表)中 ...");
			setFnumber(voucher, conn);/*设置凭证号*/
			log.info("step 1 : 将凭证数据插入到 t_Voucher(凭证主表)中 ,执行成功...");
			
		    voucherPstmt = getVoucherInsertPstmt(conn);	
		    setVoucherPars(voucherPstmt, voucher, params);
		    voucherPstmt.executeUpdate();
		    String uuid = voucher.getUuid();
		    String voucherId = getPkVal(conn,"t_Voucher","FVoucherID",uuid);
		    voucher.setVoucherId(voucherId);
		    params.put("voucherId", voucherId);
		    List<EntryModel> entrys = voucher.getEntrys();
		    int entryId = 0;
		    for(EntryModel entry : entrys){
		    	Integer dc = entry.getDc();
		    	String dcName = null == dc ? "未知" : (dc.intValue() == 0 ? "贷方":"借方");
		    	log.info("当前余额方向："+dcName);
		    	String detailId = "0";
		    	ItemModel item = entry.getItem();
		    	if(null != item){/*有核算项*/
		    		/*-------------- step 2 : 如果有核算项，则将核算项数据插入到 t_ItemDetail(K3 核算项目使用详情横表)中 ---------------*/
		    		log.info("step 2 : 如果有核算项，则将核算项数据插入到 t_ItemDetail(K3 核算项目使用详情横表)中 ...");
		    		insertItemDetail(conn, item, params);
		    		log.info("step 2 : 如果有核算项，则将核算项数据插入到 t_ItemDetail(K3 核算项目使用详情横表)中,执行成功 ...");
		    		detailId = getPkValByWhere(conn,"t_ItemDetail","FDetailID"," and F2030='"+voucherId+"'");
		    		params.put("detailId", detailId);
		    		
		    		/*-------------- step 3 : 如果有核算项，则将核算项数据插入到 t_ItemDetailV (核算项目使用详情纵表)中 ---------------*/
		    		log.info("step 3 : 如果有核算项，则将核算项数据插入到 t_ItemDetailV (核算项目使用详情纵表)中");
		    		itemDetailvPstmt = getItemDetailVInsertPstmt(conn);
	    			setItemDetailVPars(itemDetailvPstmt,item,params);
	    			itemDetailvPstmt.executeUpdate();
	    			log.info("step 3 : 如果有核算项，则将核算项数据插入到 t_ItemDetailV (核算项目使用详情纵表)中,执行成功 ...");
		    	}
		    
		    	entry.setVoucherId(Long.parseLong(voucherId));
		    	entry.setEntryId((long)entryId);
		    	entry.setDetailId(Long.parseLong(detailId));
		    	entryId++;
			    /*-------------- step 4 : 将分录数据插入到 t_VoucherEntry (凭证分录表)中 ---------------*/
		    	log.info("step 4 : 将分录数据插入到 t_VoucherEntry (凭证分录表)中...");
		    	entryPstmt = getEntryInsertPstmt(conn);
			    setEntryPars(entryPstmt, entry);
			    entryPstmt.executeUpdate();
			    log.info("step 4 : 将分录数据插入到 t_VoucherEntry (凭证分录表)中,执行成功...");
			    
			    /*-------------- step 5 : 插入日志记录到 t_Log(K3 上机日志表)中 (有多少分录，就插入多少条日志记录)---------------*/
				log.info("step 5 : 插入日志记录到 t_Log(K3 上机日志表)中 ...");
				logPstmt = getLogInsertPstmt(conn);
			    String desc = "增加凭证:"+entry.getSettleType()+"-"+voucher.getPeriod()+" 会计年度:"+voucher.getYear()+" 会计期间:"+voucher.getPeriod();
			    params.put("FUserID", k3UserId);
			    params.put("FFunctionID", "A00201");
			    params.put("FDescription", desc);
			    params.put("FMachineName", SystemUtil.getLocalHostName());
			    params.put("FIPAddress", SystemUtil.getLocalHostIP());
			    setLogInsertPars(logPstmt, params);
			    logPstmt.executeUpdate();
			    log.info("step 5 : 插入日志记录到 t_Log(K3 上机日志表)中 ,执行成功...");
		    }
		    
		    /*-------------- step 6 : 插入数据到t_FuncControl(K3 网络控制表)中 ---------------*/
		    funcControlPstmt = getFuncControlInsertPstmt(conn);
		    SHashMap<String, Object> funcPars = new SHashMap<String, Object>();
		    funcPars.put("FYear", voucher.getYear());
		    funcPars.put("FPeriod", voucher.getPeriod());
		    funcPars.put("FUserID", k3UserId);
		    funcPars.put("FRowID", voucher.getEntryCount());
		    funcPars.put("FStation", SystemUtil.getLocalHostName()+"[0]");
		    setFuncControlPars(funcControlPstmt, funcPars);
		    funcControlPstmt.executeUpdate();
		    conn.commit();
		} catch (SQLException e) {
			status = BussStateConstant.VOUCHEROPLOG_STATUS_1;
			errCode = BussStateConstant.VOUCHEROPLOG_ERRCODE_4;
			reason = e.getMessage();
			e.printStackTrace();
			try {
				if(null != conn) conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				if(null != voucherPstmt) voucherPstmt.close();
				if(null != itemDetailvPstmt) itemDetailvPstmt.close();
				if(null != entryPstmt) entryPstmt.close();
				if(null != funcControlPstmt) funcControlPstmt.close();
				if(null != logPstmt) logPstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		/*-------------- step 6 : 添加凭证日志记录 ---------------*/
		Long sysId= voucher.getSysId();
		Long vtempId = voucher.getVtempId();
		Long amountLogId = voucher.getAmountLogId();
		opLog.setSysId(sysId);
		opLog.setVtempId(vtempId);
		opLog.setAmountLogId(amountLogId);
		opLog.setStatus(status);
		opLog.setErrCode(errCode);
		opLog.setReason(reason);
		UserEntity userEntity = (UserEntity)params.get(SysConstant.USER_KEY);
		BeanUtil.setCreateInfo(userEntity, opLog);
		return opLog;
	}
	
	/**
	 * 设置凭证的凭证编号为（凭证最大ID+1）
	 * @param voucher
	 * @param conn
	 * @throws SQLException
	 */
	private void setFnumber(VoucherModel voucher, Connection conn)
			throws SQLException {
		String maxVoucherId = getPkValByWhere(conn,"t_Voucher","FVoucherID",null);
		if(!StringHandler.isValidStr(maxVoucherId)) maxVoucherId = "0";
		String fnumber = (Integer.parseInt(maxVoucherId)+1)+"";
		voucher.setNumber(fnumber);
	}
	
	/**
	 * 获取凭证插入SQL预处理对象
	 * @param conn Connection 对象
	 * @return 返回 PreparedStatement对象
	 * @throws SQLException
	 */
	private PreparedStatement getVoucherInsertPstmt(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO t_Voucher ( ")
		  .append("FDate,FTransDate,FYear,FPeriod,FGroupID,")
		  .append("FNumber,FReference,FExplanation,FAttachments,FEntryCount,")
		  .append("FDebitTotal,FCreditTotal,FInternalInd,FChecked,FPosted,")
		  .append("FPreparerID,FCheckerID,FPosterID,FCashierID,FHandler,")
		  .append("FObjectName,FParameter,FSerialNum,FTranType,FOwnerGroupID,UUID")
		  .append(") VALUES ( ")
		  .append("?,?,?,?,?,")
		  .append("?,?,?,0,?,")
		  .append("?,?,NULL,0,0,")
		  .append("?,-1,-1,-1,NULL,")
		  .append("NULL,NULL,?,0,0,?)");
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		return pstmt;
	}

	/**
	 * 设置凭证表插入参数值
	 * @param voucher	凭证对象
	 * @param pstmt
	 * @throws SQLException
	 */
	private void setVoucherPars(PreparedStatement pstmt,VoucherModel voucher,SHashMap<String, Object> params)
			throws SQLException {
		Date regDate = voucher.getRegDate();
		Date transDate = voucher.getTransDate();
		Integer year = voucher.getYear();
		Integer period = voucher.getPeriod();
		String groupId = voucher.getGroupId();
		
		String number = voucher.getNumber();
		String reference = voucher.getReference();
		String explanation = voucher.getExplanation();
		Integer entryCount = voucher.getEntryCount();
		
		Double debitTotal = voucher.getDebitTotal();
		Double creditTotal = voucher.getCreditTotal();
		UserMappingEntity userMapping = (UserMappingEntity)params.getvalAsObj("userMapping");
		String preparerID = userMapping.getRefId().toString();
		String uuid = voucher.getUuid();
		pstmt.setDate(1,new java.sql.Date(regDate.getTime()));
		pstmt.setDate(2,new java.sql.Date(transDate.getTime()));
		pstmt.setInt(3,year);
		pstmt.setInt(4,period);
		pstmt.setString(5,groupId);
		
		pstmt.setString(6,number);
		pstmt.setString(7,reference);
		pstmt.setString(8,explanation);
		pstmt.setInt(9,entryCount);
		
		pstmt.setDouble(10,debitTotal);
		pstmt.setDouble(11,creditTotal);
		pstmt.setString(12,preparerID);
		pstmt.setString(13,"0");
		pstmt.setString(14,uuid);
	}
	
	/**
	 * 获取核算项目使用详情横表插入SQL预处理对象
	 * @param conn Connection 对象
	 * @return 返回 PreparedStatement对象
	 * @throws SQLException
	 */
	private void insertItemDetail(Connection conn,ItemModel item,SHashMap<String, Object> params) throws SQLException {
		String itemCmn = "F1";
		Integer itemClassType = item.getItemClassType();
		switch (itemClassType.intValue()) {
		case ItemModel.ITEMCLASSTYPE_2:/*当核算项为银行时-->银行字段列*/
			itemCmn = "F2004";
			break;
		default:
			break;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("Insert Into t_ItemDetail(FDetailCount,"+itemCmn+",F2030) values(?,?,?)");
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		String itemId = item.getItemId();
		String voucherId = params.getvalAsStr("voucherId");
		pstmt.setInt(1, 1);
		pstmt.setString(2,itemId);
		pstmt.setString(3,voucherId);
		pstmt.executeUpdate();
		pstmt.close();
	}

	/**
	 * 获取核算项目使用详情纵表插入SQL预处理对象
	 * @param conn Connection 对象
	 * @return 返回 PreparedStatement对象
	 * @throws SQLException
	 */
	private PreparedStatement getItemDetailVInsertPstmt(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("Insert Into t_ItemDetailV(FDetailID,FItemClassID,FItemID) values(?,?,?)");
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		return pstmt;
	}

	/**
	 * 设置核算项目使用详情纵表插入参数值
	 * @param voucher	凭证对象
	 * @param pstmt
	 * @throws SQLException
	 */
	private void setItemDetailVPars(PreparedStatement pstmt,ItemModel item,SHashMap<String, Object> params)
			throws SQLException {
		String detailId = params.getvalAsStr("detailId");
		String itemClassId = item.getItemClassId();
		String itemId = item.getItemId();
		pstmt.setString(1, detailId);
		pstmt.setString(2,itemClassId);
		pstmt.setString(3,itemId);
	}
	
	/**
	 * 获取分录表插入SQL预处理对象
	 * @param conn Connection 对象
	 * @return 返回 PreparedStatement对象
	 * @throws SQLException
	 */
	private PreparedStatement getEntryInsertPstmt(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO t_VoucherEntry ")
		.append("(FVoucherID,FEntryID,FExplanation,FAccountID,FCurrencyID,")
		.append("FExchangeRate,FDC,FAmountFor,FAmount,FQuantity,")
		.append("FMeasureUnitID,FUnitPrice,FInternalInd,FAccountID2,FSettleTypeID,")
		.append("FSettleNo,FCashFlowItem,FTaskID,FResourceID,FTransNo,FDetailID) ")
		.append(" VALUES (?,?,?,?,?,")
		.append("?,?,?,?,0,")
		.append("0,0,NULL,?,?,")
		.append("NULL,0,0,0,NULL,?)");
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		return pstmt;
	}

	/**
	 * 设置分录表插入参数值
	 * @param voucher	凭证对象
	 * @param pstmt
	 * @throws SQLException
	 */
	private void setEntryPars(PreparedStatement pstmt,EntryModel entry)
			throws SQLException {
		Long voucherId = entry.getVoucherId();
		Long entryId = entry.getEntryId();
		String explanation = entry.getExplanation();
		String accountId = entry.getAccountId();
		String currencyId = entry.getCurrencyId();
		
		float exchangeRate = entry.getExchangeRate();
		Integer dc = entry.getDc();
		Double amountFor = entry.getAmountFor();
		Double amount = entry.getAmount();
		String accountId2 = entry.getAccountId2();
		String settleTypeId = entry.getSettleTypeId();
		
		String detailId = entry.getDetailId().toString();
		pstmt.setLong(1, voucherId);
		pstmt.setLong(2,entryId);
		
		pstmt.setString(3,explanation);
		pstmt.setString(4,accountId);
		pstmt.setString(5,currencyId);
		
		pstmt.setFloat(6,exchangeRate);
		pstmt.setInt(7,dc);
		pstmt.setDouble(8,amountFor);
		pstmt.setDouble(9,amount);
		pstmt.setString(10,accountId2);
		pstmt.setString(11,settleTypeId);
		pstmt.setString(12,detailId);
	}
	
	
	/**
	 * 获取网络控制表插入SQL预处理对象
	 * @param conn Connection 对象
	 * @return 返回 PreparedStatement对象
	 * @throws SQLException
	 */
	private PreparedStatement getFuncControlInsertPstmt(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("Insert Into t_FuncControl( ")
		.append("FYear,FPeriod,FFuncID,FUserID,FRowID,FBizType,FStation,FTime")
		.append(") VALUES ( ")
		.append("?,?,42,?,?,'0',?,?)");
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		return pstmt;
	}

	/**
	 * 设置网络控制表表插入参数值
	 * @param voucher	凭证对象
	 * @param pstmt
	 * @throws SQLException
	 */
	private void setFuncControlPars(PreparedStatement pstmt,SHashMap<String, Object> params) throws SQLException {
		pstmt.setString(1,params.getvalAsStr("FYear"));
		pstmt.setString(2,params.getvalAsStr("FPeriod"));
		pstmt.setString(3,params.getvalAsStr("FUserID"));
		pstmt.setString(4,params.getvalAsStr("FRowID"));
		pstmt.setString(5,params.getvalAsStr("FStation"));
		pstmt.setDate(6, new java.sql.Date(System.currentTimeMillis()));
	}
	
	@Override
	public void deleteVouchers(String ids, SHashMap<String, Object> params) throws ServiceException {
		
	}
}
