package com.craftle_mod.common.tile.machine;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.inventory.container.machine.coal_generator.CoalGeneratorContainer;
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

    private final static int BASE_CAPACITY   = 100_000;
    private final static int BASE_MAX_INPUT  = (int) (BASE_CAPACITY * 0.05);
    private final static int BASE_MAX_OUTPUT = BASE_MAX_INPUT;

    private String customName;
    private int    cookTime;


    public CoalGeneratorTileEntity(TileEntityType<?> typeIn,
                                   IRecipeType<? extends IRecipe> recipeTypeIn,
                                   CraftleBaseTier tier, int capacity,
                                   int maxReceive, int maxExtract) {
        super(typeIn, recipeTypeIn, 1, tier, capacity, maxReceive, maxExtract);
        this.cookTime = 0;
    }

    public CoalGeneratorTileEntity(TileEntityType<?> typeIn,
                                   IRecipeType<? extends IRecipe> recipeTypeIn,
                                   CraftleBaseTier tier) {
        super(typeIn, recipeTypeIn, 1, tier,
              (int) (BASE_CAPACITY * tier.getMultiplier()),
              (int) (BASE_MAX_INPUT * tier.getMultiplier()),
              (int) (BASE_MAX_OUTPUT * tier.getMultiplier()));
        this.cookTime = 0;
    }

    public CoalGeneratorTileEntity() {
        this(CraftleTileEntityTypes.COAL_GENERATOR.get(),
             CraftleRecipeType.SMELTING, CraftleBaseTier.BASIC);
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
    public boolean hasCapability(Capability<?> capability,
                                 Direction direction) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ||
               super.hasCapability(capability, direction);
    }

    @Override
    public Container createMenu(int id, PlayerInventory player) {

        CoalGeneratorContainer container = new CoalGeneratorContainer(
                CraftleContainerTypes.COAL_GENERATOR.get(), id, player, this);

        Craftle.logInfo("createMenu() %d %d %d", container.getEnergy(),
                        ((PoweredMachineTileEntity) container.getEntity())
                                .getEnergyContainer().getEnergyStored(),
                        this.getEnergyContainer().getEnergyStored());

        return container;
    }

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {

        CompoundNBT superCompound = super.write(compound);

        compound.putInt("BufferedEnergy", this.getBufferedEnergy());
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("GuiEnergy",
                        this.getEnergyContainer().getEnergyStored());
        compound.putString("Name", getDisplayName().toString());

        this.getEnergyContainer().writeToNBT(compound);

        Craftle.LOGGER.info(String.format(
                "CRAFTLE: writing NBT Entity. " + this.hashCode() + " %d",
                this.getEnergyContainer().getEnergyStored()));
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {

        Craftle.LOGGER.info(String.format("CRAFTLE: reading NBT Entity."));

        super.read(compound);

        this.getEnergyContainer().readFromNBT(compound);


        int    bufferedEnergy = compound.getInt("BufferedEnergy");
        int    cookTime       = compound.getInt("CookTime");
        int    guiEnergy      = compound.getInt("GuiEnergy");
        String name           = compound.getString("Name");

        Craftle.LOGGER.info(String.format("CRAFTLE: new GUI ENERGY %d %s",
                                          this.getEnergyContainer()
                                              .getEnergyStored(),
                                          this.customName));

        if (bufferedEnergy > 0) this.setBufferedEnergy(bufferedEnergy);
        if (cookTime > 0) this.cookTime = cookTime;
        if (guiEnergy > 0) this.getEnergyContainer().setEnergy(guiEnergy);
        if (name != null && !name.equalsIgnoreCase("")) this.customName =
                customName;

        Craftle.LOGGER.info(String.format("CRAFTLE: new GUI ENERGY %d %d %d",
                                          bufferedEnergy, cookTime, guiEnergy,
                                          name));


        Craftle.LOGGER.info(String.format("CRAFTLE: new energy %d %s",
                                          this.getEnergyContainer()
                                              .getEnergyStored(),
                                          this.customName));
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return super.getUpdatePacket();

    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        write(nbt);
        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.coal_generator");
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    @Override
    public void tick() {
        boolean log = false;
        if (log) Craftle.LOGGER
                .info(String.format("CRAFTLE: current energy in tick() %d.",
                                    this.getEnergyContainer()
                                        .getEnergyStored()));


        if (this.getEnergyContainer().getEnergyStored() <
            this.getEnergyContainer().getMaxEnergyStored()) {
            cookTime++;
            if (log) Craftle.LOGGER
                    .info(String.format("CRAFTLE: generator not full " + "%d.",
                                        this.getEnergyContainer()
                                            .getEnergyStored()));

            if (this.getBufferedEnergy() > 0) {

                int energyToIncrement =
                        this.getBufferedEnergy() / (200 - cookTime);
                this.getEnergyContainer().incrementEnergy(energyToIncrement);
                this.decrementBufferedEnergy(energyToIncrement);
                super.setBlockActive(true);
                if (log) Craftle.LOGGER.info(String.format(
                        "CRAFTLE: energy in buffer %d, " +
                        "energy to increment %d, cooktime" + " %d.",
                        this.getBufferedEnergy(), energyToIncrement,
                        this.cookTime));
            }
            else {
                super.setBlockActive(false);
                this.resetBufferedEnergy();
                this.cookTime = 0;
                if (log) Craftle.LOGGER.info(String.format(
                        "CRAFTLE: energy is less than 1 %d.",
                        this.getBufferedEnergy()));

                if (this.getTileEntityItems() != null &&
                    !this.getTileEntityItems().getStackInSlot(0).isEmpty() &&
                    isItemFuel(this.getTileEntityItems().getStackInSlot(0))) {

                    int energyToBuffer = getFuelValue(
                            this.getTileEntityItems().getStackInSlot(0));
                    this.addToBufferedEnergy(energyToBuffer);
                    this.getTileEntityItems().getStackInSlot(0).shrink(1);

                    if (log) Craftle.LOGGER.info(String.format(
                            "CRAFTLE: energy to buffer %d, buffer.",
                            energyToBuffer, this.getBufferedEnergy()));
                }

            }
        }
        else {
            super.setBlockActive(false);
            this.resetBufferedEnergy();
            this.cookTime = 0;
        }
    }

    private int getFuelValue(ItemStack stackInSlot) {

        if (Items.COAL.equals(stackInSlot.getItem()) ||
            Items.CHARCOAL.equals(stackInSlot.getItem())) {
            return 1000;
        }

        if (Items.COAL_BLOCK.equals(stackInSlot.getItem())) {
            return (int) (1000 * 9 * 1.25);
        }

        if (Items.COAL_BLOCK.equals(stackInSlot.getItem())) {
            return (int) (1000 * 9 * 1.25);
        }

        return 0;
    }


    private boolean isItemFuel(ItemStack stackInSlot) {
        return getFuelValue(stackInSlot) > 0;
    }


}
