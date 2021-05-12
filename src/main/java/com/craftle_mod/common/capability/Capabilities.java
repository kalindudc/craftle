package com.craftle_mod.common.capability;

import com.craftle_mod.common.capability.energy.CraftleEnergyStorage;
import com.craftle_mod.common.capability.energy.ICraftleEnergyStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class Capabilities {

    @CapabilityInject(ICraftleEnergyStorage.class)
    public static Capability<ICraftleEnergyStorage> ENERGY_CAPABILITY = null;


    public static void registerCapabilities() {
        CraftleEnergyStorage.register();
    }

}

