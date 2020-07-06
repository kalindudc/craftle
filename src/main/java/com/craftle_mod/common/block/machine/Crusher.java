package com.craftle_mod.common.block.machine;

import com.craftle_mod.common.block.base.MachineBlock;
import com.craftle_mod.common.registries.CraftleContainerTypes;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.resource.IBlockResource;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.CraftleTileEntity;
import com.craftle_mod.common.tile.machine.CrusherTileEntity;
import javax.annotation.Nonnull;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class Crusher extends MachineBlock {

    public Crusher(IBlockResource resource, BlockType blockType, SoundType soundType,
        CraftleBaseTier tier) {
        super(resource, blockType, soundType, tier, 0);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, World worldIn,
        @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn,
        @Nonnull BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity entity = worldIn.getTileEntity(pos);
            if (entity instanceof CrusherTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (CrusherTileEntity) entity, pos);
                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReplaced(BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos,
        BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity entity = worldIn.getTileEntity(pos);
            if (entity instanceof CrusherTileEntity) {
                InventoryHelper.dropItems(worldIn, pos, ((CrusherTileEntity) entity).getItems());
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public TileEntityType<? extends CraftleTileEntity> getTileType() {
        switch (getCraftleTier()) {
            case UNLIMITED:
            case TIER_4:
                return CraftleTileEntityTypes.CRUSHER_TIER_4.get();
            case TIER_3:
                return CraftleTileEntityTypes.CRUSHER_TIER_3.get();
            case TIER_2:
                return CraftleTileEntityTypes.CRUSHER_TIER_2.get();
            case TIER_1:
                return CraftleTileEntityTypes.CRUSHER_TIER_1.get();
            case BASIC:
            default:
                return CraftleTileEntityTypes.CRUSHER_BASIC.get();
        }
    }

    @Override
    public ContainerType<?> getContainerType() {
        switch (getCraftleTier()) {
            case UNLIMITED:
            case TIER_4:
                return CraftleContainerTypes.CRUSHER_TIER_4.get();
            case TIER_3:
                return CraftleContainerTypes.CRUSHER_TIER_3.get();
            case TIER_2:
                return CraftleContainerTypes.CRUSHER_TIER_2.get();
            case TIER_1:
                return CraftleContainerTypes.CRUSHER_TIER_1.get();
            case BASIC:
            default:
                return CraftleContainerTypes.CRUSHER_BASIC.get();
        }
    }
}
