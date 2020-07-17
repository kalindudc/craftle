package com.craftle_mod.datagen;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.datagen.blockstate.CraftleBlockStateProvider;
import com.craftle_mod.datagen.lang.CraftleLangProvider;
import com.craftle_mod.datagen.loot.CraftleLootProvider;
import com.craftle_mod.datagen.model.CraftleBlockModelProvider;
import com.craftle_mod.datagen.model.CraftleItemModelProvider;
import com.craftle_mod.datagen.recipe.CraftleRecipeProvider;
import com.craftle_mod.datagen.tag.CraftleTagProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = Craftle.MODID, bus = Bus.MOD)
public class CraftleDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        // Add blockstates and other when and if possible
        // https://github.com/MinecraftForge/MinecraftForge/blob/1.14.x/src/test/java/net/minecraftforge/debug/DataGeneratorTest.java

        if (event.includeClient()) {
            generator.addProvider(new CraftleLangProvider(generator));
            generator.addProvider(new CraftleItemModelProvider(generator, existingFileHelper));
            generator.addProvider(new CraftleBlockModelProvider(generator, existingFileHelper));
            generator.addProvider(new CraftleBlockStateProvider(generator, existingFileHelper));
        }
        if (event.includeServer()) {
            generator.addProvider(new CraftleRecipeProvider(generator));
            generator.addProvider(new CraftleLootProvider(generator));
            generator.addProvider(new CraftleTagProvider(generator));
        }
    }
}
