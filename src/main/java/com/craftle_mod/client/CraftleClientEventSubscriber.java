package com.craftle_mod.client;

import com.craftle_mod.client.gui.CraftleChestScreen;
import com.craftle_mod.client.gui.EnergyGui;
import com.craftle_mod.client.gui.machine.GeneratorGui;
import com.craftle_mod.client.gui.machine.ProducerGui;
import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.inventory.container.base.EnergyContainer;
import com.craftle_mod.common.inventory.container.machine.GeneratorContainer;
import com.craftle_mod.common.inventory.container.machine.ProducerContainer;
import com.craftle_mod.common.inventory.container.machine.WorkBenchContainer;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.registries.CraftleContainerTypes;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Craftle.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CraftleClientEventSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {

        ScreenManager
            .registerFactory(CraftleContainerTypes.TEST_CHEST.get(), CraftleChestScreen::new);

        ScreenManager.registerFactory(CraftleContainerTypes.CRUSHER_BASIC.get(),
            ProducerGui<ProducerContainer>::new);
        ScreenManager.registerFactory(CraftleContainerTypes.CRUSHER_TIER_1.get(),
            ProducerGui<ProducerContainer>::new);
        ScreenManager.registerFactory(CraftleContainerTypes.CRUSHER_TIER_2.get(),
            ProducerGui<ProducerContainer>::new);
        ScreenManager.registerFactory(CraftleContainerTypes.CRUSHER_TIER_3.get(),
            ProducerGui<ProducerContainer>::new);
        ScreenManager.registerFactory(CraftleContainerTypes.CRUSHER_TIER_4.get(),
            ProducerGui<ProducerContainer>::new);

        ScreenManager.registerFactory(CraftleContainerTypes.COAL_GENERATOR.get(),
            GeneratorGui<GeneratorContainer>::new);

        ScreenManager.registerFactory(CraftleContainerTypes.WORKBENCH.get(),
            EnergyGui<WorkBenchContainer>::new);

        ScreenManager.registerFactory(CraftleContainerTypes.ENERGY_TANK_BASIC.get(),
            EnergyGui<EnergyContainer>::new);
        ScreenManager.registerFactory(CraftleContainerTypes.ENERGY_TANK_TIER_1.get(),
            EnergyGui<EnergyContainer>::new);
        ScreenManager.registerFactory(CraftleContainerTypes.ENERGY_TANK_TIER_2.get(),
            EnergyGui<EnergyContainer>::new);
        ScreenManager.registerFactory(CraftleContainerTypes.ENERGY_TANK_TIER_3.get(),
            EnergyGui<EnergyContainer>::new);
        ScreenManager.registerFactory(CraftleContainerTypes.ENERGY_TANK_TIER_4.get(),
            EnergyGui<EnergyContainer>::new);

        // set transparent blocks
        RenderTypeLookup
            .setRenderLayer(CraftleBlocks.ENERGY_TANK_BASIC.get(), RenderType.getTranslucent());
        RenderTypeLookup
            .setRenderLayer(CraftleBlocks.ENERGY_TANK_TIER_1.get(), RenderType.getTranslucent());
        RenderTypeLookup
            .setRenderLayer(CraftleBlocks.ENERGY_TANK_TIER_2.get(), RenderType.getTranslucent());
        RenderTypeLookup
            .setRenderLayer(CraftleBlocks.ENERGY_TANK_TIER_3.get(), RenderType.getTranslucent());
        RenderTypeLookup
            .setRenderLayer(CraftleBlocks.ENERGY_TANK_TIER_4.get(), RenderType.getTranslucent());

    }

}
