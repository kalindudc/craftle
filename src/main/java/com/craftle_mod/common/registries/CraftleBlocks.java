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

    private static final DeferredRegister<Block> BLOCKS = RegistriesUtils.createRegister(ForgeRegistries.BLOCKS);

    public static final HashMap<OreTypes, RegistryObject<Block>> ORES = new HashMap<>();

    static {

        // register ores
        for (OreTypes ore : OreTypes.VALUES) {
            RegistryObject<Block> block = registerOre(ore);
            ORES.put(ore, block);
        }
    }

    private static RegistryObject<Block> registerOre(OreTypes ore) {
        return registerBlock(ore.getName() + "_" + ResourceTypes.ORE.getName(), () -> new CraftleOreBlock(ore));
    }

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> supplier) {
        RegistryObject<Block> result = BLOCKS.register(name, supplier);
        CraftleItems.registerItem(name, () -> new CraftleBlockItem(result.get(), new Item.Properties().group(Craftle.ITEM_GROUP)));
        return result;
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
