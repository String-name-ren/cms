package com.waterelephant.common.utils;

import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

@Component
public class ParamInitComponent {

	static {
		ResourceBundle config_bundle = ResourceBundle.getBundle("config");
		if (config_bundle == null) {
			throw new IllegalArgumentException("[config.properties] is not found!");
		}
		SystemConstant.ACCESSKEY_ID = config_bundle.getString("ACCESSKEY_ID");
		SystemConstant.ACCESS_KEY_SECRET = config_bundle.getString("ACCESS_KEY_SECRET");
		SystemConstant.ENDPOINT = config_bundle.getString("ENDPOINT");
		SystemConstant.BUCKET_NAME = config_bundle.getString("BUCKET_NAME");
		SystemConstant.IMG_URL = config_bundle.getString("IMG_URL");
//		SystemConstant.ORDER_CHANNEL_URL = config_bundle.getString("ORDER_CHANNEL_URL");


//		ResourceBundle article_bundle = ResourceBundle.getBundle("article");
//		if (article_bundle == null) {
//			throw new IllegalArgumentException("[article.properties] is not found!");
//		}
//		ArticleConstant.menu_title = article_bundle.getString("menu_title");
//		ArticleConstant.menu_keywords = article_bundle.getString("menu_keywords");
//		ArticleConstant.menu_description = article_bundle.getString("menu_description");
//		ArticleConstant.menu_url = article_bundle.getString("menu_url");
//		ArticleConstant.html_address = article_bundle.getString("html_address");

//		ResourceBundle redis_bundle = ResourceBundle.getBundle("redis");
//		if (redis_bundle == null) {
//			throw new IllegalArgumentException("[redis.properties] is not found!");
//		}
//		SystemConstant.REDIS_IP = redis_bundle.getString("redis_ip");
//		SystemConstant.REDIS_PORT = Integer.parseInt(redis_bundle.getString("redis_port"));
//		SystemConstant.REDIS_MAXWAIT = Integer.parseInt(redis_bundle.getString("redis_maxWait"));
//		SystemConstant.REDIS_MAXIDLE = Integer.parseInt(redis_bundle.getString("redis_maxIdle"));
//		SystemConstant.REDIS_MAXACTIVE = Integer.parseInt(redis_bundle.getString("redis_maxActive"));
//		SystemConstant.REDIS_TIMEOUT = Integer.parseInt(redis_bundle.getString("redis_timeout"));
//		SystemConstant.REDIS_PASSWORD = redis_bundle.getString("redis_password");
//
//		ResourceBundle es_bundle = ResourceBundle.getBundle("es");
//		if (es_bundle == null) {
//			throw new IllegalArgumentException("[es.properties] is not found!");
//		}
//		SystemConstant.ES_IP = es_bundle.getString("es.host");
//		SystemConstant.ES_PORT = Integer.parseInt(es_bundle.getString("es.port"));
//		SystemConstant.ES_CLUSTER_NAME = es_bundle.getString("es.cluster.name");
//		SystemConstant.ES_INDEX = es_bundle.getString("ES_INDEX");

	}
}
