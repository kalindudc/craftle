package com.craftle_mod.common.tile.storage.energy_matrix;

import com.craftle_mod.api.TileEntityConstants;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.TileEntityFactory;
import java.util.function.Supplier;

public class EnergyMatrixTileEntityFactory implements TileEntityFactory {

    public Supplier<EnergyMatrixTileEntity> buildSupplier(CraftleBaseTier tier) {
        int capacity =
            (int) (TileEntityConstants.ENERGY_MATRIX_BASE_CAPACITY * tier.getMultiplier());
        switch (tier) {
            case TIER_1:
                return (() -> new EnergyMatrixTileEntity(
                    CraftleTileEntityTypes.ENERGY_MATRIX_TIER_1.get(), tier, capacity));
            case TIER_2:
                return (() -> new EnergyMatrixTileEntity(
                    CraftleTileEntityTypes.ENERGY_MATRIX_TIER_2.get(), tier, capacity));
            case TIER_3:
                return (() -> new EnergyMatrixTileEntity(
                    CraftleTileEntityTypes.ENERGY_MATRIX_TIER_3.get(), tier, capacity));
            case TIER_4:
            case UNLIMITED:
                return (() -> new EnergyMatrixTileEntity(
                    CraftleTileEntityTypes.ENERGY_MATRIX_TIER_4.get(), tier, capacity));
            case BASIC:
            default:
                return (() -> new EnergyMatrixTileEntity(
                    CraftleTileEntityTypes.ENERGY_MATRIX_BASIC.get(), tier, capacity));

        }
    }

    public Supplier<EnergyMatrixTileEntity> buildSupplier(CraftleBaseTier tier,
        Supplier<Integer> energySupplier) {
        int capacity =
            (int) (TileEntityConstants.ENERGY_MATRIX_BASE_CAPACITY * tier.getMultiplier());
        switch (tier) {
            case TIER_1:
                return (() -> new EnergyMatrixTileEntity(
                    CraftleTileEntityTypes.ENERGY_MATRIX_TIER_1.get(), tier, capacity,
                    energySupplier.get()));
            case TIER_2:
                return (() -> new EnergyMatrixTileEntity(
                    CraftleTileEntityTypes.ENERGY_MATRIX_TIER_2.get(), tier, capacity,
                    energySupplier.get()));
            case TIER_3:
                return (() -> new EnergyMatrixTileEntity(
                    CraftleTileEntityTypes.ENERGY_MATRIX_TIER_3.get(), tier, capacity,
                    energySupplier.get()));
            case TIER_4:
            case UNLIMITED:
                return (() -> new EnergyMatrixTileEntity(
                    CraftleTileEntityTypes.ENERGY_MATRIX_TIER_4.get(), tier, capacity,
                    energySupplier.get()));
            case BASIC:
            default:
                return (() -> new EnergyMatrixTileEntity(
                    CraftleTileEntityTypes.ENERGY_MATRIX_BASIC.get(), tier, capacity,
                    energySupplier.get()));

        }
    }
}
