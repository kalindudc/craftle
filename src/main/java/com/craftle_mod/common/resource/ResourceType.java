package com.craftle_mod.common.resource;

public enum ResourceType implements ICraftleResource {
    ORE("ore", "Ore"),
    DUST("dust", "Dust"),
    INGOT("ingot", "Ingot"),
    BIT("bit", "Bit"),
    PURIFIED("purified", "Purified"),
    BLOCK("block", "Block"),
    ENHANCED("enhanced", "Enhanced");

    private final String name;
    private final String formattedName;

    ResourceType(String name, String formattedName) {
        this.name = name;
        this.formattedName = formattedName;
    }

    @Override
    public String getResourceName() {
        return name;
    }

    @Override
    public String getFormattedName() {
        return null;
    }
}
