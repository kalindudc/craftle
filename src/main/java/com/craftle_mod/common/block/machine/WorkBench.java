package com.craftle_mod.common.block.machine;

import com.craftle_mod.common.block.base.MachineBlock;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.resource.IBlockResource;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.machine.WorkBenchTileEntity;
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

public class WorkBench extends MachineBlock {

    private static final VoxelShape SHAPE = Stream.of(Block.makeCuboidShape(2, 0, 2, 14, 2, 14),
                                                      Block.makeCuboidShape(6, 2, 6, 10, 12, 10),
                                                      Block.makeCuboidShape(0, 12, 0, 16, 16, 16))
                                                  .reduce((v1, v2) -> {
                                                      return VoxelShapes.combineAndSimplify(v1, v2,
                                                                                            IBooleanFunction.OR);
                                                  }).get();
    ;

    public WorkBench(IBlockResource resource, BlockType blockType, SoundType soundType,
                     CraftleBaseTier tier) {
        super(resource, blockType, soundType, tier);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos,
                               ISelectionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return CraftleTileEntityTypes.WORKBENCH.get().create();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos,
                                             PlayerEntity player, Hand handIn,
                                             BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity entity = worldIn.getTileEntity(pos);
            if (entity instanceof WorkBenchTileEntity) {
                NetworkHooks
                        .openGui((ServerPlayerEntity) player, (WorkBenchTileEntity) entity, pos);
                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState,
                           boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity entity = worldIn.getTileEntity(pos);
            if (entity instanceof WorkBenchTileEntity) {
                InventoryHelper.dropItems(worldIn, pos, ((WorkBenchTileEntity) entity).getItems());
            }
        }
    }
}
