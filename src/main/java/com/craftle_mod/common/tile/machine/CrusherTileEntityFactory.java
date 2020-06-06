package com.craftle_mod.common.tile.machine;

import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.tier.CraftleBaseTier;

import java.util.function.Supplier;

public class CrusherTileEntityFactory {

    private CraftleBaseTier tier;

    public CrusherTileEntityFactory(CraftleBaseTier tier) {
        this.tier = tier;
    }

    public Supplier<CrusherTileEntity> buildSupplier() {
        switch (tier) {
            case TIER_1:
                return (() -> new CrusherTileEntity(
                        CraftleTileEntityTypes.CRUSHER_TIER_1.get(), tier));
            case TIER_2:
                return (() -> new CrusherTileEntity(
                        CraftleTileEntityTypes.CRUSHER_TIER_2.get(), tier));
            case TIER_3:
                return (() -> new CrusherTileEntity(
                        CraftleTileEntityTypes.CRUSHER_TIER_3.get(), tier));
            case TIER_4:
            case UNLIMITED:
                return (() -> new CrusherTileEntity(
                        CraftleTileEntityTypes.CRUSHER_TIER_4.get(), tier));
            case BASIC:
            default:
                return (() -> new CrusherTileEntity(
                        CraftleTileEntityTypes.CRUSHER_BASIC.get(), tier));

        }
    }
}
