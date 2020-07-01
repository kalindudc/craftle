package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.biome.TestBiome;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftleBiomes {

    public static final DeferredRegister<Biome> BIOMES =
            new DeferredRegister<>(ForgeRegistries.BIOMES, Craftle.MODID);

    public static final RegistryObject<Biome> TEST_BIOME =
            BIOMES.register("test_biome", () -> new TestBiome(
                    new Biome.Builder().precipitation(Biome.RainType.SNOW)
                            .scale(1.2f).temperature(-1.0f)
                            .waterColor(1751970)
                            .waterFogColor(11794906)
                            .surfaceBuilder(SurfaceBuilder.DEFAULT,
                                    new SurfaceBuilderConfig(
                                            Blocks.GRASS_BLOCK
                                                    .getDefaultState(),
                                            CraftleBlocks.TEST_BLOCK
                                                    .get()
                                                    .getDefaultState(),
                                            Blocks.SAND
                                                    .getDefaultState()))
                            .category(Biome.Category.PLAINS)
                            .downfall(0.5f).depth(0.131f)
                            .parent(null)));

    public static void registerBiomes() {
        registerBiome(TEST_BIOME.get(), BiomeDictionary.Type.PLAINS,
                BiomeDictionary.Type.OVERWORLD,
                BiomeDictionary.Type.FOREST);
    }

    private static void registerBiome(Biome biome,
                                      BiomeDictionary.Type... types) {
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL,
                new BiomeManager.BiomeEntry(biome, 100));
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addSpawnBiome(biome);
    }
}
