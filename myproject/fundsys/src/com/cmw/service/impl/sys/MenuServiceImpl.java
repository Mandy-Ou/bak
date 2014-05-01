package com.cmw.service.impl.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.sys.MenuDaoInter;
import com.cmw.entity.sys.MenuEntity;
import com.cmw.service.inter.sys.MenuService;
/**
 * 菜单管理业务实现类
 * @author ddd
 *
 */
@Description(remark="菜单管理业务实现类",createDate="2011-08-13")
@Service("menuService")
public class MenuServiceImpl extends AbsService<MenuEntity, Long> implements MenuService {
	@Autowired
	private MenuDaoInter menuDao;
	@Override
	public GenericDaoInter<MenuEntity, Long> getDao() {
		return menuDao;
	}
	@Override
	public void saveOrUpdateEntity(MenuEntity obj) throws ServiceException {
		Long menuId = obj.getMenuId();
		if(null == menuId && !obj.getType().equals(MenuEntity.MENU_TYPE_1)){
			MenuEntity parentObj = getEntity(obj.getPid());
			if(parentObj.getLeaf().equals(MenuEntity.MENU_LEAF_TRUE)){
				parentObj.setLeaf(MenuEntity.MENU_LEAF_FALSE);
				super.saveOrUpdateEntity(parentObj);
			}
		}
		super.saveOrUpdateEntity(obj);
	}
}
