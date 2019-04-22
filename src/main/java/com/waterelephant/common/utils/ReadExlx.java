package com.waterelephant.common.utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ReadExlx {

	public static Map<String,Object> readExlxList(InputStream is) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");  
		List<String> list=new ArrayList<String>();
		HttpURLConnection httpUrlConnection = null;
		InputStream fis = null;
		Workbook workbook=null;
		String regExp = "^[1][34578][0-9]{9}$";
		Pattern p = Pattern.compile(regExp);
		Map<String,Object> map=new HashMap<String,Object>();
		 try {  
			 	workbook = WorkbookFactory.create(is); //这种方式 Excel 2003/2007/2010 都是可以处理的
		        int sheetCount = workbook.getNumberOfSheets();  //Sheet的数量  
		        //遍历每个Sheet  
		        for (int s = 0; s < sheetCount; s++) {  
		            Sheet sheet = workbook.getSheetAt(s);  
		            int rowCount = sheet.getLastRowNum(); //获取总行数  
		            //遍历每一行  
		            for (int r = 0; r <= rowCount; r++) {  
		                Row row = sheet.getRow(r);
		                if(row==null){
		                	continue;
		                }
		                int cellCount = row.getLastCellNum(); //获取总列数  
		                //遍历每一列  
		                for (int c = 0; c < cellCount; c++) {  
		                    Cell cell = row.getCell(c);
		                    if(cell==null){
		                    	continue;
		                    }
		                    int cellType = cell.getCellType();  
		                    String cellValue = null;  
		                    switch(cellType) {  
		                        case Cell.CELL_TYPE_STRING: //文本  
		                            cellValue = cell.getStringCellValue();  
		                            break;  
		                        case Cell.CELL_TYPE_NUMERIC: //数字、日期  
		                            if(DateUtil.isCellDateFormatted(cell)) {  
		                                cellValue = fmt.format(cell.getDateCellValue()); //日期型  
		                            }  
		                            else {  
		                                cellValue = new DecimalFormat("#").format(cell.getNumericCellValue()); 
		                            }  
		                            break;
		                        case Cell.CELL_TYPE_BOOLEAN: //布尔型  
		                            cellValue = String.valueOf(cell.getBooleanCellValue());  
		                            break;  
		                        case Cell.CELL_TYPE_BLANK: //空白  
		                            cellValue = cell.getStringCellValue();  
		                            break;  
		                        case Cell.CELL_TYPE_ERROR: //错误  
		                            cellValue = "错误";  
		                            break;  
		                        case Cell.CELL_TYPE_FORMULA: //公式  
		                            cellValue = "错误";  
		                            break;  
		                        default:  
		                            cellValue = "错误";  
		                    } 
		                    Matcher m = p.matcher(cellValue);
							if (m.matches()) {
								list.add(cellValue);
							}else{
								map.put("code", "文件中有不符合手机格式的号码:"+cellValue+",在第"+(r+1)+"行第"+(c+1)+"列");
								map.put("row", "文件中有不符合手机格式的号码出现在:"+(r+1));
								break;
							}
		                    
		                }  
		            }  
		        }  
		    }  
		    catch (Exception e) {  
		        e.printStackTrace();  
		    } finally {
			    try {
			        if(fis!=null) {
                        fis.close();
                    }
			        if(is!=null) {
                        is.close();
                    }
			        if(httpUrlConnection!=null) {
                        httpUrlConnection.disconnect();
                    }
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			} 
		map.put("list", list);
		return map;
	}
}
