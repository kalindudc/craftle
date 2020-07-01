package com.craftle_mod.common.tile.machine.crusher;

import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.ContainerizedTileEntity;
import com.craftle_mod.common.tile.base.TileEntityFactory;

import java.util.function.Supplier;

public class CrusherTileEntityFactory implements TileEntityFactory {

    public Supplier<CrusherTileEntity> buildSupplier(CraftleBaseTier tier) {
        switch (tier) {
            case TIER_1:
                return (() -> new CrusherTileEntity(CraftleTileEntityTypes.CRUSHER_TIER_1.get(),
                        tier));
            case TIER_2:
                return (() -> new CrusherTileEntity(CraftleTileEntityTypes.CRUSHER_TIER_2.get(),
                        tier));
            case TIER_3:
                return (() -> new CrusherTileEntity(CraftleTileEntityTypes.CRUSHER_TIER_3.get(),
                        tier));
            case TIER_4:
            case UNLIMITED:
                return (() -> new CrusherTileEntity(CraftleTileEntityTypes.CRUSHER_TIER_4.get(),
                        tier));
            case BASIC:
            default:
                return (() -> new CrusherTileEntity(CraftleTileEntityTypes.CRUSHER_BASIC.get(),
                        tier));

        }
    }

    @Override
    public Supplier<? extends ContainerizedTileEntity> buildSupplier(CraftleBaseTier tier,
                                                                     Supplier<Integer> energySupplier) {
        switch (tier) {
            case TIER_1:
                return (() -> new CrusherTileEntity(CraftleTileEntityTypes.CRUSHER_TIER_1.get(),
                        tier, energySupplier.get()));
            case TIER_2:
                return (() -> new CrusherTileEntity(CraftleTileEntityTypes.CRUSHER_TIER_2.get(),
                        tier, energySupplier.get()));
            case TIER_3:
                return (() -> new CrusherTileEntity(CraftleTileEntityTypes.CRUSHER_TIER_3.get(),
                        tier, energySupplier.get()));
            case TIER_4:
            case UNLIMITED:
                return (() -> new CrusherTileEntity(CraftleTileEntityTypes.CRUSHER_TIER_4.get(),
                        tier, energySupplier.get()));
            case BASIC:
            default:
                return (() -> new CrusherTileEntity(CraftleTileEntityTypes.CRUSHER_BASIC.get(),
                        tier, energySupplier.get()));

        }
    }
}
