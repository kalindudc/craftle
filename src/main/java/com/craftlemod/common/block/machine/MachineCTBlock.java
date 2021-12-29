package com.craftlemod.common.block.machine;

import com.craftlemod.common.CraftleMod;
import java.util.function.BiFunction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

public class MachineCTBlock extends MachineBlock {

    public static final IntProperty NORTH = IntProperty.of("n", 0, 1);
    public static final IntProperty SOUTH = IntProperty.of("s", 0, 1);
    public static final IntProperty EAST = IntProperty.of("e", 0, 1);
    public static final IntProperty WEST = IntProperty.of("w", 0, 1);
    public static final IntProperty UP = IntProperty.of("u", 0, 1);
    public static final IntProperty DOWN = IntProperty.of("d", 0, 1);

    public MachineCTBlock(Identifier id, String modelPath, Settings settings, BiFunction<BlockPos, BlockState, BlockEntity> blockEntityConstructor) {
        super(id, modelPath, settings, blockEntityConstructor);
        setDefaultState(getStateManager().getDefaultState().with(NORTH, 0).with(SOUTH, 0).with(EAST, 0).with(WEST, 0).with(UP, 0).with(DOWN, 0));
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(NORTH);
        builder.add(SOUTH);
        builder.add(EAST);
        builder.add(WEST);
        builder.add(UP);
        builder.add(DOWN);
    }

    public void setDirectionalState(World world, BlockState state, BlockPos pos, Vec3f[] bounds) {
        int north = isValidNeighbor(world, pos, new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1), bounds);
        int south = isValidNeighbor(world, pos, new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1), bounds);
        int east = isValidNeighbor(world, pos, new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ()), bounds);
        int west = isValidNeighbor(world, pos, new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ()), bounds);
        int up = isValidNeighbor(world, pos, new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), bounds);
        int down = isValidNeighbor(world, pos, new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()), bounds);

        world.setBlockState(pos, state.with(NORTH, north).with(SOUTH, south).with(EAST, east).with(WEST, west).with(UP, up).with(DOWN, down));

    }

    private int isValidNeighbor(World world, BlockPos current, BlockPos neighbor, Vec3f[] bounds) {

        CraftleMod.LOGGER.error(bounds[0].getX() + "," + bounds[1].getX());
        CraftleMod.LOGGER.error(bounds[0].getY() + "," + bounds[1].getY());
        CraftleMod.LOGGER.error(bounds[0].getZ() + "," + bounds[1].getZ());
//        if (neighbor.getX() < bounds[0].getX() || neighbor.getX() > bounds[1].getX() || neighbor.getZ() > bounds[0].getZ() || neighbor.getZ() < bounds[1].getZ()
//            || neighbor.getY() < bounds[0].getY() || neighbor.getZ() > bounds[1].getZ()) {
//            return 0;
//        }

        if (world.getBlockState(neighbor).getBlock() instanceof MachineCTBlock block && block.getId().equals(((MachineCTBlock) world.getBlockState(current).getBlock()).getId())) {
            return 1;
        }

        return 0;
    }

    public void resetDirectionalState(World world, BlockState state, BlockPos pos) {
        world.setBlockState(pos, state.with(NORTH, 0).with(SOUTH, 0).with(EAST, 0).with(WEST, 0).with(UP, 0).with(DOWN, 0));
    }
}
