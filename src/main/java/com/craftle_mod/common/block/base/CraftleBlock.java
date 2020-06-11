package com.craftle_mod.common.block.base;

import com.craftle_mod.common.resource.IBlockResource;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;

public abstract class CraftleBlock extends Block {

    public static enum BlockType {
        MACHINE(),
        RESOURCE(),
        MISC()
    }

    private IBlockResource resource;
    private BlockType      blockType;

    public CraftleBlock(IBlockResource resource, BlockType blockType, SoundType soundType) {

        super(Block.Properties.create(resource.getMaterial())
                              .hardnessAndResistance(resource.getHardness(), resource.getHardness())
                              .harvestTool(resource.getHarvestTool())
                              .harvestLevel(resource.getHarvestLevel()).sound(soundType));
        this.resource  = resource;
        this.blockType = blockType;
    }

    public IBlockResource getResource() {
        return resource;
    }

    public BlockType getBlockType() {
        return blockType;
    }

}
