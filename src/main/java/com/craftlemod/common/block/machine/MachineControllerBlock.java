package com.craftlemod.common.block.machine;

import java.util.function.BiFunction;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class MachineControllerBlock extends MachineBlock {


    public MachineControllerBlock(Identifier id, String modelPath, Settings settings, BiFunction<BlockPos, BlockState, BlockEntity> blockEntityConstructor) {
        super(id, modelPath, settings, blockEntityConstructor);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
