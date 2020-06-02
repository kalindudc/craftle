package com.craftle_mod.common;

import com.craftle_mod.common.block.CraftleQuarry;
import com.craftle_mod.common.block.SpecialBlock;
import com.craftle_mod.common.init.CraftleBlocks;
import com.craftle_mod.common.init.CraftleItemGroups;
import com.craftle_mod.common.item.SpecialItem;
import com.craftle_mod.common.item.gear.CraftleArmorMaterial;
import com.craftle_mod.common.tier.CraftleItemTier;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = Craftle.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CraftleEventSubscriber {

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {

        // TEST: test item in game
        event.getRegistry().registerAll(setup(new Item(new Item.Properties()
                                                               .group(CraftleItemGroups.CRAFTLE_ITEM_GROUP)),
                                              "test_ingot"),
                                        setup(new BlockItem(
                                                      CraftleBlocks.TEST_ORE,
                                                      new Item.Properties()
                                                              .group(CraftleItemGroups.CRAFTLE_ITEM_GROUP)),
                                              "test_ore"),
                                        setup(new BlockItem(
                                                      CraftleBlocks.SPECIAL_BLOCK,
                                                      new Item.Properties()
                                                              .group(CraftleItemGroups.CRAFTLE_ITEM_GROUP)),
                                              "special_block"),
                                        setup(new BlockItem(
                                                      CraftleBlocks.QUARRY,
                                                      new Item.Properties()
                                                              .group(CraftleItemGroups.CRAFTLE_ITEM_GROUP)),
                                              "quarry"),
                                        setup(new Item(new Item.Properties()
                                                               .group(CraftleItemGroups.CRAFTLE_ITEM_GROUP)
                                                               .food(new Food.Builder()
                                                                             .hunger(3)
                                                                             .saturation(
                                                                                     1.2F)
                                                                             .effect(() -> (new EffectInstance(
                                                                                             Effects.GLOWING,
                                                                                             4000)),
                                                                                     1)
                                                                             .build())),
                                              "test_food"),
                                        setup(new PickaxeItem(
                                                      CraftleItemTier.TEST,
                                                      12, 5.0F,
                                                      new Item.Properties()
                                                              .group(CraftleItemGroups.CRAFTLE_ITEM_GROUP)),
                                              "test_tool"),
                                        setup(new SpecialItem(
                                                      new Item.Properties()
                                                              .group(CraftleItemGroups.CRAFTLE_ITEM_GROUP)),
                                              "special_item"),

                                        setup(new ArmorItem(
                                                      CraftleArmorMaterial.TEST_INGOT,
                                                      EquipmentSlotType.FEET,
                                                      new Item.Properties()
                                                              .group(CraftleItemGroups.CRAFTLE_ITEM_GROUP)),
                                              "test_boots"));
    }

    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {

        // TEST: test block in game
        event.getRegistry().registerAll(setup(new Block(
                                                Block.Properties.create(Material.ROCK)
                                                                .hardnessAndResistance(3.0F, 3.0F)
                                                                .sound(SoundType.STONE)), "test_ore"),
                                        setup(new SpecialBlock(
                                                      Block.Properties
                                                              .create(Material.IRON)
                                                              .hardnessAndResistance(
                                                                      2.0F,
                                                                      10.0F)
                                                              .harvestLevel(2)
                                                              .harvestTool(
                                                                      ToolType.PICKAXE)
                                                              .sound(SoundType.METAL)
                                                              .lightValue(4)),
                                              "special_block"),
                                        setup(new CraftleQuarry(
                                                      Block.Properties
                                                              .create(Material.IRON)
                                                              .hardnessAndResistance(
                                                                      2.0F,
                                                                      10.0F)
                                                              .harvestLevel(2)
                                                              .harvestTool(
                                                                      ToolType.PICKAXE)
                                                              .sound(SoundType.METAL)
                                                              .lightValue(4)),
                                              "quarry"));
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry,
                                                             final String name) {
        return setup(entry, new ResourceLocation(Craftle.MODID, name));
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry,
                                                             final ResourceLocation registryName) {
        entry.setRegistryName(registryName);
        return entry;
    }


}
