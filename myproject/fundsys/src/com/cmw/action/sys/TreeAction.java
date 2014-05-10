package com.cmw.action.sys;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.action.finance.GopinionAction;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.core.util.TreeUtil;
import com.cmw.entity.sys.AccordionEntity;
import com.cmw.entity.sys.AuditRecordsEntity;
import com.cmw.entity.sys.RoleEntity;
import com.cmw.entity.sys.TransCfgEntity;
import com.cmw.entity.sys.UroleEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.finance.CmnMappingService;
import com.cmw.service.inter.finance.GopinionService;
import com.cmw.service.inter.finance.TdsCfgService;
import com.cmw.service.inter.sys.AccordionService;
import com.cmw.service.inter.sys.AuditRecordsService;
import com.cmw.service.inter.sys.CityService;
import com.cmw.service.inter.sys.CompanyService;
import com.cmw.service.inter.sys.CountryService;
import com.cmw.service.inter.sys.DepartmentService;
import com.cmw.service.inter.sys.FormdiyService;
import com.cmw.service.inter.sys.FormulaService;
import com.cmw.service.inter.sys.MatTempService;
import com.cmw.service.inter.sys.MenuService;
import com.cmw.service.inter.sys.ModuleService;
import com.cmw.service.inter.sys.PostService;
import com.cmw.service.inter.sys.ProvinceService;
import com.cmw.service.inter.sys.RegionService;
import com.cmw.service.inter.sys.RestypeService;
import com.cmw.service.inter.sys.RoleService;
import com.cmw.service.inter.sys.UroleService;
import com.cmw.service.inter.sys.UserService;
/**
 *  树形结构数据源  Action
 * @author chengmingwei
 * @Date 2011-04-16
 */
@Description(remark="树ACTION",createDate="2010-6-15",defaultVals="sysTree_")
@SuppressWarnings("serial")
public class TreeAction extends BaseAction {
	/**
	 * 获取 session 中当前登录用户的 KEY
	 */
	public static final String USER_KEY = "user";
	@Resource(name="menuService")
	private MenuService menuService;
	
	@Resource(name = "accordionService")
	private AccordionService accordionService;
	
	@Resource(name = "moduleService")
	private ModuleService moduleService;
	
	@Resource(name = "companyService")
	private CompanyService companyService;
	
