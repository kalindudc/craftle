package com.craftle_mod.common.capability.energy;

import com.craftle_mod.api.NBTConstants;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.nbt.CompoundNBT;

public class CraftleEnergyStorage implements ICraftleEnergyStorage {

    public static ICraftleEnergyStorage EMPTY_IE = new CraftleEnergyStorage(0, null);

    private final CraftleBaseTier tier;

    private double energyStored;
    private double capacity;
    private double maxExtract;
    private double maxInject;

    public CraftleEnergyStorage(int capacity, CraftleBaseTier tier) {
        this(capacity, capacity, capacity, 0, tier);
    }

    public CraftleEnergyStorage(int capacity, int maxTransfer, CraftleBaseTier tier) {
        this(capacity, maxTransfer, maxTransfer, 0, tier);
    }

    public CraftleEnergyStorage(int capacity, int maxReceive, int maxExtract,
        CraftleBaseTier tier) {
        this(capacity, maxReceive, maxExtract, 0, tier);
    }

    public CraftleEnergyStorage(int capacity, int maxInject, int maxExtract, int energyStored,
        CraftleBaseTier tier) {

        this.energyStored = tier.equals(CraftleBaseTier.UNLIMITED) ? capacity : energyStored;
        this.capacity = capacity;
        this.maxExtract = maxExtract;
        this.maxInject = maxInject;
        this.tier = tier;
    }


    @Override
    public double getEnergy() {
        return energyStored;
    }

    @Override
    public double getCapacity() {
        return capacity;
    }

    @Override
    public double getMaxInjectRate() {
        return maxInject;
    }

    @Override
    public double getMaxExtractRate() {
        return maxExtract;
    }

    @Override
    public void setEnergy(double energy) {
        this.energyStored = energy;
    }

    @Override
    public double extractEnergy(double energy, boolean simulate) {

        if (isEmpty()) {
            return 0;
        }

        double extracted = this.energyStored - energy < 0 ? this.energyStored : energy;

        if (!simulate) {
            this.energyStored -= extracted;
        }

        return extracted;
    }

    @Override
    public double injectEnergy(double energy, boolean simulate) {

        if (isFilled()) {
            return 0;
        }

        double injected = this.energyStored + energy > capacity ? this.getEnergyToFill() : energy;

        if (!simulate) {
            this.energyStored += injected;
        }

        return injected;
    }

    public double injectEnergy(double energy) {
        return injectEnergy(energy, tier.equals(CraftleBaseTier.UNLIMITED));
    }

    public double extractEnergy(double energy) {
        return extractEnergy(energy, tier.equals(CraftleBaseTier.UNLIMITED));
    }

    @Override
    public void deserializeNBT(CompoundNBT compound) {

        this.energyStored = compound.getDouble(NBTConstants.ENERGY_STORED);
        this.capacity = compound.getDouble(NBTConstants.ENERGY_CAPACITY);
        this.maxInject = compound.getDouble(NBTConstants.ENERGY_MAX_INJECT);
        this.maxExtract = compound.getDouble(NBTConstants.ENERGY_MAX_EXTRACT);
    }
}
