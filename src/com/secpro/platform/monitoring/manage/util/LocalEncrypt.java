package com.secpro.platform.monitoring.manage.util;


/**
 * 
 * 本地加密算法，实现字符串的加密和解密
 * @author sxf
 *
 */
public class LocalEncrypt {
	/**
	 * 将明文加密成密文，将密文解密成明文
	 * @param strTask
	 * @return
	 */
	public static String Encode(String strTask) {
		String strEncode = new String("");
		String des = new String();
		String strKey = new String();
		if ((strTask == null) | (strTask.length() == 0)) {
			strEncode = "";
			return strEncode;
		}
		strKey = "zxcvbnm,./asdfghjkl;'qwertyuiop[]\\1234567890-=` ZXCVBNM<>?:LKJHGFDSAQWERTYUIOP{}|+_)(*&^%$#@!~";
	//	strKey="1qaz2wsx";
		for (; strTask.length() < 8; strTask = strTask + '\001')
			;
		des = "";
		for (int n = 0; n <= strTask.length() - 1; n++) {
			char code;
			char mid;
			do {
				for (code = (char) (int) Math.rint(Math.random() * 100D); code > 0
						&& ((code ^ strTask.charAt(n)) < 0 || (code ^ strTask
								.charAt(n)) > 90); code--)
					;
				mid = '\0';
				int flag = code ^ strTask.charAt(n);
				if (flag > 93)
					mid = '\0';
				else
					mid = strKey.charAt(flag);
			} while (!((code > '#') & (code < 'z') & (code != '|')
					& (code != '\'') & (code != ',') & (mid != '|')
					& (mid != '\'') & (mid != ',')));
			char temp = '\0';
			temp = strKey.charAt(code ^ strTask.charAt(n));
			des = des + code + temp;
		}
		strEncode = des;
		return strEncode;
	}

	/**
	 * 将密文解密成明文
	 * 
	 * @param varCode
	 *            待解密密文
	 * @return 返回解密后原文
	 */
	public static String Decode(String varCode) {
		String des = new String();
		String strKey = new String();
		if (varCode == null || varCode.length() == 0)
			return "";
		strKey = "zxcvbnm,./asdfghjkl;'qwertyuiop[]\\1234567890-=` ZXCVBNM<>?:LKJHGFDSAQWERTYUIOP{}|+_)(*&^%$#@!~";
	//	strKey="1qaz2wsx";
		if (varCode.length() % 2 == 1)
			varCode = varCode + "?";
		des = "";
		int n;
		for (n = 0; n <= varCode.length() / 2 - 1; n++) {
			char b = varCode.charAt(n * 2);
			int a = strKey.indexOf(varCode.charAt(n * 2 + 1));
			des = des + (char) (b ^ a);
		}
		n = des.indexOf(1);
		if (n > 0){
			return des.substring(0, n);
		}else{
			return des;
		}
	}
	public static void main(String[] args){
		String a="username";
		String b=LocalEncrypt.Encode(a);
		System.out.println(b);
		System.out.println(LocalEncrypt.Decode(b));
	}
}
