package com.cmw.test;

import java.lang.reflect.Field;

import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.kit.word.jacob.JacobWordTemplate;
import com.cmw.core.util.GenericsUtil;

public class MainTest {
	/**
	 * @param args
	 * @throws ServiceException 
	 */
	public static void main(String[] args) throws ServiceException {
		JacobWordTemplate jacobTemplate = new JacobWordTemplate(null, null);
		
	}
	
	@SuppressWarnings("rawtypes")
	public static <T> T copyValue(String fieldsStr,Object[] vals,Class<T> cls){
		T object = null;
		try {
			 object = cls.newInstance();
			 Class supCls = GenericsUtil.getSupClass(cls);
			 System.out.println(""+supCls.getName());
			 String[] fields = fieldsStr.split(",");
			 if(null == fields || 0 == fields.length) return null;
			Field fld = null;
			int i=0;
			for(String name : fields){
				try {
					fld = cls.getDeclaredField(name);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					if(null == supCls) return object;
					try {
						fld = supCls.getDeclaredField(name);
					} catch (SecurityException e1) {
						e1.printStackTrace();
					} catch (NoSuchFieldException e1) {
						e1.printStackTrace();
					}
				}
				fld.setAccessible(true);
				fld.set(object, vals[i++]);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return object;
	}
	
}
