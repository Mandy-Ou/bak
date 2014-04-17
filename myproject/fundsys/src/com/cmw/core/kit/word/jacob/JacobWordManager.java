package com.cmw.core.kit.word.jacob;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * Jocob Word 管理类
 * @author chengmingwei
 *
 */
public class JacobWordManager {
	
	  private Logger log=Logger.getLogger(JacobWordManager.class);  
	    /**   
	     * 打开文件   
	     * @param documents    
	     * @param inputDocPath   
	     * @return   
	     */    
	    private Dispatch open(Dispatch documents,String inputDocPath){     
	        return Dispatch.call(documents,"Open",inputDocPath).toDispatch();     
	    }     
	         
	    /**   
	     * 选定内容   
	     * @param word   
	     * @return   
	     */    
	    private Dispatch select(ActiveXComponent word){     
	        return word.getProperty("Selection").toDispatch();     
	    }     
	         
	    /**   
	     * 把插入点移动到文件首位置   
	     * @param selection   
	     */    
	    private void moveStart(Dispatch selection){     
	        Dispatch.call(selection, "HomeKey",new Variant(6));     
	    }     
	         
	    /**   
	     * 从选定内容或插入点开始查找文本   
	     * @param selection 选定内容   
	     * @param toFindText    要查找的文本   
	     * @return  true：查找到并选中该文本；false：未查找到文本。   
	     */    
	    private boolean find(Dispatch selection,String toFindText){     
	        //从selection所在位置开始查询     
	        Dispatch find = Dispatch.call(selection, "Find").toDispatch();     
	        //设置要查找的内容     
	        Dispatch.put(find, "Text", toFindText);     
	        //向前查找     
	        Dispatch.put(find, "Forward", "True");     
	        //设置格式     
	        Dispatch.put(find,"format","True");     
	        //大小写匹配     
	        Dispatch.put(find, "MatchCase", "True");     
	        //全字匹配     
	        Dispatch.put(find, "MatchWholeWord", "True");     
	        //查找并选中     
	        return Dispatch.call(find, "Execute").getBoolean();     
	    }     
	         
	    /**   
	     * 把选定内容替换为设定文本   
	     * @param selection   
	     * @param newText   
	     */    
	    private void replace(Dispatch selection,String newText){     
	        Dispatch.put(selection, "Text", newText);     
	    }     
	         
