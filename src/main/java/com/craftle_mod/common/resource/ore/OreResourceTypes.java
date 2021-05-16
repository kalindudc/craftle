package com.craftle_mod.common.resource.ore;

import com.craftle_mod.common.resource.ResourceTypes;

public enum OreResourceTypes {

    ORE("ore", null, "ore", ResourceTypes.ORE),
    INGOT("ingot", null, "ingot", ResourceTypes.INGOT),
    RAW("raw", "raw", null, ResourceTypes.RAW_ORE),
    DUST("dust", null, "dust", ResourceTypes.DUST),
    BLOCK("block", null, "block", ResourceTypes.STORAGE_BLOCK),
    RAW_BLOCK("raw_block", "raw", "block", ResourceTypes.RAW_BLOCK);

    private final String name;
    private final String suffix;
    private final String prefix;
    private final ResourceTypes resourceType;

    OreResourceTypes(String name, String prefix, String suffix, ResourceTypes resourceType) {
        this.name = name;
        this.suffix = suffix;
        this.prefix = prefix;
        this.resourceType = resourceType;
    }

    public String getName() {
        return name;
    }


    public String getSuffix() {
        return suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public ResourceTypes getResourceType() {
        return resourceType;
    }
}
