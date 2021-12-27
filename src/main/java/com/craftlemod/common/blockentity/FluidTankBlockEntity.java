package com.craftlemod.common.blockentity;

import com.craftlemod.common.CraftleMod;
import com.craftlemod.common.block.machine.MachineBlock;
import com.craftlemod.common.registry.CraftleBlockEntityTypes;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidTankBlockEntity extends CraftleBlockEntity {

    public static final int MAX_TANK_LENGTH = 24;

    private int fluidCapacity;
    private int currentFluidAmount;
    private boolean isTankActive;

    public FluidTankBlockEntity(BlockEntityRecord record) {
        super(record);
        this.fluidCapacity = 8;
        this.currentFluidAmount = 0;
        this.isTankActive = false;
    }

    public boolean isTankActive() {
        return isTankActive;
    }

    public void setTankActive(boolean tankActive) {
        isTankActive = tankActive;
    }

    public int getCurrentFluidAmount() {
        return currentFluidAmount;
    }

    public void setCurrentFluidAmount(int currentFluidAmount) {
        this.currentFluidAmount = currentFluidAmount;
    }

    public int getFluidCapacity() {
        return fluidCapacity;
    }

    public void setFluidCapacity(int fluidCapacity) {
        this.fluidCapacity = fluidCapacity;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        nbt.putInt("fluid_capacity", this.fluidCapacity);
        nbt.putInt("current_fluid_amount", this.currentFluidAmount);
        nbt.putBoolean("is_tank_active", this.isTankActive);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.fluidCapacity = nbt.getInt("fluid_capacity");
        this.currentFluidAmount = nbt.getInt("current_fluid_amount");
        this.isTankActive = nbt.getBoolean("is_tank_active");
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }

        if (this.isTankActive) {
            // do tank related things
            testShape(world, pos);
        }
        else {
            // check for tank shape
        }
    }

    private void testShape(World world, BlockPos pos) {
        /*
            0: left edge
            1: bottom edge
            2: right edge
            3: top edge
         */
        int[][] edgeCords = new int[4][2];

        // find edges
        for (int i = 0; i < 4; i++) {
            int delta = 1;
            if (i < 2) {
                delta = -1;
            }

            for (int j = 0; j < MAX_TANK_LENGTH; j++) {
                int x = i % 2 == 0 ? pos.getX() + (j * delta) : pos.getX();
                int z = i % 2 != 0 ? pos.getZ() + (j * delta) : pos.getZ();
                Block block = world.getBlockState(pos).getBlock();

                if (block instanceof AbstractGlassBlock || block instanceof MachineBlock) {
                    edgeCords[i][0] = x;
                    edgeCords[i][1] = z;
                }
            }
        }
    }
}
