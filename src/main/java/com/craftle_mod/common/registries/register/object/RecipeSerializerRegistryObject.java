package com.craftle_mod.common.registries.register.object;

import javax.annotation.Nonnull;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;

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
