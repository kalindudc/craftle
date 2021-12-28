package com.craftlemod.common.registry;

import com.craftlemod.common.CraftleMod;
import com.craftlemod.common.screen.FactoryScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class CraftleScreenHandlers {

    public static ScreenHandlerType<FactoryScreenHandler> FACTORY_SCREEN_HANDLER;

    public static void init() {
        FACTORY_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(new Identifier(CraftleMod.MODID, "factory_screen_handler"), FactoryScreenHandler::new);
    }
}
