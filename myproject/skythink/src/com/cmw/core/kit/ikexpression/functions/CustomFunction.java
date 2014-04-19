package com.cmw.core.kit.ikexpression.functions;

import com.cmw.core.util.StringHandler;

/**
 * 自定义函数 
 * @author chengmingwei
 *
 */
public class CustomFunction {
	/**
	 * 求N次方的函数 ,类似于数学中的 "^" 符号. 例如: 10^3 ==  pow(10,3)
	 * @param num 求N次方的数  
	 * @param n N次方值
	 * @return 返回 求某个数的 N 次方的结果
	 */
	public Double pow(Object num, Object n){
		if(!StringHandler.isValidObj(num)){
			throw new NullPointerException("函数\"pow\"第一个参数 num 为空!");
		}
		if(!StringHandler.isValidObj(n)){
			throw new NullPointerException("函数\"pow\"第二个参数 n(n次方) 为空!");
		}
		
		double a = Double.parseDouble(num.toString());
		double b = Double.parseDouble(n.toString());
		double result = Math.pow(a, b);
		return result;
	}
}
