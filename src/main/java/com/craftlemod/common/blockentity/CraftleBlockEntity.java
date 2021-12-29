package com.craftlemod.common.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CraftleBlockEntity extends BlockEntity {

    private BlockPos entityControllerPos;

    public CraftleBlockEntity(BlockEntityRecord record) {
        super(record.type(), record.pos(), record.state());
        this.entityControllerPos = null;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {

        if (blockEntity instanceof CraftleBlockEntity) {
            ((CraftleBlockEntity) blockEntity).tick(world, pos, state);
        }
    }

    public BlockPos getEntityControllerPos() {
        return entityControllerPos;
    }

    public void setEntityControllerPos(BlockPos entityControllerPos) {
        this.entityControllerPos = entityControllerPos;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        int x = nbt.getInt("controller_x");
        int y = nbt.getInt("controller_y");
        int z = nbt.getInt("controller_z");
        this.entityControllerPos = new BlockPos(x, y, z);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (entityControllerPos != null) {
            nbt.putInt("controller_x", entityControllerPos.getX());
            nbt.putInt("controller_y", entityControllerPos.getY());
            nbt.putInt("controller_z", entityControllerPos.getZ());
        }
    }
}