	@Resource(name = "roleService")
	private RoleService roleService;
	
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="uroleService")
	private UroleService uroleService;

	@Resource(name = "departmentService")
	private DepartmentService departmentService;
	
	@Resource(name = "postService")
	private PostService postService;
	
	@Resource(name = "restypeService")
	public RestypeService restypeService; 
	
	@Resource(name = "formdiyService")
	public FormdiyService formdiyService; 
	
	@Resource(name = "formulaService")
	public FormulaService formulaService;
	
	@Resource(name = "countryService")
	public CountryService countryService;
	
	@Resource(name = "provinceService")
	public ProvinceService provinceService;
	
	@Resource(name = "cityService")
	public CityService cityService;
	
	@Resource(name = "regionService")
	public RegionService regionService;
	
	@Resource(name="auditRecordsService")
	private AuditRecordsService auditRecordsService;
	
	@Resource(name="matTempService")
	private MatTempService matTempService;
	
	@Resource(name="tdsCfgService")
	private TdsCfgService tdsCfgService;
	
	@Resource(name="gopinionService")
	private GopinionService gopinionService;
	
	@Resource(name="cmnMappingService")
	private CmnMappingService cmnMappingService;
	
	private String result = ResultMsg.GRID_NODATA;
	//树工具类
	private TreeUtil treeUtil = new TreeUtil();
	
	
	/**
	 * 获取菜单列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		if(!isLoad()) return null;
		try {
			//获取卡片项的ID，并将其作为 父ID
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("pid", getVal("pid"));
			map.put("sysid", getVal("sysid"));
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dtAccordion = accordionService.getResultList(map);
			map.put("action", getVal("action"));	//响应类型  1:菜单管理功能请求的菜单
			DataTable dt = menuService.getResultList(map);
			// 将卡片菜单合并到菜单DataTable 对象中
			dt.insertDataTableToFirst(dtAccordion);
			if(null == dt || dt.getSize() == 0) result = ResultMsg.NODATA;
			treeUtil.setDt(dt);
			String pid = (String)map.get("pid");
			//如果没有提供 PID，就取所有菜单列表
			if(!StringHandler.isValidStr(pid)){	
				pid = SysConstant.MENU_ROOT_ID+"";
			}
			result = treeUtil.getJsonArr(pid);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	

	/**
	 * 获取流程表单树
	 * @return
	 * @throws Exception
	 */
	public String formlist()throws Exception {
		try {
			//获取卡片项的ID，并将其作为 父ID
			SHashMap<String, Object> map = getQParams("code");
			Long sysId = getLVal("sysId");
			map.put("sysid", sysId);
			//map.put("code",SysConstant.ACCORDION_CODE_TYPE_FORM_PREFIX);	//找编号以 "FORM_" 为前缀的卡片数据
			AccordionEntity accordEntity = accordionService.getEntity(map);
			if(null == accordEntity){
				result = ResultMsg.NODATA;
				outJsonString(result);
				return null;
			}
			map.remove("sysId");
			map.remove("code");
			
			Long accordionId = accordEntity.getId();
			map.put("accordionId",accordionId);
			map.put("action",SysConstant.MENU_ACTION_PROCESSFORM);
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = menuService.getResultList(map);
			if(null == dt || dt.getSize() == 0){
				result = ResultMsg.NODATA;
			}else{
				String menuIds = getMenuIds(dt);
				map.clear();
				map.put("menuIds", menuIds);
				map.put("action",SysConstant.MENU_ACTION_PROCESSFORM);
				DataTable dtModules = moduleService.getResultList(map);
				if(null != dtModules && dtModules.getRowCount() > 0){
					dt.addDtToEnd(dtModules);
				}
				treeUtil.setDt(dt);
				result = treeUtil.getJsonArr("C"+accordionId);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	private String getMenuIds(DataTable dtMenus){
		StringBuffer sb = new StringBuffer();
		for(int i=0,count=dtMenus.getRowCount(); i<count; i++){
			sb.append(dtMenus.getString(i, "id")+",");
		}
		String menuIds = null;
		if(null != sb && sb.length()>0){
			menuIds = StringHandler.RemoveStr(sb);
			menuIds = menuIds.replace("M", "");
		}
		return menuIds;
	}
	/**
	 * 获取角色权限列表树
	 * @return
	 * @throws Exception
	 */
	public String roleright()throws Exception {
		if(!isLoad()) return null;
		try {
			//获取卡片项的ID，并将其作为 父ID
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("sysid", getVal("sysid"));
			map.put("action", getVal("action"));	//响应类型  2:角色管理功能请求的权限菜单 || 3:用户管理，数据访问级别权限菜单
			map.put(SysConstant.USER_KEY, this.getCurUser());
			String pid = (String)map.get("pid");
			//如果没有提供 PID，就取所有菜单列表
			if(!StringHandler.isValidStr(pid)){	
				pid = SysConstant.MENU_ROOT_ID+"";
			}
			DataTable dtAccordion = accordionService.getResultList(map);
			treeUtil.setIconPath("");
			if(null==dtAccordion || dtAccordion.getRowCount() == 0){
				result = ResultMsg.NODATA;
			}else{
				String accordionIds = getIds(dtAccordion,"id","C");
				map.put("accordionIds", accordionIds);
				DataTable dt = menuService.getResultList(map);
				if(null == dt || dt.getSize() == 0){
					treeUtil.setDt(dtAccordion);
					result = treeUtil.getJsonArr(pid);
				}else{
					String menuIds = getIds(dt,"id","M");
					map.put("menuIds", menuIds);
					
					// 将卡片菜单合并到菜单DataTable 对象中
					dt.insertDataTableToFirst(dtAccordion);
					DataTable modDt = moduleService.getResultList(map);
					if(null == modDt || modDt.getSize() == 0){
						treeUtil.setDt(dt);
						result = treeUtil.getJsonArr(pid);
					}else{
						setMenuDTLeaf(dt,modDt);
						modDt.insertDataTableToFirst(dt);
						treeUtil.setDt(modDt);
						result = treeUtil.getJsonArr(pid);
					}
				}
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 当菜单下有按钮时, 设置菜单DataTable 对象的 leaf 为 false
	 * @param menuDt
	 * @param modDt
	 */
	private void setMenuDTLeaf(DataTable menuDt, DataTable modDt){
		int len = modDt.getRowCount();
		for(int i=0,count=menuDt.getRowCount(); i<count; i++){
			String leaf = menuDt.getString(i, "leaf");
			if(StringHandler.isValidStr(leaf) && leaf.equals("false")) continue;
			String id = menuDt.getString(i, "id");
			for(int j=0; j<len; j++){
				String pid = modDt.getString(j, "pid");
				if(id.equals(pid)){
					menuDt.setCellData(i, "leaf", "false");
					break;
				}
			}
		}
	}
	
	/**
	 * 获取卡片ID或菜单ID列表字符串
	 * @param dt
	 * @param cmn 列名
	 * @return
	 */
	private String getIds(DataTable dt,String cmn,String removeChar){
		StringBuffer sb = new StringBuffer();
		for(int i=0,count=dt.getRowCount(); i<count; i++){
			sb.append(dt.getString(i, cmn)+",");
		}
		String ids = StringHandler.RemoveStr(sb, ",");
		ids = ids.replace(removeChar, "");
		return ids;
	}
	
	/**
	 * 获取组织架构树
	 * @return
	 * @throws Exception
	 */
	public String orglist()throws Exception {
//		if(!isLoad()) return null;
		try {
			//获取卡片项的ID，并将其作为 父ID
			String isNeedPost = getVal("isNeedPost");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = companyService.getResultList(map);
			
			treeUtil.setIconPath("");
			if(null == dt || dt.getSize() == 0) {
				result = ResultMsg.NODATA;
			}else{
				DataTable dtDepartment = departmentService.getResultList(map);
				if(dtDepartment == null || dtDepartment.getSize() == 0){
					result = ResultMsg.NODATA;
					treeUtil.setDt(dt);
					result = treeUtil.getJsonArr("0");
				}else{
					dtDepartment.insertDataTableToFirst(dt);
					treeUtil.setDt(dtDepartment);
					result = treeUtil.getJsonArr("0");
				}
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/**
	 * 获取部门数据
	 */
	public String postlist() throws Exception{
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dtPost = postService.getPostList(map);
			treeUtil.setIconPath("");
			if(null == dtPost || dtPost.getSize() == 0) result = ResultMsg.NODATA;
			treeUtil.setDt(dtPost);
			result = treeUtil.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		
		outJsonString(result);
		return null;
	}
	/**
	 * 获取自定义数据访问级别组织架构树
	 * @return
	 * @throws Exception
	 */
	public String custorglist()throws Exception {
		if(!isLoad()) return null;
		try {
			//获取卡片项的ID，并将其作为 父ID
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			Integer action = getIVal("action");
			if(null == action){
				outJsonString(ResultMsg.NODATA);
				return null;
			}
			map.put("action", action);
			
			DataTable treeDt = null;
			DataTable dt = companyService.getResultList(map);
			treeUtil.setIconPath("");
			if(null == dt || dt.getSize() == 0) {
				result = ResultMsg.NODATA;
			}else{
				if(action.intValue() == SysConstant.ORG_ACTION_COMPANY_3){	//如果只取公司
					treeDt = dt;
				}else{
					DataTable dtDepartment = departmentService.getResultList(map);
					if(dtDepartment != null && dtDepartment.getRowCount() > 0){
						dtDepartment.insertDataTableToFirst(dt);
						if(action.intValue() == SysConstant.ORG_ACTION_DEPT_1){//取部门
							treeDt = dtDepartment;
						}else{
								DataTable dtUser = userService.getResultList(map);
								if(null != dtUser && dtUser.getRowCount()>0){
									dtUser.insertDataTableToFirst(dtDepartment);
									treeDt = dtUser;
								}else{
									treeDt = dtDepartment;
								}
						}
					}
				}
			}
			if(null == treeDt){
				result = ResultMsg.NODATA;
			}else{
				treeUtil.setDt(treeDt);
				result = treeUtil.getJsonArr();
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取角色树
	 * @return
	 * @throws Exception
	 */
	public String rolelist()throws Exception {
		if(!isLoad()) return null;
		try {
			//获取卡片项的ID，并将其作为 父ID
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = roleService.getResultList(map);
			treeUtil.setIconPath("");
			if(null == dt || dt.getSize() == 0) result = ResultMsg.NODATA;
			treeUtil.setDt(dt);
			result = treeUtil.getJsonArr("0");
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 *  获取资源树
	 */
	public String reslist()throws Exception{
		if(!isLoad()) return null;
		try {
			//获取卡片项的ID，并将其作为 父ID
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			String recode = getVal("recode");
			if(StringHandler.isValidStr(recode)){
				map.put("recode", recode);
			}
			DataTable dt = restypeService.getResultList(map);
			treeUtil.setIconPath("");
			if(null == dt || dt.getSize() == 0) result = ResultMsg.NODATA;
			treeUtil.setDt(dt);
			result = treeUtil.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/**
	 *  获取表单个性化树
	 * @return
	 * @throws Exception
	 */
	public String fdlist()throws Exception{
		if(!isLoad()) return null;
		try {
			//获取卡片项的ID，并将其作为 父ID
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = formdiyService.getResultList(map);
			if(null == dt || dt.getSize() == 0) result = ResultMsg.NODATA;
			String pid = getVal("sysid");
			treeUtil.setIconPath("");
			treeUtil.setDt(dt);
			result = treeUtil.getJsonArr(pid);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/**
	 *  公式列表树
	 * @return
	 * @throws Exception
	 */
	public String fmulalist() throws Exception{
		if(!isLoad()) return null;
		try {
			//获取卡片项的ID，并将其作为 父ID
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = formulaService.getResultList(map);
			if(null == dt || dt.getSize() == 0) result = ResultMsg.NODATA;
			treeUtil.setIconPath("");
			treeUtil.setDt(dt);
			result = treeUtil.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 获取区域架构树
	 * @return
	 * @throws Exception
	 */
	public String arealist()throws Exception {
		if(!isLoad()) return null;
		try {
 			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = countryService.getResultList(map);
			
			treeUtil.setIconPath("");
			if(null == dt || dt.getSize() == 0) {
				result = ResultMsg.NODATA;
			}else{
				DataTable dtProvince = provinceService.getResultList(map);
				if(dtProvince == null || dtProvince.getSize() == 0){
					result = ResultMsg.NODATA;
					treeUtil.setDt(dt);
					result = treeUtil.getJsonArr("R18");
				}else{
					
					dtProvince.insertDataTableToFirst(dt);
					
					DataTable dtCity = cityService.getResultList(map);
					if(dtCity == null || dtCity.getSize() == 0){
						result = ResultMsg.NODATA;
						treeUtil.setDt(dt);
						result = treeUtil.getJsonArr("R18");
					}else{
						
						dtCity.insertDataTableToFirst(dt);
						DataTable dtRegion = regionService.getResultList(map);
						if(dtRegion ==null || dtRegion.getSize() ==0){
							result = ResultMsg.NODATA;
							treeUtil.setDt(dtCity);
							result = treeUtil.getJsonArr("R18");
						}else{
							dtRegion.insertDataTableToFirst(dt);
							treeUtil.setDt(dt);
							result = treeUtil.getJsonArr("R18");
						}
					}
				}
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取用户或角色用户 Radio/checkbox
	 * @return
	 * @throws Exception
	 */
	public String useRolelist()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("acType#I,actorIds");
 			Integer acType = map.getvalAsInt("acType");
 			if(null == acType) throw new ServiceException(" acType 不能为空!");
 			String actorIds = map.getvalAsStr("actorIds");
 			map.clear();
 			JSONArray arr = new JSONArray();
 			if(acType.intValue() == TransCfgEntity.ACTYPE_1){ //角色
 				map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + actorIds);
 				List<RoleEntity> roles = roleService.getEntityList(map);
 				for(RoleEntity role : roles){
 					Long roleId = role.getId();
 					String roleName = role.getName();
 					map.clear();
 					map.put("roleId", roleId);
 					List<UroleEntity> uroles = uroleService.getEntityList(map);
 					if(null == uroles || uroles.size() == 0) continue;
 					map.remove("roleId");
 					StringBuffer sb = new StringBuffer();
 					for(UroleEntity urole : uroles){
 						sb.append(urole.getUserId()+",");
 					}
 					String userIds = StringHandler.RemoveStr(sb);
 					map.put("userId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + userIds);
 					map.put("isenabled", SysConstant.OPTION_ENABLED);
 	 				List<UserEntity> users = userService.getEntityList(map);
 	 				if(null == users || users.size() == 0) continue;
 	 				JSONObject obj = new JSONObject();
 	 				obj.put("groupName", roleName + "[角色]");
 	 				obj.put("users", getUserJsonArr(users));
 	 				arr.add(obj);
 				}
 			}else if(acType.intValue() == TransCfgEntity.ACTYPE_2){ //用户
 				arr = getUserJsonArr(actorIds);
 			}else if(acType.intValue() == TransCfgEntity.ACTYPE_3){ //上一环节处理人
 				SHashMap<String, Object> params = new SHashMap<String, Object>();
 				String procId = getVal("procId");
 				Long nodeId = getLVal("nodeId");
 				params.put("procId", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + procId);
 				params.put("bussNodeId", nodeId);
 				List<AuditRecordsEntity> recordEntitys = auditRecordsService.getEntityList(params);
 				StringBuffer sb = new StringBuffer();
 				if(null != recordEntitys && recordEntitys.size() > 0){
 					for(AuditRecordsEntity auditRecord : recordEntitys){
 						sb.append(auditRecord.getCreator().toString()+",");
 					}
 				}
 				String prev_actorIds = "-1";
 				if(sb.length() > 0) prev_actorIds = StringHandler.RemoveStr(sb);
 				arr = getUserJsonArr(prev_actorIds);
 			}else if(acType.intValue() == TransCfgEntity.ACTYPE_5){ //流程发起人
 				String procId = getVal("procId");
 				Long procStartor = auditRecordsService.getStartor(procId);
 				arr = getUserJsonArr(procStartor.toString());
 			}
 			result = arr.toJSONString();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}


	private JSONArray getUserJsonArr(String actorIds) throws ServiceException {
		JSONArray arr = new JSONArray();
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("userId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + actorIds);
		map.put("isenabled", SysConstant.OPTION_ENABLED);
		List<UserEntity> users = userService.getEntityList(map);
		JSONObject obj = new JSONObject();
		obj.put("groupName", "下一步处理人");
		obj.put("users", getUserJsonArr(users));
		arr.add(obj);
		return arr;
	}
	
	private JSONArray getUserJsonArr(List<UserEntity> users){
		int count = users.size();
		JSONArray arr = new JSONArray(count);
		for(UserEntity user : users){
			JSONObject obj = new JSONObject();
			obj.put("id", user.getUserId());
			String name = user.getEmpName();
			if(!StringHandler.isValidStr(name)) name = user.getUserName();
			obj.put("name", name);
			arr.add(obj);
		}
		return arr;
	}
	/**
	 *  获取模板列表树
	 * @return
	 * @throws Exception
	 */
	public String matTempList()throws Exception{
		if(!isLoad()) return null;
		try {
			//获取卡片项的ID，并将其作为 父ID
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = matTempService.getResultList(map);
			if(null == dt || dt.getSize() == 0) result = ResultMsg.NODATA;
			String pid = getVal("sysId");
			treeUtil.setIconPath("");
			treeUtil.setDt(dt);
			result = treeUtil.getJsonArr(pid);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 《获取金融贷款平台》合同模板树
	 * @return
	 * @throws Exception
	 */
	public String tdsCfgList()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("tempId",getVal("tempId"));
			String action=getVal("action");
			DataTable dt = tdsCfgService.getResultList(map);
			treeUtil.setIconPath("");
			if(null == dt || dt.getSize() == 0) {
				result = ResultMsg.NODATA;
			}else{
				DataTable dtDepartment = cmnMappingService.getResultList(map);
				if(dtDepartment == null || dtDepartment.getSize() == 0 ||"2".equals(action)){
					result = ResultMsg.NODATA;
					treeUtil.setDt(dt);
					result = treeUtil.getJsonArr();
				}else{
					dtDepartment.insertDataTableToFirst(dt);
					treeUtil.setDt(dtDepartment);
					result = treeUtil.getJsonArr();
				}
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 《获取金融贷款平台》担保人树
	 * @return
	 * @throws Exception
	 */
	public String gopinionList()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = gopinionService.getResultList(map);
			treeUtil.setIconPath("");
				if(dt == null || dt.getSize() == 0 ){
					result = ResultMsg.NODATA;
					treeUtil.setDt(dt);
					result = treeUtil.getJsonArr();
				}else{
					dt.insertDataTableToFirst(dt);
					treeUtil.setDt(dt);
					result = treeUtil.getJsonArr();
				}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 得到images 下面的文件夹
	 */
	public String imgFolider() throws Exception{
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put(SysConstant.USER_KEY, this.getCurUser());
		String dir = StringHandler.GetResValue("imgAll_path");
		String path = FileUtil.getFilePath(getRequest(), dir);
		JSONObject imagesFolder = new JSONObject();
		result = FileUtil.readImgFolder(path,imagesFolder).toJSONString();
		outJsonString(result);
		return null;
	}
}

