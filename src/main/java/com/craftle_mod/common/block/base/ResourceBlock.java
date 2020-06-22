package com.craftle_mod.common.block.base;

import com.craftle_mod.common.resource.IBlockResource;
import net.minecraft.block.SoundType;

public class ResourceBlock extends CraftleBlock {

    public ResourceBlock(IBlockResource resource, BlockType blockType,
        SoundType soundType) {
        super(resource, blockType, soundType);
    }


}
