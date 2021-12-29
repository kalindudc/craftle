package com.craftlemod.common.blockentity;

import com.craftlemod.common.CraftleMod;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidTankBlockEntity extends FactoryBlockEntity {

    public static final int MAX_TANK_LENGTH = 64;

    private int fluidCapacity;
    private int currentFluidAmount;

    public FluidTankBlockEntity(BlockEntityRecord record) {
        super(record);
        this.fluidCapacity = 0;
        this.currentFluidAmount = 0;
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
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.fluidCapacity = nbt.getInt("fluid_capacity");
        this.currentFluidAmount = nbt.getInt("current_fluid_amount");
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        if (this.isFactoryActive()) {
            if (!this.verifyFactoryShape()) {
                CraftleMod.LOGGER.error("Deactivate Factory");
                this.deactivateFactory();
                return;
            }

            // do other tank related things
            int x = 1;
        } else {
            Pair<Integer, Integer> factoryConfig = testFactoryShape(world, pos);
            // check for tank shape
            if (factoryConfig.getLeft() == 0 && factoryConfig.getRight() == 0) {
                return;
            }

            CraftleMod.LOGGER.error("Activate tank");
            // activate tank
            this.activateFactory(factoryConfig);
            //CraftleMod.LOGGER.error("\n\nshape: " + siloConfig.getLeft().toString() + "," + siloConfig.getRight().toString());

            for (FactoryIOBlockEntity entity : this.getFactoryIOs()) {
                //CraftleMod.LOGGER.error("pos: " + entity.getPos().toShortString());
                //CraftleMod.LOGGER.error("intake: " + entity.isIntake());
            }
        }
    }

    @Override
    public Pair<Integer, Integer> testFactoryShape(World world, BlockPos pos) {
        BlockPos[] baseEdges = new BlockPos[2];
        int radius = 0;
        int height = 0;
        List<FactoryIOBlockEntity> factoryIOs = new ArrayList<>();

        // check the base
        for (int i = 1; i < MAX_TANK_LENGTH; i++) {
            BlockPos pos1 = new BlockPos(pos.getX() - i, pos.getY(), pos.getZ() + i);
            BlockPos pos2 = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() - i);
            int numBlocks = 2 * i + 1;

            Pair<Boolean, List<FactoryIOBlockEntity>> edgeItems = areValidEdges(world, pos1, pos2, numBlocks);
            if (edgeItems.getLeft()) {
                baseEdges[0] = pos1;
                baseEdges[1] = pos2;
                radius++;
                factoryIOs.addAll(edgeItems.getRight());
            } else {
                break;
            }
        }

        if (baseEdges[0] == null || baseEdges[1] == null) {
            return new Pair<>(0, 0);
        }

        height++;
        // check the walls
        for (int i = 1; i < MAX_TANK_LENGTH * 2; i++) {
            BlockPos pos1 = new BlockPos(baseEdges[0].getX(), pos.getY() + i, baseEdges[0].getZ());
            BlockPos pos2 = new BlockPos(baseEdges[1].getX(), pos.getY() + i, baseEdges[1].getZ());
            int numBlocks = 2 * radius + 1;

            Pair<Boolean, List<FactoryIOBlockEntity>> edgeItems = areValidEdges(world, pos1, pos2, numBlocks);
            if (edgeItems.getLeft()) {
                height++;
                factoryIOs.addAll(edgeItems.getRight());
            } else {
                break;
            }
        }

        if (height < 3) {
            return new Pair<>(0, 0);
        }

        // check roof
        boolean validRoof = true;
        for (int i = 1; i < radius; i++) {
            BlockPos pos1 = new BlockPos(baseEdges[0].getX() + i, pos.getY() + height, baseEdges[0].getZ() - i);
            BlockPos pos2 = new BlockPos(baseEdges[1].getX() - i, pos.getY() + height, baseEdges[1].getZ() + i);
            int numBlocks = (2 * (radius - i)) + 1;

            Pair<Boolean, List<FactoryIOBlockEntity>> edgeItems = areValidEdges(world, pos1, pos2, numBlocks);
            if (!edgeItems.getLeft()) {
                validRoof = false;
                break;
            } else {
                factoryIOs.addAll(edgeItems.getRight());
            }
        }

        // check roof center
        validRoof = validRoof && isValidMultiBlock(world.getBlockState(new BlockPos(pos.getX(), pos.getY() + (height - 1), pos.getZ())).getBlock());
        if (!validRoof) {
            return new Pair<>(0, 0);
        } else {
            if (isValidFactoryIO(world.getBlockEntity(new BlockPos(pos.getX(), pos.getY() + height, pos.getZ())))) {
                factoryIOs.add((FactoryIOBlockEntity) world.getBlockEntity(new BlockPos(pos.getX(), pos.getY() + height, pos.getZ())));
            }
        }

        this.setFactoryIOs(factoryIOs);
        return new Pair<>(radius, height);
    }
}
