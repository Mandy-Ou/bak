package com.cmw.core.util;

import java.util.ArrayList;
import java.util.List;
/**
 * 树形类
 * @author chengmingwei
 *
 */
public class TreeUtil {
	private DataTable dt;
	//父节点需要排除的属性
	private String appendAttrs = "leaf";
	public String removeStr = "";
	private String[] apArrAttrs = appendAttrs.split(",");
	private String[] baseAttrs = null;
	private StringBuffer returnStr=new StringBuffer();
	public String iconPath = "../images/";
	
	/**
	 * 菜单展示类型  [1:卡片菜单] 采用  iconCls 属性
	 */
	public static final int MENU_TYPE_ACCORDION = 1;
	/**
	 * 菜单展示类型  [2:节点菜单] 采用  icon 属性
	 */
	public static final int MENU_TYPE_MENU = 2;
	
	public static final String BLANK_NULL = "BLANK_NULL";
	public TreeUtil() {
		
	}

	public TreeUtil(DataTable dt) {
		this.dt = dt;
		this.getBaseAttrs();
	}  
	
	public TreeUtil(DataTable dt,String removeStr) {
		this.removeStr = removeStr;
		this.dt = dt;
		this.getBaseAttrs();
	}  
    private String[] getBaseAttrs() {
    	String columns = dt.getColumnNames();
    	columns = columns.replace(","+this.appendAttrs.replace(this.removeStr, ""), "");
    	this.baseAttrs = columns.split(",");
		return this.baseAttrs;
	}
    
    /**
     * 追加属性
     * @param strs
     * @param isAppend
     */
    public void setAppendAttrs(String strs,boolean isAppend){
    	if(isAppend){
    		this.appendAttrs += ","+strs;
    	}else{
    		this.appendAttrs = strs;
    	}
    	this.apArrAttrs = this.appendAttrs.split(","); 
    	this.getBaseAttrs(); //重新设置所必须的参数
    }
    
    public void recursionFn(DataTable dt ,int index){ 
		 returnStr.append("{");
		for(int i=0,count=this.baseAttrs.length;i<count; i++){
			String attrName = this.baseAttrs[i];
			String val = dt.getString(index, attrName);
			if(!StringHandler.isValidStr(val)) val = "";
			String str = "";
			if(!StringHandler.isValidStr(attrName)) continue;
			if(BLANK_NULL.equals(val)) continue;
			if(attrName.equals("icon")){
				Integer type = dt.getInteger(index, "type");
				String icon = dt.getString(index, "icon");
				if(StringHandler.isValidStr(icon) && !"null".equals(icon)){
					//type ： 1 ---> 卡片菜单 , 2 ---> 树形节点菜单
					str = ((null != type && type.intValue()==MENU_TYPE_ACCORDION) ? "iconCls:\"" : "icon:\""+iconPath)+dt.getString(index, attrName)+"\",";
//					attrName = (null != type && type.intValue()==MENU_TYPE_ACCORDION) ? "cls" : "icon";
				}
			}else{
				if(!val.equals("true") && !val.equals("false")){
					val = "\""+val+"\"";
				}
				str = attrName+":"+val+",";
			}
			
			returnStr.append(str);   
		}
       if(dt.getString(index, "leaf").equals("false")){     
           returnStr.append("children:[");     
           List<Integer> childList = getChildList(dt,index);     
           for(Integer cindex : childList){
           	 recursionFn(dt,cindex);
           	
           }
           returnStr.append("]},");     
       }else{    
       		for(int i=0,count = this.apArrAttrs.length; i<count; i++){
           		String attrName = this.apArrAttrs[i];
           		String str = getSubStr(dt, index, attrName);
     			  returnStr.append(str);   
           	}
       	String strs = StringHandler.RemoveStr(returnStr, ",");
       	returnStr = new StringBuffer(strs);
       	returnStr.append("},");
       }     
            
   }
    

	/**
	 * 根据属性名获得其子JSON字符串
	 * @param dt	DataTable 对象
	 * @param index 当前行索引
	 * @param attrName 属性名
	 * @param str 要赋值的字符串
	 * @return
	 */
	private String getSubStr(DataTable dt, int index, String attrName) {
		String str = "[";
		if(-1 != attrName.indexOf("#")){
			String[] keys = attrName.split("#");
			String val = dt.getString(index, keys[0]);
			String[] vals = null;
			if(keys.length==2){
				str = "\""+keys[0]+"\":[";
				if(StringHandler.isValidStr(val)){
					vals = val.split(",");
					for(String js : vals){
						str += "{\""+keys[1]+"\":\""+js+"\"},";
					}
				}
			}
			str = str.substring(0,str.length()-1)+"],";
		}else{
			str = attrName+":\""+dt.getString(index, attrName)+"\",";
		}
		return str;
	}     
   
	
    public List<Integer> getChildList(DataTable dt , int index){  //得到子节点列表   
        List<Integer> li = new ArrayList<Integer>(); 
        for(int i=0,count=dt.getRowCount(); i<count; i++){
        	if(dt.getString(i, "pid").equals(dt.getString(index, "id"))){
        		li.add(i);
        	}
        }
        return li;     
    }   
    public String modifyStr(String returnStr){//修饰一下才能满足Extjs的Json格式   
        return ("["+returnStr+"]").replaceAll(",]", "]");   
           
    }  
    
    /**
     *  当顶级父ID为空时，调用此方法生成JSON格式
     * @return
     */
    public String getJsonArrByNull(){
    	clearCache();
    	List<Integer> indexes = new ArrayList<Integer>();
    	for(int i=0,count=dt.getRowCount(); i<count; i++){
    		if(!StringHandler.isValidStr(dt.getString(i, "pid"))){
    			indexes.add(i);
    		}
    	}
    	for(Integer index : indexes){
    		this.recursionFn(dt, index);
    	}
    	return this.modifyStr(this.returnStr.toString());
    }
    
    public String getJsonArr(String pid){
    	clearCache();
    	List<Integer> indexes = new ArrayList<Integer>();
    	for(int i=0,count=dt.getRowCount(); i<count; i++){
    		if(dt.getString(i, "pid").equals(pid)){
    			indexes.add(i);
    		}
    	}
    	for(Integer index : indexes){
    		this.recursionFn(dt, index);
    	}
    	return this.modifyStr(this.returnStr.toString());
    }
    
    public String getJsonArr(){
    	return this.getJsonArr("0");
    }
    
    private void clearCache(){
    	if(null != returnStr && returnStr.length() > 0) this.returnStr.delete(0, this.returnStr.length());
    }
    
    public static void main(String[] args) {     
//	    	Tree r = new Tree();
//	    	String[][] roots = {
//			{"1","易好总部","0","false"},
//			{"2","易好分部","0","false"},
//			{"3","珠海分公司","0","true"}};
//	    	for(int i=0;i<roots.length;i++){
//	    		 r.recursionFn(r.arrs, roots[i]);     
//	    	}
//	    	 System.out.println(r.modifyStr(r.returnStr.toString()));     
    }

	public DataTable getDt() {
		return dt;
	}

	public void setDt(DataTable dt) {
		this.dt = dt;
		this.getBaseAttrs();
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	 
}
