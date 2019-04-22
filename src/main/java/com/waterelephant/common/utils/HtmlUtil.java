package com.waterelephant.common.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述 解析HTML工具
 *
 * @author: renpenghui
 * @date: 2019-03-22 14:15
 **/
public class HtmlUtil {

    //获取p标签的内容
    public static String getText(String html){
        if(CommUtils.isNull(html)){
            return "";
        }
        html = html.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
        return CommUtils.isNull(html) ? "" : (html.length()>150 ? html.substring(0,150) : html);
    }


    //获取img中url
    public static String getImageUrl(String html){
        if(CommUtils.isNull(html)){
            return "";
        }
        Document doc = Jsoup.parse(html);//解析HTML字符串返回一个Document实现
        Element link = doc.select("img").first();//查找第一个img元素
        return link == null ? "" : link.attr("src");
    }

    //获取img中url
    public static String addAttr(String html,String keyword){
        if(CommUtils.isNull(html)){
            return "";
        }
        Document doc = Jsoup.parse(html);//解析HTML字符串返回一个Document实现
        Elements imgList = doc.select("img");
        imgList.attr("alt",keyword);
        System.out.println(doc.body().html());
        return doc.body().html();
    }
}
