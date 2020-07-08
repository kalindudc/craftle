package com.craftle_mod.common.tile.base;

import com.craftle_mod.common.inventory.slot.SlotConfig;

public interface IHasEnergySlot {

    SlotConfig getResourceSlotConfig();

    SlotConfig getEnergySlotConfig();

}
