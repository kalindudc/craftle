package com.craftle_mod.common.resource;

import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

/**
 * An interface to represent a resource class for a Craftle Block.
 * <p>
 * Every Resource must contain a hardness value, resistance value and a harvest level.
 */
public interface IBlockResource extends ICraftleResource {

    float getHardness();

    float getResistance();

    int getHarvestLevel();

    ToolType getHarvestTool();

    Material getMaterial();
}
