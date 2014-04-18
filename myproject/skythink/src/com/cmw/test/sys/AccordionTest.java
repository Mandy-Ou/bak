package com.cmw.test.sys;

import javax.annotation.Resource;

import org.junit.Test;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.test.AbstractTestCase;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.service.inter.sys.AccordionService;

public class AccordionTest extends AbstractTestCase {
	@Resource(name="accordionService")
	private AccordionService accordionService;
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
		//获取卡片项的ID，并将其作为 父ID
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("name", "");
		DataTable dt = accordionService.getResultList(map,0,20);
		System.out.println(dt.getRowCount());
	}
	
}