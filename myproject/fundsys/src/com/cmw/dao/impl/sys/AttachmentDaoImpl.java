package com.cmw.dao.impl.sys;


import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.sys.AttachmentDaoInter;
import com.cmw.entity.sys.AttachmentEntity;


/**
 * 附件  DAO实现类
 * @author 程明卫
 * @date 2012-12-04T00:00:00
 */
@Description(remark="附件DAO实现类",createDate="2012-12-04T00:00:00",author="程明卫")
@Repository("attachmentDao")
public class AttachmentDaoImpl extends GenericDaoAbs<AttachmentEntity, Long> implements AttachmentDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		try{
			String cmns = "id,sysId,formType,formId,atype,fileName,filePath,swfPath,fileSize,creator,createTime#yyyy-MM-dd,userId";
			StringBuffer hqlSb = new StringBuffer();
			hqlSb.append("SELECT A.id,A.sysId,A.formType,A.formId,A.atype,A.fileName,A.filePath,A.swfPath,A.fileSize,B.empName as creator,A.createTime,B.userId ")
			.append(" FROM AttachmentEntity A,UserEntity B where A.isenabled='"+SysConstant.OPTION_ENABLED+"' and A.creator=B.userId ");
			Long sysId = map.getvalAsLng("sysId");
			if(StringHandler.isValidObj(sysId)){
				hqlSb.append(" and A.sysId = '"+sysId+"' ");
			}
			
			Integer formType = map.getvalAsInt("formType");
			if(StringHandler.isValidObj(formType)){
				hqlSb.append(" and A.formType = '"+formType+"' ");
			}
			
			String formId = map.getvalAsStr("formId");
			if(StringHandler.isValidStr(formId)){
				formId = "'"+formId.replace(",", "','")+"'";
				hqlSb.append(" and A.formId in ("+formId+") ");
			}
			
			String attachmentIds =  map.getvalAsStr("attachmentIds");
			if(StringHandler.isValidStr(attachmentIds)){
				hqlSb.append(" and A.id in ("+attachmentIds+") ");
			}
			hqlSb.append(" order by A.id desc ");
			DataTable dt = find(hqlSb.toString(), cmns);
			return dt;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}
