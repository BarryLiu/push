package com.rgk.push.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rgk.push.bean.RequestData;
import com.rgk.push.bean.Response;
import com.rgk.push.bean.Response.Bean;
import com.rgk.push.bean.UpdateData;
import com.rgk.push.dbbean.BaseHisBean;
import com.rgk.push.dbbean.TPushApkHis;
import com.rgk.push.dbbean.TPushTextHis;
import com.rgk.push.util.JSONUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestJson {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		JSONArray jsonArray = null;
		JSONObject jsonObject = null;
		Object obj = null;
		
		System.out.println("================  1  ==================");
		int[] arr = new int[] {1,2,3,4,5};
		jsonArray = JSONArray.fromObject(arr);
		System.out.println(jsonArray.toString()); //[1,2,3,4,5]
		
		System.out.println("================  2  ==================");
		List<String> list = new ArrayList<String>();
		list.add("zhang");
		list.add("zi");
		list.add("xiao");
		jsonArray = JSONArray.fromObject(list);
		System.out.println(jsonArray.toString());  //["zhang","zi","xiao"]
		System.out.println(jsonArray.getString(0)+":"+jsonArray.getString(1)); //zhang:zi
		
		System.out.println("================  3  ==================");
		Map map = new HashMap();
		map.put("name", list);
		map.put("age", 28);
		jsonArray = JSONArray.fromObject(map);
		System.out.println(jsonArray);  //[{"age":28,"name":["zhang","zi","xiao"]}]
		obj = jsonArray.get(0);
		System.out.println(obj.getClass().getName()); //net.sf.json.JSONObject
		Map m = (Map) JSONObject.toBean((JSONObject)obj, Map.class);
		System.out.println(m.get("name")); //["zhang","zi","xiao"]
		System.out.println(m.get("age"));  //28
		
		System.out.println("================  4  ==================");
		Student stu = new Student(1, "xiaoming", 32);
		jsonObject = JSONObject.fromObject(stu);
		System.out.println(jsonObject); //{"age":32,"name":"xiaoming","number":1}
		
		System.out.println("================  5  ==================");
		String jsonStr = "{\"age\":32,\"name\":\"xiaoming\",\"number\":1}";
		jsonObject = JSONObject.fromObject(jsonStr);
		System.out.println(jsonStr); //{"age":32,"name":"xiaoming","number":1}
		stu = (Student) jsonObject.toBean(jsonObject, Student.class);
		System.out.println(stu.toString()); //number=1,name=xiaoming,age=32
		
		System.out.println("================  6  ==================");
		Scores scores = new Scores(45, 89);
		stu.setScores(scores);
		jsonObject = JSONObject.fromObject(stu);
		System.out.println(jsonObject); //{"age":32,"name":"xiaoming","number":1,"scores":{"history":89,"math":45}}
		obj = jsonObject.get("scores");
		System.out.println(obj.getClass().getName()); //net.sf.json.JSONObject
		stu = (Student) JSONObject.toBean(jsonObject, Student.class);
		scores = stu.getScores();
		System.out.println(scores.getMath()+":"+scores.getHistory()); //45:89
		
		System.out.println("================  7  ==================");
		jsonStr = "{\"name\":\"c_regist\"," +
					"details:{\"uuid\":\"dfdafdafdafdafda\"," +
						"\"sim1\":\"13588889999\"," +
						"\"sim2\":\"13799998888\"," +
						"\"model\":\"G101\"," +
						"\"version\":\"G101_V0.0.1_S20140101\"," +
						"\"local\":\"zh_cn\"," +
						"\"c_version\":\"5678\"" +
					"}" +
				"}";
		jsonObject = JSONObject.fromObject(jsonStr);
		RegisterData data = (RegisterData) JSONObject.toBean(jsonObject, RegisterData.class);
		System.out.println(data);
		//name=c_regist,details={uuid=dfdafdafdafdafda,sim1=13588889999,sim2=13799998888,model=G101,version=G101_V0.0.1_S20140101,local=zh_cn}
		RegisterDetails details = (RegisterDetails) JSONObject.toBean(((JSONObject)jsonObject.get("details")),RegisterDetails.class);
		System.out.println(details);
		//uuid=dfdafdafdafdafda,sim1=13588889999,sim2=13799998888,model=G101,version=G101_V0.0.1_S20140101,local=zh_cn
		
		System.out.println("================  8  ==================");
		jsonObject = JSONObject.fromObject(details);
		System.out.println(jsonObject);
		
		System.out.println("================  9  ==================");
		Response.Bean bean = new Response.Bean();
		bean.setCode("01");
		bean.setName("register");
		jsonObject = JSONObject.fromObject(bean);
		System.out.println(jsonObject); //{"code":"01","name":"register"}
		bean = (Bean) JSONObject.toBean(jsonObject, Response.Bean.class);
		System.out.println(bean.getName()+":"+bean.getCode()); //register:01
		System.out.println("你好");
		
		System.out.println("================  10  ==================");
		jsonStr = "{uuid:'123456789'}";
		RequestData reqData = JSONUtils.toBean(jsonStr, RequestData.class);
		System.out.println(reqData);
		
		System.out.println("================  11  ==================");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println(sdf.format(new Date()));
		java.util.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
		System.out.println(sdf.format(sqlDate));
		String ds = "2014-04-30 13:50:10.438";
		java.sql.Timestamp timestamp = new java.sql.Timestamp(sdf.parse(ds).getTime());
		System.out.println("timestamp="+timestamp);
	}

}
