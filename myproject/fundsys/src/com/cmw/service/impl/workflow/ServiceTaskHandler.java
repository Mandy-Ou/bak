package com.cmw.service.impl.workflow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmw.core.util.StringHandler;
/**
 * 业务任务处理类
 * @author chengmingwei
 *
 */
public class ServiceTaskHandler implements JavaDelegate {
	Log log = LogFactory.getLog(getClass());
	private Expression sysCode;/*系统编号，参见：SysCodeConstant.java 类定义*/
	private Expression custParams;/*自定义参数变量，如果有多个请用以下格式:"id:1,name:xxxx,age:12"*/
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		/*放入流程中的参数已传回到 BussFlowModel 中的 flowVariables 变量中，
		 * 获取流程参数值请看 BussFlowService 类中的  submitFlow 方法 */
		convertParamsToVars(execution); 
		log.info(execution.getProcessBusinessKey()+","+execution.getProcessDefinitionId()+","+execution.getProcessInstanceId());
	}

	
	
	/**
	 * 将流程文件中的参数传递给流程变量
	 * @param execution
	 * @throws Exception
	 */
	private void convertParamsToVars(DelegateExecution execution) throws Exception{
		String sysCodeVal = (String)sysCode.getValue(execution);
		String custParamsVal = (String)custParams.getValue(execution);
		if(!StringHandler.isValidStr(sysCodeVal)) throw new Exception("在流程配置文件中，必须为 serviceTask 节点配置 sysCode 变量，以用来区分是属于哪个系统中的流程/n" +
				"sysCode 值请参见 SysCodeConstant 类中的定义!");
		execution.setVariable("sysCode", sysCodeVal);
		if(!StringHandler.isValidStr(custParamsVal)) return;
		String[] kvArr = custParamsVal.split(",");
		if(null == kvArr || kvArr.length == 0) return;
		for(String kvObj : kvArr){
			String[] kv = kvObj.split(":");
			if(null == kv || kv.length == 0) continue;
			String key = null;
			String val = null;
			if(kv.length>0 && kv.length < 2){
				key = kv[0];
			}else{
				key = kv[0];
				val = kv[1];
			}
			execution.setVariable(key, val);
		}
	}
}
