package com.craftle_mod.common.block.base;

import com.craftle_mod.common.resource.IBlockResource;
import com.craftle_mod.common.tile.base.CraftleTileEntity;
import net.minecraft.block.SoundType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;

public class ResourceBlock extends CraftleBlock {

    public ResourceBlock(IBlockResource resource, BlockType blockType, SoundType soundType) {
        super(resource, blockType, soundType);
    }

    @Override
    public TileEntityType<? extends CraftleTileEntity> getTileType() {
        return null;
    }

    @Override
    public ContainerType<?> getContainerType() {
        return null;
    }


}
