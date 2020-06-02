package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.CraftleCreativeTabs;
import com.craftle_mod.common.item.SpecialItem;
import com.craftle_mod.common.item.gear.CraftleArmorMaterial;
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
                                                                .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP)));
    public static final RegistryObject<Item> TEST_FOOD    =
            ITEMS.register("test_food", () -> new Item(new Item.Properties()
                                                               .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP)
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
                                                         .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP)));
    public static final RegistryObject<Item> SPECIAL_ITEM =
            ITEMS.register("special_item", () -> new SpecialItem(
                    new Item.Properties()
                            .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP)));
    public static final RegistryObject<Item> TEST_BOOTS   =
            ITEMS.register("test_boots",
                           () -> new ArmorItem(CraftleArmorMaterial.TEST_INGOT,
                                               EquipmentSlotType.FEET,
                                               new Item.Properties()
                                                       .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP)));

}
