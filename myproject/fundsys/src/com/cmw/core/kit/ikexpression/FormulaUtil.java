package com.cmw.core.kit.ikexpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.PreparedExpression;
import org.wltea.expression.datameta.Variable;
/**
 * 公式解析类
 * @author Administrator
 *
 */
public class FormulaUtil {

	public static Object parseExpression(String expression , Map<String, Object> params){
		Object result = null;
		List<Variable> variables = convertToList(params);
		PreparedExpression pre = ExpressionEvaluator.preparedCompile(expression, variables);
		result = pre.execute();
		return result;
	}
	
	/**
	 * 将表达式所需的 params(HashMap 对象) 转成表达式所需的List 形式变量
	 * @param params	HashMap 参数对象
	 * @return 返回List 参数列表
	 */
	
	private static List<Variable> convertToList(Map<String, Object> params){
		if(null == params || params.size() == 0) return null;
		List<Variable> variables = new ArrayList<Variable>();
		Set<String> keys = params.keySet();
		for(String key : keys){
			variables.add(Variable.createVariable(key, params.get(key)));
		}
		return variables;
	}
	
	/**
	 * 表达式检查
	 * @param expression 表达式字符串
	 * @return 返回编译成功的表达式
	 */
	public static String check(String expression){
		return ExpressionEvaluator.compile(expression);
	}
	
	/**
	 * 表达式检查
	 * @param expression 表达式字符串
	 * @param params 表达式参数
	 * @return 返回编译成功的表达式
	 */
	public static String check(String expression,Map<String, Object> params){
		 List<Variable> variables = convertToList(params);
		return ExpressionEvaluator.compile(expression,variables);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//资产回报率
		//String expression6 = "(rate * $POW((rate + 1),zphases)) / ($POW((rate + 1),zphases) -1 )) * reprincipal";
		//((rate * $POW((rate + 1),zphases) / ($POW((rate + 1),zphases) -1 )) * reprincipal
		//(((rate + mrate) * $POW((rate + mrate + 1),totalPhases))/ ($POW((rate + mrate + 1),totalPhases) -1 )) * reprincipal
//		String expression6 = "(((rate + mrate) * $POW((rate + mrate + 1),zphases))/ ($POW((rate + mrate + 1),zphases) -1 )) * reprincipal";
//		HashMap<String, Object> params6 = new HashMap<String, Object>();
//		params6.put("rate", 0.015);
//		params6.put("mrate", 0.015);
//		params6.put("zphases", 6);
//		params6.put("reprincipal", 100000);
//		params6.put("liabilities_b3", 1774908.04);
//		
//		params6.put("liabilities_b4", 0);
//		params6.put("liabilities_b5", 0);
//		params6.put("liabilities_b7", 0);
//		params6.put("liabilities_b8", 0);
//		params6.put("liabilities_b9", 0);
		
//		Object result6 = parseExpression(expression6, params6);
//		System.out.println("还款金额="+result6+"元");
//		Object result7 = parseExpression("1>=2", params6);
//		System.out.println("还款金额="+result7+"元");
//		
//		//净资产
//		String expression5 = "(净资产>=4000)?20:(净资产>=3000)?18:(净资产>=2000)?16:(净资产>=1000)?14:(净资产>=800)?12:(净资产>=500)?10:(净资产>=300)?8:(净资产>=200)?4:(净资产>=100)?2:0";
//		HashMap<String, Object> params5 = new HashMap<String, Object>();
//		params5.put("净资产", 240);
//		Object result5 = parseExpression(expression5, params5);
//		System.out.println("净资产="+result5+"分");
//		
//		//资产负债率
//		String expression4 = "(资产负债率*100<=30)?13:(资产负债率*100<=40)?10:(资产负债率*100<=50)?8:(资产负债率*100<=60)?4:(资产负债率*100<=70)?2:0";
//		HashMap<String, Object> params4 = new HashMap<String, Object>();
//		params4.put("资产负债率", 1.34);
//		Object result4 = parseExpression(expression4, params4);
//		System.out.println("资产负债率="+result4+"分");
//		
//		//速动比率
//		String expression3 = "(速动比率*100>=100)?5:(速动比率*100>=80)?4:(速动比率*100>=60)?3:(速动比率*100>=40)?2:(速动比率*100>=20)?1:0";
//		HashMap<String, Object> params3 = new HashMap<String, Object>();
//		params3.put("速动比率", 3.07);
//		Object result3 = parseExpression(expression3, params3);
//		System.out.println("速动比率="+result3+"分");
//		
//		//流动比率
//		String expression2 = "(流动比率*100>=200)?5:(流动比率*100>=180)?4:(流动比率*100>=160)?3:(流动比率*100>=130)?2:(流动比率*100>=100)?1:0";
//		HashMap<String, Object> params2 = new HashMap<String, Object>();
//		params2.put("流动比率", 3.44);
//		Object result2 = parseExpression(expression2, params2);
//		System.out.println("流动比率="+result2+"分");
//		
//		
		//资产回报率
		//String expression = "(资产回报率*100>=10)?10:(资产回报率*100>=8)?8:(资产回报率*100)>=6?6:(资产回报率*100>=4)?4:0";
		String expression = "(yearLoan*12+monthLoan)>=12&&payAmount!=0";
		HashMap<String, Object> params = new HashMap<String, Object>();
		Integer in = new Integer(1);
		params.put("yearLoan", in.toString());
		params.put("monthLoan", 0);
		params.put("dayLoan", 0);
		params.put("payAmount", 100000.00);
		Object result = parseExpression(expression, params);
//		String xx = ExpressionEvaluator.compile(expression);
		System.out.println("资产回报率="+result+"分");

	}

	
}
