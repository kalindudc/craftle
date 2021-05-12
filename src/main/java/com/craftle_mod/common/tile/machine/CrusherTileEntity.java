package com.craftle_mod.common.tile.machine;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.machine.Crusher;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.recipe.base.CraftleRecipe;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.ProducerTileEntity;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CrusherTileEntity extends ProducerTileEntity {

    public CrusherTileEntity(Crusher block, CraftleBaseTier tier) {
        super(block, CraftleRecipeType.CRUSHING, tier);
    }

    public CrusherTileEntity(Crusher block, CraftleBaseTier tier, double energy) {
        super(block, CraftleRecipeType.CRUSHING, tier, energy);
    }

    public CrusherTileEntity() {
        super((Crusher) CraftleBlocks.CRUSHER_BASIC.get(), CraftleRecipeType.CRUSHING,
            CraftleBaseTier.BASIC);
    }

    @Nonnull
    @Override
    public CraftleRecipeType<? extends CraftleRecipe> getRecipeType() {
        return CraftleRecipeType.CRUSHING;
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

