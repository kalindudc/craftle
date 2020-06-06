package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.recipe.CrushingRecipe;
import com.craftle_mod.common.recipe.serializer.ItemsToItemsRecipeSerializer;
import com.craftle_mod.common.registries.register.RecipeSerializerDeferredRegister;
import com.craftle_mod.common.registries.register.object.RecipeSerializerRegistryObject;

public class CraftleRecipeSerializers {

    public static final RecipeSerializerDeferredRegister SERIALIZERS =
            new RecipeSerializerDeferredRegister(Craftle.MODID);

    public static final RecipeSerializerRegistryObject CRUSHING = SERIALIZERS
            .register("crushing", () -> new ItemsToItemsRecipeSerializer<>(
                    CrushingRecipe::new));
}
