package com.craftle_mod.client;

import com.craftle_mod.client.gui.CraftleChestScreen;
import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.registries.CraftleContainerTypes;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Craftle.MODID,
                        bus = Mod.EventBusSubscriber.Bus.MOD,
                        value = Dist.CLIENT)
public class CraftleClientEventSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(CraftleContainerTypes.TEST_CHEST.get(),
                                      CraftleChestScreen::new);
    }
}
