package com.craftle_mod.common.block.machine;

import com.craftle_mod.common.block.base.MachineBlock;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.resource.IBlockResource;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.machine.crusher.CrusherTileEntity;
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

public class Crusher extends MachineBlock {

    public Crusher(IBlockResource resource, BlockType blockType, SoundType soundType,
                   CraftleBaseTier tier) {
        super(resource, blockType, soundType, tier, 0);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        switch (getCraftleTier()) {
            case UNLIMITED:
            case TIER_4:
                return CraftleTileEntityTypes.CRUSHER_TIER_4.get().create();
            case TIER_3:
                return CraftleTileEntityTypes.CRUSHER_TIER_3.get().create();
            case TIER_2:
                return CraftleTileEntityTypes.CRUSHER_TIER_2.get().create();
            case TIER_1:
                return CraftleTileEntityTypes.CRUSHER_TIER_1.get().create();
            case BASIC:
            default:
                return CraftleTileEntityTypes.CRUSHER_BASIC.get().create();
        }

    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos,
                                             PlayerEntity player, Hand handIn,
                                             BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity entity = worldIn.getTileEntity(pos);
            if (entity instanceof CrusherTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (CrusherTileEntity) entity, pos);
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
            if (entity instanceof CrusherTileEntity) {
                InventoryHelper.dropItems(worldIn, pos, ((CrusherTileEntity) entity).getItems());
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
}
