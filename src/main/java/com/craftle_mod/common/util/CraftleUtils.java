package com.craftle_mod.common.util;

public abstract class CraftleUtils {

    public static String buildTranslationKey(String prefix, String... args) {

        String key = prefix + ".";

        if (prefix.length() == 0) {
            key = "";
        }

        for (String arg : args) {
            key += arg + ".";
        }

        return key.substring(0, key.length() - 1);
    }

    public static String buildBlockTranslationKey(String... args) {
        return buildTranslationKey("block.craftle", args);
    }

    public static String buildItemTranslationKey(String... args) {
        return buildTranslationKey("item.craftle", args);
    }

}
