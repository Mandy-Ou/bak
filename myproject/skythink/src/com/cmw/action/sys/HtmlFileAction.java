package com.cmw.action.sys;

import com.cmw.constant.ResultMsg;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.kit.file.FileUtil;

/**
 * @author 程明卫 E-mail:chengmingwei_1984122@126.com
 * @version 创建时间：2010-6-15 下午12:14:31
 * 类说明 	Html文件保存为txt，供 excel.jsp 调用（将HTML转Excel） ACTION   
 */
@Description(remark="卡片菜单 ACTION",createDate="2010-6-15",defaultVals="sysHtmlFile_")
@SuppressWarnings("serial")
public class HtmlFileAction extends BaseAction {
	private String result = ResultMsg.GRID_NODATA;
	
	/**
	 * 保存HTML为文本文件
	 * @return	./sysHtmlFile_save.action
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			String reportHtml = getVal("reportHtml");
			String dir = "attachments/htmlTemp/";
			String fileName = System.currentTimeMillis()+".txt";
			String absFileName = FileUtil.getFilePath(getRequest(), dir);
			FileUtil.writeStrToFile(absFileName, fileName, reportHtml);
			String filePath = dir + fileName;
			result = ResultMsg.getSuccessMsg(this, filePath);
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
}
