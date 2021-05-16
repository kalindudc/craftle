package com.craftle_mod.common.tags.util;

import java.util.StringJoiner;

public abstract class TagsUtil {

    public static String buildPath(String... args) {

        StringJoiner builder = new StringJoiner("/");
        for (String arg : args) {
            builder.add(arg);
        }

        return builder.toString();
    }

}
