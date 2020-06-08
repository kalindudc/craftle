package com.craftle_mod.common.tile.storage.energy_matrix;

import com.craftle_mod.api.TagConstants;
import com.craftle_mod.common.inventory.container.storage.energy_matrix.EnergyMatrixContainerFactory;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
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
        // complete tick
    }

}
