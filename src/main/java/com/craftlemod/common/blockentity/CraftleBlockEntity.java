package com.craftlemod.common.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CraftleBlockEntity extends BlockEntity {

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

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }
}
