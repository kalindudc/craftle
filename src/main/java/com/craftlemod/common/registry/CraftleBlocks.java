package com.craftlemod.common.registry;

import com.craftlemod.common.Craftlemod;
import com.craftlemod.common.block.CraftleBlock;
import java.util.HashMap;
import java.util.Map;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CraftleBlocks {

    public static final Map<String, CraftleBlock> BLOCKS = new HashMap<>();

    // resource
    public static final CraftleBlock LEAD_BLOCK = registerBlockResource("lead_block", "block");
    public static final CraftleBlock BRONZE_BLOCK = registerBlockResource("bronze_block", "block");

    public static void registerAll() {
        for (Map.Entry<String, CraftleBlock> entry : BLOCKS.entrySet()) {
            Registry.register(Registry.BLOCK, entry.getValue().getId(), entry.getValue());
            CraftleItems.registerBlockItem(entry.getValue());
        }
    }

    private static CraftleBlock registerBlockResource(String name, String resourceType) {
        CraftleBlock item = new CraftleBlock(new Identifier(Craftlemod.MODID, name), "resource/" + resourceType + "/" + name, FabricBlockSettings.of(Material.METAL).strength(4.0f));
        BLOCKS.put(item.getId().getPath(), item);
        return item;
    }
}
