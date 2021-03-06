package com.craftle_mod.common.block.base;

import com.craftle_mod.common.resource.IBlockResource;
import com.craftle_mod.common.tile.base.CraftleTileEntity;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.SoundType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ActiveBlockBase extends FacedBlockBase {

    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public ActiveBlockBase(IBlockResource resource, BlockType blockType, SoundType soundType) {
        super(resource, blockType, soundType);
        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH)
            .with(LIT, Boolean.FALSE));
    }

    @Override
    protected void fillStateContainer(
        StateContainer.Builder<net.minecraft.block.Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    public void setActive(boolean b, BlockState state, World worldIn, BlockPos pos,
        ActiveBlockBase block) {
        if (!worldIn.isRemote) {
            TileEntity entity = worldIn.getTileEntity(pos);
            if (entity instanceof PoweredMachineTileEntity) {
                this.setActive(b, state, worldIn, pos);
            }
        }
    }

    private void setActive(boolean b, BlockState state, World worldIn, BlockPos pos) {
        if (!(state.get(LIT) && b)) {
            worldIn.setBlockState(pos, state.with(LIT, b), 3);
        }
    }

    public void changeState(boolean b, BlockState state, World worldIn, BlockPos pos) {
        this.setActive(b, state, worldIn, pos);
    }

    @Override
    public abstract TileEntityType<? extends CraftleTileEntity> getTileType();

    public abstract ContainerType<?> getContainerType();
}
