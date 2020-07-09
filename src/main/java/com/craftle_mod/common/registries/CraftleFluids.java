package com.craftle_mod.common.registries;

import com.craftle_mod.api.constants.FluidConstants;
import com.craftle_mod.common.Craftle;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftleFluids {


    public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(
        ForgeRegistries.FLUIDS, Craftle.MODID);

    public static final RegistryObject<FlowingFluid> ENERGY_FLUID = FLUIDS.register("energy_fluid",
        () -> new ForgeFlowingFluid.Source(FluidConstants.ENERGY_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> ENERGY_FLOWING = FLUIDS
        .register("energy_flowing",
            () -> new ForgeFlowingFluid.Flowing(FluidConstants.ENERGY_FLUID_PROPERTIES));

}
