package com.craftle_mod.common.tile.machine;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.machine.Crusher;
import com.craftle_mod.common.inventory.slot.SlotConfigBuilder;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.recipe.base.CraftleRecipe;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CrusherTileEntity extends PoweredMachineTileEntity implements ISidedInventory,
    IRecipeHolder, IRecipeHelperPopulator {

    private static final int[] SLOTS_UP = new int[]{0};
    private static final int[] SLOTS_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_HORIZONTAL = new int[]{1};


    public CrusherTileEntity(Crusher block, IRecipeType<? extends CraftleRecipe> recipeTypeIn,
        CraftleBaseTier tier) {
        super(block, recipeTypeIn, 3, tier);
        init(tier);
    }

    public CrusherTileEntity(Crusher block, CraftleBaseTier tier) {
        this(block, CraftleRecipeType.CRUSHING, tier);
    }

    public CrusherTileEntity(Crusher block, CraftleBaseTier tier, int energy) {
        this(block, CraftleRecipeType.CRUSHING, tier);
        this.getEnergyContainer().setEnergy(energy);
    }

    public CrusherTileEntity() {
        this((Crusher) CraftleBlocks.CRUSHER_BASIC.get(), CraftleRecipeType.CRUSHING,
            CraftleBaseTier.BASIC);
    }

    // CONSTRUCTORS END -----------------

    private void addContainerSlotData() {

        SlotConfigBuilder resourceSlot = SlotConfigBuilder.create().inventory(this)
            .startingIndex(0);
        SlotConfigBuilder fuelSlot = SlotConfigBuilder.create().inventory(this).startingIndex(1);
        SlotConfigBuilder containerSlot = SlotConfigBuilder.create().inventory(this)
            .startingIndex(2);

        switch (getCraftleMachineTier()) {
            case TIER_1:
                resourceSlot.startX(46).startY(17);
                fuelSlot.startX(46).startY(53);
                containerSlot.numCols(2).numRows(2).startX(104).startY(25);
                break;
            case TIER_2:
                resourceSlot.startX(36).startY(17);
                fuelSlot.startX(36).startY(53);
                containerSlot.numCols(4).numRows(2).startX(90).startY(25);
                break;
            case TIER_3:
                resourceSlot.startX(36).startY(17);
                fuelSlot.startX(36).startY(53);
                containerSlot.numCols(4).numRows(4).startX(90).startY(7);
                break;
            case TIER_4:
                resourceSlot.startX(12).startY(17);
                fuelSlot.startX(12).startY(53);
                containerSlot.numCols(6).numRows(4).startX(62).startY(7);
                break;
            default:
                resourceSlot.startX(56).startY(17);
                fuelSlot.startX(56).startY(53);
                containerSlot.startX(116).startY(35);
                break;
        }

        addSlotData(resourceSlot.build());
        addSlotData(fuelSlot.build());
        addSlotData(containerSlot.build());
    }

    private void init(CraftleBaseTier tier) {
        int chestSize;
        switch (tier) {

            case TIER_1:
                chestSize = 2 + 4;
                break;
            case TIER_2:
                chestSize = 2 + 8;
                break;
            case TIER_3:
                chestSize = 2 + 16;
                break;
            case TIER_4:
            case UNLIMITED:
                chestSize = 2 + 24;
                break;
            case BASIC:
            default:
                chestSize = 2 + 1;
                break;

        }
        addContainerSlotData();
        super.resetContainerSize(chestSize);
    }

    @Nonnull
    @Override
    public CraftleRecipeType<? extends CraftleRecipe> getRecipeType() {
        return CraftleRecipeType.CRUSHING;
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

    @Nonnull
    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent(
            "block." + Craftle.MODID + ".crusher_" + this.getCraftleMachineTier().getTier());
    }

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void tickServer() {

    }

    @Override
    public void tickClient() {

    }

    //TODO: correct the following two methods
    // they are just returning dummy content, not the corect capabilities

    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        if (this.getItemHandler() != null) {
            this.getItemHandler().invalidate();
            this.setItemHandler(null);
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.getItemHandler().cast();
        }

        return super.getCapability(cap, side);
    }

    private IItemHandlerModifiable createHandler() {
        return new InvWrapper(this);
    }
}

