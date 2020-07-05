package com.craftle_mod.common.util;

import com.craftle_mod.api.UnitConstants;
import com.craftle_mod.common.capability.Capabilities;
import com.craftle_mod.common.capability.energy.CraftleEnergyStorage;
import com.craftle_mod.common.capability.energy.ICraftleEnergyStorage;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.LazyOptional;

public abstract class EnergyUtils {

    public static double emitEnergy(CraftleEnergyStorage energyContainer,
        PoweredMachineTileEntity tileEntity, double maxExtractRate) {

        return energyContainer.extractEnergy(
            emitEnergy(energyContainer.extractEnergy(maxExtractRate, true), tileEntity));
    }

    private static double emitEnergy(double energyToSend, PoweredMachineTileEntity fromTile) {

        if (energyToSend < 0) {
            throw new IllegalArgumentException(
                "Trying to extract negative energy. How did this even happen. " + fromTile);
        }

        if (energyToSend == 0) {
            return 0;
        }

        List<PoweredMachineTileEntity> acceptors = new ArrayList<>();
        int totalAcceptors = 0;

        for (Direction side : Direction.values()) {

            // get tile entity relative to fromTile
            TileEntity tileEntity = Objects.requireNonNull(fromTile.getWorld())
                .getTileEntity(fromTile.getPos().offset(side));
            if (validEnergyAcceptor(tileEntity)) {
                totalAcceptors++;
                acceptors.add((PoweredMachineTileEntity) tileEntity);
            }
        }

        if (totalAcceptors > 0) {
            double energyForAcceptors = energyToSend / totalAcceptors;
            acceptors.forEach(acceptor -> {
                emitToAcceptor(acceptor, energyForAcceptors);
            });

            return energyToSend;
        }

        return 0;
    }

    public static void emitToAcceptor(PoweredMachineTileEntity tileEntity, double energy) {
        tileEntity.injectEnergy(energy);
    }

    public static boolean validEnergyAcceptor(TileEntity tileEntity) {
        return tileEntity instanceof PoweredMachineTileEntity
            && ((PoweredMachineTileEntity) tileEntity).validAcceptor();
    }

    public enum EnergyUnit {
        JOULE(1D), KILOJOULE(1_000D), MEGAJOULE(1_000_000D), GIGAJOULE(1_000_000_000D), TERAJOULE(
            1_000_000_000_000D), PETAJOULE(1_000_000_000_000_000D),
        ;

        private final double factor;

        EnergyUnit(double factor) {
            this.factor = factor;
        }

        public double getFactor() {
            return factor;
        }
    }

    public static double convertEnergyUnit(double energy, EnergyUnit from, EnergyUnit to) {
        return energy * from.getFactor() / to.getFactor();
    }

    public static double extractEnergyFromItem(ItemStack stack, double energy) {

        LazyOptional<ICraftleEnergyStorage> items = stack
            .getCapability(Capabilities.ENERGY_CAPABILITY);

        if (stack.getCapability(Capabilities.ENERGY_CAPABILITY).isPresent()) {

            CraftleEnergyStorage container = (CraftleEnergyStorage) items
                .orElse(CraftleEnergyStorage.EMPTY_IE);

            if (container.canExtract()) {
                return container.extractEnergy(energy);
            }
        }

        return 0D;
    }

    public static double injectEnergyToItem(ItemStack stack, double energy) {

        LazyOptional<ICraftleEnergyStorage> items = stack
            .getCapability(Capabilities.ENERGY_CAPABILITY);

        if (stack.getCapability(Capabilities.ENERGY_CAPABILITY).isPresent()) {

            CraftleEnergyStorage container = (CraftleEnergyStorage) items
                .orElse(CraftleEnergyStorage.EMPTY_IE);

            if (container.canInject()) {
                return container.injectEnergy(energy);
            }
        }

        return 0;
    }

