package com.craftle_mod.common.resource;

public enum ResourceTypes {
    ORE("ore", true),
    BLOCK("block", true);

    private final String name;
    private final boolean isBlock;

    ResourceTypes(String name, boolean isBlock) {
        this.name = name;
        this.isBlock = isBlock;
    }

    public String getName() {
        return name;
    }

    public boolean isBlock() {
        return isBlock;
    }
}
