package com.craftle_mod.common.block.base;

import com.craftle_mod.common.block.CraftleBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.state.DirectionProperty;

public class CraftleDirectionalBlock extends CraftleBlock {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public CraftleDirectionalBlock(Properties properties) {
        super(properties);
    }
}
