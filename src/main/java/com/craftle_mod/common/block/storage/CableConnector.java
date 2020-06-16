package com.craftle_mod.common.block.storage;

import com.craftle_mod.common.block.base.MachineBlock;
import com.craftle_mod.common.resource.IBlockResource;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class CableConnector extends MachineBlock {

    // @formatter:off
    private static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(0, 0, 1, 16, 16, 15),
            Block.makeCuboidShape(4, 4, 0, 12, 12, 1),
            Block.makeCuboidShape(4, 4, 15, 12, 12, 16),
            Block.makeCuboidShape(0, 0, 0, 16, 1, 1),
            Block.makeCuboidShape(0, 0, 15, 16, 1, 16),
            Block.makeCuboidShape(0, 15, 0, 16, 16, 1),
            Block.makeCuboidShape(0, 15, 15, 16, 16, 16),
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(2, 1, 0, 3, 15, 1),
            Block.makeCuboidShape(13, 1, 15, 14, 15, 16),
            Block.makeCuboidShape(13, 1, 0, 14, 15, 1),
            Block.makeCuboidShape(2, 1, 15, 3, 15, 16),
            Block.makeCuboidShape(10, 13, 0, 11, 14, 1),
            Block.makeCuboidShape(5, 13, 15, 6, 14, 16),
            Block.makeCuboidShape(10, 2, 0, 11, 3, 1),
            Block.makeCuboidShape(5, 2, 15, 6, 3, 16),
            Block.makeCuboidShape(5, 13, 0, 6, 14, 1),
            Block.makeCuboidShape(10, 13, 15, 11, 14, 16),
            Block.makeCuboidShape(5, 2, 0, 6, 3, 1),
            Block.makeCuboidShape(10, 2, 15, 11, 3, 16),
            Block.makeCuboidShape(7, 13, 0, 9, 14, 1),
            Block.makeCuboidShape(7, 13, 15, 9, 14, 16),
            Block.makeCuboidShape(7, 2, 0, 9, 3, 1),
            Block.makeCuboidShape(7, 2, 15, 9, 3, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16)).reduce((v1, v2) -> {
                return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
            }).get();

    // @formatter:on

    public CableConnector(IBlockResource resource, BlockType blockType, SoundType soundType) {
        super(resource, blockType, soundType, CraftleBaseTier.UNLIMITED);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos,
                               ISelectionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return null;//return CraftleTileEntityTypes.CABLE_CONECTOR.get().create();
    }

    @Override
    public void changeState(boolean b, BlockState state, World worldIn, BlockPos pos) {
        this.setActive(b, state, worldIn, pos, this);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

}
