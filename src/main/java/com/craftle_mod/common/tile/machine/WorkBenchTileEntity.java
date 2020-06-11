package com.craftle_mod.common.tile.machine;

import com.craftle_mod.api.TagConstants;
import com.craftle_mod.api.TileEntityConstants;
import com.craftle_mod.common.inventory.container.machine.WorkBenchContainer;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.registries.CraftleContainerTypes;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WorkBenchTileEntity extends PoweredMachineTileEntity {

    public WorkBenchTileEntity(TileEntityType<?> typeIn,
                               IRecipeType<? extends IRecipe> recipeTypeIn, CraftleBaseTier tier) {
        super(typeIn, recipeTypeIn, TileEntityConstants.WORKBENCH_CONTAINER_SIZE + 9 +
                                    TileEntityConstants.WORKBENCH_CRAFTING_OUTPUT_SIZE, tier,
              (int) (TileEntityConstants.WORKBENCH_BASE_CAPACITY * tier.getMultiplier()),
              (int) (TileEntityConstants.WORKBENCH_BASE_MAX_INPUT * tier.getMultiplier()),
              (int) (TileEntityConstants.WORKBENCH_BASE_MAX_OUTPUT * tier.getMultiplier()));
    }

    public WorkBenchTileEntity() {
        this(CraftleTileEntityTypes.WORKBENCH.get(), CraftleRecipeType.CRAFTING,
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
        return new WorkBenchContainer(CraftleContainerTypes.WORKBENCH.get(), id, player, this);
    }

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(TagConstants.WORKBENCH);
    }

    @Override
    public void tick() {
        // TODO: tick :0
    }
}
