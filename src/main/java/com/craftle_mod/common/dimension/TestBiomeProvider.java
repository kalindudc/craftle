package com.craftle_mod.common.dimension;

import com.craftle_mod.common.registries.CraftleBiomes;
import com.google.common.collect.ImmutableSet;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nonnull;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

public class TestBiomeProvider extends BiomeProvider {

    private static final Set<Biome> BIOME_LIST =
        ImmutableSet.of(CraftleBiomes.TEST_BIOME.get());

    private final Random rand;

    public TestBiomeProvider() {
        super(BIOME_LIST);
        this.rand = new Random();
    }


    @Nonnull
    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return CraftleBiomes.TEST_BIOME.get();
    }
}
