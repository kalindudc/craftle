package com.craftle_mod.common.item.base;

import com.craftle_mod.common.capability.energy.EnergyContainerCapability;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnergyProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {

    private EnergyContainerCapability energyContainer;

    public EnergyProvider(int capacity, int maxReceive, int maxExtract, int energy,
                          CraftleBaseTier tier) {
        energyContainer =
                new EnergyContainerCapability(capacity, maxReceive, maxExtract, energy, tier);

    }


    public boolean hasCapability(Capability<?> capability) {
        return capability == CapabilityEnergy.ENERGY;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (hasCapability(cap))
            return LazyOptional.of(() -> (T) this.energyContainer);
        return LazyOptional.empty();
    }

    public EnergyContainerCapability getEnergyContainer() {
        return energyContainer;
    }

    public void setEnergyContainer(EnergyContainerCapability energyContainer) {
        this.energyContainer = energyContainer;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();

        this.energyContainer.writeToNBT(compound);

        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.getEnergyContainer().readFromNBT(nbt);
    }
}
