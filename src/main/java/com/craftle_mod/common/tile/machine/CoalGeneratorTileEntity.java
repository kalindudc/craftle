package com.craftle_mod.common.tile.machine;

import com.craftle_mod.api.ContainerConstants;
import com.craftle_mod.api.CraftleExceptions.CraftleTileEntityException;
import com.craftle_mod.api.NBTConstants;
import com.craftle_mod.api.TagConstants;
import com.craftle_mod.api.TileEntityConstants;
import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.machine.CoalGenerator;
import com.craftle_mod.common.inventory.container.machine.CoalGeneratorContainer;
import com.craftle_mod.common.inventory.slot.SlotConfig;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import com.craftle_mod.common.util.EnergyUtils;
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
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class CoalGeneratorTileEntity extends PoweredMachineTileEntity {

    protected final IIntArray generatorData = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return CoalGeneratorTileEntity.this.burnTime;
                case 1:
                    return CoalGeneratorTileEntity.this.totalBurnTime;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    CoalGeneratorTileEntity.this.burnTime = value;
                    break;
                case 1:
                    CoalGeneratorTileEntity.this.totalBurnTime = value;
                    break;
            }

        }

        public int size() {
            return 2;
        }
    };

    private int burnTime;
    private int totalBurnTime;

    public CoalGeneratorTileEntity(CoalGenerator block,
        IRecipeType<? extends IRecipe<?>> recipeTypeIn, CraftleBaseTier tier, double capacity,
        double maxReceive, double maxExtract) {
        super(block, recipeTypeIn, 1, tier, capacity, maxReceive, maxExtract);
        this.burnTime = 0;
        this.totalBurnTime = 0;

        addSlotData(new SlotConfig(1, 1, this, 0, 80, 20, ContainerConstants.TOTAL_SLOT_SIZE));
    }

    public CoalGeneratorTileEntity(CoalGenerator block,
        IRecipeType<? extends IRecipe<?>> recipeTypeIn, CraftleBaseTier tier) {
        this(block, recipeTypeIn, tier,
            (TileEntityConstants.COAL_GENERATOR_BASE_CAPACITY * tier.getMultiplier()),
            (TileEntityConstants.COAL_GENERATOR_BASE_MAX_INPUT * tier.getMultiplier()),
            (TileEntityConstants.COAL_GENERATOR_BASE_MAX_OUTPUT * tier.getMultiplier() * 1.5));
    }

    public CoalGeneratorTileEntity() {
        this((CoalGenerator) CraftleBlocks.COAL_GENERATOR.get(), CraftleRecipeType.SMELTING,
            CraftleBaseTier.BASIC);
    }

    @Nonnull
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory player) {

        return new CoalGeneratorContainer(getBlock().getContainerType(), id, player, this,
            generatorData);
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

        // emit energy
        if (getEnergyContainer().getEnergy() > 0) {
            this.setEnergyExtractRate(EnergyUtils.emitEnergy(getEnergyContainer(), this,
                getEnergyContainer().getMaxExtractRate() / TileEntityConstants.TICKER_PER_SECOND));
        } else {
            this.setEnergyExtractRate(0);
        }
    }

    @Override
    protected void tickClient() {

//        Craftle.logInfo("Coal Generator client: %f %f", this.getEnergyContainer().getEnergy(),
//            this.getBufferedEnergy());

    }

    private void setupFuel() {

        if (getBufferedEnergy() > this.getEnergyContainer().getMaxInjectRate()) {

            this.setEnergyInjectRate(this.getEnergyContainer().getMaxInjectRate()
                / TileEntityConstants.TICKER_PER_SECOND);
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

    @Override
    public boolean validAcceptor() {
        return false;
    }
}
