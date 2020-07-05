package com.craftle_mod.common.block.storage;

import com.craftle_mod.common.block.base.MachineBlock;
import com.craftle_mod.common.resource.IBlockResource;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.CraftleTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CableConnector extends MachineBlock {

    public CableConnector(IBlockResource resource, BlockType blockType, SoundType soundType) {
        super(resource, blockType, soundType, CraftleBaseTier.UNLIMITED);
    }

    @Override
    public void changeState(boolean b, BlockState state, World worldIn, BlockPos pos) {
        this.setActive(b, state, worldIn, pos, this);
    }

    @Override
    public TileEntityType<? extends CraftleTileEntity> getTileType() {
        return null; // TODO: implement
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

}
