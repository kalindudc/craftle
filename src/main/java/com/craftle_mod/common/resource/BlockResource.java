package com.craftle_mod.common.resource;

import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public enum BlockResource implements IBlockResource {
    COPPER("copper", 3.0f, 10.0f, 1, Material.IRON), TIN("tin", 3.0f, 10.0f, 1,
        Material.IRON), ALUMINIUM("aluminium", 5.0f, 15.0f, 2, Material.IRON), PLATINUM("platinum",
        5.0f, 15.0f, 2, Material.IRON), URANIUM("uranium", 7.5f, 22.0f, 3, Material.IRON), RUBY(
        "ruby", 5.0f, 15.0f, 2, Material.IRON), SAPPHIRE("sapphire", 5.0f, 15.0f, 2,
        Material.IRON), STEEL("steel", 5.0f, 15.0f, 2, Material.IRON), WOOD("wood", 3.0f, 4.0f, 0,
        Material.WOOD, ToolType.AXE);

    private final String name;
    private final float hardness;
    private final float resistance;
    /**
     * 0: wood/gold 1: stone 2: iron 3: diamond
     */
    private final int harvestLevel;
    private final Material material;
    private final ToolType tool;

    BlockResource(String name, float hardness, float resistance, int harvestLevel) {
        this(name, hardness, resistance, harvestLevel, Material.ROCK, ToolType.PICKAXE);
    }

    BlockResource(String name, float hardness, float resistance, int harvestLevel,
        Material material) {
        this(name, hardness, resistance, harvestLevel, material, ToolType.PICKAXE);
    }

    BlockResource(String name, float hardness, float resistance, int harvestLevel,
        Material material, ToolType tool) {
        this.name = name;
        this.hardness = hardness;
        this.resistance = resistance;
        this.harvestLevel = harvestLevel;
        this.material = material;
        this.tool = tool;
    }


    @Override
    public String getResourceName() {
        return name;
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
        return this.tool;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }
}
