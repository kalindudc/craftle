package com.craftlemod.common.registry;

import com.craftlemod.common.Craftlemod;
import com.craftlemod.common.block.CraftleBlock;
import com.craftlemod.common.item.CraftleItem;
import java.util.HashMap;
import java.util.Map;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item.Settings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CraftleItems {

    public static final Map<String, CraftleItem> ITEMS = new HashMap<>();
    public static final Map<String, CraftleBlockItem> BLOCK_ITEMS = new HashMap<>();

    // ingots
    public static final CraftleItem LEAD_INGOT = registerItemResource("lead_ingot", "ingot");
    public static final CraftleItem MAGNESIUM_INGOT = registerItemResource("magnesium_ingot", "ingot");
    public static final CraftleItem BRONZE_INGOT = registerItemResource("bronze_ingot", "ingot");
    public static final CraftleItem STEEL_INGOT = registerItemResource("steel_ingot", "ingot");
    public static final CraftleItem NICKEL_INGOT = registerItemResource("nickel_ingot", "ingot");
    public static final CraftleItem SILICON_INGOT = registerItemResource("silicon_ingot", "ingot");

    // raw material
    public static final CraftleItem LEAD_RAW = registerItemResource("raw_lead", "raw");
    public static final CraftleItem MAGNESIUM_RAW = registerItemResource("raw_magnesium", "raw");
    public static final CraftleItem BAUXITE_RAW = registerItemResource("raw_bauxite", "raw");

    // resource blocks

    public static void registerAll() {
        for (Map.Entry<String, CraftleItem> entry : ITEMS.entrySet()) {
            Registry.register(Registry.ITEM, entry.getValue().getId(), entry.getValue());
        }
    }

    public static void registerBlockItem(CraftleBlock block) {
        CraftleBlockItem blockItem = new CraftleBlockItem(block.getId(), block.getModelPath(), block, new FabricItemSettings().group(Craftlemod.ITEM_GROUP_RESOURCES));
        BLOCK_ITEMS.put(block.getId().getPath(), blockItem);
        Registry.register(Registry.ITEM, block.getId(), blockItem);
    }

    private static CraftleItem registerItemResource(String name, String resourceType) {
        CraftleItem item = new CraftleItem(new Identifier(Craftlemod.MODID, name), "resource/" + resourceType + "/" + name, new Settings().group(Craftlemod.ITEM_GROUP_RESOURCES));
        ITEMS.put(item.getId().getPath(), item);
        return item;
    }

    public static String createItemModelJson(String id) {
        String type = "generated";
        if (BLOCK_ITEMS.containsKey(id)) {
            type = "block";
        }

        // @formatter:off
        if ("generated".equals(type) || "handheld".equals(type)) {
            String modelPath = "item/" + id + "\"";
            if (ITEMS.containsKey(id)) {
                modelPath = "item/" + ITEMS.get(id).getModelPath() + "\"";
            }
            //The two types of items. "handheld" is used mostly for tools and the like, while "generated" is used for everything else.
            String model = "{\n" +
                "  \"parent\": \"item/" + type + "\",\n" +
                "  \"textures\": {\n" +
                "    \"layer0\": \"" + Craftlemod.MODID + ":" + modelPath + "\n" +
                "  }\n" +
                "}";
            return model;
        } else if ("block".equals(type)) {
            String modelPath = "block/" + BLOCK_ITEMS.get(id).getModelPath() + "\"";
            //However, if the item is a block-item, it will have a different model json than the previous two.
            return "{\n" +
                "  \"parent\": \""+ Craftlemod.MODID + ":" + modelPath + "\n" +
                "}";
        }
        else {
            //If the type is invalid, return an empty json string.
            return "";
        }
        // @formatter:on
    }
}
