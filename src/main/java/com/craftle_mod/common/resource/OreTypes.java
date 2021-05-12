package com.craftle_mod.common.resource;

import net.minecraft.block.material.Material;

public enum OreTypes {

    COPPER("copper", Material.ROCK);

    public static OreTypes[] VALUES = new OreTypes[]{COPPER};

    private final String name;
    private final int harvestLevel;
    private final Material material;
    private final float hardness;
    private final float resistance;

    OreTypes(String name, Material material) {
        this(name, material, 3.0f, 3.0f, 2);
    }

    OreTypes(String name, Material material, int harvestLevel) {
        this(name, material, 3.0f, 3.0f, harvestLevel);
    }

    OreTypes(String name, Material material, float hardness, float resistance, int harvestLevel) {
        this.name = name;
        this.material = material;
        this.hardness = hardness;
        this.resistance = resistance;
        this.harvestLevel = harvestLevel;
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
}
