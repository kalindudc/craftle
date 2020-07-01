package com.craftle_mod.common.util;

import com.craftle_mod.api.UnitConstants;
import com.craftle_mod.common.capability.energy.EnergyContainerCapability;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class EnergyUtils {

    public enum EnergyUnit {
        JOULE(1D),
        KILOJOULE(1_000D),
        MEGAJOULE(1_000_000D),
        GIGAJOULE(1_000_000_000D),
        TERAJOULE(1_000_000_000_000D),
        PETAJOULE(1_000_000_000_000_000D),
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

    public static int extractEnergyFromItem(ItemStack stack, int energy) {

        LazyOptional<IEnergyStorage> items = stack.getCapability(CapabilityEnergy.ENERGY);

        if (stack.getCapability(CapabilityEnergy.ENERGY).isPresent()) {

            EnergyContainerCapability container = (EnergyContainerCapability) items
                .orElse(EnergyContainerCapability.EMPTY_IE);

            if (container.canExtract() && container.getEnergyStored() > 0) {
                return container.extractEnergy(energy);
            }
        }

        return 0;
    }

    public static int injectEnergyToItem(ItemStack stack, int energy) {

        LazyOptional<IEnergyStorage> items = stack.getCapability(CapabilityEnergy.ENERGY);

        if (stack.getCapability(CapabilityEnergy.ENERGY).isPresent()) {

            EnergyContainerCapability container = (EnergyContainerCapability) items
                .orElse(EnergyContainerCapability.EMPTY_IE);

            if (container.canReceive()) {
                return container.receiveEnergy(energy);
            }
        }

        return 0;
    }

    public static int getEnergyRequiredForItem(ItemStack stack) {

        LazyOptional<IEnergyStorage> items = stack.getCapability(CapabilityEnergy.ENERGY);

        if (stack.getCapability(CapabilityEnergy.ENERGY).isPresent()) {

            EnergyContainerCapability container = (EnergyContainerCapability) items
                .orElse(EnergyContainerCapability.EMPTY_IE);

            if (container.getMaxEnergyStored() > 0) {
                return container.getMaxEnergyStored() - container.getEnergyStored();
            }
        }

        return 0;
    }

    public static int getEnergyStoredFromItem(ItemStack stack) {

        LazyOptional<IEnergyStorage> items = stack.getCapability(CapabilityEnergy.ENERGY);

        if (stack.getCapability(CapabilityEnergy.ENERGY).isPresent()) {

            EnergyContainerCapability container = (EnergyContainerCapability) items
                .orElse(EnergyContainerCapability.EMPTY_IE);

            if (container.getEnergyStored() > 0) {
                return container.getEnergyStored();
            }
        }

        return 0;
    }

    public static int getEnergyCapacityFromItem(ItemStack stack) {

        LazyOptional<IEnergyStorage> items = stack.getCapability(CapabilityEnergy.ENERGY);

        if (stack.getCapability(CapabilityEnergy.ENERGY).isPresent()) {

            EnergyContainerCapability container = (EnergyContainerCapability) items
                .orElse(EnergyContainerCapability.EMPTY_IE);

            if (container.getMaxEnergyStored() > 0) {
                return container.getMaxEnergyStored();
            }
        }

        return 0;
    }

    public static double getEnergyPercentageFromItem(ItemStack stack) {

        LazyOptional<IEnergyStorage> items = stack.getCapability(CapabilityEnergy.ENERGY);

        if (stack.getCapability(CapabilityEnergy.ENERGY).isPresent()) {

            EnergyContainerCapability container = (EnergyContainerCapability) items
                .orElse(EnergyContainerCapability.EMPTY_IE);

            if (container.getMaxEnergyStored() > 0) {
                return ((double) container.getEnergyStored()) /
                    ((double) container.getMaxEnergyStored());
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
