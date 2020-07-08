package com.craftle_mod.common.resource;

public enum ResourceType implements ICraftleResource {
    ORE("ore"), DUST("dust"), INGOT("ingot"), BIT("bit"), PURIFIED("purified"), BLOCK(
        "block"), ENHANCED("enhanced");

    private final String name;

    ResourceType(String name) {
        this.name = name;
    }

    @Override
    public String getResourceName() {
        return name;
    }
}
