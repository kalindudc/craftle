package com.craftle_mod.common.shared;

public interface ICraftleEnergy {

    float getCapacity();

    float getMaxCapacity();

    float getMaxInput();

    float getMaxOutput();

    float getInput();

    float getOutput();

    /**
     * Use this much energy from this item.
     * <p>
     * Reduce capacity by amount
     *
     * @param amount to reduce from capacity
     */
    void useEnergy(float amount);

    void setInput(float input);

    void setOutput(float output);
}
