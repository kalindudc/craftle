package com.craftle_mod.common.tile.machine;

import com.craftle_mod.api.ContainerConstants;
import com.craftle_mod.api.TagConstants;
import com.craftle_mod.api.TileEntityConstants;
import com.craftle_mod.common.block.machine.WorkBench;
import com.craftle_mod.common.inventory.container.machine.WorkBenchContainer;
import com.craftle_mod.common.inventory.slot.SlotConfig;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class WorkBenchTileEntity extends PoweredMachineTileEntity {

    private final SlotConfig craftingMatrixSlotData;
    private final SlotConfig craftingResultSlotData;

    public WorkBenchTileEntity(WorkBench block, IRecipeType<? extends IRecipe<?>> recipeTypeIn,
        CraftleBaseTier tier) {
        super(block, recipeTypeIn, TileEntityConstants.WORKBENCH_CONTAINER_SIZE + 9
                + TileEntityConstants.WORKBENCH_CRAFTING_OUTPUT_SIZE, tier,
            (int) (TileEntityConstants.WORKBENCH_BASE_CAPACITY * tier.getMultiplier()),
            (int) (TileEntityConstants.WORKBENCH_BASE_MAX_INPUT * tier.getMultiplier()), 0);

        craftingMatrixSlotData = new SlotConfig(3, 3, null, 0, 30, 17,
            ContainerConstants.TOTAL_SLOT_SIZE);
        craftingResultSlotData = new SlotConfig(1, 1, null, 0, 124, 35,
            ContainerConstants.TOTAL_SLOT_SIZE);

        addSlotData(craftingMatrixSlotData);
        addSlotData(craftingResultSlotData);
        addSlotData(new SlotConfig(5, 5, this, 10, 184, 70, ContainerConstants.TOTAL_SLOT_SIZE));
    }

    public WorkBenchTileEntity() {
        this((WorkBench) CraftleBlocks.WORKBENCH.get(), CraftleRecipeType.CRAFTING,
            CraftleBaseTier.BASIC);
    }

    @Nonnull
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory player) {

        return new WorkBenchContainer(getBlock().getContainerType(), id, player, this);
    }

    public SlotConfig getCraftingMatrixSlotData() {
        return craftingMatrixSlotData;
    }

    public SlotConfig getCraftingResultSlotData() {
        return craftingResultSlotData;
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
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(TagConstants.WORKBENCH);
    }

    @Override
    protected void tickServer() {
    }

    @Override
    protected void tickClient() {
    }
}
