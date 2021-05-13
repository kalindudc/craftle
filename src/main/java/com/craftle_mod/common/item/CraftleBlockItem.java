package com.craftle_mod.common.item;

import com.craftle_mod.common.resource.IResourceType;
import com.craftle_mod.common.resource.ResourceTypes;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class CraftleBlockItem extends BlockItem implements IResourceType {

    private final ResourceTypes type;

    public CraftleBlockItem(Block blockIn, Properties builder, ResourceTypes resourceType) {
        super(blockIn, builder);
        this.type = resourceType;
    }

    @Override
    public ResourceTypes getType() {
        return type;
    }

}
