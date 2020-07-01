package com.craftle_mod.common.tile.base;

import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.google.common.collect.Maps;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public abstract class MachineTileEntity extends ContainerizedTileEntity
        implements IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {

    protected final IRecipeType<? extends IRecipe<?>> recipeType;
    // using abstract furnace information
    private final Map<ResourceLocation, Integer> recipesUsed = Maps.newHashMap();
    private CraftleBaseTier tier;

    public MachineTileEntity(TileEntityType<?> typeIn,
                             IRecipeType<? extends IRecipe<?>> recipeTypeIn,
                             int containerSize, CraftleBaseTier tier) {
        super(typeIn, containerSize);
        this.tier = tier;
        this.recipeType = recipeTypeIn;
    }

    public void resetContainerSize(int size) {
        super.setContainerSize(size);
    }

    public CraftleBaseTier getCraftleMachineTier() {
        return tier;
    }

    public void setCraftleMachineTier(CraftleBaseTier tier) {
        this.tier = tier;
    }

    public abstract CraftleRecipeType<? extends IRecipe<?>> getRecipeType();

    public Map<ResourceLocation, Integer> getRecipesUsed() {
        return recipesUsed;
    }

    //TODO: add capabilities and energy management
}
