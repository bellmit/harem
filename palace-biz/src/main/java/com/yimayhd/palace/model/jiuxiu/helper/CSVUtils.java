package com.yimayhd.palace.model.jiuxiu.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.yimayhd.palace.interceptor.LoginInterceptor;

public class CSVUtils {
	
	
	/**
	 * 生成单个CVS文件，可分页后单独调用此方法生成多个文件
	 * 
	 * 源数据List
	 * @param exportData
	 * csv文件的列表头map
	 * @param map
	 *  文件路径
	 * @param outPutPath
	 * 文件名称
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static File createCSVFile(List exportData, LinkedHashMap map,
			String outPutPath, String fileName) {
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			File file = new File(outPutPath);
			if (!file.exists()) {
				file.mkdir();
			}
			// 定义文件名格式并创建
			csvFile = File.createTempFile(fileName, ".csv",
					new File(outPutPath));
			System.out.println("csvFile：" + csvFile);
			// UTF-8使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(csvFile), "UTF-8"), 1024);
			System.out.println("csvFileOutputStream：" + csvFileOutputStream);
			// 写入文件头部
//			for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator
//					.hasNext();) {
//				java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator
//						.next();
//				csvFileOutputStream
//						.write(propertyEntry.getValue() != null ? (String) propertyEntry
//								.getValue() : "");
//				if (propertyIterator.hasNext()) {
//					csvFileOutputStream.write(",");
//				}
//			}
//			csvFileOutputStream.newLine();
			// 写入文件内容
			for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
				Object row = (Object) iterator.next();
				for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator
						.hasNext();) {
					java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator
							.next();
					csvFileOutputStream.write((String) BeanUtils.getProperty(
							row, (String) propertyEntry.getKey()));
					if (propertyIterator.hasNext()) {
						csvFileOutputStream.write(",");
					}
				}
				if (iterator.hasNext()) {
					csvFileOutputStream.newLine();
				}
			}
			csvFileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}

	/**
	 * 将指定文件夹打包成zip
	 * 
	 * @param folder
	 * @param name
	 *            文件名
	 * @throws IOException
	 */
	public static void zipFile(String folder, String name) throws IOException {
		File zipFile = new File(folder + name);
		if (zipFile.exists()) {
			zipFile.delete();
		}
		ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(
				zipFile));
		File dir = new File(folder);
		File[] fs = dir.listFiles();
		byte[] buf = null;
		if (fs != null) {
			for (File f : fs) {
				if (!f.getName().equals(name)) {
					zipout.putNextEntry(new ZipEntry(f.getName()));
					FileInputStream fileInputStream = new FileInputStream(f);
					buf = new byte[2048];
					BufferedInputStream origin = new BufferedInputStream(
							fileInputStream, 2048);
					int len;
					while ((len = origin.read(buf, 0, 2048)) != -1) {
						zipout.write(buf, 0, len);
					}
					zipout.flush();
					origin.close();
					fileInputStream.close();
				}
			}
		}
		zipout.flush();
		zipout.close();
	}

	/**
	 * 下载文件
	 * 
	 * @param response
	 *            文件路径
	 * @param csvFilePath
	 *            文件名称
	 * @param fileName
	 * 
	 * @throws IOException
	 */
	public static void exportFile(HttpServletResponse response,
			String csvFilePath, String fileName) throws IOException {
//		response.setContentType("application/csv;charset=UTF-8");
//		response.setHeader("Content-Disposition", "attachment; filename="
//				+ URLEncoder.encode(fileName, "UTF-8"));
//		in = new FileInputStream(csvFilePath+fileName);
//		int len = 0;
//		byte[] buffer = new byte[1024];
//		response.setCharacterEncoding("UTF-8");
//		OutputStream out = response.getOutputStream();
//		while ((len = in.read(buffer)) > 0) {
//			//out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
//			out.write(buffer, 0, len);
//		}

		InputStream in = null;
		try {
			// 以流的形式下载文件。
            in = new BufferedInputStream(new FileInputStream(csvFilePath+fileName));
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            in.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes()));
          //  response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();

		} catch (FileNotFoundException e) {
			System.out.println(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			
		}
		
	}
	

	/**
	 * 删除该目录filePath下的所有文件包括当前文件夹  rm -rf
	 * 
	 * @param filePath
	 *            文件目录路径
	 */
	public static void deleteFiles(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					files[i].delete();
				}
			}
		}
		file.delete();
	}

	/**
	 * 删除单个文件
	 * 
	 * @param filePath
	 *            文件目录路径
	 * @param fileName
	 *            文件名称
	 */
	public static void deleteFile(String filePath, String fileName) {
		File file = new File(filePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					if (files[i].getName().equals(fileName)) {
						files[i].delete();
						return;
					}
				}
			}
		}
	}

	/**
	 * 测试数据
	 * 
	 * @param args
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws IOException {
		List exportData = new ArrayList<Map>();
		List exportData2 = new ArrayList<Map>();
		Map row1 = new LinkedHashMap<String, String>();
		row1.put("1", "11");
		row1.put("2", "12");
		row1.put("3", "13");
		row1.put("4", "14");
		exportData.add(row1);
		row1 = new LinkedHashMap<String, String>();
		row1.put("1", "21");
		row1.put("2", "22");
		row1.put("3", "23");
		row1.put("4", "24");
		exportData.add(row1);
		LinkedHashMap map = new LinkedHashMap();
		map.put("1", "第一列");
		map.put("2", "第二列");
		map.put("3", "第三列");
		map.put("4", "第四列");

		exportData2 = exportData;
		List<List> s = new ArrayList<List>();
		s.add(exportData);
		s.add(exportData2);

		String path = "export7/";
		String fileName = "cecececece";
		for (int i = 0; i < s.size(); i++) {
			File file = CSVUtils.createCSVFile(s.get(i), map, path, fileName);
			String fileName2 = file.getName();
			System.out.println("文件名称：" + fileName2);
		}
		zipFile(path, "test.zip");
		System.out.println("打包完成");
		//deleteFiles(path);
		//System.out.println("删除文件夹成功");

	}
	
	public static String getContextRealPath()  
	{  
	    String path = LoginInterceptor.class.getClassLoader().getResource("")  
	            .getPath();  
	    int end = path.length() - "WEB-INF/classes/".length();  
	    path = path.substring(1, end);  
	    return path;  
	}
}
