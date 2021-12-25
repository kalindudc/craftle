package com.craftlemod.common.registries;

import com.craftlemod.common.Craftlemod;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CraftleItems {

    public static final Map<String, Item> ITEMS = new HashMap<>();

    // ingots
    public static final Item LEAD_INGOT = registerItem("lead_ingot", new Item(new Item.Settings().group(ItemGroup.MATERIALS)));

    // raw material

    // resource blocks

    public static void registerAll() {
        for (Map.Entry<String, Item> entry : ITEMS.entrySet()) {
            Registry.register(Registry.ITEM, new Identifier(Craftlemod.MODID, entry.getKey()), entry.getValue());
        }
    }

    private static Item registerItem(String name, Item item) {
        ITEMS.put(name, item);
        return item;
    }
}
