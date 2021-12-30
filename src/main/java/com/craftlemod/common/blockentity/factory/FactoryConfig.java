package com.craftlemod.common.blockentity.factory;

import java.util.List;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;

public record FactoryConfig(int height, int radius, Vec2f topLeftCords, Vec2f bottomRightCords, List<BlockPos> intakes, List<BlockPos> exhausts) {

}
