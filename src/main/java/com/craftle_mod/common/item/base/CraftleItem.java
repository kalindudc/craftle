package com.craftle_mod.common.item.base;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class CraftleItem extends Item implements ICraftleItem {

    private final String name;

    public CraftleItem(String registryName, ItemGroup tab) {
        super(new Item.Properties().group(tab));
        this.name = registryName;
    }

    @Override
    public String getCraftleRegistryName() {
        return name;
    }
}