	    /**   
	     * 全局替换   
	     * @param selection    
	     * @param oldText   
	     * @param replaceObj   
	     */    
	    public void replaceAll(Dispatch selection,String oldText,Object replaceObj){     
	        moveStart(selection);     
//	        log.info("replace--"+oldText);  
	        if(oldText.startsWith("{table1}")){  
	            createTable(selection,oldText,(List<String[]>) replaceObj);  
	        }else if(oldText.startsWith("{zp}")){  
	            log.info("替换图片");  
	            replaceImage(selection, oldText, replaceObj.toString());  
	        }else{  
	            String newText = (String) replaceObj;  
	            while (find(selection, oldText)) {  
	                replace(selection, newText);  
	                Dispatch.call(selection, "MoveRight");  
	            }  
	        }   
	    }     
	    /** 
	     * image 
	     * @param selection 
	     * @param toFindText 
	     * @param imagePath 
	     */  
	    public void replaceImage(Dispatch selection,String toFindText, String imagePath) {   
	        if(find(selection,toFindText)){  
	         Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),  
	                 "AddPicture", imagePath);  
	               Dispatch.call(selection, "MoveRight");  
	        }  
	     }   
	    /** 
	     * table 
	     */  
	    public void createTable(Dispatch selection, String tableName, List<String[]> dataList) {  
	        if(find(selection,tableName)){  
	            int row=dataList.size();  
	          Dispatch tables = Dispatch.get(selection, "Tables").toDispatch();   
	          Dispatch range = Dispatch.get(selection, "Range").toDispatch();   
	          Dispatch newTable = Dispatch.call(tables, "Add", range,   
	                          new Variant(row), new Variant(5)).toDispatch();   
	          for(int i=0 ;i<row;i++){  
	              String[] str=dataList.get(i);  
	              for(int j=0;j<str.length;j++){  
	                  log.info("行="+i+" 列="+j+"值="+str[j]);  
	                  String s=str[j];  
	                  Dispatch cell = Dispatch.call(newTable, "Cell", new Variant(i+1), new Variant(j+1)).toDispatch();   
	                  Dispatch.call(cell, "Select");   
	                  Dispatch.put(selection, "Text", s);  
	              }  
	          }  
	          Dispatch.call(selection, "MoveRight");  
	        }  
	          
	    }   
	  
	    /**   
	     * 打印   
	     * @param document   Word Document 对象
	     */    
	    private void print(Dispatch document){     
	        Dispatch.call(document, "PrintOut");     
	    }     
	         
	    /**   
	     * 保存文件   
	     * @param word   
	     * @param outputPath   
	     */    
	    private void save(ActiveXComponent word,String outputPath){
	        Dispatch.call((Dispatch)Dispatch.call(word, "WordBasic").getDispatch(), "FileSaveAs",outputPath);     
	    }     
	         
	    /**   
	     * 关闭文件   
	     * @param doc   
	     */    
	    private void close(Dispatch doc){     
	        Dispatch.call(doc, "Close",new Variant(true));     
	    }     
	         
	    /**   
	     * 保存打印doc文档   
	     * @param inputDocPath   
	     * @param outPutDocPath   
	     * @param data   
	     * @param isPrint   
	     */    
	    public void saveDoc(String inputDocPath,String outPutDocPath,Map<String,Object> data,boolean isPrint){     
	        //初始化com的线程     
	        ComThread.InitSTA();     
	        //word运行程序对象     
	        ActiveXComponent word = new ActiveXComponent(WORD_APPLICATION_KEY);     
	        //文档对象     
	        Dispatch wordObject = (Dispatch) word.getObject();     
	        //设置属性  Variant(true)表示word应用程序可见     
	        Dispatch.put((Dispatch)wordObject,VISIBLE_KEY, new Variant(false));     
	        //word所有文档     
	        Dispatch documents = word.getProperty(DOCUMENT_KEY).toDispatch();     
	        //打开文档     
	        Dispatch document = this.open(documents,inputDocPath);     
	        Dispatch selection = this.select(word);     
	        Iterator keys = data.keySet().iterator();     
	        String oldText;     
	        Object newValue;     
	        while(keys.hasNext()){     
	            oldText = (String)keys.next();     
	            newValue = data.get(oldText);     
	            this.replaceAll(selection, oldText, newValue);     
	        }     
	        //是否打印     
	        if(isPrint){     
	            this.print(document);     
	        }     
	        this.save(word,outPutDocPath);     
	        this.close(document);     
	        word.invoke(QUIT_KEY, new Variant[0]);     
	        //关闭com的线程     
	        ComThread.Release();     
	    }     
     
	    /**   
	     * 保存打印doc文档   
	     * @param inputDocPath   
	     * @param outPutDocPath   
	     * @param data   
	     * @param isPrint   
	     */    
	    public void saveDoc(String inputDocPath,String outPutDocPath,WordDataAction action){     
	    	if(null==action) return;
	        //初始化com的线程     
	        ComThread.InitSTA();     
	        //word运行程序对象     
	        ActiveXComponent word = new ActiveXComponent(WORD_APPLICATION_KEY);     
	        //文档对象     
	        Dispatch wordObject = (Dispatch) word.getObject();     
	        //设置属性  Variant(true)表示word应用程序可见     
	        Dispatch.put((Dispatch)wordObject,VISIBLE_KEY, new Variant(false));     
	        //word所有文档     
	        Dispatch documents = word.getProperty(DOCUMENT_KEY).toDispatch();     
	        //打开文档     
	        Dispatch document = this.open(documents,inputDocPath);     
	        Dispatch selection = this.select(word);     
	        action.execute(selection);
	        this.save(word,outPutDocPath);     
	        this.close(document);     
	        word.invoke(QUIT_KEY, new Variant[0]);     
	        //关闭com的线程     
	        ComThread.Release();     
	    }     
	 
	  
        
    /**
     * Word 数据处理接口
     * @author chengmingwei
     * 
     */
     interface WordDataAction{
    	void execute(Dispatch selection);
     }
    /**
     * 代表 Word 文档应用程序对象的KEY
     */
    public static final String WORD_APPLICATION_KEY = "Word.Application";
    /**
     * 代表 Word 文档中的所有文档集合的KEY
     */
    public static final String DOCUMENT_KEY = "Documents";
    /**
     * 代表 Word 文档是否可见的KEY
     */
    public static final String VISIBLE_KEY = "Visible";
    //
    /**
     * 代表 Word 文档是 退出的KEY
     */
    public static final String QUIT_KEY = "Quit";
}
