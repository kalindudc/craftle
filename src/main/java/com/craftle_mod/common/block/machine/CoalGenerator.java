package com.craftle_mod.common.block.machine;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.base.MachineBlock;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.resource.IBlockResource;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import com.craftle_mod.common.tile.machine.CoalGeneratorTileEntity;
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

public class CoalGenerator extends MachineBlock {

    public CoalGenerator(IBlockResource resource, BlockType blockType, SoundType soundType,
                         CraftleBaseTier tier) {
        super(resource, blockType, soundType, tier);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        CoalGeneratorTileEntity entity = CraftleTileEntityTypes.COAL_GENERATOR.get().create();
        return entity;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos,
                                             PlayerEntity player, Hand handIn,
                                             BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity entity = worldIn.getTileEntity(pos);
            if (entity instanceof CoalGeneratorTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (CoalGeneratorTileEntity) entity,
                                     pos);
                Craftle.logInfo("Openning GUI %d",
                                ((PoweredMachineTileEntity) entity).getEnergyContainer()
                                                                   .getEnergyStored());
                Craftle.logInfo("HASH: " + ((PoweredMachineTileEntity) entity).hashCode());
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
            if (entity instanceof CoalGeneratorTileEntity) {
                InventoryHelper
                        .dropItems(worldIn, pos, ((CoalGeneratorTileEntity) entity).getItems());
            }
        }
    }

    @Override
    public void changeState(boolean b, BlockState state, World worldIn, BlockPos pos) {
        this.setActive(b, state, worldIn, pos, this);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
