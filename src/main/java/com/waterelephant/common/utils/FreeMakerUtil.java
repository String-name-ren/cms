package com.waterelephant.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;

/**
 * freemaker工具类 2016-9-14
 * 
 * @author duxiaoyong
 *
 */
public class FreeMakerUtil {
	private static FreeMakerUtil freeMakerUtil;
	private Configuration configuration;

	// 单例模式
	private FreeMakerUtil() {
	}

	/**
	 * 获得工具实例
	 * 
	 * @return 工具实例
	 * @throws IOException 
	 */
	public static FreeMakerUtil getInstance() throws IOException {
		if (freeMakerUtil == null) {
			synchronized (FreeMakerUtil.class) {
				if (freeMakerUtil == null) {
					freeMakerUtil = new FreeMakerUtil();
					Configuration tmp = new Configuration(Configuration.VERSION_2_3_25);
					File ftlDir = new File(SystemConstant.FTL_BASE_PATH + "/ftl");
					tmp.setDirectoryForTemplateLoading(ftlDir);
					//tmp.setClassForTemplateLoading(FreeMakerUtil.class, SystemConstant.FTL_TEMPLATE_PATH);
					tmp.setDefaultEncoding("UTF-8");
					freeMakerUtil.configuration = tmp;
				}
			}
		}
		return freeMakerUtil;
	}

	/**
	 * 模板填充
	 * 
	 * @param templateName
	 *            模板名称
	 * @param savePath
	 *            保存路径
	 * @param dataMap
	 *            数据集
	 * @throws Exception
	 */
	public void fill(String templateName, String savePath, Map<String, Object> dataMap) throws Exception {
		File target = new File(savePath);
		File parent = target.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		//构建输出流并设置编码
		OutputStream out = new FileOutputStream(target);
		OutputStreamWriter outWriter = new OutputStreamWriter(out, "UTF-8");
		Writer writer = new BufferedWriter(outWriter);
		this.configuration.getTemplate(templateName).process(dataMap, writer);
		writer.close();
	}
}
