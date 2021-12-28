package com.craftlemod.client;

import com.craftlemod.common.registry.CraftleBlocks;
import com.craftlemod.common.registry.CraftleScreenHandlers;
import com.craftlemod.common.screen.FactoryScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class CraftleModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock((Block) CraftleBlocks.FACTORY_GLASS_BLOCK, RenderLayer.getCutout());

        // screens
        ScreenRegistry.register(CraftleScreenHandlers.FACTORY_SCREEN_HANDLER, FactoryScreen::new);
    }
}
