package com.craftlemod.common.item;

import com.craftlemod.common.shared.IHasModelPath;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

public class CraftleBlockItem extends BlockItem implements IHasModelPath {

    private final Identifier id;

    private String modelPath;

    public CraftleBlockItem(Identifier id, String modelPath, Block block, Settings settings) {
        super(block, settings);
        this.id = id;
        this.modelPath = modelPath;
    }

    public Identifier getId() {
        return id;
    }

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }
}
