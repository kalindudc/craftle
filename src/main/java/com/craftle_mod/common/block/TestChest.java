package com.craftle_mod.common.block;

import com.craftle_mod.common.block.base.FacedBlockBase;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.resource.IBlockResource;
import com.craftle_mod.common.tile.TileEntityTestChest;
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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class TestChest extends FacedBlockBase {

    public TestChest(IBlockResource resource, BlockType blockType, SoundType soundType) {
        super(resource, blockType, soundType);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return CraftleTileEntityTypes.TEST_CHEST.get().create();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos,
                                             PlayerEntity player, Hand handIn,
                                             BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity entity = worldIn.getTileEntity(pos);
            if (entity instanceof TileEntityTestChest) {
                NetworkHooks
                        .openGui((ServerPlayerEntity) player, (TileEntityTestChest) entity, pos);
                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.FAIL;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState,
                           boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity entity = worldIn.getTileEntity(pos);
            if (entity instanceof TileEntityTestChest) {
                InventoryHelper.dropItems(worldIn, pos, ((TileEntityTestChest) entity).getItems());
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
}
