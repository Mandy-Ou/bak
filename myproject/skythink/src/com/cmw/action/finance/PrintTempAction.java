package com.cmw.action.finance;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.catalina.authenticator.DigestAuthenticator;

import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.CmnMappingEntity;
import com.cmw.entity.finance.PrintTempEntity;
import com.cmw.entity.finance.TdsCfgEntity;
import com.cmw.service.inter.crm.CustomerInfoService;
import com.cmw.service.inter.finance.CmnMappingService;
import com.cmw.service.inter.finance.PrintTempService;
import com.cmw.service.inter.finance.TdsCfgService;


/**
 * 合同模板表  ACTION类
 * @author 赵世龙
 * @date 2013-11-19T00:00:00
 */
@Description(remark="合同模板表ACTION",createDate="2013-11-19T00:00:00",author="赵世龙",defaultVals="fcpirntTemp_")
@SuppressWarnings("serial")
public class PrintTempAction extends BaseAction {
	@Resource(name="printTempService")
	private PrintTempService printTempService;
	
	@Resource(name="cmnMappingService")
	private CmnMappingService cmnMappingService;
	
	@Resource(name="tdsCfgService")
	private TdsCfgService tdsCfgService;
	
	@Resource(name="customerInfoService")
	private CustomerInfoService customerInfoService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 合同模板表 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("code,name,custType");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = printTempService.getResultList(map,getStart(),getLimit());
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
	 * 获取 合同模板表 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			PrintTempEntity entity = printTempService.getEntity(Long.parseLong(id));
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					
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
	 * 保存 合同模板表 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			PrintTempEntity entity = BeanUtil.copyValue(PrintTempEntity.class,getRequest());
			printTempService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(this,entity, ResultMsg.SAVE_SUCCESS);
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
	 * 新增  合同模板表 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = printTempService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("C", num);
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
	 * 删除  合同模板表 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  合同模板表 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  合同模板表 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  合同模板表 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			printTempService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 合同模板表 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			PrintTempEntity entity = printTempService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
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
	 * 获取指定的 合同模板表 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			PrintTempEntity entity = printTempService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
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
	 * 获取合同模板
	 * @return
	 * @throws Exception
	 */
	public String getClause()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			PrintTempEntity entity = printTempService.getEntity(Long.parseLong(id));
			String txtPath = entity.getTempPath();
			String content = "";
			if(StringHandler.isValidStr(txtPath)){
				String absFilePath = FileUtil.getFilePath(getRequest(), txtPath);
				content = FileUtil.ReadFileToStr(absFilePath);
				String count=getVal("count");
				if(StringHandler.isValidStr(count)&&!"id".equals(count)&&content.indexOf('{')!=-1){
					SHashMap<String, Object> map = getQParams(count);
					map.put("tempId", id);
					List<Object> dataSource = new ArrayList<Object>();
					DataTable dt= new DataTable();
					dt.setDataSource(dataSource);
					DataTable dtTds = tdsCfgService.getResultList(map);
					if(dtTds != null && dtTds.getRowCount()>0 ){
						for(int i =0;i< dtTds.getRowCount();i++){
							List<CmnMappingEntity> list=new ArrayList<CmnMappingEntity>();
							Long tdsId=Long.parseLong(dtTds.getString(i, "id").substring(1));
							SHashMap<String, Object> m=new SHashMap<String, Object>();
							m.put("tdsId", tdsId);
							DataTable dtDepartment = cmnMappingService.getCmnList(m);
							String cmns="";
							int a=dtDepartment.getRowCount();
							for(int j=0;j<a;j++){
								CmnMappingEntity cmnEnty=new CmnMappingEntity();
								String cName=dtDepartment.getString(j,"text");
								String dataType=dtDepartment.getString(j,"dataType");
								String fmt=dtDepartment.getString(j,"fmt");
								cmns+=(j<a-1)?cName+",":cName;
								cmnEnty.setName(cName);
								cmnEnty.setFmt(fmt);
								cmnEnty.setDataType(Integer.parseInt(dataType));
								list.add(cmnEnty);
							}
							TdsCfgEntity tdsEntity=tdsCfgService.getEntity(tdsId);
							dt=printTempService.getTempDetail(tdsEntity, cmns, map);
							if(dt!=null && dt.getRowCount()>0){
								dt.setColumnNames(cmns);
								String strCount="";
								for (int s = 0; s < dt.getRowCount(); s++) {
									String htCode=tdsEntity.getHtmlCode();
									for (CmnMappingEntity enty : list) {
										String newdata=dt.getString(s,enty.getName());
										if(StringHandler.isValidStr(newdata) && enty.getDataType()==2){
											if(newdata.indexOf(',')!=-1){
												newdata=newdata.replace(",", "");
											}
											newdata+=" (大写："+BigDecimalHandler.digitUppercase(Double.parseDouble(newdata))+")";
										}else if(StringHandler.isValidStr(newdata)&&enty.getDataType()==5){
											newdata+="%";
										}else if(StringHandler.isValidStr(newdata)&&enty.getDataType()==3){
											SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
											newdata=formatter.format(formatter.parse(newdata));
										}
										if("guarantorIds".equals(enty.getName())){
											if(newdata.indexOf(',')!=-1){
												String gurantor[]=newdata.split(",");
												newdata="";
												for (int mm=0;mm<gurantor.length;mm++) {
													String guranStr=customerInfoService.getEntity(StringHandler.getLongVal(gurantor[mm])).getName();
													newdata+=(mm==gurantor.length-1)?guranStr:guranStr+",";
											}
											}else{
												newdata=customerInfoService.getEntity(StringHandler.getLongVal(newdata)).getName();
											}
										}
										String printType=getVal("printType");
										if(tdsEntity.getDispType()==2&&StringHandler.isValidStr(tdsEntity.getHtmlCode())){
											if("2".equals(printType))
												htCode=htCode.replace("{"+enty.getName()+"}", newdata);
											else
												htCode=htCode.replace("{"+enty.getName()+"}", "");
										}else{
											if("2".equals(printType))
												content=content.replace("{"+enty.getName()+"}", newdata);
											else	
												content=content.replace("{"+enty.getName()+"}", "");
										}
									}
									strCount+=htCode;
								}
								if(tdsEntity.getDispType()==2&&StringHandler.isValidStr(tdsEntity.getHtmlCode())){
									content=content.replace("<!--"+tdsEntity.getDstag()+"-->", strCount);
								}
							}else{
								for (CmnMappingEntity enty : list) {
									content=content.replace("{"+enty.getName()+"}", "");
								}
							}
						}
					}
				}
			}
			Map<String,Object> appendParams = new HashMap<String, Object>();
			appendParams.put("id", entity.getId());
			appendParams.put("txtPath", entity.getTempPath());
			appendParams.put("content", content);
			result = ResultMsg.getSuccessMsg(appendParams);
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
	 * 保存合同模板
	 * @return
	 * @throws Exception
	 */
	public String saveClause()throws Exception {
		try {
			Long id = getLVal("id");
			String content = getVal("content");
			String txtPath = getVal("txtPath");
			PrintTempEntity entity = printTempService.getEntity(id);
			if(!StringHandler.isValidStr(content)){
				txtPath = null;
				if(StringHandler.isValidStr(txtPath)){
					String fileName = FileUtil.getFilePath(getRequest(), txtPath);
					FileUtil.delFile(fileName);
				}
			}else{
				if(!StringHandler.isValidStr(txtPath)){
					String subprocess_clause_path = StringHandler.GetResValue("tontract_template_path");
					String absPath = FileUtil.getFilePath(getRequest(), subprocess_clause_path);
					if(!FileUtil.exist(absPath)) FileUtil.creatDictory(absPath);
					String fileName = System.currentTimeMillis()+".txt";
					txtPath = subprocess_clause_path+fileName;
					FileUtil.writeStrToFile(absPath, fileName, content);
				}else{
					String filePath = FileUtil.getFilePath(getRequest(), txtPath);
					FileUtil.writeStrToFile(filePath,content);
				}
			}
			entity.setTempPath(txtPath);
			printTempService.updateEntity(entity);
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
