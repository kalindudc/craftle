package com.craftle_mod.common.item;

import com.craftle_mod.common.item.base.CraftleItem;
import com.craftle_mod.common.shared.ICraftleEnergy;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class BatteryItem extends CraftleItem implements ICraftleEnergy {

    private final static float BASE_CAPACITY   = 100_000F;
    private final static float BASE_MAX_INPUT  = BASE_CAPACITY * 0.01F;
    private final static float BASE_MAX_OUTPUT = BASE_MAX_INPUT * 10.0F;

    private final float capacity;
    private final float maxCapacity;
    private final float maxInput;
    private final float maxOutput;
    private       float input;
    private       float output;


    public BatteryItem(String registryName, ItemGroup tab,
                       CraftleBaseTier tierConfig) {

        super(registryName + "_" + tierConfig.getTier(), tab);

        this.maxCapacity = BASE_CAPACITY * tierConfig.getMultiplier();
        this.capacity    = tierConfig.getTier().equals(CraftleBaseTier.UNLIMITED
                                                               .getTier()) ?
                           maxCapacity : 0.0F;
        this.maxInput    = BASE_MAX_INPUT * tierConfig.getMultiplier();
        this.maxOutput   = BASE_MAX_OUTPUT * tierConfig.getMultiplier();
        this.input       = maxInput;
        this.output      = maxOutput;
    }

    @Override
    public float getCapacity() {
        return capacity;
    }

    @Override
    public float getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public float getMaxInput() {
        return maxInput;
    }

    @Override
    public float getMaxOutput() {
        return maxOutput;
    }

    @Override
    public float getInput() {
        return input;
    }

    @Override
    public float getOutput() {
        return output;
    }

    @Override
    public void setInput(float input) {
        this.input = input;
    }

    @Override
    public void setOutput(float output) {
        this.output = output;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1.0F - ((double) getCapacity() / (double) getMaxCapacity());
    }
}
