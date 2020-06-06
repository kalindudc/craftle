package com.craftle_mod.common.tile.machine;

import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.recipe.base.CraftleRecipe;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.ContainerizedTileEntity;
import com.google.common.collect.Maps;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public abstract class MachineTileEntity extends ContainerizedTileEntity
        implements IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {

    private CraftleBaseTier tier;

    // using abstract furnace information
    private final   Map<ResourceLocation, Integer>       recipesUsed =
            Maps.newHashMap();
    protected final IRecipeType<? extends CraftleRecipe> recipeType;

    public MachineTileEntity(TileEntityType<?> typeIn,
                             IRecipeType<? extends CraftleRecipe> recipeTypeIn,
                             int containerSize, CraftleBaseTier tier) {
        super(typeIn, 1);
        this.tier       = tier;
        this.recipeType = recipeTypeIn;
    }

    public void resetConainerSize(int size) {
        super.setContainerSize(size);
    }

    public void setCraftleMachineTier(CraftleBaseTier tier) {
        this.tier = tier;
    }

    public CraftleBaseTier getCraftleMachineTier() {
        return tier;
    }

    public abstract CraftleRecipeType<? extends CraftleRecipe> getRecipeType();

    public Map<ResourceLocation, Integer> getRecipesUsed() {
        return recipesUsed;
    }

    //TODO: add capabilities and energy management
}
