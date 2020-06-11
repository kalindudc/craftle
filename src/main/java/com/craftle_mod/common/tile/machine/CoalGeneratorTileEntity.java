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
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CoalGeneratorTileEntity extends PoweredMachineTileEntity {

    private int burnTime;
    private int totalBurnTime;

    public CoalGeneratorTileEntity(TileEntityType<?> typeIn,
                                   IRecipeType<? extends IRecipe> recipeTypeIn,
                                   CraftleBaseTier tier, int capacity, int maxReceive,
                                   int maxExtract) {
        super(typeIn, recipeTypeIn, 1, tier, capacity, maxReceive, maxExtract);
        this.burnTime      = 0;
        this.totalBurnTime = 0;
    }

    public CoalGeneratorTileEntity(TileEntityType<?> typeIn,
                                   IRecipeType<? extends IRecipe> recipeTypeIn,
                                   CraftleBaseTier tier) {
        super(typeIn, recipeTypeIn, 1, tier,
              (int) (TileEntityConstants.COAL_GENERATOR_BASE_CAPACITY * tier.getMultiplier()),
              (int) (TileEntityConstants.COAL_GENERATOR_BASE_MAX_INPUT * tier.getMultiplier()),
              (int) (TileEntityConstants.COAL_GENERATOR_BASE_MAX_OUTPUT * tier.getMultiplier()) *
              2);
        this.burnTime      = 0;
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
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return LazyOptional.of(() -> (T) this.getItemHandler());
        return super.getCapability(capability, side);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, Direction direction) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ||
               super.hasCapability(capability, direction);
    }

    @Override
    public Container createMenu(int id, PlayerInventory player) {

        CoalGeneratorContainer container =
                new CoalGeneratorContainer(CraftleContainerTypes.COAL_GENERATOR.get(), id, player,
                                           this);
        return container;
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

    @Override
    public CompoundNBT write(CompoundNBT compound) {

        super.write(compound);

        compound.putInt(NBTConstants.GENERATOR_CURRENT_BURN_TIME, this.burnTime);
        compound.putInt(NBTConstants.GENERATOR_TOTAL_BURN_TIME, this.totalBurnTime);

        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {

        super.read(compound);

        int burnTime      = compound.getInt(NBTConstants.GENERATOR_CURRENT_BURN_TIME);
        int totalBurnTime = compound.getInt(NBTConstants.GENERATOR_TOTAL_BURN_TIME);

        this.burnTime      = burnTime;
        this.totalBurnTime = totalBurnTime;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return super.getUpdatePacket();

    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        write(nbt);
        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
        super.onDataPacket(net, pkt);
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(TagConstants.COAL_GENERATOR);
    }

    public int getBurnPercentage() {
        return 100 - (int) ((((float) this.burnTime) / ((float) this.totalBurnTime)) * 100);
    }

    @Override
    public void tick() {
        if (this.getEnergyContainer().getEnergyStored() <
            this.getEnergyContainer().getMaxEnergyStored()) {

            if (this.getBufferedEnergy() > 0) {
                burnTime--;
                if (burnTime % TileEntityConstants.COAL_GENERATOR_BURN_MULTIPLIER == 0) {
                    int energyToIncrement = this.getEnergyReceive();
                    this.getEnergyContainer().receiveEnergy(energyToIncrement);
                    this.decrementBufferedEnergy(energyToIncrement);

                    if (this.getBufferedEnergy() < this.getEnergyReceive()) {
                        this.setEnergyReceive(this.getBufferedEnergy());
                    }
                    super.setBlockActive(true);
                }
            }
            else {
                this.resetBufferedEnergy();
                this.burnTime      = 0;
                this.totalBurnTime = 0;

                if (this.getTileEntityItems() != null &&
                    !this.getTileEntityItems().getStackInSlot(0).isEmpty() &&
                    isItemFuel(this.getTileEntityItems().getStackInSlot(0))) {

                    int energyToBuffer = getFuelValue(this.getTileEntityItems().getStackInSlot(0));
                    this.addToBufferedEnergy(energyToBuffer);
                    if (energyToBuffer > this.getEnergyContainer().getMaxReceive()) {
                        this.setEnergyReceive(this.getEnergyContainer().getMaxReceive());
                        this.burnTime      = (int) Math
                                .ceil(energyToBuffer / this.getEnergyContainer().getMaxReceive()) *
                                             TileEntityConstants.COAL_GENERATOR_BURN_MULTIPLIER;
                        this.totalBurnTime = this.burnTime;
                    }
                    else {
                        this.setEnergyReceive(energyToBuffer);
                        this.burnTime = 1;
                    }
                    this.getTileEntityItems().getStackInSlot(0).shrink(1);
                }
                else {
                    this.resetBufferedEnergy();
                    this.burnTime      = 0;
                    this.totalBurnTime = 0;
                    if (active)
                        super.setBlockActive(false);
                }

            }
        }
        else {
            super.setBlockActive(false);
            this.resetBufferedEnergy();
            this.burnTime      = 0;
            this.totalBurnTime = 0;
        }
    }

    private int getFuelValue(ItemStack stackInSlot) {

        if (Items.COAL.equals(stackInSlot.getItem()) ||
            Items.CHARCOAL.equals(stackInSlot.getItem())) {
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


}
