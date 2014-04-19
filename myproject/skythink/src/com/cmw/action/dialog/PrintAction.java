package com.cmw.action.dialog;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.core.util.XmlHandler;
import com.cmw.service.impl.dialog.PrintServiceImpl;

/**
 * @author 程明卫 E-mail:chengmingwei_1984122@126.com
 * @version 创建时间：2013-11-15 下午22:52:31
 * 类说明 	专门处理通过模板进行打印的  ACTION   
 */
@Description(remark="打印 ACTION",createDate="2013-11-15",defaultVals="./dgPrint_")
@SuppressWarnings("serial")
public class PrintAction extends BaseAction {
	
	@Resource(name="printService")
	private PrintServiceImpl printService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 处理打印
	 * ./dgPrint_doprint.action
	 * @return
	 * @throws Exception
	 */
	public String doprint()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("tempFileName,printType");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			Integer printType = map.getvalAsInt("printType");
			if(null == printType) printType = SysConstant.PRINTTYPE_1;
			String tempFileName = map.getvalAsStr("tempFileName");
			JSONObject tempCfg = getCfgByXml(tempFileName);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			appendParams.put("tempPath", tempCfg.getString("tempPath"));
			switch (printType.intValue()) {
			case SysConstant.PRINTTYPE_1:{/*打印空白文档*/
				break;
			}case SysConstant.PRINTTYPE_2:{/*打印填充数据后的文档*/
				JSONArray dsArr = getDs(tempCfg.getJSONArray("dsCfg"));
				appendParams.put("datas", dsArr);
				break;
			}default:
				break;
			}
			result = FastJsonUtil.convertMapToJsonStr(appendParams);
		} catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	private JSONArray getDs(JSONArray cfgArr) throws ServiceException{
		JSONArray jsonArr = new JSONArray();
		if(null == cfgArr || cfgArr.size() == 0) throw new ServiceException("找不到打印配置参数：[cfgArr]");
		for(int i=0,count=cfgArr.size(); i<count; i++){
			JSONObject cfg = cfgArr.getJSONObject(i);
			String dstag = cfg.getString("dstag");
			Integer loadType =  cfg.getInteger("loadType");
			Integer dispType = cfg.getInteger("dispType");
			if(null == dispType) dispType = SysConstant.DISPTYPE_1;
			String dataCode = cfg.getString("dataCode");
			String urlparams = cfg.getString("urlparams");
			String columnNames = cfg.getString("columnNames");
			JSONArray cmnMappings = cfg.getJSONArray("cmnMappings");
			SHashMap<String, Object> params = getQParams(urlparams);
			params.put("userId", this.getCurUser().getUserId());
			if(null == loadType) throw new ServiceException("XML配置文件中找不到数加加载类型参数：[loadType]");
			SHashMap<String, Object> cfgParams = new SHashMap<String, Object>();
			cfgParams.put("columnNames", columnNames);
			DataTable ds = printService.getPrintDs(loadType, dataCode, cfgParams, params);
			if(null == ds || ds.getRowCount() == 0) continue;
			JSONObject jsonObj = new JSONObject();
			switch (dispType.intValue()) {
			case SysConstant.DISPTYPE_1:{/*单条显洋*/
				JSONObject jsonDs = ds.getJsonObj();
				if(StringHandler.isValidStr(dstag)){
					jsonObj.put(dstag, jsonDs);
				}else{
					jsonObj = jsonDs;
				}
				break;
			}case SysConstant.DISPTYPE_2:{/*多条列表显示*/
				JSONArray jsonDs = ds.getJsonList();
				if(StringHandler.isValidStr(dstag)){
					jsonObj.put(dstag, jsonDs);
				}else{
					jsonObj.put("ds", jsonDs);
				}
				break;
			}default:
				break;
			}
			if(!jsonObj.isEmpty()){
				if(null != cmnMappings && cmnMappings.size() > 0)jsonObj.put("cmnMappings", cmnMappings);
				jsonArr.add(jsonObj);
			}
		}
		return jsonArr;
	}
	/**
	 * 获取 打印模板 xml 配置
	 * @return
	 */
	private JSONObject getCfgByXml(String tempFileName){
		String xmlFilePath = FileUtil.getFilePath(getRequest(), "WEB-INF/classes/tempDsCfg/"+tempFileName+".xml");
		XmlHandler xmlHandler = new XmlHandler();
		String content = xmlHandler.getRootContent(xmlFilePath);
		JSONObject jsonObj = FastJsonUtil.convertStrToJSONObj(content);
		return jsonObj;
	}
}
