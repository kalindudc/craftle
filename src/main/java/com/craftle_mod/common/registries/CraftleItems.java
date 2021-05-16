package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.item.CraftleResourceItem;
import com.craftle_mod.common.registries.util.RegistriesUtils;
import com.craftle_mod.common.resource.ResourceTypes;
import java.util.HashMap;
import java.util.function.Supplier;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftleItems {

    /**
     * UNUSED
     */
    public CraftleItems() {
    }

    public static final DeferredRegister<Item> ITEMS = RegistriesUtils.createRegister(ForgeRegistries.ITEMS);

    public static final HashMap<ResourceTypes, HashMap<String, RegistryObject<Item>>> RESOURCES = new HashMap<>();

    static {
        for (ResourceTypes type : ResourceTypes.values()) {
            RESOURCES.put(type, new HashMap<>());
        }
    }

    public static RegistryObject<Item> registerItem(String name, Supplier<Item> supplier) {
        return ITEMS.register(name, supplier);
    }

    public static RegistryObject<Item> registerResourceItem(String name, ResourceTypes type) {

        RegistryObject<Item> item = registerItem(name, () -> new CraftleResourceItem(new Properties().group(Craftle.ITEM_GROUP), type));
        RESOURCES.get(type).put(name, item);
        return item;
    }

    public static RegistryObject<Item> registerBlockItem(String name, Supplier<Item> supplier, ResourceTypes type) {

        RegistryObject<Item> result = registerItem(name, supplier);
        RESOURCES.get(type).put(name, result);
        return result;
    }


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
