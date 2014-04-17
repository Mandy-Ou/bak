package com.cmw.core.util;

import java.math.BigDecimal;

public class BigDecimalHandler {
	private static final int DEF_DIV_SCALE = 10; //这个类不能实例化
	/**
	 * 系统默认四舍五入保留小数位数
	 */
	public static final int DEFAULT_SCALE = 3;
	/**
	* 提供精确的加法运算。
	* @param v1 被加数
	* @param v2 加数
	* @return 两个参数的和
	*/
	public static double add(double v1, double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return add(b1, b2);
	}
	
	/**
	* 根据参数获取一个 BigDecimal 对象
	* @param v1  要包装成 BigDecimal 对象的 double 值
	* @return BigDecimal 对象
	*/
	public static BigDecimal get(double v1){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		return b1;
	}
	
	/**
	* 提供精确的加法运算。
	* @param v1 被加数
	* @param v2 加数
	* @return 两个参数的和
	*/
	public static double add(BigDecimal b1,BigDecimal b2){
		setDefaultVal(b1, b2);
		return b1.add(b2).doubleValue();
	}
	
	/**
	* 提供精确的加法运算。
	* @param v1 被加数
	* @param v2 加数
	* @return 两个参数的和
	*/
	public static BigDecimal add2BigDecimal(BigDecimal b1,BigDecimal b2){
		setDefaultVal(b1, b2);
		return b1.add(b2);
	}
	
	/**
	* 提供精确的加法运算。
	* @param v1 被加数
	* @param v2 加数
	* @param scale 四舍五入的位数
	* @return 两个参数的和
	*/
	public static BigDecimal add2BigDecimal(BigDecimal b1,BigDecimal b2, int scale){
		setDefaultVal(b1, b2);
		return b1.add(b2);
	}
	
	/**
	* 提供精确的多个数的加法运算，并进行四舍五入返回计算后的结果。
	* @param scale 精确小数位数（如果为0或负数则不处理四舍五入问题）
	* @param nums 要相加的多个加数
	* @return 多个参数的和
	*/
	public static BigDecimal add2BigDecimal(int scale,BigDecimal... nums){
		BigDecimal result = add2BigDecimal(nums);
		return (scale<=0) ? result : roundToBigDecimal(result, scale);
	}
	
	/**
	* 提供精确的多个数的加法运算，并返回计算后的结果。
	* @param scale 精确小数位数（如果为0或负数则不处理四舍五入问题）
	* @param nums 要相加的多个加数
	* @return 多个参数的和
	*/
	public static BigDecimal add2BigDecimal(BigDecimal... nums){
		if(null == nums || nums.length == 0) return new BigDecimal("0");
		BigDecimal result = new BigDecimal("0");
		for(BigDecimal num : nums){
			result = result.add(num);
		}
		return result;
	}
	
	/**
	* 提供精确的减法运算。
	* @param v1 被减数
	* @param v2 减数
	* @return 两个参数的差
	*/
	public static double sub(double v1, double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return sub(b1,b2);
	}
	
	/**
	* 提供精确的减法运算。
	* @param b1 被减数
	* @param b2 减数
	* @return 两个参数的差
	*/
	public static BigDecimal sub2BigDecimal(BigDecimal b1, BigDecimal b2){
		setDefaultVal(b1, b2);
		return b1.subtract(b2);
	}
	
	/**
	* 提供精确的减法运算。
	* @param b1 被减数
	* @param b2 减数
	* @param scale 精确的小数位数
	* @return 两个参数的差
	*/
	public static BigDecimal sub2BigDecimal(BigDecimal b1, BigDecimal b2, int scale){
		setDefaultVal(b1, b2);
		return b1.subtract(b2);
	}
	
	/**
	* 提供精确的减法运算。
	* @param v1 被减数
	* @param v2 减数
	* @return 两个参数的差
	*/
	public static double sub(BigDecimal b1,BigDecimal b2){
		setDefaultVal(b1, b2);
		return b1.subtract(b2).doubleValue();
	}
	