    public static double getEnergyRequiredForItem(ItemStack stack) {

        LazyOptional<ICraftleEnergyStorage> items = stack
            .getCapability(Capabilities.ENERGY_CAPABILITY);

        if (stack.getCapability(Capabilities.ENERGY_CAPABILITY).isPresent()) {

            CraftleEnergyStorage container = (CraftleEnergyStorage) items
                .orElse(CraftleEnergyStorage.EMPTY_IE);

            if (container.getCapacity() > 0) {
                return container.getCapacity() - container.getEnergy();
            }
        }

        return 0;
    }

    public static double getEnergyStoredFromItem(ItemStack stack) {

        LazyOptional<ICraftleEnergyStorage> items = stack
            .getCapability(Capabilities.ENERGY_CAPABILITY);

        if (stack.getCapability(Capabilities.ENERGY_CAPABILITY).isPresent()) {

            CraftleEnergyStorage container = (CraftleEnergyStorage) items
                .orElse(CraftleEnergyStorage.EMPTY_IE);

            if (!container.isEmpty()) {
                return container.getEnergy();
            }
        }

        return 0;
    }

    public static double getEnergyCapacityFromItem(ItemStack stack) {

        LazyOptional<ICraftleEnergyStorage> items = stack
            .getCapability(Capabilities.ENERGY_CAPABILITY);

        if (stack.getCapability(Capabilities.ENERGY_CAPABILITY).isPresent()) {

            CraftleEnergyStorage container = (CraftleEnergyStorage) items
                .orElse(CraftleEnergyStorage.EMPTY_IE);

            if (container.getCapacity() > 0) {
                return container.getCapacity();
            }
        }

        return 0;
    }

    public static double getEnergyPercentageFromItem(ItemStack stack) {

        LazyOptional<ICraftleEnergyStorage> items = stack
            .getCapability(Capabilities.ENERGY_CAPABILITY);

        if (stack.getCapability(Capabilities.ENERGY_CAPABILITY).isPresent()) {

            CraftleEnergyStorage container = (CraftleEnergyStorage) items
                .orElse(CraftleEnergyStorage.EMPTY_IE);

            if (container.getCapacity() > 0) {
                return container.getEnergy() / container.getCapacity();
            }
        }

        return 0.0D;
    }

    public static String getUnitForTierItem(CraftleBaseTier tier) {

        switch (tier) {
            case UNLIMITED:
            case TIER_4:
            case TIER_3:
                return UnitConstants.GIGAJOULES;
            case TIER_2:
            case TIER_1:
                return UnitConstants.MEGAJOULES;
            case BASIC:
            default:
                return UnitConstants.KILOJOULES;
        }
    }

    public static String getUnitForTierBlock(CraftleBaseTier tier) {

        switch (tier) {
            case UNLIMITED:
            case TIER_4:
                return UnitConstants.TERAJOULES;
            case TIER_3:
            case TIER_2:
                return UnitConstants.GIGAJOULES;
            case TIER_1:
            case BASIC:
            default:
                return UnitConstants.MEGAJOULES;
        }
    }

    public static double getJoulesForTierItem(CraftleBaseTier tier, double energy) {

        switch (tier) {
            case UNLIMITED:
            case TIER_4:
            case TIER_3:
                return convertEnergyUnit(energy, EnergyUnit.KILOJOULE, EnergyUnit.GIGAJOULE);
            case TIER_2:
            case TIER_1:
                return convertEnergyUnit(energy, EnergyUnit.KILOJOULE, EnergyUnit.MEGAJOULE);
            case BASIC:
            default:
                return energy;
        }
    }

    public static double getJoulesForTierBlock(CraftleBaseTier tier, double energy) {

        switch (tier) {
            case UNLIMITED:
            case TIER_4:
                return convertEnergyUnit(energy, EnergyUnit.KILOJOULE, EnergyUnit.TERAJOULE);
            case TIER_3:
            case TIER_2:
                return convertEnergyUnit(energy, EnergyUnit.KILOJOULE, EnergyUnit.GIGAJOULE);
            case TIER_1:
            case BASIC:
            default:
                return convertEnergyUnit(energy, EnergyUnit.KILOJOULE, EnergyUnit.MEGAJOULE);
        }
    }

}
