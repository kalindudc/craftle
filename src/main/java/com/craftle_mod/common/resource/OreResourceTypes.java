package com.craftle_mod.common.resource;

public enum OreResourceTypes {

    INGOT("ingot", false, false),
    RAW("raw", false, true),
    DUST("dust", false, false),
    BLOCK("block", true, false);

    private final String name;
    private final boolean isBlock;
    private final boolean suffixIsPrefix;

    OreResourceTypes(String name, boolean isBlock, boolean suffixIsPrefix) {
        this.name = name;
        this.isBlock = isBlock;
        this.suffixIsPrefix = suffixIsPrefix;
    }

    public String getName() {
        return name;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public boolean isSuffixIsPrefix() {
        return suffixIsPrefix;
    }
}
