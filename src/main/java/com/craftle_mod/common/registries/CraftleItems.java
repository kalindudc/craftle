package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.CraftleCreativeTabs;
import com.craftle_mod.common.item.EnergyItem;
import com.craftle_mod.common.item.SpecialItem;
import com.craftle_mod.common.item.base.CraftleItem;
import com.craftle_mod.common.item.base.CraftleResourceItem;
import com.craftle_mod.common.item.gear.CraftleArmorMaterial;
import com.craftle_mod.common.item.tool.*;
import com.craftle_mod.common.resource.Resource;
import com.craftle_mod.common.resource.ResourceType;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tier.CraftleToolTier;
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

    public static final RegistryObject<Item> TEST_INGOT = ITEMS.register("test_ingot",
            () -> new Item(
                    new Item.Properties()
                            .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MISC)));
    public static final RegistryObject<Item> TEST_FOOD = ITEMS.register("test_food",
            () -> new Item(
                    new Item.Properties()
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
    public static final RegistryObject<Item> TEST_TOOL = ITEMS.register("test_tool",
            () -> new PickaxeItem(
                    CraftleToolTier.TEST,
                    12, 5.0F,
                    new Item.Properties()
                            .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MISC)));
    public static final RegistryObject<Item> SPECIAL_ITEM = ITEMS.register("special_item",
            () -> new SpecialItem(
                    new Item.Properties()
                            .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MISC)));
    public static final RegistryObject<Item> TEST_BOOTS = ITEMS.register("test_boots",
            () -> new ArmorItem(
                    CraftleArmorMaterial.TEST_INGOT,
                    EquipmentSlotType.FEET,
                    new Item.Properties()
                            .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MISC)));

    //bits
    public static final RegistryObject<Item> ALUMINIUM_BIT =
            registerResource(Resource.ALUMINIUM, ResourceType.BIT);
    public static final RegistryObject<Item> COPPER_BIT =
            registerResource(Resource.COPPER, ResourceType.BIT);
    public static final RegistryObject<Item> DIAMOND_BIT =
            registerResource(Resource.DIAMOND, ResourceType.BIT);
    public static final RegistryObject<Item> GOLD_BIT =
            registerResource(Resource.GOLD, ResourceType.BIT);
    public static final RegistryObject<Item> IRON_BIT =
            registerResource(Resource.IRON, ResourceType.BIT);
    public static final RegistryObject<Item> PLATINUM_BIT =
            registerResource(Resource.PLATINUM, ResourceType.BIT);
    public static final RegistryObject<Item> RUBY_BIT =
            registerResource(Resource.RUBY, ResourceType.BIT);
    public static final RegistryObject<Item> SAPPHIRE_BIT =
            registerResource(Resource.SAPPHIRE, ResourceType.BIT);
    public static final RegistryObject<Item> STEEL_BIT =
            registerResource(Resource.STEEL, ResourceType.BIT);
    public static final RegistryObject<Item> TIN_BIT =
            registerResource(Resource.TIN, ResourceType.BIT);
    public static final RegistryObject<Item> URANIUM_BIT =
            registerResource(Resource.URANIUM, ResourceType.BIT);

    //dusts
    public static final RegistryObject<Item> ALUMINIUM_DUST =
            registerResource(Resource.ALUMINIUM, ResourceType.DUST);
    public static final RegistryObject<Item> COPPER_DUST =
            registerResource(Resource.COPPER, ResourceType.DUST);
    public static final RegistryObject<Item> DIAMOND_DUST =
            registerResource(Resource.DIAMOND, ResourceType.DUST);
    public static final RegistryObject<Item> GOLD_DUST =
            registerResource(Resource.GOLD, ResourceType.DUST);
    public static final RegistryObject<Item> IRON_DUST =
            registerResource(Resource.IRON, ResourceType.DUST);
    public static final RegistryObject<Item> PLATINUM_DUST =
            registerResource(Resource.PLATINUM, ResourceType.DUST);
    public static final RegistryObject<Item> RUBY_DUST =
            registerResource(Resource.RUBY, ResourceType.DUST);
    public static final RegistryObject<Item> SAPPHIRE_DUST =
            registerResource(Resource.SAPPHIRE, ResourceType.DUST);
    public static final RegistryObject<Item> STEEL_DUST =
            registerResource(Resource.STEEL, ResourceType.DUST);
    public static final RegistryObject<Item> TIN_DUST =
            registerResource(Resource.TIN, ResourceType.DUST);
    public static final RegistryObject<Item> URANIUM_DUST =
            registerResource(Resource.URANIUM, ResourceType.DUST);

    //enhanced
    public static final RegistryObject<Item> ALUMINIUM_ENHANCED =
            registerResource(Resource.ALUMINIUM, ResourceType.ENHANCED, true);
    public static final RegistryObject<Item> DIAMOND_ENHANCED =
            registerResource(Resource.DIAMOND, ResourceType.ENHANCED, true);
    public static final RegistryObject<Item> IRON_ENHANCED =
            registerResource(Resource.IRON, ResourceType.ENHANCED, true);
    public static final RegistryObject<Item> PLATINUM_ENHANCED =
            registerResource(Resource.PLATINUM, ResourceType.ENHANCED, true);
    public static final RegistryObject<Item> RUBY_ENHANCED =
            registerResource(Resource.RUBY, ResourceType.ENHANCED, true);
    public static final RegistryObject<Item> SAPPHIRE_ENHANCED =
            registerResource(Resource.SAPPHIRE, ResourceType.ENHANCED, true);
    public static final RegistryObject<Item> STEEL_ENHANCED =
            registerResource(Resource.STEEL, ResourceType.ENHANCED, true);

    //ingots
    public static final RegistryObject<Item> ALUMINIUM_INGOT =
            registerResource(Resource.ALUMINIUM, ResourceType.INGOT);
    public static final RegistryObject<Item> COPPER_INGOT =
            registerResource(Resource.COPPER, ResourceType.INGOT);
    public static final RegistryObject<Item> PLATINUM_INGOT =
            registerResource(Resource.PLATINUM, ResourceType.INGOT);
    public static final RegistryObject<Item> RUBY_INGOT =
            registerResource(Resource.RUBY, ResourceType.INGOT);
    public static final RegistryObject<Item> SAPPHIRE_INGOT =
            registerResource(Resource.SAPPHIRE, ResourceType.INGOT);
    public static final RegistryObject<Item> STEEL_INGOT =
            registerResource(Resource.STEEL, ResourceType.INGOT);
    public static final RegistryObject<Item> TIN_INGOT =
            registerResource(Resource.TIN, ResourceType.INGOT);
    public static final RegistryObject<Item> URANIUM_INGOT =
            registerResource(Resource.URANIUM, ResourceType.INGOT);

    //purified
    public static final RegistryObject<Item> PLATINUM_PURIFIED =
            registerResource(Resource.PLATINUM, ResourceType.PURIFIED);
    public static final RegistryObject<Item> STEEL_PURIFIED =
            registerResource(Resource.STEEL, ResourceType.PURIFIED);
    public static final RegistryObject<Item> URANIUM_PURIFIED =
            registerResource(Resource.URANIUM, ResourceType.PURIFIED);

    //tools
    public static final RegistryObject<Item> RUBY_SWORD = registerSword(CraftleToolTier.RUBY);
    public static final RegistryObject<Item> RUBY_PICKAXE = registerPickaxe(CraftleToolTier.RUBY);
    public static final RegistryObject<Item> RUBY_SHOVEL = registerShovel(CraftleToolTier.RUBY);
    public static final RegistryObject<Item> RUBY_AXE = registerAxe(CraftleToolTier.RUBY);
    public static final RegistryObject<Item> RUBY_HOE = registerHoe(CraftleToolTier.RUBY);

    public static final RegistryObject<Item> SAPPHIRE_SWORD =
            registerSword(CraftleToolTier.SAPPHIRE);
    public static final RegistryObject<Item> SAPPHIRE_PICKAXE =
            registerPickaxe(CraftleToolTier.SAPPHIRE);
    public static final RegistryObject<Item> SAPPHIRE_SHOVEL =
            registerShovel(CraftleToolTier.SAPPHIRE);
    public static final RegistryObject<Item> SAPPHIRE_AXE =
            registerAxe(CraftleToolTier.SAPPHIRE);
    public static final RegistryObject<Item> SAPPHIRE_HOE =
            registerHoe(CraftleToolTier.SAPPHIRE);

    public static final RegistryObject<Item> STEEL_SWORD = registerSword(CraftleToolTier.STEEL);
    public static final RegistryObject<Item> STEEL_PICKAXE = registerPickaxe(CraftleToolTier.STEEL);
    public static final RegistryObject<Item> STEEL_SHOVEL = registerShovel(CraftleToolTier.STEEL);
    public static final RegistryObject<Item> STEEL_AXE = registerAxe(CraftleToolTier.STEEL);
    public static final RegistryObject<Item> STEEL_HOE = registerHoe(CraftleToolTier.STEEL);

    public static final RegistryObject<Item> PLATINUM_SWORD =
            registerSword(CraftleToolTier.PLATINUM);
    public static final RegistryObject<Item> PLATINUM_PICKAXE =
            registerPickaxe(CraftleToolTier.PLATINUM);
    public static final RegistryObject<Item> PLATINUM_SHOVEL =
            registerShovel(CraftleToolTier.PLATINUM);
    public static final RegistryObject<Item> PLATINUM_AXE =
            registerAxe(CraftleToolTier.PLATINUM);
    public static final RegistryObject<Item> PLATINUM_HOE =
            registerHoe(CraftleToolTier.PLATINUM);

    // circuit, tier (1-4) circuit
    public static final RegistryObject<Item> CIRCUIT = registerCraftleItem(
            new CraftleItem("circuit", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    public static final RegistryObject<Item> CIRCUIT_TIER_1 = registerCraftleItem(
            new CraftleItem("circuit_tier_1", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    public static final RegistryObject<Item> CIRCUIT_TIER_2 = registerCraftleItem(
            new CraftleItem("circuit_tier_2", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    public static final RegistryObject<Item> CIRCUIT_TIER_3 = registerCraftleItem(
            new CraftleItem("circuit_tier_3", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    public static final RegistryObject<Item> CIRCUIT_TIER_4 = registerCraftleItem(
            new CraftleItem("circuit_tier_4", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    // sheets (iron, steel), compressed sheets
    public static final RegistryObject<Item> IRON_SHEET = registerCraftleItem(
            new CraftleItem("iron_sheet", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    public static final RegistryObject<Item> STEEL_SHEET = registerCraftleItem(
            new CraftleItem("steel_sheet", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    public static final RegistryObject<Item> IRON_SHEET_COMPRESSED = registerCraftleItem(
            new CraftleItem("iron_sheet_compressed",
                    CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    public static final RegistryObject<Item> STEEL_SHEET_COMPRESSED = registerCraftleItem(
            new CraftleItem("steel_sheet_compressed",
                    CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    // coils, gear (basic and advanced)
    public static final RegistryObject<Item> COIL = registerCraftleItem(
            new CraftleItem("coil", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    public static final RegistryObject<Item> COIL_ADVANCED = registerCraftleItem(
            new CraftleItem("coil_advanced", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    public static final RegistryObject<Item> GEAR = registerCraftleItem(
            new CraftleItem("gear", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    public static final RegistryObject<Item> GEAR_ADVANCED = registerCraftleItem(
            new CraftleItem("gear_advanced", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    // motor, tier (1-4)
    public static final RegistryObject<Item> MOTOR = registerCraftleItem(
            new CraftleItem("motor", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    public static final RegistryObject<Item> MOTOR_TIER_1 = registerCraftleItem(
            new CraftleItem("motor_tier_1", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    public static final RegistryObject<Item> MOTOR_TIER_2 = registerCraftleItem(
            new CraftleItem("motor_tier_2", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    public static final RegistryObject<Item> MOTOR_TIER_3 = registerCraftleItem(
            new CraftleItem("motor_tier_3", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    public static final RegistryObject<Item> MOTOR_TIER_4 = registerCraftleItem(
            new CraftleItem("motor_tier_4", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    // batteries, tier (1-4)
    public static final RegistryObject<Item> BATTERY_BASIC = registerCraftleItem(
            new EnergyItem("battery", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES,
                    CraftleBaseTier.BASIC));
    public static final RegistryObject<Item> BATTERY_TIER_1 = registerCraftleItem(
            new EnergyItem("battery", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES,
                    CraftleBaseTier.TIER_1));
    public static final RegistryObject<Item> BATTERY_TIER_2 = registerCraftleItem(
            new EnergyItem("battery", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES,
                    CraftleBaseTier.TIER_2));
    public static final RegistryObject<Item> BATTERY_TIER_3 = registerCraftleItem(
            new EnergyItem("battery", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES,
                    CraftleBaseTier.TIER_3));
    public static final RegistryObject<Item> BATTERY_TIER_4 = registerCraftleItem(
            new EnergyItem("battery", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES,
                    CraftleBaseTier.TIER_4));
    public static final RegistryObject<Item> BATTERY_UNLIMITED = registerCraftleItem(
            new EnergyItem("battery", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES,
                    CraftleBaseTier.UNLIMITED));

    // rubber // insulation
    public static final RegistryObject<Item> RUBBER = registerCraftleItem(
            new CraftleItem("rubber", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));
    public static final RegistryObject<Item> INSULATION = registerCraftleItem(
            new CraftleItem("insulation", CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_MACHINES));


    public static RegistryObject<Item> registerSword(CraftleToolTier tier) {
        CraftleSwordItem item = new CraftleSwordItem(3, -2.4F, tier);
        return registerItem(item.getCraftleRegistryName(), item);
    }

    public static RegistryObject<Item> registerPickaxe(CraftleToolTier tier) {
        CraftlePickaxeItem item = new CraftlePickaxeItem(1, -2.8F, tier);
        return registerItem(item.getCraftleRegistryName(), item);
    }

    public static RegistryObject<Item> registerAxe(CraftleToolTier tier) {
        CraftleAxeItem item = new CraftleAxeItem(5.0F, -3.0F, tier);
        return registerItem(item.getCraftleRegistryName(), item);
    }

    public static RegistryObject<Item> registerHoe(CraftleToolTier tier) {
        CraftleHoeItem item = new CraftleHoeItem(0.0F, tier);
        return registerItem(item.getCraftleRegistryName(), item);
    }

    public static RegistryObject<Item> registerShovel(CraftleToolTier tier) {
        CraftleShovelItem item = new CraftleShovelItem(1.5F, -3.0F, tier);
        return registerItem(item.getCraftleRegistryName(), item);
    }

    public static RegistryObject<Item> registerResource(Resource resource, ResourceType type) {
        CraftleResourceItem item = new CraftleResourceItem(resource, type);
        return registerItem(item.getCraftleRegistryName(), item);
    }

    public static RegistryObject<Item> registerResource(Resource resource, ResourceType type,
                                                        boolean hasEffect) {
        CraftleResourceItem item = new CraftleResourceItem(resource, type, hasEffect);
        return registerItem(item.getCraftleRegistryName(), item);
    }

    public static RegistryObject<Item> registerCraftleItem(CraftleItem item) {
        return ITEMS.register(item.getCraftleRegistryName(), () -> item);
    }

    public static RegistryObject<Item> registerItem(String name, Item item) {
        return ITEMS.register(name, () -> item);
    }

}
