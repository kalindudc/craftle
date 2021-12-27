package com.craftlemod.common.blockentity;

import java.util.List;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

public class FactoryBlockEntity extends CraftleBlockEntity{

    private List<FactoryIOBlockEntity> factoryIOs;

    public FactoryBlockEntity(BlockEntityRecord record) {
        super(record);
        this.factoryIOs = new ArrayList<>();
    }

    /**
     * Check if the given block is valid for this factory multiblock
     */
    public boolean isValidMultiBlock(Block block) {
        return false;
    }

    public boolean isValidFactoryIO(BlockEntity entity) {
        return entity instanceof FactoryIOBlockEntity;
    }

    public Pair<Boolean, List<FactoryIOBlockEntity>> areValidEdges(World world, BlockPos pos1, BlockPos pos2, int numBlocks) {
        List<FactoryIOBlockEntity> factoryIOs = new ArrayList<>();

        boolean top = isValidRow(world, pos1, new Vec3f(1, 0, 0), numBlocks, factoryIOs);
        boolean left = isValidRow(world, pos1, new Vec3f(0, 0, -1), numBlocks, factoryIOs);
        boolean bottom = isValidRow(world, pos2, new Vec3f(-1, 0, 0), numBlocks, factoryIOs);
        boolean right = isValidRow(world, pos2, new Vec3f(0, 0, 1), numBlocks, factoryIOs);

        return new Pair<>(top && left && bottom && right, factoryIOs);
    }

    public boolean isValidRow(World world, BlockPos pos, Vec3f dir, int numBlocks, List<FactoryIOBlockEntity> factoryIOs) {
        for (int i = 0; i < numBlocks; i++) {
            int x = pos.getX() + (i * (int) dir.getX());
            int y = pos.getY() + (i * (int) dir.getY());
            int z = pos.getZ() + (i * (int) dir.getZ());

            if (!isValidMultiBlock(world.getBlockState(new BlockPos(x, y, z)).getBlock())) {
                return false;
            }
            else {
                if (isValidFactoryIO(world.getBlockEntity(new BlockPos(x, y, z)))) {
                    factoryIOs.add((FactoryIOBlockEntity) world.getBlockEntity(new BlockPos(x, y, z)));
                }
            }
        }
        return true;
    }

    public Pair<Integer, Integer> testShape(World world, BlockPos pos) {
        return null;
    }

    public List<FactoryIOBlockEntity> getFactoryIOs() {
        return factoryIOs;
    }

    public void setFactoryIOs(List<FactoryIOBlockEntity> factoryIOs) {
        this.factoryIOs = factoryIOs;
    }
}
