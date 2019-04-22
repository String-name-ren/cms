package com.waterelephant.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * html转pdf工具类 2016-9-14
 * 
 * @author duxiaoyong
 *
 */
public class HtmlToPdf {

	/**
	 * html转pdf
	 * 
	 * @param srcPath
	 *            html路径，可以是硬盘上的路径，也可以是网络路径
	 * @param destPath
	 *            pdf保存路径
	 * @param logPath
	 *            日志文件路径
	 * @return 转换结果
	 * @throws Exception
	 */
	public static void convert(String srcPath, String destPath, String logPath) throws Exception {
		File file = new File(destPath);
		File parent = file.getParentFile();
		// 如果pdf保存路径不存在，则创建路径
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		// 拼接命令
		StringBuilder cmd = new StringBuilder();
		cmd.append(SystemConstant.PDF_TOOL_PATH);
		cmd.append(" ");
		cmd.append(srcPath);
		cmd.append(" ");
		cmd.append(destPath);
		// 转换
		Process proc = Runtime.getRuntime().exec(cmd.toString());
		HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream(), new FileOutputStream(logPath));
		error.start();
		proc.waitFor();
	}

	/**
	 * 根据日志文件获得合同页数
	 * 
	 * @param logFilePath
	 *            日志文件路径
	 * @return 总页数
	 * @throws Exception
	 */
	public static int getPageCountByLogFile(String logFilePath) throws Exception {
		int pageCount = 0;
		// 获得日志内容
		InputStream is = new FileInputStream(logFilePath);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer log = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			log.append(line);
		}
		br.close();
		isr.close();
		is.close();
		// 计算
		pageCount = Integer.parseInt(log.substring(log.lastIndexOf("of") + 2, log.lastIndexOf("Done")).trim());
		return pageCount;
	}

}