package com.craftlemod.common.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FactoryIOBlockEntity extends FactoryBlockEntity {

    private boolean isIntake;

    public FactoryIOBlockEntity(BlockEntityRecord record, boolean isIntake) {
        super(record);
        this.isIntake = isIntake;
    }

    public boolean isIntake() {
        return isIntake;
    }

    public void setIntake(boolean intake) {
        isIntake = intake;
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state) {
        super.tick(world, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("is_intake", isIntake);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.isIntake = nbt.getBoolean("is_intake");
    }
}