	/**
	 * 当 b1 或 b2 为空时，赋一个为 O 的默认值
	 * @param b1
	 * @param b2
	 */
	private static void setDefaultVal(BigDecimal b1,BigDecimal b2){
		if(null == b1) b1 = new BigDecimal(Double.toString(0d));
		if(null == b2) b2 = new BigDecimal(Double.toString(0d));
	}
	
	/**
	* 提供精确的乘法运算。
	* @param v1 被乘数
	* @param v2 乘数
	* @return 两个参数的积
	*/
	public static double mul(double v1, double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	
	/**
	* 提供精确的乘法运算。
	* @param v1 被乘数
	* @param v2 乘数
	* @return 两个参数的积
	*/
	public static double mul(BigDecimal b1,BigDecimal b2){
		setDefaultVal(b1, b2);
		return b1.multiply(b2).doubleValue();
	}
	
	/**
	* 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	* 小数点以后10位，以后的数字四舍五入。
	* @param v1 被除数
	* @param v2 除数
	* @return 两个参数的商
	*/
	public static double div(double v1,double v2){
		return div(v1,v2,DEF_DIV_SCALE);
	}
	
	/**
	* 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	* 定精度，以后的数字四舍五入。
	* @param v1 被除数
	* @param v2 除数
	* @param scale 表示表示需要精确到小数点以后几位。
	* @return 两个参数的商
	*/
	public static double div(double v1,double v2,int scale){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return div(b1,b2,scale);
	}
	
	/**
	* 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	* 定精度，以后的数字四舍五入。
	* @param v1 被除数
	* @param v2 除数
	* @param scale 表示表示需要精确到小数点以后几位。
	* @return 两个参数的商
	*/
	public static double div(BigDecimal b1,BigDecimal b2,int scale){
		if(scale<0){
		throw new IllegalArgumentException(
		"The scale must be a positive integer or zero");
		}
		setDefaultVal(b1, b2);
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	* 提供精确的小数位四舍五入处理。
	* @param v 需要四舍五入的数字
	* @param scale 小数点后保留几位
	* @return 四舍五入后的结果
	*/
	public static double round(double v,int scale){
		BigDecimal b1 = new BigDecimal(Double.toString(v));
		return round(b1, scale);
	}
	
	/**
	* 提供精确的小数位四舍五入处理。
	* @param v 需要四舍五入的数字
	* @param scale 小数点后保留几位
	* @return 四舍五入后的结果
	*/
	public static BigDecimal roundToBigDecimal(BigDecimal v,int scale){
		BigDecimal one = new BigDecimal("1");
		return v.divide(one,scale,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	* 提供精确的小数位四舍五入处理。
	* @param v 需要四舍五入的数字
	* @param scale 小数点后保留几位
	* @return 四舍五入后的结果
	*/
	public static BigDecimal roundToBigDecimal(double v,int scale){
		BigDecimal b1 = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b1.divide(one,scale,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	* 提供精确的小数位四舍五入处理。
	* @param v 需要四舍五入的数字
	* @param scale 小数点后保留几位
	* @return 四舍五入后的结果
	*/
	public static double round(BigDecimal b1,int scale){
		if(scale<0){
		throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if(null == b1) b1 = new BigDecimal(Double.toString(0d));
		BigDecimal one = new BigDecimal("1");
		return b1.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	

/**
 * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零
 * 要用到正则表达式
 */
public static String digitUppercase(double n){
	String fraction[] = {"角", "分"};
    String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
    String unit[][] = {{"元", "万", "亿"},
                 {"", "拾", "佰", "仟"}};

    String head = n < 0? "负": "";
    n = Math.abs(n);
    
    String s = "";
    for (int i = 0; i < fraction.length; i++) {
        s += (digit[(int)(Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
    }
    if(s.length()<1){
	    s = "整";	
    }
    int integerPart = (int)Math.floor(n);

    for (int i = 0; i < unit[0].length &&  integerPart > 0; i++) {
        String p ="";
        for (int j = 0; j < unit[1].length &&  n > 0; j++) {
            p = digit[integerPart%10]+unit[1][j] + p;
            integerPart = integerPart/10;
        }
        s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
    }
    return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
}

}
