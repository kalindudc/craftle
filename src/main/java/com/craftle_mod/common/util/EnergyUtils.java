package com.craftle_mod.common.util;

import com.craftle_mod.api.UnitConstants;
import com.craftle_mod.common.capability.energy.EnergyContainerCapability;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class EnergyUtils {

    public static float joulesToKiloJoules(float energy) {
        return energy / 1000f;
    }

    public static float joulesToMegaJoules(float energy) {
        return joulesToKiloJoules(energy) / 1000f;
    }

    public static float joulesToGigaJoules(float energy) {
        return joulesToMegaJoules(energy) / 1000f;
    }

    public static float joulesToTeraJoules(float energy) {
        return joulesToGigaJoules(energy) / 1000f;
    }

    public static float joulesToPetaJoules(float energy) {
        return joulesToTeraJoules(energy) / 1000f;
    }

    public static float kiloJoulesToMegaJoules(float energy) {
        return energy / 1000f;
    }

    public static float kiloJoulesToGigaJoules(float energy) {
        return kiloJoulesToMegaJoules(energy) / 1000f;
    }

    public static float kiloJoulesToTeraJoules(float energy) {
        return kiloJoulesToGigaJoules(energy) / 1000f;
    }

    public static float kiloJoulesToPetaJoules(float energy) {
        return kiloJoulesToTeraJoules(energy) / 1000f;
    }

    public static float megaJoulesToGigaJoules(float energy) {
        return energy / 1000f;
    }

    public static float megaJoulesToTeraJoules(float energy) {
        return megaJoulesToGigaJoules(energy) / 1000f;
    }

    public static float megaJoulesToPetaJoules(float energy) {
        return megaJoulesToTeraJoules(energy) / 1000f;
    }

    public static float gigaJoulesToTeraJoules(float energy) {
        return energy / 1000f;
    }

    public static float gigaJoulesToPetaJoules(float energy) {
        return gigaJoulesToTeraJoules(energy) / 1000f;
    }

    public static float teraJoulesToPetaJoules(float energy) {
        return energy / 1000f;
    }

    public static float petaJoulesToTeraJoules(float energy) {
        return energy * 1000f;
    }

    public static float petaJoulesToGigaJoules(float energy) {
        return petaJoulesToTeraJoules(energy) * 1000f;
    }

    public static float petaJoulesToMegaJoules(float energy) {
        return petaJoulesToGigaJoules(energy) * 1000f;
    }

    public static float petaJoulesToKiloJoules(float energy) {
        return petaJoulesToMegaJoules(energy) * 1000f;
    }

    public static float petaJoulesToJoules(float energy) {
        return petaJoulesToKiloJoules(energy) * 1000f;
    }

    public static float teraJoulesToGigaJoules(float energy) {
        return energy * 1000f;
    }

    public static float teraJoulesToMegaJoules(float energy) {
        return teraJoulesToGigaJoules(energy) * 1000f;
    }

    public static float teraJoulesToKiloJoules(float energy) {
        return teraJoulesToMegaJoules(energy) * 1000f;
    }

    public static float teraJoulesToJoules(float energy) {
        return teraJoulesToKiloJoules(energy) * 1000f;
    }

    public static float gigaJoulesToMegaJoules(float energy) {
        return energy * 1000f;
    }

    public static float gigaJoulesToKiloJoules(float energy) {
        return gigaJoulesToMegaJoules(energy) * 1000f;
    }

    public static float gigaJoulesToJoules(float energy) {
        return gigaJoulesToKiloJoules(energy) * 1000f;
    }

    public static float megaJoulesToKiloJoules(float energy) {
        return energy * 1000f;
    }

    public static float megaJoulesToJoules(float energy) {
        return megaJoulesToKiloJoules(energy) * 1000f;
    }

    public static float kiloJoulesToJoules(float energy) {
        return energy * 1000f;
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

    public static float getJoulesForTierItem(CraftleBaseTier tier, int energy) {

        switch (tier) {
            case UNLIMITED:
            case TIER_4:
            case TIER_3:
                return kiloJoulesToGigaJoules(energy);
            case TIER_2:
            case TIER_1:
                return kiloJoulesToMegaJoules(energy);
            case BASIC:
            default:
                return energy;
        }
    }

    public static float getJoulesForTierBlock(CraftleBaseTier tier, int energy) {

        switch (tier) {
            case UNLIMITED:
            case TIER_4:
                return kiloJoulesToTeraJoules(energy);
            case TIER_3:
            case TIER_2:
                return kiloJoulesToGigaJoules(energy);
            case TIER_1:
            case BASIC:
            default:
                return kiloJoulesToMegaJoules(energy);
        }
    }

}
