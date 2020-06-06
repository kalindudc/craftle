package com.craftle_mod.common.registries.register;

import com.craftle_mod.common.registries.register.object.RecipeSerializerRegistryObject;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class RecipeSerializerDeferredRegister
        extends CraftleDifferedRegister<IRecipeSerializer<?>> {

    public RecipeSerializerDeferredRegister(String modid) {
        super(modid, ForgeRegistries.RECIPE_SERIALIZERS);
    }

    public <T extends IRecipe<?>> RecipeSerializerRegistryObject<T> register(
            String name, Supplier<IRecipeSerializer<T>> sup) {
        return register(name, sup, RecipeSerializerRegistryObject::new);
    }


}
