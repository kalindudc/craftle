package com.craftle_mod.common.capability.energy;

import com.craftle_mod.api.NBTConstants;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyContainerCapability extends EnergyStorage {

    public static IEnergyStorage EMPTY_IE = new EnergyContainerCapability(0, null);

    CraftleBaseTier tier;

    public EnergyContainerCapability(int capacity, CraftleBaseTier tier) {
        super(capacity, capacity, capacity, 0);
        this.tier = tier;
    }

    public EnergyContainerCapability(int capacity, int maxTransfer, CraftleBaseTier tier) {
        super(capacity, maxTransfer, maxTransfer, 0);
        this.tier = tier;
    }

    public EnergyContainerCapability(int capacity, int maxReceive, int maxExtract,
        CraftleBaseTier tier) {
        super(capacity, maxReceive, maxExtract, 0);
        this.tier = tier;
    }

    public EnergyContainerCapability(int capacity, int maxReceive, int maxExtract, int energy,
        CraftleBaseTier tier) {
        super(capacity, maxReceive, maxExtract, energy);
        this.tier = tier;
    }

    public int receiveEnergy(int maxReceive) {
        return super.receiveEnergy(maxReceive, tier.equals(CraftleBaseTier.UNLIMITED));
    }

    public int extractEnergy(int maxExtract) {
        return super.extractEnergy(maxExtract, tier.equals(CraftleBaseTier.UNLIMITED));
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

        int energy = compound.getInt(NBTConstants.ENERGY_STORED);
        int capacity = compound.getInt(NBTConstants.ENERGY_CAPACITY);
        int maxExtract = compound.getInt(NBTConstants.ENERGY_MAX_EXTRACT);
        int maxReceive = compound.getInt(NBTConstants.ENERGY_MAX_RECEIVE);

        this.energy = energy > 0 ? energy : this.energy;
        this.capacity = capacity > 0 ? capacity : this.capacity;
        this.maxExtract = maxExtract > 0 ? maxExtract : this.maxExtract;
        this.maxReceive = maxReceive > 0 ? maxReceive : this.maxReceive;
    }

    public void writeToNBT(CompoundNBT compound) {
        compound.putInt(NBTConstants.ENERGY_STORED, this.energy);
        compound.putInt(NBTConstants.ENERGY_CAPACITY, this.capacity);
        compound.putInt(NBTConstants.ENERGY_MAX_EXTRACT, this.maxExtract);
        compound.putInt(NBTConstants.ENERGY_MAX_RECEIVE, this.maxReceive);
    }

    public int getMaxReceive() {
        return maxReceive;
    }

    public int getMaxExtract() {
        return maxExtract;
    }
}
