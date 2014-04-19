package com.cmw.core.kit.word.jacob;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javassist.NotFoundException;

import com.cmw.core.base.exception.UtilException;
import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.kit.word.jacob.JacobWordManager.WordDataAction;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SystemUtil;
import com.jacob.com.Dispatch;

/**
 *  Word 模板处理类
 *  通过开源的 Jacob 来处理 Word 模板
 * @author chengmingwei
 *
 */
public class JacobWordTemplate {
	//---> jacob.dll 是否在 Windows\System32 目录
	private static boolean jacob_dll_isok = false;
	//---> 标识DLL库是否复制失败。 false : 复制成功，true : 复制失败
	private static boolean isFailure = false;
	//---> Window\System32 目录
	private static String win_system32_dir;
	private static final String JOCOB_DLL_FILENAME = "jacob.dll";
	//---> 模板数据源
	private DataTable dataSource;
	//---> 模板中追加的参数
	private Map<String,Object> params = new HashMap<String, Object>();
	private String templatePath;
	
	private String outfilePath;
	
	private JacobWordManager jacobMgr = new JacobWordManager();
	/**
	 * 检查 Dll文件是否可用
	 * @return
	 */
	static{
		copyJacobDll();
	}
	
	
	public JacobWordTemplate(String templatePath, DataTable dataSource) {
		this.templatePath = templatePath;
		this.dataSource = dataSource;
	}
	private static final String JACOB_COPY_FAILURE_MSG = "jocob.dll 复制到 ["+win_system32_dir+"]失败，无法生成Word 文件！";
	WordDataAction action = null;
	public void export() throws UtilException{
		if(isFailure) throw new UtilException(JACOB_COPY_FAILURE_MSG);
		if(null==action) action = getWordAction();
		jacobMgr.saveDoc(templatePath, outfilePath, action);
	}
	
	private WordDataAction getWordAction(){
		WordDataAction action = new WordDataAction(){
			@Override
			public void execute(Dispatch selection) {
				makeParams(selection);
				makeDataTable(selection);
			}
		};
		return action;
	}
	private static final String PARAM_SIGIN = "$P";
	/**
	 * 参数处理
	 * @param selection
	 */
	private void makeParams(Dispatch selection){
		if(null == params || params.size() == 0) return;
		Set<String> keys = params.keySet();
		for(String key : keys){
			String toFindText = PARAM_SIGIN+"{"+key.toLowerCase()+"}";
			String newText = (String)params.get(key);
			jacobMgr.replaceAll(selection,toFindText, newText);
		}
	}
	
	/**
	 * DataTable 数据处理  【暂未实现】
	 * @param selection
	 */
	private void makeDataTable(Dispatch selection){
		if(null == dataSource || dataSource.getRowCount() == 0) return;
		// TODO Auto-generated method stub
	}
	/**
	 * 复制 jacob.dll 到 Windows\System32 目录
	 */
	private static final void copyJacobDll(){
		String winDir = SystemUtil.getWinSetUpDir();
		win_system32_dir =  winDir + File.separator + "System32"+File.separator;
		String win_dllpath = win_system32_dir + JOCOB_DLL_FILENAME;
		jacob_dll_isok = FileUtil.exist(win_dllpath);
		if(jacob_dll_isok) return;
		String sourcePath = null;
		sourcePath = JacobWordTemplate.class.getResource("").getPath();
		sourcePath += File.separator + JOCOB_DLL_FILENAME;
		try {
			FileUtil.copyFile(sourcePath, win_dllpath);
		} catch (IOException e) {
			isFailure = true;
			e.printStackTrace();
		} catch (NotFoundException e) {
			isFailure = true;
			e.printStackTrace();
		}
	}
	
	public DataTable getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataTable dataSource) {
		this.dataSource = dataSource;
	}
	public String getTemplatePath() {
		return templatePath;
	}
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	public String getOutfilePath() {
		return outfilePath;
	}
	public void setOutfilePath(String outfilePath) {
		this.outfilePath = outfilePath;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
}
