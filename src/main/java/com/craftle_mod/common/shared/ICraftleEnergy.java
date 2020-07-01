package com.craftle_mod.common.shared;

public interface ICraftleEnergy {

    float getCapacity();

    float getMaxCapacity();

    float getMaxInput();

    float getMaxOutput();

    float getInput();

    void setInput(float input);

    float getOutput();

    void setOutput(float output);

    /**
     * Use this much energy from this item.
     * <p>
     * Reduce capacity by amount
     *
     * @param amount to reduce from capacity
     */
    void useEnergy(float amount);
}
