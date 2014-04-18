package com.cmw.test.sys;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmw.action.sys.UserAction;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.test.AbstractTestCase;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.TreeUtil;
import com.cmw.entity.sys.MenuEntity;
import com.cmw.service.inter.sys.MenuService;

public class MenuTest extends AbstractTestCase {
	@Autowired
	private MenuService menuService;
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
		//获取卡片项的ID，并将其作为 父ID
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("type", SysConstant.MENU_TYPE_CARD);
		String pid = SysConstant.MENU_ROOT_ID+"";
		map.put("pid", pid);
		DataTable dt = menuService.getResultList(map);
		treeUtil.setDt(dt);
		String result = treeUtil.getJsonArr(pid);
		System.out.println(result);
	}
	
	@Test
	public void testNavigation() throws ServiceException{
		//获取卡片项的ID，并将其作为 父ID
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("id", 35+"");
		MenuEntity entity = menuService.navigationNext(map);
		System.out.println( entity.getMenuId());
	}
}
