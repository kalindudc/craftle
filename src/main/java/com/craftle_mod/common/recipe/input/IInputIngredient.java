package com.craftle_mod.common.recipe.input;

import com.google.gson.JsonElement;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import java.util.List;
import java.util.function.Predicate;

public interface IInputIngredient<T> extends Predicate<T> {

    public void write(PacketBuffer buffer);

    public List<T> getRepresentations();

    public JsonElement serialize();

    public ItemStack getMatchingInstance(ItemStack stack);

}
