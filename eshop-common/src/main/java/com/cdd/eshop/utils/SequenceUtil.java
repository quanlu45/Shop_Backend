package com.cdd.eshop.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 序列工具类
 *
 * @author quan
 * @date 2020/12/29
 */
public class SequenceUtil {

    private static final int max = 999;

    private static final AtomicInteger count = new AtomicInteger(max);

    /**
     * 获得唯一序列串
     *
     * @param prefix 前缀
     * @return {@link String}
     */
    public static String get(String prefix) {
        count.compareAndSet(max,1);
        StringBuilder builder = new StringBuilder();
        if (prefix != null){
            builder.append(prefix.toUpperCase());
        }
        builder.append(System.currentTimeMillis());
        builder.append(String.format("%03d", count.getAndAdd(1)));
        return builder.toString();
    }

    /**
     * 获得唯一序列串
     *
     * @return {@link String}
     */
    public static String get(){
        return SequenceUtil.get(null);
    }


}
