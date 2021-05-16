package com.craftle_mod.common.item;

import com.craftle_mod.common.resource.IResourceType;
import com.craftle_mod.common.resource.ResourceTypes;
import net.minecraft.item.Item;

public class CraftleResourceItem extends Item implements IResourceType {

    private final ResourceTypes type;

    public CraftleResourceItem(Properties properties, ResourceTypes type) {
        super(properties);
        this.type = type;
    }

    @Override
    public ResourceTypes getType() {
        return type;
    }
}
