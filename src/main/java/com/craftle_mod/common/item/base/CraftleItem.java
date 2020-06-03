package com.craftle_mod.common.item.base;

import com.craftle_mod.common.CraftleCreativeTabs;
import com.craftle_mod.common.resource.Resource;
import com.craftle_mod.common.resource.ResourceType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CraftleItem extends Item {

    private final Resource     resource;
    private final ResourceType type;
    private final boolean      hasEffect;

    public CraftleItem(Resource resource, ResourceType type) {
        this(resource, type, false);

    }

    public CraftleItem(Resource resource, ResourceType type,
                       boolean hasEffect) {
        super(getProperties(type));
        this.resource  = resource;
        this.type      = type;
        this.hasEffect = hasEffect;
    }

    public String getCraftleRegistryName() {
        return resource.getResourceName() + "_" + type.getResourceName();
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return hasEffect;
    }

    public Resource getResource() {
        return resource;
    }

    public ResourceType getType() {
        return type;
    }

    private static Properties getProperties(ResourceType type) {
        Properties properties;
        switch (type) {
            case ORE:
            case BIT:
            case DUST:
            case INGOT:
            case ENHANCED:
            case PURIFIED:
                properties = new Item.Properties()
                        .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_RESOURCES);
                break;
            default:
                properties = new Item.Properties()
                        .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MISC);
                break;
        }

        return properties;
    }
}
