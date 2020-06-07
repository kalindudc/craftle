package com.craftle_mod.common.capability.energy;

import com.craftle_mod.common.Craftle;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyContainerCapability extends EnergyStorage {

    public EnergyContainerCapability(int capacity) {
        super(capacity, capacity, capacity, 0);
    }

    public EnergyContainerCapability(int capacity, int maxTransfer) {
        super(capacity, maxTransfer, maxTransfer, 0);
    }

    public EnergyContainerCapability(int capacity, int maxReceive,
                                     int maxExtract) {
        super(capacity, maxReceive, maxExtract, 0);
    }

    public EnergyContainerCapability(int capacity, int maxReceive,
                                     int maxExtract, int energy) {
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

    public boolean incrementEnergy(int increment) {
        this.energy += increment;
        if (this.energy > this.capacity) {
            this.energy = this.capacity;
            return false;
        }
        return true;
    }

    public void readFromNBT(CompoundNBT compound) {

        int energy     = compound.getInt("Energy");
        int capacity   = compound.getInt("Capacity");
        int maxExtract = compound.getInt("MaxExtract");
        int maxReceive = compound.getInt("MaxReceive");

        this.energy     = energy > 0 ? energy : this.energy;
        this.capacity   = capacity > 0 ? capacity : this.capacity;
        this.maxExtract = maxExtract > 0 ? maxExtract : this.maxExtract;
        this.maxReceive = maxReceive > 0 ? maxReceive : this.maxReceive;
        
        Craftle.LOGGER.info(String.format("CRAFTLE: Reading energy NBT."));
        Craftle.LOGGER.info(String.format("CRAFTLE: Energy: %d.", this.energy));

    }

    public void writeToNBT(CompoundNBT compound) {
        compound.putInt("Energy", this.energy);
        compound.putInt("Capacity", this.capacity);
        compound.putInt("MaxExtract", this.maxExtract);
        compound.putInt("MaxReceive", this.maxReceive);
        Craftle.LOGGER.info(String.format("CRAFTLE: writing energy NBT %d.",
                                          compound.getInt("Energy")));
    }
}
