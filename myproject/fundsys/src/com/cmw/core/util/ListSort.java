package com.cmw.core.util;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cmw.entity.sys.MatParamsEntity;
 
/**
 * List对象排序的通用方法
 *
 * @author chenchuang
 *
 * @param <E>
 */
public class ListSort<E> {
    /**
     * 此方法排序是按照字典顺序排序适合字符串排序    ： 如果是  Integer 的时候使用该方法超过 两位数 则出现混乱 
     * 
     * @param list 要排序的集合
     * @param method 要排序的实体的属性所对应的get方法
     * @param sort null 为正序  
     * 例如 ： ListSort<MatParamsEntity> sortmatParamsList = new ListSort<MatParamsEntity>();
     *     sortmatParamsList.Sort(matParamsList, "getOrderNo",  null); -----> 
     *  如果需要对 Integer 排序 使用以下方法
     *  Collections.sort(matParamsList,new Comparator<MatParamsEntity>(){   
     *          public int compare(MatParamsEntity arg0, MatParamsEntity arg1) {   
     *              return arg0.getOrderNo().compareTo(arg1.getOrderNo());   
     *           }   
     *       }); 
     */
    public void Sort(List<E> list, final String method, final String sort) {
        // 用内部类实现排序
        Collections.sort(list, new Comparator<E>() {
 
            public int compare(E a, E b) {
                int ret = 0;
                try {
                    // 获取m1的方法名
                    Method m1 = a.getClass().getMethod(method, null);
                    // 获取m2的方法名
                    Method m2 = b.getClass().getMethod(method, null);
                     
                    if (sort != null && "desc".equals(sort)) {
                        ret = m2.invoke(((E)b), null).toString().compareTo(m1.invoke(((E)a),null).toString());
 
                    } else {
                        // 正序排序
                        ret = m1.invoke(((E)a), null).toString().compareTo(m2.invoke(((E)b), null).toString());
                    }
                } catch (NoSuchMethodException ne) {
                    System.out.println(ne);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return ret;
            }
        });
    }
}
 
 
 
