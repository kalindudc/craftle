package com.craftle_mod.common.resource;

public enum ResourceTypes {
    ORE("ore", true, "ores"),
    STONE("stone", true, "stones"),
    STORAGE_BLOCK("storage_block", true, "storage_blocks"),
    BLOCK("block", true, "blocks"),
    INGOT("ingot", false, "ingots"),
    DUST("dust", false, "dusts"),
    RAW_ORE("raw_ore", false, "raw_ores"),
    RAW_BLOCK("raw_block", true, "storage_blocks");

    private final String name;
    private final boolean isBlock;
    private final String tagCategory;

    ResourceTypes(String name, boolean isBlock, String tagCategory) {
        this.name = name;
        this.isBlock = isBlock;
        this.tagCategory = tagCategory;
    }

    public String getName() {
        return name;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public String getTagCategory() {
        return tagCategory;
    }
}
