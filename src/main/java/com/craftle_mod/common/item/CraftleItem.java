package com.craftle_mod.common.item;

import com.craftle_mod.common.resource.IResourceType;
import com.craftle_mod.common.resource.ResourceTypes;
import net.minecraft.item.Item;

public class CraftleItem extends Item implements IResourceType {

    private final ResourceTypes type;

    public CraftleItem(Properties properties, ResourceTypes type) {
        super(properties);
        this.type = type;
    }

    @Override
    public ResourceTypes getType() {
        return type;
    }
}
