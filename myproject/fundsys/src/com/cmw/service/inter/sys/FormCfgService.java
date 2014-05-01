package com.cmw.service.inter.sys;


import com.alibaba.fastjson.JSONObject;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.sys.FormCfgEntity;


/**
 * 节点表单配置  Service接口
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="节点表单配置业务接口",createDate="2012-11-28T00:00:00",author="程明卫")
public interface FormCfgService extends IService<FormCfgEntity, Long> {
	/**
	 * 获取业务品种流程必做和选做业务表单事项菜单数据
	 * @param params 参数
	 * 	 （包含以下元素项:[pdid:流程定义ID,procId:流程实例ID,currUser:当前用户,
	 * 	 bussType:流程业务类型  ,bussCode:业务编号,formId:业务编号]）
	 * @return 返回节点表单数据
	 * @throws ServiceException
	 */
	public JSONObject getBussFormDatas(SHashMap<String, Object> params) throws ServiceException;
}
