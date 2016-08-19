package com.rgk.push.ip;

import javax.servlet.http.HttpServletRequest;

//import org.apache.struts2.ServletActionContext;

public class Test {
	public static boolean isChinese(String str) { 
		for(int i=0;i<str.length();i++) {
		     int v = (int)str.charAt(i); 
			if(v <19968 || v > 171941) {
				return false;
			}
		}
	     return true; 
	}
	public static void main(String[] args) {
		String address = "";
		String ipCityName = "";
		System.out.println("===" + isChinese("我是中国"));
		/**
		try {
			IPSeeker seeker = IPSeeker.getInstance(ServletActionContext
					.getServletContext());
			HttpServletRequest request = ServletActionContext.getRequest();
			String ip = GroupUtil.getIpAddr(request);
			// String ip = "125.92.141.237";
			address = seeker.getAddress(ip);
			System.out.println("���IP�Զ���ó�������" + address);
			int beginIndex = address.indexOf("ʡ");
			int endIndex = address.indexOf("��");
			ipCityName = address.substring(beginIndex + 1, endIndex);
			// String city =
			// groupManagerService.getCityInfoByConditon(GroupUtil.getMap(
			// "cityName", ipCityName));
		} catch (Exception e) {
			System.out.println("���ʳ��ִ���,���ִ����IP:++++++++++" + address
					+ " ���ִ���ĳ���:++++++++++++++++++" + ipCityName);
		}*/
	}
	/**
	public void test(){
		String address = "";
		String ipCityName = "";
		try {
			IPSeeker seeker = IPSeeker.getInstance(ServletActionContext
					.getServletContext());
			HttpServletRequest request = ServletActionContext.getRequest();
			String ip = GroupUtil.getIpAddr(request);
			// String ip = "125.92.141.237";
			address = seeker.getAddress(ip);
			System.out.println("���IP�Զ���ó�������" + address);
			int beginIndex = address.indexOf("ʡ");
			int endIndex = address.indexOf("��");
			ipCityName = address.substring(beginIndex + 1, endIndex);
			// String city =
			// groupManagerService.getCityInfoByConditon(GroupUtil.getMap(
			// "cityName", ipCityName));
		} catch (Exception e) {
			System.out.println("���ʳ��ִ���,���ִ����IP:++++++++++" + address
					+ " ���ִ���ĳ���:++++++++++++++++++" + ipCityName);
		}
	}
*/
}