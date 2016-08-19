import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rgk.push.dbbean.TClient;
import com.rgk.push.util.DBUtils;

public class TestJavaSQL {

	public TestJavaSQL() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:/out_result_db.txt"),"GBK"));//new FileReader(new File("D:/out_result.txt")));
			String temp = br.readLine();
			while (temp != null) {

				try {
					String imei = temp.split(";")[0];
					String ip = temp.split(";")[1];
					String country = temp.split(";")[2];
					
					Map<String,String> tMap = new HashMap<String,String>();
					tMap.put("ip", ip);
					tMap.put("country", country);
					map.put(imei, tMap);
				} catch (Exception e) {
					
				}
				temp = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			
		}
		
		
//		String sql = "update t_client set ip='" + ip + "', country_name='" + country + "' where imei='" + imei + "';";
		String sql = "select * from t_client where country_name like '%-%'";
		List<TClient> lstData = DBUtils.query(sql, TClient.class);
		for (TClient item : lstData) {
			if(map.containsKey(item.getUuid())) {
				boolean isNeedUpdate = false;
				if(!item.getIp().equals(map.get(item.getUuid()).get("ip"))) {
					item.setIp(map.get(item.getUuid()).get("ip"));
					isNeedUpdate = true;
				}
				String country = map.get(item.getUuid()).get("country");
				
				if(!item.getCountryName().equals(country)) {
					item.setCountryName(country);
					isNeedUpdate = true;
				}
				if(isNeedUpdate)
					DBUtils.update(item);
			}
		}
		
//		try {
//
//			String sql = "SELECT * FROM t_client";
//			List<TClient> lstData = DBUtils.query(sql, TClient.class);
//			for (TClient item : lstData) {
//
//				// 删除有乱码的数据或为空的数据
//				if (item.getDeviceVersion() == null
//						|| "".equals(item.getDeviceVersion())
//						|| item.getDeviceModel() == null
//						|| "".equals(item.getDeviceModel())
//						|| StringUtil.isMessyCode(item.getDeviceVersion())
//						|| StringUtil.isMessyCode(item.getDeviceModel())) {
//					DBUtils.delete(item);
//					continue;
//				}
//
//				// 更新项目名、客户名称
//				String version = item.getDeviceVersion();
//				if (version != null
//						&& !"".equals(version)
//						&& (item.getDeviceCustomer() == null || ""
//								.equals(item.getDeviceCustomer())
//								&& (item.getDeviceProduct() == null || ""
//										.equals(item.getDeviceProduct())))) {
//					String arrTmp[] = version.split("_");
//					if (arrTmp != null && arrTmp.length > 2) {
//						item.setDeviceProduct(arrTmp[0].trim());
//						item.setDeviceCustomer(arrTmp[1].trim());
//					}
//				}
//
//				// 更新国家语言
//				String countryName = item.getCountryName();
//				if (countryName != null && !"".equals(countryName)) {
//					String arrTmp[] = countryName.split("-");
//					if (arrTmp != null && arrTmp.length == 2) {
//						item.setLanguage(arrTmp[0].trim());
//					}
//				}
//				DBUtils.update(item);
//			}
//		} catch (Exception e) {
//
//		}
	}
}
