package com.craftle_mod.common.resource.ore;

import com.craftle_mod.common.registries.util.RegistriesUtils;
import net.minecraft.block.material.Material;

public enum OreTypes {

    COPPER("copper", Material.ROCK, 1, OreResourceTypes.values(), true, new OreResourceTypes[]{OreResourceTypes.RAW}, 1.6f),
    RUBY("ruby", Material.ROCK, 2, OreResourceTypes.values(), true, new OreResourceTypes[]{OreResourceTypes.RAW}, 1.6f),
    IRON("iron", Material.ROCK, 1, new OreResourceTypes[]{OreResourceTypes.DUST}, false, new OreResourceTypes[]{OreResourceTypes.DUST}),
    GOLD("gold", Material.ROCK, 2, new OreResourceTypes[]{OreResourceTypes.DUST}, false, null),
    SAPPHIRE("sapphire", Material.ROCK, 2, OreResourceTypes.values(), true, new OreResourceTypes[]{OreResourceTypes.RAW}, 1.6f);

    private final String name;
    private final int harvestLevel;
    private final Material material;
    private final float hardness;
    private final float resistance;
    private final OreResourceTypes[] resourceTypes;
    private final boolean shouldCreateOre;
    private final OreResourceTypes[] drops;
    private final float dropMax;

    OreTypes(String name, Material material, int harvestLevel, OreResourceTypes[] oreResourceTypes, boolean shouldCreateOre,
        OreResourceTypes[] drops) {
        this(name, material, 3.0f, 3.0f, harvestLevel, oreResourceTypes, shouldCreateOre, drops, 1.0f);
    }

    OreTypes(String name, Material material, int harvestLevel, OreResourceTypes[] oreResourceTypes, boolean shouldCreateOre,
        OreResourceTypes[] drops, float dropsMax) {
        this(name, material, 3.0f, 3.0f, harvestLevel, oreResourceTypes, shouldCreateOre, drops, dropsMax);
    }

    OreTypes(String name, Material material, float hardness, float resistance, int harvestLevel, OreResourceTypes[] oreResourceTypes,
        boolean shouldCreateOre, OreResourceTypes[] drops, float dropsMax) {
        this.name = name;
        this.material = material;
        this.hardness = hardness;
        this.resistance = resistance;
        this.harvestLevel = harvestLevel;
        this.resourceTypes = oreResourceTypes;
        this.shouldCreateOre = shouldCreateOre;
        this.drops = drops;
        this.dropMax = dropsMax;
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

    public boolean shouldCreateOre() {
        return shouldCreateOre;
    }

    public OreResourceTypes[] getDrops() {
        return drops;
    }

    public float getDropsMax() {
        return dropMax;
    }

    public String getNameWithResource(OreResourceTypes type) {
        return RegistriesUtils.buildName(type.getPrefix(), this.name, type.getSuffix());
    }
}
