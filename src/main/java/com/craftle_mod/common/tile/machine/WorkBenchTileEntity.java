package com.craftle_mod.common.tile.machine;

import com.craftle_mod.api.constants.GUIConstants;
import com.craftle_mod.api.constants.TagConstants;
import com.craftle_mod.api.constants.TileEntityConstants;
import com.craftle_mod.common.block.machine.WorkBench;
import com.craftle_mod.common.inventory.container.machine.WorkBenchContainer;
import com.craftle_mod.common.inventory.slot.SlotConfig;
import com.craftle_mod.common.inventory.slot.SlotConfigBuilder;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.IHasExtraContainerSlots;
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

public class WorkBenchTileEntity extends PoweredMachineTileEntity implements
    IHasExtraContainerSlots {

    private final SlotConfig craftingMatrixSlotData;
    private final SlotConfig craftingResultSlotData;
    private final SlotConfig extraContainerSlots;

    public WorkBenchTileEntity(WorkBench block, IRecipeType<? extends IRecipe<?>> recipeTypeIn,
        CraftleBaseTier tier) {
        super(block, recipeTypeIn, TileEntityConstants.WORKBENCH_CONTAINER_SIZE + 10
                + TileEntityConstants.WORKBENCH_CRAFTING_OUTPUT_SIZE, tier,
            TileEntityConstants.WORKBENCH_BASE_CAPACITY * tier.getMultiplier(),
            TileEntityConstants.WORKBENCH_BASE_MAX_INPUT * tier.getMultiplier(), 0);

        craftingMatrixSlotData = SlotConfigBuilder.create().numCols(3).numRows(3).inventory(null)
            .startX(82).startY(17).build();
        craftingResultSlotData = SlotConfigBuilder.create().startX(139).startY(35).inventory(null)
            .build();
        extraContainerSlots = SlotConfigBuilder.create().inventory(this).numCols(4).numRows(6)
            .startingIndex(10).startX(185).startY(8 + GUIConstants.EXTRA_CONTAINER_TOP_OFFSET)
            .build();

        // order matters, slot index 0 has to be result so add it last, shift click should prioritize
        // the container so add that second and finally the craft matrix
        addSlotData(craftingMatrixSlotData);
        addSlotData(extraContainerSlots);
        addSlotData(craftingResultSlotData);

        setInfoScreenWidth(71);
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

    @Override
    public boolean canEmitEnergy() {
        return false;
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
    public SlotConfig getExtraContainerSlotsConfig() {
        return extraContainerSlots;
    }
}
