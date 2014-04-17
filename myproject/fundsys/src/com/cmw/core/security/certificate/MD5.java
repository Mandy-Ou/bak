package com.cmw.core.security.certificate;

import java.security.MessageDigest;
/**
 * MDE 加密工具类
 * @author Administrator
 * @Date 2010-06-20
 */
public class MD5 {
	private String source;

	public MD5(String source) {
		this.source = source;
	}

	public String toMD5() {
		String md5String = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source.getBytes());
			md5String = byte2Hex(md.digest());
		} catch (Exception ex) {
		}
		return md5String;
	}

	private String byte2Hex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String tmp = "";
		for (int i = 0; i < b.length; i++) {
			tmp = Integer.toHexString(b[i] & 0XFF);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);
		}
		return sb.toString().toUpperCase();
	}

	public static void main(String[] args) {
		{
			String txt = "123456_?||*!~`";
			String s = new MD5(txt).toMD5();
			System.out.println(txt);
			System.out.println(s.getBytes().length);
			System.out.println(s);
		}
		// {
		// String s = new MD5("1234567890").toMD5();
		// System.out.println(s.getBytes().length);
		// System.out.println(s);
		// }
		// {
		// String s = new MD5("abcdefghijklmnopq").toMD5();
		// System.out.println(s.getBytes().length);
		// System.out.println(s);
		// }
	}
}