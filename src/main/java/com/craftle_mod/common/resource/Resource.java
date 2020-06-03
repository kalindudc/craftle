package com.craftle_mod.common.resource;

public enum Resource implements ICraftleResource {
    // minecraft
    IRON("iron"),
    GOLD("gold"),
    DIAMOND("diamond"),
    // craftle
    COPPER("copper"),
    TIN("tin"),
    STEEL("steel"),
    ALUMINIUM("aluminium"),
    PLATINUM("platinum"),
    URANIUM("uranium"),
    RUBY("ruby"),
    SAPPHIRE("sapphire");

    private final String name;

    Resource(String name) {
        this.name = name;
    }

    @Override
    public String getResourceName() {
        return name;
    }
}
