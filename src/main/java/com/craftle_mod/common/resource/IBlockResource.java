package com.craftle_mod.common.resource;

import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

/**
 * An interface to represent a resource class for a Craftle Block.
 * <p>
 * Every Resource must contain a hardness value, resistance value and a
 * harvest level.
 */
public interface IBlockResource extends ICraftleResource {

    public float getHardness();

    public float getResistance();

    public int getHarvestLevel();

    public ToolType getHarvestTool();

    public Material getMaterial();

}
