package com.craftle_mod.common.recipe.input;

import com.craftle_mod.api.JsonConstants;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SingleItemStackIngredient extends ItemStackIngredient {

    @Nonnull
    private final Ingredient ingredient;
    private final int amount;

    public SingleItemStackIngredient(Ingredient ingredient, int amount) {
        this.ingredient = Objects.requireNonNull(ingredient);
        this.amount = amount;
    }

    public static SingleItemStackIngredient read(PacketBuffer buffer) {
        return new SingleItemStackIngredient(Ingredient.read(buffer),
                buffer.readVarInt());
    }

    @Override
    public boolean test(ItemStack stack) {
        return ingredient.test(stack) && stack.getCount() >= amount;
    }

    @Nonnull
    @Override
    public ItemStack getMatchingInstance(ItemStack stack) {
        if (test(stack)) {
            ItemStack matching = stack.copy();
            matching.setCount(amount);
            return matching;
        }
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public List<ItemStack> getRepresentations() {
        //TODO: Can this be cached some how
        List<ItemStack> representations = new ArrayList<>();
        for (ItemStack stack : ingredient.getMatchingStacks()) {
            if (stack.getCount() == amount) {
                representations.add(stack);
            } else {
                ItemStack copy = stack.copy();
                copy.setCount(amount);
                representations.add(copy);
            }
        }
        return representations;
    }

    @Override
    public void write(PacketBuffer buffer) {
        ingredient.write(buffer);
        buffer.writeVarInt(amount);
    }

    @Nonnull
    @Override
    public JsonElement serialize() {
        JsonObject json = new JsonObject();
        if (amount > 1) {
            json.addProperty(JsonConstants.AMOUNT, amount);
        }
        json.add(JsonConstants.INGREDIENT, ingredient.serialize());
        return json;
    }

}
