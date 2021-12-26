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

        for (Map.Entry<String, CraftleBlockItem> entry : BLOCK_ITEMS.entrySet()) {
            Registry.register(Registry.ITEM, entry.getValue().getId(), entry.getValue());
        }
    }

    public static void registerBlockItem(CraftleBlock block) {
        CraftleBlockItem blockItem = new CraftleBlockItem(new Identifier(Craftlemod.MODID, block.getId().getPath()), block.getModelPath(), block, new FabricItemSettings().group(Craftlemod.ITEM_GROUP_RESOURCES));
        BLOCK_ITEMS.put(block.getId().getPath(), blockItem);
    }

    private static CraftleItem registerItemResource(String name, String resourceType) {
        CraftleItem item = new CraftleItem(new Identifier(Craftlemod.MODID, name), "resource/" + resourceType + "/" + name, new Settings().group(Craftlemod.ITEM_GROUP_RESOURCES));
        ITEMS.put(name, item);
        return item;
    }

    // @formatter:off
    private static String createItemModelJson(CraftleItem item) {
        String modelPath = "item/" + item.getModelPath() + "\"";

        //The two types of items. "handheld" is used mostly for tools and the like, while "generated" is used for everything else.
        String model = "{\n" +
            "  \"parent\": \"item/generated\",\n" +
            "  \"textures\": {\n" +
            "    \"layer0\": \"" + Craftlemod.MODID + ":" + modelPath + "\n" +
            "  }\n" +
            "}";
        return model;
    }

    private static String createBlockItemModelJson(CraftleBlockItem item) {
        String modelPath = "block/" + item.getModelPath() + "\"";
        //However, if the item is a block-item, it will have a different model json than the previous two.
        String model = "{\n" +
            "  \"parent\": \""+ Craftlemod.MODID + ":" + modelPath + "\n" +
            "}";
        return model;
    }
    // @formatter:on

    public static String createModelJson(String id) {
        if (BLOCK_ITEMS.containsKey(id)) {
            return createBlockItemModelJson(BLOCK_ITEMS.get(id));
        }
        if (ITEMS.containsKey(id)) {
            return createItemModelJson(ITEMS.get(id));
        }

        return "";
    }
}
