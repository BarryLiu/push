package com.push.admin.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.push.admin.util.APKUtils;
import com.push.admin.util.FileUtils;

public class ApkFileServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(ApkFileServlet.class);
	private static final long serialVersionUID = 1L;
	private static final String savePath = "download\\apks\\";
	private static final String iconsavePath = "download\\apkIcons\\";

	// @Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = "";
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
				// 处理上传文件
				if (item.getName() != null && !item.getName().equals("")) {
					// 从客户端发送过来的上传文件路径中截取文件名
//					filename = new Date().getTime()+item.getName().substring(
//							item.getName().lastIndexOf("\\") + 1);
					filename = String.valueOf(new Date().getTime())+".apk";
					is = item.getInputStream(); // 得到上传文件的InputStream对象
					// 将路径和上传文件名组合成完整的服务端路径
					url = (savePath+filename).replaceAll("\\\\", "/");
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
			Map<String,String> map = APKUtils.getApkIcon(filename, filePath+"download\\ApkTool\\", filePath+iconsavePath,iconsavePath);
			StringBuffer json = new StringBuffer("");
			json.append("{'versionName':'").append(map.get("versionName")).append("',");
			json.append("'versionCode':'").append(map.get("versionCode")).append("',");
			json.append("'name':'").append(map.get("appName")).append("',");
			json.append("'icon':'").append(map.get("icon").replaceAll("\\\\", "/")).append("',");
			json.append("'url':'").append(url).append("',");
			json.append("'size':'").append(FileUtils.getFileSizes(new File(filename))+"b").append("',");
			json.append("'packageName':'").append(map.get("packageName")).append("'}");
			out.println(json.toString());
			out.close();
		} catch (Exception e) {
			logger.error("升级信息出错,Error:",e);

		}
	}

}
