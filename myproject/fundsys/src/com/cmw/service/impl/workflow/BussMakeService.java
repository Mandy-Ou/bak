package com.cmw.service.impl.workflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysCodeConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.sys.CommonDaoInter;
import com.cmw.entity.funds.ReceiptEntity;
import com.cmw.entity.sys.UserEntity;

/**
 * 流程业务逻辑处理方法
 * @author chengmingwei
 *
 */
@SuppressWarnings({"serial" })
@Service("bussMakeService")
public class BussMakeService implements Serializable {
	@Autowired
	private CommonDaoInter commonDao;
	
	public static final String ENTITY_SIGN = "ENTITY";
	/**
	 * 要获取流程实列的实体对象数组
	 */
	public static final String[] PROCINSTANCE_ARRS = new String []{
		"ExtensionEntity","PrepaymentEntity","ExemptEntity","AmountApplyEntity","ReceiptEntity"};/*如果移植到小额贷款系统中的时候，这里可以放 展期实体 ExtensionEntity*/
	
	public List<String> getProcessInstanceIds() throws ServiceException{
		StringBuffer sb = new StringBuffer();
		sb.append("select procId from ").append(ENTITY_SIGN).append(" A ")
		.append(" where A.isenabled="+SysConstant.OPTION_ENABLED+" and A.status="+BussStateConstant.BUSS_PROCC_DJZT_1 +" and A.procId is not null ");
		try {
			List<String> list = new ArrayList<String>();
			for(int i=0,count=PROCINSTANCE_ARRS.length; i<count; i++){
				String tempHql = sb.toString();
				String entityName = PROCINSTANCE_ARRS[i];
				String hql = tempHql.replace(ENTITY_SIGN, entityName);
				List<String> piList = commonDao.getDatasByHql(hql);
				if(null == piList || piList.size() == 0) continue;
				list.addAll(piList);
			}
			return list;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 业务处理方法
	 * @param submitDatas [表单ID:applyId,单据状态:djzt,流程实例例ID:procId]
	 * @throws ServiceException 
	 */
	public void doBuss(Map<String,Object> submitDatas) throws ServiceException{
		String bussProccCode = (String)submitDatas.get("bussProccCode");
		if(!StringHandler.isValidStr(bussProccCode)) throw new ServiceException("业务流程编号:\""+bussProccCode+"\"为空，无法处理指定的业务!");
		if(bussProccCode.equals(SysCodeConstant.BUSS_PROCC_CODE_EXTENSION)){/*展期审批流程*/
			updateFormDatas(submitDatas, "ExtensionEntity");
		}else if(bussProccCode.equals(SysCodeConstant.BUSS_PROCC_CODE_PREPAYMENT)){/*提前还款审批流程*/
			updateFormDatas(submitDatas, "PrepaymentEntity");
		}else if(bussProccCode.equals(SysCodeConstant.BUSS_PROCC_CODE_EXEMPT)){/*息费豁免审批流程*/
			updateFormDatas(submitDatas, "ExemptEntity");
		}else if(bussProccCode.equals(SysCodeConstant.BUSS_PROCC_CODE_ENTRUSTCUST)){/*委托申请审批流程*/
			updateFormDatas(submitDatas, "AmountApplyEntity");
		}else if(bussProccCode.equals(SysCodeConstant.BUSS_PROCC_CODE_RECEIPT)){/*汇票收条审批流程*/
			updateFormDatas(submitDatas, "ReceiptEntity");
		}
	}
	

	/**
	 * 更新申请单的状态和流程实例ID
	 * @param submitDatas
	 * @param tabEntity 要更新的实体名
	 * @throws ServiceException 
	 */
	private void updateFormDatas(Map<String,Object> submitDatas,String tabEntity) throws ServiceException{
		String procId = (String)submitDatas.get("procId");
		Long applyId = Long.parseLong((submitDatas.get("applyId").toString()));
		Integer djzt = (Integer)submitDatas.get("djzt");
		UserEntity user = (UserEntity)submitDatas.get(SysConstant.USER_KEY);
		Long modifier = user.getUserId();
		StringBuffer sbHql = new StringBuffer();
		sbHql.append("update ").append(tabEntity)
		.append(" set procId=?, status=?, modifier=?, modifytime=? ")
		.append(" where id = ?");
		Object[] pars = new Object[]{procId,djzt,modifier,new Date(),applyId};
		try {
			commonDao.updateDatasByHql(sbHql.toString(), pars);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 更新新闻
	 * @param submitDatas
	 * @throws ServiceException 
	 */
	private void updateNews(Map<String,Object> submitDatas) throws ServiceException{
		/*
		NewsService newsService = (NewsService)SpringContextUtil.getBean("newsService");
		Long applyId = Long.parseLong((submitDatas.get("applyId").toString()));
		String procId = (String)submitDatas.get("procId");
		Integer djzt = (Integer)submitDatas.get("djzt");
		UserEntity user = (UserEntity)submitDatas.get(SysConstant.USER_KEY);
		NewsEntity newsEntity = newsService.getEntity(applyId);
		if(!StringHandler.isValidStr(newsEntity.getProcId())){
			newsEntity.setProcId(procId);
		}
		newsEntity.setStatus(djzt);
		BeanUtil.setModifyInfo(user, newsEntity);
		newsService.updateEntity(newsEntity);
		*/
	}

	
}
