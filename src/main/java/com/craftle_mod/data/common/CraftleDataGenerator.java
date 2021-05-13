package com.craftle_mod.data.common;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.data.client.CraftleBlockStateProvider;
import com.craftle_mod.data.client.CraftleItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = Craftle.MODID, bus = Bus.MOD)
public final class CraftleDataGenerator {

    /**
     * UNUSED
     */
    private CraftleDataGenerator() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        DataGenerator dataGen = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        if (event.includeClient()) {

            dataGen.addProvider(new CraftleBlockStateProvider(dataGen, fileHelper));
            CraftleItemModelProvider provider = new CraftleItemModelProvider(dataGen, fileHelper);
            dataGen.addProvider(provider);
        }

        if (event.includeServer()) {

        }

    }
}
