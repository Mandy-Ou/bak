package com.cmw.service.impl.dialog;


import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.sys.CommonDaoInter;
import com.cmw.entity.sys.GvlistEntity;
import com.cmw.service.inter.sys.GvlistService;


/**
 * 打印  Service实现类
 * @author cmw
 * @date 2013-11-17T00:00:00
 */
@Description(remark="客户住宅信息业务实现类",createDate="2012-12-15T00:00:00",author="pdh")
@Service("printService")
public class PrintServiceImpl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private CommonDaoInter commonDao;
	
	@Resource(name="gvlistService")
	private GvlistService gvlistService;
	
	/**
	 * 获取打印数据源
	 * @param loadType
	 * @param dataCode
	 * @param cfgParams
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public DataTable getPrintDs(Integer loadType,String dataCode,SHashMap<String, Object> cfgParams,SHashMap<String, Object> params)
	throws ServiceException{
		try{
			DataTable ds = null;
			String columnNames =  cfgParams.getvalAsStr("columnNames");
			switch (loadType.intValue()) {
			case SysConstant.LOADTYPE_1:{
				if(!StringHandler.isValidStr(columnNames)) throw new ServiceException("当 loadType=1 时，必须提供\"columnNames\" 参数!");
				ds = commonDao.getDatasBySql(dataCode, columnNames);
				break;
			}case SysConstant.LOADTYPE_2:{
				if(!StringHandler.isValidStr(columnNames)) throw new ServiceException("当 loadType=1 时，必须提供\"columnNames\" 参数!");
				ds = commonDao.getDatasByHql(dataCode, columnNames);
				break;
			}case SysConstant.LOADTYPE_3:{
				Object result = BeanUtil.invokeMethod(this, dataCode, new Object[]{params});
				if(null != result) ds = (DataTable)result;
				break;
			}default:
				break;
			}
			return ds;
		}catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	public DataTable getContractDs(SHashMap<String, Object> params) throws ServiceException{
		try {
			Long contractId = params.getvalAsLng("contractId");
			StringBuffer sb = new StringBuffer();
			sb.append("select A.id,A.code,B.name as custName,B.inArea,B.inAddress,")
			.append(" C.reason,C.payremark,C.loanType,D.name as breedName,")
			.append(" A.appAmount,A.yearLoan,A.monthLoan,A.dayLoan,A.payDate,A.endDate,F.name as payType,")
			.append(" A.setdayType,A.payDay,A.rateType,A.rate")
			.append(" from fc_LoanContract A left join crm_CustomerInfo B ")
			.append(" ON A.customerId = B.id left join fc_Apply C ON A.formId=C.id")
			.append(" left join ts_variety D ON C.breed=D.id ")
			.append(" left join fc_PayType F ON A.payType=F.code ")
			.append(" where A.id='"+contractId+"' ");
			String columnNames = "id,code,custName,inArea,inAddress,"+
					"reason,payremark,loanType,breedName,appAmount,"+
					"yearLoan,monthLoan,dayLoan,payDate,endDate,payType,"+
					"setdayType,payDay,rateType,rate";
			DataTable dt = commonDao.getDatasBySql(sb.toString(), columnNames);
			if(null != dt && dt.getRowCount() > 0){
				String inArea = dt.getString("inArea");
				if(!StringHandler.isValidStr(inArea)){
					inArea = "";
				}else{
					inArea = getInArea(inArea);
				}
				String inAddress = dt.getString("inAddress");
				inArea += inAddress;
				dt.setCellData(0, "inArea", inArea);
			}
			setloanType(dt);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	/**设置贷款方式
	 * @param dt
	 * @throws ServiceException
	 */
	private void setloanType(DataTable dt) throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
		params.put("id", null);
		
		if(dt != null && dt.getRowCount()>0){
			for(int i=0, count = dt.getRowCount();i<count;i++){
				String loanType = dt.getString(i, "loanType");
				params.remove("id");
				params.put("id", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+loanType);
				List<GvlistEntity> gvlist = gvlistService.getEntityList(params);
				StringBuffer sbName = new StringBuffer();
				if(!gvlist.isEmpty() && gvlist.size()>0){
					for(GvlistEntity x : gvlist){
						String name = x.getName();
						sbName.append(name+",");
					}
					String loanTypeName  = StringHandler.RemoveStr(sbName);
					dt.setCellData(i, "loanType", loanTypeName);
				}
			}
		}
	}
	private String getInArea(String inArea) throws DaoException {
		String sql = "";
		StringBuffer sb = new StringBuffer();
		if(StringHandler.isValidStr(inArea)){
			String[] arr = inArea.split(",");
			if(null != arr && arr.length > 0){
				//--> step 1 : 取国家
				String countryId = arr[0];
				sql = "select name from ts_Country where id='"+countryId+"' ";
				List<String> list = commonDao.getDatasBySql(sql);
				if(null != list && list.size() > 0) sb.append(list.get(0));
				
				//--> step 2 : 取省份
				String provinceId = arr[1];
				sql = "select name from ts_Province where id='"+provinceId+"' ";
				list = commonDao.getDatasBySql(sql);
				if(null != list && list.size() > 0) sb.append(list.get(0));
				
				//--> step 3 : 取城市
				String cityId = arr[2];
				sql = "select name from ts_City where id='"+cityId+"' ";
				list = commonDao.getDatasBySql(sql);
				if(null != list && list.size() > 0) sb.append(list.get(0));
				
				//--> step 3 : 取城市地区
				String regionId = arr[3];
				sql = "select name from ts_Region where id='"+regionId+"' ";
				list = commonDao.getDatasBySql(sql);
				if(null != list && list.size() > 0) sb.append(list.get(0));
			}
		}
		return sb.toString();
	}
}
