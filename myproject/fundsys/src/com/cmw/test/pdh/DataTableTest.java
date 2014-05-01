package com.cmw.test.pdh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.cmw.core.util.DataTable;

/**
 *Title: DataTableTest.java
 *@作者： 彭登浩
 *@ 创建时间：2012-11-16下午2:57:15
 *@ 公司：	同心日信科技有限公司
 */
public class DataTableTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List javaArr= new ArrayList();
		javaArr.add(new Object[]{"name",1122,"idcard"});
//		System.out.println(javaArr);
		DataTable dt=new DataTable(javaArr,"pdh");
		String pdh = dt.getJsonArr();
		System.out.println(pdh);
		
		dt.addRowData(new Object[]{"pdh",11122,"ee"});
		String pdh1 = dt.getJsonList().toJSONString();//转成json数组
		System.out.println(pdh1);
//		System.out.println(dt.toString());
		
		String ColumnNames=dt.getColumnNames();//得到列名
		System.out.println("ColumnNames="+ColumnNames);
		
		long size=dt.getSize();
		System.out.println(size);
		
		List<Object> source=dt.getDataSource();//获取资源
		
//		System.out.println(source.toString());
		
		List javaArr1= new ArrayList();
		javaArr.add(new Object[]{"name","sex","idcard"});
		DataTable newdt= new DataTable(javaArr1,"pdh3");
//		dt.insertDataTableToFirst(newdt);
		dt.addDtToEnd(newdt);
		String  newdt1=dt.getJsonArr();
		System.out.println("newdt="+newdt1.toString());
		
		Integer i=dt.getRowCount();
		System.out.println(i);
		
		Object[] pdhoo=dt.getRowData(2);//获取某一行的数据
		for(Object x: pdhoo){
			System.out.println(x);
		} 
		
		System.out.println(dt.getEmptyJsonArr());
		
		System.out.println(dt.getJsonObjStr());
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("1", "eeee");
		System.out.println(dt.getJsonObjStr( map));
		JSONObject mm=dt.getJsonObj();
		System.out.println(mm);
		
		Integer l=dt.getCellIndex(pdh);
		System.out.println(l);
		
		
		
	}
}
