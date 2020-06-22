package com.craftle_mod.common.block.base;

import com.craftle_mod.common.tier.CraftleBaseTier;

public interface ICraftleMachineBlock {

    float getMaxEnergyCapacity();

    float getEnergyCapacity();

    void setEnergyCapacity(float capacity);

    CraftleBaseTier getCraftleTier();
}
