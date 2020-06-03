package com.craftle_mod.common.block;

import com.craftle_mod.common.block.base.CraftleBlock;
import com.craftle_mod.common.resource.OreResource;
import net.minecraft.block.SoundType;

public class OreBlock extends CraftleBlock {

    public OreBlock(OreResource resource, BlockType blockType,
                    SoundType soundType) {
        super(resource, blockType, soundType);
    }
}
