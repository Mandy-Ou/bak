package com.cmw.test.sys;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.test.AbstractTestCase;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.TreeUtil;
import com.cmw.service.inter.sys.FormdiyService;

public class FormdiyTest extends AbstractTestCase {
	@Autowired
	private FormdiyService formdiyService;
	@SuppressWarnings("unused")
	private TreeUtil treeUtil = new TreeUtil();
//	@Test
//	public void testSave() throws ServiceException{
//		MenuEntity menu = new MenuEntity();
//		menu.setSysId(1L);
//		menu.setCode(CodeRule.getCode("M", menuService.getMaxID()));
//		menu.setName("人力资源");
//		menu.setType(SysConstant.MENU_TYPE_CARD);
//		menu.setLink("index.html");
//		menu.setJsArray("index.js");
//		menu.setCreateTime(new Date());
//		menu.setCreator(1L);
//		menu.setOrgid(9999L);
//		menu.setDeptId(1L);
//		menuService.saveEntity(menu);
//	}
	
	@Test
	public void testgetResultList() throws ServiceException{
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			DataTable dt =formdiyService.getResultList(map);
			TreeUtil treeUtil = new TreeUtil(dt);
			String jsonStr = treeUtil.getJsonArr();
			System.out.println(jsonStr);
	}
	
	
}
