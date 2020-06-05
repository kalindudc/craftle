package com.craftle_mod.common.block.base;

import com.craftle_mod.common.resource.IBlockResource;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public abstract class MachineBlock extends ActiveBlockBase
        implements ICraftleMachineBlock {

    private final static float BASE_CAPACITY = 235_000F;

    private final CraftleBaseTier tier;
    private final float           maxCapacity;

    private float capacity;

    public MachineBlock(IBlockResource resource, BlockType blockType,
                        SoundType soundType, CraftleBaseTier tier) {
        super(resource, blockType, soundType);

        this.tier        = tier;
        this.maxCapacity = BASE_CAPACITY * tier.getMultiplier();
        this.capacity    = 0;
    }

    @Override
    public float getMaxEnergyCapacity() {
        return maxCapacity;
    }

    @Override
    public float getEnergyCapacity() {
        return capacity;
    }

    @Override
    public void setEnergyCapacity(float capacity) {
        this.capacity = capacity;
    }

    @Override
    public CraftleBaseTier getCraftleTier() {
        return tier;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public abstract TileEntity createTileEntity(BlockState state,
                                                IBlockReader world);
}
