package com.craftlemod.common.item;

import com.craftlemod.common.shared.IHasModelPath;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class CraftleItem extends Item implements IHasModelPath {

    private final Identifier id;

    private String modelPath;

    public CraftleItem(Identifier id, String modelPath, Settings settings) {
        super(settings);
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
