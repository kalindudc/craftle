package com.craftle_mod.common.block;

import com.craftle_mod.common.block.base.FacedBlockBase;
import com.craftle_mod.common.resource.IBlockResource;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

@SuppressWarnings("deprecation")
public class SpecialBlock extends FacedBlockBase {

    private static final VoxelShape SHAPE_N =
        Stream.of(Block.makeCuboidShape(8, 0.1, 7, 9, 1.1, 8),
            Block.makeCuboidShape(8.599999999999998, 0.6, 6.9,
                8.799999999999997, 0.8,
                7.1000000000000005),
            Block.makeCuboidShape(8.2, 0.6, 6.9, 8.399999999999999,
                0.8, 7.1000000000000005),
            Block.makeCuboidShape(7.800000000000001, 0.5, 7.4,
                9.200000000000001, 0.7, 7.6),
            Block.makeCuboidShape(8.200000000000001,
                0.30000000000000004,
                6.900000000000002, 8.8, 0.5,
                7.100000000000001),
            Block.makeCuboidShape(8.2, -8.326672684688674e-17, 7.1,
                8.799999999999999,
                1.4000000000000001, 8.1),
            Block.makeCuboidShape(7.9, 0.30000000000000004,
                7.199999999999997, 9.1,
                0.9000000000000001,
                7.799999999999996))
            .reduce((v1, v2) -> VoxelShapes
                .combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
        Block.makeCuboidShape(7.9999999999999964, 0.10000000000000009, 7,
            8.999999999999996, 1.1, 8),
        Block.makeCuboidShape(7.899999999999997, 0.6, 7.200000000000003,
            8.099999999999996, 0.8, 7.400000000000002),
        Block.makeCuboidShape(7.899999999999997, 0.6, 7.600000000000001,
            8.099999999999996, 0.8, 7.800000000000001),
        Block.makeCuboidShape(8.399999999999997, 0.5, 6.799999999999999,
            8.599999999999996, 0.7, 8.2),
        Block.makeCuboidShape(7.899999999999999, 0.30000000000000004,
            7.199999999999999, 8.099999999999998, 0.5,
            7.799999999999999),
        Block.makeCuboidShape(8.099999999999996, 0, 7.200000000000001,
            9.099999999999996, 1.4000000000000001,
            7.800000000000001),
        Block.makeCuboidShape(8.199999999999994, 0.30000000000000004, 6.9,
            8.799999999999992, 0.9000000000000001, 8.1))
        .reduce((v1, v2) -> VoxelShapes
            .combineAndSimplify(
                v1, v2,
                IBooleanFunction.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
        Block.makeCuboidShape(7.9999999999999964, 0.10000000000000009,
            7.0000000000000036, 8.999999999999996, 1.1,
            8.000000000000004),
        Block.makeCuboidShape(8.2, 0.6, 7.900000000000004,
            8.399999999999999, 0.8, 8.100000000000003),
        Block.makeCuboidShape(8.599999999999998, 0.6, 7.900000000000004,
            8.799999999999997, 0.8, 8.100000000000003),
        Block.makeCuboidShape(7.799999999999995, 0.5, 7.400000000000004,
            9.199999999999996, 0.7, 7.600000000000003),
        Block.makeCuboidShape(8.199999999999996, 0.30000000000000004,
            7.900000000000002, 8.799999999999995, 0.5,
            8.100000000000001),
        Block.makeCuboidShape(8.199999999999998, 0, 6.900000000000004,
            8.799999999999997, 1.4000000000000001,
            7.900000000000004),
        Block.makeCuboidShape(7.899999999999997, 0.30000000000000004,
            7.200000000000008, 9.099999999999996,
            0.9000000000000001, 7.800000000000006))
        .reduce((v1, v2) -> VoxelShapes
            .combineAndSimplify(
                v1, v2,
                IBooleanFunction.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
        Block.makeCuboidShape(8, 0.10000000000000009, 7.0000000000000036, 9,
            1.1, 8.000000000000004),
        Block.makeCuboidShape(8.9, 0.6, 7.600000000000001, 9.1, 0.8,
            7.800000000000001),
        Block.makeCuboidShape(8.9, 0.6, 7.200000000000003, 9.1, 0.8,
            7.400000000000002),
        Block.makeCuboidShape(8.4, 0.5, 6.800000000000004, 8.6, 0.7,
            8.200000000000005),
        Block.makeCuboidShape(8.899999999999999, 0.30000000000000004,
            7.200000000000005, 9.099999999999998, 0.5,
            7.800000000000004),
        Block.makeCuboidShape(7.9, 0, 7.200000000000003, 8.9,
            1.4000000000000001, 7.8000000000000025),
        Block.makeCuboidShape(8.200000000000005, 0.30000000000000004,
            6.900000000000004, 8.800000000000002,
            0.9000000000000001, 8.100000000000003))
        .reduce((v1, v2) -> VoxelShapes
            .combineAndSimplify(
                v1, v2,
                IBooleanFunction.OR)).get();

    public SpecialBlock(IBlockResource resource, BlockType blockType,
        SoundType soundType) {
        super(resource, blockType, soundType);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn,
        @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        switch (state.get(FACING)) {
            case NORTH:
                return SHAPE_N;
            case SOUTH:
                return SHAPE_S;
            case EAST:
                return SHAPE_E;
            case WEST:
                return SHAPE_W;
            default:
                return SHAPE_N;
        }
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, World worldIn,
        @Nonnull BlockPos pos, @Nonnull PlayerEntity player,
        @Nonnull Hand handIn,
        @Nonnull BlockRayTraceResult hit) {
        if (!worldIn.isRemote()) {
            ServerWorld serverWorld = (ServerWorld) worldIn;
            LightningBoltEntity entity =
                new LightningBoltEntity(worldIn, pos.getX(), pos.getY(),
                    pos.getZ(), false);
            serverWorld.addLightningBolt(entity);
        }

        return ActionResultType.SUCCESS;
    }
}
