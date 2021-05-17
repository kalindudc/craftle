package com.craftle_mod.common.registries;

import com.craftle_mod.common.registries.util.RegistriesUtils;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftleRecipeSerializers {

    /**
     * UNUSED
     */
    private CraftleRecipeSerializers() {
    }

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = RegistriesUtils.createRegister(ForgeRegistries.RECIPE_SERIALIZERS);


    public static void register(IEventBus eventBus) {
        RECIPES.register(eventBus);
    }

}
