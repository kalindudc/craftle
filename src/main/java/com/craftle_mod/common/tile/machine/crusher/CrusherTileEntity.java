package com.craftle_mod.common.tile.machine.crusher;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.inventory.container.machine.crusher.CrusherContainerFactory;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.recipe.base.CraftleRecipe;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CrusherTileEntity extends PoweredMachineTileEntity
        implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator {

    private static final int[] SLOTS_UP         = new int[]{0};
    private static final int[] SLOTS_DOWN       = new int[]{2, 1};
    private static final int[] SLOTS_HORIZONTAL = new int[]{1};


    public CrusherTileEntity(TileEntityType<?> typeIn,
                             IRecipeType<? extends CraftleRecipe> recipeTypeIn,
                             CraftleBaseTier tier) {
        super(typeIn, recipeTypeIn, 3, tier);
        init(tier);
    }

    public CrusherTileEntity(TileEntityType<?> typeIn, CraftleBaseTier tier) {
        this(typeIn, CraftleRecipeType.CRUSHING, tier);

    }

    public CrusherTileEntity() {
        this(CraftleTileEntityTypes.CRUSHER_BASIC.get(), CraftleRecipeType.CRUSHING,
             CraftleBaseTier.BASIC);
    }

    // CONSTRUCTORS END -----------------

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
        super.resetContainerSize(chestSize);
    }

    @Nonnull
    @Override
    public CraftleRecipeType<? extends CraftleRecipe> getRecipeType() {
        return CraftleRecipeType.CRUSHING;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_DOWN;
        }
        else {
            return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
        }
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        if (direction == Direction.DOWN && index == 1) {
            Item item = stack.getItem();
            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent(
                "block." + Craftle.MODID + ".crusher_" + this.getCraftleMachineTier().getTier());
    }

    @Override
    public Container createMenu(int id, PlayerInventory player) {
        return CrusherContainerFactory
                .buildWithTileEntity(this.getCraftleMachineTier(), id, player, this);
    }

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void tick() {

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
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nonnull Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.getItemHandler().cast();
        }

        return super.getCapability(cap, side);
    }

    private IItemHandlerModifiable createHandler() {
        return new InvWrapper(this);
    }
}

