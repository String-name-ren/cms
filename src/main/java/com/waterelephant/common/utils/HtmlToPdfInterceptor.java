package com.waterelephant.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * 当java调用wkhtmltopdf时，用于获取wkhtmltopdf返回的内容
 * 
 * @author duxiaoyong
 */
public class HtmlToPdfInterceptor extends Thread {
	private InputStream is;
	private OutputStream out;
	
	public HtmlToPdfInterceptor(InputStream is, OutputStream out) {
		this.is = is;
		this.out = out;
	}

	@Override
    public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			OutputStreamWriter outw = new OutputStreamWriter(out, "UTF-8");
			BufferedWriter bw = new BufferedWriter(outw);
			String line = null;
			while ((line = br.readLine()) != null) {
				bw.write(line);
				bw.newLine();
			}
			bw.flush();
			bw.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
