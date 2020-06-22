package com.craftle_mod.common.block.base;

import com.craftle_mod.common.resource.IBlockResource;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

@SuppressWarnings("deprecation")
public class FacedBlockBase extends CraftleBlock {

    public static final DirectionProperty FACING =
        HorizontalBlock.HORIZONTAL_FACING;

    public FacedBlockBase(IBlockResource resource, BlockType blockType,
        SoundType soundType) {
        super(resource, blockType, soundType);
        this.setDefaultState(this.getStateContainer().getBaseState()
            .with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING,
            context.getPlacementHorizontalFacing()
                .getOpposite());
    }

    @Nonnull
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Nonnull
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(
        StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}
