package com.craftlemod.common.blockentity;

import com.craftlemod.common.CraftleMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CraftleBlockEntity extends BlockEntity{

    public CraftleBlockEntity(BlockEntityRecord record) {
        super(record.type(), record.pos(), record.state());
    }

    public void tick(World world, BlockPos pos, BlockState state) {
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {

        if (blockEntity instanceof CraftleBlockEntity) {
            ((CraftleBlockEntity) blockEntity).tick(world, pos, state);
        }
    }
}
