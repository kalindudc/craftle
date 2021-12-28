package com.craftlemod.common.block.machine;

import com.craftlemod.common.blockentity.CraftleBlockEntity;
import java.util.function.BiFunction;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MachineControllerBlock extends MachineBlock {

    private final BiFunction<BlockPos, BlockState, BlockEntity> blockEntityConstructor;

    private BlockEntityType<BlockEntity> entityType;


    public MachineControllerBlock(Identifier id, String modelPath, Settings settings, BiFunction<BlockPos, BlockState, BlockEntity> blockEntityConstructor) {
        super(id, modelPath, settings);
        this.blockEntityConstructor = blockEntityConstructor;
        this.entityType = null;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        this.setEntity((CraftleBlockEntity) blockEntityConstructor.apply(pos, state));
        return this.getEntity();
    }

    public BiFunction<BlockPos, BlockState, BlockEntity> getConstructor() {
        return blockEntityConstructor;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, this.entityType, CraftleBlockEntity::tick);
    }

    public BlockEntityType<BlockEntity> getEntityType() {
        return entityType;
    }

    public void setEntityType(BlockEntityType<BlockEntity> entityType) {
        this.entityType = entityType;
    }
}
