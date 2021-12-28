package com.craftlemod.common;

import com.craftlemod.common.registry.CraftleBlockEntityTypes;
import com.craftlemod.common.registry.CraftleBlocks;
import com.craftlemod.common.registry.CraftleItems;
import com.craftlemod.common.registry.CraftleScreenHandlers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CraftleMod implements ModInitializer {

    public static final String MODID = "craftle";
    public static final String MOD_VERSION = "0.0.1.0";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final ItemGroup ITEM_GROUP_RESOURCES = FabricItemGroupBuilder.build(new Identifier(MODID, "resources"), () -> new ItemStack(CraftleItems.LEAD_INGOT));
    public static final ItemGroup ITEM_GROUP_MACHINES = FabricItemGroupBuilder.build(new Identifier(MODID, "machines"), () -> new ItemStack(CraftleItems.BLOCK_ITEMS.get("factory_intake")));

    @Override
    public void onInitialize() {
        CraftleBlocks.registerAll();
        CraftleItems.registerAll();
        CraftleBlockEntityTypes.registerAll();
        
        CraftleScreenHandlers.init();

        CraftleBlocks.generateOres();
    }
}
