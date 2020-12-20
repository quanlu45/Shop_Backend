package com.cdd.eshop.utils;

import java.util.concurrent.ConcurrentHashMap;

public class CacheUtil {

    private static ConcurrentHashMap<String,Object> cache = new ConcurrentHashMap<>();

    public static Object get(String key){
        return cache.get(key);
    }

    public static Object put(String key,Object value){
        return cache.put(key,value);
    }

    public static Object remove(String key){
        return cache.remove(key);
    }
}
