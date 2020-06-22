package com.craftle_mod.common.block.base;

import com.craftle_mod.api.BlockConstants;
import com.craftle_mod.common.resource.IBlockResource;
import com.craftle_mod.common.tier.CraftleBaseTier;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public abstract class MachineBlock extends ActiveBlockBase implements ICraftleMachineBlock {

    private final CraftleBaseTier tier;
    private final float maxCapacity;

    private float capacity;

    public MachineBlock(IBlockResource resource, BlockType blockType, SoundType soundType,
        CraftleBaseTier tier) {
        super(resource, blockType, soundType);

        this.tier = tier;
        this.maxCapacity = BlockConstants.MACHINE_BASE_CAPACITY * tier.getMultiplier();
        this.capacity = 0;
    }

    public MachineBlock(IBlockResource resource, BlockType blockType, SoundType soundType,
        CraftleBaseTier tier, float maxCapacity) {
        super(resource, blockType, soundType);

        this.tier = tier;
        this.maxCapacity = maxCapacity;
        this.capacity = 0;
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
    public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        // same as a minecraft furnace
        return state.get(LIT) ? 5 : 0;
    }
}
