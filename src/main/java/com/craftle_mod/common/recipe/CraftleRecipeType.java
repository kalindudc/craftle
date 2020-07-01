package com.craftle_mod.common.recipe;

import com.craftle_mod.common.recipe.base.CraftleRecipe;
import com.craftle_mod.common.recipe.base.ItemsToItemsRecipe;
import com.craftle_mod.common.recipe.inventory.EmptyInventory;
import com.craftle_mod.common.recipe.serializer.ItemsToItemsRecipeSerializer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;

public class CraftleRecipeType<T extends CraftleRecipe> implements IRecipeType<T> {

    public static final List<CraftleRecipeType<?>> RECIPE_CONTAINER = new ArrayList<>();

    public static final CraftleRecipeType<ItemsToItemsRecipe> CRUSHING = addToList("crushing",
        new ItemsToItemsRecipeSerializer<>(CrushingRecipe::new));

    private List<T> validRecipes;
    private final String recipeName;
    private final IRecipeSerializer<T> serializer;

    private CraftleRecipeType(String recipeName, IRecipeSerializer<T> serializer) {
        this.validRecipes = Collections.emptyList();
        this.recipeName = recipeName;
        this.serializer = serializer;
    }

    public String getRegistryName() {
        return recipeName;
    }

    public IRecipeSerializer<T> getSerializer() {
        return serializer;
    }

    public List<T> getRecipes(@Nullable World world) {
        if (world == null) {
            return Collections.emptyList();
        }

        // collect valid recipes
        if (validRecipes.isEmpty()) {
            RecipeManager recipeManager = world.getRecipeManager();

            // configure other machine type
            // default vanila recipes may need to be acquired

            this.validRecipes = recipeManager.getRecipes(this, EmptyInventory.INSTANCE, world);
        }

        return validRecipes;
    }

    public Stream<T> stream(@Nullable World world) {
        return getRecipes(world).stream();
    }

    public static <T extends CraftleRecipe> CraftleRecipeType<T> addToList(String recipeName,
        IRecipeSerializer<T> serializer) {
        CraftleRecipeType<T> type = new CraftleRecipeType<>(recipeName, serializer);
        RECIPE_CONTAINER.add(type);
        return type;
    }

    private static void clearValidRecipes() {
        RECIPE_CONTAINER.forEach(recipe -> recipe.validRecipes.clear());
    }

    public static void registerRecipeTypes(IForgeRegistry<IRecipeSerializer<?>> registry) {
        RECIPE_CONTAINER.forEach(type -> registry.register(type.getSerializer()));
    }

    public boolean contains(@Nullable World wold, Predicate<T> match) {
        // find if a given recipe is in this type
        return stream(wold).anyMatch(match);
    }

    /**
     * For serialization purposes.
     *
     * @return A valid string representation of this recipe type.
     */
    @Override
    public String toString() {
        return recipeName;
    }

}
