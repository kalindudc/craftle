package com.craftle_mod.common.shared;

public interface ICraftleEnergy {

    public float getCapacity();

    public float getMaxCapacity();

    public float getMaxInput();

    public float getMaxOutput();

    public float getInput();

    public float getOutput();

    /**
     * Use this much energy from this item.
     * <p>
     * Reduce capacity by amount
     *
     * @param amount to reduce from capacity
     */
    public void useEnergy(float amount);

    public void setInput(float input);

    public void setOutput(float output);
}
