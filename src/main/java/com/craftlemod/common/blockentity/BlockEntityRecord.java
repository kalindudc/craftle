package com.craftlemod.common.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public record BlockEntityRecord(BlockEntityType<?> type, BlockPos pos, BlockState state) {
}
