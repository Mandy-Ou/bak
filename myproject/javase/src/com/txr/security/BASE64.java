package com.txr.security;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class BASE64 {
	private static Base64 base64=new Base64();
	/**
	 * BASE64加密
	 * Base64内容传送编码被设计用来把任意序列的8位字节描述为一种不易被人直接识别的形式
	 * @param key加密的秘钥
	 * @return
	 */
	public static String encryptBASE64(String key){
		return new String(base64.encode(key.getBytes()));
		}
		/**
		 * BASE64解密
		 * @param key
		 * @return
		 */
		public static String disencryptBASE64(String key){
			
			return new String(base64.decode(key));
		}
		@Test
		public void testBASE(){
			String by="1335453";
			System.out.println("加密后的结果是 : "+BASE64.encryptBASE64(by));
			
			System.out.println("解密加密的信息是:  "+BASE64.disencryptBASE64(encryptBASE64(by)));
			
		}

	
}
