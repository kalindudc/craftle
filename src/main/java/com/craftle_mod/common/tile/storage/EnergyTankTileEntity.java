package com.craftle_mod.common.tile.storage;

import com.craftle_mod.api.constants.TagConstants;
import com.craftle_mod.api.constants.TileEntityConstants;
import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.storage.EnergyTank;
import com.craftle_mod.common.inventory.slot.SlotConfig;
import com.craftle_mod.common.inventory.slot.SlotConfig.SlotType;
import com.craftle_mod.common.inventory.slot.SlotConfigBuilder;
import com.craftle_mod.common.network.packet.PowerdMachineUpdatePacket;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.IUpdateableTileEntity;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class EnergyTankTileEntity extends PoweredMachineTileEntity implements
    IUpdateableTileEntity {

    private final SlotConfig injectSlotConfig;
    private final SlotConfig extractSlotConfig;

    public EnergyTankTileEntity(EnergyTank block, CraftleBaseTier tier) {
        this(block, tier, 0);
    }

    public EnergyTankTileEntity(EnergyTank block, CraftleBaseTier tier, double energy) {
        super(block, CraftleRecipeType.CRAFTING, 2, tier,
            TileEntityConstants.ENERGY_MATRIX_BASE_CAPACITY * tier.getMultiplier(),
            TileEntityConstants.ENERGY_MATRIX_BASE_CAPACITY * tier.getMultiplier(), 0, energy);

        injectSlotConfig = SlotConfigBuilder.create().inventory(this).startX(135).startY(20)
            .slotType(SlotType.INJECT).build();
        extractSlotConfig = SlotConfigBuilder.create().inventory(this).startingIndex(1).startX(135)
            .startY(50).slotType(SlotType.EXTRACT).build();

        addSlotData(injectSlotConfig);
        addSlotData(extractSlotConfig);
    }

    @Override
    public boolean canEmitEnergy() {
        return !getEnergyContainer().isEmpty();
    }

    public SlotConfig getInjectSlotConfig() {
        return injectSlotConfig;
    }

    public SlotConfig getExtractSlotConfig() {
        return extractSlotConfig;
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

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {

        super.write(compound);

        return compound;
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {

        super.read(compound);
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(
            TagConstants.ENERGY_MATRIX + "_" + this.getCraftleMachineTier().getTier());
    }

    @Override
    public void tickServer() {

        // this tick will emit energy
        super.tickServer();

        double energyExtract = 0;
        double energyReceive = 0;

        // check active status
        this.setBlockActive(!this.getEnergyContainer().isEmpty());

        if (!this.getEnergyContainer().isFilled()) {

            // refactor later
            if (this.getContainerContents() != null) {
                energyReceive += injectFromItemSlot(this.getContainerContents().get(0));
            }
        }

        if (this.getContainerContents() != null) {
            energyExtract += extractFromItemSlot(this.getContainerContents().get(1));
        }

        this.incrementExtractRate(energyExtract);
        this.incrementInjectRate(energyReceive);

        // try to emit energy
        this.emitEnergy();

        if (energyExtract > 0 || energyReceive > 0) {
            sendUpdatePacket();
        }
    }

    @Override
    public double injectEnergy(double energy) {

        double energyToReturn = super.injectEnergy(energy);

        sendUpdatePacket();

        return energyToReturn;
    }

    @Override
    protected void tickClient() {
    }

    @Override
    public void sendUpdatePacket(TileEntity tile) {
        Craftle.packetHandler.sendToTrackingClients(new PowerdMachineUpdatePacket(this), this);
    }

    @Override
    public void sendUpdatePacket() {
        sendUpdatePacket(this);
    }

    public void handlePacket(CompoundNBT tag) {
        read(tag);
    }
}
