package com.craftle_mod.common.capability.energy;

import com.craftle_mod.api.NBTConstants;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.nbt.CompoundNBT;

public class EnergyContainerCapability extends CraftleEnergyStorage {

    CraftleBaseTier tier;

    public EnergyContainerCapability(long capacity, CraftleBaseTier tier) {
        super(capacity, capacity, capacity, 0);
        this.tier = tier;
    }

    public EnergyContainerCapability(long capacity, long maxTransfer, CraftleBaseTier tier) {
        super(capacity, maxTransfer, maxTransfer, 0);
        this.tier = tier;
    }

    public EnergyContainerCapability(long capacity, long maxReceive, long maxExtract,
                                     CraftleBaseTier tier) {
        super(capacity, maxReceive, maxExtract, 0);
        this.tier = tier;
    }

    public EnergyContainerCapability(long capacity, long maxReceive, long maxExtract, long energy,
                                     CraftleBaseTier tier) {
        super(capacity, maxReceive, maxExtract, energy);
        this.tier = tier;
    }

    public long receiveEnergy(long maxReceive) {
        return super.injectEnergy(maxReceive, tier.equals(CraftleBaseTier.UNLIMITED));
    }

    public long extractEnergy(long maxExtract) {
        return super.drainEnergy(maxExtract, tier.equals(CraftleBaseTier.UNLIMITED));
    }

    @Override
    public long getEnergy() {
        return super.getEnergy();
    }

    @Override
    public long getCapacity() {
        return super.getCapacity();
    }

    @Override
    public boolean canExtract() {
        return super.canExtract();
    }

    @Override
    public boolean canReceive() {
        return super.canReceive();
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void readFromNBT(CompoundNBT compound) {

        long energy     = compound.getLong(NBTConstants.ENERGY_STORED);
        long capacity   = compound.getLong(NBTConstants.ENERGY_CAPACITY);
        long maxExtract = compound.getLong(NBTConstants.ENERGY_MAX_EXTRACT);
        long maxReceive = compound.getLong(NBTConstants.ENERGY_MAX_RECEIVE);

        this.energy     = energy > 0 ? energy : this.energy;
        this.capacity   = capacity > 0 ? capacity : this.capacity;
        this.maxExtract = maxExtract > 0 ? maxExtract : this.maxExtract;
        this.maxReceive = maxReceive > 0 ? maxReceive : this.maxReceive;
    }

    public void writeToNBT(CompoundNBT compound) {
        compound.putLong(NBTConstants.ENERGY_STORED, this.energy);
        compound.putLong(NBTConstants.ENERGY_CAPACITY, this.capacity);
        compound.putLong(NBTConstants.ENERGY_MAX_EXTRACT, this.maxExtract);
        compound.putLong(NBTConstants.ENERGY_MAX_RECEIVE, this.maxReceive);
    }

    public long getMaxRecieve() {
        return maxReceive;
    }

    public long getMaxExtract() {
        return maxExtract;
    }
}
