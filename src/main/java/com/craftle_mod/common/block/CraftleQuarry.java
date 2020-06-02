package com.craftle_mod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class CraftleQuarry extends Block {

    public static final DirectionProperty FACING =
            HorizontalBlock.HORIZONTAL_FACING;

    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public CraftleQuarry(Properties properties) {
        super(properties);
        this.setDefaultState(
                this.getStateContainer().getBaseState()
                    .with(FACING, Direction.NORTH).with(LIT,
                                                        Boolean.valueOf(
                                                                false)));
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING,
                                           context.getPlacementHorizontalFacing()
                                                  .getOpposite());
    }

    /**
     * Returns the blockstate with the given rotation from the passed
     * blockstate. If inapplicable, returns the passed
     * blockstate.
     *
     * @deprecated call via {@link IBlockState#withRotation(Rotation)}
     * whenever possible. Implementing/overriding is
     * fine.
     */
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos,
                             Rotation direction) {
        return state.with(FACING, direction.rotate(state.get(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate.
     * If inapplicable, returns the passed
     * blockstate.
     *
     * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever
     * possible. Implementing/overriding is fine.
     */
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    protected void fillStateContainer(
            StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }
}
