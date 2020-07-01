package com.craftle_mod.client;

import com.craftle_mod.client.gui.CraftleChestScreen;
import com.craftle_mod.client.gui.machine.CoalGeneratorScreen;
import com.craftle_mod.client.gui.machine.WorkBenchScreen;
import com.craftle_mod.client.gui.machine.crusher.CrusherScreenFactory;
import com.craftle_mod.client.gui.storage.EnergyMatrixScreen;
import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.registries.CraftleContainerTypes;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Craftle.MODID, bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT)
public class CraftleClientEventSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {

        ScreenManager
                .registerFactory(CraftleContainerTypes.TEST_CHEST.get(), CraftleChestScreen::new);

        ScreenManager.registerFactory(CraftleContainerTypes.CRUSHER_BASIC.get(),
                CrusherScreenFactory.build(CraftleBaseTier.BASIC));
        ScreenManager.registerFactory(CraftleContainerTypes.CRUSHER_TIER_1.get(),
                CrusherScreenFactory.build(CraftleBaseTier.TIER_1));
        ScreenManager.registerFactory(CraftleContainerTypes.CRUSHER_TIER_2.get(),
                CrusherScreenFactory.build(CraftleBaseTier.TIER_2));
        ScreenManager.registerFactory(CraftleContainerTypes.CRUSHER_TIER_3.get(),
                CrusherScreenFactory.build(CraftleBaseTier.TIER_3));
        ScreenManager.registerFactory(CraftleContainerTypes.CRUSHER_TIER_4.get(),
                CrusherScreenFactory.build(CraftleBaseTier.TIER_4));

        ScreenManager.registerFactory(CraftleContainerTypes.COAL_GENERATOR.get(),
                CoalGeneratorScreen::new);

        ScreenManager.registerFactory(CraftleContainerTypes.WORKBENCH.get(), WorkBenchScreen::new);

        ScreenManager.registerFactory(CraftleContainerTypes.ENERGY_MATRIX_BASIC.get(),
                EnergyMatrixScreen::new);
        ScreenManager.registerFactory(CraftleContainerTypes.ENERGY_MATRIX_TIER_1.get(),
                EnergyMatrixScreen::new);
        ScreenManager.registerFactory(CraftleContainerTypes.ENERGY_MATRIX_TIER_2.get(),
                EnergyMatrixScreen::new);
        ScreenManager.registerFactory(CraftleContainerTypes.ENERGY_MATRIX_TIER_3.get(),
                EnergyMatrixScreen::new);
        ScreenManager.registerFactory(CraftleContainerTypes.ENERGY_MATRIX_TIER_4.get(),
                EnergyMatrixScreen::new);

        // set transparent blocks
        //        RenderTypeLookup.setRenderLayer(CraftleBlocks.ENERGY_MATRIX_BASIC.get(),
        //                                        RenderType.getTranslucent());
        //        RenderTypeLookup.setRenderLayer(CraftleBlocks.ENERGY_MATRIX_TIER_1.get(),
        //                                        RenderType.getTranslucent());
        //        RenderTypeLookup.setRenderLayer(CraftleBlocks.ENERGY_MATRIX_TIER_2.get(),
        //                                        RenderType.getTranslucent());
        //        RenderTypeLookup.setRenderLayer(CraftleBlocks.ENERGY_MATRIX_TIER_3.get(),
        //                                        RenderType.getTranslucent());
        //        RenderTypeLookup.setRenderLayer(CraftleBlocks.ENERGY_MATRIX_TIER_4.get(),
        //                                        RenderType.getTranslucent());

    }

}
