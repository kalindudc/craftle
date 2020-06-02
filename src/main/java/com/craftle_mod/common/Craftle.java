package com.craftle_mod.common;

import com.craftle_mod.common.world.gen.TestOreGen;
import net.minecraftforge.common.MinecraftForge;
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
@EventBusSubscriber(modid = Craftle.MODID,
                    bus = EventBusSubscriber.Bus.MOD)
public class Craftle {

    public static final String MODID       = "craftle";
    public static final String MOD_VERSION = "0.0.1.0";

    public static final Logger LOGGER = LogManager.getLogger(Craftle.MODID);

    private static Craftle instance;

    public Craftle() {

        instance = this;

        FMLJavaModLoadingContext.get().getModEventBus()
                                .addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus()
                                .addListener(this::clientRegistries);

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
