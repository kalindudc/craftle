package com.craftle_mod.data.common;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.registries.CraftleItems;
import com.craftle_mod.common.tags.CraftleBlockTags;
import com.craftle_mod.common.tags.CraftleItemTags;
import com.craftle_mod.common.tags.util.ResourceTag;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CraftleItemTagProvider extends ItemTagsProvider {

    public CraftleItemTagProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider,
        @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, Craftle.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {

        for (String key : CraftleItemTags.TAGS_BLOCK_ITEMS.keySet()) {
            ResourceTag<Block> resourceTagBlock = CraftleBlockTags.RESOURCE_ORES.get(key);
            ResourceTag<Item> resourceTagItem = CraftleItemTags.TAGS_BLOCK_ITEMS.get(key);

            copy(resourceTagBlock.getTag(), resourceTagItem.getTag());
            if (resourceTagBlock.getCommonTag() != null && resourceTagItem.getCommonTag() != null) {
                copy(resourceTagBlock.getCommonTag(), resourceTagItem.getCommonTag());
            }
        }

        for (String key : CraftleItemTags.TAGS_ITEMS.keySet()) {
            ResourceTag<Item> resourceTag = CraftleItemTags.TAGS_ITEMS.get(key);

            getOrCreateBuilder(resourceTag.getTag())
                .add(CraftleItems.RESOURCES.get(resourceTag.getType()).get(resourceTag.getName()).get());
            if (resourceTag.getCommonTag() != null) {
                getOrCreateBuilder(resourceTag.getTag()).addTag(resourceTag.getTag());
            }
        }


    }
}
