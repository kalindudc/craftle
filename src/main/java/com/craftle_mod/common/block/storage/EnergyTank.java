package com.craftle_mod.common.block.storage;

import com.craftle_mod.common.block.base.MachineBlock;
import com.craftle_mod.common.registries.CraftleContainerTypes;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.resource.IBlockResource;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.CraftleTileEntity;
import com.craftle_mod.common.tile.storage.EnergyTankTileEntity;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

@SuppressWarnings("deprecation")
public class EnergyTank extends MachineBlock {

    public EnergyTank(IBlockResource resource, BlockType blockType, SoundType soundType,
        CraftleBaseTier tier) {
        super(resource, blockType, soundType, tier);

    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return getTileType().create();
    }

    @Override
    public void onReplaced(BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos,
        BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity entity = worldIn.getTileEntity(pos);
            if (entity instanceof EnergyTankTileEntity) {
                InventoryHelper.dropItems(worldIn, pos, ((EnergyTankTileEntity) entity).getItems());
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

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, World worldIn,
        @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn,
        @Nonnull BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity entity = worldIn.getTileEntity(pos);

            if (entity instanceof EnergyTankTileEntity) {
                NetworkHooks
                    .openGui((ServerPlayerEntity) player, (EnergyTankTileEntity) entity, pos);
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

    @Override
    public TileEntityType<? extends CraftleTileEntity> getTileType() {
        switch (getCraftleTier()) {
            case UNLIMITED:
            case TIER_4:
                return CraftleTileEntityTypes.ENERGY_TANK_TIER_4.get();
            case TIER_3:
                return CraftleTileEntityTypes.ENERGY_TANK_TIER_3.get();
            case TIER_2:
                return CraftleTileEntityTypes.ENERGY_TANK_TIER_2.get();
            case TIER_1:
                return CraftleTileEntityTypes.ENERGY_TANK_TIER_1.get();
            case BASIC:
            default:
                return CraftleTileEntityTypes.ENERGY_TANK_BASIC.get();
        }
    }

    @Override
    public ContainerType<?> getContainerType() {
        switch (getCraftleTier()) {
            case UNLIMITED:
            case TIER_4:
                return CraftleContainerTypes.ENERGY_TANK_TIER_4.get();
            case TIER_3:
                return CraftleContainerTypes.ENERGY_TANK_TIER_3.get();
            case TIER_2:
                return CraftleContainerTypes.ENERGY_TANK_TIER_2.get();
            case TIER_1:
                return CraftleContainerTypes.ENERGY_TANK_TIER_1.get();
            case BASIC:
            default:
                return CraftleContainerTypes.ENERGY_TANK_BASIC.get();
        }
    }
}
