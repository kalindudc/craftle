package com.craftlemod.common;

import com.craftlemod.common.registry.CraftleBlocks;
import com.craftlemod.common.registry.CraftleItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Craftlemod implements ModInitializer {

    public static final String MODID = "craftle";
    public static final String MOD_VERSION = "0.0.1.0";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final ItemGroup ITEM_GROUP_RESOURCES = FabricItemGroupBuilder.build(new Identifier(MODID, "resources"), () -> new ItemStack(CraftleItems.LEAD_INGOT));

    @Override
    public void onInitialize() {
        CraftleBlocks.registerAll();
        CraftleItems.registerAll();

        CraftleBlocks.generateOres();
    }
}
