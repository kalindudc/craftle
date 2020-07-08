package com.craftle_mod.common.tile.base;

import com.craftle_mod.api.constants.GUIConstants;
import com.craftle_mod.common.block.machine.Crusher;
import com.craftle_mod.common.inventory.slot.SlotConfig;
import com.craftle_mod.common.inventory.slot.SlotConfig.SlotType;
import com.craftle_mod.common.inventory.slot.SlotConfigBuilder;
import com.craftle_mod.common.recipe.base.CraftleRecipe;
import com.craftle_mod.common.tier.CraftleBaseTier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.Direction;

public abstract class ProducerTileEntity extends PoweredMachineTileEntity implements
    IHasExtraContainerSlots, IHasEnergySlot, ISidedInventory, IRecipeHolder,
    IRecipeHelperPopulator {

    private static final int[] SLOTS_UP = new int[]{0};
    private static final int[] SLOTS_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_HORIZONTAL = new int[]{1};

    private SlotConfig resourceSlotConfig;
    private SlotConfig energySlotConfig;
    private SlotConfig extraContainerSlots;

    public ProducerTileEntity(Crusher block, IRecipeType<? extends CraftleRecipe> recipeTypeIn,
        CraftleBaseTier tier) {
        this(block, recipeTypeIn, tier, 0);
    }

    public ProducerTileEntity(Crusher block, IRecipeType<? extends CraftleRecipe> recipeTypeIn,
        CraftleBaseTier tier, double energy) {
        super(block, recipeTypeIn, 3, tier);
        this.getEnergyContainer().setEnergy(energy);
        init(tier);
    }

    private void init(CraftleBaseTier tier) {
        int chestSize;
        switch (tier) {

            // resource + energy + (rows * cols)
            case TIER_1:
                chestSize = 2 + (3 * 3);
                break;
            case TIER_2:
                chestSize = 2 + (4 * 4);
                break;
            case TIER_3:
                chestSize = 2 + (6 * 4);
                break;
            case TIER_4:
            case UNLIMITED:
                chestSize = 2 + (8 * 4);
                break;
            case BASIC:
            default:
                chestSize = 2 + (2 * 2);
                break;

        }
        addContainerSlotData();
        super.resetContainerSize(chestSize);
    }

    @Override
    public SlotConfig getExtraContainerSlotsConfig() {
        return extraContainerSlots;
    }

    private void addContainerSlotData() {

        resourceSlotConfig = SlotConfigBuilder.create().inventory(this).startX(135).startY(16)
            .build();
        energySlotConfig = SlotConfigBuilder.create().inventory(this).startingIndex(1).startX(135)
            .startY(55).slotType(SlotType.ENERGY).build();
        SlotConfigBuilder containerSlot = SlotConfigBuilder.create().inventory(this)
            .startingIndex(2).startX(185).startY(8 + GUIConstants.EXTRA_CONTAINER_TOP_OFFSET);

        switch (getCraftleMachineTier()) {
            case TIER_1:
                containerSlot.numCols(3).numRows(3);
                break;
            case TIER_2:
                containerSlot.numCols(4).numRows(4);
                break;
            case TIER_3:
                containerSlot.numCols(4).numRows(6);
                break;
            case TIER_4:
                containerSlot.numCols(4).numRows(8);
                break;
            default:
                containerSlot.numCols(2).numRows(2);
                break;
        }
        extraContainerSlots = containerSlot.build();

        addSlotData(resourceSlotConfig);
        addSlotData(energySlotConfig);
        addSlotData(extraContainerSlots);
    }

    @Override
    public SlotConfig getResourceSlotConfig() {
        return resourceSlotConfig;
    }

    @Override
    public SlotConfig getEnergySlotConfig() {
        return energySlotConfig;
    }

    @Override
    public boolean canEmitEnergy() {
        return false;
    }

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    @Nonnull
    @Override
    public int[] getSlotsForFace(@Nonnull Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_DOWN;
        } else {
            return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
        }
    }

    @Override
    public boolean canInsertItem(int index, @Nonnull ItemStack itemStackIn,
        @Nullable Direction direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, @Nonnull ItemStack stack,
        @Nonnull Direction direction) {
        if (direction == Direction.DOWN && index == 1) {
            Item item = stack.getItem();
            return item == Items.WATER_BUCKET || item == Items.BUCKET;
        }

        return true;
    }

    @Override
    protected void tickServer() {

        double energyReceive = 0;

        // check active status
        super.setBlockActive(!this.getEnergyContainer().isEmpty());

        if (!this.getEnergyContainer().isFilled()) {

            // refactor later
            if (this.getContainerContents() != null) {
                energyReceive += injectFromItemSlot(this.getContainerContents().get(1));
            }
        }

        super.tickServer();

        this.setEnergyInjectRate(energyReceive + getEnergyInjectRate());
    }
}
