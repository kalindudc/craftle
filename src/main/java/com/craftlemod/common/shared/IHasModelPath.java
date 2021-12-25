package com.craftlemod.common.shared;

import net.minecraft.util.Identifier;

public interface IHasModelPath {

    Identifier getId();

    String getModelPath();

    void setModelPath(String modelPath);
}
