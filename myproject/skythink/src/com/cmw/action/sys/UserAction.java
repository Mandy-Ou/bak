package com.cmw.action.sys;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.security.certificate.AuthImage;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.IPUtils;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.DepartmentEntity;
import com.cmw.entity.sys.PostEntity;
import com.cmw.entity.sys.RoleEntity;
import com.cmw.entity.sys.UroleEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.RoleCache;
import com.cmw.service.impl.lock.LockManager;
import com.cmw.service.inter.sys.DepartmentService;
import com.cmw.service.inter.sys.PostService;
import com.cmw.service.inter.sys.RoleService;
import com.cmw.service.inter.sys.UroleService;
import com.cmw.service.inter.sys.UserService;


/**
 * 用户  ACTION类
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="用户ACTION",createDate="2011-09-24T00:00:00",author="chengmingwei",defaultVals="sysUser_")
@SuppressWarnings("serial")
public class UserAction extends BaseAction {
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="postService")
	private PostService postService;
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	@Resource(name="roleService")
	private RoleService roleService;
	@Resource(name="uroleService")
	private UroleService uroleService;
	private String result = ResultMsg.GRID_NODATA;
	
	  
	
	/**
	 * 用户登录
	 * @return
	 * @throws Exception
	 */
	public String gotoLogin()throws Exception {
		return SUCCESS;
	}
	/**
	 * 用户登录
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public String login()throws Exception {
		try {
			SHashMap<String, Object> params = getQParams("userName,code,passWord");
			String code = (String)params.get("code");
			String passWord = (String)params.get("passWord");
			params.removes("code,passWord");
			params.put("userName", SqlUtil.LOGIC_EQ+SqlUtil.LOGIC+ params.getvalAsStr("userName"));
			UserEntity user = userService.getEntity(params);
			
			/*if(!code.equals(validCode)){	//验证验证码是否正确
			throw new ServiceException(ResultMsg.ERROR_VALID_CODE);
			}else*/ if(null == user){	//用户名不存在
				throw new ServiceException(ResultMsg.ERROR_USERNAME_NOHAVE);
			}else if(!StringHandler.isValidStr(passWord)){ //密码错误
				throw new ServiceException(ResultMsg.ERROR_PASSWORD);
			}else if(!user.getPassWord().equals(passWord)){
				throw new ServiceException(ResultMsg.ERROR_PASSWORD);
			}
			Integer loglimit = user.getLoglimit();
			String Iplimit = null;
			if(loglimit!=null && loglimit.intValue()==SysConstant.LOGLIMIT_1 ){
				Iplimit = user.getIplimit();
				if(StringHandler.isValidStr(Iplimit)){
					String LoginIp = IPUtils.getRealIp();
					String[] Ip =  Iplimit.split("-");
					String[][] iplimit1 = {{Ip[0],Ip[1]}};
					boolean flag = false;
					flag = IPUtils.IsIp(LoginIp, iplimit1);
					if(!flag){
						throw new ServiceException(ResultMsg.ERROR_IPLIMIT);//验证登录计算机的名称是否设置的计算机名称
					}
				}
			}else if(loglimit!=null && loglimit.intValue()==SysConstant.LOGLIMIT_2){
				Iplimit = user.getIplimit();
				if(StringHandler.isValidStr(Iplimit)){
					if(!Iplimit.equals(IPUtils.getcomputername())){
						throw new ServiceException(ResultMsg.ERROR_COMPUTERNAME);//验证登录计算机的ip 是否在设置的ip段
					}
				}
			}
			String validCode = (String)getSession().getAttribute(AuthImage.VALID_KEY);
			getSession().setAttribute(SysConstant.USER_KEY, user);
			LockManager.getInstance().removeLock(user);
				result = ResultMsg.getSuccessMsg("ok");
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 用户退出
	 * @return
	 * @throws Exception
	 */
	public String logout()throws Exception{
		UserEntity user = this.getCurUser();
		LockManager.getInstance().removeLock(user);
		session.invalidate();
		getSession().removeAttribute(SysConstant.USER_KEY);
		getSession().invalidate();
		return SUCCESS;
	}
	/**
	 * 用户第一次登录获取初始密码及其账号
	 */
	public String editPassWord() throws Exception{
		try {
			Long userId = getLVal("userId");
			if(!StringHandler.isValidObj(userId))  throw new ServiceException(ServiceException.ID_IS_NULL);
			UserEntity userEntity = userService.getEntity(userId);
			Integer pwdtip = userEntity.getPwdtip();
			if(pwdtip!=null && pwdtip.intValue()==0 ||pwdtip==null){
				String passWord = userEntity.getPassWord();
				Map<String,Object> appendParams = new HashMap<String, Object>();
				if(passWord.equals("111")){
					appendParams.put("passWord", passWord);
					String userName = userEntity.getUserName();
					appendParams.put("userName", userName);
//					if(pwdtip!=null && pwdtip.intValue()==0){
//						String pwdcycle = userEntity.getPwdcycle();
//						if(StringHandler.isValidStr(pwdcycle)){
//							if(pwdcycle.indexOf("##")==-1){
//								Date endDate = DateUtil.calculateEndDate(new Date(),Integer.parseInt(pwdcycle),"DAY");
//								pwdcycle = pwdcycle +"##"+ endDate.toString();
//							}else{
//								String[] enDate = pwdcycle.split("##");
//								if(DateUtil.dateFormat(enDate[1]).equals(DateUtil.dateFormat(new Date()))){
//									appendParams.put("pwdcycle", "-1");
//								}
//							}
//							
//						}
//					}
					result = FastJsonUtil.convertMapToJsonStr(appendParams);
				}
			}
			
		} catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/**
	 * 用户保存用户修改的密码
	 */
	public String savePassWord() throws Exception{
		try {
			String userName = getVal("userName");
			String passWord = getVal("passWord");
			if(!StringHandler.isValidObj(userName))  throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String,Object> Params = new SHashMap<String, Object>();
			Params.put("userName", userName);
			UserEntity userEntity = userService.getEntity(Params);
			userEntity.setPassWord(passWord);
			userEntity.setIsSystem(userEntity.getIsSystem());
			userService.saveOrUpdateEntity(userEntity);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
		} catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/**
	 * 获取 用户 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("indeptId#L,empName,isenabled#I");
//			map.put("postId", getLVal("postId"));
//			map.put("empName", getVal("empName"));
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = userService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
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
	 * 获取出纳人
	 */
	/**
	 * 获取 用户 列表
	 * @return
	 * @throws Exception
	 */
	public String cashierlist()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("postId", getLVal("postId"));
			map.put("empName", getVal("empName"));
			map.put("isenabled", SysConstant.OPTION_ENABLED);
			map.put("isLoan", SysConstant.POST_ISLOAN_1);
			DataTable dt = userService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
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
	 * 获取 用户的上级领导 列表
	 * @return
	 * @throws Exception
	 */
	public String getLeaders()throws Exception {
		try {
			Long leader = this.getCurUser().getLeader();
			if(null == leader){
				result = ResultMsg.getFailureMsg();
			}else{
				SHashMap<String, Object> params = new SHashMap<String, Object>();
				params.put("userId", leader);
				UserEntity userEntity = userService.getEntity(params);
				if(null == userEntity){
					result = ResultMsg.getFailureMsg();
				}else{
					Map<String,Object> appendParams = new HashMap<String, Object>();
					appendParams.put("userId", userEntity.getUserId());
					String userName = userEntity.getEmpName();
					if(!StringHandler.isValidStr(userName)) userName = userEntity.getUserName();
					appendParams.put("userName", userName);
					result = FastJsonUtil.convertMapToJsonStr(appendParams);
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
	 * 获取 用户 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			List<UroleEntity> uRoles = null;
			Long id = getLVal("userId");
			
			if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			UserEntity entity = userService.getEntity(id);
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("userId", SqlUtil.LOGIC_EQ+SqlUtil.LOGIC+id);
			uRoles = uroleService.getEntityList(map);
			List<RoleEntity> roles = RoleCache.getRoles(null);
			if(null == roles || roles.size() == 0) roles = roleService.getEntityList();
			StringBuffer bf=new StringBuffer();
			if(uRoles!= null && uRoles.size() > 0){
			for(RoleEntity role : roles){
					for(UroleEntity uRole : uRoles){
						if(role.getId().equals(uRole.getRoleId())){
							String roleName= role.getName();
							bf.append(roleName+",");
						}
					}
				}
			}
			
			Long postId = entity.getPostId();
			makeAccessIds(entity, false);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(StringHandler.isValidObj(postId) && postId != -1 ){
				PostEntity postEntity = postService.getEntity(postId);
				String postName = null;
				if(null != postEntity){
					postName = postEntity.getName();
					postName  = postId +"##" + postName;
				}
				appendParams.put("postName", postName);
			}
			Long leader = entity.getLeader();
			if(StringHandler.isValidObj(leader)){
				UserEntity leaderMan = userService.getEntity(leader);
				if(leaderMan!=null){
					appendParams.put("leaderName", leaderMan.getUserId()+"##"+leaderMan.getEmpName());
				}
			}
			
			if(bf.length() > 0){
				appendParams.put("roles", StringHandler.RemoveStr(bf,","));
			}
			result = ResultMsg.getSuccessMsg(entity, appendParams);
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
	 * 保存 用户 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			String postName = getVal("postName");
			String postId = null;
			if(StringHandler.isValidStr(postName)){
				postId = postName.split("##")[0];
				if(postId.indexOf("P")!=-1){
					postId = postId.substring(1);
				}
			}
			Integer isSystem = getIVal("isSystem");
			UserEntity user = getCurUser();
			UserEntity entity = BeanUtil.copyValue(UserEntity.class,getRequest());
			if(StringHandler.isValidIntegerNull(isSystem) && isSystem.intValue()!=0){
				entity.setIsSystem(isSystem);
			}
			entity.setPostId(Long.parseLong(postId));
			String userName = entity.getUserName();
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("userName", userName);
			params.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
			List<UserEntity> userEntity = userService.getEntityList(params);
			
			boolean flag = false;
			Long userId = entity.getUserId();
			if(!userEntity.isEmpty() && userEntity.size()>0){
				for(UserEntity uEntity : userEntity){
					String existName = uEntity.getUserName();
					if(existName.equals(userName)){
						Long userid = uEntity.getUserId();
						if(userid != userId){
							flag = true;
						}
						break;
					}
				}
			}
			
			if(flag){
				Map<String,Object> appendParams = new HashMap<String, Object>();
				appendParams.put("msg", StringHandler.formatFromResource(user.getI18n(), "Account.have", userName));
				result = ResultMsg.getFailureMsg(appendParams);
			}else{
				String leaderName = getVal("leaderName");
				if(StringHandler.isValidStr(leaderName)){
					String[] leaderIdName = leaderName.split("##");
					if(null != leaderIdName && leaderIdName.length>1){
						Long leader = Long.parseLong(leaderIdName[0]);
						entity.setLeader(leader);
					}
				}
				
				Long indeptId = getLVal("indeptId");
				Long poid=null;
				if(indeptId !=null){
					DepartmentEntity dentity=departmentService.getEntity(indeptId);
					if(dentity!=null){
						Integer potype = dentity.getPotype();
						if(potype==1){
							entity.setIncompId(dentity.getPoid());
						}else{
							 poid=dentity.getPoid();//如果上级单位类型是部门，就得上级部门id
							 DepartmentEntity sjbmentity1=null;
							 int   counut =1;
							 while(potype==2){
								Long sjbmpoid = null;
								if(counut==1){
									sjbmentity1=departmentService.getEntity(poid);
									sjbmpoid = sjbmentity1.getPoid();
								}else{
									sjbmpoid = sjbmentity1.getPoid();
									sjbmentity1=departmentService.getEntity(sjbmpoid);
								}
								counut++;
								potype=sjbmentity1.getPotype();
							}
						if(sjbmentity1.getPotype()==1){
									entity.setIncompId(sjbmentity1.getPoid());
								}
						}
					}
				}
				makeAccessIds(entity,true);
				String uroles = getVal("uroles");
				Map<String,Object> complexData = new HashMap<String, Object>();
				complexData.put("userEntity", entity);
				complexData.put("uroles", uroles);
				
				userService.doComplexBusss(complexData);
				result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
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
	
	private void makeAccessIds(UserEntity entity,boolean isRemove){
		Byte dataLevel = entity.getDataLevel();
		String str = null;
		switch (dataLevel.byteValue()) {
		case SysConstant.DATA_LEVEL_NO:	//无
		case SysConstant.DATA_LEVEL_SELF://本人
		case SysConstant.DATA_LEVEL_CURRDEPT://本部门
		case SysConstant.DATA_LEVEL_CURRCOMPANY://本公司
		case SysConstant.DATA_LEVEL_ALL://无限制
		case SysConstant.DATA_LEVEL_CURR_CHILDS_DEPT://本部门及所有子部门
			entity.setAccessIds("");
			break;
		case SysConstant.DATA_LEVEL_CUSTUSER:{//自定义用户数据
			str = "U";
			break;
		}case SysConstant.DATA_LEVEL_CUSTOMDEPT:{//自定义部门数据
			str = "D";
			break;
		}case SysConstant.DATA_LEVEL_CUSTCOMPANY:{//自定义公司数据
			str = "C";
			break;
		}default:
			break;
		}
		
		if(!StringHandler.isValidStr(str)) return;
		String accessIds = entity.getAccessIds();
		if(!StringHandler.isValidStr(accessIds)) return;
		String[] accessArr = accessIds.split("##");
		String accessNames = null;
		if(null != accessArr && accessArr.length>1){
			accessIds = accessArr[0];
			accessNames = accessArr[1];
		}
		if(isRemove){
			accessIds = accessIds.replace(str, "");
			if(StringHandler.isValidStr(accessNames)){
				entity.setAccessNames(accessNames);
			}
		}else{
			accessIds = str + (accessIds.replace(",", ","+str));
			accessNames = entity.getAccessNames();
			if(StringHandler.isValidStr(accessNames)){
				accessIds+="##"+entity.getAccessNames();
			}
		}
		entity.setAccessIds(accessIds);
	}
	
	/**
	 * 新增  用户 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = userService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("U", num);
			result = JsonUtil.getJsonString("code",code);
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
	 * 删除 用户 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用用户
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用 用户 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 起用用户
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			userService.enabledEntitys(ids, isenabled);
			result = ResultMsg.getSuccessMsg(this,sucessMsg);
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
	 * 获取指定的 用户 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			String userId = getVal("userId");
			SHashMap<String,Object> params = new SHashMap<String, Object>();
			if(StringHandler.isValidObj(userId)){
				params.put("id", userId);
			}
			UserEntity entity = userService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				makeAccessIds(entity, false);
				Long postId = entity.getPostId();
				Long leader = entity.getLeader();
				String leaderName = null;
				if(StringHandler.isValidObj(leader)){
					UserEntity user = userService.getEntity(leader);
					if(null !=user){
						leaderName = leader + "##" +user.getEmpName();
					}
				}
				if(postId==-1 ){
					if(postId==-1) result = ResultMsg.getFirstMsg(appendParams);
				}else if(StringHandler.isValidObj(postId)){
					PostEntity postEntity = postService.getEntity(postId);
					if(null != postEntity){
						appendParams.put("leaderName", leaderName);
						appendParams.put("postName", postId + "##"+postEntity.getName());
						result = ResultMsg.getSuccessMsg(entity, appendParams);
					}else{
						result = ResultMsg.getFirstMsg(appendParams);
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
	 * 获取指定的 用户 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			String userId = getVal("userId");
			SHashMap<String,Object> params = new SHashMap<String, Object>();
			if(StringHandler.isValidObj(userId)){
				params.put("id", userId);
			}
			UserEntity entity = userService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				makeAccessIds(entity, false);
				Long postId = entity.getPostId();
				Long leader = entity.getLeader();
				String leaderName = null;
				if(StringHandler.isValidObj(leader)){
					UserEntity user = userService.getEntity(leader);
					if(null !=user){
						leaderName = leader + "##" +user.getEmpName();
					}
				}
				if(null != postId){
					PostEntity postEntity = postService.getEntity(postId);
					if(null==postEntity){
						result = ResultMsg.getFirstMsg(appendParams);
					}else{
						appendParams.put("leaderName", leaderName);
						appendParams.put("postName", postId + "##" + postEntity.getName());
					}
				}
				result = ResultMsg.getSuccessMsg(entity, appendParams);
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
	 * 用户岗位调整 
	 * @return
	 * @throws Exception
	 */
	public String changePost()throws Exception {
		try {
			SHashMap<String, Object> params = getQParams("userId#L,postId#L,indeptId#L,incompId#L");
			Long userId = params.getvalAsLng("userId");
			Long postId = params.getvalAsLng("postId");
			Long indeptId = params.getvalAsLng("indeptId");
			Long incompId = params.getvalAsLng("incompId");
			if(null == userId) throw new ServiceException(ServiceException.ID_IS_NULL);
			if(null == postId) throw new ServiceException(ServiceException.POSTID_IS_NULL);
			UserEntity entity = userService.getEntity(userId);
			entity.setPostId(postId);
			entity.setIndeptId(indeptId);
			entity.setIncompId(incompId);
			BeanUtil.setModifyInfo(getCurUser(), entity);
			userService.updateEntity(entity);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
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
}
