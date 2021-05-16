package com.craftle_mod.data.common;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.tags.CraftleBlockTags;
import com.craftle_mod.common.tags.util.ResourceTag;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CraftleBlockTagsProvider extends BlockTagsProvider {

    public CraftleBlockTagsProvider(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, Craftle.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {

        for (String key : CraftleBlockTags.RESOURCE_ORES.keySet()) {
            if (CraftleBlocks.RESOURCE_BLOCKS.get(key) != null) {

                ResourceTag<Block> resourceTag = CraftleBlockTags.RESOURCE_ORES.get(key);

                getOrCreateBuilder(resourceTag.getTag()).add(CraftleBlocks.RESOURCE_BLOCKS.get(key).get());
                if (resourceTag.getCommonTag() != null) {
                    getOrCreateBuilder(resourceTag.getCommonTag()).addTag(resourceTag.getTag());
                }
            }
        }

        //getOrCreateBuilder();
    }
}
