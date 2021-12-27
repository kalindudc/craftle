package com.craftlemod.common.registry;

import com.craftlemod.common.CraftleMod;
import com.craftlemod.common.block.CraftleBlock;
import com.craftlemod.common.block.CraftleOreBlock;
import com.craftlemod.common.block.machine.MachineBlock;
import com.craftlemod.common.block.machine.MachineControllerBlock;
import com.craftlemod.common.blockentity.BlockEntityRecord;
import com.craftlemod.common.blockentity.FluidTankBlockEntity;
import com.craftlemod.common.shared.IHasModelPath;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

public class CraftleBlocks {

    public static final Map<String, IHasModelPath> BLOCKS = new HashMap<>();
    public static final Map<String, CraftleOreBlock> ORE_BLOCKS = new HashMap<>();
    public static final Map<String, PlacedFeature> ORE_FEATURES = new HashMap<>();
    public static final Map<String, ConfiguredFeature<?, ?>> ORE_CONFIGURATIONS = new HashMap<>();

    // resource blocks
    public static final IHasModelPath LEAD_BLOCK = registerResourceBlock("lead_block", "block", FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f).requiresTool());
    public static final IHasModelPath BRONZE_BLOCK = registerResourceBlock("bronze_block", "block", FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f).requiresTool());

    // ores
    public static final IHasModelPath LEAD_ORE = registerOreBlock("lead_ore", FabricBlockSettings.of(Material.STONE).strength(3.0f, 3.0f).requiresTool(), 2, 64, 9);
    public static final IHasModelPath DEEPSLATE_LEAD_ORE = registerOreBlock("deepslate_lead_ore", FabricBlockSettings.of(Material.STONE).strength(4.5f, 3.0f).requiresTool(), -50, 0, 9);
    public static final IHasModelPath MAGNESIUM_ORE = registerOreBlock("magnesium_ore", FabricBlockSettings.of(Material.STONE).strength(3.0f, 3.0f).requiresTool(), 24, 128, 12);
    public static final IHasModelPath DEEPSLATE_MAGNESIUM_ORE = registerOreBlock("deepslate_magnesium_ore", FabricBlockSettings.of(Material.STONE).strength(4.5f, 3.0f).requiresTool(), -30, 10, 12);
    public static final IHasModelPath BAUXITE_ORE = registerOreBlock("bauxite_ore", FabricBlockSettings.of(Material.STONE).strength(3.0f, 3.0f).requiresTool(), 2, 256, 24);
    public static final IHasModelPath DEEPSLATE_BAUXITE_ORE = registerOreBlock("deepslate_bauxite_ore", FabricBlockSettings.of(Material.STONE).strength(4.5f, 3.0f).requiresTool(), -10, 0, 24);

    // Machine blocks
    public static final IHasModelPath FACTORY_BLOCK = registerMachineBlock("factory_block","base", FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f).requiresTool());
    public static final IHasModelPath FACTORY_INTAKE = registerMachineBlock("factory_intake", "base", FabricBlockSettings.of(Material.STONE).strength(4.5f, 3.0f).requiresTool());
    public static final IHasModelPath FACTORY_EXHAUST = registerMachineBlock("factory_exhaust", "base", FabricBlockSettings.of(Material.STONE).strength(4.5f, 3.0f).requiresTool());
    public static final IHasModelPath FLUID_TANK_CONTROLLER = registerMachineBlockWithEntity("fluid_tank_controller", "tank",
        FabricBlockSettings.of(Material.STONE).strength(4.5f, 3.0f).requiresTool(),
        (pos, state) -> new FluidTankBlockEntity(new BlockEntityRecord(CraftleBlockEntityTypes.FLUID_TANK_BLOCK_ENTITY, pos, state)));

    public static void registerAll() {
        for (Map.Entry<String, IHasModelPath> entry : BLOCKS.entrySet()) {
            Registry.register(Registry.BLOCK, entry.getValue().getId(), (Block) entry.getValue());
        }
    }

    public static String createModelJson(String id) {
        if (!BLOCKS.containsKey(id)) {
            return "";
        }

        // @formatter:off
        return "{\n" +
            "\"parent\": \"block/cube_all\",\n" +
            "\"textures\": {\n" +
            "\"all\": \"" + CraftleMod.MODID + ":block/" + BLOCKS.get(id).getModelPath() + "\"\n" +
            "}\n" +
            "}";
        // @formatter:on
    }

    public static void generateOres() {
        for (Map.Entry<String, CraftleOreBlock> entry : ORE_BLOCKS.entrySet()) {
            RuleTest configFeature = OreConfiguredFeatures.STONE_ORE_REPLACEABLES;
            if (entry.getKey().contains("deepslate")) {
                configFeature = OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES;
            }
            ConfiguredFeature<?, ?> config = Feature.ORE
                .configure(new OreFeatureConfig(
                    configFeature,
                    entry.getValue().getDefaultState(),
                    entry.getValue().getVeinSize()));
            PlacedFeature feature = config.withPlacement(
                CountPlacementModifier.of(10),
                SquarePlacementModifier.of(),
                HeightRangePlacementModifier.uniform(YOffset.fixed(entry.getValue().getMinY()), YOffset.fixed(entry.getValue().getMaxY())));

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, entry.getValue().getId(), config);
            Registry.register(BuiltinRegistries.PLACED_FEATURE, entry.getValue().getId(), feature);
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, entry.getValue().getId()));

            ORE_CONFIGURATIONS.put(entry.getKey(), config);
            ORE_FEATURES.put(entry.getKey(), feature);
        }
    }

    private static IHasModelPath registerOreBlock(String name, FabricBlockSettings settings, int minY, int maxY, int veinSize) {
        CraftleOreBlock block = new CraftleOreBlock(new Identifier(CraftleMod.MODID, name), "resource/ore/" + name, settings, minY, maxY, veinSize);
        ORE_BLOCKS.put(name, block);
        return registerBlock(name, block, CraftleMod.ITEM_GROUP_RESOURCES);
    }

    private static IHasModelPath registerResourceBlock(String name, String resourceType, FabricBlockSettings settings) {
        CraftleBlock block = new CraftleBlock(new Identifier(CraftleMod.MODID, name), "resource/" + resourceType + "/" + name, settings);
        return registerBlock(name, block, CraftleMod.ITEM_GROUP_RESOURCES);
    }

    private static IHasModelPath registerMachineBlock(String name, String machineType, FabricBlockSettings settings) {

        IHasModelPath block = new MachineBlock(new Identifier(CraftleMod.MODID, name), "machine/" + machineType + "/" + name, settings);
        return registerBlock(name, block, CraftleMod.ITEM_GROUP_MACHINES);
    }

    private static IHasModelPath registerMachineBlockWithEntity(String name, String machineType, FabricBlockSettings settings, BiFunction<BlockPos, BlockState, BlockEntity> constructor) {

        IHasModelPath block = new MachineControllerBlock(new Identifier(CraftleMod.MODID, name), "machine/" + machineType + "/" + name, settings, constructor);
        return registerBlock(name, block, CraftleMod.ITEM_GROUP_MACHINES);
    }

    private static IHasModelPath registerBlock(String name, IHasModelPath block, ItemGroup group) {
        BLOCKS.put(name, block);
        CraftleItems.registerBlockItem(block, group);
        return block;
    }
}
