package com.rgk.push.ip;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Create:liangyulong
 * Email:javayulong@126.com
 */
public class Utils {  
	private static final Logger logger = Logger.getLogger(Utils.class);
	
	//获取访问ip
	public static String getRemortIP(HttpServletRequest request) {

		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	/**
	 * 从ip的字符串形式得到字节数组形式
	 * 
	 * @param ip
	 *            字符串形式的ip
	 * @return 字节数组形式的ip
	 */
	public static byte[] getIpByteArrayFromString(String ip) {
		byte[] ret = new byte[4];
		java.util.StringTokenizer st = new java.util.StringTokenizer(ip, ".");
		try {
			ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ret;
	}

	/**
	 * 对原始字符串进行编码转换，如果失败，返回原始的字符串
	 * 
	 * @param s
	 *            原始字符串
	 * @param srcEncoding
	 *            源编码方式
	 * @param destEncoding
	 *            目标编码方式
	 * @return 转换编码后的字符串，失败返回原始字符串
	 */
	public static String getString(String s, String srcEncoding,
			String destEncoding) {
		try {
			return new String(s.getBytes(srcEncoding), destEncoding);
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

	/**
	 * 根据某种编码方式将字节数组转换成字符串
	 * 
	 * @param b
	 *            字节数组
	 * @param encoding
	 *            编码方式
	 * @return 如果encoding不支持，返回一个缺省编码的字符串
	 */
	public static String getString(byte[] b, String encoding) {
		try {
			return new String(b, encoding);
		} catch (UnsupportedEncodingException e) {
			return new String(b);
		}
	}

	/**
	 * 根据某种编码方式将字节数组转换成字符串
	 * 
	 * @param b
	 *            字节数组
	 * @param offset
	 *            要转换的起始位置
	 * @param len
	 *            要转换的长度
	 * @param encoding
	 *            编码方式
	 * @return 如果encoding不支持，返回一个缺省编码的字符串
	 */
	public static String getString(byte[] b, int offset, int len,
			String encoding) {
		try {
			return new String(b, offset, len, encoding);
		} catch (UnsupportedEncodingException e) {
			return new String(b, offset, len);
		}
	}

	/**
	 * @param ip
	 *            ip的字节数组形式
	 * @return 字符串形式的ip
	 */
	public static String getIpStringFromBytes(byte[] ip) {
		StringBuffer sb = new StringBuffer();
		sb.append(ip[0] & 0xFF);
		sb.append('.');
		sb.append(ip[1] & 0xFF);
		sb.append('.');
		sb.append(ip[2] & 0xFF);
		sb.append('.');
		sb.append(ip[3] & 0xFF);
		return sb.toString();
	}
	/**
	 * 
	 * @param address
	 * @return ip所属的省份
	 */
	public static String getIpBelongProvince(String address){
		String province="";
		if(address.indexOf("省")>-1){
			int endIndex = address.indexOf("省");
			province=address.substring(0, endIndex+1);
			return province;
		}			
		return province;		
	}
	/**
	 * 
	 * @param address
	 * @return ip所属的省份
	 */
	public static String getIpBelongCity(String address){
		String ipCityName="";
		if((address.indexOf("省")>-1)&&(address.indexOf("市")>-1)){
			int beginIndex = address.indexOf("省");
			int endIndex = address.indexOf("市");
			ipCityName = address.substring(beginIndex + 1, endIndex+1);
			return ipCityName;
		}
		else if(address.indexOf("市")>-1){
			int endIndex = address.indexOf("市");
			ipCityName = address.substring(0, endIndex+1);
		}
		else{
			ipCityName=address;
		}
		return ipCityName;
	
	}
	public static String getIpBelongCountry(String country){
		String ipCountryName="";
		if((country.indexOf("省")>-1)&&(country.indexOf("市")>-1)){				
			return "中国";
		}
		else if(country.indexOf("北京")>-1||country.indexOf("天津")>-1||country.indexOf("上海")>-1||country.indexOf("重庆")>-1||country.indexOf("香港")>-1||country.indexOf("澳门")>-1){
			return "中国";
		}
		else{
			ipCountryName=country;
		}
		return ipCountryName;
	
	}

	/**
	 * get country name from sina website interface
	 * @param IP
	 * @return
	 */
	public static String getIpCountryFromSina(String IP){
		String countryName = "";
		int count = 0;
		while(count < 10) { // 重试10次
			String str = getWebPageString("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip=" + IP);
			int index = str.indexOf("\"country\":\"") + "\"country\":\"".length();
			String showStr = str.substring(index).split("\",\"")[0];
			countryName = UnicodeToString(showStr);
			logger.info("getIpCountryFromSina IP=" + IP + "; countryName=" + countryName + "; count=" + count);
			if(isChinese(countryName)) {
				break;
			}
			
			count++;
			try{
				Thread.sleep(1000);
			} catch(Exception e) {
				
			}
		}
		if(countryName.contains("meset> <frame id='top")) {
			countryName = "";
		}
		
		return countryName;
	}
	
	public static boolean isChinese(String str) { 
		for(int i=0;i<str.length();i++) {
		    int v = (int)str.charAt(i); 
			if(v <19968 || v > 171941) {
				return false;
			}
		}
	     return true; 
	}
	
	/**
	 * get web page, for get ip's country
	 * @param httpUrl
	 * @return
	 */
	private static String getWebPageString(String httpUrl) {
		
		String htmlContent = "";
		java.io.InputStream inputStream =null;
		java.net.URL url = null;
		java.net.HttpURLConnection connection = null;

		try {
			url = new java.net.URL(httpUrl);
			connection = (java.net.HttpURLConnection) url.openConnection();
			connection.connect();
			inputStream = connection.getInputStream();
			byte[] bytes = new byte[1024 * 2000];
			int index = 0;
			int count = inputStream.read(bytes, index, 1024 * 2000);
			while (count != -1) {
				index += count;
				count = inputStream.read(bytes, index, 1);
			}
			htmlContent = new String(bytes, "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
				try {
					if(inputStream != null)inputStream.close();
					if(connection != null) connection.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return htmlContent.trim();
	}
	
	private static String UnicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");    
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");    
        }
        return str;
  }
}
