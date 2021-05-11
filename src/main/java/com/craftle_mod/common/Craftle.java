package com.craftle_mod.common;

import com.craftle_mod.common.lib.Version;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Craftle.MODID)
@EventBusSubscriber(modid = Craftle.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Craftle {

    public static final String MODID = "craftle";
    //public static final PacketHandler packetHandler = new PacketHandler();

    public static final Logger LOGGER = LogManager.getLogger(Craftle.MODID);
    public static final ResourceLocation TEST_DIM_TYPE = new ResourceLocation(MODID,
        "test_dimension");

    private static Craftle instance;

    private final Version version;

    public Craftle() {

        final IEventBus craftleEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        craftleEventBus.addListener(this::setup);
        craftleEventBus.addListener(this::clientRegistries);

        version = new Version(
            ModLoadingContext.get().getActiveContainer().getModInfo().getVersion());

        instance = this;

    }

    public Version getVersion() {
        return version;
    }

    public static Craftle getInstance() {
        return instance;
    }

    private void setup(final FMLCommonSetupEvent event) {

        MinecraftForge.EVENT_BUS.register(this);

        // packet handler
        logInfo("Craftle loaded..");
    }

    private void clientRegistries(final FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    public static void logInfo(String string, Object... args) {
        Craftle.LOGGER.info(String.format(string, args));
    }
}
