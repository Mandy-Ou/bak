package com.cmw.service.impl.job;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.justobjects.pushlet.util.Log;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.SchedulerException;

import com.cmw.constant.SysConstant;
import com.cmw.constant.SysparamsConstant;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.kit.quartz.QuartzManager;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SpringContextUtil;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.SysparamsEntity;
import com.cmw.service.impl.fininter.external.impl.K3FinSysServiceImpl;
import com.cmw.service.inter.sys.SysparamsService;

/**
 * 业务JOB调度器管理类
 * @author chengmingwei
 *
 */
public class BussJobManager {
	static Logger logger = Logger.getLogger(K3FinSysServiceImpl.class);
	private static Map<String,Job> jobsMap = new HashMap<String, Job>();
	
	private static  final String[] SYSPARS_KEYS = new String[]{
		SysparamsConstant.OVER_TO_LATE_HOUR,
		SysparamsConstant.CACLUTE_PEN_DEL_HOUR
	};
	
	public static final void startJobs(){
		initJobsMap();
		startAllJobs();
	}
	
	
	private static final void startAllJobs(){
		List<SysparamsEntity> sysparamsList = null;
		SysparamsService sysparamsService = (SysparamsService)SpringContextUtil.getBean("sysparamsService");
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		String recodes = StringHandler.join(SYSPARS_KEYS);
		recodes = "'"+recodes.replace(",", "','")+"'";
		map.put("isenabled", SysConstant.OPTION_ENABLED);
		map.put("recode", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + recodes);
		try {
			sysparamsList = sysparamsService.getEntityList(map);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		if(null == sysparamsList || sysparamsList.size() == 0) return;
		
		logger.info("本次共:"+sysparamsList.size()+"个任务需要开启...");
		for(SysparamsEntity obj : sysparamsList){
			String recode = obj.getRecode();
			String val = obj.getVal();
			try {
				Job job = jobsMap.get(recode);
				String timeStr = "0 0 "+val+" * * ?";
				QuartzManager.addJob(recode, job, timeStr);
				logger.info("任务["+recode+"]开启完成...");
			} catch (SchedulerException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据时间变化：删除，修改，或添加调度器
	 * @param recode
	 * @param time
	 */
	public static final void changeJob(String recode,String time){
		try {
			if(StringHandler.IsInteger(time) && time.equals("-1")){/*表示删除调度器*/
				QuartzManager.removeJob(recode);
				logger.info("任务["+recode+"]被移除...");
				return;
			}
			
			String timeStr = "0 0 "+time+" * * ?";
			if(jobsMap.containsKey(recode)){
				QuartzManager.modifyJobTime(recode, timeStr);
				logger.info("任务["+recode+"]的时间更新为["+timeStr+"] ...");
			}else{
				Job job = jobsMap.get(recode);
				if(job == null) throw new SchedulerException("没有为:["+recode+"]配置 Job 对象");
				QuartzManager.addJob(recode, job, timeStr);
				logger.info("任务["+recode+"]添加到调度器工作组中，["+timeStr+"] ...");
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	private static final void initJobsMap(){
		jobsMap.put(SysparamsConstant.OVER_TO_LATE_HOUR, new Auto2OverJob());
		jobsMap.put(SysparamsConstant.CACLUTE_PEN_DEL_HOUR, new OverAmountCacluteJob());
	}
}
