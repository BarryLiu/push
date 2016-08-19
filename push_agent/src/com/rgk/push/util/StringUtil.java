package com.rgk.push.util;


public class StringUtil {
	
	public static boolean isMessyCode(String str) {
//		System.out.println("str=" + str);
		if(str != null && !"".equals(str)) {
			for(int i=0;i<str.length();i++) {
				int v = (int)str.charAt(i); 
//				System.out.println(v);
				if((v <19968 && (v<32 || v>126)) || v > 171941) { // 非汉字
					return true;
				}
			}
		}
		return false;
	}

		public static void main(String []args) {
			System.out.println(isMessyCode("à′·à′·"));
//			System.out.println(isMessyCode("我是中国人"));
//			System.out.println(isMessyCode("2312T211"));
		}
}
