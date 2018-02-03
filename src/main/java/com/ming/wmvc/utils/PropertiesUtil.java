package com.ming.wmvc.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Ming on 2018/1/31.
 */
public class PropertiesUtil {
    static Properties properties = new Properties();
    static{
        try {
            properties.load(PropertiesUtil.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        return properties.getProperty(key);
    }

}
