package com.craftle_mod.common.resource;

import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public enum OreResource implements IBlockResource {
    COPPER("copper", 1, "Copper"),
    TIN("tin", 1, "Tin"),
    URANIUM("uranium", 3, "Uranium"),
    RUBY("ruby", 2, "Ruby"),
    SAPPHIRE("sapphire", 2, "Sapphire");

    private final String name;
    private final String formattedName;
    private final float hardness;
    private final float resistance;
    /**
     * 0: wood/gold 1: stone 2: iron 3: diamond
     */
    private final int harvestLevel;

    OreResource(String name, int harvestLevel, String formattedName) {
        this.name = name;
        this.harvestLevel = harvestLevel;
        this.hardness = 3.0f;
        this.resistance = 10.0f;
        this.formattedName = formattedName;
    }

    @Override
    public float getHardness() {
        return hardness;
    }

    @Override
    public float getResistance() {
        return resistance;
    }

    @Override
    public int getHarvestLevel() {
        return harvestLevel;
    }

    @Override
    public ToolType getHarvestTool() {
        return ToolType.PICKAXE;
    }

    @Override
    public Material getMaterial() {
        return Material.ROCK;
    }

    @Override
    public String getResourceName() {
        return name;
    }

    @Override
    public String getFormattedName() {
        return formattedName;
    }
}
