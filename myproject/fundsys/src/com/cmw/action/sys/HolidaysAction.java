package com.cmw.action.sys;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.HolidaysEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.sys.HolidaysService;


/**
 * 节假日设置  ACTION类
 * @author 程明卫
 * @date 2012-11-20T00:00:00
 */
@Description(remark="节假日设置ACTION",createDate="2012-11-20T00:00:00",author="程明卫",defaultVals="sysHolidays_")
@SuppressWarnings("serial")
public class HolidaysAction extends BaseAction {
	@Resource(name="holidaysService")
	private HolidaysService holidaysService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 节假日设置 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("isenabled#I,name,sdate,edate");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = holidaysService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取 节假日设置 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			HolidaysEntity entity = holidaysService.getEntity(Long.parseLong(id));
			Map<String,Object> appendParams = new HashMap<String, Object>();
			sded(entity, appendParams);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 保存 节假日设置 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			UserEntity user = getCurUser();
			HolidaysEntity entity = BeanUtil.copyValue(HolidaysEntity.class,getRequest());
			SHashMap<String, Object> appendParams = new SHashMap<String, Object>();
			Date Sdate = entity.getSdate();
			String sd = DateUtil.dateFormatToStr(Sdate);
			Date Edate = entity.getEdate();
			String ed = DateUtil.dateFormatToStr(Edate);
			appendParams.put("Sdate", sd);
			List<HolidaysEntity> sentity = holidaysService.getEntityList(appendParams);
			HashMap<String, Object> params = new HashMap<String, Object>();
			if(!sentity.isEmpty()){
				params.put("msg", StringHandler.formatFromResource(user.getI18n(), "sdate.have",sd));
				result = ResultMsg.getFailureMsg(params);
				outJsonString(result);
				return null;
			}else{
				appendParams.clear();
				appendParams.put("Edate", ed);
				List<HolidaysEntity> eentity = holidaysService.getEntityList(appendParams);
				if(!eentity.isEmpty()){
					params.put("msg", StringHandler.formatFromResource(user.getI18n(), "edate.have",ed));
					result = ResultMsg.getFailureMsg(params);
					outJsonString(result);
					return null;
				}
			}
			holidaysService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 新增  节假日设置 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = holidaysService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("H", num);
			result = JsonUtil.getJsonString("code",code);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 删除  节假日设置 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  节假日设置 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  节假日设置 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  节假日设置 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			holidaysService.enabledEntitys(ids, isenabled);
			result = ResultMsg.getSuccessMsg(this,sucessMsg);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 节假日设置 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = new SHashMap<String, Object>();
			params.put("id", getVal("id"));
			HolidaysEntity entity = holidaysService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				sded(entity, appendParams);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 节假日设置 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = new SHashMap<String, Object>();
			params.put("id", getVal("id"));
			HolidaysEntity entity = holidaysService.navigationNext(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			/*------> 可通过  appendParams 加入附加参数<--------*/
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				sded(entity, appendParams);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

	/** 开始日期和结束日转换成字符串
	 * @param entity 节假日实体
	 * @param appendParams  
	 */
	private void sded(HolidaysEntity entity, Map<String, Object> appendParams) throws Exception {
		Date sdate=entity.getSdate();
		Date edate=entity.getEdate();
		String form = DateUtil.dateFormatToStr(sdate);
		String form1 = DateUtil.dateFormatToStr(edate);
		appendParams.put("sdate", form);
		appendParams.put("edate", form1);
		result = ResultMsg.getSuccessMsg(entity, appendParams);
	}
	/**
	 * 根据输入的年份计算出着一年的所有星期六和星期日
	 */
	public String cal() throws Exception{
		try{
			String iniyear =getVal("iniyear");
			Integer iniyes =getIVal("iniyes");
			int year =Integer.parseInt(iniyear);
			if(!StringHandler.isValidObj(iniyes)){
				HashMap<String, Object> complexData = new HashMap<String, Object>();
				complexData.put("iniYear", iniyear);
				List<HolidaysEntity> holidays = holidaysService.getQuery(complexData);
				if(!holidays.isEmpty()){
					outJsonString("-1");
					return null;
				}
			}else if(iniyes.intValue()==1){
				SHashMap<String, Object> params = new SHashMap<String, Object>();
				params.put("iniyear", iniyear);
				holidaysService.deleteEntitys(params);
			}
		    Calendar calendar = new GregorianCalendar(year, 0, 1);
		    int i = 1; 
		    UserEntity user= this.getCurUser();
		    List<HolidaysEntity> list= new ArrayList<HolidaysEntity>(); 
		    while(calendar.get(Calendar.YEAR) < year + 1) {
		          calendar.set(Calendar.WEEK_OF_YEAR, i++);
		          calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		          HolidaysEntity entity =new HolidaysEntity();
		          entity.setCreateTime(new Date());
		          entity.setCreator(user.getUserId());
		          entity.setDeptId(user.getDeptId());
		          entity.setOrgid(user.getOrgid());
		          entity.setName("双休日");
		          entity.setEmpId(user.getEmpId());
		          String edate=null;
		          String sdate=null;
		          DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		          if(calendar.get(Calendar.YEAR) == year) {
		        		edate = String.format("%tF", calendar);
		            }else{
		            	edate = String.format("%tF", calendar);
		            	 sdate = attscs(edate, format1);
		            }
		            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		            if(calendar.get(Calendar.YEAR) == year) {
		            	String strFirstDay = calendar.get(Calendar.YEAR) + "-01-01";
				           if(edate.equals(strFirstDay))
				           {
				        	   sdate = (calendar.get(Calendar.YEAR)-1) + "-12-31";
				           }else{
				        	   sdate = attscs(edate, format1);
				           }
		            }
		            else{
			        	sdate = attscs(edate, format1);
		            }
		            int subed = Integer.parseInt(edate.substring(0, 4));
	            	if(subed != year) continue;
//		            System.out.println("开始日期："+sdate+",结束日期："+edate);
		            entity.setSdate(format1.parse(sdate));
		            entity.setEdate(format1.parse(edate));
		            list.add(entity);
		        }
		    holidaysService.batchSaveOrUpdateEntitys(list);
		    result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
		}catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

	/**
	 * 根据星期天算出星期六  
	 * 方法名称：According to the Sunday calculates Saturday
	 * @param edate
	 * @param format1
	 * @return
	 * @throws ParseException
	 */
	public String attscs(String edate, DateFormat format1)
			throws ParseException {
		String sdate;
		Date ed = format1.parse(edate);
		Calendar et= Calendar.getInstance();
		et.setTime(ed);
		et.add(Calendar.DAY_OF_YEAR, -1);
		Date dt=et.getTime();
		sdate = String.format("%tF", dt);
		return sdate;
	}
	
	
}
