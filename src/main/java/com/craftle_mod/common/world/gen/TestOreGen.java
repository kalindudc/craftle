package com.craftle_mod.common.world.gen;

import com.craftle_mod.common.init.CraftleBlocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class TestOreGen {

    public static void generateOre() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            ConfiguredPlacement customConfig =
                    Placement.COUNT_RANGE
                            .configure(new CountRangeConfig(20, 5, 5, 50));
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                             Feature.ORE.withConfiguration(new OreFeatureConfig(
                                     OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                     CraftleBlocks.TEST_ORE.getDefaultState(),
                                     10)).withPlacement(customConfig));
        }
    }
}
