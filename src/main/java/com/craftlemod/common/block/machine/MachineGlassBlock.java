package com.craftlemod.common.block.machine;

import java.util.function.BiFunction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class MachineGlassBlock extends MachineBlock {

    public static final IntProperty NORTH = IntProperty.of("n", 0, 1);
    public static final IntProperty SOUTH = IntProperty.of("s", 0, 1);
    public static final IntProperty EAST = IntProperty.of("e", 0, 1);
    public static final IntProperty WEST = IntProperty.of("w", 0, 1);
    public static final IntProperty UP = IntProperty.of("u", 0, 1);
    public static final IntProperty DOWN = IntProperty.of("d", 0, 1);

    public MachineGlassBlock(Identifier id, String modelPath, Settings settings, BiFunction<BlockPos, BlockState, BlockEntity> blockEntityConstructor) {
        super(id, modelPath, settings, blockEntityConstructor);
        setDefaultState(getStateManager().getDefaultState().with(NORTH, 0));
        setDefaultState(getStateManager().getDefaultState().with(SOUTH, 0));
        setDefaultState(getStateManager().getDefaultState().with(EAST, 0));
        setDefaultState(getStateManager().getDefaultState().with(WEST, 0));
        setDefaultState(getStateManager().getDefaultState().with(UP, 0));
        setDefaultState(getStateManager().getDefaultState().with(DOWN, 0));

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

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0f;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.isOf(this)) {
            return true;
        }
        return super.isSideInvisible(state, stateFrom, direction);
    }

    public void setDirectionalState(World world, BlockState state, BlockPos pos, Vec3f[] bounds) {
//        world.setBlockState(pos, state.with(NORTH, isValidNeighbor(world, new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1), bounds)));
//        world.setBlockState(pos, state.with(SOUTH, isValidNeighbor(world, new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1), bounds)));
//        world.setBlockState(pos, state.with(EAST, isValidNeighbor(world, new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ()), bounds)));
//        world.setBlockState(pos, state.with(WEST, isValidNeighbor(world, new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ()), bounds)));
//        world.setBlockState(pos, state.with(UP, isValidNeighbor(world, new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), bounds)));
//        world.setBlockState(pos, state.with(DOWN, isValidNeighbor(world, new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()), bounds)));

        world.setBlockState(pos, state.with(NORTH, 1));
        world.setBlockState(pos, state.with(SOUTH, 1));
        world.setBlockState(pos, state.with(EAST, 1));
        world.setBlockState(pos, state.with(WEST, 1));
        world.setBlockState(pos, state.with(UP, 1));
        world.setBlockState(pos, state.with(DOWN, 1));
    }

    private int isValidNeighbor(World world, BlockPos pos, Vec3f[] bounds) {

//        if (pos.getX() < bounds[0].getX() || pos.getX() > bounds[1].getX() || pos.getZ() > bounds[0].getZ() || pos.getZ() < bounds[1].getZ() || pos.getY() < bounds[0].getY()
//            || pos.getZ() > bounds[1].getZ()) {
//            return 0;
//        }

        if (world.getBlockState(pos).getBlock() instanceof MachineGlassBlock) {
            return 1;
        }

        return 0;
    }

    public void resetDirectionalState(World world, BlockState state, BlockPos pos) {
        world.setBlockState(pos, state.with(NORTH, 0));
        world.setBlockState(pos, state.with(SOUTH, 0));
        world.setBlockState(pos, state.with(EAST, 0));
        world.setBlockState(pos, state.with(WEST, 0));
        world.setBlockState(pos, state.with(UP, 0));
        world.setBlockState(pos, state.with(DOWN, 0));
    }
}
