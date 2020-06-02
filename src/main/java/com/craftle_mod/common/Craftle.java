package com.craftle_mod.common;

import com.craftle_mod.common.registries.CraftleBiomes;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.registries.CraftleItems;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.world.gen.TestOreGen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Craftle.MODID)
@EventBusSubscriber(modid = Craftle.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Craftle {

    public static final String MODID       = "craftle";
    public static final String MOD_VERSION = "0.0.1.0";

    public static final Logger LOGGER = LogManager.getLogger(Craftle.MODID);

    private static Craftle instance;

    public Craftle() {

        final IEventBus craftleEventBus =
                FMLJavaModLoadingContext.get().getModEventBus();

        craftleEventBus.addListener(this::setup);
        craftleEventBus.addListener(this::clientRegistries);

        CraftleItems.ITEMS.register(craftleEventBus);
        CraftleBlocks.BLOCKS.register(craftleEventBus);
        CraftleTileEntityTypes.TILE_ENTITY_TYPES.register(craftleEventBus);
        CraftleBiomes.BIOMES.register(craftleEventBus);

        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static Craftle getInstance() {
        return instance;
    }

    private void setup(final FMLCommonSetupEvent event) {
        // TODO: preinit function
        LOGGER.debug(MODID + ": setup registered!");
    }

    private void clientRegistries(final FMLClientSetupEvent event) {
        // TODO: only run on the client side, ex models ...
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // TODO: server is starting
    }

    @SubscribeEvent
    public static void loadCompleteEvent(FMLLoadCompleteEvent event) {
        TestOreGen.generateOre();
    }
}
