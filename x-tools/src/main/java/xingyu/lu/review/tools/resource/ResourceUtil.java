/*
 * Copyright 2015-2016 fShows.com All right reserved. This software is the
 * confidential and proprietary information of fShows.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with fShows.com.
 */
package xingyu.lu.review.tools.resource;

import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * 类ResourceUtil.java的实现描述：读取properties配置文件工具类
 */
public final class ResourceUtil {
	  private final static Logger log = Logger.getLogger(ResourceUtil.class);

	    private static ResourceBundle system;
	    static {
	        try {
	            system = ResourceBundle.getBundle("config/systemConfig");
	        } catch (Exception e) {
	            log.error("systemConfig.properties Not Found. some keys lost.");
	        }
	    }

	    /**
	     * 获取properties配置字段value
	     * 
	     * @param fileName
	     * @param key
	     * @return
	     * @author TianmeiLee 2013-1-21 上午9:19:39
	     */
	    public static String getPropertiesValue(String fileName, String key) {

	        try {
	            ResourceBundle prop = ResourceBundle.getBundle(fileName);
	            return prop.getString(key);
	        } catch (Exception e) {
	            log.error("properties file Not Found. some keys lost.");
	        }
	        return null;
	    }

	    /**
	     * systemConfig
	     * 
	     * @param key
	     * @return
	     */
	    public static String getSystem(final String key) {
	        String msg = null;
	        try {
	            msg = system.getString(key);
	        } catch (Exception e) {
	            log.error("Key['" + key + "'] Not Found in systemConfig.properties .");
	        }
	        return msg == null ? system.getString("default") : msg;
	    }

	    /**
	     * 获取磁盘配置文件key-value
	     * 
	     * @param filePath
	     * @param key
	     * @return
	     * @author TianmeiLee 2013-9-2 上午11:37:42
	     */
	    public static String getPropWithFilePath(String filePath, String key) {
	        try {
	            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
	            ResourceBundle rb = new PropertyResourceBundle(in);
	            return rb.getString(key);
	        } catch (IOException e) {
	            log.error("properties file Not Found [filePath:" + filePath + "]");
	            return null;
	        }
	    }

	    /**
	     * 获取url绝对路径
	     * 
	     * @param domainKey
	     * @param path
	     * @return
	     */
	    public static String getUrl(final String domainKey, final String path) {
	        String url = "";
	        try {
	            url = system.getString(domainKey) + path;
	        } catch (Exception e) {
	            log.error("Key['" + domainKey + "'] Not Found in systemConfig.properties .");
	        }
	        return url;
	    }
	    
	    /**
	     * 获得url
	     */
	    public static String getUrl(final String domainKey){
	        String url = "";
	        try {
	            url = system.getString(domainKey);
	        } catch (Exception e) {
	            log.error("Key['" + domainKey + "'] Not Found in systemConfig.properties .");
	        }
	        return url;
	    }
}
