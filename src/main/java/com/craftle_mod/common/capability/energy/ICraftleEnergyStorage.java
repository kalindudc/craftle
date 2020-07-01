package com.craftle_mod.common.capability.energy;

import com.craftle_mod.api.NBTConstants;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICraftleEnergyStorage extends INBTSerializable<CompoundNBT> {

    /**
     * Returns the energy stored in this container.
     *
     * @return the energy stored in this container. Energy stored will always be >= 0.
     */
    double getEnergy();

    /**
     * Returns the maximum capacity of this container.
     *
     * @return the maximum capacity of this container. Energy capacity will always be >= 0.
     */
    double getCapacity();

    /**
     * Returns the maximum injection rate of this container.
     *
     * @return the maximum injection rate of this container. Injection rate will always be >= 0.
     */
    double getMaxInjectRate();

    /**
     * Returns the maximum extraction rate of this container.
     *
     * @return the maximum extraction rate of this container. Extraction rate will always be >= 0.
     */
    double getMaxExtractRate();

    /**
     * Returns the total energy required to fill this container.
     *
     * @return the total energy required to fill this container. The energy requirement will always
     * be >= 0.
     */
    default double getEnergyToFill() {
        return getCapacity() - getEnergy();
    }

    /**
     * Check if this container is empty.
     *
     * @return <code>true</code> if energy stored > 0, <code>false</code> otherwise.
     */
    default boolean isEmpty() {
        return getEnergy() <= 0;
    }

    /**
     * Check if this container is filled.
     *
     * @return <code>true</code> if energy stored < max capacity, <code>false</code> otherwise.
     */
    default boolean isFilled() {
        return getEnergy() >= getCapacity();
    }

    /**
     * Normalize the energy levels of this container.
     */
    default void normalize() {
        if (getEnergy() < 0) {
            resetStorage();
        }
    }

    /**
     * Change the amount of stored energy in this container.
     *
     * @param energy the new stored energy value of this container.
     */
    void setEnergy(double energy);

    /**
     * Reset the values of this energy container and empty this energy storage.
     */
    default void resetStorage() {
        setEnergy(0);
    }

    /**
     * Extract energy from this container
     * <p>
     * The returned value will be less than or equal to {@code energy}.
     * </p>
     *
     * @param energy the amount to extract. Can be greater than the current stored amount.
     * @param simulate if <code>true</code>, energy extraction will only be simulated, otherwise
     * energy will be extracted from this container
     * @return the amount extracted from this container. The returned value may be less than the
     * amount requested if {@code energy} is greater than the stored amount.
     */
    double extractEnergy(double energy, boolean simulate);

    /**
     * Inject energy into this container
     * <p>
     * The returned value will be less than or equal to {@code energy}.
     * </p>
     *
     * @param energy the amount to inject.
     * @param simulate if <code>true</code>, energy injection will only be simulated, otherwise
     * energy will be injected into this container
     * @return the amount injected into this container. The returned value may be less than the
     * amount requested if {@code energy} is greater than the amount required to fill this
     * container.
     */
    double injectEnergy(double energy, boolean simulate);

    /**
     * Fill this energy container.
     */
    default void fillEnergyStorage() {
        setEnergy(getCapacity());
    }

    @Override
    default CompoundNBT serializeNBT() {
        CompoundNBT comp = new CompoundNBT();

        comp.putDouble(NBTConstants.ENERGY_STORED, getEnergy());
        comp.putDouble(NBTConstants.ENERGY_CAPACITY, getCapacity());
        comp.putDouble(NBTConstants.ENERGY_MAX_EXTRACT, getMaxExtractRate());
        comp.putDouble(NBTConstants.ENERGY_MAX_INJECT, getMaxInjectRate());

        return comp;
    }

}
