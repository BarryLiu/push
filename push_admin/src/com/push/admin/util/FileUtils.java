package com.push.admin.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

public class FileUtils {


	public static void copy(File resFile, File objFolderFile) throws IOException { 
		if (!resFile.exists()) return; 
		if (!objFolderFile.exists()) objFolderFile.mkdirs(); 
		if (resFile.isFile()) { 
			File objFile = new File(objFolderFile.getPath() + File.separator + resFile.getName());
			InputStream ins = new FileInputStream(resFile); 
			FileOutputStream outs = new FileOutputStream(objFile); 
			byte[] buffer = new byte[1024 * 512]; 
			int length; 
			while ((length = ins.read(buffer)) != -1) { 
				outs.write(buffer, 0, length); 
			} 
			ins.close(); 
			outs.flush(); 
			outs.close(); 
		} else { 
			String objFolder = objFolderFile.getPath() + File.separator + resFile.getName(); 
			File _objFolderFile = new File(objFolder); 
			_objFolderFile.mkdirs(); 
			for (File sf : resFile.listFiles()) { 
				copy(sf, new File(objFolder)); 
			} 
		} 
		//���ԣ�  copy(new File("D:\\test"), new File("C:\\")); 
	}  
	/** 
	 * ���ļ����У��ƶ�����һ���ļ��� 
	 * 
	 * @param resFile             Դ�ļ����У� 
	 * @param objFolderFile Ŀ���ļ��� 
	 * @throws IOException �쳣ʱ�׳� 
	 */ 
	public static void move(File resFile, File objFolderFile) throws IOException { 
		copy(resFile, objFolderFile); 
		delete(resFile); 
		//���ԣ�move(new File("C:\\test"), new File("E:\\")); 
	} 
	/** 
	 * ɾ���ļ����У� 
	 * 
	 * @param file �ļ����У� 
	 */ 
	public static void delete(File file) { 
		if (!file.exists()) return; 
		if (file.isFile()) { 
			file.delete(); 
		} else { 
			for (File f : file.listFiles()) { 
				delete(f); 
			} 
			file.delete(); 
		} 
		//���ԣ�  delete(new File("e:/test")); 

	}
	//ȡ���ļ���С
	public static long getFileSizes(File f) throws Exception{
		long s=0;
		if (f.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(f);
			s= fis.available();
		} else {
			f.createNewFile();
			System.out.println("�ļ�������...");
		}
		return s;
	}
	public static String FormetFileSize(long fileS) {//ת���ļ���С
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
	//��ȡ����ip
	public static String getRemortIP(HttpServletRequest request) {

		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	private static final int CPUTIME = 500; 
	private static final int PERCENT = 100; 
	private static final int FAULTLENGTH = 10; 
	//���cpuʹ���� 
	public static int getCpuRatioForWindows() { 
		try { 
			String procCmd = System.getenv("windir") + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount"; 
			// ȡ�����Ϣ 
			long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd)); 
			Thread.sleep(CPUTIME); 
			long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd)); 
			if (c0 != null && c1 != null) { 
				long idletime = c1[0] - c0[0]; 
				long busytime = c1[1] - c0[1]; 
				return Double.valueOf(PERCENT * (busytime)*1.0 / (busytime + idletime)).intValue(); 
			} else { 
				return 0; 
			} 
		} catch (Exception ex) { 
			ex.printStackTrace(); 
			return 0; 
		} 
	} 

	//��ȡcpu�����Ϣ 
	private static long[] readCpu(final Process proc) { 
		long[] retn = new long[2]; 
		try { 
			proc.getOutputStream().close(); 
			InputStreamReader ir = new InputStreamReader(proc.getInputStream()); 
			LineNumberReader input = new LineNumberReader(ir); 
			String line = input.readLine(); 
			if (line == null || line.length() < FAULTLENGTH) { 
				return null; 
			} 
			int capidx = line.indexOf("Caption"); 
			int cmdidx = line.indexOf("CommandLine"); 
			int rocidx = line.indexOf("ReadOperationCount"); 
			int umtidx = line.indexOf("UserModeTime"); 
			int kmtidx = line.indexOf("KernelModeTime"); 
			int wocidx = line.indexOf("WriteOperationCount"); 
			long idletime = 0; 
			long kneltime = 0; 
			long usertime = 0; 
			while ((line = input.readLine()) != null) { 
				if (line.length() < wocidx) { 
					continue; 
				} 
				// �ֶγ���˳��Caption,CommandLine,KernelModeTime,ReadOperationCount, 
				// ThreadCount,UserModeTime,WriteOperation 
				String caption =substring(line, capidx, cmdidx - 1).trim(); 
				String cmd = substring(line, cmdidx, kmtidx - 1).trim(); 
				if (cmd.indexOf("wmic.exe") >= 0) { 
					continue; 
				} 
				String s1 = substring(line, kmtidx, rocidx - 1).trim(); 
				String s2 = substring(line, umtidx, wocidx - 1).trim(); 
				if (caption.equals("System Idle Process") || caption.equals("System")) { 
					if (s1.length() > 0) 
						idletime += Long.valueOf(s1).longValue(); 
					if (s2.length() > 0) 
						idletime += Long.valueOf(s2).longValue(); 
					continue; 
				} 
				if (s1.length() > 0) 
					kneltime += Long.valueOf(s1).longValue(); 
				if (s2.length() > 0) 
					usertime += Long.valueOf(s2).longValue(); 
			} 
			retn[0] = idletime; 
			retn[1] = kneltime + usertime; 
			return retn; 
		} catch (Exception ex) { 
			ex.printStackTrace(); 
		} finally { 
			try { 
				proc.getInputStream().close(); 
			} catch (Exception e) { 
				e.printStackTrace(); 
			} 
		} 
		return null; 
	} 

	/** 
	 * ����String.subString�Ժ��ִ���������⣨��һ��������Ϊһ���ֽ�)������� ���ֵ��ַ�ʱ�����������ֵ������£� 
	 * @param src Ҫ��ȡ���ַ� 
	 * @param start_idx ��ʼ��꣨���������) 
	 * @param end_idx ��ֹ��꣨��������꣩ 
	 * @return 
	 */ 
	private static String substring(String src, int start_idx, int end_idx) { 
		byte[] b = src.getBytes(); 
		String tgt = ""; 
		for (int i = start_idx; i <= end_idx; i++) { 
			tgt += (char) b[i]; 
		} 
		return tgt; 
	} 
	/**
	 * ��ȡ�ļ�����ʱ��
	 * @param _file _file Ҫ��ȡ����ʱ����ļ�����
	 * @return datetime datetime ����ʱ��
	 */ 
	public static String getFileCreateDate(File _file) {
		File file = _file;
		try {
			Process ls_proc = Runtime.getRuntime().exec(
					"cmd.exe /c dir " + file.getAbsolutePath() + " /tc");
			BufferedReader br = new BufferedReader(new InputStreamReader(ls_proc.getInputStream()));
			for (int i = 0; i < 5; i++) {
				br.readLine();
			}
			String stuff = br.readLine();
			StringTokenizer st = new StringTokenizer(stuff);
			String dateC = st.nextToken();
			String time = st.nextToken();
			String datetime = dateC.concat(time);
			br.close();
			return datetime;

		} catch (Exception e) {
			return null;
		}

	}
	public  String  getLocalFilePath(){
		InputStream instream=this.getClass().getClassLoader().getResourceAsStream("serverConfig.properties");
		Properties p=new Properties();
		String filePath="";
		try{
			p.load(instream);
			filePath=p.getProperty("localPath");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(p!=null)p.clear();
			if(instream!=null)
				try {
					instream.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return filePath;




	}
	public int getTimingInfo(String param){
		InputStream instream=this.getClass().getClassLoader().getResourceAsStream("serverConfig.properties");
		Properties p=new Properties();
		int inTim=0;
		try{
			p.load(instream);
			String strParam=p.getProperty(param);
			inTim=Integer.parseInt(strParam);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(p!=null)p.clear();
			if(instream!=null)
				try {
					instream.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
		}		
		return inTim;
	}

	public static String getShowProReport() {
		File file = new File(new File("").getAbsolutePath() + "/ShowProForReport.txt");
		//System.out.println("filePath=" + file.getAbsolutePath());
		StringBuffer sb = new StringBuffer();
		if (file.isFile()) {
			BufferedReader bufferedReader = null;
			FileReader fileReader = null;
			try {
				fileReader = new FileReader(file);
				bufferedReader = new BufferedReader(fileReader);
				String line = bufferedReader.readLine();
				while (line != null) {
					sb.append(line);
					line = bufferedReader.readLine();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fileReader.close();
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return sb.toString();
	}

	public static void setShowProReport(String showPro) {
		File newfile = new File(new File("").getAbsolutePath() + "/ShowProForReport.txt");
		//System.out.println("filePath=" + newfile.getAbsolutePath());
		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(newfile);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(showPro);
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
