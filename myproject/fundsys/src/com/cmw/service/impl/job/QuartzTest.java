package com.cmw.service.impl.job;

import org.quartz.Job;

import com.cmw.core.kit.quartz.QuartzManager;

public class QuartzTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		  Job job = new OverAmountCacluteJob();
	        String job_name = "11";
	        try {
	            System.out.println("【系统启动】");
	            //"0/5 * * * * ?"
	            QuartzManager.addJob(job_name, job, "0 0 17 * * ?");
	            Thread.sleep(10000);
	            
	            System.out.println("【修改时间】");
	            QuartzManager.modifyJobTime(job_name, "0 2 17 * * ?");            
	            Thread.sleep(20000);
	            
//	            System.out.println("【移除定时】");
//	            QuartzManager.removeJob(job_name);
//	            Thread.sleep(10000);

//	            System.out.println("\n【添加定时任务】");
//	            QuartzManager.addJob(job_name, job, "0/5 * * * * ?");

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	}

}
