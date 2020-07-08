package com.craftle_mod.common.block.machine;

import com.craftle_mod.common.block.base.MachineBlock;
import com.craftle_mod.common.registries.CraftleContainerTypes;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.resource.IBlockResource;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.CraftleTileEntity;
import com.craftle_mod.common.tile.machine.CoalGeneratorTileEntity;
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
public class CoalGenerator extends MachineBlock {

    public CoalGenerator(IBlockResource resource, BlockType blockType, SoundType soundType,
        CraftleBaseTier tier) {
        super(resource, blockType, soundType, tier);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return CraftleTileEntityTypes.COAL_GENERATOR.get().create();
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, World worldIn,
        @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn,
        @Nonnull BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity entity = worldIn.getTileEntity(pos);
            if (entity instanceof CoalGeneratorTileEntity) {
                NetworkHooks
                    .openGui((ServerPlayerEntity) player, (CoalGeneratorTileEntity) entity, pos);
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
            if (entity instanceof CoalGeneratorTileEntity) {
                InventoryHelper
                    .dropItems(worldIn, pos, ((CoalGeneratorTileEntity) entity).getItems());
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public void changeState(boolean b, BlockState state, World worldIn, BlockPos pos) {
        this.setActive(b, state, worldIn, pos, this);
    }

    @Override
    public TileEntityType<? extends CraftleTileEntity> getTileType() {
        return CraftleTileEntityTypes.COAL_GENERATOR.get();
    }

    @Override
    public ContainerType<?> getContainerType() {
        return CraftleContainerTypes.COAL_GENERATOR.get();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        // same as a minecraft furnace
        return state.get(LIT) ? 13 : 0;
    }
}
