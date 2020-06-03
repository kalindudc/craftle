package com.craftle_mod.common.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.TwoFeatureChoiceConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.Placement;

public class TestBiome extends Biome {

    public TestBiome(Builder biomeBuilder) {
        super(biomeBuilder);
        // DefaultBiomeFeature
        this.addSpawn(EntityClassification.CREATURE,
                      new SpawnListEntry(EntityType.GIANT, 20, 2, 12));
        this.addCarver(GenerationStage.Carving.AIR,
                       Biome.createCarver(WorldCarver.CAVE,
                                          new ProbabilityConfig(0.14285715f)));
        this.addCarver(GenerationStage.Carving.AIR,
                       Biome.createCarver(WorldCarver.HELL_CAVE,
                                          new ProbabilityConfig(0.02f)));
        this.addCarver(GenerationStage.Carving.AIR,
                       Biome.createCarver(WorldCarver.CANYON,
                                          new ProbabilityConfig(0.14285926f)));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION,
                        Feature.FOSSIL.withConfiguration(
                                IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(
                                Placement.CHANCE_PASSTHROUGH
                                        .configure(new ChanceConfig(128))));
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
                        Feature.RANDOM_BOOLEAN_SELECTOR.withConfiguration(
                                new TwoFeatureChoiceConfig(
                                        Feature.HUGE_RED_MUSHROOM
                                                .withConfiguration(
                                                        DefaultBiomeFeatures.BIG_RED_MUSHROOM),
                                        Feature.HUGE_BROWN_MUSHROOM
                                                .withConfiguration(
                                                        DefaultBiomeFeatures.BIG_BROWN_MUSHROOM)))
                                                       .withPlacement(
                                                               Placement.COUNT_HEIGHTMAP
                                                                       .configure(
                                                                               new FrequencyConfig(
                                                                                       1))));
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.withConfiguration(
                                DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG)
                                            .withPlacement(
                                                    Placement.COUNT_CHANCE_HEIGHTMAP
                                                            .configure(
                                                                    new HeightWithChanceConfig(
                                                                            1,
                                                                            0.25F))));
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
                        Feature.RANDOM_PATCH.withConfiguration(
                                DefaultBiomeFeatures.RED_MUSHROOM_CONFIG)
                                            .withPlacement(
                                                    Placement.COUNT_CHANCE_HEIGHTMAP_DOUBLE
                                                            .configure(
                                                                    new HeightWithChanceConfig(
                                                                            1,
                                                                            0.125F))));
        DefaultBiomeFeatures.addBirchTrees(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addExtraReedsAndPumpkins(this);
        DefaultBiomeFeatures.addGiantTreeTaigaTrees(this);
    }
}
