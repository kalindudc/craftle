package com.craftle_mod.common.tile.machine;

import com.craftle_mod.api.NBTConstants;
import com.craftle_mod.api.TagConstants;
import com.craftle_mod.api.TileEntityConstants;
import com.craftle_mod.common.inventory.container.machine.CoalGeneratorContainer;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.registries.CraftleContainerTypes;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class CoalGeneratorTileEntity extends PoweredMachineTileEntity {

    private int burnTime;
    private int totalBurnTime;

    public CoalGeneratorTileEntity(TileEntityType<?> typeIn,
        IRecipeType<? extends IRecipe<?>> recipeTypeIn, CraftleBaseTier tier, int capacity,
        int maxReceive, int maxExtract) {
        super(typeIn, recipeTypeIn, 1, tier, capacity, maxReceive, maxExtract);
        this.burnTime = 0;
        this.totalBurnTime = 0;
    }

    public CoalGeneratorTileEntity(TileEntityType<?> typeIn,
        IRecipeType<? extends IRecipe<?>> recipeTypeIn, CraftleBaseTier tier) {
        super(typeIn, recipeTypeIn, 1, tier,
            (int) (TileEntityConstants.COAL_GENERATOR_BASE_CAPACITY * tier.getMultiplier()),
            (int) (TileEntityConstants.COAL_GENERATOR_BASE_MAX_INPUT * tier.getMultiplier()),
            (int) (TileEntityConstants.COAL_GENERATOR_BASE_MAX_OUTPUT * tier.getMultiplier()) * 2);
        this.burnTime = 0;
        this.totalBurnTime = 0;
    }

    public CoalGeneratorTileEntity() {
        this(CraftleTileEntityTypes.COAL_GENERATOR.get(), CraftleRecipeType.SMELTING,
            CraftleBaseTier.BASIC);
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

    @Nonnull
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory player) {

        return new CoalGeneratorContainer(CraftleContainerTypes.COAL_GENERATOR.get(), id, player,
            this);
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

    public int getBurnPercentage() {
        if (burnTime == 0 || totalBurnTime == 0) {
            return 0;
        }
        return 100 - (int) ((((float) this.burnTime) / ((float) this.totalBurnTime)) * 100);
    }

    @Override
    protected void tickServer() {
        /*
        // emit energy
        if (getEnergyContainer().getEnergy() > 0) {
            EnergyUtils
                .emitEnergy(getEnergyContainer(), this, getEnergyContainer().getMaxExtract());
        }
         */

        // burn fuel
        boolean notFilled =
            this.getEnergyContainer().getEnergy() < this.getEnergyContainer().getCapacity();

        if (notFilled) {

            if (this.getBufferedEnergy() > 0) {
                burn();
            } else {
                ItemStack stack = this.getContainerContents().get(0);

                if (isItemFuel(stack)) {

                    int energyToBuffer = getFuelValue(stack);
                    this.addToBufferedEnergy(energyToBuffer);
                    if (energyToBuffer > this.getEnergyContainer().getMaxInjectRate()) {
                        this.setEnergyReceive(this.getEnergyContainer().getMaxInjectRate());
                        this.burnTime = (int) Math.ceil(
                            (float) energyToBuffer / (float) this.getEnergyContainer()
                                .getMaxInjectRate())
                            * TileEntityConstants.COAL_GENERATOR_BURN_MULTIPLIER;
                        this.totalBurnTime = this.burnTime;
                    } else {
                        this.setEnergyReceive(energyToBuffer);
                        this.burnTime = 1;
                        this.totalBurnTime = 1;
                    }
                    stack.shrink(1);
                    burn();
                } else {
                    this.resetBufferedEnergy();
                    this.burnTime = 0;
                    this.totalBurnTime = 0;
                    super.setBlockActive(false);
                }

            }
//            Craftle.logInfo("Coal Generator server: %f %f", this.getEnergyContainer().getEnergy(),
//                this.getBufferedEnergy());
        }

        if (!notFilled) {

            super.setBlockActive(false);
            this.resetBufferedEnergy();
            this.burnTime = 0;
            this.totalBurnTime = 0;
            this.markDirty();
        }

        if (burnTime > 0) {
            this.markTileDirty();
        }
    }

    @Override
    protected void tickClient() {

//        Craftle.logInfo("Coal Generator client: %f %f", this.getEnergyContainer().getEnergy(),
//            this.getBufferedEnergy());

    }

    private void burn() {
        burnTime--;
        if (burnTime % TileEntityConstants.COAL_GENERATOR_BURN_MULTIPLIER == 0) {
            double energyToIncrement = this.getEnergyReceive();
            this.getEnergyContainer().injectEnergy(energyToIncrement);
            this.decrementBufferedEnergy(energyToIncrement);

            if (this.getBufferedEnergy() < this.getEnergyReceive()) {
                this.setEnergyReceive(this.getBufferedEnergy());
            }
            super.setBlockActive(true);
        }
    }

    private int getFuelValue(ItemStack stackInSlot) {

        if (Items.COAL.equals(stackInSlot.getItem()) || Items.CHARCOAL
            .equals(stackInSlot.getItem())) {
            return TileEntityConstants.COAL_FUEL_VALUE;
        }

        if (Items.COAL_BLOCK.equals(stackInSlot.getItem())) {
            return TileEntityConstants.COAL_BLOCK_FUEL_VALUE;
        }

        return 0;
    }


    private boolean isItemFuel(ItemStack stackInSlot) {
        return getFuelValue(stackInSlot) > 0;
    }

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
        CoalGeneratorTileEntity entity = (CoalGeneratorTileEntity) o;
        return super.equals(o) && burnTime == entity.burnTime
            && totalBurnTime == entity.totalBurnTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), burnTime, totalBurnTime);
    }
}
