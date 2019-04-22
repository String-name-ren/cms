package com.waterelephant.common.utils;

import com.waterelephant.dto.BaiduSuccess;
import net.sf.json.util.JSONUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-03-23 14:53
 **/
public class HttpClientUtil {

    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public static int post(String url, String charset, String jsonStrData) {
        int success  = 0;
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        try {
            URL url1 = new URL(url);
            URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
            HttpPost httppost = new HttpPost(uri);
            HttpEntity entity_request = new StringEntity(jsonStrData, charset);
            httppost.setEntity(entity_request);
            httppost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseResult = EntityUtils.toString(entity, charset);
                    if(responseResult.contains("error")){
                        success = 0;
                    }else {
                        BaiduSuccess result = (BaiduSuccess)JsonUtil.json2Object(responseResult,BaiduSuccess.class);
                        success = result.getSuccess();
                    }
                } else {
                    success = 0;
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    /**
     * main方法测试
     *
     * @param args
     */
    public static void main(String[] args) {
        // 测试Post方法sxfq.com:80/testA/13.html
        String url = " http://data.zz.baidu.com/urls?site=sxfq.com&token=r99Bfx6Gec0bPJFg";
        int result = post(url,"utf-8","sxfq.com:80/testA/13.html");

        System.out.println(result);
    }


}
