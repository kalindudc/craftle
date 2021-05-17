package com.craftle_mod.common.block;

import com.craftle_mod.common.block.base.HasTileEntity;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class CraftleBlock extends Block {

    public CraftleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return this instanceof HasTileEntity;
    }

    @Override
    public TileEntity createTileEntity(@Nonnull BlockState state, @Nonnull IBlockReader world) {
        if (this instanceof HasTileEntity) {
            return ((HasTileEntity<?>) this).getTileEntityType().create();
        }
        return null;
    }
}
