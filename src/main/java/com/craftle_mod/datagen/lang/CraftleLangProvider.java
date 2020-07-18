package com.craftle_mod.datagen.lang;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.registries.CraftleFluids;
import com.craftle_mod.common.registries.CraftleItems;
import com.craftle_mod.common.resource.BlockResource;
import com.craftle_mod.common.resource.OreResource;
import com.craftle_mod.common.resource.Resource;
import com.craftle_mod.common.resource.ResourceType;
import com.craftle_mod.common.tier.CraftleToolTier;
import com.craftle_mod.common.util.CraftleUtils;
import java.util.Arrays;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.Item;
import net.minecraft.util.Util;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

public class CraftleLangProvider extends LanguageProvider {

    public CraftleLangProvider(DataGenerator gen) {
        super(gen, Craftle.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addResources();
        addBlocks();
        addItems();
        addFluids();
    }

    public void addResources() {

        BlockResource[] resourceBlocksFilter = new BlockResource[]{BlockResource.WOOD,
            BlockResource.URANIUM};
        Resource[] ingotsFilter = new Resource[]{Resource.DIAMOND, Resource.IRON, Resource.GOLD};
        Resource[] enhancedIngotsFilter = new Resource[]{Resource.URANIUM, Resource.TIN,
            Resource.GOLD, Resource.COPPER};

        // ores
        for (OreResource resource : OreResource.values()) {
            add(CraftleUtils.buildBlockTranslationKey(
                resource.getResourceName() + "_" + ResourceType.ORE.getResourceName()),
                resource.getFormattedName() + " Ore");
        }

        // resource blocks
        for (BlockResource resource : BlockResource.values()) {
            if (Arrays.stream(resourceBlocksFilter)
                .noneMatch(x -> x.getResourceName().equals(resource.getResourceName()))) {
                add(CraftleUtils.buildBlockTranslationKey(
                    resource.getResourceName() + "_" + ResourceType.BLOCK.getResourceName()),
                    resource.getFormattedName() + " Block");
            }
        }

        for (Resource resource : Resource.values()) {

            if (Arrays.stream(ingotsFilter)
                .noneMatch(x -> x.getResourceName().equals(resource.getResourceName()))) {
                add(CraftleUtils.buildItemTranslationKey(
                    resource.getResourceName() + "_" + ResourceType.INGOT.getResourceName()),
                    resource.getFormattedName() + " Ingot");
            }

            if (Arrays.stream(enhancedIngotsFilter)
                .noneMatch(x -> x.getResourceName().equals(resource.getResourceName()))) {
                add(CraftleUtils.buildItemTranslationKey(
                    resource.getResourceName() + "_" + ResourceType.ENHANCED.getResourceName()),
                    "Enhanced " + resource.getFormattedName() + " Ingot");
            }

            add(CraftleUtils.buildItemTranslationKey(
                resource.getResourceName() + "_" + ResourceType.BIT.getResourceName()),
                resource.getFormattedName() + " Bit");

            add(CraftleUtils.buildItemTranslationKey(
                resource.getResourceName() + "_" + ResourceType.DUST.getResourceName()),
                resource.getFormattedName() + " Dust");
        }

        // special ingots
        add(CraftleUtils.buildItemTranslationKey(
            Resource.PLATINUM.getResourceName() + "_" + ResourceType.PURIFIED.getResourceName()),
            "Purified " + Resource.PLATINUM.getFormattedName() + " Ingot");
        add(CraftleUtils.buildItemTranslationKey(
            Resource.STEEL.getResourceName() + "_" + ResourceType.PURIFIED.getResourceName()),
            "Purified " + Resource.STEEL.getFormattedName() + " Ingot");
        add(CraftleUtils.buildItemTranslationKey(
            Resource.URANIUM.getResourceName() + "_" + ResourceType.PURIFIED.getResourceName()),
            "Purified " + Resource.URANIUM.getFormattedName() + " Ingot");
    }

    private void addBlocks() {

        // regular
        add(CraftleBlocks.COAL_GENERATOR.get(), "Coal Generator");
        add(CraftleBlocks.WORKBENCH.get(), "Workbench");
        add(CraftleBlocks.CABLE_CONNECTOR.get(), "Cable Connector");

        // tiered
        addTiered(CraftleBlocks.MACHINE_BASE_BASIC, CraftleBlocks.MACHINE_BASE_TIER_1,
            CraftleBlocks.MACHINE_BASE_TIER_2, CraftleBlocks.MACHINE_BASE_TIER_3,
            CraftleBlocks.MACHINE_BASE_TIER_4, "Machine Base");

        // tiered with containers
        addTieredWithContainer(CraftleBlocks.CRUSHER_BASIC, CraftleBlocks.CRUSHER_TIER_1,
            CraftleBlocks.CRUSHER_TIER_2, CraftleBlocks.CRUSHER_TIER_3,
            CraftleBlocks.CRUSHER_TIER_4, "Crusher");

        addTieredWithContainer(CraftleBlocks.ENERGY_TANK_BASIC, CraftleBlocks.ENERGY_TANK_TIER_1,
            CraftleBlocks.ENERGY_TANK_TIER_2, CraftleBlocks.ENERGY_TANK_TIER_3,
            CraftleBlocks.ENERGY_TANK_TIER_4, "Energy Tank");
    }

    private void addItems() {
        addTools();

        // regular
        add(CraftleItems.IRON_SHEET.get(), "Iron Sheet");
        add(CraftleItems.STEEL_SHEET.get(), "Steel Sheet");
        add(CraftleItems.IRON_SHEET_COMPRESSED.get(), "Compressed Iron Sheet");
        add(CraftleItems.STEEL_SHEET_COMPRESSED.get(), "Compressed Steel Sheet");
        add(CraftleItems.COIL.get(), "Coil");
        add(CraftleItems.COIL_ADVANCED.get(), "Advanced Coil");
        add(CraftleItems.GEAR.get(), "Gear");
        add(CraftleItems.GEAR_ADVANCED.get(), "Advanced Gear");
        add(CraftleItems.RUBBER.get(), "Rubber");
        add(CraftleItems.INSULATION.get(), "Insulation");

        // tiered
        addTiered(CraftleItems.CIRCUIT.get().getTranslationKey(),
            CraftleItems.CIRCUIT_TIER_1.get().getTranslationKey(),
            CraftleItems.CIRCUIT_TIER_2.get().getTranslationKey(),
            CraftleItems.CIRCUIT_TIER_3.get().getTranslationKey(),
            CraftleItems.CIRCUIT_TIER_4.get().getTranslationKey(), "Circuit");
        addTiered(CraftleItems.MOTOR.get().getTranslationKey(),
            CraftleItems.MOTOR_TIER_1.get().getTranslationKey(),
            CraftleItems.MOTOR_TIER_2.get().getTranslationKey(),
            CraftleItems.MOTOR_TIER_3.get().getTranslationKey(),
            CraftleItems.MOTOR_TIER_4.get().getTranslationKey(), "Motor");
        addTiered(CraftleItems.BATTERY_BASIC.get().getTranslationKey(),
            CraftleItems.BATTERY_TIER_1.get().getTranslationKey(),
            CraftleItems.BATTERY_TIER_2.get().getTranslationKey(),
            CraftleItems.BATTERY_TIER_3.get().getTranslationKey(),
            CraftleItems.BATTERY_TIER_4.get().getTranslationKey(), "Battery");
        addUnlimited(CraftleItems.BATTERY_UNLIMITED.get().getTranslationKey(), "Unlimited Battery");
    }

    private void addUnlimited(String key, String name) {
        add(key, "\u00A7d" + name);
    }

    private void addTools() {
        for (CraftleToolTier tier : CraftleToolTier.values()) {
            // sword
            add(CraftleUtils.buildItemTranslationKey(tier.getMaterialName() + "_sword"),
                tier.getResource().getFormattedName() + " Sword");
            // shovel
            add(CraftleUtils.buildItemTranslationKey(tier.getMaterialName() + "_shovel"),
                tier.getResource().getFormattedName() + " Shovel");
            // pickaxe
            add(CraftleUtils.buildItemTranslationKey(tier.getMaterialName() + "_pickaxe"),
                tier.getResource().getFormattedName() + " Pickaxe");
            // axe
            add(CraftleUtils.buildItemTranslationKey(tier.getMaterialName() + "_axe"),
                tier.getResource().getFormattedName() + " Axe");
            // hoe
            add(CraftleUtils.buildItemTranslationKey(tier.getMaterialName() + "_hoe"),
                tier.getResource().getFormattedName() + " Hoe");
        }
    }

    private void addFluids() {
        addFluid(CraftleFluids.ENERGY_FLUID, CraftleFluids.ENERGY_FLOWING, CraftleBlocks.ENERGY,
            CraftleItems.ENERGY_BUCKET, "Energy");
    }

    private void addFluid(RegistryObject<FlowingFluid> still, RegistryObject<FlowingFluid> flowing,
        RegistryObject<Block> block, RegistryObject<Item> bucket, String name) {
        add(block.get(), name);
        add("flowing_" + block.get().getTranslationKey(), "Flowing " + name);
        add(bucket.get(), name + " Bucket");
    }

    private void addWithContainer(RegistryObject<Block> registryObject, String name) {
        add(registryObject.get(), name);
        add(Util.makeTranslationKey("container", registryObject.get().getRegistryName()), name);
    }

    private void addTiered(RegistryObject<Block> basic, RegistryObject<Block> tier1,
        RegistryObject<Block> tier2, RegistryObject<Block> tier3, RegistryObject<Block> tier4,
        String name) {

        add(basic.get(), name);
        add(tier1.get(), name + " Tier 1");
        add(tier2.get(), "\u00A7e" + name + " Tier 2");
        add(tier3.get(), "\u00A7b" + name + " Tier 3");
        add(tier4.get(), "\u00A74" + name + " Tier 4");
    }

    private void addTiered(String basic, String tier1, String tier2, String tier3, String tier4,
        String name) {

        add(basic, name);
        add(tier1, name + " Tier 1");
        add(tier2, "\u00A7e" + name + " Tier 2");
        add(tier3, "\u00A7b" + name + " Tier 3");
        add(tier4, "\u00A74" + name + " Tier 4");
    }

    private void addTieredWithContainer(RegistryObject<Block> basic, RegistryObject<Block> tier1,
        RegistryObject<Block> tier2, RegistryObject<Block> tier3, RegistryObject<Block> tier4,
        String name) {

        addWithContainer(basic, name);
        addWithContainer(tier1, name + " Tier 1");
        addWithContainer(tier2, "\u00A7e" + name + " Tier 2");
        addWithContainer(tier3, "\u00A7b" + name + " Tier 3");
        addWithContainer(tier4, "\u00A74" + name + " Tier 4");
    }
}
