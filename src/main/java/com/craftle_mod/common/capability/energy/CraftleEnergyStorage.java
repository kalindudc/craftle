package com.craftle_mod.common.capability.energy;

import net.minecraftforge.energy.IEnergyStorage;

public abstract class CraftleEnergyStorage implements IEnergyStorage {

    protected long energy;
    protected long capacity;
    protected long maxReceive;
    protected long maxExtract;

    public CraftleEnergyStorage(long capacity) {
        this(capacity, capacity, capacity, 0);
    }

    public CraftleEnergyStorage(long capacity, long maxTransfer) {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public CraftleEnergyStorage(long capacity, long maxReceive, long maxExtract) {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public CraftleEnergyStorage(long capacity, long maxReceive, long maxExtract, long energy) {
        this.capacity   = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.energy     = Math.max(0, Math.min(capacity, energy));
    }

    public long injectEnergy(long maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        long energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            energy += energyReceived;
        return energyReceived;
    }

    public long drainEnergy(long maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

        long energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            energy -= energyExtracted;
        return energyExtracted;
    }


    public long getEnergy() {
        return energy;
    }

    public long getCapacity() {
        return capacity;
    }

    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    /**
     * Unused: using craftle equivalent getEnergy()
     *
     * @return
     */
    @Deprecated
    public int getEnergyStored() {
        return -1;
    }

    /**
     * Unused: using craftle equivalent getCapacity()
     *
     * @return
     */
    @Deprecated
    public int getMaxEnergyStored() {
        return -1;
    }

    /**
     * Unused: using craftle equivalent injectEnergy()
     *
     * @return
     */
    @Override
    @Deprecated
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return -1;
    }

    /**
     * Unused: using craftle equivalent drainEnergy()
     *
     * @return
     */
    @Override
    @Deprecated
    public int extractEnergy(int maxExtract, boolean simulate) {
        return -1;
    }
}
