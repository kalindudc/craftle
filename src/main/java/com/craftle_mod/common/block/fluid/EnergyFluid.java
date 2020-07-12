package com.craftle_mod.common.block.fluid;

import com.craftle_mod.common.block.base.CraftleFluidBlock;
import com.craftle_mod.common.registries.CraftleFluids;
import com.craftle_mod.common.registries.CraftleItems;
import java.util.function.Supplier;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class EnergyFluid extends CraftleFluidBlock {

    public EnergyFluid(Supplier<? extends FlowingFluid> supplier,
        ForgeFlowingFluid.Properties properties) {
        super(supplier, properties);
    }

    public Fluid getFlowingFluid() {
        return CraftleFluids.ENERGY_FLOWING.get();
    }

    public Fluid getStillFluid() {
        return CraftleFluids.ENERGY_FLUID.get();
    }

    public Item getFilledBucket() {
        return CraftleItems.ENERGY_BUCKET.get();
    }

}
