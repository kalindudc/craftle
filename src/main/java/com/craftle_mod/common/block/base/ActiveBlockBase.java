package com.craftle_mod.common.block.base;

import com.craftle_mod.common.resource.IBlockResource;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.SoundType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

public class ActiveBlockBase extends FacedBlockBase {

    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public ActiveBlockBase(IBlockResource resource, BlockType blockType,
                           SoundType soundType) {
        super(resource, blockType, soundType);
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
