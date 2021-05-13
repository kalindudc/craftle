package com.craftle_mod.common.resource;

import net.minecraft.block.material.Material;

public enum OreTypes {

    COPPER("copper", Material.ROCK),
    RUBY("ruby", Material.ROCK, new OreResourceTypes[]{OreResourceTypes.BLOCK, OreResourceTypes.INGOT}),
    SAPPHIRE("sapphire", Material.ROCK, new OreResourceTypes[]{OreResourceTypes.BLOCK, OreResourceTypes.INGOT});

    public static OreTypes[] VALUES = new OreTypes[]{COPPER};

    private final String name;
    private final int harvestLevel;
    private final Material material;
    private final float hardness;
    private final float resistance;
    private final OreResourceTypes[] resourceTypes;

    OreTypes(String name, Material material) {
        this(name, material, 3.0f, 3.0f, 2);
    }

    OreTypes(String name, Material material, int harvestLevel) {
        this(name, material, 3.0f, 3.0f, harvestLevel);
    }

    OreTypes(String name, Material material, OreResourceTypes[] oreResourceTypes) {
        this(name, material, 3.0f, 3.0f, 2, oreResourceTypes);
    }

    OreTypes(String name, Material material, float hardness, float resistance, int harvestLevel) {
        this(name, material, hardness, resistance, harvestLevel, OreResourceTypes.values());
    }

    OreTypes(String name, Material material, float hardness, float resistance, int harvestLevel, OreResourceTypes[] oreResourceTypes) {
        this.name = name;
        this.material = material;
        this.hardness = hardness;
        this.resistance = resistance;
        this.harvestLevel = harvestLevel;
        this.resourceTypes = oreResourceTypes;
    }

    public String getName() {
        return name;
    }

    public int getHarvestLevel() {
        return harvestLevel;
    }

    public Material getMaterial() {
        return material;
    }

    public float getHardness() {
        return hardness;
    }

    public float getResistance() {
        return resistance;
    }

    public OreResourceTypes[] getResourceTypes() {
        return resourceTypes;
    }
}
