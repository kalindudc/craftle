package com.craftle_mod.common.block;

import com.craftle_mod.common.resource.IResourceType;
import com.craftle_mod.common.resource.ResourceTypes;
import net.minecraft.block.Block;

public class CraftleBlock extends Block implements IResourceType {

    private final ResourceTypes type;

    public CraftleBlock(Properties properties, ResourceTypes type) {
        super(properties);
        this.type = type;
    }

    @Override
    public ResourceTypes getType() {
        return type;
    }
}
