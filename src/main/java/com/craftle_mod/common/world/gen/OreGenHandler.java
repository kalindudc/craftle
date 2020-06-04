package com.craftle_mod.common.world.gen;

import com.craftle_mod.common.config.OreGenConfig;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGenHandler {

    public static void generateOre() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            addFeature(biome, OreGenConfig.COPPER);
            addFeature(biome, OreGenConfig.RUBY);
            addFeature(biome, OreGenConfig.SAPPHIRE);
            addFeature(biome, OreGenConfig.TIN);
            addFeature(biome, OreGenConfig.URANIUM);
        }
    }

    private static void addFeature(Biome biome, OreGenConfig config) {

        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                         Feature.ORE.withConfiguration(new OreFeatureConfig(
                                 OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                 config.getBlock().getDefaultState(),
                                 config.getMaxVeinSize())).withPlacement(
                                 Placement.COUNT_RANGE.configure(
                                         new CountRangeConfig(
                                                 config.getConfig().getCount(),
                                                 config.getConfig()
                                                       .getBottomOffset(),
                                                 config.getConfig()
                                                       .getTopOffset(),
                                                 config.getConfig()
                                                       .getMaximum()))));
    }
}
