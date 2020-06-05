package com.craftle_mod.common.block.base;

import com.craftle_mod.common.resource.IBlockResource;
import net.minecraft.block.SoundType;

public class MachineBlock extends ActiveBlockBase {

    public MachineBlock(IBlockResource resource, BlockType blockType,
                        SoundType soundType) {
        super(resource, blockType, soundType);
    }
}
