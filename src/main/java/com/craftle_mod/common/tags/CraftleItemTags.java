package com.craftle_mod.common.tags;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.tags.util.ResourceTag;
import java.util.HashMap;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class CraftleItemTags {

    public static HashMap<String, ResourceTag<Item>> TAGS_ITEMS = new HashMap<>();
    public static HashMap<String, ResourceTag<Item>> TAGS_BLOCK_ITEMS = new HashMap<>();

    public static ITag.INamedTag<Item> forge(String path) {
        return ItemTags.makeWrapperTag(new ResourceLocation("forge", path).toString());
    }

    public static ITag.INamedTag<Item> mod(String path) {
        return ItemTags.makeWrapperTag(new ResourceLocation(Craftle.MODID, path).toString());
    }

    public static ITag.INamedTag<Item> minecraft(String path) {
        return ItemTags.makeWrapperTag(new ResourceLocation("minecraft", path).toString());
    }

}
