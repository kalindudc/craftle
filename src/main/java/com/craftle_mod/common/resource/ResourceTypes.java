package com.craftle_mod.common.resource;

public enum ResourceTypes {
    ORE("ore");

    private final String name;

    ResourceTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
