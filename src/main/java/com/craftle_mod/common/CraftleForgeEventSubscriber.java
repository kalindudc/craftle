package com.craftle_mod.common;

import com.craftle_mod.common.registries.CraftleDimensions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Craftle.MODID, bus = Bus.FORGE)
public class CraftleForgeEventSubscriber {

    @SubscribeEvent
    public static void onRegisterDimensions(
            final RegisterDimensionsEvent event) {
        if (DimensionType.byName(Craftle.TEST_DIM_TYPE) == null) {
            DimensionManager.registerDimension(Craftle.TEST_DIM_TYPE,
                    CraftleDimensions.TEST_DIMENSION
                            .get(), null, true);
        }

        Craftle.LOGGER.info("CRAFTLE: Registered Dimensions.");
    }
}
