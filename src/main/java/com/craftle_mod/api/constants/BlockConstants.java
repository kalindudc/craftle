package com.craftle_mod.api.constants;

import com.craftle_mod.common.Craftle;
import net.minecraft.util.ResourceLocation;

public class BlockConstants {

    // MACHINE BASE CAPACITY
    public final static int MACHINE_BASE_CAPACITY = 240;

    public static final ResourceLocation ENERGY_STILL = new ResourceLocation(Craftle.MODID,
        "block/fluid/energy_still");
    public static final ResourceLocation ENERGY_FLOWING = new ResourceLocation(Craftle.MODID,
        "block/fluid/energy_flow");
    public static final ResourceLocation ENERGY_FLUID_OVERLAY = new ResourceLocation(Craftle.MODID,
        "block/fluid/energy_fluid_overlay");

}
