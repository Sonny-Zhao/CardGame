package com.tianyu.cardgame.utils;

import com.tencent.mmkv.MMKV;
import com.tianyu.cardgame.application.CardGameApplication;

/**
 * @Synopsis: MMKVUtils: 基于mmap的高性能KV组件
 * @Author: shisheng.zhao
 * @Date: 2019-07-17 11:54
 */
public class MMKVUtils {

    static class MMKVHolder {
        private final static MMKV mmkv;

        static {
            MMKV.initialize(CardGameApplication.getApp());
            mmkv = MMKV.mmkvWithID("BigFoot", MMKV.MULTI_PROCESS_MODE);
        }
    }

    /**
     * 写入字符串
     *
     * @param key
     * @param value
     */
    public static void putString(String key, String value) {
        MMKVHolder.mmkv.encode(key, value);
    }

    /**
     * 读取字符串
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        return MMKVHolder.mmkv.decodeString(key, "");
    }

    /**
     * 读取字符串
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static String getString(String key, String defaultValue) {
        return MMKVHolder.mmkv.decodeString(key, defaultValue);
    }

    /**
     * 写入布尔值
     *
     * @param key
     * @param value
     */
    public static void putBoolean(String key, boolean value) {
        MMKVHolder.mmkv.encode(key, value);
    }

    /**
     * 读取布尔值
     *
     * @param key
     * @param defValue: 指定默认值
     * @return
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return MMKVHolder.mmkv.decodeBool(key, defValue);
    }

    /**
     * 读取布尔值
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(String key) {
        return MMKVHolder.mmkv.decodeBool(key);
    }

    /**
     * 写入long值
     *
     * @param key
     * @param value
     */
    public static void putLong(String key, long value) {
        MMKVHolder.mmkv.encode(key, value);
    }

    /**
     * 读取long值
     *
     * @param key
     * @param defValue：指定默认值
     * @return
     */
    public static long getLong(String key, long defValue) {
        return MMKVHolder.mmkv.decodeLong(key, defValue);
    }

    /**
     * 读取long值
     *
     * @param key
     * @return
     */
    public static long getLong(String key) {
        return MMKVHolder.mmkv.decodeLong(key);
    }

    /**
     * 写入float值
     *
     * @param key
     * @param value
     */
    public static void putFloat(String key, float value) {
        MMKVHolder.mmkv.encode(key, value);
    }

    /**
     * 读取long值
     *
     * @param key
     * @return
     */
    public static float getFloat(String key, float defValue) {
        return MMKVHolder.mmkv.decodeFloat(key, defValue);
    }

    /**
     * 写入double值
     *
     * @param key
     * @param value
     */
    public static void putDouble(String key, double value) {
        MMKVHolder.mmkv.encode(key, value);
    }

    /**
     * 读取double值
     *
     * @param key
     * @return
     */
    public static double getDouble(String key) {
        return MMKVHolder.mmkv.decodeDouble(key);
    }

    /**
     * 写入int值
     *
     * @param key
     * @param value
     */
    public static void putInt(String key, int value) {
        MMKVHolder.mmkv.encode(key, value);
    }

    /**
     * 读取int值
     *
     * @param key
     * @param defValue：指定默认值
     * @return
     */
    public static int getInt(String key, int defValue) {
        return MMKVHolder.mmkv.decodeInt(key, defValue);
    }

    /**
     * 读取int值
     *
     * @param key
     * @return
     */
    public static int getInt(String key) {
        return MMKVHolder.mmkv.decodeInt(key);
    }

    /**
     * 写入bytes值
     *
     * @param key
     * @param value
     */
    public static void putBytes(String key, byte[] value) {
        MMKVHolder.mmkv.encode(key, value);
    }

    /**
     * 根据key删除value
     *
     * @param key
     */
    public static void removeValueForKey(String key) {
        MMKVHolder.mmkv.removeValueForKey(key);
    }

    /**
     * 批量删除多个key的value
     *
     * @param keys
     */
    public static void removeValueForKeys(String[] keys) {
        MMKVHolder.mmkv.removeValuesForKeys(keys);
    }
}
