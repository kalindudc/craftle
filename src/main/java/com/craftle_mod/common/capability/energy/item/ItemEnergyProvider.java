package com.craftle_mod.common.capability.energy.item;

import com.craftle_mod.common.capability.Capabilities;
import com.craftle_mod.common.capability.energy.CraftleEnergyStorage;
import com.craftle_mod.common.tier.CraftleBaseTier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ItemEnergyProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {

    private CraftleEnergyStorage energyContainer;

    public ItemEnergyProvider(double capacity, double maxReceive, double maxExtract, double energy,
        CraftleBaseTier tier) {
        energyContainer = new CraftleEnergyStorage(capacity, maxReceive, maxExtract, energy, tier);

    }

    public boolean hasCapability(Capability<?> capability) {
        return capability == Capabilities.ENERGY_CAPABILITY;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (hasCapability(cap)) {
            return LazyOptional.of(() -> this.energyContainer).cast();
        }
        return LazyOptional.empty();
    }

    public CraftleEnergyStorage getEnergyContainer() {
        return energyContainer;
    }

    public void setEnergyContainer(CraftleEnergyStorage energyContainer) {
        this.energyContainer = energyContainer;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();

        serializeNBT(compound);

        return compound;
    }

    public CompoundNBT serializeNBT(CompoundNBT compound) {

        this.energyContainer.serializeNBT(compound);

        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.energyContainer.deserializeNBT(nbt);
    }

}
