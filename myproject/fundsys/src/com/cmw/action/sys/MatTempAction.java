package com.cmw.action.sys;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.MatParamsEntity;
import com.cmw.entity.sys.MatSubjecEntity;
import com.cmw.entity.sys.MatTempEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.entity.sys.VarietyEntity;
import com.cmw.service.inter.sys.MatParamsService;
import com.cmw.service.inter.sys.MatSubjecService;
import com.cmw.service.inter.sys.MatTempService;
import com.cmw.service.inter.sys.VarietyService;


/**
 * 资料模板  ACTION类
 * @author pdt
 * @date 2012-12-26T00:00:00
 */
@Description(remark="资料模板ACTION",createDate="2012-12-26T00:00:00",author="pdt",defaultVals="sysMatTemp_")
@SuppressWarnings("serial")
public class MatTempAction extends BaseAction {
	@Resource(name="matTempService")
	private MatTempService matTempService;
	@Resource(name="matSubjecService")
	private MatSubjecService matSubjecService;
	@Resource(name="matParamsService")
	private MatParamsService matParamsService;
	@Resource(name="varietyService")
	private VarietyService varietyService;
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 资料模板 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = matTempService.getResultList(map,getStart(),getLimit());
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
	 * 
	 */
	public String getMat() throws Exception{
		try {
			String breed = getVal("breed");
			if(!StringHandler.isValidSigin(breed)) new ServiceException(ServiceException.BREED_IS_NULL);
			Integer custType = getIVal("custType");
			if(!StringHandler.isValidObj(custType)) new ServiceException(ServiceException.CUSTTYPE_IS_NULL);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("breedId", Long.parseLong(breed));
			params.put("custType", custType);
			params.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
			MatTempEntity entity = matTempService.getEntity(params);
			HashMap<String, Object> appParams = new HashMap<String, Object>();
			if(entity != null){
				setNameAndId(entity,appParams);
			}else {
				params.remove("breedId");
				MatTempEntity entityTwo = matTempService.getEntity(params);
				if(entityTwo != null){
					setNameAndId(entityTwo,appParams);
				}
			}
			result = ResultMsg.getSuccessMsg(appParams);
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
	/**设置	模板名称和Id
	 * @param appParams
	 */
	private void setNameAndId(MatTempEntity entity,
			HashMap<String, Object> appParams) {
		String name = entity.getName();
		Long id = entity.getId(); 
		appParams.put("name", name);
		appParams.put("id", id);
	}
	
	
	/**
	 * 获取 资料模板 详情
	 * @return
	 * @throws Exception
	 */
	public String getTemp()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
            SHashMap< String, Object> map = new SHashMap<String, Object>();//定义一个Hashmap 
            map.put("tempId", id);
            map.put("isenabled", SysConstant.OPTION_ENABLED);
            /*--- step 1 : 读出清单标题，并存入 JSONArray 中 ---*/
            List<MatSubjecEntity> subjects = matSubjecService.getEntityList(map);//取到matSubjec表放入list集合中
            Collections.sort(subjects,new Comparator<MatSubjecEntity>(){   
                public int compare(MatSubjecEntity arg0, MatSubjecEntity arg1) {   
                    return arg0.getOrderNo().compareTo(arg1.getOrderNo());   
                 }   
             });   
            StringBuffer  sb = new StringBuffer();//每个方法都能有效地将给定的数据转换成字符串
           JSONArray subjectArr = new JSONArray();//定义一个指定的json数组
           for(MatSubjecEntity subject : subjects){//循环读出所以的id;
        	   Long subjectId =subject.getId();//得到所有id; 
        	   sb.append(subjectId+",");//附加起来
        	   String name = subject.getName();//得到标题名称
        	   JSONObject obj = new JSONObject();//定义一个json对象
        	   obj.put("id1", subjectId);//用键值对的方法加到obje 的put中
        	   obj.put("name", name);
        	   obj.put("items", new JSONArray());
        	   subjectArr.add(obj);//把object放入数组中
           }
          
           /*--- step 2 : 根据清单标题ID列表，取出所有的清单项  ---*/
           map.clear();//清空map中的所有
           String subjectIds = StringHandler.RemoveStr(sb);//移除带豆号的字符串
           ArrayList<MatParamsEntity> matParamsList =  new ArrayList<MatParamsEntity>();
           if(StringHandler.isValidStr(subjectIds)){//判断字符串是否为空
        	     map.put("subjectId", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+subjectIds);//
        	     map.put("isenabled", SysConstant.OPTION_ENABLED);
        	     matParamsList.addAll(matParamsService.getEntityList(map));
           }
           Collections.sort(matParamsList,new Comparator<MatParamsEntity>(){   
               public int compare(MatParamsEntity arg0, MatParamsEntity arg1) {   
                   return arg0.getOrderNo().compareTo(arg1.getOrderNo());   
                }   
            }); 
           /*--- step 3 : 根据清单标题ID与清单项标题ID作比较，相等则存入该清单标题的 items(JSONArray) 中  ---*/
           if(null != matParamsList && matParamsList.size() > 0){//
        	   for(int i=0,count=subjectArr.size(); i<count; i++){
        		   JSONObject jsonObj = subjectArr.getJSONObject(i);
        		   Long subjectId = jsonObj.getLong("id1");
        		   JSONArray items = jsonObj.getJSONArray("items");
        		   for(MatParamsEntity matParams : matParamsList){
            		   Long _subjectId = matParams.getSubjectId();
            		   if(subjectId.equals(_subjectId)){
            			   JSONObject obj = new JSONObject();
                    	   obj.put("id2", matParams.getId());
                    	   obj.put("orderNo", matParams.getOrderNo());
                    	   obj.put("allowBlank", matParams.getAllowBlank());
                    	   obj.put("isAttach", matParams.getIsAttach());
                    	   obj.put("remark", matParams.getIsRemark());
                    	   obj.put("name", matParams.getName());
            			   items.add(obj); 
            		   }
            	   }
        	   }
           }
           result = subjectArr.toJSONString();
           
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
	
	
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			MatTempEntity entity = matTempService.getEntity(Long.parseLong(id));
            Long breedId = entity.getBreedId();
            
            final VarietyEntity breedEntity = varietyService.getEntity(breedId);
			result = FastJsonUtil.convertJsonToStr(entity, new Callback(){
				public void execute(JSONObject jsonObj) {
				  if(null != breedEntity){
					  String oldbreedName = breedEntity.getName();
					  jsonObj.put("oldbreedName", oldbreedName);
		            }
				}
			});
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
	 * 保存 资料模板 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			    UserEntity user = this.getCurUser();
				MatTempEntity entity = BeanUtil.copyValue(MatTempEntity.class,getRequest());
				Long breedId = entity.getBreedId();
				SHashMap<String, Object> params = new SHashMap<String, Object>();
				params.put("breedId", breedId);
				params.put("isenabled",SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
				List<MatTempEntity> matList = matTempService.getEntityList(params);
				params.remove("breedId");
				params.put("id", breedId);
				VarietyEntity  varietyEntity =  varietyService.getEntity(params);
				String name =  "";
				if(varietyEntity != null){
					name = varietyEntity.getName();
				}
				boolean flag = false;
				Long id = entity.getId();
				if(!matList.isEmpty()){
					for(MatTempEntity x: matList){
						Long matId = x.getId();
						if(id != matId){
							flag = true;
						}
					}
				}
				HashMap<String, Object> appendParams = new HashMap<String, Object>();
				if(flag){
					appendParams = new HashMap<String, Object>();
					appendParams.put("msg", StringHandler.formatFromResource(user.getI18n(), "matTemp.exist.error",name));
					result = ResultMsg.getFailureMsg(appendParams);
				}else{
					matTempService.saveOrUpdateEntity(entity);
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
	
	/**
	 * 复制资料模板 
	 * @return
	 * @throws Exception
	 */
	public String copy()throws Exception {
		try {	
				MatTempEntity entity = BeanUtil.copyValue(MatTempEntity.class,getRequest());
				UserEntity user = getCurUser();
				Long id = entity.getId();
				if(!StringHandler.isValidObj(id))  throw new ServiceException(ServiceException.ID_IS_NULL);
				MatTempEntity matTempEntity =  matTempService.getEntity(id);
				SHashMap<String, Object> params = new SHashMap<String, Object>();
				params.put("tempId", id);
				List<MatSubjecEntity> matSubjecEntityList  = matSubjecService.getEntityList(params);
				if(!matSubjecEntityList.isEmpty()&& matSubjecEntityList.size()>0){
					StringBuffer buffer = new StringBuffer();
					for(MatSubjecEntity X : matSubjecEntityList){
						String Mids = X.getId().toString();
						buffer.append(Mids+",");
					}
					String sb = StringHandler.RemoveStr(buffer);
					if(StringHandler.isValidStr(sb)){
						params.clear();
						String[]  subjectId = sb.split(",");
						ArrayList<MatParamsEntity> MatParamsEntity  = new ArrayList<MatParamsEntity>();
						
						for(String subId : subjectId ){
							params.put("subjectId", subId);
							MatParamsEntity.addAll(matParamsService.getEntityList(params));
						}
						
						if(MatParamsEntity.size()>0){
							matTempEntity.setId(null);
							matTempEntity.setName(entity.getName());
							matTempEntity.setBreedId(entity.getBreedId());
							matTempService.saveOrUpdateEntity(matTempEntity);
							Long afterSaveId  = matTempEntity.getId();
							List<MatSubjecEntity> matSubEntity = matSubjecEntityList;
							for(MatSubjecEntity Y  : matSubEntity){
								Y.setId(null);
								Y.setTempId(afterSaveId);
								Y.setCreator(user.getCreator());
							}
							
//							matSubjecService.batchSaveOrUpdateEntitys(matSubEntity);
//							params.clear();
//							params.put("tempId", afterSaveId);
//							List<MatSubjecEntity> afterSaveEntitys = matSubjecService.getEntityList(params);
					        int i = 0;
							for(MatSubjecEntity Z:  matSubEntity){
								Long afterId  = Z.getId();
								String[]  subid = sb.split(",");
								int j=0;
								for(String sid : subid){
									Long suid  = Long.parseLong(sid);
									if(j!=i ) {
										j++;
										continue;
									}
									for(MatParamsEntity M : MatParamsEntity){
										Long subId = M.getSubjectId();
										if(subId == suid){
											M.setId(null);
											M.setSubjectId(afterId);
											M.setCreator(user.getCreator());
										}
									}
										i++;
										break;
								}
							}
							matParamsService.batchSaveOrUpdateEntitys(MatParamsEntity);
						}else{
							matTempEntity.setId(null);
							matTempEntity.setName(entity.getName());
							matTempEntity.setBreedId(entity.getBreedId());
							matTempService.saveOrUpdateEntity(matTempEntity);
						}
					}else{
						matTempEntity.setId(null);
						matTempEntity.setName(entity.getName());
						matTempEntity.setBreedId(entity.getBreedId());
						matTempService.saveOrUpdateEntity(matTempEntity);
						Long afterSaveId  = matTempEntity.getId();
						List<MatSubjecEntity> matSubEntity = matSubjecEntityList;
						for(MatSubjecEntity Y  : matSubEntity){
							Y.setId(null);
							Y.setTempId(afterSaveId);
						}
						matSubjecService.batchSaveOrUpdateEntitys(matSubEntity);
					}
				}else{
					matTempEntity.setId(null);
					matTempEntity.setName(entity.getName());
					matTempEntity.setBreedId(entity.getBreedId());
					matTempService.saveOrUpdateEntity(matTempEntity);
				}
				
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
	/**
	 * 新增  资料模板 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = matTempService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("F", num);
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
	 * 删除  资料模板 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  资料模板 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  资料模板 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  资料模板 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			String id = null;
			if(StringHandler.isValidStr(ids)){
				id = ids.substring(1);
			}
			Integer isenabled = getIVal("isenabled");
			matTempService.enabledEntitys(id, isenabled);
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
	 * 获取指定的 资料模板 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			MatTempEntity entity = matTempService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				 Long breedId = entity.getBreedId();
				 VarietyEntity breedEntity = varietyService.getEntity(breedId);
				 String oldbreedName = breedEntity.getName();
				 appendParams.put("oldbreedName", oldbreedName);
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
	 * 获取指定的 资料模板 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			MatTempEntity entity = matTempService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				Long breedId = entity.getBreedId();
				 VarietyEntity breedEntity = varietyService.getEntity(breedId);
				 String oldbreedName = breedEntity.getName();
				 appendParams.put("oldbreedName", oldbreedName);
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
	
}
