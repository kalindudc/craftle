package com.craftle_mod.common.registries.register.object;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;

public class RecipeSerializerRegistryObject<T extends IRecipe<?>>
        extends CraftleRegistryObject<IRecipeSerializer<T>> {

    public RecipeSerializerRegistryObject(
            RegistryObject<IRecipeSerializer<T>> registryObject) {
        super(registryObject);
    }

    @Nonnull
    public IRecipeSerializer<T> getRecipeSerializer() {
        return get();
    }

}
