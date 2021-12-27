package com.craftlemod.common.block.machine;

import com.craftlemod.common.CraftleMod;
import com.craftlemod.common.blockentity.CraftleBlockEntity;
import com.craftlemod.common.registry.CraftleBlockEntityTypes;
import com.craftlemod.common.shared.IHasModelPath;
import java.util.function.BiFunction;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MachineControllerBlock extends MachineBlock {

    private final BiFunction<BlockPos, BlockState, BlockEntity> blockEntityConstructor;

    private CraftleBlockEntity entity;
    private BlockEntityType<BlockEntity> entityType;


    public MachineControllerBlock(Identifier id, String modelPath, Settings settings, BiFunction<BlockPos, BlockState, BlockEntity> blockEntityConstructor) {
        super(id, modelPath, settings);
        this.blockEntityConstructor = blockEntityConstructor;
        this.entity = null;
        this.entityType = null;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        this.entity = (CraftleBlockEntity) blockEntityConstructor.apply(pos, state);
        return this.entity;
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
