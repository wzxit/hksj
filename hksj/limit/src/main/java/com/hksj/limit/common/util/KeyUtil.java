package com.hksj.limit.common.util;

public class KeyUtil {
    public static String getCompositeKey(String prefix, String name, String key) {
        return prefix + "_" + name + "_" + key;
    }

    public static String getCompositeWildcardKey(String prefix, String name) {
        return prefix + "_" + name + "*";
    }

    public static String getCompositeWildcardKey(String key) {
        return key + "*";
    }
}
