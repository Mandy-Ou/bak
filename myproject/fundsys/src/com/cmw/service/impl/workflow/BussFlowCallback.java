package com.cmw.service.impl.workflow;
/**
 * 业务流程回调接口
 * @author chengmingwei
 *
 */
public interface BussFlowCallback {
	/**
	 * 业务执行方法
	 * @param flowModel 工作流模型对象
	 * @return
	 * @throws BussFlowException
	 */
	Object execute(BussFlowModel flowModel) throws BussFlowException;
}
