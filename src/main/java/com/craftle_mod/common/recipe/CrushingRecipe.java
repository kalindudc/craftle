package com.craftle_mod.common.recipe;

import com.craftle_mod.common.recipe.base.ItemsToItemsRecipe;
import com.craftle_mod.common.recipe.input.ItemStackIngredient;
import com.craftle_mod.common.registries.CraftleRecipeSerializers;
import javax.annotation.Nonnull;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

public class CrushingRecipe extends ItemsToItemsRecipe {

    public CrushingRecipe(ResourceLocation resourceLocation, ItemStackIngredient input,
        ItemStack output) {
        super(resourceLocation, input, output);
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return CraftleRecipeSerializers.CRUSHING.getRecipeSerializer();
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return CraftleRecipeType.CRUSHING;
    }
}
