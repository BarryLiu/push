import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestJava {

	public TestJava() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();

		File out = new File("D:/out_result.txt");
		BufferedWriter boResult = null;
		try {
			boResult = new BufferedWriter(new FileWriter(out));
		} catch (Exception e) {
			e.printStackTrace();
		}

		File folder = new File("D:/");
		File[] lstFile = folder.listFiles();
		for (File item : lstFile) {
			if (item.getName().startsWith("a_info.log")) {
				System.out.println("path=" + item.getName());
				try {
					BufferedReader br = new BufferedReader(new FileReader(item));
					String temp = br.readLine();
					boolean isNewRecord = false;
					String imei = "";
					String ip = "";
					while (temp != null) {
						if (temp.contains("name=c_regist;details=")) {
							System.out.println("line=" + temp);
							isNewRecord = true;

							int start = temp.indexOf("{\"uuid\":\"")
									+ "{\"uuid\":\"".length();
							imei = temp.substring(start, start + 15);

						} else if (temp.contains("[ INFO ]  ip=")) {
							System.out.println("line=" + temp);
							if (isNewRecord) {
								int start = temp.indexOf("ip=")
										+ "ip=".length();
								ip = temp.substring(start, start + 15).split(
										",")[0];

								System.out
										.println("imei=" + imei + ";ip=" + ip);

								// check whether exists
								if (!map.containsKey(imei)) {
									String countryName = getCountryName(ip);
									boResult.write(imei + ";" + ip + ";" + countryName);
									boResult.write("\n");
									boResult.flush();
								}

								isNewRecord = false;
								imei = "";
								ip = "";
							}
						}
						temp = br.readLine();
					}
					if (br != null)
						br.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (boResult != null)
							boResult.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}
	}

	private static String getCountryName(String ip) {
		String countryName = "";
		int count = 0;
		while (count < 1) { // 重试10次
			String str = getWebPageString("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip="
					+ ip);
			int index = str.indexOf("\"country\":\"")
					+ "\"country\":\"".length();
			String showStr = str.substring(index).split("\",\"")[0];
			countryName = UnicodeToString(showStr);
			if (isChinese(countryName)) {
				break;
			}

			count++;
			try {
				Thread.sleep(1000);
			} catch (Exception e) {

			}
		}
		if (countryName.contains("meset> <frame id='top")) {
			countryName = "";
		}

		return countryName;
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

	private static String getWebPageString(String httpUrl) {

		String htmlContent = "";
		java.io.InputStream inputStream = null;
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
				if (inputStream != null)
					inputStream.close();
				if (connection != null)
					connection.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return htmlContent.trim();
	}

	public static boolean isChinese(String str) {
		for (int i = 0; i < str.length(); i++) {
			int v = (int) str.charAt(i);
			if (v < 19968 || v > 171941) {
				return false;
			}
		}
		return true;
	}
}
