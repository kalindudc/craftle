package com.craftle_mod.data.common.recipe;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.registries.CraftleItems;
import com.craftle_mod.common.registries.util.RegistriesUtils;
import com.craftle_mod.common.resource.ore.OreResourceTypes;
import com.craftle_mod.common.resource.ore.OreTypes;
import com.craftle_mod.common.tags.CraftleItemTags;
import com.craftle_mod.common.tags.util.ResourceTag;
import java.util.function.Consumer;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class CraftleRecipeProvider extends RecipeProvider {

    public CraftleRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        // build raw blocks and build resource blocks
        for (OreTypes ore : OreTypes.values()) {
            for (OreResourceTypes resource : ore.getResourceTypes()) {

                switch (resource) {
                    case ORE:
                        // if there are ores,
                        if (ore.shouldCreateOre()) {
                            smeltToIngot(ore, resource, OreResourceTypes.INGOT, consumer);
                        }
                        break;
                    case INGOT:
                        craftToStorageBlock(ore, resource, OreResourceTypes.BLOCK, consumer);
                        break;
                    case RAW:
                        smeltToIngot(ore, resource, OreResourceTypes.INGOT, consumer);
                        craftToStorageBlock(ore, resource, OreResourceTypes.RAW_BLOCK, consumer);
                        break;
                    case DUST:
                        smeltToIngot(ore, resource, OreResourceTypes.INGOT, consumer);
                        break;
                }

            }
        }

    }

    private void craftToStorageBlock(OreTypes ore, OreResourceTypes input, OreResourceTypes output, Consumer<IFinishedRecipe> consumer) {

        String inputKey = ore.getNameWithResource(input);
        String outputKey = ore.getNameWithResource(output);

        Item inputItem = CraftleItems.RESOURCES.get(input.getResourceType()).get(inputKey).get();
        Item outputItem = CraftleItems.RESOURCES.get(output.getResourceType()).get(outputKey).get();

        ShapelessRecipeBuilder.shapelessRecipe(inputItem, 9).addIngredient(outputItem).addCriterion("has_item", hasItem(inputItem))
            .build(consumer);
    }

    private void smeltToIngot(OreTypes ore, OreResourceTypes input, OreResourceTypes output, Consumer<IFinishedRecipe> consumer) {

        String inputKey = ore.getNameWithResource(input);
        String outputKey = ore.getNameWithResource(output);

        RegistryObject<Item> registryItem = CraftleItems.RESOURCES.get(output.getResourceType()).get(outputKey);

        ITag<Item> inputTag = getResourceItemTag(inputKey);

        Item outputItem = null;
        if (registryItem != null) {
            outputItem = registryItem.get();
        } else {
            switch (ore) {
                case IRON:
                    outputItem = Items.IRON_INGOT;
                    break;
                case GOLD:
                    outputItem = Items.GOLD_INGOT;
                    break;
            }
        }

        if (inputTag != null && outputItem != null) {
            CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(inputTag), outputItem, 0.7F, 200)
                .addCriterion("has_item", hasItem(inputTag)).build(consumer, modId(RegistriesUtils.buildName(inputKey, "smelting")));
            CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(inputTag), outputItem, 0.7F, 100)
                .addCriterion("has_item", hasItem(inputTag)).build(consumer, modId(RegistriesUtils.buildName(inputKey, "blasting")));
        }
    }

    private ITag<Item> getResourceItemTag(String path) {
        ITag<Item> tag;

        // check both items and block items for a given path. (ore vs items)
        ResourceTag<Item> resourceTag = CraftleItemTags.TAGS_ITEMS.get(path);
        if (resourceTag == null) {
            resourceTag = CraftleItemTags.TAGS_BLOCK_ITEMS.get(path);
        }

        if (resourceTag != null) {
            tag = resourceTag.getTag();
        } else {
            tag = CraftleItemTags.minecraft(path);
        }

        return tag;
    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(Craftle.MODID, path);
    }
}
