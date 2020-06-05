package com.craftle_mod.common.shared;

public interface ICraftleEnergy {

    public float getCapacity();

    public float getMaxCapacity();

    public float getMaxInput();

    public float getMaxOutput();

    public float getInput();

    public float getOutput();

    public void setInput(float input);

    public void setOutput(float output);
}
