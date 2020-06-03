package com.craftle_mod.common.block.base;

import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

public class CraftleActiveBlockBase extends CraftleBlockBase {

    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public CraftleActiveBlockBase(
            net.minecraft.block.Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                                 .with(FACING, Direction.NORTH)
                                 .with(LIT, Boolean.valueOf(false)));
    }

    @Override
    protected void fillStateContainer(
            StateContainer.Builder<net.minecraft.block.Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

}
