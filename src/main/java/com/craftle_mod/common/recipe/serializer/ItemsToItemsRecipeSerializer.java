package com.craftle_mod.common.recipe.serializer;

import com.craftle_mod.api.JsonConstants;
import com.craftle_mod.api.SerializationHelper;
import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.recipe.base.ItemsToItemsRecipe;
import com.craftle_mod.common.recipe.input.ItemStackIngredient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemsToItemsRecipeSerializer<T extends ItemsToItemsRecipe>
        extends ForgeRegistryEntry<IRecipeSerializer<?>>
        implements IRecipeSerializer<T> {

    private final IRFactory<T> factory;

    public ItemsToItemsRecipeSerializer(IRFactory<T> factory) {
        this.factory = factory;
    }

    @Nonnull
    @Override
    public T read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        JsonElement input = JSONUtils.isJsonArray(json, JsonConstants.INPUT) ?
                JSONUtils.getJsonArray(json, JsonConstants.INPUT) :
                JSONUtils.getJsonObject(json, JsonConstants.INPUT);
        ItemStackIngredient inputIngredient =
                ItemStackIngredient.deserialize(input);
        ItemStack output =
                SerializationHelper.getItemStack(json, JsonConstants.OUTPUT);
        if (output.isEmpty()) {
            throw new JsonSyntaxException("Recipe output must not be empty.");
        }
        return this.factory.create(recipeId, inputIngredient, output);
    }

    @Nullable
    @Override
    public T read(@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer) {

        try {
            ItemStackIngredient inputIngredient =
                    ItemStackIngredient.read(buffer);
            ItemStack output = buffer.readItemStack();
            return this.factory.create(recipeId, inputIngredient, output);
        } catch (Exception e) {
            Craftle.LOGGER.error("Error reading items stack in recipe.", e);
            throw e;
        }
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull T recipe) {
        try {
            recipe.write(buffer);
        } catch (Exception e) {
            Craftle.LOGGER.error("Error writing items stack in recipe.", e);
            throw e;
        }
    }

    /**
     * Factory to create custom recipe.
     */
    public interface IRFactory<T extends ItemsToItemsRecipe> {

        T create(ResourceLocation id, ItemStackIngredient ingredient,
                 ItemStack output);
    }
}
