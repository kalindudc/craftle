package com.craftlemod.common.block;

import com.craftlemod.common.shared.IHasModelPath;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public class CraftleBlock extends Block implements IHasModelPath {

    private final Identifier id;

    private String modelPath;

    public CraftleBlock(Identifier id, String modelPath, Settings settings) {
        super(settings);
        this.id = id;
        this.modelPath = modelPath;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public String getModelPath() {
        return modelPath;
    }

    @Override
    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }
}
