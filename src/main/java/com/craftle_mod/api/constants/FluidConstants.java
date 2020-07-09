package com.craftle_mod.api.constants;

import com.craftle_mod.api.ColorData;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.registries.CraftleFluids;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fluids.ForgeFlowingFluid.Properties;

public class FluidConstants {

    public static final ForgeFlowingFluid.Properties ENERGY_FLUID_PROPERTIES = new Properties(
        CraftleFluids.ENERGY_FLUID, CraftleFluids.ENERGY_FLOWING,
        FluidAttributes.builder(BlockConstants.ENERGY_STILL, BlockConstants.ENERGY_FLOWING)
            .color(ColorData.ENERGY.getColorCode()).overlay(BlockConstants.ENERGY_FLUID_OVERLAY))
        .block(() -> (FlowingFluidBlock) CraftleBlocks.ENERGY.get());

}
