package com.craftle_mod.common.block.storage;

import com.craftle_mod.common.block.base.MachineBlock;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.resource.IBlockResource;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.storage.energy_matrix.EnergyMatrixTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
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
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class EnergyMatrix extends MachineBlock {

    // @formatter:off
    private static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(1, 1, 1, 15, 15, 15),
            Block.makeCuboidShape(5, 5, 0, 11, 16, 1),
            Block.makeCuboidShape(5, 15, 1, 11, 16, 11),
            Block.makeCuboidShape(0, 0, 5, 11, 1, 11),
            Block.makeCuboidShape(0, 1, 5, 1, 10.999999999999998, 11),
            Block.makeCuboidShape(15, 5, 5, 16, 11, 16),
            Block.makeCuboidShape(5.000000000000002, 5, 15, 15, 11, 16),
            Block.makeCuboidShape(15, 3, 0, 16, 16, 4),
            Block.makeCuboidShape(12, 3, 0, 15, 16, 1),
            Block.makeCuboidShape(12, 15, 1, 15, 16, 4),
            Block.makeCuboidShape(3, 0, 15, 16, 4, 16),
            Block.makeCuboidShape(3, 0, 12, 16, 1, 15),
            Block.makeCuboidShape(15, 1, 12, 16, 4, 15),
            Block.makeCuboidShape(0, 15, 12, 13, 16, 15),
            Block.makeCuboidShape(0, 12, 15, 13, 16, 16),
            Block.makeCuboidShape(0, 12, 12, 1, 15, 15),
            Block.makeCuboidShape(0, 0, 0, 1, 13, 4),
            Block.makeCuboidShape(1, 0, 0, 4, 13, 1),
            Block.makeCuboidShape(1, 0, 1, 4, 1, 4)).reduce((v1, v2) -> {
                return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
            }).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(1, 1, 1, 15, 15, 15),
            Block.makeCuboidShape(15, 5, 5, 16, 16, 11),
            Block.makeCuboidShape(5, 15, 5, 15, 16, 11),
            Block.makeCuboidShape(5, 0, 0, 11, 1, 11),
            Block.makeCuboidShape(5, 1, 0, 11, 10.999999999999998, 1),
            Block.makeCuboidShape(0, 5, 15, 11, 11, 16),
            Block.makeCuboidShape(0, 5, 5.000000000000002, 1, 11, 15),
            Block.makeCuboidShape(12, 3, 15, 16, 16, 16),
            Block.makeCuboidShape(15, 3, 12, 16, 16, 15),
            Block.makeCuboidShape(12, 15, 12, 15, 16, 15),
            Block.makeCuboidShape(0, 0, 3, 1, 4, 16),
            Block.makeCuboidShape(1, 0, 3, 4, 1, 16),
            Block.makeCuboidShape(1, 1, 15, 4, 4, 16),
            Block.makeCuboidShape(1, 15, 0, 4, 16, 13),
            Block.makeCuboidShape(0, 12, 0, 1, 16, 13),
            Block.makeCuboidShape(1, 12, 0, 4, 15, 1),
            Block.makeCuboidShape(12, 0, 0, 16, 13, 1),
            Block.makeCuboidShape(15, 0, 1, 16, 13, 4),
            Block.makeCuboidShape(12, 0, 1, 15, 1, 4)).reduce((v1, v2) -> {
                return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
            }).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(1, 1, 1, 15, 15, 15),
            Block.makeCuboidShape(5, 5, 15, 11, 16, 16),
            Block.makeCuboidShape(5, 15, 5, 11, 16, 15),
            Block.makeCuboidShape(5, 0, 5, 16, 1, 11),
            Block.makeCuboidShape(15, 1, 5, 16, 10.999999999999998, 11),
            Block.makeCuboidShape(0, 5, 0, 1, 11, 11),
            Block.makeCuboidShape(1, 5, 0, 10.999999999999998, 11, 1),
            Block.makeCuboidShape(0, 3, 12, 1, 16, 16),
            Block.makeCuboidShape(1, 3, 15, 4, 16, 16),
            Block.makeCuboidShape(1, 15, 12, 4, 16, 15),
            Block.makeCuboidShape(0, 0, 0, 13, 4, 1),
            Block.makeCuboidShape(0, 0, 1, 13, 1, 4),
            Block.makeCuboidShape(0, 1, 1, 1, 4, 4),
            Block.makeCuboidShape(3, 15, 1, 16, 16, 4),
            Block.makeCuboidShape(3, 12, 0, 16, 16, 1),
            Block.makeCuboidShape(15, 12, 1, 16, 15, 4),
            Block.makeCuboidShape(15, 0, 12, 16, 13, 16),
            Block.makeCuboidShape(12, 0, 15, 15, 13, 16),
            Block.makeCuboidShape(12, 0, 12, 15, 1, 15)).reduce((v1, v2) -> {
                return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
            }).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(1, 1, 1, 15, 15, 15),
            Block.makeCuboidShape(0, 5, 5, 1, 16, 11),
            Block.makeCuboidShape(1, 15, 5, 11, 16, 11),
            Block.makeCuboidShape(5, 0, 5, 11, 1, 16),
            Block.makeCuboidShape(5, 1, 15, 11, 10.999999999999998, 16),
            Block.makeCuboidShape(5, 5, 0, 16, 11, 1),
            Block.makeCuboidShape(15, 5, 1, 16, 11, 10.999999999999998),
            Block.makeCuboidShape(0, 3, 0, 4, 16, 1),
            Block.makeCuboidShape(0, 3, 1, 1, 16, 4),
            Block.makeCuboidShape(1, 15, 1, 4, 16, 4),
            Block.makeCuboidShape(15, 0, 0, 16, 4, 13),
            Block.makeCuboidShape(12, 0, 0, 15, 1, 13),
            Block.makeCuboidShape(12, 1, 0, 15, 4, 1),
            Block.makeCuboidShape(12, 15, 3, 15, 16, 16),
            Block.makeCuboidShape(15, 12, 3, 16, 16, 16),
            Block.makeCuboidShape(12, 12, 15, 15, 15, 16),
            Block.makeCuboidShape(0, 0, 15, 4, 13, 16),
            Block.makeCuboidShape(0, 0, 12, 1, 13, 15),
            Block.makeCuboidShape(1, 0, 12, 4, 1, 15)).reduce((v1, v2) -> {
                return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
            }).get();

    // @formatter:on

    public EnergyMatrix(IBlockResource resource, BlockType blockType, SoundType soundType,
                        CraftleBaseTier tier) {
        super(resource, blockType, soundType, tier);

    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        switch (getCraftleTier()) {
            case UNLIMITED:
            case TIER_4:
                return CraftleTileEntityTypes.ENERGY_MATRIX_TIER_4.get().create();
            case TIER_3:
                return CraftleTileEntityTypes.ENERGY_MATRIX_TIER_3.get().create();
            case TIER_2:
                return CraftleTileEntityTypes.ENERGY_MATRIX_TIER_2.get().create();
            case TIER_1:
                return CraftleTileEntityTypes.ENERGY_MATRIX_TIER_1.get().create();
            case BASIC:
            default:
                return CraftleTileEntityTypes.ENERGY_MATRIX_BASIC.get().create();
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos,
                               ISelectionContext context) {

        switch (state.get(super.FACING)) {
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

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState,
                           boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity entity = worldIn.getTileEntity(pos);
            if (entity instanceof EnergyMatrixTileEntity) {
                InventoryHelper
                        .dropItems(worldIn, pos, ((EnergyMatrixTileEntity) entity).getItems());
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public void changeState(boolean b, BlockState state, World worldIn, BlockPos pos) {
        this.setActive(b, state, worldIn, pos, this);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos,
                                             PlayerEntity player, Hand handIn,
                                             BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity entity = worldIn.getTileEntity(pos);

            if (entity instanceof EnergyMatrixTileEntity) {
                NetworkHooks
                        .openGui((ServerPlayerEntity) player, (EnergyMatrixTileEntity) entity, pos);
                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        // same as a minecraft furnace
        return state.get(LIT) ? 9 : 0;
    }
}
