package com.craftle_mod.common.block.machine;

import com.craftle_mod.common.block.base.MachineBlock;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.resource.IBlockResource;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.SoundType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class Quarry extends MachineBlock {

    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public Quarry(IBlockResource resource, BlockType blockType, SoundType soundType) {
        super(resource, blockType, soundType, CraftleBaseTier.BASIC);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return CraftleTileEntityTypes.QUARRY.get().create();
    }
}
