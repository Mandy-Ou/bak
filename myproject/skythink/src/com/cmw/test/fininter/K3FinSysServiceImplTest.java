package com.cmw.test.fininter;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.test.AbstractTestCase;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.fininter.AcctGroupEntity;
import com.cmw.entity.fininter.CurrencyEntity;
import com.cmw.entity.fininter.ItemClassEntity;
import com.cmw.entity.fininter.SettleEntity;
import com.cmw.entity.fininter.SubjectEntity;
import com.cmw.entity.fininter.UserMappingEntity;
import com.cmw.entity.fininter.VoucherGroupEntity;
import com.cmw.service.impl.fininter.external.FinSysService;
import com.cmw.service.inter.sys.UserService;

public class K3FinSysServiceImplTest  extends AbstractTestCase{
	@Resource(name="k3FinSysService")
	FinSysService k3FinSysService;
	@Resource(name="userService")
	private UserService userService;
	@Test
	public void testGetUserMappings() throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put(SysConstant.USER_KEY, userService.getEntity(45L));
		params.put("refIds", -1);
		List<UserMappingEntity> list = k3FinSysService.getUserMappings(params);
		System.out.println(list.size());
	}

	
	@Test
	public void testGetAcctGroups()throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put(SysConstant.USER_KEY, userService.getEntity(45L));
		params.put("refIds", -1);
		List<AcctGroupEntity> list = k3FinSysService.getAcctGroups(params);
		System.out.println(list.size());
	}

	@Test
	public void testGetSubjects()throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put(SysConstant.USER_KEY, userService.getEntity(45L));
		params.put("refIds", -1);
		List<SubjectEntity> list = k3FinSysService.getSubjects(params);
		System.out.println(list.size());
	}

	@Test
	public void testGetCurrencys()throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put(SysConstant.USER_KEY, userService.getEntity(45L));
		params.put("refIds", -1);
		List<CurrencyEntity> list = k3FinSysService.getCurrencys(params);
		System.out.println(list.size());
	}

	@Test
	public void testGetItemClasses()throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put(SysConstant.USER_KEY, userService.getEntity(45L));
		params.put("refIds", -1);
		List<ItemClassEntity> list = k3FinSysService.getItemClasses(params);
		System.out.println(list.size());
	}

	@Test
	public void testGetSettles()throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put(SysConstant.USER_KEY, userService.getEntity(45L));
		params.put("refIds", -1);
		List<SettleEntity> list = k3FinSysService.getSettles(params);
		System.out.println(list.size());
	}

	@Test
	public void testGetVoucherGroups() throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put(SysConstant.USER_KEY, userService.getEntity(45L));
		params.put("refIds", -1);
		List<VoucherGroupEntity> list = k3FinSysService.getVoucherGroups(params);
		System.out.println(list.size());
	}

	@Test
	public void saveItem() throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		
		SHashMap<String, Object> returnMap = null;
	}

	@Test
	public void saveItems() throws ServiceException {
		// TODO Auto-generated method stub
	}

	@Test
	public void saveVouchers()throws ServiceException {
		// TODO Auto-generated method stub
	}
	
	@Test
	public void insetTest(){
	}
}
