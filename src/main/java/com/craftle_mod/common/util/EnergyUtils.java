package com.craftle_mod.common.util;

import com.craftle_mod.common.capability.Capabilities;
import com.craftle_mod.common.capability.energy.CraftleEnergyStorage;
import com.craftle_mod.common.capability.energy.ICraftleEnergyStorage;
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

            int accepted = totalAcceptors;
            double energySent = 0;

            for (PoweredMachineTileEntity acceptor : acceptors) {
                double energy = emitToAcceptor(acceptor, (energyToSend - energySent) / accepted);
                accepted--;
                energySent += energy;
            }
            return energySent;
        }

        return 0;
    }

    public static boolean canResetInjectionRate(PoweredMachineTileEntity tile) {
        for (Direction side : Direction.values()) {

            // get tile entity relative to fromTile
            TileEntity tileEntity = Objects.requireNonNull(tile.getWorld())
                .getTileEntity(tile.getPos().offset(side));
            if (tileEntity instanceof PoweredMachineTileEntity) {
                if (((PoweredMachineTileEntity) tileEntity).canEmitEnergy()) {
                    return true;
                }
            }
        }

        return false;
    }

    public static double emitToAcceptor(PoweredMachineTileEntity tileEntity, double energy) {

        // directly inject energy (can be generalized for forge energy as well)
        return tileEntity.getEnergyContainer().injectEnergy(energy);
    }

    public static boolean validEnergyAcceptor(TileEntity tileEntity) {
        return tileEntity instanceof PoweredMachineTileEntity
            && ((PoweredMachineTileEntity) tileEntity).validAcceptor();
    }

    public enum EnergyUnit {
        JOULE(1D, "J"),
        KILOJOULE(1_000D, "kJ"),
        MEGAJOULE(1_000_000D, "MJ"),
        GIGAJOULE(1_000_000_000D, "GJ"),
        TERAJOULE(1_000_000_000_000D, "TJ"),
        PETAJOULE(1_000_000_000_000_000D, "PJ");

        private final double factor;
        private final String unit;

        EnergyUnit(double factor, String unit) {
            this.factor = factor;
            this.unit = unit;
        }

        public double getFactor() {
            return factor;
        }

        public String getUnit() {
            return unit;
        }

        public static double convertEnergyUnit(double energy, EnergyUnit from, EnergyUnit to) {
            return energy * from.getFactor() / to.getFactor();
        }
    }

    public static class EnergyConverter {

        private double energy;
        private String unit;

        public EnergyConverter(double energy) {
            convert(energy);
        }

        private void convert(double energy) {

            if (energy / EnergyUnit.PETAJOULE.getFactor() > 1) {
                this.energy = energy / EnergyUnit.PETAJOULE.getFactor();
                this.unit = EnergyUnit.PETAJOULE.getUnit();
            } else if (energy / EnergyUnit.TERAJOULE.getFactor() > 1) {
                this.energy = energy / EnergyUnit.TERAJOULE.getFactor();
                this.unit = EnergyUnit.TERAJOULE.getUnit();
            } else if (energy / EnergyUnit.GIGAJOULE.getFactor() > 1) {
                this.energy = energy / EnergyUnit.GIGAJOULE.getFactor();
                this.unit = EnergyUnit.GIGAJOULE.getUnit();
            } else if (energy / EnergyUnit.MEGAJOULE.getFactor() > 1) {
                this.energy = energy / EnergyUnit.MEGAJOULE.getFactor();
                this.unit = EnergyUnit.MEGAJOULE.getUnit();
            } else if (energy / EnergyUnit.KILOJOULE.getFactor() > 1) {
                this.energy = energy / EnergyUnit.KILOJOULE.getFactor();
                this.unit = EnergyUnit.KILOJOULE.getUnit();
            } else {
                this.energy = energy;
                this.unit = EnergyUnit.JOULE.getUnit();
            }
        }

        public double getEnergy() {
            return energy;
        }

        public String getUnit() {
            return unit;
        }
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
}
