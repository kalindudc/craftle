package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.CraftleOreBlock;
import com.craftle_mod.common.item.CraftleBlockItem;
import com.craftle_mod.common.registries.util.RegistriesUtils;
import com.craftle_mod.common.resource.OreTypes;
import com.craftle_mod.common.resource.ResourceTypes;
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

    public static final HashMap<OreTypes, RegistryObject<Block>> ORES = new HashMap<>();

    static {

        // register ores
        for (OreTypes ore : OreTypes.values()) {
            RegistryObject<Block> block = registerOre(ore);
            ORES.put(ore, block);
        }
    }

    private static RegistryObject<Block> registerOre(OreTypes ore) {
        RegistryObject<Block> result = registerBlock(ore.getName(), ResourceTypes.ORE.getName(), () -> new CraftleOreBlock(ore),
            ResourceTypes.ORE);
        CraftleItems.registerOreResources(ore);
        return result;
    }

    public static RegistryObject<Block> registerBlock(String name, String suffix, Supplier<Block> supplier, ResourceTypes type) {
        RegistryObject<Block> result = BLOCKS.register(name + "_" + suffix, supplier);
        CraftleItems.registerBlockItem(name + "_" + suffix,
            () -> new CraftleBlockItem(result.get(), new Item.Properties().group(Craftle.ITEM_GROUP), type), type);
        return result;
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
