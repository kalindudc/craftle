package com.craftle_mod.common.recipe.input;

import com.craftle_mod.api.JsonConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class ItemStackIngredient
        implements IInputIngredient<ItemStack> {


    public static ItemStackIngredient deserialize(@Nullable JsonElement json) {

        if (json == null || json.isJsonNull()) {
            throw new JsonSyntaxException("Ingredient cannot be null");
        }
        if (json.isJsonArray()) {
            JsonArray jsonArray = json.getAsJsonArray();
            int       size      = jsonArray.size();
            if (size == 0) {
                throw new JsonSyntaxException(
                        "Ingredient array cannot be empty.");
            }

            // TODO: handle array of objects for multiple ingredients


            json = jsonArray.get(0);
        }
        if (!json.isJsonObject()) {
            throw new JsonSyntaxException(
                    "Item must be an object or an array" + " of objects");
        }

        JsonObject jsonObject = json.getAsJsonObject();
        int        amount     = 1;
        if (jsonObject.has(JsonConstants.AMOUNT)) {
            JsonElement count = jsonObject.get(JsonConstants.AMOUNT);
            if (!JSONUtils.isNumber(count)) {
                throw new JsonSyntaxException(
                        "Amount must be a number larger than 0.");
            }
            amount = count.getAsJsonPrimitive().getAsInt();
            if (amount < 1) {
                throw new JsonSyntaxException(
                        "Amount must be larger than or equal to one");
            }
        }
        JsonElement jsonelement =
                JSONUtils.isJsonArray(jsonObject, JsonConstants.INGREDIENT) ?
                JSONUtils.getJsonArray(jsonObject, JsonConstants.INGREDIENT) :
                JSONUtils.getJsonObject(jsonObject, JsonConstants.INGREDIENT);
        Ingredient ingredient = Ingredient.deserialize(jsonelement);
        return create(ingredient, amount);
    }

    public static ItemStackIngredient create(@Nonnull Ingredient ingredient,
                                             int amount) {
        return new SingleItemStackIngredient(ingredient, amount);
    }

    public static ItemStackIngredient read(PacketBuffer buffer) {
        //TODO: add ability to use multi stack ingredients
        return SingleItemStackIngredient.read(buffer);
    }

}
