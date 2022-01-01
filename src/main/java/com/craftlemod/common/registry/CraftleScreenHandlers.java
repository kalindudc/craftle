package com.craftlemod.common.registry;

import com.craftlemod.common.CraftleMod;
import com.craftlemod.common.screen.FactoryControllerScreenHandler;
import com.craftlemod.common.screen.FactoryIOScreenHandler;
import com.craftlemod.common.screen.FactoryScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class CraftleScreenHandlers {

    public static ScreenHandlerType<FactoryScreenHandler> FACTORY_CONTROLLER_SCREEN_HANDLER;
    public static ScreenHandlerType<FactoryScreenHandler> FACTORY_IO_SCREEN_HANDLER;

    public static void init() {
        FACTORY_CONTROLLER_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(new Identifier(CraftleMod.MODID, "factory_controller_screen_handler"),
            FactoryControllerScreenHandler::new);
        FACTORY_IO_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(new Identifier(CraftleMod.MODID, "factory_io_screen_handler"), FactoryIOScreenHandler::new);
    }
}
