package com.cmw.core.kit.word.jacob.example;

import java.util.HashMap;
import java.util.Map;

import com.cmw.core.base.exception.UtilException;
import com.cmw.core.kit.word.jacob.JacobWordTemplate;
import com.cmw.core.util.DataTable;

public class WordTemplateTest {
	static final String TEMPLATEPATH = "E:/dev/skythink/src/com/cmw/core/kit/word/jacob/example/110719微型企业借款合同及担保合同（定）.doc";
	static final String OUTFILEPATH = "E:/dev/skythink/src/com/cmw/core/kit/word/jacob/example/110719微型企业借款合同及担保合同（定）_导出.doc";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DataTable dataSource = new DataTable();
		JacobWordTemplate wordTmpt = new JacobWordTemplate(TEMPLATEPATH, dataSource);
		
		try {
			wordTmpt.setOutfilePath(OUTFILEPATH);
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("年份", "2011");
			params.put("字", "微借");
			params.put("号", "100901");
			params.put("公司名称", "向南小额贷款");
			
			wordTmpt.setParams(params);
			wordTmpt.export();
			
		} catch (UtilException e) {
			e.printStackTrace();
		}
	}
	

}
