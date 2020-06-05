package com.craftle_mod.common.block.machine;

import com.craftle_mod.common.block.base.MachineBlock;
import com.craftle_mod.common.resource.IBlockResource;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Crusher extends MachineBlock {

    public Crusher(IBlockResource resource, BlockType blockType,
                   SoundType soundType, CraftleBaseTier tier) {
        super(resource, blockType, soundType, tier);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return null;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn,
                                             BlockPos pos, PlayerEntity player,
                                             Hand handIn,
                                             BlockRayTraceResult hit) {

        worldIn.setBlockState(pos, state.with(LIT, Boolean.valueOf(
                !state.get(LIT).booleanValue())), 3);
        return ActionResultType.SUCCESS;
    }
}
