package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.CraftleCreativeTabs;
import com.craftle_mod.common.item.SpecialItem;
import com.craftle_mod.common.item.base.CraftleItem;
import com.craftle_mod.common.item.gear.CraftleArmorMaterial;
import com.craftle_mod.common.resource.Resource;
import com.craftle_mod.common.resource.ResourceType;
import com.craftle_mod.common.tier.CraftleItemTier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftleItems {

    public static final DeferredRegister<Item> ITEMS =
            new DeferredRegister<>(ForgeRegistries.ITEMS, Craftle.MODID);

    public static final RegistryObject<Item> TEST_INGOT   =
            ITEMS.register("test_ingot", () -> new Item(new Item.Properties()
                                                                .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MISC)));
    public static final RegistryObject<Item> TEST_FOOD    =
            ITEMS.register("test_food", () -> new Item(new Item.Properties()
                                                               .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MISC)
                                                               .food(new Food.Builder()
                                                                             .hunger(3)
                                                                             .saturation(
                                                                                     1.2F)
                                                                             .effect(() -> (new EffectInstance(
                                                                                             Effects.GLOWING,
                                                                                             4000)),
                                                                                     1)
                                                                             .build())));
    public static final RegistryObject<Item> TEST_TOOL    =
            ITEMS.register("test_tool",
                           () -> new PickaxeItem(CraftleItemTier.TEST, 12, 5.0F,
                                                 new Item.Properties()
                                                         .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MISC)));
    public static final RegistryObject<Item> SPECIAL_ITEM =
            ITEMS.register("special_item", () -> new SpecialItem(
                    new Item.Properties()
                            .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MISC)));
    public static final RegistryObject<Item> TEST_BOOTS   =
            ITEMS.register("test_boots",
                           () -> new ArmorItem(CraftleArmorMaterial.TEST_INGOT,
                                               EquipmentSlotType.FEET,
                                               new Item.Properties()
                                                       .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MISC)));

    //bits
    public static final RegistryObject<Item> ALUMINIUM_BIT =
            registerResource(Resource.ALUMINIUM, ResourceType.BIT);
    public static final RegistryObject<Item> COPPER_BIT    =
            registerResource(Resource.COPPER, ResourceType.BIT);
    public static final RegistryObject<Item> DIAMOND_BIT   =
            registerResource(Resource.DIAMOND, ResourceType.BIT);
    public static final RegistryObject<Item> GOLD_BIT      =
            registerResource(Resource.GOLD, ResourceType.BIT);
    public static final RegistryObject<Item> IRON_BIT      =
            registerResource(Resource.IRON, ResourceType.BIT);
    public static final RegistryObject<Item> PLATINUM_BIT  =
            registerResource(Resource.PLATINUM, ResourceType.BIT);
    public static final RegistryObject<Item> RUBY_BIT      =
            registerResource(Resource.RUBY, ResourceType.BIT);
    public static final RegistryObject<Item> SAPPHIRE_BIT  =
            registerResource(Resource.SAPPHIRE, ResourceType.BIT);
    public static final RegistryObject<Item> STEEL_BIT     =
            registerResource(Resource.STEEL, ResourceType.BIT);
    public static final RegistryObject<Item> TIN_BIT       =
            registerResource(Resource.TIN, ResourceType.BIT);
    public static final RegistryObject<Item> URANIUM_BIT   =
            registerResource(Resource.URANIUM, ResourceType.BIT);
    //dusts

    //enhanced

    //ingots

    //purified


    public static RegistryObject<Item> registerResource(Resource resource,
                                                        ResourceType type) {
        CraftleItem item = new CraftleItem(resource, type);
        return ITEMS.register(item.getCraftleRegistryName(), () -> item);
    }

}
