package com.push.admin.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.push.admin.dbbean.TPushUpdateClient;
import com.push.admin.dwr.Dwr;
import com.push.admin.dwr.Result;
import com.push.admin.util.DBUtils;
import com.push.admin.util.InsertPushParams;

public class PushClientFileServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(ApkFileServlet.class);
	private static final long serialVersionUID = 1L;
	private static final String savePath = "download\\clients\\";
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String title = "";// 客户名称
		String comments = "";// 项目名称
		String url = "";// 机器型号
		String cversion = "";// 硬件版本
		String type = "";// 初始软件版本
		String pushDate = "";// OTA路径
		// add by kui.li, start
		String imei1="";
		String imei2="";
		String country="";
		String project="";
		String customer="";
		String model="";
		String version="";
		// add by kui.li, end
		
		String id = "";
		try {
			System.out.println("新增升级信息");
			request.setCharacterEncoding("UTF-8"); // 设置处理请求参数的编码格式
			response.setContentType("text/html;charset=UTF-8"); // 设置Content-Type字段值
			PrintWriter out = response.getWriter();
			// 下面的代码开始使用Commons-UploadFile组件处理上传的文件数据
			FileItemFactory factory = new DiskFileItemFactory(); // 建立FileItemFactory对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 分析请求，并得到上传文件的FileItem对象
			List<FileItem> items = upload.parseRequest(request);
			String filePath = this.getServletConfig().getServletContext()
					.getRealPath("/");
			String uploadPath = filePath + savePath;
			String filename = ""; // 上传文件保存到服务器的文件名
			InputStream is = null; // 当前上传文件的InputStream对象
			for (FileItem item : items) {
				// 处理普通的表单域
				if (item.isFormField()) {
					if (item.getFieldName().equals("id")) {
						id = item.getString("UTF-8");
					} else if (item.getFieldName().equals("title")) {
						title = item.getString("UTF-8");
					} else if (item.getFieldName().equals("comments")) {
						comments = item.getString("UTF-8");
					} else if (item.getFieldName().equals("url")) {
						url = item.getString("UTF-8");
					} else if (item.getFieldName().equals("cversion")) {
						cversion = item.getString("UTF-8");
					} else if (item.getFieldName().equals("type")) {
						type = item.getString("UTF-8");
					} else if (item.getFieldName().equals("pushDate")) {
						pushDate = item.getString("UTF-8");
						// add by kui.li, start
					} else if (item.getFieldName().equals("imei1")) {
						imei1 = item.getString("UTF-8");
					} else if (item.getFieldName().equals("imei2")) {
						imei2 = item.getString("UTF-8");
					} else if (item.getFieldName().equals("country")) {
						country = item.getString("UTF-8");
					} else if (item.getFieldName().equals("project")) {
						project = item.getString("UTF-8");
					} else if (item.getFieldName().equals("customer")) {
						customer = item.getString("UTF-8");
					} else if (item.getFieldName().equals("model")) {
						model = item.getString("UTF-8");
					} else if (item.getFieldName().equals("version")) {
						version = item.getString("UTF-8");
						// add by kui.li, end
					}
				}
			}
			for (FileItem item : items) {
				// 处理上传文件
				if (item.getName() != null && !item.getName().equals("")) {
					// 从客户端发送过来的上传文件路径中截取文件名
					filename = new Date().getTime()+item.getName().substring(
							item.getName().lastIndexOf("\\") + 1);
					System.out.println("222--filename=-=来了" + filename);
					is = item.getInputStream(); // 得到上传文件的InputStream对象
					// 将路径和上传文件名组合成完整的服务端路径
					url=(savePath+filename).replaceAll("\\\\", "/");
					filename = uploadPath + filename;
					System.out.println("--filename=-=路径" + filename);
					// 如果服务器已经存在和上传文件同名的文件，则输出提示信息
					if (new File(filename).exists()) {
						new File(filename).delete();
					}
					File iconOutDir = new File(uploadPath);
					if (!iconOutDir.exists()) {
						iconOutDir.mkdirs();
					}
					// 开始上传文件
					if (!filename.equals("")) {
						// 用FileOutputStream打开服务端的上传文件
						FileOutputStream fos = new FileOutputStream(filename);
						byte[] buffer = new byte[819200]; // 每次读800K字节
						int count = 0;
						// 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
						while ((count = is.read(buffer)) > 0) {
							fos.write(buffer, 0, count); // 向服务端文件写入字节流

						}
						fos.close(); // 关闭FileOutputStream对象
						is.close(); // InputStream对象
					}
				}
			}
			TPushUpdateClient client = new TPushUpdateClient();
			client.setTitle(title);
			client.setType(Integer.parseInt(type));
			if(!cversion.equals("")){
			client.setVersion(Integer.parseInt(cversion));
			}
			client.setComments(comments);
			client.setTitle(title);
			if (!url.equals("")) {
				client.setUrl(url);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			client.setPushDate(new Timestamp(sdf.parse(pushDate).getTime()));
			client.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			// add by kui.li, start
			client.setImei1(imei1);
			client.setImei2(imei2);
			client.setCountryName(country);
			client.setProjectName(project);
			client.setCustomerName(customer);
			client.setModelName(model);
			client.setVersionName(version);
			// add by kui.li, end
			response.setHeader("Cache-Control", "no-cache");
			if (id.equals("")) {
				client.setCreateDate(new Timestamp(System.currentTimeMillis()));
				
				long _id = DBUtils.insert(client);
				if(_id > 0) {
					// 添加push params数据
					InsertPushParams ipp = new InsertPushParams(
							InsertPushParams.TYPE_CLIENT, _id, client.getCountryName(),
							client.getProjectName(), client.getCustomerName(),
							client.getModelName(), client.getVersionName());
					Thread thread = new Thread(ipp);
					thread.start();
	
					out.println( Result.toJson("success", "Add successfully!", id));
				} else {
					out.println( Result.toJson("failure", "Add failed!", id));
				}
			} else {
				client.setId(Long.parseLong(id));
				out.println(new Dwr().updateSqlBean(client));
				
				// 更新push params数据
				InsertPushParams ipp = new InsertPushParams(
						InsertPushParams.TYPE_CLIENT, client.getId(), client.getCountryName(),
						client.getProjectName(), client.getCustomerName(),
						client.getModelName(), client.getVersionName());
				Thread thread = new Thread(ipp);
				thread.start();
			}
			out.close();
		} catch (Exception e) {
			logger.error("升级信息出错,Error:" + e.toString());

		}
	}
	
}
