package com.craftlemod.common.block;

import net.minecraft.util.Identifier;

public class CraftleOreBlock extends CraftleBlock {

    private final int minY;
    private final int maxY;
    private final int veinSize;

    public CraftleOreBlock(Identifier id, String modelPath, Settings settings, int minY, int maxY, int veinSize) {
        super(id, modelPath, settings);

        this.minY = minY;
        this.maxY = maxY;
        this.veinSize = veinSize;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinY() {
        return minY;
    }

    public int getVeinSize() {
        return veinSize;
    }
}
