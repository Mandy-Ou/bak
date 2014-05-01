package com.cmw.service.impl.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SpringContextUtil;
import com.cmw.service.inter.finance.OverdueDeductService;

/**
 * 自动转逾期JOB
 * 描述：系统自动将还款计划表中，应还款日大于当前日期的数据转为逾期状态 
 * @author chengmingwei
 *
 */
public class Auto2OverJob  implements Job {
	static Logger logger = Logger.getLogger(Auto2OverJob.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("开始自动转逾期业务...");
		long time = 0;
		Date startDate = new Date();
		OverdueDeductService overdueDeductService = (OverdueDeductService)SpringContextUtil.getBean("overdueDeductService");
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			overdueDeductService.calculateBatchLateDatas(map);
			
			Date endDate = new Date();
			time = endDate.getTime() - startDate.getTime();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		logger.info("自动转逾期业务，处理完成,共耗时["+time+"]秒...");
	}

}
