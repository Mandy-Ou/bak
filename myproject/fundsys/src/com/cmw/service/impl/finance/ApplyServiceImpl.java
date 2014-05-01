package com.cmw.service.impl.finance;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.ApplyDaoInter;
import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.sys.GvlistEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.sys.GvlistService;
import com.cmw.service.inter.sys.UserService;


/**
 * 贷款申请  Service实现类
 * @author 程明卫
 * @date 2012-12-16T00:00:00
 */
@Description(remark="贷款申请业务实现类",createDate="2012-12-16T00:00:00",author="程明卫")
@Service("applyService")
public class ApplyServiceImpl extends AbsService<ApplyEntity, Long> implements  ApplyService {
	@Autowired
	private ApplyDaoInter applyDao;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="gvlistService")
	private GvlistService gvlistService;
	
	@Override
	public GenericDaoInter<ApplyEntity, Long> getDao() {
		return applyDao;
	}
	/**
	  * 获取已经开始并且未完结的流程实例ID列表
	  * @return 以DataTable 返回
	  * @throws DaoException
	  */
	public List<String> getProcessInstanceIds() throws ServiceException{
		try {
			return applyDao.getProcessInstanceIds();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	 }
	
	 /**
	 * 根据还款计划ID获取贷款申请单列表
	 * @param planIds 还款计划表ID集合字符串
	 * @return
	 * @throws DaoException 
	 */
	public Map<Long,ApplyEntity> getApplyEntitysByPlanIds(String planIds) throws ServiceException{
		try {
			return applyDao.getApplyEntitysByPlanIds(planIds);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	/**
	 * 更新项目的状态
	 * data 值 state,user,contractIds
	 * @param data
	 * @throws DaoException
	 */
	public void updateState(SHashMap<String, Object> data) throws ServiceException{
		try {
			applyDao.updateState(data);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		try {
			if(params.containsKey("userId")){
				SHashMap<String, Object>  userParams = new SHashMap<String, Object>();
				userParams.put("userId", params.get("userId"));
				UserEntity user = userService.getEntity(userParams);
				params.put(SysConstant.USER_KEY, user);
			}
			DataTable dt = applyDao.getResultList(new SHashMap<String, Object>(params), -1, -1);
			
			SHashMap<String, Object> typeParams = new SHashMap<String, Object>();
			typeParams.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
			typeParams.put("id", null);
			if(dt != null && dt.getRowCount()>0){
				for(int i=0, count = dt.getRowCount();i<count;i++){
					String loanType = dt.getString(i, "loanType");
					typeParams.remove("id");
					typeParams.put("id", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+loanType);
					List<GvlistEntity> gvlist = gvlistService.getEntityList(typeParams);
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
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.cmw.service.inter.finance.ApplyService#getApplyDt(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public <K, V> DataTable getApplyDt(SHashMap<K, V> params, int offset,
			int pageSize) throws ServiceException {
		try {
				return applyDao.getApplyDt( params, offset, pageSize);
			} catch (DaoException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
	}
	
	
}
