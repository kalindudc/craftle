package com.craftle_mod.common.tile.storage.energy_matrix;

import com.craftle_mod.api.TagConstants;
import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.capability.energy.EnergyContainerCapability;
import com.craftle_mod.common.inventory.container.storage.energy_matrix.EnergyMatrixContainerFactory;
import com.craftle_mod.common.item.EnergyItem;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import com.craftle_mod.common.util.EnergyUtils;
import javafx.geometry.Point3D;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EnergyMatrixTileEntity extends PoweredMachineTileEntity {

    public EnergyMatrixTileEntity(TileEntityType<?> typeIn,
                                  IRecipeType<? extends IRecipe> recipeTypeIn, int containerSize,
                                  CraftleBaseTier tier, int capacity) {
        super(typeIn, recipeTypeIn, containerSize, tier, capacity);
    }

    public EnergyMatrixTileEntity(TileEntityType<?> typeIn, CraftleBaseTier tier, int capacity) {
        super(typeIn, CraftleRecipeType.CRAFTING, 2, tier, capacity);
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

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory player) {
        return EnergyMatrixContainerFactory
                .buildWithTileEntity(this.getCraftleMachineTier(), id, player, this);
    }

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed() {
        return null;
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

    @Override
    public CompoundNBT write(CompoundNBT compound) {

        super.write(compound);

        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {

        super.read(compound);
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(
                TagConstants.ENERGY_MATRIX + "_" + this.getCraftleMachineTier().getTier());
    }

    @Override
    public void tick() {

        // check active status
        if (this.getEnergyContainer().getEnergyStored() > 0) {
            super.setBlockActive(true);
        }

        if (this.getEnergyContainer().getEnergyStored() == 0) {
            super.setBlockActive(false);
        }

        if (this.getEnergyContainer().getEnergyStored() <
            this.getEnergyContainer().getMaxEnergyStored()) {

            // TODO: all this logic can be handled in the energy container
            // refactor later
            if (this.getTileEntityItems() != null) {
                // check for an item in inject
                ItemStack injectStack = this.getTileEntityItems().getStackInSlot(0);
                if (!injectStack.isEmpty() && isItemFuel(injectStack)) {

                    int storedEnergy = getFuelValue(injectStack);
                    if (storedEnergy < this.getEnergyContainer().getMaxEnergyStored() &&
                        storedEnergy < (this.getEnergyContainer().getMaxEnergyStored() -
                                        this.getEnergyContainer().getEnergyStored())) {
                        int received  = this.getEnergyContainer().receiveEnergy(storedEnergy);
                        int extracted = EnergyUtils.extractEnergyFromItem(injectStack, received);
                    }
                    else {
                        int received = this.getEnergyContainer().receiveEnergy(
                                this.getEnergyContainer().getMaxEnergyStored() -
                                this.getEnergyContainer().getEnergyStored());
                        int extracted = EnergyUtils.extractEnergyFromItem(injectStack, received);
                    }
                }
            }

            List<ICapabilityProvider> energyProvidingBlocks = getNeighborsWithEnergy();

            int i = 0;
            for (ICapabilityProvider entity : energyProvidingBlocks) {
                IEnergyStorage container =
                        entity.getCapability(CapabilityEnergy.ENERGY).orElse(null);
                if (container != null) {
                    Craftle.logInfo("info %b", container instanceof EnergyContainerCapability);

                    if (container.getEnergyStored() > 0) {

                        int toReceive = Math.min(container.getEnergyStored(),
                                                 this.getEnergyContainer().getMaxEnergyStored() -
                                                 this.getEnergyContainer().getEnergyStored());


                        if (container instanceof EnergyContainerCapability) {
                            toReceive = Math.min(
                                    ((EnergyContainerCapability) container).getMaxExtract(),
                                    toReceive);
                            Craftle.logInfo("info %d %d",
                                            ((EnergyContainerCapability) container).getMaxExtract(),
                                            toReceive);
                        }

                        int extracted = container.extractEnergy(toReceive, false);
                        int received  = this.getEnergyContainer().receiveEnergy(extracted);
                    }
                }
                i++;
            }

        }

        // check for an item in extract
        ItemStack extractStack = this.getTileEntityItems().getStackInSlot(1);
        if (validToReceive(extractStack) && this.getEnergyContainer().getEnergyStored() > 0) {

            int toExtract = EnergyUtils.getEnergyRequiredForItem(extractStack);
            if (toExtract < this.getEnergyContainer().getEnergyStored()) {
                int received  = EnergyUtils.injectEnergyToItem(extractStack, toExtract);
                int extracted = this.getEnergyContainer().extractEnergy(received);
            }
            else {
                int received = EnergyUtils.injectEnergyToItem(extractStack,
                                                              this.getEnergyContainer()
                                                                  .getEnergyStored());
                int extracted = this.getEnergyContainer().extractEnergy(received);
            }
        }
    }

    public List<ICapabilityProvider> getNeighborsWithEnergy() {
        List<ICapabilityProvider> energyProvidingBlocks = new ArrayList<>();
        Point3D currentPos = new Point3D(this.pos.getX(), this.pos.getY(), this.pos.getZ());

        for (int i = -1; i < 2; i++) {

            if (i != 0) {
                TileEntity entity =
                        checkEntityAtPos(currentPos, this.pos.getX() + i, this.pos.getY(),
                                         this.pos.getZ());
                if (entity != null)
                    energyProvidingBlocks.add(entity);

                entity = checkEntityAtPos(currentPos, this.pos.getX(), this.pos.getY() + i,
                                          this.pos.getZ());
                if (entity != null)
                    energyProvidingBlocks.add(entity);

                entity = checkEntityAtPos(currentPos, this.pos.getX(), this.pos.getY(),
                                          this.pos.getZ() + i);
                if (entity != null)
                    energyProvidingBlocks.add(entity);
            }
        }

        return energyProvidingBlocks;
    }

    private TileEntity checkEntityAtPos(Point3D currentPos, int x, int y, int z) {
        if (!new Point3D(x, y, z).equals(currentPos)) {
            TileEntity entity = this.world.getTileEntity(new BlockPos(x, y, z));

            if (entity instanceof ICapabilityProvider &&
                !(entity instanceof EnergyMatrixTileEntity)) {
                return entity;
            }
        }

        return null;
    }

    private int getFuelValue(ItemStack stack) {
        if (stack.getItem() instanceof EnergyItem) {
            return EnergyUtils.getEnergyStoredFromItem(stack);
        }

        return 0;
    }

    private boolean isItemFuel(ItemStack stackInSlot) {
        return getFuelValue(stackInSlot) > 0;
    }

    private boolean validToReceive(ItemStack stack) {
        return stack.getItem() instanceof EnergyItem;
    }

}
