package com.craftlemod.common.blockentity;

import com.craftlemod.common.CraftleMod;
import java.awt.List;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
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
