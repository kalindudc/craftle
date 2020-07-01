package com.craftle_mod.common.recipe.base;

import com.craftle_mod.common.recipe.inventory.EmptyInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * A class to represent a CraftleRecipe for a custom recipe
 */
public abstract class CraftleRecipe implements IRecipe<EmptyInventory> {

    private final ResourceLocation resourceLocation;

    protected CraftleRecipe(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return resourceLocation;
    }

    public abstract void write(PacketBuffer buffer);

    @Override
    public boolean isDynamic() {

        // add recipe to the recipe book ?
        // I don't know how this works yet
        // TODO: figure this out
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull EmptyInventory inv) {
        // This is not a valid recipe... Yet.
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        // Not yet!
        return ItemStack.EMPTY;
    }

    @Override
    public boolean matches(@Nonnull EmptyInventory inv, @Nonnull World world) {
        return true;
    }
}

