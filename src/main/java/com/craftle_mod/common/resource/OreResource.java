package com.craftle_mod.common.resource;

import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public enum OreResource implements IBlockResource {
    COPPER("copper", 1), TIN("tin", 1), URANIUM("uranium", 3), RUBY("ruby", 2), SAPPHIRE("sapphire",
        2);

    private final String name;
    private final float hardness;
    private final float resistance;
    /**
     * 0: wood/gold 1: stone 2: iron 3: diamond
     */
    private final int harvestLevel;

    OreResource(String name, int harvestLevel) {
        this.name = name;
        this.harvestLevel = harvestLevel;
        this.hardness = 3.0f;
        this.resistance = 10.0f;
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
}
