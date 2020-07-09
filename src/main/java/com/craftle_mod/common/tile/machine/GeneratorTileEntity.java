package com.craftle_mod.common.tile.machine;

import com.craftle_mod.api.CraftleExceptions.CraftleTileEntityException;
import com.craftle_mod.api.constants.NBTConstants;
import com.craftle_mod.api.constants.TagConstants;
import com.craftle_mod.api.constants.TileEntityConstants;
import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.machine.CoalGenerator;
import com.craftle_mod.common.inventory.container.machine.GeneratorContainer;
import com.craftle_mod.common.inventory.slot.SlotConfigBuilder;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public abstract class GeneratorTileEntity extends PoweredMachineTileEntity {

    protected final IIntArray generatorData = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return GeneratorTileEntity.this.burnTime;
                case 1:
                    return GeneratorTileEntity.this.totalBurnTime;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    GeneratorTileEntity.this.burnTime = value;
                    break;
                case 1:
                    GeneratorTileEntity.this.totalBurnTime = value;
                    break;
            }

        }

        public int size() {
            return 2;
        }
    };

    private int burnTime;
    private int totalBurnTime;
    private double burnMultiplier;

    public GeneratorTileEntity(CoalGenerator block, IRecipeType<? extends IRecipe<?>> recipeTypeIn,
        CraftleBaseTier tier, double capacity, double maxReceive, double maxExtract) {
        super(block, recipeTypeIn, 1, tier, capacity, maxReceive, maxExtract);
        this.burnTime = 0;
        this.totalBurnTime = 0;
        this.burnMultiplier = 1;

        addSlotData(SlotConfigBuilder.create().inventory(this).startX(135).startY(20).build());
    }

    @Nonnull
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory player) {

        return new GeneratorContainer(getBlock().getContainerType(), id, player, this,
            generatorData);
    }

    public double getBurnMultiplier() {
        return burnMultiplier;
    }

    public void setBurnMultiplier(double burnMultiplier) {
        this.burnMultiplier = burnMultiplier;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability,
        @Nullable Direction side) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.getItemHandler().cast();
        }
        return super.getCapability(capability, side);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, Direction direction) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super
            .hasCapability(capability, direction);
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getTotalBurnTime() {
        return totalBurnTime;
    }

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {

        super.write(compound);

        compound.putInt(NBTConstants.GENERATOR_CURRENT_BURN_TIME, this.burnTime);
        compound.putInt(NBTConstants.GENERATOR_TOTAL_BURN_TIME, this.totalBurnTime);

        return compound;
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {

        super.read(compound);

        int burnTime = compound.getInt(NBTConstants.GENERATOR_CURRENT_BURN_TIME);
        int totalBurnTime = compound.getInt(NBTConstants.GENERATOR_TOTAL_BURN_TIME);

        this.burnTime = burnTime;
        this.totalBurnTime = totalBurnTime;
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(TagConstants.COAL_GENERATOR);
    }

    @Override
    protected void tickServer() {

        // burn fuel
        boolean notFilled =
            this.getEnergyContainer().getEnergy() < this.getEnergyContainer().getCapacity();

        if (notFilled) {

            if (this.getBufferedEnergy() > 0) {
                burn();
            } else {

                ItemStack stack = this.getContainerContents().get(0);
                if (isItemFuel(stack)) {

                    this.addToBufferedEnergy(getFuelValue(stack));
                    setupFuel();
                    stack.shrink(1);

                    // burn for this tick
                    burn();
                } else {
                    notFilled = false;
                }
            }
        }

        if (!notFilled && active) {
            resetGenerator();
        }

        if (burnTime > 0) {
            this.markTileDirty();
        }

        super.tickServer();
    }

    private void setupFuel() {

        double injectRate =
            this.getEnergyContainer().getMaxInjectRate() / (TileEntityConstants.TICKER_PER_SECOND
                * this.burnMultiplier);

        if (getBufferedEnergy() > injectRate) {

            this.setEnergyInjectRate(injectRate);
            this.burnTime = (int) Math.ceil(getBufferedEnergy() / this.getEnergyInjectRate());
            this.totalBurnTime = this.burnTime;
        } else {
            this.setEnergyInjectRate(getBufferedEnergy());
            this.burnTime = this.totalBurnTime = 1;
        }
    }

    private void resetGenerator() {

        super.setBlockActive(false);
        this.resetBufferedEnergy();
        this.burnTime = this.totalBurnTime = 0;
        this.markDirty();
    }

    private void burn() {
        burnTime--;

        if (burnTime < 0) {
            // something went wrong
            Craftle.LOGGER.warn("Coal Generator failed on burn",
                new CraftleTileEntityException("Coal Generator failed on burn"));
            resetGenerator();
            return;
        }

        double energyToIncrement = this.getBufferedEnergy() / burnTime;

        if (this.getBufferedEnergy() < energyToIncrement) {
            this.setEnergyInjectRate(this.getBufferedEnergy());
            energyToIncrement = this.getBufferedEnergy();
            burnTime = 0;
        }

        this.getEnergyContainer().injectEnergy(energyToIncrement);
        this.decrementBufferedEnergy(energyToIncrement);

        super.setBlockActive(true);

    }

    public abstract double getFuelValue(ItemStack stackInSlot);

    public abstract boolean isItemFuel(ItemStack stackInSlot);

    @Override
    public String toString() {
        return "CoalGeneratorTileEntity{" + "burnTime=" + burnTime + ", totalBurnTime="
            + totalBurnTime + "super=" + super.toString() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }
        GeneratorTileEntity entity = (GeneratorTileEntity) o;
        return super.equals(o) && burnTime == entity.burnTime
            && totalBurnTime == entity.totalBurnTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), burnTime, totalBurnTime);
    }

    @Override
    public boolean validAcceptor() {
        return false;
    }

    @Override
    public boolean canEmitEnergy() {
        return !getEnergyContainer().isEmpty();
    }
}
