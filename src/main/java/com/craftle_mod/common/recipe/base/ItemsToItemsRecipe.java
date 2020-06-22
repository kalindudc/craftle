package com.craftle_mod.common.recipe.base;

import com.craftle_mod.common.recipe.input.ItemStackIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public abstract class ItemsToItemsRecipe extends CraftleRecipe {

    private final ItemStackIngredient input;
    private final ItemStack output;

    public ItemsToItemsRecipe(ResourceLocation resourceLocation,
        ItemStackIngredient input, ItemStack output) {
        super(resourceLocation);
        this.input = input;
        this.output = output;
    }

    public ItemStackIngredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    @Override
    public void write(PacketBuffer buffer) {
        input.write(buffer);
        buffer.writeItemStack(output);
    }

}
