package com.craftle_mod.common.tile.base;

import com.craftle_mod.common.tier.CraftleBaseTier;

import java.util.function.Supplier;

public interface TileEntityFactory {

    Supplier<? extends ContainerizedTileEntity> buildSupplier(CraftleBaseTier tier);

    Supplier<? extends ContainerizedTileEntity> buildSupplier(CraftleBaseTier tier,
                                                              Supplier<Integer> energySupplier);
}
