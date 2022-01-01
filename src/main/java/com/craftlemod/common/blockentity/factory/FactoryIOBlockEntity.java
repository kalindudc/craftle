package com.craftlemod.common.blockentity.factory;

import com.craftlemod.common.blockentity.BlockEntityRecord;
import com.craftlemod.common.screen.FactoryIOScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new FactoryIOScreenHandler(syncId, inv, this);
    }

}
