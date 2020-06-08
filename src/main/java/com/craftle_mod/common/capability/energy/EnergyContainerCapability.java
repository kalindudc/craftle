package com.craftle_mod.common.capability.energy;

import com.craftle_mod.api.NBTConstants;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyContainerCapability extends EnergyStorage {


    public EnergyContainerCapability(int capacity) {
        super(capacity, capacity, capacity, 0);

    }

    public EnergyContainerCapability(int capacity, int maxTransfer) {
        super(capacity, maxTransfer, maxTransfer, 0);

    }

    public EnergyContainerCapability(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract, 0);

    }

    public EnergyContainerCapability(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return super.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return super.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return super.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return super.getMaxEnergyStored();
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

        int energy     = compound.getInt(NBTConstants.ENERGY_STORED);
        int capacity   = compound.getInt(NBTConstants.ENERGY_CAPACITY);
        int maxExtract = compound.getInt(NBTConstants.ENERGY_MAX_EXTRACT);
        int maxReceive = compound.getInt(NBTConstants.ENERGY_MAX_RECEIVE);

        this.energy     = energy > 0 ? energy : this.energy;
        this.capacity   = capacity > 0 ? capacity : this.capacity;
        this.maxExtract = maxExtract > 0 ? maxExtract : this.maxExtract;
        this.maxReceive = maxReceive > 0 ? maxReceive : this.maxReceive;
    }

    public void writeToNBT(CompoundNBT compound) {
        compound.putInt(NBTConstants.ENERGY_STORED, this.energy);
        compound.putInt(NBTConstants.ENERGY_CAPACITY, this.capacity);
        compound.putInt(NBTConstants.ENERGY_MAX_EXTRACT, this.maxExtract);
        compound.putInt(NBTConstants.ENERGY_MAX_RECEIVE, this.maxReceive);
    }

    public int getMaxRecieve() {
        return maxReceive;
    }

    public int getMaxExtract() {
        return maxExtract;
    }
}
