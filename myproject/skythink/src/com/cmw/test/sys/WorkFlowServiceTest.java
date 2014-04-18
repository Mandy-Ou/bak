package com.cmw.test.sys;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

import com.cmw.core.base.test.AbstractTestCase;
import com.cmw.core.base.workflow.WorkFlowService;
/**
 * 工作流测试
 * @author Administrator
 *
 */
public class WorkFlowServiceTest extends AbstractTestCase {
	@Resource(name="workflowService")
	private WorkFlowService workflowService;
	
	@Test
	public void testDeploy(){
		String xmlfilePath = "F:\\dev\\skythink\\resources\\activitis\\DemoProcess.bpmn";
		String zipFilePath = "F:\\dev\\skythink\\resources\\activitis\\finance.zip";
//		Deployment deploy = workflowService.deployXml(xmlfilePath);
		System.out.println("------------------ 开始流程部署  -----------------");
		Deployment deploy = workflowService.deployZip(zipFilePath);
		String deploymentId = deploy.getId();
		System.out.println(deploymentId+","+deploy.getName()+","+deploy.getDeploymentTime());
		System.out.println("------------------ 流程部署完成  -----------------");
		System.out.println("================================================");
		
		System.out.println("------------------ 开始获取 ProcessDefintion 对象 信息  -----------------");
		ProcessDefinition processDef = workflowService.getLateProcDefByDeploymentId(deploymentId);
		String processDefId = processDef.getId();
		String key = processDef.getKey();
		String name = processDef.getName();
		String diagramName = processDef.getDiagramResourceName();
		String resourceName = processDef.getResourceName();
		int version = processDef.getVersion();
		String description = processDef.getDescription();
		System.out.println("processDefId="+processDefId+",key="+key+",name="+name+",diagramName="+diagramName
				+",resourceName="+resourceName+",version="+version+",description="+description);
		System.out.println("------------------  ProcessDefintion 对象 信息 读取完成  -----------------");
		System.out.println("================================================");
		
		System.out.println("------------------ 开始获取 ProcessDefintion 对象图片 信息  -----------------");
		InputStream ins = workflowService.getProcDefImg(processDefId);
		BufferedInputStream bins = new BufferedInputStream(ins);
		BufferedOutputStream bios = null;
		String desFilePath = "F:\\dev\\skythink\\src\\com\\cmw\\test\\sys\\finance.png";
		try {
			bios = new BufferedOutputStream(new FileOutputStream(desFilePath));
			byte[] data = new byte[1];
			while(-1 != bins.read(data)){
				bios.write(data);
			}
			bios.flush();
			bins.close();
			bios.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("------------------  ProcessDefintion 对象图片已成功保存到["+desFilePath+"] 读取完成  -----------------");
		System.out.println("================================================");
		List<Map<String,Object>> nodes = workflowService.getNodesByProcDefId(processDefId);
		for(Map<String,Object> map : nodes){
			Set<String> keys = map.keySet();
			for(String curkey : keys){
				System.out.println(curkey+" = "+map.get(curkey));
			}
		}
	}
	
}
