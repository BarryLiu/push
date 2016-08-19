package com.push.admin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.xmlpull.v1.XmlPullParser;

import android.content.res.AXmlResourceParser;
import android.util.TypedValue;

public class APKUtils {

	/**
	 * 抽取apk程序的应用图标
	 * 
	 * @param apkPath
	 *            apk的路径名
	 * @param aaptPath
	 *            aapt工具的全路径名
	 * @param outDir
	 *            图标的输出路径
	 */
	public static Map<String,String> getApkIcon(String apkPath, String aaptPath, String outDir,String outDir1) {
		Map<String,String> map = new HashMap<String,String>();
		String iconName = getIconName(apkPath, aaptPath,map);
		File iconOutDir = new File(outDir);
		if (!iconOutDir.exists()) {
			iconOutDir.mkdirs();
		}
		getIcon(apkPath, iconName, outDir + Math.abs(iconName.hashCode()) + iconName,map,outDir1+Math.abs(iconName.hashCode()) + iconName);
		return map;
	}

	/**
	 * 获取图标的名字
	 */
	private static String getIconName(String apkPath, String aaptPath,Map<String,String> map) {
		String iconName = "";
		try {
			/**
			 * Runtime类封装了运行时的环境。每个Java应用程序都有一个Runtime 类实例，使应用程序能够与其运行的环境相连接。
			 * 一般不能实例化一个Runtime对象
			 * ，应用程序也不能创建自己的Runtime类实例，但可以通过getRuntime方法获取当前Runtime运行时对象的引用。
			 */
			Runtime rt = Runtime.getRuntime();
			String order = "\""+aaptPath + "aapt.exe\"" + " d badging \"" + apkPath
					+ "\"";
			Process proc = rt.exec(order);
			InputStream inputStream = proc.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream, "UTF-8"));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains("application:")) {
					iconName = line
							.substring(line.lastIndexOf("/") + 1,
									line.lastIndexOf("'")).trim().toLowerCase();
					String str1 = line.substring(line.indexOf("'")+1);
					String str2 = str1.substring(0,str1.indexOf("'"));
					map.put("appName", str2);
					break;
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return iconName;
	}

	/**
	 * 抽取图标
	 */
	private static void getIcon(String apkPath, String iconName, String outPath,Map<String,String> map,String outDir1) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		ZipInputStream zis = null;
		File apkFile = new File(apkPath);
		try {
			fis = new FileInputStream(apkFile);
			zis = new ZipInputStream(fis);
			ZipEntry zipEntry = null;
			while ((zipEntry = zis.getNextEntry()) != null) {
				String name = zipEntry.getName().toLowerCase();
				if (zipEntry.getName().endsWith("AndroidManifest.xml")) {
					try {
						AXmlResourceParser parser = new AXmlResourceParser();
						parser.open(zis);
						while (true) {
							int type = parser.next();
							if (type == XmlPullParser.END_DOCUMENT) {
								break;
							}
							switch (type) {
							case XmlPullParser.START_TAG: {
								for (int i = 0; i != parser
										.getAttributeCount(); ++i) {
									if ("versionName".equals(parser
											.getAttributeName(i))) {
										map.put("versionName",getAttributeValue(parser, i));
									} else if ("versionCode".equals(parser
											.getAttributeName(i))) {
										map.put("versionCode",getAttributeValue(parser, i));
									} else if ("package".equals(parser
											.getAttributeName(i))) {
										map.put("packageName",getAttributeValue(parser, i));
									}
								}
							}
							}
						}
						if(map.get("icon")!=null){
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ((name.endsWith("/" + iconName) && name.contains("drawable") && name
						.contains("res"))
						|| (name.endsWith("/" + iconName)
								&& name.contains("raw") && name.contains("res"))) {
					fos = new FileOutputStream(new File(outPath));
					byte[] buffer = new byte[1024];
					int n = 0;
					while ((n = zis.read(buffer, 0, buffer.length)) != -1) {
						fos.write(buffer, 0, n);
					}
					map.put("icon", outDir1);
					if(map.get("packageName")!=null){
						break;
					}
				}
			}
			fos = null;
			zis.closeEntry();
			zipEntry = null;
			System.out.println("图标抽取成功" + outPath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (zis != null) {
					zis.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static String getAttributeValue(AXmlResourceParser parser, int index) {
		int type = parser.getAttributeValueType(index);
		int data = parser.getAttributeValueData(index);
		if (type == TypedValue.TYPE_STRING) {
			return parser.getAttributeValue(index);
		}
		if (type == TypedValue.TYPE_ATTRIBUTE) {
			return String.format("?%s%08X", getPackage(data), data);
		}
		if (type == TypedValue.TYPE_REFERENCE) {
			return String.format("@%s%08X", getPackage(data), data);
		}
		if (type == TypedValue.TYPE_FLOAT) {
			return String.valueOf(Float.intBitsToFloat(data));
		}
		if (type == TypedValue.TYPE_INT_HEX) {
			return String.format("0x%08X", data);
		}
		if (type == TypedValue.TYPE_INT_BOOLEAN) {
			return data != 0 ? "true" : "false";
		}
		if (type == TypedValue.TYPE_DIMENSION) {
			return Float.toString(complexToFloat(data))
					+ DIMENSION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
		}
		if (type == TypedValue.TYPE_FRACTION) {
			return Float.toString(complexToFloat(data))
					+ FRACTION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
		}
		if (type >= TypedValue.TYPE_FIRST_COLOR_INT
				&& type <= TypedValue.TYPE_LAST_COLOR_INT) {
			return String.format("#%08X", data);
		}
		if (type >= TypedValue.TYPE_FIRST_INT
				&& type <= TypedValue.TYPE_LAST_INT) {
			return String.valueOf(data);
		}
		return String.format("<0x%X, type 0x%02X>", data, type);
	}

	private static String getPackage(int id) {
		if (id >>> 24 == 1) {
			return "android:";
		}
		return "";
	}

	// ///////////////////////////////// ILLEGAL STUFF, DONT LOOK :)
	public static float complexToFloat(int complex) {
		return (float) (complex & 0xFFFFFF00) * RADIX_MULTS[(complex >> 4) & 3];
	}

	private static final float RADIX_MULTS[] = { 0.00390625F, 3.051758E-005F,
			1.192093E-007F, 4.656613E-010F };
	private static final String DIMENSION_UNITS[] = { "px", "dip", "sp", "pt",
			"in", "mm", "", "" };
	private static final String FRACTION_UNITS[] = { "%", "%p", "", "", "", "",
			"", "" };
}
