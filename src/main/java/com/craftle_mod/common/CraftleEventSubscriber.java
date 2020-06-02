package com.craftle_mod.common;

import com.craftle_mod.common.registries.CraftleBiomes;
import com.craftle_mod.common.registries.CraftleBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = Craftle.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CraftleEventSubscriber {

    @SubscribeEvent
    public static void onRegisterItems(
            final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        CraftleBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                            .forEach(block -> {
                                final Item.Properties properties =
                                        new Item.Properties()
                                                .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP);
                                final BlockItem blockItem =
                                        new BlockItem(block, properties);
                                blockItem.setRegistryName(
                                        block.getRegistryName());

                                registry.register(blockItem);
                            });

        Craftle.LOGGER.debug("CRAFTLE: Registered block items.");

    }

    @SubscribeEvent
    public static void onRegisterBiomes(
            final RegistryEvent.Register<Biome> event) {
        CraftleBiomes.registerBiomes();
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry,
                                                             final String name) {
        return setup(entry, new ResourceLocation(Craftle.MODID, name));
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry,
                                                             final ResourceLocation registryName) {
        entry.setRegistryName(registryName);
        return entry;
    }


}
