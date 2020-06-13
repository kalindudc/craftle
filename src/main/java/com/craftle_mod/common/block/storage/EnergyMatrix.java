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
    private static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(0, 13, 0, 16, 16, 16),
            Block.makeCuboidShape(6, 1, 6, 10, 15, 10),
            Block.makeCuboidShape(0, 0, 0, 16, 3, 16),
            Block.makeCuboidShape(13, 1, 1, 15, 15, 3),
            Block.makeCuboidShape(13, 1, 13, 15, 15, 15),
            Block.makeCuboidShape(10, 1, 1, 12, 15, 3),
            Block.makeCuboidShape(10, 1, 13, 12, 15, 15),
            Block.makeCuboidShape(7, 1, 1, 9, 15, 3),
            Block.makeCuboidShape(7, 1, 13, 9, 15, 15),
            Block.makeCuboidShape(4, 1, 1, 6, 15, 3),
            Block.makeCuboidShape(1, 1, 1, 3, 15, 3),
            Block.makeCuboidShape(1, 1, 13, 3, 15, 15),
            Block.makeCuboidShape(1, 1, 10, 3, 15, 12),
            Block.makeCuboidShape(4, 1, 13, 6, 15, 15),
            Block.makeCuboidShape(13, 1, 10, 15, 15, 12),
            Block.makeCuboidShape(1, 1, 7, 3, 15, 9),
            Block.makeCuboidShape(13, 1, 7, 15, 15, 9),
            Block.makeCuboidShape(1, 1, 4, 3, 15, 6),
            Block.makeCuboidShape(13, 1, 4, 15, 15, 6)).reduce((v1, v2) -> {
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
        return SHAPE;
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
}
