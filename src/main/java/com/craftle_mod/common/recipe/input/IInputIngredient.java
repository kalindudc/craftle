package com.craftle_mod.common.recipe.input;

import com.google.gson.JsonElement;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public interface IInputIngredient<T> extends Predicate<T> {

    void write(PacketBuffer buffer);

    List<T> getRepresentations();

    JsonElement serialize();

    ItemStack getMatchingInstance(ItemStack stack);

}
