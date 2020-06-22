package com.craftle_mod.common.config;

import com.craftle_mod.common.registries.CraftleBlocks;
import net.minecraft.block.Block;

public enum OreGenConfig {
    COPPER(CraftleBlocks.COPPER_ORE.get(), new OreConfig(16, 0, 0, 80), 10),
    TIN(CraftleBlocks.TIN_ORE.get(), new OreConfig(16, 0, 0, 80), 10),
    RUBY(CraftleBlocks.RUBY_ORE.get(), new OreConfig(10, 0, 0, 40), 8),
    SAPPHIRE(CraftleBlocks.SAPPHIRE_ORE.get(), new OreConfig(16, 0, 0, 40), 8),
    URANIUM(CraftleBlocks.URANIUM_ORE.get(), new OreConfig(6, 0, 0, 12), 8);

    private final Block block;
    private final OreConfig config;
    private final int maxVeinSize;

    OreGenConfig(Block block, OreConfig config, int maxVeinSize) {
        this.block = block;
        this.config = config;
        this.maxVeinSize = maxVeinSize;
    }

    public Block getBlock() {
        return block;
    }

    public OreConfig getConfig() {
        return config;
    }

    public int getMaxVeinSize() {
        return maxVeinSize;
    }

    public static class OreConfig {

        private final int count;
        private final int bottomOffset;
        private final int topOffset;
        private final int maximum;

        private OreConfig(int count, int bottomOffset, int topOffset,
            int maximum) {
            this.count = count;
            this.bottomOffset = bottomOffset;
            this.topOffset = topOffset;
            this.maximum = maximum;
        }

        public int getCount() {
            return count;
        }

        public int getBottomOffset() {
            return bottomOffset;
        }

        public int getTopOffset() {
            return topOffset;
        }

        public int getMaximum() {
            return maximum;
        }
    }
}
