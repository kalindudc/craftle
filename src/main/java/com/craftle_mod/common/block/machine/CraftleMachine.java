package com.craftle_mod.common.block.machine;

import com.craftle_mod.common.block.base.CraftleDirectionalBlock;
import com.craftle_mod.common.block.base.HasTileEntity;
import com.craftle_mod.common.tile.CraftleTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

@SuppressWarnings("Nonnull")
public class CraftleMachine extends CraftleDirectionalBlock implements HasTileEntity<CraftleTileEntity> {

    public CraftleMachine(Properties properties) {
        super(properties);
    }


    @Override
    public TileEntityType<CraftleTileEntity> getTileEntityType() {
        return null;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
        BlockRayTraceResult hit) {
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
