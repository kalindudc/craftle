package com.craftle_mod.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.JSONUtils;

import javax.annotation.Nonnull;

public class SerializationHelper {

    public static ItemStack getItemStack(@Nonnull JsonObject json,
                                         @Nonnull String key) {
        if (!json.has(key)) {
            throw new JsonSyntaxException(
                    "Missing '" + key + "', expected to find an object");
        }
        if (!json.get(key).isJsonObject()) {
            throw new JsonSyntaxException(
                    "Expected '" + key + "' to be an object");
        }

        return ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, key));
    }
}
