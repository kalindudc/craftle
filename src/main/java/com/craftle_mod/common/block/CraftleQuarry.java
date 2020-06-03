package com.craftle_mod.common.block;

import com.craftle_mod.common.block.base.CraftleActiveBlockBase;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class CraftleQuarry extends CraftleActiveBlockBase {

    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public CraftleQuarry(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return CraftleTileEntityTypes.QUARRY.get().create();
    }
}
