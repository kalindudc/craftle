package com.craftle_mod.common.tags;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.resource.ore.OreResourceTypes;
import com.craftle_mod.common.resource.ore.OreTypes;
import com.craftle_mod.common.tags.util.ResourceTag;
import com.craftle_mod.common.tags.util.TagsUtil;
import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags.Blocks;
import net.minecraftforge.common.Tags.Items;

public class CraftleBlockTags {

    public static HashMap<String, ResourceTag<Block>> RESOURCE_ORES = new HashMap<>();

    static {

        for (OreTypes ore : OreTypes.values()) {
            for (OreResourceTypes resource : ore.getResourceTypes()) {
                String key = ore.getNameWithResource(resource);
                String path = TagsUtil.buildPath(resource.getResourceType().getTagCategory(), ore.getName());

                ResourceTag<Item> itemTag = new ResourceTag<>(resource.getResourceType(), key, CraftleItemTags.forge(path));

                if (resource.getResourceType().isBlock()) {
                    ResourceTag<Block> blockTag = new ResourceTag<>(resource.getResourceType(), key, forge(path));

                    switch (resource.getResourceType()) {
                        case STORAGE_BLOCK:
                            blockTag = blockTag.addCommonTag(Blocks.STORAGE_BLOCKS);
                            itemTag = itemTag.addCommonTag(Items.STORAGE_BLOCKS);
                            break;
                        case ORE:
                            blockTag = blockTag.addCommonTag(Blocks.ORES);
                            itemTag = itemTag.addCommonTag(Items.ORES);
                            break;
                    }

                    RESOURCE_ORES.put(key, blockTag);
                    CraftleItemTags.TAGS_BLOCK_ITEMS.put(key, itemTag);
                } else {

                    switch (resource.getResourceType()) {
                        case INGOT:
                            itemTag = itemTag.addCommonTag(Items.INGOTS);
                            break;
                    }
                    CraftleItemTags.TAGS_ITEMS.put(key, itemTag);
                }
            }
        }
    }

    public static ITag.INamedTag<Block> forge(String path) {
        return BlockTags.makeWrapperTag(new ResourceLocation("forge", path).toString());
    }

    public static ITag.INamedTag<Block> mod(String path) {
        return BlockTags.makeWrapperTag(new ResourceLocation(Craftle.MODID, path).toString());
    }

}
