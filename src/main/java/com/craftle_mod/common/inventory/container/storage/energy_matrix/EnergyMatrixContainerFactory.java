package com.craftle_mod.common.inventory.container.storage.energy_matrix;

import com.craftle_mod.common.registries.CraftleContainerTypes;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.storage.energy_matrix.EnergyMatrixTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.IContainerFactory;

public class EnergyMatrixContainerFactory {

    public static IContainerFactory<EnergyMatrixContainer> buildContainerFactory(
            CraftleBaseTier tier) {

        return (windowId, playerInventory, data) -> build(tier, windowId, playerInventory, data);
    }

    public static EnergyMatrixContainer build(CraftleBaseTier tier, int windowId,
                                              PlayerInventory playerInventory, PacketBuffer data) {

        switch (tier) {
            case TIER_1:
                return new EnergyMatrixContainer(CraftleContainerTypes.ENERGY_MATRIX_TIER_1.get(),
                                                 windowId, playerInventory, data);
            case TIER_2:
                return new EnergyMatrixContainer(CraftleContainerTypes.ENERGY_MATRIX_TIER_2.get(),
                                                 windowId, playerInventory, data);
            case TIER_3:
                return new EnergyMatrixContainer(CraftleContainerTypes.ENERGY_MATRIX_TIER_3.get(),
                                                 windowId, playerInventory, data);
            case TIER_4:
            case UNLIMITED:
                return new EnergyMatrixContainer(CraftleContainerTypes.ENERGY_MATRIX_TIER_4.get(),
                                                 windowId, playerInventory, data);
            case BASIC:
            default:
                return new EnergyMatrixContainer(CraftleContainerTypes.ENERGY_MATRIX_BASIC.get(),
                                                 windowId, playerInventory, data);

        }
    }

    public static EnergyMatrixContainer buildWithTileEntity(CraftleBaseTier tier, int windowId,
                                                            PlayerInventory playerInventory,
                                                            EnergyMatrixTileEntity energyMatrixTileEntity) {
        switch (tier) {
            case TIER_1:
                return new EnergyMatrixContainer(CraftleContainerTypes.ENERGY_MATRIX_TIER_1.get(),
                                                 windowId, playerInventory, energyMatrixTileEntity);
            case TIER_2:
                return new EnergyMatrixContainer(CraftleContainerTypes.ENERGY_MATRIX_TIER_2.get(),
                                                 windowId, playerInventory, energyMatrixTileEntity);
            case TIER_3:
                return new EnergyMatrixContainer(CraftleContainerTypes.ENERGY_MATRIX_TIER_3.get(),
                                                 windowId, playerInventory, energyMatrixTileEntity);
            case TIER_4:
            case UNLIMITED:
                return new EnergyMatrixContainer(CraftleContainerTypes.ENERGY_MATRIX_TIER_4.get(),
                                                 windowId, playerInventory, energyMatrixTileEntity);
            case BASIC:
            default:
                return new EnergyMatrixContainer(CraftleContainerTypes.ENERGY_MATRIX_BASIC.get(),
                                                 windowId, playerInventory, energyMatrixTileEntity);

        }
    }
}
