package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.CraftleBlock;
import com.craftle_mod.common.item.CraftleItem;
import com.craftle_mod.common.registries.util.RegistriesUtils;
import com.craftle_mod.common.resource.OreResourceTypes;
import com.craftle_mod.common.resource.OreTypes;
import com.craftle_mod.common.resource.ResourceTypes;
import java.util.HashMap;
import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.material.Material;
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

    public static RegistryObject<Item> registerBlockItem(String name, Supplier<Item> supplier, ResourceTypes type) {

        RegistryObject<Item> result = registerItem(name, supplier);
        RESOURCES.get(type).put(name, result);
        return result;
    }

    public static void registerOreResources(OreTypes ore) {
        for (OreResourceTypes type : ore.getResourceTypes()) {
            String itemName = type.isSuffixIsPrefix() ? buildName(type.getName(), ore.getName()) : buildName(ore.getName(), type.getName());

            if (type.isBlock()) {
                CraftleBlocks.registerBlock(ore.getName(), type.getName(), () -> new CraftleBlock(
                    AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(ore.getHardness(), ore.getResistance())
                        .setRequiresTool().harvestLevel(ore.getHarvestLevel()), ResourceTypes.BLOCK), ResourceTypes.ORE);
            } else {
                RegistryObject<Item> item = registerItem(itemName,
                    () -> new CraftleItem(new Properties().group(Craftle.ITEM_GROUP), ResourceTypes.ORE));
                RESOURCES.get(ResourceTypes.ORE).put(itemName, item);
            }
        }
    }

    private static String buildName(String name, String suffix) {
        return name + "_" + suffix;
    }


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
