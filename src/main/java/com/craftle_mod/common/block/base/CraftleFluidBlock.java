package com.craftle_mod.common.block.base;

import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class CraftleFluidBlock extends FlowingFluidBlock {

    public CraftleFluidBlock(Supplier<? extends FlowingFluid> supplier,
        ForgeFlowingFluid.Properties properties) {
        super(supplier, Block.Properties.create(Material.WATER).doesNotBlockMovement()
            .hardnessAndResistance(100f).noDrops());
    }
}
