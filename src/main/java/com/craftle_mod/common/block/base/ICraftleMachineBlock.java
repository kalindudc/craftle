package com.craftle_mod.common.block.base;

import com.craftle_mod.common.tier.CraftleBaseTier;

public interface ICraftleMachineBlock {

    public float getMaxEnergyCapacity();

    public float getEnergyCapacity();

    public void setEnergyCapacity(float capacity);

    public CraftleBaseTier getCraftleTier();
}
