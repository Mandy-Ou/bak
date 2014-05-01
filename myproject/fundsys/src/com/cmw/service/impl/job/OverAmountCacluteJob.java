package com.cmw.service.impl.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.SpringContextUtil;
import com.cmw.service.inter.finance.OverdueDeductService;
/**
 * 罚息滞纳金自动计算JOB
 * @author chengmingwei
 *
 */
public class OverAmountCacluteJob implements Job {
	static Logger logger = Logger.getLogger(OverAmountCacluteJob.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("罚息滞纳金计算业务...");
		long time = 0;
		Date startDate = new Date();
		OverdueDeductService overdueDeductService = (OverdueDeductService)SpringContextUtil.getBean("overdueDeductService");
		try {
			overdueDeductService.calculateLateDatas();
			Date endDate = new Date();
			time = endDate.getTime() - startDate.getTime();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		logger.info("罚息滞纳金计算业务，处理完成,共耗时["+time+"]秒...");
	}

}
