package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.CraftleOreBlock;
import com.craftle_mod.common.block.CraftleResourceBlock;
import com.craftle_mod.common.item.CraftleBlockItem;
import com.craftle_mod.common.registries.util.RegistriesUtils;
import com.craftle_mod.common.resource.ResourceTypes;
import com.craftle_mod.common.resource.ore.OreResourceTypes;
import com.craftle_mod.common.resource.ore.OreTypes;
import java.util.HashMap;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftleBlocks {

    /**
     * UNUSED
     */
    private CraftleBlocks() {
    }

    public static final DeferredRegister<Block> BLOCKS = RegistriesUtils.createRegister(ForgeRegistries.BLOCKS);

    public static final HashMap<String, RegistryObject<Block>> RESOURCE_BLOCKS = new HashMap<>();

    static {

        // register ores
        for (OreTypes ore : OreTypes.values()) {

//            if (ore.shouldCreateOre()) {
//                RegistryObject<Block> block = registerOre(ore);
//            }
//            CraftleItems.registerOreResources(ore);

            registerOre(ore);
        }
    }

    private static void registerOre(OreTypes ore) {

        for (OreResourceTypes type : ore.getResourceTypes()) {

            String fullPath = ore.getNameWithResource(type);

            if (type.getResourceType().isBlock()) {

                Block block;
                if (type.equals(OreResourceTypes.ORE)) {
                    block = new CraftleOreBlock(ore);
                } else {
                    block = new CraftleResourceBlock(ore, type.getResourceType());
                }
                registerResourceBlock(fullPath, () -> block, type.getResourceType());
            } else {
                CraftleItems.registerResourceItem(fullPath, type.getResourceType());
            }
        }
    }

    public static RegistryObject<Block> registerBlock(String name, Supplier<Block> supplier, ResourceTypes type) {
        RegistryObject<Block> result = BLOCKS.register(name, supplier);
        CraftleItems
            .registerBlockItem(name, () -> new CraftleBlockItem(result.get(), new Item.Properties().group(Craftle.ITEM_GROUP), type), type);
        return result;
    }

    public static RegistryObject<Block> registerResourceBlock(String name, Supplier<Block> supplier, ResourceTypes type) {
        RegistryObject<Block> result = registerBlock(name, supplier, type);
        RESOURCE_BLOCKS.put(name, result);
        return result;
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
