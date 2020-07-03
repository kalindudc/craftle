package com.craftle_mod.common;

import com.craftle_mod.common.capability.Capabilities;
import com.craftle_mod.common.registries.CraftleBiomes;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.registries.CraftleContainerTypes;
import com.craftle_mod.common.registries.CraftleDimensions;
import com.craftle_mod.common.registries.CraftleItems;
import com.craftle_mod.common.registries.CraftleRecipeSerializers;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.world.gen.OreGenHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
    public static final String MOD_VERSION = "0.0.1.0";

    public static final Logger LOGGER = LogManager.getLogger(Craftle.MODID);
    public static final ResourceLocation TEST_DIM_TYPE = new ResourceLocation(MODID,
        "test_dimension");

    private static Craftle instance;

    public Craftle() {

        final IEventBus craftleEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        craftleEventBus.addListener(this::setup);
        craftleEventBus.addListener(this::clientRegistries);

        CraftleItems.ITEMS.register(craftleEventBus);
        CraftleBlocks.BLOCKS.register(craftleEventBus);
        CraftleTileEntityTypes.TILE_ENTITY_TYPES.register(craftleEventBus);
        CraftleContainerTypes.CONTAINER_TYPES.register(craftleEventBus);
        CraftleRecipeSerializers.SERIALIZERS.register(craftleEventBus);
        CraftleBiomes.BIOMES.register(craftleEventBus);
        CraftleDimensions.DIMENSIONS.register(craftleEventBus);

        instance = this;

    }

    private static Craftle getInstance() {
        return instance;
    }

    private void setup(final FMLCommonSetupEvent event) {

        Capabilities.registerCapabilities();

        OreGenHandler.generateOre();

        MinecraftForge.EVENT_BUS.register(this);

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
