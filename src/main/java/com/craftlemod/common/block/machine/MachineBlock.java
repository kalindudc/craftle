package com.craftlemod.common.block.machine;

import com.craftlemod.common.blockentity.CraftleBlockEntity;
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

public class MachineBlock extends BlockWithEntity implements IHasModelPath {

    private final Identifier id;

    private String modelPath;

    public MachineBlock(Identifier id, String modelPath, Settings settings) {
        super(settings);
        this.id = id;
        this.modelPath = modelPath;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public String getModelPath() {
        return modelPath;
    }

    @Override
    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }


    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return null;
    }
}
