package com.cmw.test.sys;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.junit.Test;

import com.cmw.core.base.test.AbstractTestCase;

public class ActivitiTest  extends AbstractTestCase {
	@Resource(name="repositoryService")
	RepositoryService repositoryService;
	@Test
	public void testDeploy(){
		String deploymentId = repositoryService
		  .createDeployment()
		  .addClasspathResource("com/cmw/test/sys/autodeploy.a.bpmn20.xml")
		  .deploy()
		  .getId();
		System.out.println("deploymentId="+deploymentId);
	}
}
